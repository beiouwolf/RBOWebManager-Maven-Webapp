package org.allen.rbo.action;

import org.allen.rbo.domain.DataRobot;
import org.allen.rbo.service.DataRobotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/data")
public class DatalistAction {
	@Autowired
	private DataRobotService service;
	
	@RequestMapping(value = "/robot",method = RequestMethod.GET)
	public String robotList(Model model) {
//		model.addAttribute("list", service.select(null));
		return "/data/robot";
	}
	
	@RequestMapping(value = "/robot",method = RequestMethod.POST)
	public String robotListPost(DataRobot condition,Model model) {
		model.addAttribute("list", service.select(condition));
		return "/data/robot";
	}
}
