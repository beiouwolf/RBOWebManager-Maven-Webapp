package org.allen.rbo.common;

import java.nio.charset.Charset;


public class Constants {
	public static final Charset CHARSET = Charset.forName("GBK");
	
	public enum Directory {
		items("Items"),
		card("card"),
		robot("Robot"),
		player("save"),
		map("map");
		public String val;
		private Directory(String val) {
			this.val = val;
		}
	}
}
