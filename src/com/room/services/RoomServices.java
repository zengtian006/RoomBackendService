package com.room.services;

import java.util.List;
import java.util.UUID;

import javax.enterprise.inject.Default;

import com.room.model.Items;
import com.room.model.Categories;
import com.room.model.User;

public interface RoomServices {

	public List<User> getUUID();

	public void addUUID();

	public User login(User user);

	public boolean addItems(Items item);

	public List<Items> findAllItems(String user_id);

	public List<Categories> findManCategories();

	public List<Categories> findWomenCategories();

	public List<Categories> findSubCategoriesById(Integer cate_id);

	public boolean updateItems(Items item);

	public List<Items> findAllGlobalItems();

	public List<Items> findAlmostOverdueItem(String interval, String now);

}
