package org.allen.rbo.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.allen.rbo.common.LogUtil;
import org.allen.rbo.common.SpringUtil;
import org.allen.rbo.common.SystemConfig;
import org.allen.rbo.domain.Counts;
import org.allen.rbo.domain.Ranking;
import org.allen.rbo.domain.RankingList;
import org.allen.rbo.service.ini.CardService;
import org.allen.rbo.service.ini.IniDocument;
import org.allen.rbo.service.ini.ItemService;
import org.allen.rbo.service.ini.PlayerService;
import org.allen.rbo.service.ini.RobotService;
import org.allen.rbo.wrap.StorageWrap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class SystemService {
	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private CardService cardService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private RobotService robotService;
	
	@Autowired
	protected SystemConfig systemConfig;
	
	@Autowired
	protected StorageWrap storageWrap;
	
	private static final long DAYTIME = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS);
	
	private File getSerializeFile(String name) {
		Resource resource = SpringUtil.getContext().getResource("classpath:/");
		File doc;
		try {
			doc = new File(resource.getFile(),"webCounts");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		if(!doc.exists())
			doc.mkdir();
		File file = new File(doc,name);
		
		return file;
	}
	public Counts loadCardCounts() {
		File file = getSerializeFile("card.dat");
		LogUtil.console.info("[SYSTEM] load card counts - {}",file.getAbsolutePath());
		
		if(isMoreDay(file))
			return createCardCounts();
		
		Counts counts = (Counts) Util.deSerialize(file);
		if(counts == null)
			return createCardCounts();
		else
			return counts;
	}
	
	public Counts loadItemCounts() {
		File file = getSerializeFile("item.dat");
		LogUtil.console.info("[SYSTEM] load item counts - {}",file.getAbsolutePath());
		
		if(isMoreDay(file))
			return createItemCounts();
		
		Counts counts = (Counts) Util.deSerialize(file);
		if(counts == null)
			return createItemCounts();
		else
			return counts;
	}
	
	public Counts loadRobotCounts() {
		File file = getSerializeFile("robot.dat");
		LogUtil.console.info("[SYSTEM] load robot counts - {}",file.getAbsolutePath());
		
		if(isMoreDay(file))
			return createRobotCounts();
		
		Counts counts = (Counts) Util.deSerialize(file);
		if(counts == null)
			return createRobotCounts();
		else
			return counts;
	}
	
	public void loadRanking() {
		File file = getSerializeFile("jifen.dat");
//		LogUtil.console.info("[SYSTEM] load ranking - {}",file.getAbsolutePath());
		
		if(isMoreDay(file)) {
			createRankingCounts();
			return;
		}
	}
	
	private boolean isMoreDay(File file) {
		return System.currentTimeMillis() - file.lastModified() > DAYTIME;
	}
	
	public Counts createCardCounts() {
		LogUtil.sys.info("[SYSTEM] create card counts");
		
		// 获取card大小
		int cardMax = cardService.getIniMax() + 1;
		
		// 获取文件列表
		List<IniDocument> playerList = playerService.listFiles();
		
		Counts counts = new Counts(cardMax);
		for(IniDocument player : playerList) {
			if(passAdminAndPassable(player))
				continue;
			
			String cardStr = player.getValue("Card");
			if(cardStr == null || cardStr.indexOf(",") < 0)
				continue;
			
			// 分割card字符串
			String[] cards = cardStr.split(",");
			for(String cardid : cards) {
				if(cardid == null || cardid.isEmpty())
					continue;
				try {
					IniDocument cardini = cardService.find(cardid);
					counts.add(cardid, cardini.getValue("Name"));
				} catch (FileNotFoundException e) {
					LogUtil.sys.error(e.getMessage(),e);
					continue;
				}
			}
		}
		
		counts.setCreated_timestamp(Util.getCreateTimestamp(new Date()));
		// 写入IO
		Util.serialize(counts, getSerializeFile("card.dat"));
		return counts;
	}
	
	public Counts createItemCounts() {
		LogUtil.sys.info("[SYSTEM] create item counts");
		
		// 获取card大小
		int max = itemService.getIniMax() + 1;
		
		// 获取文件列表
		List<IniDocument> playerList = playerService.listFiles();
		
		Counts counts = new Counts(max);
		for(IniDocument player : playerList) {
			if(passAdminAndPassable(player))
				continue;
			
			String tmp = player.getValue("ItemInBank");
			if(tmp == null)
				continue;
			StringBuilder cardStr = new StringBuilder(tmp);
			for(int i = 1; i <=5; ++i) {
				if(cardStr.length() > 0 && cardStr.charAt(cardStr.length() - 1) != ',')
					cardStr.append(",");
				cardStr.append(player.getValue("Robot" + i + "Eqip"));
			}
				
			if(cardStr == null || cardStr.indexOf(",") < 0)
				continue;
			
			// 分割card字符串
			String[] cards = cardStr.toString().split(",");
			for(String itemid : cards) {
				if(itemid == null || itemid.isEmpty())
					continue;
				try {
					IniDocument itemini = itemService.find(itemid);
					counts.add(itemid, itemini.getValue("Name"));
				} catch (FileNotFoundException e) {
					LogUtil.sys.error(e.getMessage(),e);
					continue;
				}
			}
		}
		
		counts.setCreated_timestamp(Util.getCreateTimestamp(new Date()));
		// 写入IO
		Util.serialize(counts, getSerializeFile("item.dat"));
		return counts;
	}
	
	public Counts createRobotCounts() {
		LogUtil.sys.info("[SYSTEM] create robot counts");
		
		// 获取card大小
		int max = robotService.getIniMax() + 1;
		
		// 获取文件列表
		List<IniDocument> playerList = playerService.listFiles();
		
		Counts counts = new Counts(max);
		
		for(IniDocument player : playerList) {
			if(passAdminAndPassable(player))
				continue;
			
			String r1 = player.getValue("Robot1");
			String r2 = player.getValue("Robot2");
			String r3 = player.getValue("Robot3");
			String r4 = player.getValue("Robot4");
			String r5 = player.getValue("Robot5");

			addRobotMapCount(counts, r1);
			addRobotMapCount(counts, r2);
			addRobotMapCount(counts, r3);
			addRobotMapCount(counts, r4);
			addRobotMapCount(counts, r5);
		}
		
		// 数据库
		List<String> dbList = storageWrap.selectRobot();
		for(String id : dbList)
			addRobotMapCount(counts, id);
		
		counts.setCreated_timestamp(Util.getCreateTimestamp(new Date()));
		// 写入IO
		Util.serialize(counts, getSerializeFile("robot.dat"));
		return counts;
	}
	private void addRobotMapCount(Counts counts,String id) {
		if(id == null || "0".equals(id))
			return;
		
		try {
			IniDocument itemini = robotService.find(id);
			counts.add(id, itemini.getValue("Name"));
		} catch (FileNotFoundException e) {
			LogUtil.sys.error(e.getMessage(),e);
		}
	}
	
	// 统计排行榜
	public RankingList loadRandingJifen() {
		return (RankingList) Util.deSerialize(getSerializeFile("jifen.dat"));
	}
	public RankingList loadRandingShengwang() {
		return (RankingList) Util.deSerialize(getSerializeFile("shengwang.dat"));
	}
	public RankingList loadRandingRongyu() {
		return (RankingList) Util.deSerialize(getSerializeFile("rongyu.dat"));
	}
	public RankingList loadRandingMoney() {
		return (RankingList) Util.deSerialize(getSerializeFile("money.dat"));
	}
	public RankingList loadRandingKill() {
		return (RankingList) Util.deSerialize(getSerializeFile("kill.dat"));
	}
	public void createRankingCounts() {
		LogUtil.sys.info("[SYSTEM] create ranking counts");
				
		// 获取文件列表
		List<IniDocument> playerList = playerService.listFiles();
		
		RankingList rankingList = new RankingList(playerList.size());
		
		for(IniDocument player : playerList) {
			if(passAdminAndPassable(player))
				continue;
			
			String playerName = player.getFile().getName();
			String jifen = player.getValue("VIP_JiFen");
			String shengwang = player.getValue("ShengWang");
			String rongyu = player.getValue("RongYu");
			String money = player.getValue("Money");
			String kill = player.getValue("EnemyKills");
			
			Ranking ranking = new Ranking();
			ranking.setPlayer(playerName);
			ranking.setJifen(toInt(jifen));
			ranking.setMoney(toInt(money));
			ranking.setRongyu(toInt(rongyu));
			ranking.setShengwang(toInt(shengwang));
			ranking.setKill(toInt(kill));
			rankingList.add(ranking);
		}
		
		RankingList jifenList = new RankingList(rankingList);
		RankingList shengwangList = new RankingList(rankingList);
		RankingList rongyuList = new RankingList(rankingList);
		RankingList moneyList = new RankingList(rankingList);
		RankingList killList = new RankingList(rankingList);
		Collections.sort(jifenList, new JifenCompare());
		Collections.sort(shengwangList, new ShengwangCompare());
		Collections.sort(rongyuList, new RongyuCompare());
		Collections.sort(moneyList, new MoneyCompare());
		Collections.sort(killList, new KillCompare());
		jifenList = limit(jifenList);
		shengwangList = limit(shengwangList);
		rongyuList = limit(rongyuList);
		moneyList = limit(moneyList);
		killList = limit(killList);
		// 写入IO
		Util.serialize(jifenList, getSerializeFile("jifen.dat"));
		Util.serialize(shengwangList, getSerializeFile("shengwang.dat"));
		Util.serialize(rongyuList, getSerializeFile("rongyu.dat"));
		Util.serialize(moneyList, getSerializeFile("money.dat"));
		Util.serialize(killList, getSerializeFile("kill.dat"));
	}
	private int toInt(String value) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return 0;
		}
	}
	private class JifenCompare implements Comparator<Ranking> {
		@Override
		public int compare(Ranking o1, Ranking o2) {
			if(o1.getJifen() == o2.getJifen())
				return o1.getPlayer().compareTo(o2.getPlayer());
			else
				return o1.getJifen() > o2.getJifen() ? -1 : 1;
		}
	}
	private class ShengwangCompare implements Comparator<Ranking> {
		@Override
		public int compare(Ranking o1, Ranking o2) {
			if(o1.getShengwang() == o2.getShengwang())
				return o1.getPlayer().compareTo(o2.getPlayer());
			else
				return o1.getShengwang() > o2.getShengwang() ? -1 : 1;
		}
	}
	private class RongyuCompare implements Comparator<Ranking> {
		@Override
		public int compare(Ranking o1, Ranking o2) {
			if(o1.getRongyu() == o2.getRongyu())
				return o1.getPlayer().compareTo(o2.getPlayer());
			else
				return o1.getRongyu() > o2.getRongyu() ? -1 : 1;
		}
	}
	private class MoneyCompare implements Comparator<Ranking> {
		@Override
		public int compare(Ranking o1, Ranking o2) {
			if(o1.getMoney() == o2.getMoney())
				return o1.getPlayer().compareTo(o2.getPlayer());
			else
				return o1.getMoney() > o2.getMoney() ? -1 : 1;
		}
	}
	private class KillCompare implements Comparator<Ranking> {
		@Override
		public int compare(Ranking o1, Ranking o2) {
			if(o1.getMoney() == o2.getMoney())
				return o1.getPlayer().compareTo(o2.getPlayer());
			else
				return o1.getKill() > o2.getKill() ? -1 : 1;
		}
	}
	private RankingList limit(RankingList list) {
		if(list.size() > 20)
			return new RankingList(list.subList(0, 20));
		else
			return list;
	}
	
	
	private boolean passAdminAndPassable(IniDocument player) {
		return player.isTrue("Admin") || player.isTrue("HACKER") || player.isTrue("堕落");
	}
}
