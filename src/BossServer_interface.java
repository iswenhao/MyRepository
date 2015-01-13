/*
 * Web service 
 * author @tiandong
 * e-mail: tiandong@ultrapower.com.cn
 * ����˽����������ƽ̨�ͻ���������Զ�����¼�����
 */

import java.util.Map;
import com.ultra.interfac.KF_creater;

public class BossServer_interface{
   
	  String ticketid;
	  String alarmText;
	  String alarmTitle;
	  String Group;
	  String event_time;
	  String processTime;
	  String redefine_severity;
	  String syscoming;//系统标志
	 

	public String CreaterProcess(Map m) throws Exception{
	
	  System.out.println("connect to success...");

	  ticketid = (String)m.get("ticketid");
	  alarmText = (String)m.get("alarmText");
	  alarmTitle = (String)m.get("alarmTitle");
	  Group = (String)m.get("Group");
	  event_time = (String)m.get("event_time");
	  processTime = (String)m.get("processTime");
	  redefine_severity = (String)m.get("redefine_severity");
	  //新增标志字段
	  syscoming = (String)m.get("syscoming");//新系统传入boss2,旧系统此字段为空
	  System.out.println("系统标准字段syscoming="+syscoming);
	 if(syscoming==null||syscoming.equals("")||syscoming.equals("null")){
		 syscoming = "";
	 }
	
	 System.out.println("set value success !!!");
	 String[] creat_KFvalue={ticketid,alarmText,
	                         alarmTitle,Group,event_time,
                                 processTime,redefine_severity,syscoming};

    
          System.out.println("creating KF_creater");   

          KF_creater kfc = new KF_creater("/u01/tomcat55/webapps/axis/attchfiles/");
		
         System.out.println("calling KF_creater.InsertData");  
        	 kfc.InsertData("boss","createProcess",creat_KFvalue);
		
         System.out.println("all success��");
		
	 return ticketid;
	
	}
    
}

