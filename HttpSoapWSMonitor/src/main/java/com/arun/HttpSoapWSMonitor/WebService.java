package com.arun.HttpSoapWSMonitor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class WebService {

	String environment;
	String serviceName;
	String serviceLink;
	String serviceType;
	String serivceMethod;
	int timeOut;
	JSONArray parameter;
	String xmlFile;
	JSONObject jsonRspose;
	public JSONObject getJsonRspose() {
		return jsonRspose;
	}
	@SuppressWarnings("unchecked")
	public void setJsonRspose(int resposeCode,long latency) {
		this.jsonRspose = new JSONObject();
		this.jsonRspose.put("retuncode", Integer.toString(resposeCode));
		this.jsonRspose.put("environment",this.getEnvironment());
		this.jsonRspose.put("service", this.getServiceName());
		this.jsonRspose.put("latency", latency+"ms");
			
	}
	private String getEnvironment() {
		return environment;
	}
	private void setEnvironment(String environment) {
		this.environment = environment;
	}
	private String getServiceName() {
		return serviceName;
	}
	private void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	private String getServiceLink() {
		return serviceLink;
	}
	private void setServiceLink(String serviceLink) {
		this.serviceLink = serviceLink;
	}
	private String getServiceType() {
		return serviceType;
	}
	private void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	private String getSerivceMethod() {
		return serivceMethod;
	}
	private void setSerivceMethod(String serivceMethod) {
		this.serivceMethod = serivceMethod;
	}
	private int getTimeOut() {
		return timeOut;
	}
	private void setTimeOut(String timeOutString) {
		this.timeOut = Integer.parseInt(timeOutString);
	}
	private JSONArray getParameter() {
		return parameter;
	}
	private void setParameter(JSONArray parameter) {
		this.parameter = parameter;
	}
	private String getXmlFile() {
		return xmlFile;
	}
	private void setXmlFile(String xmlFile) {
		this.xmlFile = xmlFile;
	}
	public WebService() {
		super();
	}
	public WebService(JSONObject jsonObj) {
		
		this.setEnvironment((String) jsonObj.get("environment"));
		this.setServiceName((String) jsonObj.get("servicename"));
		this.setServiceLink((String) jsonObj.get("servicelink"));
		this.setServiceType((String) jsonObj.get("servicetype"));
		this.setSerivceMethod((String) jsonObj.get("servicemethod"));
		this.setTimeOut(jsonObj.get("timeout").toString());
		this.setParameter((JSONArray) jsonObj.get("parameter"));
		this.setXmlFile((String) jsonObj.get("xmlname"));
		
		//printWebservice();

		
	}
	
	private void printWebservice() {
		System.out.println("environment:"+this.getEnvironment());
		System.out.println("servicename:"+this.getServiceName());
		System.out.println("servicelink"+this.getServiceLink());
		System.out.println("servicetype"+this.getServiceType());
		System.out.println("servicemethod"+this.getSerivceMethod());
		System.out.println("timeout"+this.getTimeOut());
		System.out.println("parameter"+this.getParameter());
		System.out.println("xmlname"+this.getXmlFile());
	}
	private int getHttpRespose() throws IOException{
		URL url = new URL(this.getServiceLink());
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        http.setRequestMethod(this.getSerivceMethod());
        http.setReadTimeout(this.getTimeOut());
        return http.getResponseCode();
	}
	private int getSOAPResponse() {
		try {
			 String url = this.getServiceLink();
			 URL obj = new URL(url);
			 HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			 con.setRequestMethod("POST");
			 con.setConnectTimeout(this.getTimeOut());
			 con.setReadTimeout(this.getTimeOut());
			 con.setRequestProperty("Content-Type","application/soap+xml; charset=utf-8");
			 String xml=this.getXmlString();				
			 
			 con.setDoOutput(true);
			 DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			 wr.writeBytes(xml);
			 wr.flush();
			 wr.close();
			 int responseStatus = con.getResponseCode();
			 
			 //Code to Get the XML respose Not Needed Here
			 /*System.out.println(responseStatus);
			 BufferedReader in = new BufferedReader(new InputStreamReader(
			 con.getInputStream()));
			 String inputLine;
			 StringBuffer response = new StringBuffer();
			 while ((inputLine = in.readLine()) != null) {
			 response.append(inputLine);
			 }
			 in.close();
			 System.out.println("response:" + response.toString());*/
			 return responseStatus;
			 } catch (Exception e) {
			 System.out.println(e);
			 return -1;
			 }
	}
	private String getXmlString() throws IOException{
		Reader fileReader = new FileReader(this.getXmlFile()); 
		BufferedReader bufReader = new BufferedReader(fileReader); 
		StringBuilder sb = new StringBuilder(); 
		String line = bufReader.readLine(); 
		while( line != null){ 
			sb.append(line).append("\n"); 
			line = bufReader.readLine(); 
		} 
		String xml = sb.toString();
		bufReader.close();
		
		for (Object o : this.getParameter()) {
        	String parmString = (String) o;
        	xml=xml.replaceFirst("PARAM", parmString);
        }
		return xml;
	}
	public JSONObject getResponseCode() throws IOException {
		long startTime=0,endTime=0,latency=0;
		int respCode=0;
		
		if(this.getServiceType().equals("http")) {
			//System.out.println("Service:"+this.getServiceName()+ "Return Code:"+this.getHttpRespose());
			startTime=System.nanoTime();
			respCode=this.getHttpRespose();
			endTime=System.nanoTime();
			latency=Math.round((endTime-startTime)/1000000);
			this.setJsonRspose(respCode,latency);
		}
		else if(this.getServiceType().equals("soap")) {
			//System.out.println("Service:"+this.getServiceName()+ "Return Code:"+this.getSOAPResponse());
			startTime=System.nanoTime();
			respCode=this.getSOAPResponse();
			endTime=System.nanoTime();
			latency=Math.round((endTime-startTime)/1000000);
			this.setJsonRspose(respCode,latency);
			
		}
		return this.getJsonRspose();
		
		
	}
}
