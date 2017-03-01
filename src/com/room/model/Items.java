package com.room.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.eclipse.persistence.jpa.jpql.parser.DateTime;
import org.hibernate.annotations.GenericGenerator;

import com.room.utils.UUIDAdapter;

// Generated 2017-2-10 15:27:36 by Hibernate Tools 4.3.1

/**
 * Items generated by hbm2java
 */
public class Items implements java.io.Serializable, Comparable<Items> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(columnDefinition = "BINARY(16)")
	@XmlJavaTypeAdapter(UUIDAdapter.class)
	private UUID id;

	// @XmlJavaTypeAdapter(UUIDAdapter.class)
	// private UUID userId;
	private String brand;
	private String title;
	private String imageName;
	private String remark;
	private String status;
	private Integer cateId;
	private User user;
	private String date;
	private String global;
	private String created;
	private String expDate;
	private List<String> tags;

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getGlobal() {
		return global;
	}

	public void setGlobal(String global) {
		this.global = global;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getCateId() {
		return cateId;
	}

	public void setCateId(Integer cateId) {
		this.cateId = cateId;
	}

	public Items() {
	}

	public UUID getId() {
		return this.id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	// public UUID getUserId() {
	// return userId;
	// }
	//
	// public void setUserId(UUID userId) {
	// this.userId = userId;
	// }

	public String getBrand() {
		return this.brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImageName() {
		return this.imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public int compareTo(Items compareItem) {
		String comparecreate = ((Items) compareItem).getCreated();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date1 = null, date2 = null;
		try {
			date1 = format.parse(comparecreate);
			date2 = format.parse(this.created);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/* For Descending order do like this */
		return (int) date1.getTime() - (int) date2.getTime();
	}

	public String getExpDate() {
		return expDate;
	}

	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}
}
