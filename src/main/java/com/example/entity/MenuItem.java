package com.example.entity;

public class MenuItem {
	private String itemId;      //{ps} �Ӳ˵� ID
	private String itemName;    //{ps} �Ӳ˵�����
	private String urlLink;     //{ps} ӳ���ַ
	private String visible;     //{ps} �ɼ���
	
	public MenuItem( String itemId, String itemName, 
			String urlLink, String visible) {
		this.itemId = itemId;
		this.itemName = itemName;
		this.urlLink = urlLink;
		this.visible = visible;
	}
		
	public MenuItem( RoleMenu roleMenu ) {
		this.itemId = roleMenu.getItemId();
		this.itemName = roleMenu.getItemName();
		this.urlLink = roleMenu.getUrlLink();
		this.visible = roleMenu.getVisible();
	}

	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getUrlLink() {
		return urlLink;
	}
	public void setUrlLink(String urlLink) {
		this.urlLink = urlLink;
	}
	public String getVisible() {
		return visible;
	}
	public void setVisible(String visible) {
		this.visible = visible;
	}
	
	@Override
	public String toString() {
		return "MenuItem [itemId=" + itemId + ", itemName=" + itemName + ", urlLink=" + urlLink + ", visible=" + visible
				+ "]";
	}
}
