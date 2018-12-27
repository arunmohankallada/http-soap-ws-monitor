package com.arun.HttpSoapWSMonitor;

public class EnvParameters {

	public static String BASE_DIR;
	public static String KPI_INPUT_JSON;
	public static String KPI_OUTPUT_JSON;
	static {
		BASE_DIR=System.getProperty("basedir");
		KPIErrorLog.logger.debug("BASE_DIR:"+BASE_DIR);
		
		KPI_INPUT_JSON=BASE_DIR+"/json/in/"+System.getProperty("injsonfile");
		KPIErrorLog.logger.debug("KPI_INPUT_JSON:"+KPI_INPUT_JSON);
		
		KPI_OUTPUT_JSON=BASE_DIR+"/json/out/"+System.getProperty("outjsonfile");
		KPIErrorLog.logger.debug("KPI_OUTPUT_JSON:"+KPI_OUTPUT_JSON);
	}
	public EnvParameters() {
		super();
	}

}
