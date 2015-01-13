import java.util.Map;

import org.apache.log4j.Logger;

import com.ultra.interfac.AllEventCreate;


public class CreateEventServer {
	private static final Logger loger = Logger.getLogger(CreateEventServer.class);
	private String outSystem;
	private String serialNo;
	private String workItemId;
	private String summary;
	private String description;
	private String priority;
	private String casetype;
	private String source;
	private String system;
	private String region;
	private String contactPerson;
	private String csp_personID;
	private String contactPhone1;
	private String email;
	private String address;
	private String creationTime;
	private String resolvedTime;
	private String affixName;
	private String affixFullUrl;
	
	public String CreaterProcess(Map m) throws Exception {
		System.out.println("CreaterProcess connect to success...");
		outSystem = (String) m.get("outsystem");
		serialNo = (String) m.get("serialno");
		workItemId = (String) m.get("workitemid");
		summary = (String) m.get("summary");
		description = (String) m.get("description");
		priority = (String) m.get("priority");
		casetype = (String) m.get("casetype");
		source = (String) m.get("source");
		system = (String) m.get("system");
		region = (String) m.get("region");
		contactPerson = (String) m.get("contactperson");
		csp_personID = (String) m.get("csp_personid");
		contactPhone1 = (String) m.get("contactphone1");
		email = (String) m.get("email");
		address = (String) m.get("address");
		creationTime = (String) m.get("creationtime");
		resolvedTime = (String) m.get("resolvedtime");
		affixName = (String) m.get("affixname");
		affixFullUrl = (String) m.get("affixfullurl");
		
		
		loger.info("set value success !!!");
		loger.info("outSystem = " + outSystem);
		loger.info("serialNo = " + serialNo);
		loger.info("workItemId = " + workItemId);
		loger.info("summary = " + summary);
		loger.info("description = " + description);
		loger.info("system = " + system);
		loger.info("priority = " + priority);
		loger.info("casetype = " + casetype);
		loger.info("source = " + source);
		loger.info("contactPerson = " + contactPerson);
		loger.info("csp_personID = " + csp_personID);
		loger.info("contactPhone1 = " + contactPhone1);
		loger.info("email = " + email);
		loger.info("address = " + address);
		loger.info("region = " + region);
		loger.info("creationTime = " + creationTime);
		loger.info("resolvedTime = " + resolvedTime);
		loger.info("affixName = " + affixName);
		loger.info("affixFullUrl = " + affixFullUrl);
		
		if ("".equals(outSystem) || outSystem == null || "".equals(workItemId)
				|| workItemId == null || "".equals(summary)
				|| summary == null || "".equals(description)
				|| description == null || system == null
				|| "".equals(system) || priority == null
				|| "".equals(priority) || casetype == null
				|| "".equals(casetype) || source == null
				|| "".equals(source) || contactPerson == null
				|| "".equals(contactPerson) || creationTime == null
				|| "".equals(creationTime) || resolvedTime == null
				|| "".equals(resolvedTime) || region == null
				|| "".equals(region)) {
			
			return "CreateEventServer In the transmission field has NULL the value...";
		} else {

			AllEventCreate eventCreate = new AllEventCreate(
					"/u01/tomcat55/webapps/axis/attchfiles/");

			loger.info("calling AllEventCreate.InsertData");

			eventCreate.InsertData("allevent","createProcess", m);

			loger.info("all success");

			return "0";
		}
	}
}
