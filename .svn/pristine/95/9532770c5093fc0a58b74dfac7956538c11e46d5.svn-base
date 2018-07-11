package com.jiajie.service.signup.impl;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import com.jiajie.bean.PageBean;
import org.apache.commons.lang.math.NumberUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;
import com.jiajie.action.signup.SignUpAction;
import com.jiajie.bean.MsgBean;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.signup.uploadCertificateService;
import com.jiajie.util.EmailUtil;
import com.jiajie.util.bmtime;

@Service("uploadCertificateService")
public class uploadCertificateServiceImpl extends ServiceBridge implements uploadCertificateService{

	@SuppressWarnings("unchecked")
	@Override
	public MsgBean getKsrs(String bmlcid) {
		String sql = "select JFPZ,LXR,LXDH,IFNULL(sl,0) AS SL,95 as JFBZ,IFNULL(95*SL,0) as JFJE from cus_kwbm_examround where bmlcid='"+bmlcid+"'";
		List<Object> list = getListSQL(sql);
		if(list!=null && list.size()>0){
			getMsgBean(list.get(0));
		}else{
			getMsgBean(null );
		}
		
		return this.MsgBean;
	}

	@Override
	public MsgBean save(String bmlcid, String pzpath,String lxr,String lxdh) {
		if(lxr==null || "".equals(lxr)){
			return getMsgBean(false,"联系人不能为空！");
		}
		if(lxdh==null || "".equals(lxdh)){
			return getMsgBean(false,"联系电话不能为空！");
		}
		if(bmlcid==null||"".equals(bmlcid)){
			return getMsgBean(false,"缴费凭证上传失败！");
		}
		List<Map<String,String>> ls = getSession().createSQLQuery("select SHFLAG from cus_kwbm_examround where BMLCID='"+bmlcid+"'").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		if("2".equals(ls.get(0).get("SHFLAG"))){
			String sql = "update cus_kwbm_examround set SHFLAG='3',SHSM='',JFPZ='"+pzpath+"',LXR='"+lxr+"',LXDH='"+lxdh+"' where bmlcid='"+bmlcid+"'";
			update(sql);
			return getMsgBean(true,"凭证上传成功！请等待审核！");	
		}else{
			boolean flag = bmtime.checkPZ();
			if(flag){
				String sql = "update cus_kwbm_examround set SHFLAG='3',SHSM='',JFPZ='"+pzpath+"',LXR='"+lxr+"',LXDH='"+lxdh+"' where bmlcid='"+bmlcid+"'";
				update(sql);
				return getMsgBean(true,"凭证上传成功！请等待审核！");
			}else{
				return getMsgBean(false,"不是报名时间，不能上传凭证");
			}
		}
		
	}
	
	public MsgBean getInvoice(String bmlcid){
		String sql = "select LXDH,EXAMNAME from cus_kwbm_examround where bmlcid='"+bmlcid+"'";
		List<Object> list = getListSQL(sql);
		getMsgBean(list.get(0));
		return this.MsgBean;
	}
	
	public MsgBean saveInvoice(String bmlcid,String dwname,String dpno,String dwaddress,String lxrdh,String email,String bankName,String bankNum,String phone,String remark,String number){
//		boolean flag = true;
		String st = "select fpzt from cus_kwbm_examround where BMLCID='"+bmlcid+"'";
		
		List<Map<String,Object>> lss=getListSQL(st);
		if(lss.size()!=0){
			if("1".equals(lss.get(0).get("fpzt"))){
				return getMsgBean(false,"已提交发票申请，不能新增发票信息！");
			}
		}
		
		if(dwname==null || "".equals(dwname)){
			return getMsgBean(false,"单位名称不能为空！");
		}
		if(dwaddress==null || "".equals(dwaddress)){
			return getMsgBean(false,"单位地址不能为空！");
		}
		if(dpno==null || "".equals(dpno)){
			return getMsgBean(false,"税务号不能为空！");
		}
		if(lxrdh==null || "".equals(lxrdh)){
			return getMsgBean(false,"联系电话不能为空！");
		}
		if(email==null || "".equals(email)){
			return getMsgBean(false,"电子邮箱不能为空！");
		}
		if(number==null || "".equals(number)){
			return getMsgBean(false,"人数不能为空！");
		}
		if(phone==null || "".equals(phone)){
			return getMsgBean(false,"推送手机不能为空！");
		}
		if(!NumberUtils.isNumber(number)){
			return getMsgBean(false,"人数必须是数字！");
		}
		String sql = "select sl from cus_kwbm_examround where bmlcid='"+bmlcid+"'";
		List<Map<String,Object>> list=getListSQL(sql);
		int allNumber = Integer.parseInt(list.get(0).get("sl").toString());
		if(Integer.parseInt(number)>allNumber){
			return getMsgBean(false,"总人数超过报名人数！");
		}
		String sql1 = "SELECT SUM(NUMBER) AS number FROM cus_kw_invoice_excal WHERE ISUSE = '1' AND BMLCID = '" + bmlcid + "'";
		List<Map<String,Object>> list1=getListSQL(sql1);
		int thatNumber = Integer.parseInt(number);
		if(list1.get(0).get("number") != null){
			int thisNumber = Integer.parseInt(list1.get(0).get("number").toString());
			allNumber = allNumber - thisNumber;
		}
		if(allNumber < thatNumber){
//			flag = false;
			return getMsgBean(false,"总人数超过报名人数！");
		}
		boolean boo = EmailUtil.emailFormat(email);
		if(!boo){
//			flag = false;
			return getMsgBean(false,"邮箱格式不正确！");
		}
		boolean bool = EmailUtil.isPhone(phone);
		if(phone!=null&&!"".equals(phone)){
			if(!bool){
//				flag = false;
				return getMsgBean(false,"推送手机号码格式错误！");
			}
		}

//		if(flag){
			WebApplicationContext wac = null;
			SessionFactory sf = null;   
			Session session = null;
			try {
				wac = ContextLoader.getCurrentWebApplicationContext(); 
				sf = (SessionFactory)wac.getBean("sessionFactory");
				session = sf.openSession();
				int amount = Integer.parseInt(number) * 95;
				String invoiceAdd = "insert into cus_kw_invoice_excal(NAME,DUTY,ADDRESS,TELLPHONE,BANKNAME,BANKNUM,PHONE," +
						"EMAIL,REMARK,AMOUNT,NUMBER,BMLCID) values('"+dwname+"','"+dpno+"','"+dwaddress+"','"+lxrdh+"','"+bankName+"','"+bankNum+"','"+phone+"','"+email+"','"+remark+"','"+amount+"','"+number+"','"+bmlcid+"')";
				session.createSQLQuery(invoiceAdd).executeUpdate();
				
				String sql2 = "update cus_kwbm_examround set LXDH='"+lxrdh+"' where BMLCID='"+bmlcid+"'";
				session.createSQLQuery(sql2).executeUpdate();
				return getMsgBean(true,"申请发票成功！");
			} catch (Exception e) {
				e.printStackTrace();
				return getMsgBean(false,"申请失败！");
				
			} finally{
				if(session!=null){
					session.close();
				}
				if(sf!=null){
					sf.close();
				}
			}
			
//		}
//		return this.MsgBean;
	}

	@Override
	public PageBean getListPage(String companyName,String orderNum,String state) {//STATE
		String sql = "select ID,NAME,DUTY,ADDRESS,TELLPHONE,BANKNAME,BANKNUM,PHONE,EMAIL,REMARK,GOODSNAME,RATE,AMOUNT,NUMBER,BMLCID,case STATE when '2' then '审核中' when '1' then '审核成功' end as STATE from cus_kw_invoice_excal where isuse=1 and state !=0 ";
		if(companyName != null && !companyName.equals("")){
			sql += " and name like binary('%"+companyName+"%') ";
		}
		if(orderNum != null && !orderNum.equals("")){
			sql += " and ID =" + orderNum;
		}
		if(state != null && !state.equals("")){
			if(state.equals("1")){
				sql += " AND STATE = '1'";
			}
			if(state.equals("2")){
				sql += " AND STATE = '2'";
			}
		}
		return getSQLPageBean(sql);
	}

	@Override
	public com.jiajie.bean.MsgBean getInvoice2(String orderNum) {
		String sql = "select * from cus_kw_invoice_excal where ID='"+orderNum+"'";
		List<Object> list = getListSQL(sql);
		getMsgBean(list.get(0));
		return this.MsgBean;
	}

	@Override
	public com.jiajie.bean.MsgBean updateInvoice
			(String orderNum, String NAME, String DUTY, String ADDRESS,
			String TELLPHONE, String EMAIL, String BANKNAME, String BANKNUM,
			String PHONE, String REMARK, String NUMBER,String bmlcid,String state) {
		if(!"1".equals(state)){
			String st = "select fpzt from cus_kwbm_examround where BMLCID='"+bmlcid+"'";
			List<Map<String,Object>> lss=getListSQL(st);
			if(lss.size()!=0){
				if("1".equals(lss.get(0).get("fpzt"))){
					return getMsgBean(false,"已提交发票申请，不能修改发票信息！");
				}
			}
		}
		if(NAME==null || "".equals(NAME)){
			return getMsgBean(false,"单位名称不能为空！");
		}
		if(ADDRESS==null || "".equals(ADDRESS)){
			return getMsgBean(false,"单位地址不能为空！");
		}
		if(DUTY==null || "".equals(DUTY)){
			return getMsgBean(false,"税务号不能为空！");
		}
		if(TELLPHONE==null || "".equals(TELLPHONE)){
			return getMsgBean(false,"联系电话不能为空！");
		}
		if(EMAIL==null || "".equals(EMAIL)){
			return getMsgBean(false,"电子邮箱不能为空！");
		}
		if(NUMBER==null || "".equals(NUMBER)){
			return getMsgBean(false,"人数不能为空！");
		}
		if(PHONE==null || "".equals(PHONE)){
			return getMsgBean(false,"推送手机不能为空！");
		}
		if(!NumberUtils.isNumber(NUMBER)){
			getMsgBean(false,"人数必须是数字！");
			return this.MsgBean;
		}
		String str = "select sl from cus_kwbm_examround where bmlcid='"+bmlcid+"'";
		List<Map<String,Object>> list=getListSQL(str);
		int sum = Integer.parseInt(list.get(0).get("sl").toString());
		String sql1 = "SELECT SUM(NUMBER) AS number FROM cus_kw_invoice_excal WHERE BMLCID = '" + bmlcid + "' and ISUSE = '1' and id!='"+orderNum+"'";
		List<Map<String,Object>> list1=getListSQL(sql1);
		int thatNumber = Integer.parseInt(NUMBER);
		if(list1.get(0).get("number") != null){
			int thisNumber = Integer.parseInt(list1.get(0).get("number").toString());
			sum = sum - thisNumber;
		}
		if(sum < thatNumber){
			return getMsgBean(false,"总人数超过报名人数！");
		}
		boolean boo = EmailUtil.emailFormat(EMAIL);
		if(!boo){
			return getMsgBean(false,"邮箱格式不正确！");
		}
		if(PHONE!=null&&!"".equals(PHONE)){
			boolean bool = EmailUtil.isPhone(PHONE);
			if(!bool){
				return getMsgBean(false,"推送手机号码格式错误！");
			}
		}
		int AMOUNT = Integer.parseInt(NUMBER) * 95;
		String sql2 = "update cus_kw_invoice_excal set NAME='" + NAME + "',DUTY ='" + DUTY
				+ "',ADDRESS = '" + ADDRESS + "',TELLPHONE = '" + TELLPHONE + "',EMAIL = '" + EMAIL
				+ "',BANKNAME = '" + BANKNAME + "',BANKNUM = '" + BANKNUM + "',PHONE = '" + PHONE
				+ "',REMARK = '" + REMARK + "',AMOUNT = '" + AMOUNT + "',NUMBER = '" + NUMBER +"' WHERE ID = '" + orderNum +"'";
		update(sql2);
		this.getMsgBean(true,"修改发票成功！");
		return this.MsgBean;
	}

	@Override
	public com.jiajie.bean.MsgBean updateUse(String orderNum,String bmlcid,String state) {
		if(!"1".equals(state)){
			String st = "select fpzt from cus_kwbm_examround where BMLCID='"+bmlcid+"'";
			List<Map<String,Object>> lss=getListSQL(st);
			if(lss.size()!=0){
				if("1".equals(lss.get(0).get("fpzt"))){
					return getMsgBean(false,"已提交发票申请，不能删除发票信息！");
				}
			}
		}
		if(orderNum != null && !orderNum.equals("")){
			String sql = "update cus_kw_invoice_excal set ISUSE = '2' WHERE ID ='" + orderNum + "'";
			update(sql);
			this.getMsgBean(true,"删除发票成功！");
		}else {
			this.getMsgBean(false,"删除发票失败！");
		}
		return this.MsgBean;
	}

	@Override
	public PageBean getPage(String bmlcid) {
		String sql = "select ID,NAME,DUTY,ADDRESS,BANKNAME,BANKNUM,PHONE,EMAIL,REMARK,NUMBER,TELLPHONE,case STATE when '0' then '未提交' when '1' then '审核成功' when '2' then '审核中' end as STATE from cus_kw_invoice_excal where ISUSE='1' AND BMLCID='"+bmlcid+"'";
		return getSQLPageBean(sql);
	}

	@Override
	public com.jiajie.bean.MsgBean getSum(String bmlcid) {
		String sql = "select sum(NUMBER) from cus_kw_invoice_excal where ISUSE='1' AND BMLCID='"+bmlcid+"'";
		int sum = Integer.parseInt(getSession().createSQLQuery(sql).uniqueResult().toString());
		System.out.println(sum);
		return getMsgBean(sum);
	}

	@Override
	public com.jiajie.bean.MsgBean updateFpzt(String bmlcid) {
		String sql = "update cus_kwbm_examround set fpzt='1' where BMLCID='"+bmlcid+"'";
		update(sql);
		String sql1 = "update cus_kw_invoice_excal set STATE='2' where BMLCID='"+bmlcid+"'";
		update(sql1);
		return getMsgBean(true,"提交申请成功！");
	}


}
