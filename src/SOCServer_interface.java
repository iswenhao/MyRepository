
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ultra.interfac.KF_creater;

public class SOCServer_interface {
	private static final Logger loger = Logger.getLogger(SOCServer_interface.class);
	String outSystem;
	String serialNo;
	String workItemId;
	String serviceContent;
	String contactPerson;
	String urgentID;
	String businessId;
	String type;
	String Category;
	String acceptTime;
	String creationTime;
	String completeTime;
	String affixName;
	String affixFullUrl;
	String Disposal_team;
	String handlingStaff;
	String remarkA;
	String caption;
	
	String redemyNo;
	String group;
	String handlingComment;
	
	
	public String createProcess(Map m) throws Exception{
		outSystem=(String)m.get("outSystem");
		serialNo=(String)m.get("serialNo");
		workItemId=(String)m.get("workItemId");
		serviceContent=(String)m.get("serviceContent");
		contactPerson=(String)m.get("contactPerson");
		urgentID=(String)m.get("urgentID");
		businessId=(String)m.get("businessId");
		type=(String)m.get("type");
		Category=(String)m.get("Category");
		acceptTime=(String)m.get("acceptTime");
		creationTime=(String)m.get("creationTime");
		completeTime=(String)m.get("completeTime");
		affixName=(String)m.get("affixName");
		affixFullUrl=(String)m.get("affixFullUrl");
		Disposal_team=(String)m.get("Disposal_team");
		handlingStaff=(String)m.get("handlingStaff");
		remarkA=(String)m.get("remarkA");
		caption=(String)m.get("caption");
		
		
		loger.info("接收MAP中数据开始..........................");
		loger.info("outSystem :"+outSystem);
		loger.info("serialNo :"+serialNo);
		loger.info("workItemId :"+workItemId);
		loger.info("serviceContent :"+serviceContent);
		loger.info("contactPerson :"+contactPerson);
		loger.info("urgentID :"+urgentID);
		loger.info("businessId :"+businessId);
		loger.info("type :"+type);
		loger.info("Category :"+Category);
		loger.info("acceptTime :"+acceptTime);
		loger.info("creationTime :"+creationTime);
		loger.info("completeTime :"+completeTime);
		loger.info("affixName :"+affixName);
		loger.info("affixFullUrl :"+affixFullUrl);
		loger.info("Disposal_team :"+Disposal_team);
		loger.info("handlingStaff :"+handlingStaff);
		loger.info("remarkA :"+remarkA);
		loger.info("caption :"+caption);
		loger.info("接收到的MAP数据已结束......................");


		 System.out.println("oait set value success !!!");
		 
		    
		 String[] creat_KFvalue={outSystem,serialNo,
				 workItemId,serviceContent,contactPerson,urgentID,
				 businessId,type,Category,acceptTime,creationTime,
				 completeTime,affixName,affixFullUrl,Disposal_team,handlingStaff,remarkA,caption};

	    
	     System.out.println("creating KF_creater");   
	     KF_creater kfc = new KF_creater("/u01/tomcat55/webapps/axis/attchfiles/");
			
	     System.out.println("calling KF_creater.InsertData");  
		
	    kfc.InsertData("soc","createProcess",creat_KFvalue);
			
	    System.out.println("all success！");
		
		return "0";
	}
}
