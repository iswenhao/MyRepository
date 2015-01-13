package com.ultra.interfac;

public class MyFtpClient extends sun.net.ftp.FtpClient
{
	public int MyIssueCommand(java.lang.String cmd) throws java.io.IOException
	{
		int iRet = -1;
		try
		{
			iRet = issueCommand(cmd);
		}
		catch (java.io.IOException e)
		{
			throw e;
		}
		return iRet;
	}
}

