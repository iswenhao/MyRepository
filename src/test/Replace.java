package test;

public class Replace {
	
	
	//str Ϊԭ�ָ���   pattern ΪҪ�滻���ַ���  replace��Ҫ�滻�ɵ��ַ�����
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
		String s ="\\���Բ��1234!@\\#$%^&^%*)(#$^&_+&^#$:JHGFDE{!@#$%^&*()}[]|/WOP|YREWT><>?>:'CMNNMCXYTRTUI567890����#/��%��������*����!@#\\$%^&*()���'������������������ǵ��ⲻ��ʿ�������";
		Replace r = new Replace();
		String ss = r.Replace(s, "$", "\\\\$");
		System.out.println(ss);
	}
}
