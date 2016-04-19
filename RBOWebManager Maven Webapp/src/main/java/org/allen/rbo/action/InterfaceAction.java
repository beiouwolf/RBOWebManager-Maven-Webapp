package org.allen.rbo.action;

import org.allen.rbo.domain.PlayerChangeBean;
import org.allen.rbo.service.InterfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/interface")
public class InterfaceAction {
	@Autowired
	private InterfaceService interfaceService;
	
//	@RequestMapping(value = "/player/mo",method=RequestMethod.POST)
//	@ResponseBody
	public String playerChange(@RequestBody PlayerChangeBean bean) {
		return interfaceService.playerChange(bean);
	}
	
}
