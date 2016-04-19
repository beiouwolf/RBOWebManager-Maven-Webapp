package org.allen.rbo.domain;

import java.io.Serializable;

import org.allen.rbo.common.LogUtil;

/**
 * 用于统计的bean
 * 名称
 * @author Yao
 *
 */
public class Counts implements Serializable,TimestampMask {
	private static final long serialVersionUID = -6502683686766075044L;

	private String created_timestamp;
	
	public class Item implements Serializable{
		private static final long serialVersionUID = 8266337966967423003L;
		
		private String id;
		private String name;
		private int counts;
		
		public Item(String id,String name) {
			this.id = id;
			this.name = name;
			this.counts = 0;
		}
		
		public void addCount() {
			this.counts += 1;
		}

		public String getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public int getCounts() {
			return counts;
		}
	}

	private Item[] list;
	public Counts(int size) {
		this.list = new Item[size];
	}
	
	public void add(String id,String name) {
		int index;
		try {
			index = Integer.parseInt(id);
		} catch (NumberFormatException e) {
			LogUtil.sys.error(e.getMessage(),e);
			return;
		}
		Item item= list[index];
		if(item == null) {
			item = new Item(id, name);
			list[index] = item;
		}
		
		item.addCount();
	}
	
	public Item get(int id) {
		return list[id];
	}

	public Item[] getList() {
		return list;
	}

	public String getCreated_timestamp() {
		return created_timestamp;
	}

	public void setCreated_timestamp(String created_timestamp) {
		this.created_timestamp = created_timestamp;
	}
}
