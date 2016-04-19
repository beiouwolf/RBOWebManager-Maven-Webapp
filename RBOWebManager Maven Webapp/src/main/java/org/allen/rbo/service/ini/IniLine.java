package org.allen.rbo.service.ini;

public class IniLine {
	private boolean bRem;
	private String key;
	private String value;
	private String cache;
	public boolean isbRem() {
		return bRem;
	}
	public void setbRem(boolean bRem) {
		this.bRem = bRem;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
		setCache(key + "=" + value);
	}
	public String getCache() {
		return cache;
	}
	public void setCache(String cache) {
		this.cache = cache;
	}
}
