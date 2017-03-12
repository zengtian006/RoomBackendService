package com.room.model;

import java.util.List;
import java.util.Map;

public class ItemSeries {
	private String title;
	private String title_cn;
	private Integer cate_id;
	private List<Items> items;
	private List<TagEntry> allTagsMap;

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

	public List<TagEntry> getAllTagsMap() {
		return allTagsMap;
	}

	public void setAllTagsMap(List<TagEntry> allTagsMap) {
		this.allTagsMap = allTagsMap;
	}

	public String getTitle_cn() {
		return title_cn;
	}

	public void setTitle_cn(String title_cn) {
		this.title_cn = title_cn;
	}
}
