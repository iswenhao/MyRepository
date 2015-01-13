import java.util.Map;

import org.apache.log4j.Logger;

import com.ultra.interfac.KF_creater;


public class REQServer_interface {
	private static final Logger loger = Logger.getLogger(REQServer_interface.class);
	String applicant;
	String title;
	String PRI;
	String department;
	String phone;
	String mail;
	String mission;
	String submission;
	String system;
	String cause;
	String finishtime;
	String attachname;
	String attach;
	String ID;
	String seq;
	String region;
	
	public String createProcess(Map m) throws Exception{
		applicant=(String)m.get("applicant");
		title=(String)m.get("title");
		PRI=(String)m.get("PRI");
		department=(String)m.get("department");
		phone=(String)m.get("phone");
		mail=(String)m.get("mail");
		mission=(String)m.get("mission");
		submission=(String)m.get("submission");
		system=(String)m.get("system");
		cause=(String)m.get("cause");
		finishtime=(String)m.get("finishtime");
		attachname=(String)m.get("attachname");
		attach=(String)m.get("attach");
		ID=(String)m.get("ID");
		seq=(String)m.get("seq");
		region=(String)m.get("region");
		
		loger.info("Begin....................................");
		loger.info("applicant :"+applicant);
		loger.info("title :"+title);
		loger.info("PRI :"+PRI);
		loger.info("department :"+department);
		loger.info("phone :"+phone);
		loger.info("mail :"+mail);
		loger.info("mission :"+mission);
		loger.info("submission :"+submission);
		loger.info("system :"+system);
		loger.info("cause :"+cause);
		loger.info("finishtime :"+finishtime);
		loger.info("attachname :"+attachname);
		loger.info("attach :"+attach);
		loger.info("ID :"+ID);
		loger.info("seq :"+seq);
		loger.info("region :"+region);
		loger.info("End......................................");
		
		
		System.out.println("REQ set value success !!!");
		 
	    
		 String[] creat_KFvalue={applicant,title,
				 PRI,department,phone,mail,
				 mission,submission,system,cause,finishtime,
				 attachname,attach,ID,seq,region};

	    
	     System.out.println("creating KF_creater");   
	     KF_creater kfc = new KF_creater("/u01/tomcat55/webapps/axis/attchfiles/");
			
	     System.out.println("calling KF_creater.InsertData");  
		
	    kfc.InsertData("req","createProcess",creat_KFvalue);
			
	    System.out.println("all success");
	    
	    
	    return "0";
	}
	
	/*public String replayProcess(Map map)throws Exception{
		String status;
		String taskid;
		String ID;
		String attachname;
		String attach;
		String worklog;
		status=(String)map.get("status");
		taskid=(String)map.get("taskid");
		ID=(String)map.get("ID");
		attachname=(String)map.get("attachname");
		attach=(String)map.get("attach");
		worklog=(String)map.get("worklog");
		

		 System.out.println("oait set value success !!!");
		 
		    
		 String[] creat_KFvalue={status,taskid,
				 ID,attachname,attach,worklog
				 };
	     System.out.println("taking of my info  begin...........................");   
	     System.out.println("status :"+status);
	     System.out.println("taskid :"+taskid);
	     System.out.println("ID :"+ID);
	     System.out.println("attachname :"+attachname);
	     System.out.println("attach :"+attach);
	     System.out.println("worklog "+worklog); 
			
	    System.out.println("This is all success end.........................");
		
		return "0";
	}*/
}
