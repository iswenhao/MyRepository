import java.util.Map;

import org.apache.log4j.Logger;

import com.ultra.interfac.DM_creater;

public class DMServer_interface {
	private static final Logger loger = Logger
			.getLogger(DMServer_interface.class);

	public String replyProcess(String outSystem, String serialNo,
			String tskRult, String tskDescription,String attchname,String attchurl) throws Exception {

		System.out.println("connect to success...");

		loger.info("outSystem=" + outSystem);
		loger.info("serialNo=" + serialNo);
		loger.info("tskRult="+ tskRult);
		loger.info("tskDescription="+tskDescription);
		loger.info("attchname="+attchname);
		loger.info("attchurl="+attchurl);
		
		StringBuffer sb = new StringBuffer(128);
		
		if("".equals(outSystem)||outSystem==null)
			sb.append("outSystem,");
		if("".equals(serialNo)||serialNo==null)
			sb.append("serialNo,");
		if("".equals(tskRult)||tskRult==null)
			sb.append("tskRult,");
		if("".equals(tskDescription)||tskDescription==null)
			sb.append("tskDescription,");
		
		String sbstr = sb.toString();
		if (!sbstr.equals("")) {
			return "In the transmission field "+sbstr+ " has the NULL value ...";
		} else {

			String[] creat_DMvalue = { outSystem, serialNo, tskRult, tskDescription , attchname, attchurl};

			loger.info("creating DM_creater");

			DM_creater dmc = new DM_creater(
					"/u01/tomcat55/webapps/axis/attchfiles/");

			loger.info("calling DM_creater.InsertData");
			
			try{

			dmc.InsertData("dm", "replyProcess", creat_DMvalue);
			
			}catch(Exception e){
				return "Insert record occur error ,the error information is:"+e.getMessage();
			}

			loger.info(serialNo+" insert success");

			return "0";
		}
	}
}
