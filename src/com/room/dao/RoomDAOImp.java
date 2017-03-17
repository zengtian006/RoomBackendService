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

import com.room.model.ItemLikes;
import com.room.model.ItemSeason;
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
	public void addTAGS() {
		Session session = null;
		Transaction tx = null;

		// ItemLikes like = new ItemLikes();
		// like.setItemId(UUID.fromString("0a00036a-5a89-1bbb-815a-893be4070000"));
		// like.setUserId(UUID.fromString("0a00036a-5a26-1b79-815a-269d607e0000"));
		// user.setId(uuId);

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			// session.persist(like);
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
			for (int i = 0; i < item.getTags().size(); i++) {
				ItemTags it = new ItemTags(item.getId(), item.getTags().get(i));
				session.persist(it);
				if (i % 20 == 0) {
					session.flush();
					session.clear();
				}
			}
			for (int i = 0; i < item.getSeasons().size(); i++) {
				ItemSeason is = new ItemSeason(item.getId(), item.getSeasons()
						.get(i));
				session.persist(is);
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
	public List<Items> findAllItems(User user) {
		Session session = null;
		String user_id = user.getId().toString().replaceAll("-", "");

		try {
			session = sessionFactory.openSession();
			System.out.println("user id: " + user_id);
			List<Items> items = new ArrayList<>();

			if (user.getIsPublic() == null) {
				items = (List<Items>) session
						.createQuery(
								"FROM Items i WHERE hex(user.id) =:user_id ORDER BY i.created DESC")
						.setParameter("user_id", user_id).getResultList();
			} else if (user.getIsPublic().equals("1")) {
				items = (List<Items>) session
						.createQuery(
								"FROM Items i WHERE hex(user.id) =:user_id and global =:global ORDER BY i.created DESC")
						.setParameter("user_id", user_id)
						.setParameter("global", "1").getResultList();
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
	public List<Items> findAlmostOverdueItem(String interval, String now,
			String user_id) {
		Session session = null;
		user_id = user_id.replaceAll("-", "");

		System.out.println("INTERVAL: " + interval);
		System.out.println("now: " + now);

		try {
			session = sessionFactory.openSession();
			List<Items> users = null;
			if (interval.equals(now)) {// already overduedItem
				users = (List<Items>) session
						.createQuery(
								"FROM Items i where i.expDate< :now and hex(user.id) =:user_id ORDER BY i.expDate ASC")
						.setParameter("now", now)
						.setParameter("user_id", user_id).getResultList();
			} else {
				users = (List<Items>) session
						.createQuery(
								"FROM Items i where i.expDate<= :interval and i.expDate>= :now and hex(user.id) =:user_id ORDER BY i.expDate ASC")
						.setParameter("interval", interval)
						.setParameter("now", now)
						.setParameter("user_id", user_id).getResultList();

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

	@Override
	public boolean updateUser(User user) {
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.update(user);
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

	@Override
	public User addUser(User user) {
		Session session = null;
		Transaction tx = null;

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.persist(user);
			tx.commit();
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			return null;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
				session = null;
			}
		}
	}

	@Override
	public boolean updateItemLike(ItemLikes itemLike) {
		Session session = null;
		Transaction tx = null;

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();

			String item_id = itemLike.getItemId().toString()
					.replaceAll("-", "");
			String user_id = itemLike.getUserId().toString()
					.replaceAll("-", "");
			@SuppressWarnings("unchecked")
			List<ItemLikes> hasLiked = (List<ItemLikes>) session
					.createQuery(
							"FROM ItemLikes where hex(item_id) = :item_id and hex(user_id) = :user_id")
					.setParameter("item_id", item_id)
					.setParameter("user_id", user_id).getResultList();
			if (hasLiked.size() > 0) {
				session.delete(hasLiked.get(0));
			} else {
				session.persist(itemLike);
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
	public List<Items> findAllLikedItems(User user) {
		Session session = null;
		Transaction tx = null;

		String user_id = user.getId().toString().replaceAll("-", "");

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();

			System.out.println("user id: " + user_id);
			List<ItemLikes> itemLikeList = (List<ItemLikes>) session
					.createQuery("FROM ItemLikes WHERE hex(userId) =:user_id")
					.setParameter("user_id", user_id).getResultList();
			if (itemLikeList.size() > 0) {
				String itemIdString = "";
				List<String> itemIdList = new ArrayList<>();
				for (ItemLikes itemLike : itemLikeList) {
					String item_id = itemLike.getItemId().toString()
							.replaceAll("-", "");
					itemIdList.add(item_id);
					itemIdString += item_id + ",";
				}
				itemIdString = itemIdString.substring(0,
						itemIdString.length() - 1);
				System.out.println("IdList: " + itemIdString);

				List<Items> items = (List<Items>) session
						.createQuery(
								"FROM Items WHERE hex(id) IN (:item_id_list) ORDER BY created DESC")
						.setParameter("item_id_list", itemIdList)
						.getResultList();
				System.out.println("SIZE: " + items.size());
				System.out.println("title: " + items.get(0).getTitle());
				tx.commit();
				return items;
			} else {
				tx.commit();
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
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
	public String checkUsername(String username) {
		Session session = null;

		try {
			session = sessionFactory.openSession();
			List<Items> users = (List<Items>) session
					.createQuery(
							"FROM User where name =:name")
					.setParameter("name", username).getResultList();
			if(users.size()>0){
				return "Login";
			}else{
				return "Register";
			}
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
