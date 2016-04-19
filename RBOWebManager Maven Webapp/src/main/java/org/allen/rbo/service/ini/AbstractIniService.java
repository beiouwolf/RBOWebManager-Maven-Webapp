package org.allen.rbo.service.ini;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.allen.rbo.common.LogUtil;
import org.allen.rbo.common.SystemConfig;
import org.allen.rbo.common.Constants.Directory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * ini服务 基础函数
 * @author Yao
 *
 */
public abstract class AbstractIniService implements IniService,InitializingBean{

	// 系统配置 读取目录
	@Autowired
	protected SystemConfig systemConfig;
	
	// ini读写服务
	@Autowired
	protected IniRandomAccess iniRandomAccess;
	
	// 路径模式
	protected Directory directoryEnum;
	// 访问路径
	protected File directory;
	
	// 子类实现,告知路径模式
	public abstract Directory determinDirectory();
	
	public void afterPropertiesSet() throws Exception {
		setDirectoryEnum(determinDirectory());
		setDirectory(new File(systemConfig.getRboPath(),directoryEnum.val));
	}
	
	// 子类实现,告知文件名模式
	protected abstract String name(String name);
	
	// 获取文件位置
	protected File file(String name) {
		return new File(directory,name(name));
	}
	
	/**
	 * 获取ini文件内容
	 * @throws FileNotFoundException 
	 */
	public IniDocument find(String name) throws FileNotFoundException {
		return iniRandomAccess.read(file(name));
	}
	
	/**
	 * 获取文件夹列表
	 * @return
	 */
	protected abstract FilenameFilter getFilenameFilter();
	public List<IniDocument> listFiles() {
		File[] files = directory.listFiles(getFilenameFilter());
		ArrayList<IniDocument> list = new ArrayList<IniDocument>(files.length);
		for(File file : files) {
			try {
				list.add(iniRandomAccess.read(file));
			} catch (FileNotFoundException e) {
				LogUtil.sys.error(e.getMessage(),e);
			}
		}
		return list;
	}
	
	/**
	 * 写入文件系统
	 * @param doc
	 */
	public void write(IniDocument doc) {
		iniRandomAccess.writer(doc.getFile(), doc);
	}
	
	/**
	 * 获取定义的index.ini文件中max=项目值
	 */
	public int getIniMax() {
		File file = new File(directory,"Index.ini");
		FileReader reader = null;
		Properties prop = new Properties();
		try {
			reader = new FileReader(file);
			prop.load(reader);
			String max = prop.getProperty("Max");
			return Integer.parseInt(max);
		} catch (IOException e) {
			LogUtil.sys.error(e.getMessage(),e);
			return 0;
		} finally {
			if(reader != null)
				try {
					reader.close();
				} catch (IOException e) {
				}
		}
	}

	public Directory getDirectoryEnum() {
		return directoryEnum;
	}

	public void setDirectoryEnum(Directory directoryEnum) {
		this.directoryEnum = directoryEnum;
	}

	public File getDirectory() {
		return directory;
	}

	public void setDirectory(File directory) {
		this.directory = directory;
	}

}
