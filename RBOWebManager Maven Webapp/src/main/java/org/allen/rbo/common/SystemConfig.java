package org.allen.rbo.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

@Repository
@Lazy(false)
public class SystemConfig {
	@Value("${rbo.path}")
	private  String rboPath;

	public String getRboPath() {
		return rboPath;
	}

	public void setRboPath(String rboPath) {
		this.rboPath = rboPath;
	}

}
