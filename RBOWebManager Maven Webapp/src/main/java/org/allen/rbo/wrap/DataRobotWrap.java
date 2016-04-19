package org.allen.rbo.wrap;

import java.util.List;

import org.allen.rbo.dao.DataRobotMapper;
import org.allen.rbo.domain.DataRobot;
import org.allen.rbo.domain.DataRobotWeapon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DataRobotWrap {
	@Autowired
	private DataRobotMapper mapper;
	
	@Transactional(readOnly = true)
	public List<DataRobot> select(DataRobot robot) {
		return mapper.select(robot);
	}
	
	@Transactional(readOnly = false)
	public void insert(DataRobot robot) {
		mapper.insertRobot(robot);
		if(robot != null && robot.getWeapons() != null) {
			for(DataRobotWeapon w : robot.getWeapons())
				mapper.insertRobotWeapon(w);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete() {
		mapper.deleteRobotWeapon();
		mapper.deleteRobot();
	}
}
