/*
 * Web service 服务器端
 * author @tiandong
 * e-mail: tiandong@ultrapower.com.cn
 * 服务端接受客服中心客户端请求后自动生成事件工单
 */

import java.util.Map;
import com.ultra.interfac.KF_creater;

public class ClientServer_interface{
   
    
	
	  String outSystem;//外部系统ID
	  String serialNo;//流水号
	  String workItemId;//派单号
	  String subsNumber;//受理号码
	  String serviceContent;//业务内容
	  String handlingComment;//派单建议
	  String srTypeID;//服务请求类型
	  String subsCity;//号码归属地
	  String acceptCity;//受理地市
	  String handlingStaff;//下派工号
	  String contactPerson;//联系人
	  String contactPhone1;//联系电话1
	  String contactPhone2;//联系电话2
	  String address;//联系地址
	  String acceptTime;//受理时间
	  String creationTime;//下派时间
	  String subsLevel;//用户品牌
	  String urgentID;//紧急程度
	  String completeTime;//最终回复时限
	  String affixName;//附件名
	  String affixFullUrl;//附件地址
          String complainType;//投诉类型

	public String CreaterProcess(Map m) throws Exception{
	
	  System.out.println("connect to success...");

	  outSystem = (String)m.get("outSystem");
	  serialNo = (String)m.get("serialNo");
	  workItemId= (String)m.get("workItemId");
	  subsNumber = (String)m.get("subsNumber");
	  serviceContent = (String)m.get("serviceContent");
	  handlingComment = (String)m.get("handlingComment");
	  srTypeID = (String)m.get("srTypeID");
	  subsCity = (String)m.get("subsCity");
	  acceptCity = (String)m.get("acceptCity");
	  handlingStaff = (String)m.get("handlingStaff");
	  contactPerson = (String)m.get("contactPerson");
	  contactPhone1 = (String)m.get("contactPhone1");
	  contactPhone2 = (String)m.get("contactPhone2");
	  address = (String)m.get("address");
	  acceptTime = (String)m.get("acceptTime");
	  creationTime = (String)m.get("creationTime");
	  subsLevel = (String)m.get("subsLevel");
	  urgentID = (String)m.get("urgentID");
	  completeTime = (String)m.get("completeTime");
	  affixName = (String)m.get("affixName");
	  affixFullUrl = (String)m.get("affixFullUrl");
          complainType = (String)m.get("complainType");
	  
	  System.out.println("set value success !!!");
	 
          if("".equals(outSystem)||outSystem==null ||"".equals(serialNo)|| serialNo==null||"".equals(workItemId)||workItemId==null
	    ||"".equals(subsNumber)||subsNumber==null||"".equals(serviceContent)||serviceContent==null||
	    "".equals(handlingComment)||handlingComment==null||"".equals(acceptTime)||acceptTime==null||
	    "".equals(creationTime)||creationTime==null||"".equals(subsLevel)||subsLevel==null
	    ||"".equals(completeTime)||completeTime==null||"".equals(complainType)||complainType==null){
		 
		 return "In the transmission field has NULL the value...";
		 
	   }else{

		String[] creat_KFvalue={outSystem,
               serialNo,workItemId,subsNumber,serviceContent,handlingComment,
               srTypeID,subsCity,acceptCity,handlingStaff,contactPerson,
               contactPhone1,contactPhone2,address,acceptTime,creationTime,
               subsLevel,urgentID,completeTime,affixName,affixFullUrl,complainType};

    
      System.out.println("creating KF_creater");   

      KF_creater kfc = new KF_creater("/u01/tomcat55/webapps/axis/attchfiles/");
		
      System.out.println("calling KF_creater.InsertData"); 
      
      kfc.InsertData("cs","createProcess",creat_KFvalue);
		
	  System.out.println("all success！");
		
	      return "0";
	 }
	}
    
}

