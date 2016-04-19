package org.allen.rbo.action.sec;

import org.allen.rbo.service.DataRobotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/sec/data")
public class DataDatabaseAction {
	@Autowired
	private DataRobotService dataRobotService;
	
	@RequestMapping("/robot")
	@ResponseBody
	public String loadRobotDatabase() {
		dataRobotService.buildRobotDatabase();
		return "ok";
	}
}
