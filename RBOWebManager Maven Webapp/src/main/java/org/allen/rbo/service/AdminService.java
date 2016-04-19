package org.allen.rbo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.allen.rbo.common.LogUtil;
import org.allen.rbo.domain.FindInfo;
import org.allen.rbo.service.ini.IniDocument;
import org.allen.rbo.service.ini.ItemService;
import org.allen.rbo.service.ini.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class AdminService {
	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private ItemService itemService;
	
	public void findItemInPlayer(String itemid,Model model) {
		LogUtil.sys.info("[SYSTEM] find item in player");
		
		// 获取文件列表
		List<IniDocument> playerList = playerService.listFiles();
		
		Map<String,List<FindInfo>> adminMap = new HashMap<String,List<FindInfo>>();
		Map<String,List<FindInfo>> normalMap = new HashMap<String,List<FindInfo>>();
		
		for(IniDocument player : playerList) {
			String name = player.getFile().getName();
			boolean adminFlag = player.isTrue("Admin");
			
			List<FindInfo> list;
			Map<String,List<FindInfo>> tmpMap = adminFlag ? adminMap : normalMap;

			list = tmpMap.get(name);
			if(list == null)
				list = new ArrayList<FindInfo>();
			
			findInLine(player, list, "ItemInBank", itemid);
			findInLine(player, list, "Robot1Eqip", itemid);
			findInLine(player, list, "Robot2Eqip", itemid);
			findInLine(player, list, "Robot3Eqip", itemid);
			findInLine(player, list, "Robot4Eqip", itemid);
			findInLine(player, list, "Robot5Eqip", itemid);
			
			if(list.size() > 0)
				tmpMap.put(name, list);
		}

		model.addAttribute("adminMap", adminMap);
		model.addAttribute("normalMap", normalMap);
	}
	
	private void findInLine(IniDocument player,List<FindInfo> list,String name,String id) {
		boolean adminFlag = player.isTrue("Admin");
		
		String data = player.getValue(name);
		if(data != null && !data.isEmpty()) {
			String[] items = data.split(",");
			int len = items.length;
			for(int i = 0; i < len; ++i) {
				if(items[i].equals(id))
					list.add(new FindInfo(player.getFile().getName(), adminFlag, name, i+1));
			}
		}
	}
}
