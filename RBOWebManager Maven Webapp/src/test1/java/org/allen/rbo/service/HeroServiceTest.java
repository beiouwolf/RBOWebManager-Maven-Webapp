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
public class HeroServiceTest extends AbstractJUnit4SpringContextTests{
	@Autowired
	private HeroService heroService;
	
	@Test
	public void findHeroTest() {
		IniDocument hero = heroService.find("777777");
		Assert.notNull(hero);
		
		System.out.println(hero.getKeyMap());
	}
}
