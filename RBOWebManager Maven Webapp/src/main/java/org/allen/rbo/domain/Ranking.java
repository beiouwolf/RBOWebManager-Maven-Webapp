package org.allen.rbo.domain;

import java.io.Serializable;

public class Ranking implements Serializable{
	private static final long serialVersionUID = 2419395208249360783L;
	
	private String player;
	private int jifen;
	private int shengwang;
	private int rongyu;
	private long money;
	private int kill;
	public String getPlayer() {
		return player;
	}
	public void setPlayer(String player) {
		this.player = player;
	}
	public int getJifen() {
		return jifen;
	}
	public void setJifen(int jifen) {
		this.jifen = jifen;
	}
	public int getShengwang() {
		return shengwang;
	}
	public void setShengwang(int shengwang) {
		this.shengwang = shengwang;
	}
	public int getRongyu() {
		return rongyu;
	}
	public void setRongyu(int rongyu) {
		this.rongyu = rongyu;
	}
	public long getMoney() {
		return money;
	}
	public void setMoney(long money) {
		this.money = money;
	}
	public int getKill() {
		return kill;
	}
	public void setKill(int kill) {
		this.kill = kill;
	}
}
