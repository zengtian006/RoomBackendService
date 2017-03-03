package com.room.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Id;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.room.utils.UUIDAdapter;

public class ItemLikes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private Integer id;
	@XmlJavaTypeAdapter(UUIDAdapter.class)
	private UUID itemId;
	@XmlJavaTypeAdapter(UUIDAdapter.class)
	private UUID userId;
	
	public ItemLikes() {
	}
	public ItemLikes(Integer id, UUID itemId, UUID userId) {
		super();
		this.id = id;
		this.itemId = itemId;
		this.userId = userId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public UUID getItemId() {
		return itemId;
	}
	public void setItemId(UUID itemId) {
		this.itemId = itemId;
	}
	public UUID getUserId() {
		return userId;
	}
	public void setUserId(UUID userId) {
		this.userId = userId;
	}

}
