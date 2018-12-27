package com.arun.HttpSoapWSMonitor;

public class TestKPI {
   
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		KPIErrorLog.logger.info("************Begining of Program************"); 
		CtKPI cKPIObj=new CtKPI(EnvParameters.KPI_INPUT_JSON,EnvParameters.KPI_OUTPUT_JSON);
        cKPIObj.KPIValidate();
         //cKPIObj.KPIValidate();
        KPIErrorLog.logger.info("************End of Program************");	
		
	}
}
