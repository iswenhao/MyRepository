
/**
* Title:ZMCC.java
* Description: ���ݿ����ӹ�����
* Copyright: Copyright (c) 2008
* Company: ultrapower
* Author : tiandong
* Version 1.0
* Date:2008-3-20
 */

package com.ultra.dbConn;

import java.util.Vector;

import com.ultra.dbConn.DBOperation;

public class ZMCC {
	
	 /**
     * ���ӹ���
     */
	public static String zmccDBConn="zmcc";
    
	/**
	 * �����ַ�ת������
	 * @param strvalue
	 * @return
	 */
	public static String toCHN(String strvalue)
	   {
	         try{
	             if(strvalue==null)
	                return null;
	             else
	             {
	                String s = new String(strvalue.getBytes("iso-8859-1"), "gb2312");
	                
	                return s;
	         }
	         }catch(Exception e){
	               return null;
	         }
	   }
	
	
}
