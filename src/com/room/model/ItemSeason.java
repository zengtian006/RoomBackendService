package com.room.model;

import java.util.UUID;

import javax.persistence.Id;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.room.utils.UUIDAdapter;

// Generated 2017-3-1 17:10:45 by Hibernate Tools 3.4.0.CR1

/**
 * ItemSeasons generated by hbm2java
 */
public class ItemSeason implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private Integer id;
	@XmlJavaTypeAdapter(UUIDAdapter.class)
	private UUID itemId;
	private String season;

	public ItemSeason() {
	}

	public ItemSeason(UUID itemId, String season) {
		this.itemId = itemId;
		this.setSeason(season);
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UUID getItemId() {
		return this.itemId;
	}

	public void setItemId(UUID itemId) {
		this.itemId = itemId;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}
}
