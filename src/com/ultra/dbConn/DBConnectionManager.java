/**
* Title: DBConnectionManager.java
* Description: ���ӳع�����
* Copyright: Copyright (c)
* Company: nercita
* Author : wt
* Version 1.0
*/
package com.ultra.dbConn;


import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.database.IDataBase;
/**
 * ������DBConnectionManager֧�ֶ�һ�������������ļ���������ݿ�����
 * �صķ���.�ͻ�������Ե���getInstance()�������ʱ����Ψһʵ��.
 */

public class DBConnectionManager {
  static private DBConnectionManager instance; // Ψһʵ��
  static private int clients;

  private Vector drivers = new Vector();
  private PrintWriter log;
  private Hashtable pools = new Hashtable();

  private IDataBase iDataBase;
  /**
   * ����Ψһʵ��.����ǵ�һ�ε��ô˷���,�򴴽�ʵ��
   *
   * @return DBConnectionManager Ψһʵ��
   **/
  static synchronized public DBConnectionManager getInstance() {
    if (instance == null) {
      instance = new DBConnectionManager();
    }
    clients++;
    return instance;
  }

  /**
   * ��������˽���Է�ֹ�������󴴽�����ʵ��
   */
  private DBConnectionManager() {
	  iDataBase=GetDataBase.createDataBase("");
    //init();
  }

  /**
   * �����Ӷ��󷵻ظ�������ָ�������ӳ�
   *
   * @param name �������ļ��ж�������ӳ�����
   * @param con ���Ӷ���
   **/
  public void freeConnection(String name, Connection con) {
	  iDataBase.closeConn();
	  /* DBConnectionPool pool = (DBConnectionPool) pools.get(name);
    if (pool != null) {
      pool.freeConnection(con);
    }*/
  }

  /**
   * ���һ�����õ�(���е�)����.���û�п�������,������������С�����������
   * ����,�򴴽�������������
   *
   * @param name �������ļ��ж�������ӳ�����
   * @return Connection �������ӻ�null
   */
  public Connection getConnection(String name) {
	  return this.iDataBase.getConn();
	  /* DBConnectionPool pool = (DBConnectionPool) pools.get(name);
    if (pool != null) {
      return pool.getConnection();
    }
    return null;*/
  }

  /**
   * ���һ����������.��û�п�������,������������С���������������,
   * �򴴽�������������.����,��ָ����ʱ���ڵȴ������߳��ͷ�����.
   *
   * @param name ���ӳ�����
   * @param time �Ժ���Ƶĵȴ�ʱ��
   * @return Connection �������ӻ�null
   */
  public Connection getConnection(String name, long time) {
	  IDataBase base=GetDataBase.createDataBase(name);
		 return base.getConn();
	  /*DBConnectionPool pool = (DBConnectionPool) pools.get(name);
    if (pool != null) {
      return pool.getConnection(time);
    }
    return null;*/
  }

  /**
   * �ر���������,�������������ע��
   */
  public synchronized void release() {
// �ȴ�ֱ�����һ���ͻ��������
   /* if (--clients != 0) {
      return;
    }

    Enumeration allPools = pools.elements();
    while (allPools.hasMoreElements()) {
      DBConnectionPool pool = (DBConnectionPool) allPools.nextElement();
      pool.release();
    }
    Enumeration allDrivers = drivers.elements();
    while (allDrivers.hasMoreElements()) {
      Driver driver = (Driver) allDrivers.nextElement();
      try {
        DriverManager.deregisterDriver(driver);
        log("����JDBC�������� " + driver.getClass().getName() + "��ע��");
      }
      catch (SQLException e) {
        log(e, "�޷���������JDBC���������ע��: " + driver.getClass().getName());
      }
    }*/
  }

  /**
   * ����ָ�����Դ������ӳ�ʵ��.
   *
   * @param props ���ӳ�����
   */
  private void createPools(Properties props) {
    /*Enumeration propNames = props.propertyNames();
    while (propNames.hasMoreElements()) {
      String name = (String) propNames.nextElement();
      if (name.endsWith(".url")){
        String poolName = name.substring(0, name.lastIndexOf("."));
        String url = props.getProperty(poolName + ".url");
        if (url == null) {
          log("û��Ϊ���ӳ�" + poolName + "ָ��url");
          continue;
        }
        String user = props.getProperty(poolName + ".user");
        String password = props.getProperty(poolName + ".password");
        String maxconn = (String)props.getProperty(poolName + ".maxconn","0");
        int max;
        //��ֵ�����2006.3.07���ǽ���catch....
        
        try {
          max = Integer.parseInt(maxconn);
          System.out.println("max1="+max);
        }
        catch (NumberFormatException e){
          log("������������������: " + maxconn + " .���ӳ�: " + poolName);
          max = 0;
        }
        
        max = 0;
        System.out.print("url="+url+"/");
        System.out.print("user="+user+"/");
        System.out.print("password="+password+"/");
        DBConnectionPool pool = new DBConnectionPool(poolName, url, user, password, max);
        pools.put(poolName, pool);
        log("�ɹ��������ӳ�" + poolName);
      }
    }*/
  }

  /**
   * ��ȡ������ɳ�ʼ��
   */
  private void init() {
    /*InputStream is = getClass().getResourceAsStream("db.properties");
    Properties dbProps = new Properties();
    try {
      dbProps.load(is);
    }
    catch (Exception e) {
      System.err.println("���ܶ�ȡ�����ļ�. " +
                         "��ȷ��db.properties��CLASSPATHָ����·����");
      return;
    }
    String logFile = dbProps.getProperty("sqlserver.logfile", "DBConnectionManager.log");
    try {
      log = new PrintWriter(new FileWriter(logFile, true), true);
    }
    catch (IOException e) {
      System.err.println("�޷�����־�ļ�: " + logFile);
      log = new PrintWriter(System.err);
    }
    loadDrivers(dbProps);
    createPools(dbProps);*/
  }

  /**
   * װ�غ�ע������JDBC��������
   *
   * @param props ����
   */
  private void loadDrivers(Properties props) {
    String driverClasses = props.getProperty("sqlserver.driver");
    StringTokenizer st = new StringTokenizer(driverClasses);
    while (st.hasMoreElements()) {
      String driverClassName = st.nextToken().trim();
      try {
       /* Driver driver = (Driver)Class.forName(driverClassName).newInstance();
        DriverManager.registerDriver(driver);
        drivers.addElement(driver);*/
        log("�ɹ�ע��JDBC��������" + driverClassName);
      }
      catch (Exception e) {
        log("�޷�ע��JDBC��������:"+driverClassName+",����:"+e);
      }
    }
  }

  /**
   * ���ı���Ϣд����־�ļ�
   */
  private void log(String msg) {
    log.println(new Date() + ": " + msg);
  }

  /**
   * ���ı���Ϣ���쳣д����־�ļ�
   */
  private void log(Throwable e, String msg) {
    log.println(new Date() + ": " + msg);
    e.printStackTrace(log);
  }

//  /**
//   * ���ڲ��ඨ����һ�����ӳ�.���ܹ�����Ҫ�󴴽�������,ֱ��Ԥ������
//   * ��������Ϊֹ.�ڷ������Ӹ��ͻ�����֮ǰ,���ܹ���֤���ӵ���Ч��.
//   */
//  class DBConnectionPool {
//    private int checkedOut;
//    private Vector freeConnections = new Vector();
//    private int maxConn;
//    private String name;
//    private String password;
//    private String url;
//    private String user;
//
//    /**
//     * �����µ����ӳ�
//     *
//     * @param name ���ӳ�����
//     * @param url ���ݿ��JDBC url
//     * @param user ���ݿ��ʺ�,�� null
//     * @param password ����,�� null
//     * @param maxConn �����ӳ������������������
//     */
//    public DBConnectionPool(String name, String url, String user,String password,
//    						int maxConn){
//      this.name = name;
//      this.url = url;
//      this.user = user;
//      this.password = password;
//      this.maxConn = maxConn;
//      System.out.print("url2="+url+"/");
//      System.out.print("user2="+user+"/");
//      System.out.print("password2="+password+"/");
//
//    }
//
//    /**
//     * ������ʹ�õ����ӷ��ظ����ӳ�
//     *
//     * @param con �ͻ������ͷŵ�����
//     */
//    public synchronized void freeConnection(Connection con) {
//// ��ָ�����Ӽ��뵽����ĩβ
//      freeConnections.addElement(con);
//      checkedOut--;
//      notifyAll();
//    }
//
//    /**
//     * �����ӳػ��һ����������.��û�п��е������ҵ�ǰ������С���������
//     * ������,�򴴽�������.��ԭ���Ǽ�Ϊ���õ����Ӳ�����Ч,�������ɾ��֮,
//     * Ȼ��ݹ�����Լ��Գ����µĿ�������.
//     */
//    public synchronized Connection getConnection() {
//      Connection con = null;
//      if (freeConnections.size() > 0) {
//		// ��ȡ�����е�һ����������
//        con = (Connection) freeConnections.firstElement();
//        freeConnections.removeElementAt(0);
//        try {
//          if (con.isClosed()) {
//            log("�����ӳ�" + name + "ɾ��һ����Ч����");
//		// �ݹ�����Լ�,�����ٴλ�ȡ��������
//            con = getConnection();
//          }
//        }
//        catch (SQLException e) {
//          log("�����ӳ�" + name + "ɾ��һ����Ч����");
//		// �ݹ�����Լ�,�����ٴλ�ȡ��������
//          con = getConnection();
//        }
//      }
//      else if (maxConn == 0 || checkedOut < maxConn) {
//        con = newConnection();
//      }
//      if (con != null) {
//        checkedOut++;
//      }
//      return con;
//    }
//
//    /**
//     * �����ӳػ�ȡ��������.����ָ���ͻ������ܹ��ȴ����ʱ��
//     * �μ�ǰһ��getConnection()����.
//     *
//     * @param timeout �Ժ���Ƶĵȴ�ʱ������
//     */
//    public synchronized Connection getConnection(long timeout) {
//      long startTime = new Date().getTime();
//      Connection con;
//      while ( (con = getConnection()) == null) {
//        try {
//          wait(timeout);
//        }
//        catch (InterruptedException e) {}
//        if ( (new Date().getTime() - startTime) >= timeout) {
//// wait()���ص�ԭ���ǳ�ʱ
//          return null;
//        }
//      }
//      return con;
//    }
//
//    /**
//     * �ر���������
//     */
//    public synchronized void release() {
//      Enumeration allConnections = freeConnections.elements();
//      while (allConnections.hasMoreElements()) {
//        Connection con = (Connection) allConnections.nextElement();
//        try {
//          con.close();
//          log("�ر����ӳ�" + name + "�е�һ������");
//        }
//        catch (SQLException e) {
//          log(e, "�޷��ر����ӳ�" + name + "�е�����");
//        }
//      }
//      freeConnections.removeAllElements();
//    }
//
//    /**
//     * �ر�һ������
//     */
//    public synchronized void releaseOne() {
//      if(freeConnections.firstElement()!=null) {
//  	Connection con = (Connection) freeConnections.firstElement();
//	try {
//	  con.close();
//	  log("�ر����ӳ�" + name+"�е�һ������");
//	}
//	catch (SQLException e) {
//	  log(e, "�޷��ر����ӳ�" + name+"�е�����");
//	}
//      }
//      else {
//	log("releaseOne() bug.......................................................");
//
//      }
//    }
//
//
//    /**
//     * �����µ�����
//     **/
//     private Connection newConnection(){
//       Connection con = null;
//       try{
//         if(user == null){
//           con = DriverManager.getConnection(url);
//         }
//	     else{
//               System.out.print(url+":"+user+":"+password);
//	     	con = DriverManager.getConnection(url, user, password);
//
//	     }
//       }
//       catch(Exception e){
//         log("�޷�ȡ��������");
//       }
//       return con;
//     }
//   }
}
