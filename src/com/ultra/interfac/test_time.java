package com.ultra.interfac;

import java.text.SimpleDateFormat;

public class test_time {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		test_time tf = new test_time();
		
//		String tff=tf.getOrderTime();
		
		System.out.print(getOrderTime());
		
	}

	
	
	public static String getOrderTime() {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		java.util.Date now = new java.util.Date();
		String date = formatter.format(now);
		return date;

	}
}
