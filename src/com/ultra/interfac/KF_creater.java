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

public class KF_creater extends DBOperation {
	
	private String xmlPath =null;
	String[] att_list =null;
	String att_name_list ="";
	attachRef_process attp = new attachRef_process();
	public KF_creater(String xmlPath) {
		this.xmlPath = xmlPath;
	}

	public String InsertData(String interface_class, String interface_method,
			String[] creat_KFvalue) throws Exception {
		
		if (creat_KFvalue == null || creat_KFvalue.length == 0) {
			throw new Exception("parameters error !");
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
		String att0="";
		String att1="";
		String att2="";
		String att3="";
		String att4="";
		String S32_2 = "";
		String S256_3 = "";
		String S2048_3 = "";
		
		
		String strN = "ID,IFCNAME,FNNAME,FNTYPE,NOWTIME";
		String strV = "SEQ_UP_IFC_RCV_ID.nextval,'" + interface_class + "','"
				+ interface_method + "','" + one.attributeValue("fntype")+ "','" + getOrderTime() + "'";

		List three = one.elements();
		
		for (int i = 0; i < creat_KFvalue.length; i++) {

			String tmpVdb = ((Element) three.get(i)).attributeValue("dbf");

			String arf = ((Element) three.get(i)).attributeValue("arftype");
			strN += "," + tmpVdb;
			strV += ",?";

			if ("S32_2".equals(tmpVdb)) {

				S32_2 = creat_KFvalue[i];
			}
			
			System.out.println("@@@Axis@@@ :"+"**********1******");
			
			if ("S256_3".equals(tmpVdb)) {

				S256_3 = creat_KFvalue[i];
				
				System.out.println("@@@Axis@@@ :"+"if (S256_3.equals(tmpVdb)) S256_3:::"+S256_3);
				
				att_name_list = S256_3;
				System.out.println("@@@Axis@@@ :"+"if (S256_3.equals(tmpVdb)) att_name_list:::"+att_name_list);
				
				
				creat_KFvalue[i] = att_name_list;
				System.out.println("@@@Axis@@@ :"+"if (S256_3.equals(tmpVdb)) att_name_list:::"+att_name_list);
				
				
			}
			
			
			// System.out.println("@@@Axis@@@ :"+att_name_list);

			if ("ATTACH".equals(arf)) {

				System.out
						.println("********@@@@@@@@@@@@@@@@@@deal ATTACH!!!!hhhhhhhh******");

				S2048_3 = creat_KFvalue[i];

				if (S2048_3 == null || S2048_3.equals("")) {

					creat_KFvalue[i] = "";

					System.out
							.println("**********2::creat_KFvalue[i] is empty******");

				} else {

					System.out
							.println("**********333kkk::;S2048_3 is not empty******");

					System.out
							.println("**********soyoleo******att_name_list is attachname1111:::"
									+ att_name_list);

					att_list = attp.create_FTPstr(S2048_3, f);

					System.out
							.println("**********soyoleo******att_name_list is attachname2222:::"
									+ att_name_list);

					if (att_list != null && att_list.length == 1) {

						System.out
								.println("**********h1******" + att_name_list);

						att0 = att_name_list + "^";
						att1 = att_list[0];
						att2 = att0 + att1;

						System.out.println("@@@Axis@@@ :"+" att2=att0+att1;;;att2:::" + att2);

						creat_KFvalue[i] = att2;

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

						creat_KFvalue[i] = att4;

					}
					System.out.println("@@@Axis@@@ :"+"**********h7******");
				}

				System.out.println("@@@Axis@@@ :"+"**********3******");
			}

		}
			

		try {

			Vector vc = getserialNo(S32_2, interface_class);

			if ("CREATE".equals(one.attributeValue("fntype"))) {

				if (vc.size() > 0) {

					System.out.println("@@@Axis@@@ :"+"数据库中有重复流水号，无法进行"+interface_method+"操作！！！");

					throw new Exception("数据库中有重复流水号，无法进行"+interface_method+"操作！！！");
					
				}
				System.out.println("@@@Axis@@@ :"+"**********h8******");
			} else {

				if (vc.size() == 0) {

					System.out.println("@@@Axis@@@ :"+"无法进行"+interface_method+"操作，请确认有此流水号的信息存在！！！");

					throw new Exception("无法进行"+interface_method+"操作，请确认有此流水号的信息存在！！！");

				} else {
					Vector vv = getFNTimes(S32_2, interface_class,interface_method);
					if ("SINGLE".equals(one.attributeValue("fntimes")) && vv.size()>0) {

						System.out.println("@@@Axis@@@ :"+"无法进行"+interface_method+"操作，请确认有此操作是否已经执行！！！");

						throw new Exception("无法进行"+interface_method+"操作，请确认有此操作是否已经执行！！！");
						
					}
				}
				System.out.println("@@@Axis@@@ :"+"**********h9******");
			}
			
			
			//2010-1-19 Begin
			String sqll = "insert into UP_IFC_RCV(" + strN 
			+ ") values(" + strV + ")";
			System.out.println("@@@Axis@@@ :"+"**********h10******");
			this.setConnName(ZMCC.zmccDBConn);
			this.prepare(sqll);
			System.out.println("@@@Axis@@@ :"+sqll);
			String sql2=sqll;
			String value;
			for (int i = 0; i < creat_KFvalue.length; i++) {
				System.out.println("@@@Axis@@@ :"+"KF_creater.class index::::");
				System.out.println("@@@Axis@@@ :"+i+1);
				System.out.println("@@@Axis@@@ :"+"KF_creater.class creat_KFvalue[i]:"+creat_KFvalue[i]);
				value=creat_KFvalue[i];
				this.setObject(i + 1, creat_KFvalue[i]);	
				
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

	public Vector getserialNo(String S32_2, String interface_class) {

		Vector vc = new Vector();

		String sql = " select S32_2 from UP_IFC_RCV where S32_2 = '" + S32_2
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


	public Vector getFNTimes(String S32_2, String interface_class, String interface_method) {

		Vector vc = new Vector();

		String sql = " select S32_2 from UP_IFC_RCV where S32_2 = '" + S32_2
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
