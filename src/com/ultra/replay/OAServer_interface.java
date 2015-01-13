package com.ultra.replay;

/*
 * Web service 
 * author @tiandong
 * e-mail: tiandong@ultrapower.com.cn
 * ����˽���OA�칫ƽ̨�ͻ���������Զ�����¼�����
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
	  String attach; 
	  String attachname; 
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
	  finishtime = this.getDate_index((String)m.get("finishtime"));
	  attach = (String)m.get("attach");
	  attachname = (String)m.get("attachname");
	  ID = (String)m.get("ID");
	  seq = (String)m.get("seq");
	  region = (String)m.get("region");
	 
	  
	 System.out.println("set value success !!!");
	 
    
	 String[] creat_KFvalue={applicant,department,
			                 phone,mail,system,cause,
			                 finishtime,attach,attachname,
			                 ID,seq,region};

    
          System.out.println("creating KF_creater");   

          KF_creater kfc = new KF_creater("/u02/tomcat55/webapps/axis/attchfiles/");
		
         System.out.println("calling KF_creater.InsertData");  
	
         kfc.InsertData("oa","createProcess",creat_KFvalue);
		
         System.out.println("all success��");
		
	       return "OA Incident Task_table Success.......";
	
	}
    
	public static String getDate_index(String finishtime){
		
		
		if(finishtime.length()>10){
			
			finishtime = finishtime.substring(0, 10);
			
		}
		return finishtime;
		
	}
	
}


