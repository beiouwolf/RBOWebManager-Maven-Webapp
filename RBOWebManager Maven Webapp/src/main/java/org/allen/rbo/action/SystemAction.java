package org.allen.rbo.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.allen.rbo.common.SpringUtil;
import org.allen.rbo.domain.RankingList;
import org.allen.rbo.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/system")
public class SystemAction {
	@Autowired
	private SystemService systemService;
	
	@RequestMapping("/counts/card")
	public String cardCounts(Model model) {
		model.addAttribute("counts", systemService.loadCardCounts());
		return "system/counts/card";
	}
	
	@RequestMapping("/counts/item")
	public String  itemCounts(Model model) {
		model.addAttribute("counts", systemService.loadItemCounts());
		return "system/counts/item";
	}
	
	@RequestMapping("/counts/robot")
	public String  robotCounts(Model model) {
		model.addAttribute("counts", systemService.loadRobotCounts());
		return "system/counts/robot";
	}
	
	@RequestMapping("/ranking/jifen")
	public String jifenRanking(Model model) {
		systemService.loadRanking();
		RankingList list = systemService.loadRandingJifen();
		model.addAttribute("ranking", list);
		model.addAttribute("created_timestamp", list.getCreated_timestamp());
		return "system/ranking/jifen";
	}
	@RequestMapping("/ranking/shengwang")
	public String shengwangRanking(Model model) {
		systemService.loadRanking();
		RankingList list = systemService.loadRandingShengwang();
		model.addAttribute("ranking", list);
		model.addAttribute("created_timestamp", list.getCreated_timestamp());
		return "system/ranking/shengwang";
	}
	@RequestMapping("/ranking/rongyu")
	public String rongyuRanking(Model model) {
		systemService.loadRanking();
		RankingList list = systemService.loadRandingRongyu();
		model.addAttribute("ranking", list);
		model.addAttribute("created_timestamp", list.getCreated_timestamp());
		return "system/ranking/rongyu";
	}
	@RequestMapping("/ranking/money")
	public String moneyRanking(Model model) {
		systemService.loadRanking();
		RankingList list = systemService.loadRandingMoney();
		model.addAttribute("ranking", list);
		model.addAttribute("created_timestamp", list.getCreated_timestamp());
		return "system/ranking/money";
	}
	@RequestMapping("/ranking/kill")
	public String killRanking(Model model) {
		systemService.loadRanking();
		RankingList list = systemService.loadRandingKill();
		model.addAttribute("ranking", list);
		model.addAttribute("created_timestamp", list.getCreated_timestamp());
		return "system/ranking/kill";
	}
	
	private static final String BIMG_PATH = "classpath:/bimg/";
	@RequestMapping("/bimg/{name}")
	public void bimg(@PathVariable String name,HttpServletResponse response) {
		Resource img = SpringUtil.getContext().getResource(BIMG_PATH);
		
		File doc;
		try {
			doc = img.getFile();
		} catch (IOException e1) {
			throw new RuntimeException(e1);
		}
		File file = new File(doc,name + ".gif");
		if(!file.exists())
			file = new File(doc,name + ".rpc");
		
		if(!file.exists())
			return;
		
		FileInputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(file);
			os = response.getOutputStream();
			byte[] data = new byte[2048];
			int size = 0;
			while((size = is.read(data)) != -1) {
				os.write(data,0,size);
			}
			os.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if(os != null)
				try {
					os.close();
				} catch (IOException e) {
				}
			if(is != null)
				try {
					is.close();
				} catch (IOException e) {
				}
		}
	}
}
