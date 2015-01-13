package com.ultra.interfac;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;

import sun.net.TelnetInputStream;

public class test001 {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
//		// TODO Auto-generated method stub
//		KF_creater fc = new KF_creater("D:\\");
//		//String creat_KFname[]={"FLOWNO","S32_1","S32_2","S32_3","S32_4","S32_5","S128_1","S128_2","S128_3","S128_4","S128_5","S2048_1"};
//		String creat_KFvalue[]={"1010101109","1","2","3","4","5","5","4","3","2","1","<attachRef>" +
//				"<attachInfo>" +
//				"<attachName>附件1</attachName>" +
//				"<attachURL>ftp://root:root@10.18.19.48:21/csp/附件1.doc</attachURL>" +
//				"<attachLength>500</attachLength>" +
//				"</attachInfo>" +
//				"<attachInfo>" +
//				"<attachName>附件2</attachName>" +
//				"<attachURL>ftp://root:root@10.18.19.48:21/csp/附件2.doc</attachURL>" +
//				"<attachLength>2010</attachLength>" +
//				"</attachInfo>" +
//				"</attachRef>"};
//		fc.InsertData("cs", "createProcess",creat_KFvalue);
//////		
////	

//
//		
//		String[]att_name_list={"1","2","3"};
//		String[] att_list={"d:\\abd\\jkle.txt",
//				           "d:\\abd\\jool.txt",
//				           "d:\\abd\\jkes.txt"};
//		String att1="";
//		String att2="";
//		String att0="";
//		
//		for(int j=0;j<att_list.length;j++){
//			
//			 att0 = att_name_list[j]+"^";
//			 att1 = att_list[j]+";";
//			 att2 += att0+att1;
//			 
//		}
//         System.out.print(att2);
		String sAttFtpFile[] ={"ftp://administrator:@hu@10.70.189.57:21/ocm/message.jsp"};
		String KF_FTP_ADDR = "";
		String KF_FTP_UN = "";
		String KF_FTP_PW = "";
		int KF_FTP_PORT = 21;
		String random_num="";
		String sAttLoc[] = new String[sAttFtpFile.length];

		// Deal Address String And Get Parameters
		// It May Like "ftp://administrator:@hu@10.70.189.57:21/ocm/message.jsp"
		int tmpPos = sAttFtpFile[0].indexOf("ftp://");
		
		System.out.println("1========"+tmpPos);
		int tmpEndPos = 0;
		if (tmpPos > -1)
		{
			tmpPos += 6;
			tmpEndPos = sAttFtpFile[0].indexOf("/", tmpPos);
			
			System.out.println("2====="+tmpEndPos);
			
			java.lang.String tmpStr = sAttFtpFile[0].substring(tmpPos, tmpEndPos);
			
			System.out.println("********"+tmpStr+"*******");
			
			tmpPos = tmpStr.lastIndexOf("@");
			
			System.out.println("3====="+tmpPos);
			
			if (tmpPos > -1)
			{
				KF_FTP_ADDR = tmpStr.substring(tmpPos + 1);
				
				System.out.println("4====="+KF_FTP_ADDR);
				
				
				tmpStr = tmpStr.substring(0, tmpPos);
				
				System.out.println("5====="+tmpStr);
				
				tmpPos = tmpStr.indexOf(":");
				
				System.out.println("6====="+tmpPos);
				
				
				if (tmpPos > -1)
				{
					KF_FTP_UN = tmpStr.substring(0, tmpPos);
					
					System.out.println("7====="+KF_FTP_UN);
					
					KF_FTP_PW = tmpStr.substring(tmpPos + 1);
					
					System.out.println("8====="+KF_FTP_PW);
					
				}
				else
				{
					KF_FTP_UN = tmpStr;
				}
			}
			else
			{
				KF_FTP_ADDR = tmpStr;
				
				System.out.println("KF_FTP_ADDR=="+KF_FTP_ADDR);
			}
			tmpPos = KF_FTP_ADDR.indexOf(":");
			
			System.out.println("9====="+tmpPos);
			
			if (tmpPos > -1) {
				
				KF_FTP_ADDR = KF_FTP_ADDR.substring(0, tmpPos);
				
				System.out.println("10====="+KF_FTP_ADDR);
				
				//KF_FTP_PORT = Integer.parseInt(KF_FTP_ADDR.substring(tmpPos+1));
				
				//System.out.println("11====="+KF_FTP_PORT);
			}
		}
		else
		{
			
		}
		
	}
		
		
	}


