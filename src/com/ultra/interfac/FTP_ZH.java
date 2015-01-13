
/**
* Title:ZMCC.java
* Description: 数据库联接管理类
* Copyright: Copyright (c) 2008
* Company: ultrapower
* Author : tiandong
* Version 1.0
* Date:2008-3-20
 */

package com.ultra.interfac;

import java.util.Vector;
public class FTP_ZH {
	/**
	 * 中文字符转换程序
	 * @param strvalue
	 * @return
	 */
	public static String toCHN1(String strvalue)
	   {
	         try{
	             if(strvalue==null)
	                return null;
	             else
	             {
	                String s = new String(strvalue.getBytes("GB2312"), "GB2312");
	                
	                return s;
	                
	         }
	         }catch(Exception e){
	               return null;
	         }
	   }
	
	public static String toCHN2(String strvalue)
	   {
	         try{
	             if(strvalue==null)
	                return null;
	             else
	             {
	                String s = new String(strvalue.getBytes("GB2312"), "UTF-8");
	                
	                return s;
	                
	         }
	         }catch(Exception e){
	               return null;
	         }
	   }
	public static String toCHN3(String strvalue)
	   {
	         try{
	             if(strvalue==null)
	                return null;
	             else
	             {
	                String s = new String(strvalue.getBytes("ISO-8859-1"), "UTF-8");
	                
	                return s;
	                
	         }
	         }catch(Exception e){
	               return null;
	         }
	   }
	public static String toCHN4(String strvalue)
	   {
	         try{
	             if(strvalue==null)
	                return null;
	             else
	             {
	                String s = new String(strvalue.getBytes("UTF-8"), "GB2312");
	                
	                return s;
	                
	         }
	         }catch(Exception e){
	               return null;
	         }
	   }
	public static String toCHN5(String strvalue)
	   {
	         try{
	             if(strvalue==null)
	                return null;
	             else
	             {
	                String s = new String(strvalue.getBytes("GB2312"), "ISO-8859-1");
	                
	                return s;
	                
	         }
	         }catch(Exception e){
	               return null;
	         }
	   }
	public static String toCHN6(String strvalue)
	   {
	         try{
	             if(strvalue==null)
	                return null;
	             else
	             {
	                String s = new String(strvalue.getBytes("UTF-8"), "ISO-8859-1");
	                
	                return s;
	                
	         }
	         }catch(Exception e){
	               return null;
	         }
	   }
	public static String toCHN7(String strvalue)
	   {
	         try{
	             if(strvalue==null)
	                return null;
	             else
	             {
	                String s = new String(strvalue.getBytes("UTF-8"), "UTF-8");
	                
	                return s;
	                
	         }
	         }catch(Exception e){
	               return null;
	         }
	   }
	public static String toCHN8(String strvalue)
	   {
	         try{
	             if(strvalue==null)
	                return null;
	             else
	             {
	                String s = new String(strvalue.getBytes("ISO-8859-1"), "ISO-8859-1");
	                
	                return s;
	                
	         }
	         }catch(Exception e){
	               return null;
	         }
	   }
	public static String toCHN9(String strvalue)
	   {
	         try{
	             if(strvalue==null)
	                return null;
	             else
	             {
	                String s = new String(strvalue.getBytes("ISO-8859-1"), "GB2312");
	                
	                return s;
	                
	         }
	         }catch(Exception e){
	               return null;
	         }
	   }
}
