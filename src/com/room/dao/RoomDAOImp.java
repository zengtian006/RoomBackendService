package com.room.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import com.room.model.ItemTags;
import com.room.model.Items;
import com.room.model.Categories;
import com.room.model.User;

public class RoomDAOImp implements RoomDAO, Serializable {

	private static final long serialVersionUID = 1L;
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUUID() {

		Session session = null;

		try {
			session = sessionFactory.openSession();

			List<User> users = (List<User>) session.createQuery("FROM User")
					.getResultList();
			return users;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
				session = null;
			}

		}

	}

	@Override
	public void addUUID() {
		Session session = null;
		Transaction tx = null;

		User user = new User();
		UUID uuId = UUID.randomUUID();
		user.setName("tim");
		user.setPassword("111");
		// user.setId(uuId);
		System.out.println(uuId);

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.persist(user);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
				session = null;
			}
		}

	}

	@Override
	public User login(User user) {
		Session session = null;

		try {
			session = sessionFactory.openSession();

			return (User) session
					.createQuery(
							"FROM User where name= :name and password = :password")
					.setParameter("name", user.getName())
					.setParameter("password", user.getPassword())
					.getSingleResult();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
				session = null;
			}
		}
	}

	@Override
	public boolean addItems(Items item) {
		Session session = null;
		Transaction tx = null;
		try {
			item.setDate(null);// delete later
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.persist(item);
			for (int i = 0; i < item.getTags().size(); i++) {
				ItemTags it = new ItemTags(item.getId(), item.getTags().get(i));
				session.persist(it);
				if (i % 20 == 0) {
					session.flush();
					session.clear();
				}
			}
			tx.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			return false;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
				session = null;
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Items> findAllItems(String user_id) {
		Session session = null;

		try {
			session = sessionFactory.openSession();
			System.out.println("user id: " + user_id);
			List<Items> items = (List<Items>) session
					.createQuery(
							"FROM Items i WHERE hex(user.id) =:user_id ORDER BY i.created DESC")
					.setParameter("user_id", user_id).getResultList();
			for (Items item : items) {
				System.out.println("TAG SIZE: " + item.getItemTags().size());
			}
			// for (Items item : items) {
			// Iterator<ItemTags> iterator = item.getItemTags().iterator();
			// List<String> tagList = new ArrayList<>();
			// while (iterator.hasNext()) {
			// String tag = iterator.next().getTag();
			// System.out.println("TAG:" + tag);
			// tagList.add(tag);
			// }
			// item.setTags(tagList);
			// item.setItemTags(null);
			// }
			return items;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
				session = null;
			}

		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Categories> findManCategories() {
		Session session = null;

		try {
			session = sessionFactory.openSession();

			List<Categories> cates = (List<Categories>) session
					.createQuery("FROM Categories where is_man= :is_man")
					.setParameter("is_man", "Y").getResultList();
			return cates;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
				session = null;
			}

		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Categories> findWomenCategories() {
		Session session = null;

		try {
			session = sessionFactory.openSession();

			List<Categories> cates = (List<Categories>) session
					.createQuery("FROM Categories c where is_man= :is_man")
					.setParameter("is_man", "N").getResultList();
			return cates;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
				session = null;
			}

		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Categories> findSubCategoriesById(Integer cate_id) {
		Session session = null;

		try {
			session = sessionFactory.openSession();

			List<Categories> cates = (List<Categories>) session
					.createQuery("FROM Categories where parent_id= :parent_id")
					.setParameter("parent_id", cate_id).getResultList();
			return cates;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
				session = null;
			}

		}
	}

	@Override
	public boolean updateItems(Items item) {
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.update(item);
			tx.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			return false;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
				session = null;
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Items> findAllGlobalItems() {
		Session session = null;

		try {
			session = sessionFactory.openSession();
			List<Items> users = (List<Items>) session
					.createQuery(
							"FROM Items i where i.global =:global  ORDER BY i.created DESC")
					.setParameter("global", "1").getResultList();
			return users;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
				session = null;
			}

		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Items> findAlmostOverdueItem(String interval, String now) {
		Session session = null;
		System.out.println("INTERVAL: " + interval);
		System.out.println("now: " + now);

		try {
			session = sessionFactory.openSession();
			List<Items> users = null;
			if (interval.equals(now)) {// already overduedItem
				users = (List<Items>) session
						.createQuery(
								"FROM Items i where i.expDate< :now ORDER BY i.expDate ASC")
						.setParameter("now", now).getResultList();
			} else {
				users = (List<Items>) session
						.createQuery(
								"FROM Items i where i.expDate<= :interval and i.expDate>= :now ORDER BY i.expDate ASC")
						.setParameter("interval", interval)
						.setParameter("now", now).getResultList();

			}
			return users;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
				session = null;
			}

		}
	}
}
