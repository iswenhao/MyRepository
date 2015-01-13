package test;

/*
 * Web service 服务器端
 * author @tiandong
 * e-mail: tiandong@ultrapower.com.cn
 * 服务端接受安全平台客户端请求后自动生成事件工单
 */

import java.util.Map;
import com.ultra.interfac.KF_creater;

public class Safe_EventServer_interface{
   
	  String serialNo;//流水号
	  String workItemId;//派单号
	  String contactPerson;//联系人
	  String acceptTime;//受理时间
	  String completeTime;//最终回复时限
	  String Had_Group;//处理组
	  String Had_Person;//处理人
	  String serviceContent;//业务内容
	  String affixName;//附件名
	  String affixFullUrl;//附件地址

	public String CreaterProcess(Map m) throws Exception{
	
	  System.out.println("connect to success...");

	 
	  serialNo = (String)m.get("serialNo");
	  workItemId= (String)m.get("workItemId");
	  contactPerson = (String)m.get("contactPerson");
	  acceptTime = (String)m.get("acceptTime");
	  completeTime = (String)m.get("completeTime");
      Had_Group = (String)m.get("Had_Group");
      Had_Person = (String)m.get("Had_Person");
	  serviceContent = (String)m.get("serviceContent");
	  affixName = (String)m.get("affixName");
	  affixFullUrl = (String)m.get("affixFullUrl");
	  
	  System.out.println("set value success !!!");
	 
    
		String[] creat_KFvalue={serialNo,workItemId,
		contactPerson,acceptTime,completeTime,Had_Group,Had_Person,
		serviceContent,affixName,affixFullUrl};

    
      System.out.println("creating KF_creater");   

      KF_creater kfc = new KF_creater("/u02/tomcat55/webapps/axis/attchfiles/");
		
      System.out.println("calling KF_creater.InsertData"); 
      
      kfc.InsertData("safe_event","createProcess",creat_KFvalue);
		
	  System.out.println("all success！");
		
	      return "0";
	
	}
    
}


