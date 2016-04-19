package org.allen.rbo.service.ini;

import java.io.File;
import java.io.FilenameFilter;

import org.allen.rbo.common.Constants.Directory;
import org.springframework.stereotype.Service;

@Service
public class PlayerService extends AbstractIniService{

	@Override
	public Directory determinDirectory() {
		return Directory.player;
	}

	@Override
	protected String name(String name) {
		return name;
	}
	
	@Override
	protected FilenameFilter getFilenameFilter() {
		return new FilenameFilter() {
			
			public boolean accept(File dir, String name) {
				return true;
			}
		};
	}
}
