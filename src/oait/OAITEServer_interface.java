package oait;
import java.util.Map;

import com.ultra.interfac.KF_creater;

/**
 * @date 2009-09-03
 * @author yingwen
 * OAITE�½��¼����ӿڷ�����
 */ 
public class OAITEServer_interface {
/**
 * ��װ�ֶβ���
 */
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
		String oait_area;
		String task_classone;
		String task_classtwo;
		
	public String CreaterProcess(Map m) throws Exception{
	/**
	 * GET�ó�MAP��ֵ������
	 */
		applicant=(String)m.get("applicant");
		department=(String)m.get("department");
		phone=(String)m.get("phone");
		mail=(String)m.get("mail");
		system=(String)m.get("system");
		cause=(String)m.get("cause");
		finishtime=(String)m.get("finishtime");
		attachname=(String)m.get("attachname");
		attach=(String)m.get("attach");
		ID=(String)m.get("ID");
		seq=(String)m.get("seq");
		region=(String)m.get("region");
		oait_area=(String)m.get("oait_area");
		task_classone=(String)m.get("task_classone");
		task_classtwo=(String)m.get("task_classtwo");
	  
	  String[] creat_KFvalue={applicant,department,phone,mail,system,cause,
			  finishtime,attachname,attach,ID,seq,region,oait_area,task_classone,task_classtwo};

   
      System.out.println("creating KF_creater..........");   
      
      /**
       * ���������ļ�·��interface_con.xml
       */
           KF_creater kfc = new KF_creater("/u01/tomcat55/webapps/axis/attchfiles/");
          
		
      System.out.println("start KF_creater.InsertData......"); 

     /**
      *�������ݣ�����ҵ��ϵͳ�������󷽷����������
      */
           kfc.InsertData("oaite","createProcess",creat_KFvalue);
           
		
      System.out.println("complete KF_creater.InsertData..........");
      
		
	   return "0";
	
	}
   
}
