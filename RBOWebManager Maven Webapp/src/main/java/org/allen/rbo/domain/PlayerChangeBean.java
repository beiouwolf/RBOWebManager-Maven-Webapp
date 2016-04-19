package org.allen.rbo.domain;

import java.util.List;

public class PlayerChangeBean {
	private String playername;
	private String password;
	private List<String> values;
	
	public String getPlayername() {
		return playername;
	}
	public void setPlayername(String playername) {
		this.playername = playername;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<String> getValues() {
		return values;
	}
	public void setValues(List<String> values) {
		this.values = values;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PlayerChangeBean [playername=").append(playername)
				.append(", password=").append(password).append(", values=")
				.append(values).append("]");
		return builder.toString();
	}
}
