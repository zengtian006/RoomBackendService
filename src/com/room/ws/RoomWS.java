package com.room.ws;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.room.model.ItemLikes;
import com.room.model.ItemSeason;
import com.room.model.ItemSeries;
import com.room.model.ItemTags;
import com.room.model.Items;
import com.room.model.Categories;
import com.room.model.TagEntry;
import com.room.model.User;
import com.room.model.UserResponse;
import com.room.services.RoomServices;

@Component
@Path("/")
public class RoomWS implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	RoomServices roomServices;

	public RoomWS() {
		// TODO Auto-generated constructor stub
	}

	@GET
	@Path("getUUID")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUUID() {
		return roomServices.getUUID();
	}

	@POST
	@Path("login")
	@Produces(MediaType.APPLICATION_JSON)
	public UserResponse login(User user) {
		User loginUser = roomServices.login(user);
		UserResponse userResponse = new UserResponse();
		userResponse.setStatus("YES");
		userResponse.setSuccess(true);
		userResponse.setUser(loginUser);
		return userResponse;
	}

	@POST
	@Path("updateUser")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String updateUser(User user) {
		boolean result = roomServices.updateUser(user);
		return String.valueOf(result);
	}

	@POST
	@Path("addUser")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public User addUser(User user) {
		return roomServices.addUser(user);
	}

	@POST
	@Path("addLikes")
	@Produces(MediaType.APPLICATION_JSON)
	public String addTAGS() {
		roomServices.addTAGS();
		return "ture";
	}

	@POST
	@Path("addUUID")
	@Produces(MediaType.APPLICATION_JSON)
	public String addUUID() {
		roomServices.addUUID();
		return "ture";
	}

	@POST
	@Path("addItem")
	@Produces(MediaType.APPLICATION_JSON)
	public String addItem(Items item) {
		// Items item1 = new Items();
		// item1.setUserId(UUID.randomUUID());
		// item1.setTitle("fly");

		System.out.println(item.getBrand());
		// item.setUserId(UUID.randomUUID());
		System.out.println(item.getImageName());
		return String.valueOf(roomServices.addItems(item));
	}

	// @GET
	// @Path("/findAllItemsTest/{userid}")
	// @Consumes(MediaType.APPLICATION_JSON)
	// public List<Items> findAllItemsTest(@PathParam("userid") String user_id)
	// {
	// // UUID uuid = UUID.fromString(user_id);
	// String hex_user_id = user_id.replaceAll("-", "");
	// System.out.println("haha:" + hex_user_id);
	// return roomServices.findAllItems(hex_user_id);
	// }

	@POST
	@Path("findAllItems")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<ItemSeries> findAllItems(User user) {
		List<ItemSeries> itemSeriesList = new ArrayList<ItemSeries>();

		List<Items> items = roomServices.findAllItems(user);

		// init Categroy
		List<Categories> categories = new ArrayList<Categories>();
		if (user.getGender().equals("M")) {
			categories = roomServices.findManCategories();
		} else {
			categories = roomServices.findWomenCategories();
		}

		for (Categories cate : categories) {
			if (cate.getParentId() == 0) {
				ItemSeries itemSeries = new ItemSeries();
				itemSeries.setCate_id(cate.getId());
				itemSeries.setTitle(cate.getCateName());
				itemSeries.setTitle_cn(cate.getCateNameCn());
				List<Items> filetedAllItems = new ArrayList<Items>();
				for (Categories subcate : categories) {
					if (subcate.getParentId() == cate.getId()) {
						System.out.println("subcate: " + subcate.getCateName());
						List<Items> filetedItems = items
								.stream()
								.filter(x -> subcate.getId().equals(
										x.getCateId()))
								.collect(Collectors.toList());
						if (filetedItems.size() > 0) {
							filetedAllItems.addAll(filetedItems);
						}

					}
				}
				Collections.sort(filetedAllItems);

				// init Tags like season
				List<String> allTagsList = new ArrayList<>();
				for (Items item : filetedAllItems) {
					List<String> tagList = new ArrayList<>();
					for (ItemTags tag : item.getItemTags()) {
						tagList.add(tag.getTag());
					}
					item.setTags(tagList);
					allTagsList.addAll(tagList);

					if (item.getItemLikes().isEmpty()) {
						item.setLikesCount(0);
					} else {
						item.setLikesCount(item.getItemLikes().size());
					}

					List<String> seasonList = new ArrayList<>();
					for (ItemSeason season : item.getItemSeason()) {
						seasonList.add(season.getSeason());
					}
					item.setSeasons(seasonList);
				}
				Map<String, Long> result = allTagsList.stream().collect(
						Collectors.groupingBy(Function.identity(),
								Collectors.counting()));

				Map<String, Long> finalMap = new LinkedHashMap<>();

				// Sort a map and add to finalMap
				result.entrySet()
						.stream()
						.sorted(Map.Entry.<String, Long> comparingByValue()
								.reversed())
						.forEachOrdered(
								e -> finalMap.put(e.getKey(), e.getValue()));

				List<TagEntry> tagEntryList = new ArrayList<>();
				Iterator<Entry<String, Long>> iter = finalMap.entrySet()
						.iterator();
				while (iter.hasNext()) {
					Entry<String, Long> entry = iter.next();
					String key = (String) entry.getKey();
					Long val = (Long) entry.getValue();
					System.out.println(key + ":" + val);
					tagEntryList.add(new TagEntry(key, val));
				}
				itemSeries.setAllTagsMap(tagEntryList);
				itemSeries.setItems(filetedAllItems);
				if (itemSeries.getTitle().equals("collocation")) {
					itemSeriesList.add(0, itemSeries);
				} else {
					itemSeriesList.add(itemSeries);
				}
			}
		}

		// System.out.println("haha:" + user.getName());
		// System.out.println("haha:" + user.getId().toString());
		// List<ItemSeries> itemSeriesList = new ArrayList<ItemSeries>();
		//
		// itemSeries.setTitle("TestTitle");
		// itemSeries.setItems(roomServices.findAllItems(user.getId().toString()
		// .replaceAll("-", "")));
		// itemSeriesList.add(itemSeries);
		// itemSeries = new ItemSeries();
		// itemSeries.setTitle("TestTitle22");
		// itemSeries.setItems(roomServices.findAllItems(user.getId().toString()
		// .replaceAll("-", "")));
		// itemSeriesList.add(itemSeries);
		return itemSeriesList;
	}

	@GET
	@Path("findManCategories")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Categories> findManCategories() {
		return roomServices.findManCategories();
	}

	@GET
	@Path("findWomenCategories")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Categories> findWomenCategories() {
		return roomServices.findWomenCategories();
	}

	// must use Produces
	@GET
	@Path("findSubCategoriesById/{cate_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Categories> findSubCategoriesById(
			@PathParam("cate_id") Integer cate_id) {
		System.out.println("id: " + cate_id);
		// List<Categories> cateList =
		// roomServices.findSubCategoriesById(cate_id);
		// System.out.println("list: "+cateList.size());
		// GenericEntity<List<Categories>> cates = new
		// GenericEntity<List<Categories>>(cateList) {};
		// return Response.ok(cates).build();
		return roomServices.findSubCategoriesById(cate_id);
	}

	@POST
	@Path("updateItem")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String updateItems(Items item) {
		boolean result = roomServices.updateItems(item);
		return String.valueOf(result);
	}

	@GET
	@Path("findAllGlobalItems")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Items> findAllGlobalItems() {
		// UUID uuid = UUID.fromString(user_id);
		List<Items> items = roomServices.findAllGlobalItems();
		for (Items item : items) {
			List<String> tagList = new ArrayList<>();
			for (ItemTags tag : item.getItemTags()) {
				tagList.add(tag.getTag());
			}
			item.setTags(tagList);

			if (item.getItemLikes().isEmpty()) {
				item.setLikesCount(0);
			} else {
				item.setLikesCount(item.getItemLikes().size());
			}

			List<String> seasonList = new ArrayList<>();
			for (ItemSeason season : item.getItemSeason()) {
				seasonList.add(season.getSeason());
			}
			item.setSeasons(seasonList);
		}
		return items;
	}

	@GET
	@Path("findAlmostOverdueItem/{date}/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Items> findAlmostOverdueItem(@PathParam("date") Integer date,
			@PathParam("userId") String user_id) {
		System.out.print("USERID:" + user_id);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		String now = df.format(c.getTime());
		c.add(Calendar.MONTH, date);
		String interval = df.format(c.getTime());
		return roomServices.findAlmostOverdueItem(interval, now, user_id);
	}

	@POST
	@Path("updateItemLike")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String updateItemLike(ItemLikes itemLike) {
		boolean result = roomServices.updateItemLike(itemLike);
		return String.valueOf(result);
	}

	@POST
	@Path("findAllLikedItems")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Items> findAllLikedItems(User user) {
		List<Items> items = roomServices.findAllLikedItems(user);
		for (Items item : items) {
			List<String> tagList = new ArrayList<>();
			for (ItemTags tag : item.getItemTags()) {
				tagList.add(tag.getTag());
			}
			item.setTags(tagList);

			if (item.getItemLikes().isEmpty()) {
				item.setLikesCount(0);
			} else {
				item.setLikesCount(item.getItemLikes().size());
			}

			List<String> seasonList = new ArrayList<>();
			for (ItemSeason season : item.getItemSeason()) {
				seasonList.add(season.getSeason());
			}
			item.setSeasons(seasonList);
		}
		return items;

	}
}
