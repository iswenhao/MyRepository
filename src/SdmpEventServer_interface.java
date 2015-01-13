import java.util.Map;

import org.apache.log4j.Logger;

import com.ultra.interfac.KF_creater;

public class SdmpEventServer_interface {
	private static final Logger loger = Logger
			.getLogger(SdmpEventServer_interface.class);
	private String outSystem;
	private String serialNo;
	private String workitemID;
	private String serviceContent;
	private String acceptTime;
	private String creationTime;
	private String urgentID;
	private String completeTime;
	private String contactPerson;
	private String contactPhone;
	private String Disposal_team;
	private String handlingStaff;

	public String CreaterProcess(Map m) throws Exception {

		System.out.println("connect to success...");

		outSystem = (String) m.get("outSystem");
		serialNo = (String) m.get("serialNo");
		workitemID = (String) m.get("workitemID");
		serviceContent = (String) m.get("serviceContent");
		handlingStaff = (String) m.get("handlingStaff");
		acceptTime = (String) m.get("acceptTime");
		creationTime = (String) m.get("creationTime");
		urgentID = (String) m.get("urgentID");
		completeTime = (String) m.get("completeTime");
		contactPerson = (String) m.get("contactPerson");
		contactPhone = (String) m.get("contactPhone");
		Disposal_team = (String) m.get("Disposal_team");

		loger.info("set value success !!!");
		loger.info("outSystem=" + outSystem);
		loger.info("serialNo=" + serialNo);
		loger.info("workitemID=" + workitemID);
		loger.info("serviceContent=" + serviceContent);
		loger.info("handlingStaff=" + handlingStaff);
		loger.info("acceptTime=" + acceptTime);
		loger.info("creationTime=" + creationTime);
		loger.info("urgentID=" + urgentID);
		loger.info("completeTime=" + completeTime);
		loger.info("contactPerson=" + contactPerson);
		loger.info("contactPhone=" + contactPhone);
		loger.info("Disposal_team=" + Disposal_team);

		if ("".equals(outSystem) || outSystem == null || "".equals(serialNo)
				|| serialNo == null || "".equals(serviceContent)
				|| serviceContent == null || "".equals(acceptTime)
				|| acceptTime == null || "".equals(creationTime)
				|| creationTime == null || urgentID == null
				|| "".equals(urgentID) || "".equals(completeTime)
				|| completeTime == null || Disposal_team == null
				|| "".equals(Disposal_team)) {

			return "In the transmission field has NULL the value...";
		} else {

			String[] creat_KFvalue = { outSystem, serialNo, workitemID,
					serviceContent, handlingStaff, contactPerson, contactPhone,
					acceptTime, creationTime, urgentID, completeTime,
					Disposal_team };

			loger.info("creating KF_creater");

			KF_creater kfc = new KF_creater(
					"/u01/tomcat55/webapps/axis/attchfiles/");

			loger.info("calling KF_creater.InsertData");

			kfc.InsertData("sdmpevent", "createProcess", creat_KFvalue);

			loger.info("all success");

			return "0";
		}
	}
}
