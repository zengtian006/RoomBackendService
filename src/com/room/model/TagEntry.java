package com.room.model;

public class TagEntry {
	private String key;
    private Long value;

    public TagEntry(String key, Long value) {
		super();
		this.key = key;
		this.value = value;
	}

	public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
