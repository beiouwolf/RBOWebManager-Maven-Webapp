package org.allen.rbo.action;

import java.io.FileNotFoundException;

import org.allen.rbo.service.ini.IniDocument;
import org.allen.rbo.service.ini.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserAction {
	@Autowired
	private PlayerService heroService;
	
	@RequestMapping("/hero")
	public String hero(@RequestParam(required=false) String name,Model model) {
		if(name != null) {
			model.addAttribute("name", name);
			try {
				IniDocument save = heroService.find(name);
				model.addAttribute("save", save);
			} catch (RuntimeException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				model.addAttribute("alert","用户不存在");
			}
		}

		return "/user/hero";
	}
	
}
