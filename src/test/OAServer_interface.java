package test;

/*
 * Web service 
 * author @tiandong
 * e-mail: tiandong@ultrapower.com.cn
 * 服务端接收OAIT支持单客户端请求后自动生成事件工单
 */

import java.util.Map;
import com.ultra.interfac.KF_creater;

public class OAServer_interface{
   
	  
	  String applicant;
	  String department;
	  String phone; 
	  String mail; 
	  String system; 
	  String cause; 
	  String finishtime; 
	  String attachname; 
          String attach; 
	  String ID; 
	  String seq; 
	  String region; 

	public String CreaterProcess(Map m) throws Exception{
	
	  System.out.println("connect to success...");

	  applicant = (String)m.get("applicant");
	  department = (String)m.get("department");
	  phone = (String)m.get("phone");
	  mail = (String)m.get("mail");
	  system = (String)m.get("system");
	  cause = (String)m.get("cause");
	  finishtime = (String)m.get("finishtime");
	  attachname = (String)m.get("attachname");
          attach = (String)m.get("attach");
	  ID = (String)m.get("ID");
	  seq = (String)m.get("seq");
	  region = (String)m.get("region");
	 
	  
	 System.out.println("set value success !!!");
	 
    
	 String[] creat_KFvalue={applicant,department,
			                 phone,mail,system,cause,
			                 finishtime,attachname,attach,
			                 ID,seq,region};

    
          System.out.println("creating KF_creater");   

          KF_creater kfc = new KF_creater("/u02/tomcat55/webapps/axis/attchfiles/");
		
         System.out.println("calling KF_creater.InsertData");  
	
         kfc.InsertData("oa","createProcess",creat_KFvalue);
		
         System.out.println("all success！");
		
	       return "OA Incident Task_table Success.......";
	
	}
    
}
