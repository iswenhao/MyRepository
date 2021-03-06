/*
 * Web service 服务器端
 * author @tiandong
 * e-mail: tiandong@ultrapower.com.cn
 * 服务端接受客服中心客户端请求后自动生成事件工单
 */

import java.util.Map;
import com.ultra.interfac.KF_creater;

public class BamServer_interface{
   

	  String serialNo;//flow number
	  String serviceContent;//alarm content
	  String acceptTime;//alarm happen time
	  String table_classid;//outsystem Id

	public String CreaterProcess(Map m) throws Exception{
	
	

	  serialNo = (String)m.get("serialNo");
	  serviceContent = (String)m.get("serviceContent");
	  acceptTime= (String)m.get("acceptTime");
	  table_classid = (String)m.get("table_classid");
	  
	  String[] creat_KFvalue={serialNo,serviceContent,
	                          acceptTime,table_classid};

    
       System.out.println("creating KF_creater..........");   
       

            KF_creater kfc = new KF_creater("/u01/tomcat55/webapps/axis/attchfiles/");
           
		
       System.out.println("start KF_creater.InsertData......"); 

      
            kfc.InsertData("bam","createProcess",creat_KFvalue);
            
		
       System.out.println("complete KF_creater.InsertData..........");
       
		
	   return "bam create table success....wait..process...";
	
	}
    
}

