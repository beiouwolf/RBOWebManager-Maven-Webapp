package org.allen.rbo.service.ini;

import java.io.File;
import java.io.FilenameFilter;

import org.allen.rbo.common.Constants.Directory;
import org.allen.rbo.wrap.StorageWrap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RobotService extends AbstractCacheIniService{

	@Autowired
	private StorageWrap storageWrap;
	
	@Override
	public Directory determinDirectory() {
		return Directory.robot;
	}

	@Override
	protected String name(String name) {
		return name + ".ini";
	}

	@Override
	protected int docFileSize() {
		return getDirectory().listFiles().length;
	}

	@Override
	protected FilenameFilter getFilenameFilter() {
		return new FilenameFilter() {
			public boolean accept(File dir, String name) {
				try {
					String tmp = name.substring(0,name.indexOf(".ini"));
					int id = Integer.parseInt(tmp);
					return id>0;
				} catch (NumberFormatException e) {
					return false;
				}
			}
		};
	}
}
