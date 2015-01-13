import java.util.Map;

import org.apache.log4j.Logger;

import com.ultra.interfac.KF_creater;

public class SdmpTaskServer_interface {
	private static final Logger loger = Logger
			.getLogger(SdmpTaskServer_interface.class);
	private String applicant;
	private String title;
	private String PRI;
	private String department;
	private String phone;
	private String mail;
	private String system;
	private String cause;
	private String finishtime;
	private String attach;
	private String attachname;
	private String ID;
	private String seq;
	private String region;

	public String createProcess(Map m) throws Exception {
		applicant = (String) m.get("applicant");
		title = (String) m.get("title");
		PRI = (String) m.get("PRI");
		department = (String) m.get("department");
		phone = (String) m.get("phone");
		mail = (String) m.get("mail");
		system = (String) m.get("system");
		cause = (String) m.get("cause");
		finishtime = (String) m.get("finishtime");
		attachname = (String) m.get("attachname");
		attach = (String) m.get("attach");
		ID = (String) m.get("ID");
		seq = (String) m.get("seq");
		region = (String) m.get("region");

		loger.info("Begin....................................");
		loger.info("applicant :" + applicant);
		loger.info("title :" + title);
		loger.info("PRI :" + PRI);
		loger.info("department :" + department);
		loger.info("phone :" + phone);
		loger.info("mail :" + mail);
		loger.info("system :" + system);
		loger.info("cause :" + cause);
		loger.info("finishtime :" + finishtime);
		loger.info("attachname :" + attachname);
		loger.info("attach :" + attach);
		loger.info("ID :" + ID);
		loger.info("seq :" + seq);
		loger.info("region :" + region);
		loger.info("End......................................");

		System.out.println("sdmptask set value success !!!");

		String[] creat_KFvalue = { applicant, title, PRI, department, phone,
				mail, system, cause, finishtime, attachname, attach, ID, seq,
				region };

		System.out.println("creating KF_creater");
		KF_creater kfc = new KF_creater(
				"/u01/tomcat55/webapps/axis/attchfiles/");

		System.out.println("calling KF_creater.InsertData");

		kfc.InsertData("sdmptask", "createProcess", creat_KFvalue);

		System.out.println("all success");

		return "0";
	}
}
