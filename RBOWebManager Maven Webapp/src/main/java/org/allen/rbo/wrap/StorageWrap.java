package org.allen.rbo.wrap;

import java.util.List;
import java.util.Map;

import org.allen.rbo.dao.StorageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class StorageWrap {
	@Autowired
	private StorageMapper mapper;
	
	@Transactional(readOnly = false)
	public void insert(String Username,String robot,String HPEN,String Weaponpower,String HpEnGuard,String Eqip) {
		mapper.insert(Username,robot,HPEN,Weaponpower,HpEnGuard,Eqip);
	}
	
	@Transactional(readOnly = true)
	public List<Map<String,Object>> select(String Username) {
		return mapper.select(Username);
	}
	
	@Transactional(readOnly = true)
	public Map<String,Object> selectOne(int id,String username) {
		return mapper.selectOne(id,username);
	}
	
	@Transactional(readOnly = false)
	public void delete(int id,String username) {
		mapper.delete(id,username);
	}
	
	@Transactional(readOnly = true)
	public List<String> selectRobot() {
		return mapper.selectRobot();
	}
}
