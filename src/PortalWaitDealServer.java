import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ultra.util.MD5;
import com.ultra.util.PortalWaitDealService;
import com.ultra.util.WaitDealBean;


public class PortalWaitDealServer {
	Log log = LogFactory.getLog(PortalWaitDealServer.class);
	public String getToDoItemList(String userxml) throws Exception{
		log.info("------------portal请求待办列表开始------------");
		String returnvalue = "";
		String userid = "";
		String ts = "";
		String pwd = "";
		Document doc = DocumentHelper.parseText(userxml);
		Element root = doc.getRootElement();
		List<Element> attrs = root.elements("attr");
		for(Element attr : attrs){
			if(null!=attr.attributeValue("name")){
				if("userid".equals(attr.attributeValue("name"))){
					userid = attr.getText();
				}else if("ts".equals(attr.attributeValue("name"))){
					ts = attr.getText();
				}else if("pwd".equals(attr.attributeValue("name"))){
					pwd = attr.getText();
				}
			}
		}
		MD5 m = new MD5();
		String pwdNew = m.md5s(userid+"BOMC_PORTAL"+ts+"000000");
		if(pwdNew.equals(pwd)){
			log.info("MD5鉴权成功 "+pwd+"="+pwdNew);
			List<WaitDealBean> list = new ArrayList<WaitDealBean>();
			PortalWaitDealService service = new PortalWaitDealService();
			service.getWaitDealList(userid);
			list = service.getList();
			if(null==list){
				return "no user";
			}
			log.info("^_^  工单待办列表集查询成功  ^_^");
			returnvalue = createXML(list);
			System.out.println(returnvalue);
		}else{
			return "MD5 test fail";
		}
		log.info("------------portal请求待办列表开始------------");
		return returnvalue;
	}
	
	private String createXML(List<WaitDealBean> list){
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("items");
		root.addAttribute("size", list.size()+"");
		int size = list.size();
		if(size>100){
			size = 100;
		}
		for(int i=0;i<size;i++){
			WaitDealBean bean = list.get(i);
			Element item = root.addElement("item");
			item.addElement("record").addText(bean.getRecord());
			item.addElement("title").addText(bean.getTitle());
			item.addElement("build").addText(bean.getBuild());
			item.addElement("status").addText(bean.getStatus());
			item.addElement("priority").addText(bean.getPriority());
			item.addElement("type").addText(bean.getType());
			item.addElement("begin").addText(bean.getBegin());
			item.addElement("group").addText(bean.getGroup());
			item.addElement("dispose").addText(bean.getDispose());
			item.addElement("end").addText(bean.getEnd());
			item.addElement("url").addText(bean.getUrl());
		}
		
		return doc.asXML();
	}
}
