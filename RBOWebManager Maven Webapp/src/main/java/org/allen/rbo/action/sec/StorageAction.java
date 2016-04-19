package org.allen.rbo.action.sec;

import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.allen.rbo.service.ini.IniDocument;
import org.allen.rbo.service.ini.PlayerService;
import org.allen.rbo.service.ini.RobotService;
import org.allen.rbo.wrap.StorageWrap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sec/storage")
public class StorageAction {
	
	@Autowired
	private PlayerService playerService;
	@Autowired
	private RobotService robotService;
	
	@Autowired
	private StorageWrap storageWrap;
	
	/**
	 * 机体仓库页面
	 * @param model
	 * @return
	 * @throws FileNotFoundException
	 */
	@RequestMapping("/storage")
	public String storageRobot(Model model) throws FileNotFoundException {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		
		// 当前用户
		IniDocument player = playerService.find(username);
		
		// 检查在线
		if(checkOnline(player, model))
			return "/user/storage";
		
		// 获取数据
		IniDocument[] robots = new IniDocument[5];
		robots[0] = robotService.find(player.getValue("Robot1"));
		robots[1] = robotService.find(player.getValue("Robot2"));
		robots[2] = robotService.find(player.getValue("Robot3"));
		robots[3] = robotService.find(player.getValue("Robot4"));
		robots[4] = robotService.find(player.getValue("Robot5"));
		
		String[] hpens = new String[10];
		String tmp = player.getValue("RobotHPEN");
		hpens = tmp.split(",");
				
		int[] eqsSize = new int[5];
		eqsSize[0] = player.getValue("Robot1Eqip").split(",").length;
		eqsSize[1] = player.getValue("Robot2Eqip").split(",").length;
		eqsSize[2] = player.getValue("Robot3Eqip").split(",").length;
		eqsSize[3] = player.getValue("Robot4Eqip").split(",").length;
		eqsSize[4] = player.getValue("Robot5Eqip").split(",").length;
		
		// 当前机体位置
		String NowRobot = player.getValue("NowRobot");
				
		model.addAttribute("robots", robots);
		model.addAttribute("hpens", hpens);
		model.addAttribute("eqsSize", eqsSize);
		model.addAttribute("NowRobot", Integer.parseInt(NowRobot));
		
		// 获取仓库数据
		List<Map<String,Object>> storageRobots = storageWrap.select(username);
		
		Map<String,IniDocument> st_robots = new LinkedHashMap<String,IniDocument>(storageRobots.size());
		for(Map<String,Object> robot : storageRobots)
			st_robots.put(((Integer)robot.get("id")).toString(),robotService.find((String)robot.get("robot")));
		model.addAttribute("st_robots", st_robots);
		
		return "/user/storage";
	}
	
	@RequestMapping("/store/{index}")
	public String store(@PathVariable int index,Model model) throws FileNotFoundException {
		// 操作索引
		index += 1;
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		// 当前用户
		IniDocument player = playerService.find(username);
		
		// 不能替换当前机体
		String NowRobot = player.getValue("NowRobot");
		if(NowRobot.equals(index))
			return "redirect:../storage?_tmp=" + System.currentTimeMillis();
		
		// 检查在线状态
		if(checkOnline(player, model))
			return "redirect:../storage?_tmp=" + System.currentTimeMillis();
		
		// 替换机体编号
		String robot = player.replaceValue("Robot" + index,"0");
		if("0".equals(robot))
			return "redirect:../storage?_tmp=" + System.currentTimeMillis();
		
		// 替换HPEN
		String tmp = player.getValue("RobotHPEN");
		String[] hpens = tmp.split(",");
		String HPEN = hpens[(index - 1) * 2] + "," + hpens[((index - 1) * 2) + 1];
		hpens[(index - 1) * 2] = "0";
		hpens[((index - 1) * 2) + 1] = "0";
		player.setValue("RobotHPEN", deepHPEN(hpens));
		// 替换弹药
		tmp = player.getValue("RobotAmmo");
		String[] ammo = tmp.split(",");
		ammo[index - 1] = "0";
		player.setValue("RobotAmmo", deepHPEN(ammo));
		// 替换武器加成
		String Weaponpower = player.replaceValue("Robot" + index + "Weaponpower","");
		// 替换机体加成
		String HpEnGuard = player.replaceValue("Robot" + index + "HpEnGuard+","0,0,0,0,0,0");
		// 替换装备
		String Eqip = player.replaceValue("Robot" + index + "Eqip",",");
		
		// 设置当前HPEN
		player.setValue("NowHP" + index, "1");
		player.setValue("NowEN" + index, "1");
		
		// 写入数据
		storageWrap.insert(username, robot, HPEN, Weaponpower, HpEnGuard, Eqip);
		playerService.write(player);
		
		return "redirect:../storage?_tmp=" + System.currentTimeMillis();
	}
	
	@RequestMapping("/recall/{id}/{freePos}")
	public String recall(@PathVariable int id,@PathVariable int freePos,Model model) throws FileNotFoundException {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		// 仓库数据
		Map<String,Object> robotData = storageWrap.selectOne(id,username);
		if(robotData == null)
			return "redirect:../../storage?_tmp=" + System.currentTimeMillis();
		
		// 当前用户
		IniDocument player = playerService.find(username);
		
		// 检查在线状态
		if(checkOnline(player, model))
			return "redirect:../../storage?_tmp=" + System.currentTimeMillis();
		
		// 机体编号
		player.setValue("Robot" + freePos,(String)robotData.get("robot"));
		
		// 替换HPEN
		String tmp = player.getValue("RobotHPEN");
		String[] hpens = tmp.split(",");
		String dbtmp = (String) robotData.get("HPEN");
		String[] dbHpens = dbtmp.split(",");
		hpens[(freePos - 1) * 2] = dbHpens[0];
		hpens[((freePos - 1) * 2) + 1] = dbHpens[1];
		player.setValue("RobotHPEN", deepHPEN(hpens));
		// 替换弹药
		tmp = player.getValue("RobotAmmo");
		String[] ammo = tmp.split(",");
		ammo[freePos - 1] = "0";
		player.setValue("RobotAmmo", deepHPEN(ammo));
		// 替换武器加成
		player.setValue("Robot" + freePos + "Weaponpower",(String) robotData.get("Weaponpower"));
		// 替换机体加成
		player.setValue("Robot" + freePos + "HpEnGuard+",(String) robotData.get("HpEnGuard"));
		// 替换装备
		player.setValue("Robot" + freePos + "Eqip",(String) robotData.get("Eqip"));
		
		// 设置当前HPEN
		player.setValue("NowHP" + freePos, "1");
		player.setValue("NowEN" + freePos, "1");
		
		// 写入数据
		storageWrap.delete(id,username);
		playerService.write(player);
				
		return "redirect:../../storage?_tmp=" + System.currentTimeMillis();
	}
	
	private String deepHPEN(String[] hpens) {
		StringBuilder sb = new StringBuilder();
		for(String s : hpens)
			sb.append(s).append(",");
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}
	
	private boolean checkOnline(IniDocument player,Model model) {
		String LoginIndex = player.getValue("LoginIndex");
		if("0".equals(LoginIndex)) {
			return false;
		} else {
			model.addAttribute("error", "请先下线,在线用户无法使用页面服务!");
			return false;
		}
	}
}
