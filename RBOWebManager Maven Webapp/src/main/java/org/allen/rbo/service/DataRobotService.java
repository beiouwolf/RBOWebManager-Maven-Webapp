package org.allen.rbo.service;

import java.util.ArrayList;
import java.util.List;

import org.allen.rbo.common.LogUtil;
import org.allen.rbo.domain.DataRobot;
import org.allen.rbo.domain.DataRobotWeapon;
import org.allen.rbo.service.ini.IniDocument;
import org.allen.rbo.service.ini.RobotService;
import org.allen.rbo.wrap.DataRobotWrap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataRobotService {
	
	@Autowired
	private RobotService robotService;
	
	@Autowired
	private DataRobotWrap dataRobotWrap;
	
	
	public void buildRobotDatabase() {
		dataRobotWrap.delete();
		
		List<IniDocument> robotList = robotService.listFiles();
		
		for(IniDocument robot : robotList) {
			String filename = robot.getFile().getName();
			int id = Integer.parseInt(filename.substring(0,filename.indexOf(".ini")));
			
			DataRobot r = new DataRobot();
			r.setId(id);
			r.setName(robot.getValue("Name"));
			r.setHp(robot.getValue("HP"));
			r.setEn(robot.getValue("EN"));
			try {
				String tmp = robot.getValue("Ability");
				String[] tmps = tmp.split("|");
				r.setAbility1(tmps[0]);
				r.setAbility2(tmps[1]);
				r.setAbility3(tmps[2]);
				r.setAbility4(tmps[3]);
			} catch (Exception e) {
				LogUtil.sys.error("缓存机体数据读取,ability错误 机体:" + filename);
			}
			r.setMove(robot.getValue("Move"));
			r.setSpeed(robot.getValue("ATBSpeed"));
			r.setEvade(robot.getValue("Evade"));
			r.setGlight(robot.getValue("LightGuard"));
			r.setGball(robot.getValue("BallGuard"));
			r.setGwrestle(robot.getValue("WrestleGuard"));
			r.setGmagic(robot.getValue("MagicGuard"));
			r.setGhyper(robot.getValue("HyperspaceGuard"));
			r.setEquip(robot.getValue("EquipNumber"));
			String image = robot.getValue("Image");
			if(image != null)
				image = image.substring(0, image.lastIndexOf("."));
			r.setImage(image);
			r.setHprestore(robot.getValue("HPRestore"));
			r.setEnrestore(robot.getValue("ENRestore"));
			r.setNeed(robot.getValue("Need"));
			r.setSheildname(robot.getValue("ShieldName"));
			
			// weapon
			try {
				String[] weapon = robot.getValue("Weapon").split(",");
				String[] weaponpower = robot.getValue("Weaponpower").split(",");
				String[] weaponDefinition = robot.getValue("WeaponDefinition").split(",");
				String[] weaponen = robot.getValue("Weaponen").split(",");
				String[] weaponspace = robot.getValue("Weaponspace").split(",");
				String[] weaponCrtical = robot.getValue("WeaponCrtical").split(",");
				String[] wpSpeed1 = robot.getValue("WpSpeed1").split(",");
				String[] wpSpeed2 = robot.getValue("WpSpeed2").split(",");
				String[] weaponAttribute = robot.getValue("WeaponAttribute").split(",");
				List<DataRobotWeapon> weaponList = new ArrayList<>(weapon.length);
				for(int i = 0; i < weapon.length; ++i) {
					DataRobotWeapon w = new DataRobotWeapon();
					w.setName(weapon[i]);
					w.setPower(weaponpower[i]);
					w.setDef(weaponDefinition[i]);
					w.setEn(weaponen[i]);
					w.setSpace(weaponspace[i]);
					w.setCrtical(weaponCrtical[i]);
					w.setSpeed1(wpSpeed1[i]);
					w.setSpeed2(wpSpeed2[i]);
					w.setAttr(weaponAttribute[i]);
					w.setAmmo(robot.getValue("MaxAmmo"));
					w.setRobotid(id);
					weaponList.add(w);
				}
				r.setWeapons(weaponList);
			} catch (Exception e) {
				LogUtil.sys.error("缓存机体数据读取,武器读取失败" + filename);
			}
			dataRobotWrap.insert(r);
		}
	}
	
	public List<DataRobot> select(DataRobot robot) {
		return dataRobotWrap.select(robot);
	}
}
