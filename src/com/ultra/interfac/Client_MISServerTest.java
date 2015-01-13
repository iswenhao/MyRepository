package com.ultra.interfac;
import java.util.HashMap;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

public class Client_MISServerTest {
	
	public static void main(String args[]){
		String endpoint="http://10.70.60.132:8181/axis/MISServer_interface.jws?wsdl";
		try{
            // Create Service and Call object to set up a SOAP RPC

            Service service = new Service();

            Call call = (Call)service.createCall();

            // Tells which service and method will be invoked

            call.setTargetEndpointAddress(new java.net.URL(endpoint));

            call.setOperationName(new QName("createTicket"));
           // Invoke method with required parameters
			HashMap map=new HashMap();
            map.put("BOSSITicketID", "20090903258006");
            map.put("InstanceID", "20090903257374006");
            map.put("MISModelKey", "20090903289002106");
            map.put("Summary", "新建任务工单1");
            map.put("Description","任务单描述信息");
            map.put("TicketCompanyID", "A001");
            map.put("customerName", "路小名");
            map.put("CustomerID", "OO");
            map.put("submiterFullname", "勤力");
            map.put("submiterID", "");
/*            map.put("attachname", "");
            map.put("attachmentInfo", "");*/
            map.put("attachname", "Testaa.txt");
            map.put("attachmentInfo", "<attachRef>" +
      				"<attachInfo>" +
        				"<attachName>Testaa.txt</attachName>" +
        				"<attachURL>ftp://remeatt:remeatt@10.70.60.132:21/Testaa.txt</attachURL>" +
        				"<attachLength>39</attachLength>" +
       				"</attachInfo>" +
       				"</attachRef>");
            
/*            call.setOperationName(new QName("feedbackTicket"));
            HashMap map=new HashMap();
            map.put("MISTicketID", "");
            map.put("statusClosureCode", "锟缴癸拷");*/
 
            
            String ret = (String)call.invoke(new Object[] {map});
          
            System.out.println(ret);
	
		}catch(Exception e){
			e.printStackTrace();
			
		}
	}
}
