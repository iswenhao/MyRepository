package com.ultra.dbConn;
import java.sql.*;

public class JdbcTestOra2 {

public static void main(String args[]) {
    try {
      //װ����������
      Class.forName("oracle.jdbc.driver.OracleDriver");
      
      //�������ݿ�����
      Connection con = DriverManager.getConnection("jdbc:oracle:thin:@soyo-PC:1521:arsystem","aradminbam","aradminbam");
      
      //����SQL�������
      Statement st = con.createStatement();
      
      //ִ��SQL��䣬����ִ�н��
      int rs = st.executeUpdate("select count(*) from up_ifc_rcv t where t.ifcname='cs'");
            
      //������
      System.out.println("��ѯ��¼����"+rs);
      
      //�ͷ����ݿ���Դ��ע��˳��
      st.close();
      con.close();            
    }
    catch (Exception e) {
      e.printStackTrace(System.out);
    }
}
}