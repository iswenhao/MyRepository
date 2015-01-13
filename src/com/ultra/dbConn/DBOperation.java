/**
* Title: DBOperation.java
* Description: ���ݿ����
* Copyright: Copyright (c)
* Company: nercita
* Author : wt
* remark : ����ָ��ع�
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
   * @throws SQLException �����ݿ�������ִ���
   * @return Connection����
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
   * �����ݿ����ӳ���ѡ��һ���µ����ݿ�����
   *
   * @throws SQLException �����ݿ�������ִ���
   * @return Connection����
   */
  private Connection newConnection(String name) throws SQLException {
	connMgr = DBConnectionManager.getInstance();
	conn = connMgr.getConnection(name);
   //     System.out.println("name="+name);
    if (conn == null) {
      throw new SQLException("�����ݿ�ȡ���Ӵ���! DbConnectionPool is exhausted!");
    }
    return conn;
  }

  /**
   * Get db connection in time.
   *
   * @throws SQLException �����ݿ�������ִ���
   * @return Connection����
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
   * �ڹ涨ʱ������ݿ����ӳ���ѡ��һ���µ����ݿ�����
   *
   * @throws SQLException �����ݿ�������ִ���
   * @return Connection����
   */
  private Connection newConnection(String name, long time) throws SQLException {
	connMgr = DBConnectionManager.getInstance();
	conn = connMgr.getConnection(name,time);
    if (conn == null) {
      throw new SQLException("�����ݿ�ȡ���Ӵ���! DbConnectionPool is exhausted!");
    }
    return conn;
  }

  /**
   * Ԥ����SQL���, ����PreparedStatement����.
   *
   * @param strSQL SQL���
   * @throws SQLException �����ݿ�������ִ���
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
     // System.out.println("����PreparedStatement������ִ���" + e);
      throw e;
    }
  }
  
  //�ӿڿ���Ϊ������ַ����洢���⣬ֱ�Ӳ������ݿ��м��
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
    	//  System.out.println("ע�⣺�����˵���perpare������");
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
   * ִ��sql���ؽ��--��ά����<br>
   * @throws SQLException �����ݿ�������ִ���
   * @return  ����ѯ������άVector[[aaa,bbb]��[ccc,ddd]], ��δ�鵽���ʱ����[]��������
   * Ӧ�ԣ�(java.util.Vector��ret).size()�ж�@return�Ƿ�Ϊ��<br>
   *
   */
  public Vector executeQuery() throws SQLException {
    ResultSet rs = null;
    Vector vResult = null;
    try {
      if (pstmt == null) {
      //  System.out.println("ע�⣺�����˵���perpare������");
        throw new SQLException("Not prepared.");
      }

      rs = pstmt.executeQuery();
      vResult = voRs(rs);
    }
    catch (SQLException e) {
      System.err.println("DBOperation��ѯ���ؽ��������"+e);
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
   * ��ѯ���ر�����
   * @param  strSQL sql���
   * @param  parameter   ����Ĳ�����
   * @throws SQLException �����ݿ�������ִ���
   * @return ��ѯ���
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
   * ִ��sql���ؽ��.
   *
   * @throws SQLException �����ݿ�������ִ���
   * @return ����Ӱ��ļ�¼��<br>
   */
  public int executeUpdate() throws SQLException {
    try {
      if (pstmt == null) {
      //  System.out.println("ע�⣺�����˵���perpare������");

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
   * ִ��insert��update���ͷ�����
   * @param  strSQL sql���
   * @param  parameter   ����Ĳ�����
   * @throws SQLException �����ݿ�������ִ���
   * @return ִ�н��
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
	*ִ��������
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
		//		System.out.println(" ��ʼִ�����"+(String)SqlArray.elementAt(i));
				stmt.executeUpdate((String)SqlArray.elementAt(i));
		//		System.out.println(" ִ�гɹ�");
			}
			conn.commit();
			conn.setAutoCommit(true) ;//����
			System.out.println("����ִ�гɹ�");
			result = true;
		}
		catch(java.sql.SQLException e)
		{//System.out.println("trans="+e.getMessage());
			try
			{
			//	System.out.println(e.toString());
			//	System.out.println("���ݿ����ʧ��");
				conn.rollback();
			}
			catch(java.sql.SQLException Te)
			{
				System.err.println("�������ع��쳣");
			}
		}
		try
		{
			conn.setAutoCommit(true);
		}
		catch(java.sql.SQLException e)
		{
			System.err.println("�����Զ��ύʧ��");
		}
		//�д��Ľ�2006.3.6
        close(connName);
		return result;
    }

	/**
	*�ͷ�����
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
				//System.out.println("�����ͷ�һ������...");

			}
		}
		catch(java.sql.SQLException e)
		{
		System.err.println("�ͷ����ӳ���");
		}
	}
  /**
   * ��rs��ŵ�vector��
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
   * �õ��õ���ͷֵ
   *
   * @return ��ͷֵvector 
   */
  public Vector getColumnName() {
	  	 
		return columnameVO; 
	  }
  /**
   * �õ�row��, col�е�ֵ
   *
   * @param row ��λ��. ��0��ʼ
   * @param col ��λ��. ��0��ʼ
   * @return row,col���ڵ��ַ���ֵ
   */
  public String get(int row, int col) {
    return ( (Vector) rsVO.elementAt(row)).elementAt(col).toString();
  }

  /**
   * �õ�һ�е�ֵ.
   *
   * @param row ��λ��
   * @return row���ڵ�����ֵ.
   */
  public Vector getRow(int row) {
    if (getRowCount() < row) {
      return null;
    }
    return (Vector) rsVO.elementAt(row);
  }

  /**
   * �õ�һ�е�ֵ
   *
   * @param col ��λ��
   * @return col�е�����ֵ
   */
  public Vector getColumn(int col) {
    Vector vo = new Vector();

    for (int i = 0; i < getRowCount(); i++) {
      vo.add( ( (Vector) rsVO.elementAt(i)).elementAt(col));
    }
    return vo;

  }

  /**
   * �õ�����
   *
   * @return ����
   */
  public int getRowCount() {
    return rsVO == null ? 0 : rsVO.size();
  }

  /**
   * �õ�����
   *
   * @return ����
   */
  public int getColCount() {
    if (getRowCount() == 0) {
      return 0; //���û�еõ���¼����0
    }
    return ( (Vector) rsVO.elementAt(0)).size();
  }

  /**
   * Ϊ��ֹ���ݿ������ͷ�ʧ��������ʱ���
   *
   * @throws Throwable ��DbConnection����ʱ���ִ���
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
   * ��������ֵObject.
   *
   * @param index ����λ��. ��1��ʼ, ˳�����.
   * @param parameter ����
   * @throws SQLException �����ݿ�������ִ���
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
