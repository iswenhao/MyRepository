package com.ultra.interfac;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.com.ultrapower.eoms.util.StringUtil;

import com.ultra.dbConn.DBOperation;
import com.ultra.dbConn.ZMCC;

public class AllEventCreate extends DBOperation  {

	private String workitemid;
	private String outsystem;
	private String affixname;
	private String affixfullurl;
	private String xmlPath =null;
	String[] att_list =null;
	String att_name_list ="";
	attachRef_process attp = new attachRef_process();
	public AllEventCreate(String xmlPath) {
		this.xmlPath = xmlPath;
	}

	public String InsertData(String interface_class, String interface_method,
			Map m) throws Exception {
		
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
		String att0="";
		String att1="";
		String att2="";
		String att3="";
		String att4="";
		
		workitemid = (String) m.get("workitemid");
		affixname = (String) m.get("affixname");
		affixfullurl = (String) m.get("affixfullurl");
		outsystem = (String) m.get("outsystem");
		List<String> list = new ArrayList<String>(); 
		
		String strN = "ID,IFCNAME,FNNAME,FNTYPE,NOWTIME";
		String strV = "UP_IFC_RCV_EVE_SEQ.nextval,'" + interface_class + "','"
				+ interface_method + "','" + one.attributeValue("fntype")+ "','" + getOrderTime() + "'";

		List three = one.elements();
		
		for (int i = 0; i < three.size(); i++) {

			String tmpVdb = ((Element) three.get(i)).attributeValue("dbf");

			String arf = ((Element) three.get(i)).attributeValue("arftype");
			strN += "," + tmpVdb;
			strV += ",?";

			if ("workitemid".equals(tmpVdb)) {
				workitemid = (String)m.get("workitemid");
			}
			if ("outsystem".equals(tmpVdb)) {
				outsystem = (String)m.get("outsystem");
			}
			
			System.out.println("@@@Axis@@@ :"+"**********1******");
			
			if ("affixName".equals(tmpVdb)) {
				affixname = (String)m.get("affixname");
				System.out.println("@@@Axis@@@ :"+"if (S256_3.equals(tmpVdb)) S256_3:::"+affixname);
				att_name_list = affixname;
				System.out.println("@@@Axis@@@ :"+"if (S256_3.equals(tmpVdb)) att_name_list:::"+att_name_list);
				m.put("affixName", att_name_list);
			}
			
			if ("ATTACH".equals(arf)) {

				System.out.println("@@@Axis@@@ :deal ATTACH******");

				affixfullurl = (String)m.get("affixFullUrl");
				if (affixfullurl == null || affixfullurl.equals("")) {
					m.put("affixFullUrl","");
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

						m.put("affixFullUrl",att2);

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
						m.put("affixFullUrl",att4);
					}
					System.out.println("@@@Axis@@@ :"+"**********h7******");
				}
				System.out.println("@@@Axis@@@ :"+"**********3******");
			}
			
			list.add(StringUtil.checkNull((String)m.get(tmpVdb)));

		}
			

		try {

			Vector vc = getserialNo(workitemid, interface_class);

			if ("CREATE".equals(one.attributeValue("fntype"))) {

				if (vc.size() > 0 && "csgz_gt".equals(outsystem)) {

					System.out.println("@@@Axis@@@ :"+"数据库中有重复流水号，无法进行"+interface_method+"操作！！！");

					throw new Exception("数据库中有重复流水号，无法进行"+interface_method+"操作！！！");
					
				}
				System.out.println("@@@Axis@@@ :"+"**********h8******");
			}
			
			
			//2010-1-19 Begin
			String sqll = "insert into UP_IFC_RCV_EVE(" + strN 
			+ ") values(" + strV + ")";
			System.out.println("@@@Axis@@@ :"+"**********h10******");
			this.setConnName(ZMCC.zmccDBConn);
			this.prepare(sqll);
			System.out.println("@@@Axis@@@ :"+sqll);
			String sql2=sqll;
			for (int i = 0; i < list.size(); i++) {
				System.out.println("@@@Axis@@@ :"+"AllEventCreate.class index::::");
				System.out.println("@@@Axis@@@ :"+"AllEventCreate.class "+(i+1)+"value:"+list.get(i));
				this.setObject(i + 1, list.get(i));	
				
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

	public Vector getserialNo(String workItemId, String interface_class) {

		Vector vc = new Vector();

		String sql = " select workItemId from UP_IFC_RCV_EVE where workItemId = '" + workItemId
				+ "' and IFCNAME= '" + interface_class + "' and FNTYPE='CREATE'";
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


	public Vector getFNTimes(String workItemId, String interface_class, String interface_method) {

		Vector vc = new Vector();

		String sql = " select workItemId from UP_IFC_RCV_EVE where workItemId = '" + workItemId
				+ "' and IFCNAME= '" + interface_class + "' and FNNAME='"+interface_method+"'";
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
