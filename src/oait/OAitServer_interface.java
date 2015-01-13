package oait;

/*
 * Web service 
 * author @tiandong
 * e-mail: tiandong@ultrapower.com.cn
 * 服务端接收OAIT支持单客户端请求后自动生成事件工单
 */

import java.util.Map;
import com.ultra.interfac.KF_creater;

public class OAitServer_interface{
   
	  
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
	  String oait_area;
	  String task_classone;
	  String task_classtwo;

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
	  oait_area = (String)m.get("oait_area");
	  task_classone = (String)m.get("task_classone");
	  task_classtwo = (String)m.get("task_classtwo");
	  
	 System.out.println("oait set value success !!!");
	 
    
	 String[] creat_KFvalue={applicant,department,
			                 phone,mail,system,cause,
			                 finishtime,attachname,attach,
			                 ID,seq,region,oait_area,task_classone,task_classtwo};

    
          System.out.println("creating KF_creater");   

          KF_creater kfc = new KF_creater("/u01/tomcat55/webapps/axis/attchfiles/");
		
         System.out.println("calling KF_creater.InsertData");  
	
         kfc.InsertData("oait","createProcess",creat_KFvalue);
		
         System.out.println("all success！");
		
         
	

        return "success";
}
}

