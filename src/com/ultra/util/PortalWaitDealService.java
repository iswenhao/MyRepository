package com.ultra.util;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.ultra.dbConn.DBOperation;
import com.ultra.dbConn.ZMCC;

public class PortalWaitDealService extends DBOperation {
	Log log = LogFactory.getLog(PortalWaitDealService.class);
	private List<WaitDealBean> list ;
	public static String SHR_ConsolidatedListTableName;
	public static String GZ_SupervisorTableName;
	public static String YZGJ_SupervisorTableName;
	public static String WF_App_Base_InforTableName;
	public static String WF_App_DealProcessTableName;
	public static String WF_App_AuditingProcessTableName;
	public static String ZMCC_Problem_NewTableName;
	public static String QC_AppealTableName;
	static String path = "";
	static Map<String,String> schemaMap = new HashMap<String,String>();
	static Map<String,String> priorityMap = new HashMap<String,String>();
	static{
		schemaMap.put("MAINHELPDESK","HPD:HelpDesk");
		schemaMap.put("MAINPROBLEM","ZMCC:Problem");
		schemaMap.put("MAINTASK","CHG:Task");
		schemaMap.put("OPERATION","ZMCC_MaintenanceOperations");
		schemaMap.put("MAINCHANGE","CHG:Change");
		schemaMap.put("MAINOPERATION","ZMCC_OperatingPlan");
		
		priorityMap.put("0", "低");
		priorityMap.put("1", "中");
		priorityMap.put("2", "高");
		priorityMap.put("3", "紧急");
	}
	
	public PortalWaitDealService() {
		this.setConnName(ZMCC.zmccDBConn);
		if(null==SHR_ConsolidatedListTableName || "".equals(SHR_ConsolidatedListTableName)){
			SHR_ConsolidatedListTableName = getSchemaId("SHR:ConsolidatedList");
		}
		if(null==GZ_SupervisorTableName || "".equals(GZ_SupervisorTableName)){
			GZ_SupervisorTableName = getSchemaId("GZ_Supervisor");
		}
		if(null==YZGJ_SupervisorTableName || "".equals(YZGJ_SupervisorTableName)){
			YZGJ_SupervisorTableName = getSchemaId("YZGJ_Supervisor");
		}
		if(null==WF_App_Base_InforTableName || "".equals(WF_App_Base_InforTableName)){
			WF_App_Base_InforTableName = getSchemaId("WF:App_Base_Infor");
		}
		if(null==WF_App_DealProcessTableName || "".equals(WF_App_DealProcessTableName)){
			WF_App_DealProcessTableName = getSchemaId("WF:App_DealProcess");
		}
		if(null==WF_App_AuditingProcessTableName || "".equals(WF_App_AuditingProcessTableName)){
			WF_App_AuditingProcessTableName = getSchemaId("WF:App_AuditingProcess");
		}
		if(null==ZMCC_Problem_NewTableName || "".equals(ZMCC_Problem_NewTableName)){
			ZMCC_Problem_NewTableName = getSchemaId("ZMCC:Problem_New");
		}
		if(null==QC_AppealTableName || "".equals(QC_AppealTableName)){
			QC_AppealTableName = getSchemaId("QC_Appeal");
		}
		if(null==path || "".equals(path)){
			SAXReader reader = new SAXReader();
			Document document = null;

			try {
				document = reader.read(new File("/u01/tomcat55/webapps/axis/attchfiles/interface_con.xml"));
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Element root = document.getRootElement();
			Element one = root.element("portalWaitDeal");
			path = one.elementText("path");
		}
	}

	private String getSchemaId(String schemaName){
		String schemaTable = "select s.schemaid from arschema s where s.name='"+schemaName+"'";
		String tableName = "T";
		try {
			this.prepare(schemaTable);
			Vector vList = this.executeQuery();
			if(null!=vList && !vList.isEmpty()){
				Vector vresult = (Vector)vList.get(0);
				tableName += vresult.get(0);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tableName;
	}
	
	
	public void getWaitDealList(String userLoginname) {
		
		String sql = "select c8 as fullname,c104 as grouplist from t22 t where t.c101 = '"
				+ userLoginname + "'";
		String grouplistid = "";
		String grouplistname = "";
		String fullname = "";
		try {
			
			
			this.prepare(sql);
			Vector vc = this.executeQuery();
			if(0==vc.size()){
				log.info("查无此人:"+userLoginname);
				return ;
			}
			for (int i = 0; i < vc.size(); i++) {
				Vector v = (Vector) vc.get(i);
				fullname = v.get(0).toString();
				grouplistid = v.get(1).toString();
			}

			if (!grouplistid.equals("")) {
				String sqlgroupname = "select c105 as groupname from t21 t where ';"
						+ grouplistid + "' like '%;'||c106||';%'";
				this.prepare(sqlgroupname);
				Vector vcgroup = this.executeQuery();
				for (int i = 0; i < vc.size(); i++) {
					Vector vgp = (Vector) vcgroup.get(i);
					grouplistname = grouplistname + vgp.get(0).toString();
				}
			}
			
			execSql(getConsolidatedList(userLoginname,fullname,grouplistid),userLoginname,"");
			execSql(getGZSupervisor(grouplistname),userLoginname,"");
			execSql(getYZGJSupervisor(fullname,grouplistname),userLoginname,"");
//			execSql(getZJYD_User_Audit(grouplistid,userLoginname),userLoginname);
//			execSql(getZJYD_City_Suggest(grouplistid,userLoginname),userLoginname);
//			execSql(getZJYD_City_Examine(grouplistid,userLoginname),userLoginname);
//			execSql(getZJYD_Work_Cooperate(grouplistid,userLoginname),userLoginname);
			execSql(getZJYD_Firewall_Change(grouplistid,userLoginname),userLoginname,"WF");
			execSql(getZJYD_Firewall_Change_Free(grouplistid,userLoginname),userLoginname,"WF");
			execSql(getZJYD_MachineRoom_InAndOut(grouplistid,userLoginname),userLoginname,"WF");
			execSql(getZJYD_Parter_Manage(grouplistid,userLoginname),userLoginname,"WF");
			execSql(getZJYD_Save_Manage(grouplistid,userLoginname),userLoginname,"WF");
			execSql(getZJYD_Contract_Review_Manage(grouplistid,userLoginname),userLoginname,"WF");
			execSql(getProblem_New(grouplistid,fullname,userLoginname),userLoginname,"");
			execSql(getQC_Appeal(grouplistname,userLoginname),userLoginname,"");
			execSql(getAudit(grouplistname,userLoginname),userLoginname,"");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void execSql(String sql,String userLoginname,String type){
		if(null==list){
			list = new ArrayList<WaitDealBean>();
		}
		log.info("执行SQL:"+sql);
		String url = path+"/synchrologin.jsp?loginName="+userLoginname;
		try {
			this.prepare(sql);
			Vector VList = this.executeQuery();
			for(int i=0;i<VList.size();i++){
				Vector vresult = (Vector)VList.get(i);
				String Schema = schemaMap.get(StringUtil.checkNull(vresult.get(1)))==null ? StringUtil.checkNull(vresult.get(1)) : schemaMap.get(StringUtil.checkNull(vresult.get(1)));
				if("WF".equals(type)){
					url = url+"&BaseID="+StringUtil.checkNull(vresult.get(2))+"&Schema="+Schema;//url 工单url
				}else{
					url = url+"&BaseID="+StringUtil.checkNull(vresult.get(0))+"&Schema="+Schema;//url 工单url
				}
				WaitDealBean bean = new WaitDealBean(StringUtil.checkNull(vresult.get(0)),//record 工单编号
						StringUtil.checkNull(vresult.get(3)),//title 标题
						StringUtil.checkNull(vresult.get(4)),//build 请求人
						StringUtil.checkNull(vresult.get(5)),//status 工单状态
						priorityMap.get(StringUtil.checkNull(vresult.get(6)))==null ? StringUtil.checkNull(vresult.get(6)) : priorityMap.get(StringUtil.checkNull(vresult.get(6))),//priority 优先级
						
						StringUtil.checkNull(vresult.get(7)),//type 工单类型
						StringUtil.checkNull(vresult.get(8)),//begin 派单日期
						StringUtil.checkNull(vresult.get(9)),//group 处理组
						StringUtil.checkNull(vresult.get(10)),//dispose 处理人
						StringUtil.checkNull(vresult.get(11)),//end 解决时限
						url
						);
				
				list.add(bean);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// 查询helpdesk类工单 T398 SHR:ConsolidatedList
	private String getConsolidatedList(String loginname, String fullname,
			String grouplistid) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer
				.append(" select C260000005 as Chr_CaseId,C230000009 as SchemaNames,C536870913 as eventid,C8 as Summary,C2 as Submitter,C260000000 as Status,C260000126 as C260000126,C260000003,C260000502,C240000006 as AssignedToGroup,C4 as AssignedTo,C536871046 as mDTResolvedTime,C536870923 as mChrFormLock,C536870931 as schemaType,C536870924 as mSelFormLocked,C536870932 as hpdCaseType from "+SHR_ConsolidatedListTableName+" where 1 = 1");
		stringBuffer
				.append(" AND ( C260000000<>'已关闭')AND (C260000000<>'一线支持已解决') AND (C260000000<>'二线支持已解决') AND (C260000000<>'已解决并等待用户确认') AND (C260000000<>'Closed')");
		stringBuffer.append("AND ((C4='" + loginname + "' ) OR (C4='"
				+ fullname + "')OR ((C4 is null )AND (C240000006 is not null)");

		String groupSql = "";
		if (!grouplistid.equals("")) {
			String[] tmpgrouplist = grouplistid.split(";");
			StringBuffer groupBuffer = new StringBuffer();
			int flag = 0;
			for (int i = 0; i < tmpgrouplist.length; i++) {
				if (flag == 0) {
					groupBuffer
							.append(" C240000010 ='" + tmpgrouplist[i] + "'");
					flag = 1;
				} else {
					groupBuffer.append(" or C240000010 ='" + tmpgrouplist[i]
							+ "'");
				}
			}
			groupSql = " and (" + groupBuffer.toString() + ")";
		}

		stringBuffer.append(groupSql);
		stringBuffer
				.append("))AND (((((C260000000<>'Closed') AND (C260000009<6))");
		stringBuffer.append(" OR ");
		stringBuffer.append("(C536871003=3)");
		stringBuffer.append(")");
		stringBuffer.append("AND(C230000009='MAINHELPDESK'))");

		stringBuffer.append("OR ((C260000000<>'已关闭')AND (C260000000<>'已驳回')");
		stringBuffer.append("AND (C230000009='MAINCHANGE')");
		stringBuffer.append("AND (C260000009<7))");

		stringBuffer.append("OR ((C230000009='MAINTASK') AND (C260000009<4))");
		stringBuffer
				.append("OR ((C230000009='MAINPROBLEM') AND (C260000009<4 or C260000009=6))");

		stringBuffer
				.append("OR ((C230000009='MAINOPERATION') AND (C260000009=2))");
		stringBuffer.append("OR ((C230000009='OPERATION') AND (C260000009<4))");

		stringBuffer
				.append(") AND ((C536870915 IS NULL) OR (C536870915='Original'))");

		return stringBuffer.toString();
	}

	// GZ_Supervisor 故障监控调度 正式机：T1076 测试机：T1151   公司：T1066
	private String getGZSupervisor(String grouplistname) {
		StringBuffer stringBuffer = new StringBuffer();

		stringBuffer
				.append(" select C1 as Chr_CaseId,C230000009 as SchemaNames,C536870918 as eventid,C536870925 as Summary,C2 as Submitter,C536870964 as Status,C536870928 as C260000126,C536870960,C536870923,C536870939 as AssignedToGroup,C536870933 as AssignedTo,C536870924 as mDTResolvedTime,C536870963 as mChrFormLock,C536870952 as schemaType,C536870922 as mSelFormLocked,C536870945 as hpdCaseType from "+GZ_SupervisorTableName+" t ");
		stringBuffer.append(" where C536870920 <> 5 and ('" + grouplistname
				+ "'  LIKE  '%' || C536870939 || '%' )");

		return stringBuffer.toString();
	}

	// YZGJ_Supervisor 严重告警监控调度 正式机：T1066 测试机：T1128    公司：T1069
	private String getYZGJSupervisor(String fullname, String grouplistname) {
		StringBuffer stringBuffer = new StringBuffer();

		stringBuffer
				.append(" select C1 as Chr_CaseId,C230000009 as SchemaNames,C536870918 as eventid,C536870925 as Summary,C2 as Submitter,C536870971 as Status,C536870928 as C260000126,C536870960,C536870923,C536870939 as AssignedToGroup,C536870933 as AssignedTo,C536870942 as mDTResolvedTime,C536870963 as mChrFormLock,C536870952 as schemaType,C536870922 as mSelFormLocked,C536870945 as hpdCaseType from "+YZGJ_SupervisorTableName+" t ");
		stringBuffer
				.append(" where (C536870969 = 2 or C536870969 = 0) and C536870933 is null and ('"
						+ grouplistname
						+ "'  LIKE  '%' || C536870939 || '%' AND C536870928 IS NOT NULL) ");
		stringBuffer.append(" union all ");
		stringBuffer
				.append("select C1 as Chr_CaseId,C230000009 as SchemaNames,C536870918 as eventid,C536870925 as Summary,C2 as Submitter,C536870971 as Status,C536870928 as C260000126,C536870960,C536870923,C536870939 as AssignedToGroup,C536870933 as AssignedTo,C536870942 as mDTResolvedTime,C536870963 as mChrFormLock,C536870952 as schemaType,C536870922 as mSelFormLocked,C536870945 as hpdCaseType from "+YZGJ_SupervisorTableName+" t ");
		stringBuffer
				.append(" where (C536870969 = 2 or C536870969 = 0) and t.C536870933 = '"
						+ fullname + "'");

		return stringBuffer.toString();
	}

	// WF:ZJYD_User_Audit --人员审计
	// --936:WF:App_Base_Infor  923:WF:App_DealProcess 947:WF:App_AuditingProcess
	private String getZJYD_User_Audit(String grouplistid, String loginName) {
		StringBuffer stringBuffer = new StringBuffer();

		stringBuffer
				.append(" select dp.C1 as processid,C700000001 as SchemaNames,base.C700000000 as eventid,C700000011 as Summary,C700000004 as Submitter,C700000010 as Status,'' as C260000126,C700000002 as C260000003,C700020015 as C260000502,C700020105 as AssignedToGroup,C700020005 as AssignedTo,C700000018 as BaseDealOutTime,C700020043 as ProcessType,'1' as ipID,C700000008 as mDTResolvedTime,C700020007 as groupname  from "+WF_App_Base_InforTableName+" base, "+WF_App_DealProcessTableName+" dp where base.C700000000 = dp.C700020001 and base.C700000001 = dp.C700020002 and dp.C700020020 = '1'AND (C700020016 is null AND (C700020006 = '"
						+ loginName
						+ "' or C700020048 = '"
						+ loginName
						+ "' OR '"
						+ grouplistid
						+ "' like '%;' || C700020008 || ';%') )"
						+ "and (C700020019 = '0' or C700020019 = '1' or C700020019 = '2' or C700020019 = '4')"
						+ "AND (C700000001 = 'WF:ZJYD_User_Audit')");

		stringBuffer.append(" union all ");
		stringBuffer
				.append(" select ap.C1 as processid,C700000001 as SchemaNames,base.C700000000 as eventid,C700000011 as Summary,C700000004 as Submitter,C700000010 as Status,'' as C260000126,C700000002 as C260000003,C700020015 as C260000502,C700020103 as AssignedToGroup,C700020005 as AssignedTo,C700000018 as BaseDealOutTime,C700020043 as ProcessType,'1' as ipID,C700000008 as mDTResolvedTime,C700020007 as groupname  from "+WF_App_Base_InforTableName+" base,"+WF_App_AuditingProcessTableName+" ap  where base.C700000000 = ap.C700020001  and base.C700000001 = ap.C700020002 and ap.C700020020 = '1'AND (C700020016 is null AND (C700020006 = '"
						+ loginName
						+ "' or C700020048 = '"
						+ loginName
						+ "' OR '"
						+ grouplistid
						+ "' like '%;' || C700020008 || ';%') )"
						+ "and (C700020019 = '3' or C700020019 = '5')"
						+ "AND (C700000001 = 'WF:ZJYD_User_Audit')");

		return stringBuffer.toString();
	}

	// 意见建议 WF:ZJYD_City_Suggest_you_hua
	// --936:WF:App_Base_Infor  923:WF:App_DealProcess
	private String getZJYD_City_Suggest(String grouplistid, String loginName) {
		StringBuffer stringBuffer = new StringBuffer();

		stringBuffer
				.append(" select dp.C1 as processid,C700000001 as SchemaNames,base.C700000000 as eventid,C700000011 as Summary,C700000004 as Submitter,C700000010 as Status,'' as C260000126,C700000002 as C260000003,C700020015 as C260000502,C700020103 as AssignedToGroup,C700020005 as AssignedTo,C700000018 as BaseDealOutTime,C700020043 as ProcessType,'1' as ipID,C700000008 as mDTResolvedTime,C700020007 as groupname from "+WF_App_Base_InforTableName+" base,"+WF_App_DealProcessTableName+" dp where base.C700000000 = dp.C700020001 and base.C700000001 = dp.C700020002 and dp.C700020020 = '1' AND (C700020016 is null AND (C700020006 = '"
						+ loginName
						+ "' or C700020048 = '"
						+ loginName
						+ "' OR '"
						+ grouplistid
						+ "' like '%;' || C700020008 || ';%') )"
						+ "and (C700020019 = '0' or C700020019 = '1' or C700020019 = '2' or C700020019 = '4')"
						+ "AND (C700000001 = 'WF:ZJYD_City_Suggest_you_hua')");
		stringBuffer.append(" union all ");
		stringBuffer
				.append(" select ap.C1 as processid,C700000001 as SchemaNames,base.C700000000 as eventid,C700000011 as Summary,C700000004 as Submitter,C700000010 as Status,'' as C260000126,C700000002 as C260000003,C700020015 as C260000502,C700020103 as AssignedToGroup,C700020005 as AssignedTo,C700000018 as BaseDealOutTime,C700020043 as ProcessType,'1' as ipID,C700000008 as mDTResolvedTime,C700020007 as groupname from "+WF_App_Base_InforTableName+" base,"+WF_App_AuditingProcessTableName+" ap where base.C700000000 = ap.C700020001 and base.C700000001 = ap.C700020002 and ap.C700020020 = '1' AND (C700020016 is null AND (C700020006 = '"
						+ loginName
						+ "' or C700020048 = '"
						+ loginName
						+ "' OR '"
						+ grouplistid
						+ "' like '%;' || C700020008 || ';%') )"
						+ "and (C700020019 = '3' or C700020019 = '5')"
						+ "AND (C700000001 = 'WF:ZJYD_City_Suggest_you_hua')");

		return stringBuffer.toString();
	}

	// 地市考核管理 WF:ZJYD_City_Examine
	// --936:WF:App_Base_Infor  923:WF:App_DealProcess
	private String getZJYD_City_Examine(String grouplistid, String loginName) {
		StringBuffer stringBuffer = new StringBuffer();

		stringBuffer
				.append(" select dp.C1 as processid,C700000001 as SchemaNames,base.C700000000 as eventid,C700000011 as Summary,C700000004 as Submitter,C700000010 as Status,'' as C260000126,C700000002 as C260000003,C700020015 as C260000502,C700020103 as AssignedToGroup,C700020005 as AssignedTo,C700000018 as BaseDealOutTime,C700020043 as ProcessType,'1' as ipID,C700000008 as mDTResolvedTime,C700020007 as groupname from "+WF_App_Base_InforTableName+" base,"+WF_App_DealProcessTableName+" dp where base.C700000000 = dp.C700020001 and base.C700000001 = dp.C700020002 and dp.C700020020 = '1' AND (C700020016 is null AND (C700020006 = '"
						+ loginName
						+ "' or C700020048 = '"
						+ loginName
						+ "' OR '"
						+ grouplistid
						+ "' like '%;' || C700020008 || ';%') )"
						+ "and (C700020019 = '0' or C700020019 = '1' or C700020019 = '2' or C700020019 = '4')"
						+ "AND (C700000001 = 'WF:ZJYD_City_Examine')");
		stringBuffer.append(" union all ");
		stringBuffer
				.append(" select ap.C1 as processid,C700000001 as SchemaNames,base.C700000000 as eventid,C700000011 as Summary,C700000004 as Submitter,C700000010 as Status,'' as C260000126,C700000002 as C260000003,C700020015 as C260000502,C700020103 as AssignedToGroup,C700020005 as AssignedTo,C700000018 as BaseDealOutTime,C700020043 as ProcessType,'1' as ipID,C700000008 as mDTResolvedTime,C700020007 as groupname from "+WF_App_Base_InforTableName+" base,"+WF_App_AuditingProcessTableName+" ap where base.C700000000 = ap.C700020001 and base.C700000001 = ap.C700020002 and ap.C700020020 = '1' AND (C700020016 is null AND (C700020006 = '"
						+ loginName
						+ "' or C700020048 = '"
						+ loginName
						+ "' OR '"
						+ grouplistid
						+ "' like '%;' || C700020008 || ';%') )"
						+ "and (C700020019 = '3' or C700020019 = '5')"
						+ "AND (C700000001 = 'WF:ZJYD_City_Examine')");

		return stringBuffer.toString();
	}

	// 工作配合 WF:ZJYD_Work_Cooperate
	// --936:WF:App_Base_Infor  923:WF:App_DealProcess
	private String getZJYD_Work_Cooperate(String grouplistid, String loginName) {
		StringBuffer stringBuffer = new StringBuffer();

		stringBuffer
				.append(" select dp.C1 as processid,C700000001 as SchemaNames,base.C700000000 as eventid,C700000011 as Summary,C700000004 as Submitter,C700000010 as Status,'' as C260000126,C700000002 as C260000003,C700020015 as C260000502,C700020103 as AssignedToGroup,C700020005 as AssignedTo,C700000018 as BaseDealOutTime,C700020043 as ProcessType,'1' as ipID,C700000008 as mDTResolvedTime,C700020007 as groupname from "+WF_App_Base_InforTableName+" base,"+WF_App_DealProcessTableName+" dp where base.C700000000 = dp.C700020001 and base.C700000001 = dp.C700020002 and dp.C700020020 = '1' AND (C700020016 is null AND (C700020006 = '"
						+ loginName
						+ "' or C700020048 = '"
						+ loginName
						+ "' OR '"
						+ grouplistid
						+ "' like '%;' || C700020008 || ';%') )"
						+ "and (C700020019 = '0' or C700020019 = '1' or C700020019 = '2' or C700020019 = '4')"
						+ "AND (C700000001 = 'WF:ZJYD_Work_Cooperate')");
		stringBuffer.append(" union all ");
		stringBuffer
				.append(" select ap.C1 as processid,C700000001 as SchemaNames,base.C700000000 as eventid,C700000011 as Summary,C700000004 as Submitter,C700000010 as Status,'' as C260000126,C700000002 as C260000003,C700020015 as C260000502,C700020103 as AssignedToGroup,C700020005 as AssignedTo,C700000018 as BaseDealOutTime,C700020043 as ProcessType,'1' as ipID,C700000008 as mDTResolvedTime,C700020007 as groupname from "+WF_App_Base_InforTableName+" base,"+WF_App_AuditingProcessTableName+" ap where base.C700000000 = ap.C700020001 and base.C700000001 = ap.C700020002 and ap.C700020020 = '1' AND (C700020016 is null AND (C700020006 = '"
						+ loginName
						+ "' or C700020048 = '"
						+ loginName
						+ "' OR '"
						+ grouplistid
						+ "' like '%;' || C700020008 || ';%') )"
						+ "and (C700020019 = '3' or C700020019 = '5')"
						+ "AND (C700000001 = 'WF:ZJYD_Work_Cooperate')");

		return stringBuffer.toString();
	}
	
	
	// 防火墙开通   WF:ZJYD_Firewall_Change
	// --936:WF:App_Base_Infor  923:WF:App_DealProcess
	private String getZJYD_Firewall_Change(String grouplistid, String loginName) {
		StringBuffer stringBuffer = new StringBuffer();

		stringBuffer
				.append(" select dp.C1 as processid,C700000001 as SchemaNames,base.C700000000 as eventid,C700000011 as Summary,C700000004 as Submitter,C700000010 as Status,'' as C260000126,C700000002 as C260000003,C700020015 as C260000502,C700020103 as AssignedToGroup,C700020005 as AssignedTo,C700000018 as BaseDealOutTime,C700020043 as ProcessType,'1' as ipID,C700000008 as mDTResolvedTime,C700020007 as groupname from "+WF_App_Base_InforTableName+" base,"+WF_App_DealProcessTableName+" dp where base.C700000000 = dp.C700020001 and base.C700000001 = dp.C700020002 and dp.C700020020 = '1' AND (C700020016 is null AND (C700020006 = '"
						+ loginName
						+ "' or C700020048 = '"
						+ loginName
						+ "' OR '"
						+ grouplistid
						+ "' like '%;' || C700020008 || ';%') )"
						+ "and (C700020019 = '0' or C700020019 = '1' or C700020019 = '2' or C700020019 = '4')"
						+ "AND (C700000001 = 'WF:ZJYD_Firewall_Change')");
		stringBuffer.append(" union all ");
		stringBuffer
				.append(" select ap.C1 as processid,C700000001 as SchemaNames,base.C700000000 as eventid,C700000011 as Summary,C700000004 as Submitter,C700000010 as Status,'' as C260000126,C700000002 as C260000003,C700020015 as C260000502,C700020103 as AssignedToGroup,C700020005 as AssignedTo,C700000018 as BaseDealOutTime,C700020043 as ProcessType,'1' as ipID,C700000008 as mDTResolvedTime,C700020007 as groupname from "+WF_App_Base_InforTableName+" base,"+WF_App_AuditingProcessTableName+" ap where base.C700000000 = ap.C700020001 and base.C700000001 = ap.C700020002 and ap.C700020020 = '1' AND (C700020016 is null AND (C700020006 = '"
						+ loginName
						+ "' or C700020048 = '"
						+ loginName
						+ "' OR '"
						+ grouplistid
						+ "' like '%;' || C700020008 || ';%') )"
						+ "and (C700020019 = '3' or C700020019 = '5')"
						+ "AND (C700000001 = 'WF:ZJYD_Firewall_Change')");

		return stringBuffer.toString();
	}
	
	

	// 防火墙清退   WF:ZJYD_Firewall_Change_Free
	// --936:WF:App_Base_Infor  923:WF:App_DealProcess
	private String getZJYD_Firewall_Change_Free(String grouplistid, String loginName) {
		StringBuffer stringBuffer = new StringBuffer();

		stringBuffer
				.append(" select dp.C1 as processid,C700000001 as SchemaNames,base.C700000000 as eventid,C700000011 as Summary,C700000004 as Submitter,C700000010 as Status,'' as C260000126,C700000002 as C260000003,C700020015 as C260000502,C700020103 as AssignedToGroup,C700020005 as AssignedTo,C700000018 as BaseDealOutTime,C700020043 as ProcessType,'1' as ipID,C700000008 as mDTResolvedTime,C700020007 as groupname from "+WF_App_Base_InforTableName+" base,"+WF_App_DealProcessTableName+" dp where base.C700000000 = dp.C700020001 and base.C700000001 = dp.C700020002 and dp.C700020020 = '1' AND (C700020016 is null AND (C700020006 = '"
						+ loginName
						+ "' or C700020048 = '"
						+ loginName
						+ "' OR '"
						+ grouplistid
						+ "' like '%;' || C700020008 || ';%') )"
						+ "and (C700020019 = '0' or C700020019 = '1' or C700020019 = '2' or C700020019 = '4')"
						+ "AND (C700000001 = 'WF:ZJYD_Firewall_Change_Free')");
		stringBuffer.append(" union all ");
		stringBuffer
				.append(" select ap.C1 as processid,C700000001 as SchemaNames,base.C700000000 as eventid,C700000011 as Summary,C700000004 as Submitter,C700000010 as Status,'' as C260000126,C700000002 as C260000003,C700020015 as C260000502,C700020103 as AssignedToGroup,C700020005 as AssignedTo,C700000018 as BaseDealOutTime,C700020043 as ProcessType,'1' as ipID,C700000008 as mDTResolvedTime,C700020007 as groupname from "+WF_App_Base_InforTableName+" base,"+WF_App_AuditingProcessTableName+" ap where base.C700000000 = ap.C700020001 and base.C700000001 = ap.C700020002 and ap.C700020020 = '1' AND (C700020016 is null AND (C700020006 = '"
						+ loginName
						+ "' or C700020048 = '"
						+ loginName
						+ "' OR '"
						+ grouplistid
						+ "' like '%;' || C700020008 || ';%') )"
						+ "and (C700020019 = '3' or C700020019 = '5')"
						+ "AND (C700000001 = 'WF:ZJYD_Firewall_Change_Free')");

		return stringBuffer.toString();
	}
	

	// 机房人员出入管理   WF:ZJYD_MachineRoom_InAndOut
	// --936:WF:App_Base_Infor  923:WF:App_DealProcess
	private String getZJYD_MachineRoom_InAndOut(String grouplistid, String loginName) {
		StringBuffer stringBuffer = new StringBuffer();

		stringBuffer
				.append(" select dp.C1 as processid,C700000001 as SchemaNames,base.C700000000 as eventid,C700000011 as Summary,C700000004 as Submitter,C700000010 as Status,'' as C260000126,C700000002 as C260000003,C700020015 as C260000502,C700020103 as AssignedToGroup,C700020005 as AssignedTo,C700000018 as BaseDealOutTime,C700020043 as ProcessType,'1' as ipID,C700000008 as mDTResolvedTime,C700020007 as groupname from "+WF_App_Base_InforTableName+" base,"+WF_App_DealProcessTableName+" dp where base.C700000000 = dp.C700020001 and base.C700000001 = dp.C700020002 and dp.C700020020 = '1' AND (C700020016 is null AND (C700020006 = '"
						+ loginName
						+ "' or C700020048 = '"
						+ loginName
						+ "' OR '"
						+ grouplistid
						+ "' like '%;' || C700020008 || ';%') )"
						+ "and (C700020019 = '0' or C700020019 = '1' or C700020019 = '2' or C700020019 = '4')"
						+ "AND (C700000001 = 'WF:ZJYD_MachineRoom_InAndOut')");
		stringBuffer.append(" union all ");
		stringBuffer
				.append(" select ap.C1 as processid,C700000001 as SchemaNames,base.C700000000 as eventid,C700000011 as Summary,C700000004 as Submitter,C700000010 as Status,'' as C260000126,C700000002 as C260000003,C700020015 as C260000502,C700020103 as AssignedToGroup,C700020005 as AssignedTo,C700000018 as BaseDealOutTime,C700020043 as ProcessType,'1' as ipID,C700000008 as mDTResolvedTime,C700020007 as groupname from "+WF_App_Base_InforTableName+" base,"+WF_App_AuditingProcessTableName+" ap where base.C700000000 = ap.C700020001 and base.C700000001 = ap.C700020002 and ap.C700020020 = '1' AND (C700020016 is null AND (C700020006 = '"
						+ loginName
						+ "' or C700020048 = '"
						+ loginName
						+ "' OR '"
						+ grouplistid
						+ "' like '%;' || C700020008 || ';%') )"
						+ "and (C700020019 = '3' or C700020019 = '5')"
						+ "AND (C700000001 = 'WF:ZJYD_MachineRoom_InAndOut')");

		return stringBuffer.toString();
	}
	
	
	// 日常考评   WF:ZJYD_Parter_Manage
	// --936:WF:App_Base_Infor  923:WF:App_DealProcess
	private String getZJYD_Parter_Manage(String grouplistid, String loginName) {
		StringBuffer stringBuffer = new StringBuffer();

		stringBuffer
				.append(" select dp.C1 as processid,C700000001 as SchemaNames,base.C700000000 as eventid,C700000011 as Summary,C700000004 as Submitter,C700000010 as Status,'' as C260000126,C700000002 as C260000003,C700020015 as C260000502,C700020103 as AssignedToGroup,C700020005 as AssignedTo,C700000018 as BaseDealOutTime,C700020043 as ProcessType,'1' as ipID,C700000008 as mDTResolvedTime,C700020007 as groupname from "+WF_App_Base_InforTableName+" base,"+WF_App_DealProcessTableName+" dp where base.C700000000 = dp.C700020001 and base.C700000001 = dp.C700020002 and dp.C700020020 = '1' AND (C700020016 is null AND (C700020006 = '"
						+ loginName
						+ "' or C700020048 = '"
						+ loginName
						+ "' OR '"
						+ grouplistid
						+ "' like '%;' || C700020008 || ';%') )"
						+ "and (C700020019 = '0' or C700020019 = '1' or C700020019 = '2' or C700020019 = '4')"
						+ "AND (C700000001 = 'WF:ZJYD_Parter_Manage')");
		stringBuffer.append(" union all ");
		stringBuffer
				.append(" select ap.C1 as processid,C700000001 as SchemaNames,base.C700000000 as eventid,C700000011 as Summary,C700000004 as Submitter,C700000010 as Status,'' as C260000126,C700000002 as C260000003,C700020015 as C260000502,C700020103 as AssignedToGroup,C700020005 as AssignedTo,C700000018 as BaseDealOutTime,C700020043 as ProcessType,'1' as ipID,C700000008 as mDTResolvedTime,C700020007 as groupname from "+WF_App_Base_InforTableName+" base,"+WF_App_AuditingProcessTableName+" ap where base.C700000000 = ap.C700020001 and base.C700000001 = ap.C700020002 and ap.C700020020 = '1' AND (C700020016 is null AND (C700020006 = '"
						+ loginName
						+ "' or C700020048 = '"
						+ loginName
						+ "' OR '"
						+ grouplistid
						+ "' like '%;' || C700020008 || ';%') )"
						+ "and (C700020019 = '3' or C700020019 = '5')"
						+ "AND (C700000001 = 'WF:ZJYD_Parter_Manage')");

		return stringBuffer.toString();
	}
	
	// 安全自查   WF:ZJYD_Save_Manage
	// --936:WF:App_Base_Infor  923:WF:App_DealProcess
	private String getZJYD_Save_Manage(String grouplistid, String loginName) {
		StringBuffer stringBuffer = new StringBuffer();

		stringBuffer
				.append(" select dp.C1 as processid,C700000001 as SchemaNames,base.C700000000 as eventid,C700000011 as Summary,C700000004 as Submitter,C700000010 as Status,'' as C260000126,C700000002 as C260000003,C700020015 as C260000502,C700020103 as AssignedToGroup,C700020005 as AssignedTo,C700000018 as BaseDealOutTime,C700020043 as ProcessType,'1' as ipID,C700000008 as mDTResolvedTime,C700020007 as groupname from "+WF_App_Base_InforTableName+" base,"+WF_App_DealProcessTableName+" dp where base.C700000000 = dp.C700020001 and base.C700000001 = dp.C700020002 and dp.C700020020 = '1' AND (C700020016 is null AND (C700020006 = '"
						+ loginName
						+ "' or C700020048 = '"
						+ loginName
						+ "' OR '"
						+ grouplistid
						+ "' like '%;' || C700020008 || ';%') )"
						+ "and (C700020019 = '0' or C700020019 = '1' or C700020019 = '2' or C700020019 = '4')"
						+ "AND (C700000001 = 'WF:ZJYD_Save_Manage')");
		stringBuffer.append(" union all ");
		stringBuffer
				.append(" select ap.C1 as processid,C700000001 as SchemaNames,base.C700000000 as eventid,C700000011 as Summary,C700000004 as Submitter,C700000010 as Status,'' as C260000126,C700000002 as C260000003,C700020015 as C260000502,C700020103 as AssignedToGroup,C700020005 as AssignedTo,C700000018 as BaseDealOutTime,C700020043 as ProcessType,'1' as ipID,C700000008 as mDTResolvedTime,C700020007 as groupname from "+WF_App_Base_InforTableName+" base,"+WF_App_AuditingProcessTableName+" ap where base.C700000000 = ap.C700020001 and base.C700000001 = ap.C700020002 and ap.C700020020 = '1' AND (C700020016 is null AND (C700020006 = '"
						+ loginName
						+ "' or C700020048 = '"
						+ loginName
						+ "' OR '"
						+ grouplistid
						+ "' like '%;' || C700020008 || ';%') )"
						+ "and (C700020019 = '3' or C700020019 = '5')"
						+ "AND (C700000001 = 'WF:ZJYD_Save_Manage')");

		return stringBuffer.toString();
	}

	// 合同回顾   WF:ZJYD_Contract_Review_Manage
	// --936:WF:App_Base_Infor  923:WF:App_DealProcess
	private String getZJYD_Contract_Review_Manage(String grouplistid, String loginName) {
		StringBuffer stringBuffer = new StringBuffer();

		stringBuffer
				.append(" select dp.C1 as processid,C700000001 as SchemaNames,base.C700000000 as eventid,C700000011 as Summary,C700000004 as Submitter,C700000010 as Status,'' as C260000126,C700000002 as C260000003,C700020015 as C260000502,C700020103 as AssignedToGroup,C700020005 as AssignedTo,C700000018 as BaseDealOutTime,C700020043 as ProcessType,'1' as ipID,C700000008 as mDTResolvedTime,C700020007 as groupname from "+WF_App_Base_InforTableName+" base,"+WF_App_DealProcessTableName+" dp where base.C700000000 = dp.C700020001 and base.C700000001 = dp.C700020002 and dp.C700020020 = '1' AND (C700020016 is null AND (C700020006 = '"
						+ loginName
						+ "' or C700020048 = '"
						+ loginName
						+ "' OR '"
						+ grouplistid
						+ "' like '%;' || C700020008 || ';%') )"
						+ "and (C700020019 = '0' or C700020019 = '1' or C700020019 = '2' or C700020019 = '4')"
						+ "AND (C700000001 = 'WF:ZJYD_Contract_Review_Manage')");
		stringBuffer.append(" union all ");
		stringBuffer
				.append(" select ap.C1 as processid,C700000001 as SchemaNames,base.C700000000 as eventid,C700000011 as Summary,C700000004 as Submitter,C700000010 as Status,'' as C260000126,C700000002 as C260000003,C700020015 as C260000502,C700020103 as AssignedToGroup,C700020005 as AssignedTo,C700000018 as BaseDealOutTime,C700020043 as ProcessType,'1' as ipID,C700000008 as mDTResolvedTime,C700020007 as groupname from "+WF_App_Base_InforTableName+" base,"+WF_App_AuditingProcessTableName+" ap where base.C700000000 = ap.C700020001 and base.C700000001 = ap.C700020002 and ap.C700020020 = '1' AND (C700020016 is null AND (C700020006 = '"
						+ loginName
						+ "' or C700020048 = '"
						+ loginName
						+ "' OR '"
						+ grouplistid
						+ "' like '%;' || C700020008 || ';%') )"
						+ "and (C700020019 = '3' or C700020019 = '5')"
						+ "AND (C700000001 = 'WF:ZJYD_Contract_Review_Manage')");

		return stringBuffer.toString();
	}
	

	// ZMCC:Problem_New 新问题单 生产机：1014     公司：T1009
	private String getProblem_New(String grouplistid, String fullName, String loginName) {
		StringBuffer stringBuffer = new StringBuffer();

		stringBuffer
				.append(" select c1 eventid,c536871200 schemaname,c536870917 problemid,c536870916 Summary,c536870981 submitter,c536870988 status, case c536870924 when 3 then 0 when 2 then 1 when 1 then 2 when 0 then 3 end as C260000126,c536871201 as C260000003 ,c536870950 as C260000502,c536870926 AssignedToGroup,c536870927 AssignedTo,c536871110 escTime,'','',1 as mDTResolvedTime,'' from "+ZMCC_Problem_NewTableName+" where " +
						"((((c7 = 0 or c7 = 1 or c7 = 2 or c7 = 6) and c536870922 is null) " +
						"or (c7 = 1 and (c536870922 = 1 or c536870922 = 2)) or (c7 = 2 and c536870922 = 2)) and ((c536870927 = '"
						+ fullName
						+ "' ) OR (';"
						+ grouplistid
						+ "' like '%;' || c536871185 || ';%' AND c536870927 is null)))");
		stringBuffer.append(" OR (" +
			            			"(C536870922='0' and ((c7='4') or (c7='5'))) " +
			            			"AND (';"+grouplistid+"' like '%;40200;%')" +
            					") " + 
		            		"OR (" +
		            				"c7='5' and C536870922='4' " +
		            				"AND ((c536871202='"+loginName+"') or (c536871203='"+loginName+"'))" +
            					")"
				            		);

		return stringBuffer.toString();
	}

	
	// QC_Appeal  质量管理   T317
	private String getQC_Appeal(String grouplistname, String loginName) {
		StringBuffer stringBuffer = new StringBuffer();

		stringBuffer
				.append(" select C1 as Request_ID,C536870913 as mChrAppealTitle,C536870919 as mChrReviewMatter,C240000001 as Requester_Name_, C536870945 as mChrSupportPerson,C536870954 as mChrStatus,C536870937 as mSelAuditStatus,C536870938 as mSelEscalateStatus from "+QC_AppealTableName+" where 1=1 and (C7 != 5 AND ((C536870955 = 0 AND C536870943 = '" 
						+ loginName 
						+ "') OR(C7 = 4 AND C240000001 = '" 
						+ loginName
						+ "') OR ((C536870945 = '" 
						+ loginName
						+ "' OR (C536870945 IS NULL AND '"
						+ grouplistname
						+ "' LIKE '%' || C536870944 || '%')) AND (C7 = 2 OR C7 = 3 OR C536870955 = 2))))");

		return stringBuffer.toString();
	}
	
	
	// 待审批  SHR:ConsolidatedList   T398
	private String getAudit(String grouplistname, String loginName) {
		StringBuffer stringBuffer = new StringBuffer();

		stringBuffer
				.append(" select C260000005 as Chr_CaseId,C230000009 as SchemaNames,C536870931 as ApprovalId4Chg,C536870913 as eventid,C8 as Summary,C2 as Submitter,C260000000 as Status,C260000126 as Priority,C260000003,C260000502,C240000006 as AssignedToGroup,C4 as AssignedTo,C536871046 as mDTResolvedTime,C536870924 as mSelFormLocked  from "+SHR_ConsolidatedListTableName+"  where 1 = 1"
						+ " AND ((C230000009 = 'MAINPROBLEM' and C260000009 = 4 and '"
						+ grouplistname
						+ "' like'%问题经理%' and C536870922 = 0)OR((C230000009 = 'MAINOPERATION' OR C230000009 = 'MAINCHANGE') and C536870922 = 0 and C536870930 LIKE '%"
						+loginName
						+ "%' and C260000000 <> '已关闭'))");

		return stringBuffer.toString();
	}

	public List<WaitDealBean> getList() {
		return list;
	}

	public void setList(List<WaitDealBean> list) {
		this.list = list;
	}

}
