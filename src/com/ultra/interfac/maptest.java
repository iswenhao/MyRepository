package com.ultra.interfac;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class maptest {

	
	
	public static void main(String[] args){
		
		Map map = new HashMap();
		
		map.put("a", "000000");
		map.put("b", "11111");
		map.put("c", "22222");
		
		
		Iterator iterator=map.keySet().iterator(); 
		
		while(iterator.hasNext()){ 
			
		        Object key=iterator.next(); 
		        
		        Object value=map.get(key);
		       
		     System.out.println(key+ "-- "+value); 
		     
		} 

		
		
		
	}
	
}
