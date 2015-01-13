import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
public class Client_SOCServerTest {
	
	public static void main(String [] args) {
        try {

              String endpoint = "http://soyo-PC:8181/Axis/SOCServer_interface.jws";
              

              // Create Service and Call object to set up a SOAP RPC

              Service service = new Service();

              Call call = (Call)service.createCall();

              // Tells which service and method will be invoked

              call.setTargetEndpointAddress(new java.net.URL(endpoint));

              call.setOperationName(new QName("createProcess"));

              // Invoke method with required parameters
           	Map map=new HashMap();
            map.put("outSystem", "soc");
        	map.put("serialNo", "231");
        	map.put("workItemId", "24");
        	map.put("serviceContent","业务内容信息");
        	map.put("contactPerson", "admin");
        	map.put("urgentID", "2");
            map.put("businessId", "系统编号");
            map.put("type", "1");
            map.put("Category", "网络");
            map.put("remarkA", "");
        	map.put("acceptTime", "2009-09-14 16:11:42");
        	map.put("creationTime", "2009-09-14 15:56:42");
        	map.put("completeTime", "2009-09-15 00:11:42");
            map.put("affixName", "");
            map.put("affixFullUrl", "");
            map.put("Disposal_team", "dds");
            map.put("handlingStaff", "admin");


              String ret = (String)call.invoke(new Object[] {map});
            		          
              System.out.println("is sucesses   " + ret + "'");

        } catch (Exception e) {

              System.err.println(e.toString());
        }

  }
}
