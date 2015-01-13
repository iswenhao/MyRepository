/**
* Title: DBOperation.java
* Description: 数据库操作
* Copyright: Copyright (c)
* Company: nercita
* Author : wt
* remark : 加入指针回滚
* Version  1.0
*/
package com.ultra.dbConn;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.net.*;

import org.springframework.web.servlet.ModelAndView;

public class DBOperation {

	private DBConnectionManager connMgr;
	private Statement stmt;
	private PreparedStatement pstmt;
	private Connection conn;
	private ResultSet rs;
    private Vector rsVO;
    private String connName;
    Vector columnameVO=new Vector();
    private ResultSetMetaData rsmd=null; 
    private String _sql_str; 
    private int _total_records; 
    private int _pages; 
    private int _pagesize; 
    private ResultSet rs_split;

  /**
   * Get db connection.
   *
   * @throws SQLException 当数据库操作出现错误
   * @return Connection对象
   */
  public void setConnName(String var)
  {
	  connName=var;
	  
  }
  
  public Connection getConnection(String name) throws SQLException {
    if (conn != null && !conn.isClosed()) {
      return conn;
    }
    else {
      return newConnection(name);
    }
  }

  /**
   * 从数据库连接池中选择一个新的数据库连接
   *
   * @throws SQLException 当数据库操作出现错误
   * @return Connection对象
   */
  private Connection newConnection(String name) throws SQLException {
	connMgr = DBConnectionManager.getInstance();
	conn = connMgr.getConnection(name);
   //     System.out.println("name="+name);
    if (conn == null) {
      throw new SQLException("从数据库取连接错误! DbConnectionPool is exhausted!");
    }
    return conn;
  }

  /**
   * Get db connection in time.
   *
   * @throws SQLException 当数据库操作出现错误
   * @return Connection对象
   */
  public Connection getConnection(String name, long time) throws SQLException {
    if (conn != null && !conn.isClosed()) {
      return conn;
    }
    else {
      return newConnection(name,time);
    }
  }

  /**
   * 在规定时间从数据库连接池中选择一个新的数据库连接
   *
   * @throws SQLException 当数据库操作出现错误
   * @return Connection对象
   */
  private Connection newConnection(String name, long time) throws SQLException {
	connMgr = DBConnectionManager.getInstance();
	conn = connMgr.getConnection(name,time);
    if (conn == null) {
      throw new SQLException("从数据库取连接错误! DbConnectionPool is exhausted!");
    }
    return conn;
  }

  /**
   * 预编译SQL语句, 创建PreparedStatement对象.
   *
   * @param strSQL SQL语句
   * @throws SQLException 当数据库操作出现错误
   */
  public void prepare(String strSQL) throws SQLException {
    //System.out.println(strSQL);
    if (conn == null || conn.isClosed()) {
      getConnection(connName);

    }
    try {
      if(pstmt != null){
      	pstmt.close();
        pstmt = null;
      }
    }
    catch (Throwable e){
       System.err.println(e.getMessage());
    }

    try {
      pstmt = getConnection(connName).prepareStatement(strSQL,ResultSet.TYPE_SCROLL_INSENSITIVE,
                                                       ResultSet.CONCUR_READ_ONLY);
      //pstmt.setMaxRows(1000);
    }
    catch (SQLException e) {
     // System.out.println("创建PreparedStatement对象出现错误" + e);
      throw e;
    }
  }
  
  //接口开发为处理大字符串存储问题，直接插入数据库中间表
  public void stmtInsert(String strSQL) throws SQLException {
		 System.out.println("DBOperation.java:stmtInsert::interface::insert SQL---::"+strSQL);
		if (conn == null || conn.isClosed()) {
			getConnection(connName);

		}

		try {
			stmt = getConnection(connName).createStatement();
			stmt.execute(strSQL);
			// pstmt.setMaxRows(1000);
		} catch (SQLException e) {
			 System.out.println("stmtInsert(sql)is error" + e);
			throw e;
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception ex) {
			}
		}
	}  
  
  
  public void setPageSize(int var)
  {
	  _pagesize=var;
  }
  public void initialize() 
  {  
 
    try
   { 
       if (pstmt == null) {
    	//  System.out.println("注意：你忘了调用perpare方法了");
    	    throw new SQLException("Not prepared.");
    	 }
       rs_split = pstmt.executeQuery();
      rsmd=rs_split.getMetaData(); 
      if (rs_split!=null) 
      { 
    	  rs_split.last(); 
         this._total_records = rs_split.getRow(); 
         rs_split.first(); 
         this._pages = (this._total_records - 1) / this._pagesize + 1; 
      } 
   } 
   catch(SQLException e)
   {
      //System.out.println(e.toString()); 
   } 
 } 
  public Vector getPage(int ipage)
  {  
	 //initialize();
     Vector vData=new Vector(); 
     int n=ipage; 
     int m=0; 
     m=(n-1)*this._pagesize; 
     try
     { 
        if (rs_split!=null) 
        { 
           if (n!=1) 
           {
        	   rs_split.absolute(m);
           } 
           for(int i=0;i<this._pagesize;i++)
           { 
             Vector sData=new Vector(); 
             for(int j=0;j<rsmd.getColumnCount();j++) 
             { 
               sData.addElement(rs_split.getString(j+1)); 
             } 
             if (sData==null) 
             { 
                break; 
             } 
             vData.addElement(sData); 
             rs_split.next(); 
           } 
        } 
        rs_split.close(); 
        pstmt.close(); 
    } 
    catch(SQLException e)
    {
      // System.out.println(e.toString()); 
    } 
    return vData; 
  } 
  public int getPages() 
  { 
    return this._pages; 
  } 
  public int getTotalRecords() 
  { 
    return this._total_records; 
  }
  /**
   * 执行sql返回结果--两维向量<br>
   * @throws SQLException 当数据库操作出现错误
   * @return  ：查询返回两维Vector[[aaa,bbb]，[ccc,ddd]], （未查到结果时返回[]，程序中
   * 应以（(java.util.Vector）ret).size()判断@return是否为空<br>
   *
   */
  public Vector executeQuery() throws SQLException {
    ResultSet rs = null;
    Vector vResult = null;
    try {
      if (pstmt == null) {
      //  System.out.println("注意：你忘了调用perpare方法了");
        throw new SQLException("Not prepared.");
      }

      rs = pstmt.executeQuery();
      vResult = voRs(rs);
    }
    catch (SQLException e) {
      System.err.println("DBOperation查询返回结果集出错"+e);
      throw e;
    }
    finally {
      try {
        close(connName);
      }
      catch(Exception e){
        System.err.println(e.getMessage());
      }
    }
    return rsVO = vResult;
  }

  /**
   * 查询并关闭连接
   * @param  strSQL sql语句
   * @param  parameter   输入的参数组
   * @throws SQLException 当数据库操作出现错误
   * @return 查询结果
   */
  public Vector executeQuery(String strSQL, Vector parameter) throws SQLException {
    try {
      prepare(strSQL);
      for (int i = 0; i < parameter.size(); i++) {
        setObject(i + 1, parameter.elementAt(i));
      }
      //System.out.println(strSQL);
      return executeQuery();
    }
    catch (SQLException e) {
      throw e;
    }
    finally {
      try {
        close(connName);
      }
      catch(Exception e){
        System.err.println(e.getMessage());
      }
    }
  }

  /**
   * 执行sql返回结果.
   *
   * @throws SQLException 当数据库操作出现错误
   * @return 操作影响的纪录数<br>
   */
  public int executeUpdate() throws SQLException {
    try {
      if (pstmt == null) {
      //  System.out.println("注意：你忘了调用perpare方法了");

      }
      return pstmt.executeUpdate();
    }
    catch (NullPointerException e) {
      return -1;
    }
    finally {
      try {
        close(connName);
      }
      catch(Exception e){
        System.err.println(e.getMessage());
      }
    }
  }

  /**
   * 执行insert或update并释放连接
   * @param  strSQL sql语句
   * @param  parameter   输入的参数组
   * @throws SQLException 当数据库操作出现错误
   * @return 执行结果
   */
  public int executeUpdate(String strSQL, Vector parameter) throws SQLException {
    try {
      prepare(strSQL);
      for (int i = 0; i < parameter.size(); i++) {
        setObject(i + 1, parameter.elementAt(i));
      }
      return executeUpdate();
    }
    catch (SQLException e) {
      throw e;
    }
    finally {
      try {
        close(connName);
      }
      catch(Exception e){
        System.err.println(e.getMessage());
      }
    }
  }



	/**
	*执行事务处理
	*/
	public boolean handleTransaction(Vector SqlArray) throws Exception
	{
		boolean result = false;
		int ArraySize = SqlArray.size();
		try
		{
    		if (conn == null || conn.isClosed()){
    			getConnection(connName);
    		}
			stmt = conn.createStatement();
			conn.setAutoCommit(false);
		//	System.out.println("ArraySize is" +ArraySize);
			for(int i=0;i<ArraySize;i++)
			{
		//		System.out.println(" 开始执行语句"+(String)SqlArray.elementAt(i));
				stmt.executeUpdate((String)SqlArray.elementAt(i));
		//		System.out.println(" 执行成功");
			}
			conn.commit();
			conn.setAutoCommit(true) ;//必须
			System.out.println("事务执行成功");
			result = true;
		}
		catch(java.sql.SQLException e)
		{//System.out.println("trans="+e.getMessage());
			try
			{
			//	System.out.println(e.toString());
			//	System.out.println("数据库操作失败");
				conn.rollback();
			}
			catch(java.sql.SQLException Te)
			{
				System.err.println("事务出错回滚异常");
			}
		}
		try
		{
			conn.setAutoCommit(true);
		}
		catch(java.sql.SQLException e)
		{
			System.err.println("设置自动提交失败");
		}
		//有待改进2006.3.6
        close(connName);
		return result;
    }

	/**
	*释放连接
	*/
	public void close(String name) throws Exception
	{
		try
		{
			if(rs!=null)rs.close();
			if(stmt!=null)stmt.close();
			if(pstmt!=null)pstmt.close();
			if(conn!=null)
			{
				connMgr.freeConnection(name,conn);
				//System.out.println("正在释放一个连接...");

			}
		}
		catch(java.sql.SQLException e)
		{
		System.err.println("释放连接出错");
		}
	}
  /**
   * 把rs存放到vector中
   * @param rs ResultSet
   * @return Vector
   */
  private Vector voRs(java.sql.ResultSet rs) throws SQLException {
    int columnCount = rs.getMetaData().getColumnCount();
    for (int j=1; j < columnCount+1; j++)//ma
        columnameVO.addElement(rs.getMetaData().getColumnName(j));//ma
    Vector vResult = new Vector();
    while (rs.next()) {
      Vector vTemp = new Vector();
      for (int i = 0; i < columnCount; i++) {
        Object oTemp = rs.getObject(i + 1);
        String sTemp = oTemp == null ? "" : oTemp.toString();
        vTemp.addElement(sTemp.trim());
      }
      vResult.addElement(vTemp);
    }
    return vResult;
  }
  /**
   * 得到得到表头值
   *
   * @return 表头值vector 
   */
  public Vector getColumnName() {
	  	 
		return columnameVO; 
	  }
  /**
   * 得到row行, col列的值
   *
   * @param row 行位置. 从0开始
   * @param col 列位置. 从0开始
   * @return row,col所在的字符串值
   */
  public String get(int row, int col) {
    return ( (Vector) rsVO.elementAt(row)).elementAt(col).toString();
  }

  /**
   * 得到一行的值.
   *
   * @param row 行位置
   * @return row所在的整行值.
   */
  public Vector getRow(int row) {
    if (getRowCount() < row) {
      return null;
    }
    return (Vector) rsVO.elementAt(row);
  }

  /**
   * 得到一列的值
   *
   * @param col 列位置
   * @return col列的所有值
   */
  public Vector getColumn(int col) {
    Vector vo = new Vector();

    for (int i = 0; i < getRowCount(); i++) {
      vo.add( ( (Vector) rsVO.elementAt(i)).elementAt(col));
    }
    return vo;

  }

  /**
   * 得到行数
   *
   * @return 行数
   */
  public int getRowCount() {
    return rsVO == null ? 0 : rsVO.size();
  }

  /**
   * 得到列数
   *
   * @return 列数
   */
  public int getColCount() {
    if (getRowCount() == 0) {
      return 0; //如果没有得到纪录返回0
    }
    return ( (Vector) rsVO.elementAt(0)).size();
  }

  /**
   * 为防止数据库连接释放失败在析构时检查
   *
   * @throws Throwable 当DbConnection析构时出现错误
   */
  protected void finalize() throws Throwable {
    try {
      close(connName);
    }
    catch (Throwable e) {
    }
    super.finalize();
  }


  /**
   * 给参数赋值Object.
   *
   * @param index 参数位置. 从1开始, 顺序递增.
   * @param parameter 参数
   * @throws SQLException 当数据库操作出现错误
   */
  public void setObject(int index, Object parameter) throws SQLException {
    if (! (parameter instanceof java.sql.Date)) {
      parameter = (parameter == null ? "" : parameter.toString().trim());

    }
    pstmt.setObject(index, parameter);
  }

	public void setCharacterStream(int index, Object parameter) throws SQLException {
		parameter = (parameter == null ? "" : parameter.toString().trim());
		try{
			String message = parameter.toString();
			
			  
			  System.out.println("DBOperation.class index::@@@@:::"+index);
			  System.out.println("DBOperation.class message::@@@@:::"+message);
			  System.out.println("DBOperation.class length::@@@@:::"+message.length());
			 // System.out.println("messageBytesLength length::@#####$%^&:");
			//  System.out.println("messageBytesLength length::@@@@:::"+message.toString());
			  Reader r = new StringReader(parameter.toString());
			 
			//Reader isr = new InputStreamReader(new java.io.ByteArrayInputStream(messageBytes.toString());
			pstmt.setCharacterStream(index, r, 4000);
			 // pstmt.setCharacterStream(index,new InputStreamReader(new ByteArrayInputStream(message.getBytes())), message.length());
			  
			System.out.println("setCharacterStream sucessfull!@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			}catch(Exception e){
			e.printStackTrace();	
		}
	}
  public void setString(int index, Object parameter) throws SQLException {
    parameter = (parameter == null ? "" : parameter.toString().trim());
    System.out.println(parameter.toString()+"@@@@@@@@");
    pstmt.setString(index, parameter.toString());
  }
  public void setInt(int index, int parameter) throws SQLException {
    pstmt.setInt(index, parameter);
  }
  public void setLong(int index, long parameter) throws SQLException {
    pstmt.setLong(index, parameter);
  }
  public void setDouble(int index, double parameter) throws SQLException {
    pstmt.setDouble(index, parameter);
  }
  public void setBoolean(int index, boolean parameter) throws SQLException {
    pstmt.setBoolean(index, parameter);
  }
  public void setFloat(int index, float parameter) throws SQLException {
    pstmt.setFloat(index, parameter);
  }
  public void setDate(int index, java.sql.Date parameter) throws SQLException {
   pstmt.setDate(index, parameter);
 }
  public void setBinaryStream(int index, java.io.InputStream parameter,int filelength) throws SQLException {
   pstmt.setBinaryStream(index, parameter,filelength);
 }
 public static void main(String[] agr){
	 /*DBOperation dbo=new DBOperation();
	 String sql="select XMID,XMAPNF,XMSZDQ,XMXZ,XMLX,XMMC,QQGZJZQK,BGQK,BGHXM,XMZTZ,BZ,CJR,ZHXGR,CJSJ,ZHXGSJ,TBDWDM from T_XMK where SFYAPNDJH='0'";
	    
	    dbo.setConnName("ndjh");
	    dbo.prepare(sql);
	    dbo.setPageSize(2);
	    int pagesize=dbo.getPages();
	    int totalpages=dbo.getTotalRecords();
	    if (strpages==null) 
		{ 
 	pages=1; 
		} 
		else 
		{ 
		try{pages=Integer.parseInt(strpages);} 
		catch(NumberFormatException e){} 
		if (pages<1) pages=1; 
		if (pages>pagesize) pages=pagesize; 
		} 
	    Vrsdata=dbo.getPage(pages);
	    Vrs.add(0,Vrsdata);
	    Vrs.add(1,String.valueOf(pagesize));
	    Vrs.add(2,String.valueOf(totalpages));
	    Vrs.add(3,String.valueOf(pages));
	}catch(Exception e)
  { 
		   e.printStackTrace();
	 }*/
 
	
	
	
 }
}
