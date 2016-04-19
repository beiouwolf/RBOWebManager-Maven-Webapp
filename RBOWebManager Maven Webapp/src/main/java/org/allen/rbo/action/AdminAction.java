package org.allen.rbo.action;

import org.allen.rbo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/sec/admin")
public class AdminAction {
	@Autowired
	private AdminService adminService;
	
	@RequestMapping(value = "/",method = RequestMethod.GET)
	public String index() {
		return "redirect:/sec/admin/findItem";
	}
	
	@RequestMapping(value = "/index.html",method = RequestMethod.GET)
	public String index2() {
		return "redirect:/sec/admin/findItem";
	}
	
	@RequestMapping(value = "/findItem",method = RequestMethod.GET)
	public String findItemPage() {
		
		return "admin/findItemInPlayer";
	}
	
	@RequestMapping(value = "/findItem",method = RequestMethod.POST)
	public String findItem(String itemid,Model model) {
		adminService.findItemInPlayer(itemid,model);
		model.addAttribute("itemid", itemid);
		return "admin/findItemInPlayer";
	}
}
