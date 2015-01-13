package com.ultra.replay;

/*
 * Web service 
 * author @tiandong
 * e-mail: tiandong@ultrapower.com.cn
 * 服务端接收OA办公平台客户端请求后自动生成事件工单
 */
/*
 * Web service 
 * author @tiandong
 * e-mail: tiandong@ultrapower.com.cn
 * 服务端接收OA办公平台客户端请求后自动生成事件工单
 */
import java.util.Map;
import com.ultra.interfac.KF_creater;

/*
 * Web service 
 * author @tiandong
 * e-mail: tiandong@ultrapower.com.cn
 * 服务端接收OA办公平台客户端请求后自动生成事件工单
 */
public class IMEPServer_interface {

	String title;

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

	String cheaker;

	String cheak_note;

	String score;

	String imep_status;

	String start_distime;

	String com_distime;

	String worklog;

	String taskid;
	
	String imep_discom;

	public String CreaterProcess(Map m) throws Exception{
		
		  System.out.println("connect to success...");

	          title = (String)m.get("title");
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
		 
	    
		 String[] creat_KFvalue={title,applicant,department,
				                 phone,mail,system,cause,
				                 finishtime,attachname,attach,
				                 ID,seq,region};

	    
	          System.out.println("creating KF_creater");   

	          KF_creater kfc = new KF_creater("/u02/tomcat55/webapps/axis/attchfiles/");
			
	         System.out.println("calling KF_creater.InsertData");  
		
	         kfc.InsertData("imep","createProcess",creat_KFvalue);
			
	         System.out.println("all success！");
			
		       return "0";
		
		}
	    
	        public String RevertProcess(Map m) throws Exception{

	          System.out.println("connect to RevertProcess...");

	           cheaker=(String)m.get("cheaker");
	           cheak_note=(String)m.get("cheak_note");
	           score=(String)m.get("score");
	           imep_status=(String)m.get("imep_status");
	           ID = (String)m.get("ID");
	          System.out.println("RevertProcess set value success !!!");
		 
	    
		     String[] creat_KFvalue={cheaker,cheak_note,score,imep_status,ID};

	    
	          System.out.println("creating RevertProcess");   

	          KF_creater kfc = new KF_creater("/u02/tomcat55/webapps/axis/attchfiles/");
			
	         System.out.println("calling KF_creater.InsertData");  
		
	         kfc.InsertData("imep","RevertProcess",creat_KFvalue);
			
	         System.out.println("RevertProcess all success！");
			
		       return "0";
		
		}

	        public String RedisposeProcess(Map m) throws Exception{

	          System.out.println("connect to RedisposeProcess...");

	        start_distime = (String)m.get("start_distime");
		    worklog = (String)m.get("worklog");
		    com_distime = (String)m.get("com_distime");
		    attachname = (String)m.get("attachname");
		    attach = (String)m.get("attach");
		    taskid = (String)m.get("taskid");
		    imep_discom = (String)m.get("imep_discom");
		    ID = (String)m.get("ID");

	          System.out.println("RedisposeProcess set value success !!!");
		 
	    
		     String[] creat_KFvalue={start_distime,worklog,com_distime,attachname,attach,taskid,imep_discom,ID};

	    
	          System.out.println("creating RedisposeProcess");   

	          KF_creater kfc = new KF_creater("/u02/tomcat55/webapps/axis/attchfiles/");
			
	         System.out.println("calling KF_creater.InsertData");  
		
	         kfc.InsertData("imep","RedisposeProcess",creat_KFvalue);
			
	         System.out.println("RedisposeProcess all success！");
			
		       return "0";
		
		}
	        


	}
