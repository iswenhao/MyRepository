package com.ultra.replay;

/*
 * Web service 
 * author @tiandong
 * e-mail: tiandong@ultrapower.com.cn
 * 服务端接受亿阳网管平台客户端请求后自动生成事件工单
 */

import java.util.HashMap;
import java.util.Map;
import com.ultra.interfac.KF_creater;

public class BossServer_interface{
   
	  String ticketid;
	  String alarmText;
	  String alarmTitle;
	  String Group;
	  String charge;
	  String event_time;
	  String processTime;
	  String redefine_severity;
	 

	public String CreaterProcess(Map m) throws Exception{
	
	  System.out.println("connect to success...");

	  ticketid = (String)m.get("ticketid");
	  alarmText = (String)m.get("alarmText");
	  alarmTitle = (String)m.get("alarmTitle");
	  Group = (String)m.get("Group");
	  charge = (String)m.get("charge");
	  event_time = (String)m.get("event_time");
	  processTime = (String)m.get("processTime");
	  redefine_severity = (String)m.get("redefine_severity");
	 
	  
	 System.out.println("set value success !!!");
	 
    
	 String[] creat_KFvalue={ticketid,alarmText,
	                       alarmTitle,Group,charge,
			       event_time,processTime,redefine_severity};

    
          System.out.println("creating KF_creater");   
          String xmlpath="/u02/tomcat55/webapps/axis/attchfiles/";
          String xmlPth="/Axis/WebRoot/attchfiles/interface_con.xml";
          String XMLpt="D:\\Projects\\Axis\\WebRoot\\attchfiles\\interface_con.xml";
          KF_creater kfc = new KF_creater(XMLpt);
		
         System.out.println("calling KF_creater.InsertData");  
	
         kfc.InsertData("boss","createProcess",creat_KFvalue);
		
         System.out.println("all success！");
		
	 return ticketid;
	
	}
    public static void main(String args[]){
    	Map map=new HashMap();
    	map.put("ticketid", "012222222222");
    	map.put("alarmText", "ddd");
    	map.put("alarmTitle", "Tilename");
    	map.put("Group", "shiye");
    	map.put("charge", "B02");
    	map.put("event_time", "090803");
    	map.put("processTime", "09090312");
    	map.put("redefine_severity", "safe");
    	String result="";
    	BossServer_interface m_BossServer_interface=new BossServer_interface();
    	try {
			result=m_BossServer_interface.CreaterProcess(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}


