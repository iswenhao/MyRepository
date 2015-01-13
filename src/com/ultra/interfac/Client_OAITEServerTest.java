package com.ultra.interfac;

import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

public final class Client_OAITEServerTest {

	public Client_OAITEServerTest() {
		// TODO Auto-generated constructor stub
	}
	public static void main(String args[]){
	
		String endpoint = "http://wuwl:8280/wstest/services/OAITEServer_interface?wsdl";
		try{
			Service service = new Service();	
			Call call = (Call)service.createCall();
	        // Tells which service and method will be invoked
			
	        call.setTargetEndpointAddress(new java.net.URL(endpoint));

	        call.setOperationName(new QName("CreaterProcess"));

	        // Invoke method with required parameters
	     	Map map=new HashMap();   	
		  	map.put("outSystem", "oaite");
		  	map.put("serialNo", "20080425001271101");
		  	map.put("workItemId", "2008042500008501");
		  	map.put("serviceContent", "���Է������ӣ��д���");
		  	map.put("contactPerson", "Ӧ��");
		  	map.put("contactPhone1", "13439584026");
		    map.put("address", "�㽭ʡ");
		    map.put("acceptTime", "2008-9-04 12:01:00");
		    map.put("creationTime", "2008-9-04 12:01:00");
		    map.put("completeTime", "2008-9-14 12:01:00");
		     map.put("Category","BOSSϵͳ");
		     map.put("Region", "ʡ��˾");
	        String ret = (String)call.invoke(new Object[] {map});
	      		          
	        System.out.println("***�ɹ����յ��ⲿϵͳ_OAite*�¼������ݣ�**" + ret + "'");
		}catch(Exception e){
			e.printStackTrace();
			
		}
	}

}
