package com.ultra.interfac;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Vector;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import com.ultra.dbConn.*;

public class KF_creater_bak20100111 extends DBOperation {
	
	private String xmlPath =null;
	String[] att_list =null;
	String att_name_list ="";
	attachRef_process attp = new attachRef_process();
	public KF_creater_bak20100111(String xmlPath) {
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
		
		
		String strN = "ID,IFCNAME,FNNAME,FNTYPE";
		String strV = "SEQ_UP_IFC_RCV_ID.nextval,'" + interface_class + "','"
				+ interface_method + "','" + one.attributeValue("fntype")+ "'";

		List three = one.elements();
		
		for (int i = 0; i < creat_KFvalue.length; i++) {

			String tmpVdb = ((Element) three.get(i)).attributeValue("dbf");

			String arf = ((Element) three.get(i)).attributeValue("arftype");
			strN += "," + tmpVdb;
			strV += ",?";

			if ("S32_2".equals(tmpVdb)) {

				S32_2 = creat_KFvalue[i];
			}
			
			System.out.println("**********1******");
			
			if ("S256_3".equals(tmpVdb)) {

				S256_3 = creat_KFvalue[i];
				
				att_name_list = S256_3;
				
				creat_KFvalue[i] = att_name_list;
				
			}
			
			
			System.out.println(att_name_list);	
			
					if("ATTACH".equals(arf)){
						
						System.out.println("********hhhhhhhh******");	
						
						S2048_3 = creat_KFvalue[i];
						
		
								if(S2048_3==null||S2048_3.equals("")){
									
									creat_KFvalue[i]="";
									
									System.out.println("**********2******");	
									
								}else{
									
									
									System.out.println("**********333kkk******");
									
										att_list = attp.create_FTPstr(S2048_3, f);
											
										
										
										
										
											if(att_list!=null&&att_list.length==1){
												
												System.out.println("**********h1******"+att_name_list);
												
												 att0 = att_name_list+"^";
												 att1 = att_list[0];
												 att2 = att0+att1;
												 
											System.out.println(att2);
											
											creat_KFvalue[i] = att2;
											
											  System.out.println("**********h2******");
											}
											System.out.println("**********h3******");
											if(att_list!=null&&att_list.length>1){
												System.out.println("**********h4******");
												int idex = att_name_list.indexOf("#");
												
												String str1 = att_name_list.substring(0, idex);
												
												 att0 = str1+"^";
												 att1 = att_list[0]+";";
												 att2 = att0+att1;
												 System.out.println("1----------"+att2);
												 
												 String str2 = att_name_list.substring(idex+1);
												 System.out.println("2----------"+str2);
												 att3 = att_list[1];
												 att4 = att2+str2+"^"+att3;
											
												 System.out.println("**********h6******");
											System.out.println(att4);
											
											creat_KFvalue[i] = att4;
											
											}
											System.out.println("**********h7******");	
								     }
								
								System.out.println("**********3******");			
					}
							
			
		}
			

		try {

			Vector vc = getserialNo(S32_2, interface_class);

			if ("CREATE".equals(one.attributeValue("fntype"))) {

				if (vc.size() > 0) {

					System.out.println("数据库中有重复流水号，无法进行"+interface_method+"操作！！！");

					throw new Exception("数据库中有重复流水号，无法进行"+interface_method+"操作！！！");
					
				}
				System.out.println("**********h8******");
			} else {

				if (vc.size() == 0) {

					System.out.println("无法进行"+interface_method+"操作，请确认有此流水号的信息存在！！！");

					throw new Exception("无法进行"+interface_method+"操作，请确认有此流水号的信息存在！！！");

				} else {
					// 判断modify的方法是否可以被调用多次
					Vector vv = getFNTimes(S32_2, interface_class,interface_method);
					if ("SINGLE".equals(one.attributeValue("fntimes")) && vv.size()>0) {

						System.out.println("无法进行"+interface_method+"操作，请确认有此操作是否已经执行！！！");

						throw new Exception("无法进行"+interface_method+"操作，请确认有此操作是否已经执行！！！");
						
					}
				}
				System.out.println("**********h9******");
			}
			
			
			String sqll = "insert into UP_IFC_RCV(" + strN 
			+ ") values(" + strV + ")";
			System.out.println("**********h10******");
			this.setConnName(ZMCC.zmccDBConn);
			this.prepare(sqll);
			System.out.println(sqll);
//			for (int i = 0; i < creat_KFvalue.length; i++) {
//				System.out.println(creat_KFvalue[i]+"@@@@@@@@");
//				this.setString(i + 1, creat_KFvalue[i]);
//
//			}
			String sql2=sqll;
			for (int i = 0; i < creat_KFvalue.length; i++) {
				System.out.println("KF_creater.class index::::");
				System.out.println(i+1);
				System.out.println("KF_creater.class creat_KFvalue[i]:"+creat_KFvalue[i]);
				
				sql2=sql2.replaceFirst("\\?", "'"+creat_KFvalue[i]+"'");
				
				this.setCharacterStream(i + 1, creat_KFvalue[i]);
/*        		if(S2048_1==i || S2048_2==i){
        			System.out.println("setCharacterStream:"+creat_KFvalue[i]);
        			this.setCharacterStream(i + 1, creat_KFvalue[i]);
        		}else{
        			System.out.println("setStream:"+creat_KFvalue[i]);
        			this.setString(i + 1, creat_KFvalue[i]);
        		}	*/	
			}
			System.out.println("real SQL: "+sql2);
			int charu = this.executeUpdate();
			System.out.println(charu + " values into sucess！");
			System.out.println("Now Datetime Is " + getOrderTime());

			System.out.println("**********h11******");

		} catch (Exception e) {

			e.printStackTrace();
			throw e;

		}
		System.out.println("**********6******");	
		return "ok";
	}

	public Vector getserialNo(String S32_2, String interface_class) {

		Vector vc = new Vector();

		String sql = " select S32_2 from UP_IFC_RCV where S32_2 = '" + S32_2
				+ "' and IFCNAME= '" + interface_class + "' and FNTYPE='CREATE'";
		try {

			this.setConnName(ZMCC.zmccDBConn);
			this.prepare(sql);
			System.out.println(sql);
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
			System.out.println(sql);
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
