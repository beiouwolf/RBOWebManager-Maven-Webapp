package org.allen.rbo.service.ini;

import org.allen.rbo.common.Constants.Directory;
import org.springframework.stereotype.Service;

@Service
public class CardService extends AbstractCacheIniService{

	@Override
	public Directory determinDirectory() {
		return Directory.card;
	}

	@Override
	protected String name(String name) {
		return name + ".ini";
	}

	@Override
	protected int docFileSize() {
		
		return getDirectory().listFiles().length;
	}

}
