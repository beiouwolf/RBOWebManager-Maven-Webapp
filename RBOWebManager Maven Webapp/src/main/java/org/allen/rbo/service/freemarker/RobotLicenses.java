package org.allen.rbo.service.freemarker;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.allen.rbo.service.ini.IniDocument;
import org.allen.rbo.service.ini.IniLine;
import org.allen.rbo.service.ini.RobotService;
import org.springframework.beans.factory.annotation.Autowired;

import freemarker.ext.beans.StringModel;
import freemarker.template.SimpleHash;
import freemarker.template.SimpleNumber;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

public class RobotLicenses implements TemplateMethodModelEx {

	@Autowired
	private RobotService service;

	public Object exec(List args) throws TemplateModelException {
		if(args.size() != 1)
			throw new TemplateModelException("Wrong arguments");
		
		SimpleHash save = ((SimpleHash) args.get(0));
		Map<String,IniLine> map = save.toMap();
		
		List<IniDocument> list = new LinkedList<IniDocument>();
		for(Entry<String,IniLine> entry : map.entrySet()) {
			String name = entry.getKey();
			IniLine line = entry.getValue();
			if(!line.isbRem() && name.startsWith("Robot") && line.getValue().equals("True")) {
				String robot = name.substring(5);
				try {
					list.add(service.find(robot));
				} catch (FileNotFoundException e) {
					continue;
				}
			}
		}
		
		return list;
	}

}
