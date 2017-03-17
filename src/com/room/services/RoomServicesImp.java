package com.room.services;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.room.dao.RoomDAO;
import com.room.model.ItemLikes;
import com.room.model.Items;
import com.room.model.Categories;
import com.room.model.User;

@Transactional(readOnly = true)
public class RoomServicesImp implements RoomServices, Serializable {

	private static final long serialVersionUID = 1L;
	private RoomDAO roomDAO;

	public void setRoomDAO(RoomDAO roomDAO) {
		this.roomDAO = roomDAO;
	}

	@Override
	public List<User> getUUID() {
		return roomDAO.getUUID();
	}

	@Transactional(readOnly = false)
	@Override
	public void addTAGS() {
		roomDAO.addTAGS();
	}

	@Transactional(readOnly = false)
	@Override
	public void addUUID() {
		roomDAO.addUUID();
	}

	@Override
	public User login(User user) {
		// TODO Auto-generated method stub
		return roomDAO.login(user);
	}

	@Override
	public boolean addItems(Items item) {
		return roomDAO.addItems(item);
	}

	@Override
	public List<Items> findAllItems(User user) {
		// TODO Auto-generated method stub
		return roomDAO.findAllItems(user);
	}

	@Override
	public List<Categories> findManCategories() {
		// TODO Auto-generated method stub
		return roomDAO.findManCategories();
	}

	@Override
	public List<Categories> findWomenCategories() {
		// TODO Auto-generated method stub
		return roomDAO.findWomenCategories();
	}

	@Override
	public List<Categories> findSubCategoriesById(Integer cate_id) {
		// TODO Auto-generated method stub
		return roomDAO.findSubCategoriesById(cate_id);
	}

	@Transactional(readOnly = false)
	@Override
	public boolean updateItems(Items item) {
		// TODO Auto-generated method stub
		return roomDAO.updateItems(item);
	}

	@Override
	public List<Items> findAllGlobalItems() {
		// TODO Auto-generated method stub
		return roomDAO.findAllGlobalItems();
	}

	@Override
	public List<Items> findAlmostOverdueItem(String interval, String now,
			String user_id) {
		// TODO Auto-generated method stub
		return roomDAO.findAlmostOverdueItem(interval, now, user_id);
	}

	@Override
	public boolean updateUser(User user) {
		// TODO Auto-generated method stub
		return roomDAO.updateUser(user);
	}

	@Override
	public User addUser(User user) {
		// TODO Auto-generated method stub
		return roomDAO.addUser(user);
	}

	@Override
	public boolean updateItemLike(ItemLikes itemLike) {
		// TODO Auto-generated method stub
		return roomDAO.updateItemLike(itemLike);
	}

	@Override
	public List<Items> findAllLikedItems(User user) {
		// TODO Auto-generated method stub
		return roomDAO.findAllLikedItems(user);
	}

	@Override
	public String checkUsername(String username) {
		// TODO Auto-generated method stub
		return roomDAO.checkUsername(username);
	}
}
