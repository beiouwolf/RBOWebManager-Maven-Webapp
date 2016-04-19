package org.allen.rbo.service.freemarker;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import org.allen.rbo.service.ini.IniDocument;
import org.allen.rbo.service.ini.IniLine;
import org.allen.rbo.service.ini.RobotService;
import org.springframework.beans.factory.annotation.Autowired;

import freemarker.template.SimpleHash;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

public class RobotImage implements TemplateMethodModelEx {

	@Autowired
	private RobotService service;

	public Object exec(List args) throws TemplateModelException {
		if(args.size() != 1)
			throw new TemplateModelException("Wrong arguments");
		
//		int robotid = ((SimpleNumber) args.get(0)).getAsNumber().intValue();
		SimpleHash save = ((SimpleHash) args.get(0));
		Map<String,IniLine> map = save.toMap();
		
		String nowRobot = map.get("NowRobot").getValue();
		String robotN = map.get("Robot" + nowRobot).getValue();
		
		try {
			IniDocument robot = service.find(robotN);
			return new SimpleHash(robot);
		} catch (FileNotFoundException e) {
			return "file not found";
		}
	}

}
