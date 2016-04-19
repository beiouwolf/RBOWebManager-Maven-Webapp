package org.allen.rbo.service;

import org.allen.rbo.service.ini.IniDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:/mvc.xml")
public class CardServiceTest extends AbstractJUnit4SpringContextTests{
	@Autowired
	private CardService service;
	
	@Test
	public void findHeroTest() {
		IniDocument ini = service.find("371");
		Assert.notNull(ini);
		
		System.out.println(ini.getKeyMap());
	}
}
