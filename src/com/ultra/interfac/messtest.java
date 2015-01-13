package com.ultra.interfac;

public class messtest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		  String xx=null;
		
		String p = "abcdad";
		String[] strs = p.split("c");
		
		if(strs.length!=0) {
		for(int i=0;i<strs.length;i++){	
			
	      xx = strs[i];
		
		
		System.out.println(xx);
		}
		}


	}
}
