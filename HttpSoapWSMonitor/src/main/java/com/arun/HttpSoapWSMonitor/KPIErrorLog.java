package com.arun.HttpSoapWSMonitor;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class KPIErrorLog {

	final static Logger logger = Logger.getLogger(KPIErrorLog.class);
	public KPIErrorLog() {
		super();
	}
	public void setLogLevel()
	{
		logger.setLevel(Level.DEBUG);
	}

}
