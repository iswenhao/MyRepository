package com.ultra.interfac;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Vector;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import test.Replace;
import com.ultra.dbConn.*;

public class DM_creater extends DBOperation {
	
	private String xmlPath =null;
	String[] att_list =null;
	String att_name_list ="";
	attachRef_process attp = new attachRef_process();
	private String affixname;
	private String affixfullurl;
	private String serialno;
	private String outsystem;
	public DM_creater(String xmlPath) {
		this.xmlPath = xmlPath;
	}

	public String InsertData(String interface_class, String interface_method,
			String[] creat_DMvalue) throws Exception {
		
		if (creat_DMvalue == null || creat_DMvalue.length == 0) {
			throw new Exception("parameters error ! NULL parameters");
		}
		SAXReader reader = new SAXReader();
		Document document = null;

		try {
			document = reader
					.read(new File(this.xmlPath + "interface_con.xml"));
		} catch (Exception e) {
			throw e;
		}
		Element root = document.getRootElement();
		Element one = root.element(interface_class);
		one = one.element("server");
		one = one.element(interface_method);
		Element floer = root.element("attachment");
		floer = floer.element("localpath");
		
		String f = floer.getText();
		affixfullurl = "";
		affixname = "";
		serialno = "";
		outsystem = "";
		
		String att0="";
		String att1="";
		String att2="";
		String att3="";
		String att4="";
		
		String strN = "ID,IFCNAME,FNTAG,NOWTIME";
		String strV = "DM_ID_SEQ.nextval,'" + interface_class + "','"
				+ interface_method + "','" + getOrderTime() + "'";

		List three = one.elements();
		
		for (int i = 0 , len = creat_DMvalue.length; i < len; i++) {

			String tmpVdb = ((Element) three.get(i)).attributeValue("dbf");

			String arf = ((Element) three.get(i)).attributeValue("arftype");
			
			strN += "," + tmpVdb;
			strV += ",?";

			if ("serialno".equals(tmpVdb)) {

				serialno = creat_DMvalue[i];
				strN += ",SYSID";			//sysid 与 serialno 是同一值
				strV += ","+serialno;
			}
			
			if ("outsystem".equals(tmpVdb)) {
				outsystem = creat_DMvalue[i];
			}
			
			System.out.println("@@@Axis@@@ :"+"**********1******");
			
			if ("ATTCHNAME".equals(tmpVdb)) {
				affixname = creat_DMvalue[i];
				System.out.println("@@@Axis@@@ :"+"if (S256_3.equals(tmpVdb)) S256_3:::"+affixname);
				att_name_list = affixname;
				System.out.println("@@@Axis@@@ :"+"if (S256_3.equals(tmpVdb)) att_name_list:::"+att_name_list);
				creat_DMvalue[i] = att_name_list;
			}
			
			if ("ATTACH".equals(arf)) {

				System.out.println("@@@Axis@@@ :deal ATTACH******");

				affixfullurl = creat_DMvalue[i];
				if (affixfullurl == null || affixfullurl.equals("")) {
					creat_DMvalue[i]="";
					System.out.println("@@@Axis@@@:******2::affixFullUrl is empty******");

				} else {
					System.out.println("@@@Axis@@@:**********333kkk::;affixFullUrl is not empty******");
					System.out.println("@@@Axis@@@:att_name_list is :::" + att_name_list);
					att_list = attp.create_FTPstr(affixfullurl, f);

					if (att_list != null && att_list.length == 1) {

						System.out.println("@@@Axis@@@:**********h1******" + att_name_list);

						att0 = att_name_list + "^";
						att1 = att_list[0];
						att2 = att0 + att1;
						System.out.println("@@@Axis@@@ :"+" att2=att0+att1;;;att2:::" + att2);

						creat_DMvalue[i] = att2;

						System.out.println("@@@Axis@@@ :"+"**********h2******");
					}
					System.out.println("@@@Axis@@@ :"+"**********h3******");
					if (att_list != null && att_list.length > 1) {
						System.out.println("@@@Axis@@@ :"+"**********h4******");
						int idex = att_name_list.indexOf("#");

						String str1 = att_name_list.substring(0, idex);

						att0 = str1 + "^";
						att1 = att_list[0] + ";";
						att2 = att0 + att1;
						System.out.println("@@@Axis@@@ :"+"1----------" + att2);

						String str2 = att_name_list.substring(idex + 1);
						System.out.println("@@@Axis@@@ :"+"2----------" + str2);
						att3 = att_list[1];
						att4 = att2 + str2 + "^" + att3;

						System.out.println("@@@Axis@@@ :"+"**********h6******");
						System.out.println("@@@Axis@@@ :"+att4);
						creat_DMvalue[i] = att4;
					}
					System.out.println("@@@Axis@@@ :"+"**********h7******");
				}
				System.out.println("@@@Axis@@@ :"+"**********3******");
			}
			
			// System.out.println("@@@Axis@@@ :"+att_name_list);

		}
		try {

			Vector vc = getserialNo(serialno, interface_class);

			if ("reply".equals(one.attributeValue("fntype"))) {

				if (vc.size() > 0 && "dm".equals(outsystem)) {

					System.out.println("@@@Axis@@@ :"+"数据库中有重复流水号"+serialno+"，无法进行"+interface_method+"操作！！！");

					throw new Exception("数据库中有重复流水号"+serialno+"，无法进行"+interface_method+"操作！！！");
					
				}
				System.out.println("@@@Axis@@@ :"+"**********h8******");
			}
			
			String sqll = "insert into up_datemask_snd(" + strN 
			+ ") values(" + strV + ")";
			System.out.println("@@@Axis@@@ :"+"**********h10******");
			this.setConnName(ZMCC.zmccDBConn);
			this.prepare(sqll);
			System.out.println("@@@Axis@@@ :"+sqll);
			String sql2=sqll;
			String value;
			for (int i = 0 , len = creat_DMvalue.length; i < len ; i++) {
				System.out.println("@@@Axis@@@ :"+"DM_creater.class index::::");
				System.out.println("@@@Axis@@@ :"+i+1);
				System.out.println("@@@Axis@@@ :"+"DM_creater.class creat_DMvalue[i]:"+creat_DMvalue[i]);
				value=creat_DMvalue[i];
				this.setObject(i + 1, creat_DMvalue[i]);	
				
			} 
			int charu = this.executeUpdate();
			System.out.println("@@@Axis@@@ :"+"real SQL: "+sql2);
			System.out.println("@@@Axis@@@ :"+"Now Datetime Is " + getOrderTime());

			System.out.println("@@@Axis@@@ :"+"**********h11******");

		} catch (Exception e) {

			e.printStackTrace();
			throw e;

		}
		System.out.println("@@@Axis@@@ :"+"**********6******");	
		return "ok";
	}

	public Vector getserialNo(String serialno, String interface_class) {

		Vector vc = new Vector();

		String sql = " select serialno from up_datemask_snd where serialno = '" + serialno
				+ "' and IFCNAME= '" + interface_class + "' and FNTAG='replyProcess'";
		try {

			this.setConnName(ZMCC.zmccDBConn);
			this.prepare(sql);
			System.out.println("@@@Axis@@@ :"+sql);
			vc = this.executeQuery();

		} catch (Exception e){

			e.printStackTrace();

		}
		return vc;
	}


	public Vector getFNTimes(String serialno, String interface_class, String interface_method) {

		Vector vc = new Vector();

		String sql = " select serialno from up_datemask_snd where serialno = '" + serialno
				+ "' and IFCNAME= '" + interface_class + "' and FNTAG='"+interface_method+"'";
		try {

			this.setConnName(ZMCC.zmccDBConn);
			this.prepare(sql);
			System.out.println("@@@Axis@@@ :"+sql);
			vc = this.executeQuery();

		} catch (Exception e) {

			e.printStackTrace();

		}
		
		return vc;
	}
	
	public String getOrderTime() {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		java.util.Date now = new java.util.Date();
		String date = formatter.format(now);
		return date;

	}
	

}
