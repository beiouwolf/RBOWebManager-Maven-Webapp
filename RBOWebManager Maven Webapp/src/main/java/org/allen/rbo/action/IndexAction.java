package org.allen.rbo.action;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.allen.rbo.common.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexAction {
	@Autowired
	private ApplicationContext ctx;
	
	private Random random = new Random();
	
	@RequestMapping("/index.html")
	public String index(Model model) {
		File doc;
		try {
			doc = ctx.getResource("/common/images/index").getFile();
			File[] files = doc.listFiles();
			
			int index = random.nextInt(files.length);
			model.addAttribute("background_image", files[index].getName());
		} catch (IOException e) {
			LogUtil.sys.error(e.getMessage(),e);
		}
		return "/index";
	}
}
