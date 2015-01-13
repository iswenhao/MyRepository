package com.ultra.replay;

import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

public class Client_revertProcess {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
	          String endpoint = "http://10.70.21.78:8080/csp/services/pbh/c_pbh_remoteReplyWebService?wsdl";

	              // Create Service and Call object to set up a SOAP RPC

	              Service service = new Service();

	              Call call = (Call)service.createCall();

	              // Tells which service and method will be invoked

	              call.setTargetEndpointAddress(new java.net.URL(endpoint));

	              call.setOperationName(new QName("CreaterProcess"));

	              // Invoke method with required parameters
	              Map m=new HashMap();
	              m.put("serialNo","1223111192191");
	              String ret = (String)call.invoke(new Object[] {m});
	            		          
	              System.out.println("***已将事件单信息回复至客服系统！！！***" + ret + "'");

	        } catch (Exception e) {
	        	e.printStackTrace();
	              System.err.println(e.toString());

	        }

	  }

	}

