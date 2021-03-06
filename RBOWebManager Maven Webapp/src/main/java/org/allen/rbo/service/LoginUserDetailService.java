package org.allen.rbo.service;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.allen.rbo.service.ini.IniDocument;
import org.allen.rbo.service.ini.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class LoginUserDetailService implements UserDetailsService{
	@Autowired
	private PlayerService playerService;
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			IniDocument ini = playerService.find(username);
			return buildUserForAuthentication(username, ini.getValue("Password"),buildUserAuthority(ini));
		} catch (FileNotFoundException e) {
			throw new UsernameNotFoundException(username);
		}
	}

	/**
	   * 返回验证角色
	   * @param userRoles
	   * @return
	   */
	  private List<GrantedAuthority> buildUserAuthority(IniDocument player){
	    List<GrantedAuthority> result = new ArrayList<GrantedAuthority>();
	    result.add(new SimpleGrantedAuthority("ROLE_USER"));
	    if(player.isTrue("Admin"))
	    	result.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
	    return result;
	  }
	  
	  /**
	   * 返回验证用户
	   * @param user
	   * @param authorities
	   * @return
	   */
	  private User buildUserForAuthentication(String username,String password,List<GrantedAuthority> authorities){
	    return new User(username,password,true,true,true,true,authorities);
	  }
}
