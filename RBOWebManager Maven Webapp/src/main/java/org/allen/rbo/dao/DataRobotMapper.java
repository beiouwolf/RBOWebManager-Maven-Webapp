package org.allen.rbo.dao;

import java.util.List;

import org.allen.rbo.domain.DataRobot;
import org.allen.rbo.domain.DataRobotWeapon;

public interface DataRobotMapper {
	public List<DataRobot> select(DataRobot robot);
	
	public void insertRobot(DataRobot robot);
	public void insertRobotWeapon(DataRobotWeapon weapon);
	
	public void deleteRobot();
	public void deleteRobotWeapon();
}
