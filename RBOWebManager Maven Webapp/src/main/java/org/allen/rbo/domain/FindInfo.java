package org.allen.rbo.domain;

public class FindInfo {
	private String player;
	private boolean admin;
	private String name;
	private int index;
	
	public FindInfo() {
	
	}
	
	public FindInfo(String player, boolean admin, String name, int index) {
		this.player = player;
		this.admin = admin;
		this.name = name;
		this.index = index;
	}

	public String getPlayer() {
		return player;
	}
	public void setPlayer(String player) {
		this.player = player;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
}
