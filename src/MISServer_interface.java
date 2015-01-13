import java.util.Map;

import com.ultra.interfac.KF_creater;


public class MISServer_interface {

			String BOSSITicketID;
			String InstanceID;
			String MISModelKey;
			String Summary;
			String Description;
			String TicketCompanyID;
			String customerName;
			String CustomerID;
			String submiterFullname;
			String submiterID;
			String attachname;
			String attachmentInfo;
			String MISTicketID;
			String statusClosureCode;
	
	public String createTicket(Map m) throws Exception{
		
		  System.out.println("connect to success...");
			
		  	BOSSITicketID=(String)m.get("BOSSITicketID");
			InstanceID=(String)m.get("InstanceID");
			MISModelKey=(String)m.get("MISModelKey");
			Summary=(String)m.get("Summary");
			Description=(String)m.get("Description");
			TicketCompanyID=(String)m.get("TicketCompanyID");
			customerName=(String)m.get("customerName");
			CustomerID=(String)m.get("CustomerID");
			submiterFullname=(String)m.get("submiterFullname");
			submiterID=(String)m.get("submiterID");
			attachname=(String)m.get("attachname");
			attachmentInfo=(String)m.get("attachmentInfo");
		 
		 System.out.println("set value success !!!");
		 
	     if(BOSSITicketID.equals("") || InstanceID.equals("") || CustomerID.equals("") || Summary.equals("") )
	     {
	    	System.out.println("failed,parameter can't null!");
	    	return "1"; 
	     }
	     else{
		 String[] creat_KFvalue={BOSSITicketID,InstanceID,MISModelKey,
				 Summary,Description,TicketCompanyID,customerName,
				 CustomerID,submiterFullname,submiterID,
				 attachname,attachmentInfo};

	    
	          System.out.println("creating KF_creater");   

	          KF_creater kfc = new KF_creater("/u01/tomcat55/webapps/axis/attchfiles/");
			
	         System.out.println("calling KF_creater.InsertData");  
		
	         kfc.InsertData("mis","createTicket",creat_KFvalue);
			
	         System.out.println("all success£¡");
			
		       return "0";
	     }
		}
	    
	   public String feedbackTicket(Map m) throws Exception{

	          System.out.println("connect to RevertProcess...");

	          MISTicketID=(String)m.get("MISTicketID");
	          statusClosureCode=(String)m.get("statusClosureCode");
	          
	          System.out.println("RevertProcess set value success !!!");
	          
	          if(MISTicketID.equals("") || statusClosureCode.equals(""))
	          {
	  	    	System.out.println("failed,parameter can't null!");
		    	return "1"; 
	          }
	          else
	          {
		 		    String[] creat_KFvalue={MISTicketID,statusClosureCode};
			        System.out.println("creating RevertProcess");   
	
			        KF_creater kfc = new KF_creater("/u02/tomcat55/webapps/axis/attchfiles/");
			        System.out.println("calling KF_creater.InsertData");  
				
			        kfc.InsertData("mis","feedbackTicket",creat_KFvalue);
			        System.out.println("feedbackTicket all success£¡");
					
				       return "0";
	          }
		}
}
