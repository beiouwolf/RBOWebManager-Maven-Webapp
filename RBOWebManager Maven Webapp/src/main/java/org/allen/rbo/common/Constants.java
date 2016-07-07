package org.allen.rbo.common;

import java.nio.charset.Charset;


public class Constants {
	public static final Charset CHARSET = Charset.forName("GBK");
	
	public enum Directory {
		items("Items"),
		npcs("Npcs"),
		card("card"),
		robot("Robot"),
		player("save"),
		maps("Maps");
		
		public String val;
		private Directory(String val) {
			this.val = val;
		}
	}
}
