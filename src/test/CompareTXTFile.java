package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.Charset;

public class CompareTXTFile {

	private String txtfile1 = "C:/Users/Administrator/Desktop/�ʼ���������/temp_2010��11��17��10��/str2.txt";
	private String txtfile2 = "C:/Users/Administrator/Desktop/�ʼ���������/temp_2010��11��17��10��/str1.txt";
	private String txtfile1_encode = "UTF-8";
	private String txtfile2_encode = "UTF-8";

	// private String txtfile1 =
	// "C:/Users/Administrator/Desktop/�ʼ���������/temp_2010��11��16��9��/1.txt";
	// private String txtfile2 =
	// "C:/Users/Administrator/Desktop/�ʼ���������/temp_2010��11��16��9��/2.txt";

	public CompareTXTFile() {
		try {
			run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void run() throws Exception {
		BufferedReader in1 = new BufferedReader(new FileReader(txtfile1));
		// BufferedReader in2 = new BufferedReader(new FileReader(txtfile2));
		String str1 = "", str2 = "";
		String[] str3;
		int i = 0;
		int j = 0;
		int jj = 0;
		String kk = "";
		while (true) {
			i++;
			str1 = in1.readLine();
			// System.out.println(i+"---"+str1+"::txtfile1");
			BufferedReader in2 = new BufferedReader(new FileReader(txtfile2));
			if (str1.equals("quit")) {
				// System.out.println("str1 over");
				break;
			}
			kk = "false";
			jj = 1;
			while (true) {
				j++;
				str2 = in2.readLine();
				//System.out.println(j+"---"+str2+"::txtfile2");
//				if(!str2.equals("quit"))
//				{	
////				�ָ��ַ���
//				str3 = str2.split("ID: ");
//				str2 = str3[1];
//				str3 = str2.split(">");
//				str2 = str3[0]+">";
//				System.out.println(str2);
////				
//				}
				
				if (str1 == null || str2 == null) {
					break;
				}
				str1 = new String(str1.getBytes());
				str2 = new String(str2.getBytes());
				if (str1.equals(str2)) {
					jj++;
					if (jj > 2) {
						System.out.println("str1��" + i + "��, ��str2:" + j + "�ظ���");
					} else {
						// System.out.println("str1��" + i + "��, ��str2:" + j +"���ڣ�");
					}
					kk = "ture";
				}
				if (str2.equals("quit")) {
					j = 0;
					break;
				}
			}
			
			if (kk.equals("false")) {
				System.out.println("str1��" + i + "��, ��str2�в����ڣ�");
			}
		}
	}

	public static void main(String[] args) {
		new CompareTXTFile();
	}
}