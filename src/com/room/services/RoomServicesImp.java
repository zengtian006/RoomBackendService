package com.room.services;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.room.dao.RoomDAO;
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
	public List<Items> findAllItems(String user_id) {
		// TODO Auto-generated method stub
		return roomDAO.findAllItems(user_id);
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
}
