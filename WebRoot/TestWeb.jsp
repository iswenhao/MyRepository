<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.ultra.interfac.KF_client2"  %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'TestWeb.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <%
    	KF_client2 KF_client=new KF_client2();
    	String endpoint = "http://10.70.60.133:8181/axis/services/IMEPServer_interface.jws";
    	String result=KF_client.testWeb(endpoint);
    	
     %>
	<%=endpoint %>
	 testWebserivces:<%=result%>
  </body>
</html>