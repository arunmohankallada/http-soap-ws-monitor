package com.arun.HttpSoapWSMonitor;

import java.io.File;
import java.net.URL;

public class  ClasspathFileReader{

	private static final String CONFIG_FILE = "/WSList.json";
	private static final String APP_CONFIG = "/application.properties";
	public ClasspathFileReader() {
		// TODO Auto-generated constructor stub
	}
	public String readInputJson()
    {
        URL fileUrl = getClass().getResource(CONFIG_FILE);
        File a= new File(fileUrl.getFile());
        return a.toString();
    }
	public String getPropFileName() {
		URL fileUrl = getClass().getResource(APP_CONFIG);
        File a= new File(fileUrl.getFile());
        return a.toString();
	}
	public String getXMLPath(String xmlName) throws Exception {
		try {
			URL fileUrl = getClass().getResource(xmlName);
	        File a= new File(fileUrl.getFile());
	        return a.toString();
		}
		catch (Exception e){
			System.out.println( e.getMessage());
		}
		
        return null;
	}

}
