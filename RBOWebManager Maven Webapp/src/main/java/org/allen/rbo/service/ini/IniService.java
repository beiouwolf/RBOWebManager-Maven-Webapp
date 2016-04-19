package org.allen.rbo.service.ini;

import java.io.FileNotFoundException;
import java.util.List;

public interface IniService {
	public IniDocument find(String name) throws FileNotFoundException;
	public void write(IniDocument doc);
	
	public int getIniMax();
	
	public List<IniDocument> listFiles();
}
