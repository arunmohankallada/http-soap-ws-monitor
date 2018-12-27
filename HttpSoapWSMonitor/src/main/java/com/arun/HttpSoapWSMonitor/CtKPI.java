package com.arun.HttpSoapWSMonitor;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.arun.HttpSoapWSMonitor.WebService;

public class CtKPI {
    
	JSONObject KPIObject;
	private String overAllStatus;
	JSONArray kpiData;
	String outJsonFile;
	public String getOutJsonFile() {
		return outJsonFile;
	}

	public void setOutJsonFile(String outJsonFile) {
		this.outJsonFile = outJsonFile;
	}

	public String getOverAllStatus() {
		return overAllStatus;
	}
	public void setOverAllStatus(String overAllStatus) {
		this.overAllStatus = overAllStatus;
	}
	public JSONObject getKPIObject() {
		return KPIObject;
	}
	public void setKPIObject(JSONObject kPIObject) {
		KPIObject = kPIObject;
	}
	public CtKPI() {
		super();
		this.KPIObject=new JSONObject();
		this.setOverAllStatus("GREEN");
	}
	
	public CtKPI(String kpiinutfile,String jsonOutFile) {
		this();
		this.setOutJsonFile(jsonOutFile);
		try {
		JSONParser parser = new JSONParser();
		this.setKPIObject((JSONObject) parser.parse(new FileReader(kpiinutfile)));
	    } catch (FileNotFoundException e) {
	            KPIErrorLog.logger.error("Error Message",e);
	    } catch (IOException e) {
	    			KPIErrorLog.logger.error("Error Message",e);
	    } catch (ParseException e) {
	    			KPIErrorLog.logger.error("Error Message",e);
	    }
		KPIErrorLog.logger.info("Read Input File:"+this.getKPIObject().toString());
	}
	public void KPIValidate() {
		kpiData = new JSONArray();
		JSONArray wsArray = (JSONArray) getKPIObject().get("webservice");
		JSONObject KPIResponse=new JSONObject();
		for (Object o : wsArray) {
			
			try {
				
			JSONObject jsonObject = (JSONObject) o;
			WebService ws=new WebService(jsonObject);
			JSONObject jsonRsp = ws.getResponseCode();
			KPIErrorLog.logger.debug("OutPut Json: "+ jsonRsp.toString());
			kpiData.add(jsonRsp);
			//System.out.println(ws.getResponseCode());
			}
			catch (IOException e) {
				KPIErrorLog.logger.error("Error Message",e);
			}					        	
		}
		KPIResponse.put("metadata",(JSONObject) getKPIObject().get("metadata"));
		KPIResponse.put("kpidata", kpiData);
		KPIResponse.put("overallstatus", getOverAllStatus());
		this.WriteKPIOutFile(KPIResponse);
	}
	private void WriteKPIOutFile (JSONObject kpiout) {
		KPIErrorLog.logger.info("Writing Out to File :" + kpiout.toString());
		try {
			FileWriter file = new FileWriter(this.getOutJsonFile());
			file.write(kpiout.toString());
			file.close();
		}
		catch(IOException e) {
			KPIErrorLog.logger.error("Error Message",e);
		}
		
	}

	

}
