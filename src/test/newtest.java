package test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class newtest {

	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        String month2Check =null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MONTH, -2);
		month2Check = sdf.format(calendar.getTime());
		
		int r = 3;
		int rr = 4;
		
		int rrr = 5;
		
		int sum=0;
		
		sum = r+rr-rrr;
		System.out.print(month2Check+sum);

	}

}
