package test;

/*
 * Web service ��������
 * author @tiandong
 * e-mail: tiandong@ultrapower.com.cn
 * ����˽��ܰ�ȫƽ̨�ͻ���������Զ������¼�����
 */

import java.util.Map;
import com.ultra.interfac.KF_creater;

public class Safe_EventServer_interface{
   
	  String serialNo;//��ˮ��
	  String workItemId;//�ɵ���
	  String contactPerson;//��ϵ��
	  String acceptTime;//����ʱ��
	  String completeTime;//���ջظ�ʱ��
	  String Had_Group;//������
	  String Had_Person;//������
	  String serviceContent;//ҵ������
	  String affixName;//������
	  String affixFullUrl;//������ַ

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
		
	  System.out.println("all success��");
		
	      return "0";
	
	}
    
}


