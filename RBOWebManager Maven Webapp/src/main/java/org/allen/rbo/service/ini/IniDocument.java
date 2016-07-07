package org.allen.rbo.service.ini;

import java.io.File;
import java.util.LinkedHashMap;

public class IniDocument extends LinkedHashMap<String,IniLine> {
	
	private static final long serialVersionUID = 1L;

	private File file;
	
	private int KEYMASK = 0;
	
	public void add(String input) {
		IniLine line = new IniLine();
		line.setCache(input);
		
		if(input.indexOf('=') > 0) {
			line.setbRem(false);
			line.setKey(input.substring(0,input.indexOf('=')));
			line.setValue(input.substring(input.indexOf('=') + 1));
		} else {
			line.setbRem(true);
			line.setKey("_KEYMASK_" + KEYMASK++);
		}
		
		put(line.getKey(), line);
	}
	
	public String getValue(String key) {
		IniLine line = get(key);
		if(line == null)
			return null;
		return line.getValue();
	}
	
	public void setValue(String key,String value) {
		IniLine line = get(key);
		line.setValue(value);
	}
	
	public String replaceValue(String key,String newValue) {
		IniLine line = get(key);
		if(line == null)
			return null;
		String oldValue = line.getValue();
		line.setValue(newValue);
		return oldValue;
	}
	
	public boolean isTrue(String key) {
		return "True".equals(getValue(key));
	}
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}


//	public void put(String key,String value) {
//		int index = keyMap.get(key);
//		IniLine line = get(index);
//		if(line == null) {
//			line = new IniLine();
//			line.setbRem(false);
//			line.setKey(key);
//			line.setValue(value);
//			line.setCache(key + '=' + value);
//			add(line);
//			keyMap.put(key, size());
//		} else {
//			line.setValue(value);
//			line.setCache(key + '=' + value);
//		}
//	}

}
