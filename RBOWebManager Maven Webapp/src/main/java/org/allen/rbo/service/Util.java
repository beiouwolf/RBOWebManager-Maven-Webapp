package org.allen.rbo.service;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.allen.rbo.common.LogUtil;
import org.allen.rbo.domain.Counts;
import org.allen.rbo.domain.TimestampMask;

public class Util {
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static void serialize(Object counts,File file) {
		FileOutputStream os = null;
		ObjectOutputStream oos = null;
		
		try {
			os = new FileOutputStream(file);
			oos = new ObjectOutputStream(os);
			
			oos.writeObject(counts);
		} catch (Exception e) {
			LogUtil.sys.error(e.getMessage(),e);
		} finally {
			if(os != null)
				try {
					os.close();
				} catch (IOException e) {
				}
		}
	}
	
	public static TimestampMask deSerialize(File file) {
		FileInputStream is = null;
		ObjectInputStream ois = null;
		
		try {
			is = new FileInputStream(file);
			ois = new ObjectInputStream(is);
			
			TimestampMask count = (TimestampMask) ois.readObject();
			count.setCreated_timestamp(getCreateTimestamp(new Date(file.lastModified())));
			return count;
		} catch (Exception e) {
			LogUtil.sys.error(e.getMessage(),e);
			return null;
		} finally {
			if(is != null)
				try {
					is.close();
				} catch (IOException e) {
				}
		}
	}
	
	public static String getCreateTimestamp(Date date) {
		return dateFormat.format(date);
	}
}
