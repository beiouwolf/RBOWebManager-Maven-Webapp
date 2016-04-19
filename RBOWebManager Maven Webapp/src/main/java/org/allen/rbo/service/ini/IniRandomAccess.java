package org.allen.rbo.service.ini;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Map.Entry;

import org.allen.rbo.common.Constants;
import org.allen.rbo.common.SpringUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

@Repository
public class IniRandomAccess implements InitializingBean{
	private File BACKUP_DOC;
	public void afterPropertiesSet() throws Exception {
		Resource resource = SpringUtil.getContext().getResource("classpath:/");
		BACKUP_DOC = new File(resource.getFile(),"saveBak");
		if(!BACKUP_DOC.exists())
			BACKUP_DOC.mkdir();
	}
	 
	public IniDocument read(File file) throws FileNotFoundException {
		return read(new FileInputStream(file),file);
	}
	
	public void writer(File file,IniDocument doc) {
		try {
			write(new FileOutputStream(file), doc);
			File backFile = new File(BACKUP_DOC,file.getName());
			write(new FileOutputStream(backFile), doc);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	private IniDocument read(InputStream is,File file) {
		InputStreamReader isreader = null;
		BufferedReader reader;
		try {
			isreader = new InputStreamReader(is,Constants.CHARSET);
			reader = new BufferedReader(isreader);
			
			IniDocument doc = new IniDocument();
			doc.setFile(file);
			while(reader.ready())
				doc.add(reader.readLine());
						
//			doc.add("_filename=" + );
			return doc;
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if(isreader != null)
				try {
					isreader.close();
				} catch (IOException e) {
				}
		}
	}
	
	private void write(OutputStream os,IniDocument doc) {
		OutputStreamWriter oswriter = null;
		BufferedWriter writer = null;
		
		try {
			oswriter = new OutputStreamWriter(os,Constants.CHARSET);
			writer = new BufferedWriter(oswriter);
			
			for(Entry<String,IniLine> entry : doc.entrySet()) {
				writer.write(entry.getValue().getCache());
				writer.newLine();
			}
			
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if(oswriter != null)
				try {
					oswriter.close();
				} catch (IOException e) {
				}
		}
	}
}
