package com.ultra.dbConn;
import java.sql.*;

public class JdbcTestOra2 {

public static void main(String args[]) {
    try {
      //装载驱动程序
      Class.forName("oracle.jdbc.driver.OracleDriver");
      
      //建立数据库连接
      Connection con = DriverManager.getConnection("jdbc:oracle:thin:@soyo-PC:1521:arsystem","aradminbam","aradminbam");
      
      //创建SQL语句载体
      Statement st = con.createStatement();
      
      //执行SQL语句，接收执行结果
      int rs = st.executeUpdate("select count(*) from up_ifc_rcv t where t.ifcname='cs'");
            
      //处理结果
      System.out.println("查询记录数："+rs);
      
      //释放数据库资源，注意顺序
      st.close();
      con.close();            
    }
    catch (Exception e) {
      e.printStackTrace(System.out);
    }
}
}