package com.dispatcher;

public class Item {

	private String name;
	private String items;
	private String domain;
	private int x;
	private int y;

	Item() {
	}
	
	Item(String name, String items, String domain) {
		super();
		this.name = name;
		this.items = items;
		this.domain = domain;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getX() {
		return x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getY() {
		return y;
	}

}
