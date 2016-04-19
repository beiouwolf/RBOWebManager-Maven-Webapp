package org.allen.rbo.domain;

import java.util.ArrayList;
import java.util.Collection;

public class RankingList extends ArrayList<Ranking> implements TimestampMask{
	private static final long serialVersionUID = -1287268944473527579L;

	public RankingList(int size) {
		super(size);
	}
	public RankingList(Collection<Ranking> list) {
		super(list);
	}
	
	private String created_timestamp;

	public String getCreated_timestamp() {
		return created_timestamp;
	}

	public void setCreated_timestamp(String created_timestamp) {
		this.created_timestamp = created_timestamp;
	} 
}
