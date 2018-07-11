package com.jiajie.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.jiajie.bean.foreignkey.ForeignBean;
import com.jiajie.bean.foreignkey.ForeignColumnBean;
import com.jiajie.bean.foreignkey.ForeignTableBean;


public class XmlManage {
	private File inputXml;
	
	public XmlManage(File inputXml) {
		this.inputXml = inputXml;
	}

	public Document getDocument() {
		SAXReader saxReader = new SAXReader();
		Document document = null;
		try {
			document = saxReader.read(inputXml);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return document;
	}

	public Element getRootElement() {
		return getDocument().getRootElement();
	}

	@SuppressWarnings("unchecked")
	public List<ForeignBean> traversalDocumentByIterator() {
		List<ForeignBean> foreignBeanList = new ArrayList<ForeignBean>();
		Element root = getRootElement();
		// 枚举根节点下所有子节点
		for (Iterator ie = root.elementIterator(); ie.hasNext();) {
			Element element = (Element) ie.next();
			ForeignBean foreignBean = new ForeignBean();
			List<ForeignColumnBean> foreignColumnList = new ArrayList<ForeignColumnBean>();
			
			
			// 枚举属性
			for (Iterator ia = element.attributeIterator(); ia.hasNext();) {
				Attribute attribute = (Attribute) ia.next();
				if("name".equals(attribute.getName())) {
					//判断表是否存在
					
					foreignBean.setForeignName(attribute.getData().toString());
				} else if("comment".equals(attribute.getName())) {
					foreignBean.setForeignComment(attribute.getData().toString());
				} else if("isEnable".equals(attribute.getName())) {
					foreignBean.setEnable(Boolean.valueOf(attribute.getData().toString()));
				}
			}
			// 枚举当前节点下所有子节点
			for (Iterator columnie = element.elementIterator(); columnie.hasNext();) {
				Element columnElement = (Element) columnie.next();
				ForeignColumnBean foreignColumnBean = new ForeignColumnBean();
				List<ForeignTableBean> foreignTableList = new ArrayList<ForeignTableBean>();
				for (Iterator ia = columnElement.attributeIterator(); ia.hasNext();) {
					Attribute attribute = (Attribute) ia.next();
					if("name".equals(attribute.getName())) {
						//判断列是否存在
						
						foreignColumnBean.setColumnName(attribute.getData().toString());
					} else if("cue".equals(attribute.getName())) {
						foreignColumnBean.setColumnCue(attribute.getData().toString());
					} else if("isEnable".equals(attribute.getName())) {
						foreignColumnBean.setEnable(Boolean.valueOf(attribute.getData().toString()));
					}
				}
				// 枚举当前节点下所有子节点
				for (Iterator fortablenie = columnElement.elementIterator(); fortablenie.hasNext();) {
					Element fortablenieElement = (Element) fortablenie.next();
					ForeignTableBean foreignTableBean = new ForeignTableBean();
					for (Iterator ia = fortablenieElement.attributeIterator(); ia.hasNext();) {
						Attribute attribute = (Attribute) ia.next();
						if("table_name".equals(attribute.getName())) {
							//判断表是否存在
							
							foreignTableBean.setTableName(attribute.getData().toString());
						} else if("table_comment".equals(attribute.getName())) {
							foreignTableBean.setTableComment(attribute.getData().toString());
						} else if("id_name".equals(attribute.getName())) {
							//判断列是否存在
							
							foreignTableBean.setIdName(attribute.getData().toString());
						} else if("isEnable".equals(attribute.getName())) {
							foreignTableBean.setEnable(Boolean.valueOf(attribute.getData().toString()));
						}
					}
					foreignTableList.add(foreignTableBean);
				}
				if(foreignTableList != null && foreignTableList.size() > 0) {
					foreignColumnBean.setForeignTableList(foreignTableList);
				}
				foreignColumnList.add(foreignColumnBean);
			}
			if(foreignColumnList != null && foreignColumnList.size() > 0) {
				foreignBean.setForeignColumnList(foreignColumnList);
			}
			foreignBeanList.add(foreignBean);
		}
		return foreignBeanList;
	}
	/** 
     * 获取WEB-INF目录下面server.xml文件的路径 
     * @return 
     */  
    public static String getXmlPath(String xmlName)  
    {  
        //file:/D:/JavaWeb/.metadata/.me_tcat/webapps/TestBeanUtils/WEB-INF/classes/   
        String path=Thread.currentThread().getContextClassLoader().getResource("").toString();  
        path=path.replace('/', '\\'); // 将/换成\  
        path=path.replace("file:", ""); //去掉file:  
        path=path.substring(1); //去掉第一个\,如 \D:\JavaWeb...  
        path+=xmlName;  
        return path;  
    }  
}