package org.allen.rbo.domain;

import java.util.List;

public class DataRobot {
	private int id;
	private String name;
	private String hp;
	private String en;
	private String ability1;
	private String ability2;
	private String ability3;
	private String ability4;
	private String move;
	private String speed;
	private String evade;
	private String glight;
	private String gball;
	private String gwrestle;
	private String gmagic;
	private String ghyper;
	private String equip;
	private String image;
	private String hprestore;
	private String enrestore;
	private String need;
	private String sheildname;
	
	private List<DataRobotWeapon> weapons;
	
	private String where;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getHp() {
		return hp;
	}
	public void setHp(String hp) {
		this.hp = hp;
	}
	public String getEn() {
		return en;
	}
	public void setEn(String en) {
		this.en = en;
	}
	public String getAbility1() {
		return ability1;
	}
	public void setAbility1(String ability1) {
		this.ability1 = ability1;
	}
	public String getAbility2() {
		return ability2;
	}
	public void setAbility2(String ability2) {
		this.ability2 = ability2;
	}
	public String getAbility3() {
		return ability3;
	}
	public void setAbility3(String ability3) {
		this.ability3 = ability3;
	}
	public String getAbility4() {
		return ability4;
	}
	public void setAbility4(String ability4) {
		this.ability4 = ability4;
	}
	public String getMove() {
		return move;
	}
	public void setMove(String move) {
		this.move = move;
	}
	public String getSpeed() {
		return speed;
	}
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	public String getEvade() {
		return evade;
	}
	public void setEvade(String evade) {
		this.evade = evade;
	}
	public String getGlight() {
		return glight;
	}
	public void setGlight(String glight) {
		this.glight = glight;
	}
	public String getGball() {
		return gball;
	}
	public void setGball(String gball) {
		this.gball = gball;
	}
	public String getGwrestle() {
		return gwrestle;
	}
	public void setGwrestle(String gwrestle) {
		this.gwrestle = gwrestle;
	}
	public String getGmagic() {
		return gmagic;
	}
	public void setGmagic(String gmagic) {
		this.gmagic = gmagic;
	}
	public String getGhyper() {
		return ghyper;
	}
	public void setGhyper(String ghyper) {
		this.ghyper = ghyper;
	}
	public String getEquip() {
		return equip;
	}
	public void setEquip(String equip) {
		this.equip = equip;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getHprestore() {
		return hprestore;
	}
	public void setHprestore(String hprestore) {
		this.hprestore = hprestore;
	}
	public String getEnrestore() {
		return enrestore;
	}
	public void setEnrestore(String enrestore) {
		this.enrestore = enrestore;
	}
	public String getNeed() {
		return need;
	}
	public void setNeed(String need) {
		this.need = need;
	}
	public String getSheildname() {
		return sheildname;
	}
	public void setSheildname(String sheildname) {
		this.sheildname = sheildname;
	}
	public List<DataRobotWeapon> getWeapons() {
		return weapons;
	}
	public void setWeapons(List<DataRobotWeapon> weapons) {
		this.weapons = weapons;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWhere() {
		return where;
	}
	public void setWhere(String where) {
		this.where = where;
	}
}
