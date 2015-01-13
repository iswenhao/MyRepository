<%@ page language="java"
         pageEncoding="utf-8"
         import="cn.com.ultrapower.ultraprocess.share.constants.Constants,
                com.ultrapower.zjitsm.model.CommonUserInfo,
                com.ultrapower.zjitsm.config.BuildMenu"
%><%
CommonUserInfo userInfo = (CommonUserInfo) session.getAttribute(Constants.SESSIONNAME + "_ZJUSERINFO");
if (userInfo == null) {
	userInfo = new CommonUserInfo();
}
String groupNames = userInfo.getGroupNames();
BuildMenu menu = new BuildMenu(userInfo.getRoles());
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<title></title>
<link rel="stylesheet" href="../style/main.css"></link>
<script language="javascript">
function switchTreeMenu(id) {
	var obj = document.all(id);
	var objimg = document.all(id+"img");
	if (obj.style.display == "none") {
		obj.style.display = "";
		objimg.style.backgroundImage = "url(../img/left/folderopen.gif)";
	} else {
		obj.style.display = "none";
		objimg.style.backgroundImage = "url(../img/left/folderclose.gif)";
	}
}
var lastBlock = null;
function switchBlock(id) {
	if (id == null) {
		return;
	}
	if (lastBlock != null) {
		document.all(lastBlock+"Tr").style.display = "none";
	}
	document.all(id+"Tr").style.display = "";
	// 30*4(four title bars) + 1*4(four bottom bars) + 2(2 pixel below bottom)
	var h = document.body.clientHeight - 126;
	if (h < 30) {
		h = 30;
	}
	document.all(id + "Td").style.height = h;
	lastBlock = id;
}
function init() {
	switchBlock("comm");
}
window.onresize = function() {
	switchBlock(lastBlock);
}
</script>
</head>

<body onLoad="init();" onselectstart="return false;">

<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr onclick="switchBlock('info');"><td class="left_titlebar">
	<table width="65%" border="0" cellpadding="0" cellspacing="0"><tr>
	<td width="22"><img src="../img/left/usercd.gif" width="20" height="20"></td>
	<td class="left_titlebartext">个人信息</td>
	</tr></table>
</td></tr>
<tr id="infoTr" style="display:none;"><td id="infoTd" class="left_barbody">
<div class="left_barbodydiv">
	<table border="0" width="100%" cellpadding="0" cellspacing="2">
	  <tr><td height="20">欢迎：<span class="left_fullname"><%=userInfo.getFullname()%></span></td></tr>
	  <tr><td height="20">部门：<%=(userInfo.getSite()==null?"":userInfo.getSite())%> <%=(userInfo.getDepartment()==null?"":userInfo.getDepartment())%></td></tr>
	  <tr><td height="20">您所在的组列表：</td></tr>
<%
String gn[] = groupNames.split(";");
if (gn == null || gn.length == 0) {
%><tr><td height="20" style="padding-left:15">[无]</td></tr><%
} else {
	for (int i = 0; i < gn.length; i ++) {
		if (gn[i] != null && !"".equals(gn[i]))
%><tr><td height="20" style="padding-left:15" class="left_fullname"><%=gn[i]%></td></tr><%
	}
}
%>
	</table>
</div>
</td></tr>
<tr><td height="1" background="../img/left/bottom_bg.gif"></td></tr>
</table>

<!--=======================-->

<table width="100%" border="0" cellpadding="0" cellspacing="0" onselectstart="return false;">
<tr onClick="switchBlock('comm');"><td class="left_titlebar">
	<table width="65%" border="0" cellpadding="0" cellspacing="0"><tr>
	<td width="22"><img src="../img/left/cycdopen.gif" width="20" height="20"></td>
	<td class="left_titlebartext">日常运维</td>
	</tr></table>
</td></tr>
<tr id="commTr" style="display:none;"><td id="commTd" class="left_barbody">
<div class="left_barbodydiv">
	<table border="0" width="100%" cellpadding="0" cellspacing="0" id="test">
	<tr><td class="left_child"></td><td class="left_childtext"><a href="javascript:" onclick="parent.maingo('../comm/query/todeal.jsp');">待处理工单</a></td></tr>
	<tr><td class="left_child"></td><td class="left_childtext"><a href="javascript:" onclick="parent.maingo('../comm/query/toaudit.jsp');">待审批工单</a></td></tr>
<%=menu.build("A", 1)%>
<%
if(userInfo.getFullname().equals("Demo")&userInfo.getFullname().equals("tiandong")){

%>
	<tr><td class="left_child"></td><td class="left_childtext"><a href="javascript:" onclick="parent.maingo('/zjitsm/UltraProcess/share/main.jsp');">工单管理</a></td></tr>
	<tr><td class="left_child"></td><td class="left_childtext"><a href="javascript:" onclick="parent.maingo('/zjitsm/UltraProcess/share/flowmanageindex.jsp');">流程管理</a></td></tr>

<%}else{%>

<tr><td class="left_child"></td><td class="left_childtext"><a href="javascript:" onclick="parent.maingo('/zjitsm/UltraProcess/manageprocess/BaseView_All.jsp?basechema=WF:APP_TES');">测试单查询</a></td></tr>
<tr><td class="left_child"></td><td class="left_childtext"><a href="javascript:" onclick="parent.maingo('/zjitsm/UltraProcess/manageprocess/WaitingDeal_User.jsp?type=user&basechema=WF:APP_TES');">待处理测试单</a></td></tr>
<tr><td class="left_child"></td><td class="left_childtext"><a href="javascript:" onclick="parent.maingo('/zjitsm/UltraProcess/manageprocess/BaseView_All.jsp?1=1&basechema=WF:APP_RFC');">发布单查询</a></td></tr>
<tr><td class="left_child"></td><td class="left_childtext"><a href="javascript:" onclick="parent.maingo('/zjitsm/UltraProcess/manageprocess/WaitingDeal_User.jsp?type=user&basechema=WF:APP_RFC');">待处理发布单</a></td></tr>
<%}%>

	</table>
</div>
</td></tr>
<tr><td height="1" background="../img/left/bottom_bg.gif"></td></tr>
</table>

<!--========================-->

<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr onClick="switchBlock('report');"><td class="left_titlebar">
	<table width="65%" border="0" cellpadding="0" cellspacing="0"><tr>
	<td width="22"><img src="../img/left/sys.gif" width="20" height="20"></td>
	<td class="left_titlebartext">常用工具</td>
	</tr></table>
</td></tr>
<tr id="reportTr" style="display:none;"><td id="reportTd" class="left_barbody">
<div class="left_barbodydiv" style="width:expression(this.offsetParent.offsetWidth-7);">
	<table border="0" width="100%" cellpadding="0" cellspacing="0">
	<tr><td class="left_child"></td><td class="left_childtext"><a href="javascript:" onclick="parent.maingo('/arsys/forms/'+parent.arServerName+'/Change+Password/Requester/?mode=CREATE');">修改个人信息</a></td></tr>
	<tr><td class="left_child"><img src="../img/left/blank.gif" width="15" height="15"></td><td class="left_childtext"><nobr><a href="javascript:" onclick="parent.maingo('/zjitsm/comm/query/information.jsp');">信息单查询</a>&nbsp;<a href="javascript:" 
	onclick="window.open('/arsys/forms/zjwfrpt/ZMCC_CaseNoticeDisplay/Default%20Admin%20View/?mode=CREATE')">[新建]
	</a></nobr></td></tr>
<%=menu.build("B", 1)%>
	</table>
</div>
</td></tr>
<tr><td height="1" background="../img/left/bottom_bg.gif"></td></tr>
</table>

<!--========================-->

<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr onClick="switchBlock('admin');"><td class="left_titlebar">
	<table width="65%" border="0" cellpadding="0" cellspacing="0"><tr>
	<td width="22"><img src="../img/left/report.gif" width="20" height="20"></td>
	<td class="left_titlebartext">系统管理</td>
	</tr></table>
</td></tr>
<tr id="adminTr" style="display:none;"><td id="adminTd" class="left_barbody">
<div class="left_barbodydiv" style="width:expression(this.offsetParent.offsetWidth-7);">
	<table border="0" width="100%" cellpadding="0" cellspacing="0">
<%if ("Demo".equals(userInfo.getUsername())) {%>
	<tr><td class="left_child"></td><td class="left_childtext"><a href="javascript:" onclick="parent.maingo('/arsys/forms/'+parent.arServerName+'/SHR%3ALocation/Administrator/?mode=CREATE');">地域</a></td></tr>
	<tr><td class="left_child"></td><td class="left_childtext"><a href="javascript:" onclick="parent.maingo('/arsys/forms/'+parent.arServerName+'/SHRCFG%3AConfigPeople/Administrator/?mode=CREATE');">人员定义</a></td></tr>
	<tr><td class="left_child"></td><td class="left_childtext"><a href="javascript:" onclick="parent.maingo('/arsys/forms/'+parent.arServerName+'/SHRCFG%3AConfigApplicationGroups/Administrator/?mode=CREATE');">组定义</a></td></tr>
	<tr><td class="left_child"></td><td class="left_childtext"><a href="javascript:" onclick="parent.maingo('/arsys/forms/'+parent.arServerName+'/SHRCFG%3AConfigGroupSkills/Administrator/?mode=CREATE');">组技能配置</a></td></tr>
	<tr><td class="left_child"></td><td class="left_childtext"><a href="javascript:" onclick="parent.maingo('/arsys/forms/'+parent.arServerName+'/SHRCFG%3AConfigManageGroups/Administrator/?mode=CREATE');">组成员</a></td></tr>
	<tr><td class="left_child"></td><td class="left_childtext"><a href="javascript:" onclick="parent.maingo('/arsys/forms/'+parent.arServerName+'/SHR%3ACategorization/Administrator/?mode=CREATE');">分类配置</a></td></tr>
	<tr><td class="left_child"></td><td class="left_childtext"><a href="javascript:" onclick="parent.maingo('/arsys/forms/'+parent.arServerName+'/ZMCC_HelpDesk_Unlock/Default%20Admin%20View/?mode=CREATE');">工单解锁</a></td></tr>

	<tr onClick="switchTreeMenu('Admin1');"><td id="Admin1img" class="left_folder"><img src="../img/left/blank.gif" width="15" height="15"></td><td class="left_foldertext"><nobr>事件</nobr></td></tr>
	<tr id="Admin1"><td class="left_folderchild" colspan="2">
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr><td class="left_child"><img src="../img/left/blank.gif" width="15" height="15"></td><td class="left_childtext"><nobr><a href="javascript:" onclick="parent.maingo('/arsys/forms/'+parent.arServerName+'/ZM_HelpDeskCFGGroupCategorization/Default%20Admin%20View/?mode=CREATE');">与集团分类对应关系</a></nobr></td></tr>
		</table>
	</td></tr>
<%}%>
	</table>
</div>
</td></tr>
<tr><td height="1" background="../img/left/bottom_bg.gif"></td></tr>
</table>

<%
menu.finalize();
%>
</body>
</html>
