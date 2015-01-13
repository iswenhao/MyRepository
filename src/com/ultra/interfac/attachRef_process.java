package com.ultra.interfac;

import java.io.File;
import java.io.FileOutputStream;

import org.omg.IOP.Encoding;

import sun.net.TelnetInputStream;
public class attachRef_process {
	
	public String Str_get_filename="";

	public String[] create_FTPstr(String attachRef,String sDownLocFolder) throws Exception{
		String[] urldate=null;
		int nCouter = 0;
		int nPos = 0;
		
		nPos = attachRef.indexOf("<attachInfo>");
		while (nPos > -1)
		{
			nCouter += 1;
			nPos = attachRef.indexOf("<attachInfo>", nPos + 12);
		}
		if (nCouter > 0)
		{
			nPos = attachRef.indexOf("<attachInfo>");
			String tmpAttRef = attachRef.substring(nPos, attachRef.lastIndexOf("</attachInfo>") + 13);
			System.out.print(tmpAttRef);
			//String arrAttName[] = new String[nCouter];
			String arrAttUrl[] = new String[nCouter];
			//String arrAttLength[] = new String[nCouter];
			for (int i = 0; i < nCouter; i ++)
			{
				//nPos = tmpAttRef.indexOf("<attachName>") + 12;
				//arrAttName[i] = tmpAttRef.substring(nPos, tmpAttRef.indexOf("</attachName>"));
				nPos = tmpAttRef.indexOf("<attachURL>") + 11;
				arrAttUrl[i] = tmpAttRef.substring(nPos, tmpAttRef.indexOf("</attachURL>"));
				//nPos = tmpAttRef.indexOf("<attachLength>") + 14;
				//arrAttLength[i] = tmpAttRef.substring(nPos, tmpAttRef.indexOf("</attachLength>"));
				tmpAttRef = tmpAttRef.substring(tmpAttRef.indexOf("</attachInfo>") + 13);
			}
			
			urldate=DealAttachment(arrAttUrl,sDownLocFolder);
			
		}
		
		return urldate;
		
		
	}
	
		public String[] GetFtp2Loc(String sAttFtpFile[],String sDownLocFolder) throws Exception
		
		{
			
			String KF_FTP_ADDR = "";
			String KF_FTP_UN = "";
			String KF_FTP_PW = "";
			int KF_FTP_PORT = 21;
			randomKey re = new randomKey();
			String random_num = re.randomNUM();
			String sAttLoc[] = new String[sAttFtpFile.length];

			// Deal Address String And Get Parameters
			// It May Like "ftp://administrator:@hu@10.70.189.57:21/ocm/message.jsp"
			int tmpPos = sAttFtpFile[0].indexOf("ftp://");
			int tmpEndPos = 0;
			if (tmpPos > -1)
			{
				tmpPos += 6;
				tmpEndPos = sAttFtpFile[0].indexOf("/", tmpPos);
				java.lang.String tmpStr = sAttFtpFile[0].substring(tmpPos, tmpEndPos);
				tmpPos = tmpStr.lastIndexOf("@");
				if (tmpPos > -1)
				{
					KF_FTP_ADDR = tmpStr.substring(tmpPos + 1);
					tmpStr = tmpStr.substring(0, tmpPos);
					tmpPos = tmpStr.indexOf(":");
					if (tmpPos > -1)
					{
						KF_FTP_UN = tmpStr.substring(0, tmpPos);
						KF_FTP_PW = tmpStr.substring(tmpPos + 1);
					}
					else
					{
						KF_FTP_UN = tmpStr;
					}
				}
				else
				{
					KF_FTP_ADDR = tmpStr;
				}
				
				tmpPos = KF_FTP_ADDR.indexOf(":");
				
				if (tmpPos > -1) {
					
					KF_FTP_ADDR = KF_FTP_ADDR.substring(0, tmpPos);
					//KF_FTP_PORT = Integer.parseInt(KF_FTP_ADDR.substring(tmpPos+1));
				}
			}
			else
			{
				return sAttFtpFile;
			}

			// Connect And Get Files From Ftp Server
			MyFtpClient ftpClt = new MyFtpClient();
			try
			{
				ftpClt.openServer(KF_FTP_ADDR, KF_FTP_PORT);
				ftpClt.login(KF_FTP_UN, KF_FTP_PW);
			}
			catch (java.lang.Exception e)
			{
				throw e;
			}
			ftpClt.binary();
			byte[] byteBuffer = new byte[10240];
			int byteCounter = 0;
			for (int i = 0; i < sAttFtpFile.length; i ++)
				
			{
				sAttFtpFile[i] = sAttFtpFile[i].substring(tmpEndPos);				// Now File Path Should Like "/ftpPath/fileName"
				
				tmpPos = sAttFtpFile[i].lastIndexOf("/");
				
				String sFtpPath = sAttFtpFile[i].substring(0, tmpPos);	// ftpPath
				
				sAttFtpFile[i] = FTP_ZH.toCHN5(sAttFtpFile[i].substring(tmpPos + 1));// fileName
				
				
				Str_get_filename = sAttFtpFile[i];
				
				System.out.println("filename:"+Str_get_filename);
				
				
				
				
				sAttLoc[i] = sDownLocFolder + sAttFtpFile[i];
				System.out.println("path:"+sFtpPath);
				System.out.println("file:"+sAttFtpFile[i]);
				ftpClt.cd(sFtpPath);

				
				
				TelnetInputStream is = ftpClt.get(sAttFtpFile[i]);
				if(i>=1){
					for(int j=0;j<i;j++){
						
				sAttLoc[i] = sDownLocFolder + random_num+j;
				
					}
				
				}else{
				sAttLoc[i] = sDownLocFolder + random_num;
				}
				File fOut = new File(sAttLoc[i]);
				
				System.out.println("fOut:"+fOut);
				FileOutputStream os = new FileOutputStream(fOut);
				
				
				
				while ((byteCounter = is.read(byteBuffer)) > -1)
				{
					os.write(byteBuffer, 0, byteCounter);
				}
				is.close();
				os.close();
			}
			try
			{
				ftpClt.closeServer();
			}
			catch (java.lang.Exception e)
			{}

			return sAttLoc;
		}
		
		   public String arrAttNamelist(){
			   
			return Str_get_filename;
			
		}
		
		public String[] DealAttachment(String sAttLoc[],String sDownLocFolder)throws Exception
		
		{

				String sAttLocNew[] = GetFtp2Loc(sAttLoc,sDownLocFolder);
				return sAttLocNew;
		}
	
	
}
