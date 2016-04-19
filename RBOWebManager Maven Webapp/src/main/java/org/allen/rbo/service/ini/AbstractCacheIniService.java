package org.allen.rbo.service.ini;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;

/**
 * 管理一个缓存区
 * @author Yao
 *
 */
public abstract class AbstractCacheIniService extends AbstractIniService implements IniService,InitializingBean{

	// 缓冲区
	private Map<String,FileStatus> cacheMap;
		
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		this.cacheMap = new HashMap<String, AbstractCacheIniService.FileStatus>(docFileSize());
	}
		
	// 覆盖find方法,由缓冲区获取数据
	@Override
	public IniDocument find(String name) throws FileNotFoundException {
		FileStatus status = cacheMap.get(name);
		File file = file(name);
	
		IniDocument ini;
		// 如果缓冲区数据不存在,并且最后更新时间不同,从实际数据中读取
		if(status == null || file.lastModified() != status.lastModified) {
			ini = iniRandomAccess.read(file);
			cacheMap.put(name, new FileStatus(file, file.lastModified(), ini));
			return ini;
		} else {
			return status.ini;
		}
	}
	
	protected abstract int docFileSize();

	private class FileStatus {
		File file;
		long lastModified;
		IniDocument ini;
		public FileStatus(File file, long lastModified, IniDocument ini) {
			super();
			this.file = file;
			this.lastModified = lastModified;
			this.ini = ini;
		}
	}
	
	@Override
	protected FilenameFilter getFilenameFilter() {
		// TODO Auto-generated method stub
		return null;
	}
}
