package com.room.dao;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

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
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.persist(item);
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
			List<Items> users = (List<Items>) session
					.createQuery("FROM Items where hex(user.id) =:user_id")
					.setParameter("user_id", user_id).getResultList();
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
					.createQuery("FROM Categories where is_man= :is_man")
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
					.createQuery("FROM Items where global =:global")
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
}
