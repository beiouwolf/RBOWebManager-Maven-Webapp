package org.allen.rbo.service;

import java.io.FileNotFoundException;

import org.allen.rbo.domain.PlayerChangeBean;
import org.allen.rbo.service.ini.IniDocument;
import org.allen.rbo.service.ini.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InterfaceService {
	@Autowired
	private PlayerService playerService;
	
	public String playerChange(PlayerChangeBean bean) {
		IniDocument player = null;
		// 当前用户
		try {
			player = playerService.find(bean.getPlayername());
		} catch (FileNotFoundException e) {
			return "no user";
		}
	
		for(String line : bean.getValues()) {
			String key = line.substring(0,line.indexOf("="));
			String value = line.substring(line.indexOf("=") + 1);
			
			player.replaceValue(key, value);
		}
		playerService.write(player);
		
		return "ok";
	}
}
