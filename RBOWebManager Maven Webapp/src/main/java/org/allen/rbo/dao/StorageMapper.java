package org.allen.rbo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface StorageMapper {
	public void insert(@Param("Username") String Username,@Param("robot") String robot,
			@Param("HPEN") String HPEN,@Param("Weaponpower") String Weaponpower,
			@Param("HpEnGuard") String HpEnGuard,@Param("Eqip") String Eqip);
	
	public List<Map<String,Object>> select(@Param("Username") String Username);
	public Map<String,Object> selectOne(@Param("id") int id,@Param("Username") String Username);
	
	public void delete(@Param("id") int id,@Param("Username") String Username);
	
	public List<String> selectRobot();
}
