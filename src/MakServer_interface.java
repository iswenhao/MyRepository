/*
 * Web service 
 * author @tiandong
 * e-mail: tiandong@ultrapower.com.cn
 * ����˽����г���Ӫ����ϵͳƽ̨�ͻ���������Զ�������񹤵�
 */

import java.util.Map;
import com.ultra.interfac.KF_creater;

public class MakServer_interface{
   
	  String title;
	  String PRI;
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

          title = (String)m.get("title");
          PRI = (String)m.get("PRI");
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
	 
    
	 String[] creat_KFvalue={title,PRI,applicant,department,
			          phone,mail,system,cause,
			          finishtime,attachname,attach,
			          ID,seq,region};

    
          System.out.println("creating KF_creater");   

          KF_creater kfc = new KF_creater("/u01/tomcat55/webapps/axis/attchfiles/");
		
         System.out.println("calling KF_creater.InsertData");  
	
         kfc.InsertData("mak","createProcess",creat_KFvalue);
		
         System.out.println("all success��");
		
	       return "0";
	
	}
    
}