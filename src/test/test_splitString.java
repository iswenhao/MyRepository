package test;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;

public class test_splitString {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String txtfile2 = "C:/Users/Administrator/Desktop/�ʼ���������/temp_2010��11��18��9��-11��/str2.txt";
		BufferedReader in2 = null;
		in2 = new BufferedReader(new FileReader(txtfile2));
		String str1 = "", str2 = "";
		String[] str3;
		int j = 0;
		while (true) {
			j++;
			str2 = in2.readLine();
			//System.out.println(j+"---"+str2+"::txtfile2");
			if(str2.equals("quit"))
			{
			}else{
//			�ָ��ַ���
			str3 = str2.split("ID: ");
			str2 = str3[1];
			System.out.println(str2);
			str3 = str2.split(">");
			str2 = str3[0];
			str2 = str2+">";
			System.out.println(str2);
			}
			
	}

}
}
