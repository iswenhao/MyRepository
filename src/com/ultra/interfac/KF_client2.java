package com.ultra.interfac;
import java.util.HashMap;
import java.util.Map;
import javax.xml.namespace.QName;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;


public class KF_client2 {

	//public String   endpoint = "http://10.70.60.133:8181/axis/ClientServer_interface.jws?wsdl";
	//public String   endpoint = "http://10.70.9.197:8801/CSPRouter/services/RemoteReplyService?wsdl";
	
	public static void main(String[] args)
	{
		KF_client2 client=new KF_client2();
	}

	public   String  testWeb(String endpoint) {
		String result="";
		try {

            // Create Service and Call object to set up a SOAP RPC

            Service service = new Service();

            Call call = (Call)service.createCall();

            // Tells which service and method will be invoked

            call.setTargetEndpointAddress(new java.net.URL(endpoint));
            System.out.println("test1!!");
            call.setOperationName(new QName("CreaterProcess"));
            System.out.println("test2!!");
            // Invoke method with required parameters
            Map m=new HashMap();
       
            m.put("outSystem", "cs");
            m.put("serialNo","200807091959345311199");
            m.put("workItemId","200807211759354418899");
            m.put("subsNumber", "1377787242000");
            m.put("serviceContent","����");
            m.put("handlingComment", "����");
            m.put("srTypeID", "001003001002");
            m.put("subsCity", "571");
            m.put("acceptCity", "00");
            m.put("handlingStaff", "KFTEST0029");
            m.put("contactPerson", "KFTEST0029");
            m.put("contactPhone1", "13387191039");
            m.put("contactPhone2", "182910102");
            m.put("address", "����");
            m.put("acceptTime", "2008-5-11 12:01:11");
            m.put("creationTime", "2008-5-21 12:01:11");
            m.put("subsLevel","dds");
            m.put("urgentID","1");
            m.put("completeTime", "2008-12-30 00:00:00");
            
           // m.put("affixFullUrl", "<attachRef><attachInfo><attachName>1009281652058059</attachName><attachURL>ftp://remeatt:remeatt@10.70.60.133:21/1009281652058059.xml</attachURL><attachLength>1406</attachLength></attachInfo></attachRef>");
           // m.put("affixName", "1009281652058059.xml");
            m.put("affixName","0927����ͷ��ؿ��ϱ�_1.xls");
            m.put("affixFullUrl", "<attachRef>" +
    				"<attachInfo>" +
      				"<attachName>0927����ͷ��ؿ��ϱ�_1.xls</attachName>" +
      				"<attachURL>ftp://remeatt:remeatt@10.70.60.134:21/attch_oa/0927����ͷ��ؿ��ϱ�_1.xls</attachURL>" +
      				"<attachLength>39</attachLength>" +
     				"</attachInfo>" +
     				"</attachRef>");
            m.put("complainType", "leixing");

           System.out.println("test3!!"+ m.get("complainType"));
           
            String ret = (String)call.invoke(new Object[] {m});
          		          
            System.out.println("*************" + ret + "'");
            result="OK";
      } catch (Exception e) {
    	  result=e.getMessage();
            System.err.println(e.toString());

      }
      
      return result;

}

}

