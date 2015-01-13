package com.ultra.interfac;

public class randomKey{

public String randomNUM() {
	
	String base;
	String temp;
	int i;
	int p;

	base = "1234567890";
	temp = "";
	for (i = 1; i < 20; i++)
	{
	p = (int) (Math.random() * 10);
	temp += base.substring(p, p + 1);
	}
	return (temp);
	
}
}