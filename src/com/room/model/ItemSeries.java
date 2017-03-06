package com.room.model;

import java.util.List;
import java.util.Map;

public class ItemSeries {
	private String title;
	private Integer cate_id;
	private List<Items> items;
	private Map<String, Long> allTagsMap;

	public Integer getCate_id() {
		return cate_id;
	}

	public void setCate_id(Integer cate_id) {
		this.cate_id = cate_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Items> getItems() {
		return items;
	}

	public void setItems(List<Items> items) {
		this.items = items;
	}

	public Map<String, Long> getAllTagsMap() {
		return allTagsMap;
	}

	public void setAllTagsMap(Map<String, Long> allTagsMap) {
		this.allTagsMap = allTagsMap;
	}
}
