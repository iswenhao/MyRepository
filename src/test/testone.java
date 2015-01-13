package test;

import java.text.SimpleDateFormat;

public class testone {

	String hh="11";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		testone t1 = new testone();
		
		String date=t1.getOrderTime2();
		
		// TODO Auto-generated method stub
		Integer ddd=new Integer(Integer.parseInt(date));
		int xdd=ddd.intValue();
		
		System.out.println("xdd="+xdd);

		int s = 10;
		int y = 2;
		
		int c = s/y;
		
		System.out.println(c);
		
	}

	public static String getOrderTime2() {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		java.util.Date now = new java.util.Date();
		String date = formatter.format(now);
		return date;
		
		
		
		

	}
	
}
