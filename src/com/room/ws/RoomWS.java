package com.room.ws;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
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

import com.room.model.ItemSeries;
import com.room.model.Items;
import com.room.model.Categories;
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
		System.out.println(item.getUserId().toString());
		return String.valueOf(roomServices.addItems(item));
	}

	@GET
	@Path("/findAllItemsTest/{userid}")
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Items> findAllItemsTest(@PathParam("userid") String user_id) {
		// UUID uuid = UUID.fromString(user_id);
		String hex_user_id = user_id.replaceAll("-", "");
		System.out.println("haha:" + hex_user_id);
		return roomServices.findAllItems(hex_user_id);
	}

	@POST
	@Path("findAllItems")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<ItemSeries> findAllItems(User user) {

		String user_id = user.getId().toString().replaceAll("-", "");
		List<Items> items = roomServices.findAllItems(user_id);
		List<Categories> categories = new ArrayList<Categories>();
		if (user.getGender().equals("M")) {
			categories = roomServices.findManCategories();
		} else {
			categories = roomServices.findWomenCategories();
		}
		List<ItemSeries> itemSeriesList = new ArrayList<ItemSeries>();
		for (Categories cate : categories) {
			if (cate.getParentId() == 0) {
				ItemSeries itemSeries = new ItemSeries();
				itemSeries.setCate_id(cate.getId());
				itemSeries.setTitle(cate.getCateName());
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
				itemSeries.setItems(filetedAllItems);
				System.out.println("title:" + itemSeries.getTitle());
				System.out.println("size:" + itemSeries.getItems().size());
				itemSeriesList.add(itemSeries);
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
	public Response findManCategories() {
		List<Categories> cateList = roomServices.findManCategories();
		GenericEntity<List<Categories>> cates = new GenericEntity<List<Categories>>(
				cateList) {
		};
		return Response.ok(cates).build();
	}

	@GET
	@Path("findWomenCategories")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findWomenCategories() {
		List<Categories> cateList = roomServices.findWomenCategories();
		return Response.ok(cateList).build();
	}

	//must use Produces
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
}
