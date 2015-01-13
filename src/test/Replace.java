package test;

public class Replace {
	
	
	//str 为原字附串   pattern 为要替换的字符串  replace是要替换成的字符串。
	public static String Replace(String str, String pattern, String replace) {
        int s = 0;
        int e = 0;
        StringBuffer result = new StringBuffer();
        while ((e = str.indexOf(pattern, s)) >= 0) {
            result.append(str.substring(s, e));
            result.append(replace);
            s = e+pattern.length();
        }
        result.append(str.substring(s));
        return result.toString();
    }
	
	public static void main(String[] args){
		String s ="\\测试测测1234!@\\#$%^&^%*)(#$^&_+&^#$:JHGFDE{!@#$%^&*()}[]|/WOP|YREWT><>?>:'CMNNMCXYTRTUI567890！·#/￥%……——*（）!@#\\$%^&*()测测'测测测测测测测测测测测测测测测测是的这不是士大夫革测试";
		Replace r = new Replace();
		String ss = r.Replace(s, "$", "\\\\$");
		System.out.println(ss);
	}
}
