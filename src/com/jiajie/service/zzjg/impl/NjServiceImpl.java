package com.jiajie.service.zzjg.impl;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.jiajie.bean.PageBean;
import com.jiajie.bean.basicsinfo.CusKwBuilding;
import com.jiajie.dao.DaoSupportImpl;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.zzjg.NjService;
import com.jiajie.util.StringUtil;
import com.jiajie.util.bean.MBspInfo;
import com.jiajie.bean.MsgBean;
@Service("njService")
@SuppressWarnings("all")
public class NjServiceImpl extends ServiceBridge implements NjService {
		
	public PageBean getList(String xx_jbxx_id,String njmc,MBspInfo bspInfo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select distinct nj.XX_NJXX_ID,nj.XX_JBXX_ID,nj.NJMC,nj.RXNF,nj.JYJD,");
		sb.append("nj.XJNJDM,nj.NJZT,xx.XXMC,sf.mc ZT,dj.name DJMC ");
		sb.append("from zxxs_xx_njxx nj ");
		sb.append("left join zxxs_xx_jbxx xx on xx.xx_jbxx_id=nj.xx_jbxx_id ");
		sb.append("left join sys_enum_value dj on dj.DIC_TYPE='ZD_XT_NJDM' and dj.code=nj.xjnjdm ");
		sb.append("left join zd_sfbz sf on sf.dm=nj.njzt where 0=0 ");
		if(StringUtil.isNotNullOrEmpty(xx_jbxx_id)) {
			sb.append(" and nj.xx_jbxx_id = '"+xx_jbxx_id+"'");
		}
		if(StringUtil.isNotNullOrEmpty(njmc)) {
			sb.append(" and nj.NJMC like binary('%"+njmc+"%')");
		}		
		sb.append(" order by nj.rxnf desc");
		return getSQLPageBean(sb.toString());
	}
			
	public MsgBean del(String xx_njxx_id) {
		String rtn = "";
		try {
			List ls = getListSQL("select * from zxxs_xx_bjxx where xx_njxx_id='"+xx_njxx_id+"'");
			if (ls.size()>0) {
				return this.getMsgBean(false,"已存在该等级的课程，不能删除！");
			}
			String sql = "delete from zxxs_xx_bjxx where xx_njxx_id='"+xx_njxx_id+"'";
			delete(sql);
			sql = "delete from zxxs_xx_njxx where xx_njxx_id='"+xx_njxx_id+"'";
			delete(sql);
			this.getMsgBean(true,"删除成功！");			
		} catch (Exception e) {
			e.printStackTrace();
			this.getMsgBean(false,"删除失败！");
		}
		return MsgBean;
	}
		
	public List findNjmc(String njmc,String xx_jbxx_id){
		String sql = "select * from zxxs_xx_njxx where njmc='"+njmc+"' and XX_JBXX_ID='"+xx_jbxx_id+"'";
		return getListSQL(sql); 
	}
		
	private String getNjCode(String rxnf){
		String sql = "select count(xx_njxx_id)+1 NJCODE,RXNF from zxxs_xx_njxx where RXNF='"+rxnf+"'";
		List ls = getListSQL(sql);
		String njcode = "1";
		String rxnfStr = "";
		if (ls.size()>0) {
			Map result = (Map) ls.get(0);
			njcode = result.get("NJCODE").toString();
			rxnf = result.get("RXNF")==null?rxnf:result.get("RXNF").toString();
		}else {
			rxnfStr = rxnf;
		}
		njcode = "nj"+rxnf+"xx"+njcode;
		return njcode;
	}
		
	public MsgBean update(String xx_njxx_id,String xx_jbxx_id,String njmc,
			String rxnf,String jyjd,String djdm,String njzt) {
		try {
			String sql = "";
			if (xx_njxx_id==null||"".equals(xx_njxx_id)) {
				if (StringUtil.isNotNullOrEmpty(njmc.trim())){
					List ls = findNjmc(njmc,xx_jbxx_id);
					if(ls!=null&&ls.size()>0){
						this.getMsgBean(false,"等级已存在！");
						return MsgBean;
					}
					xx_njxx_id = getNjCode(rxnf);
					sql = "insert into zxxs_xx_njxx(XX_NJXX_ID,XX_JBXX_ID,NJMC,RXNF,JYJD,XJNJDM,NJZT) values('"+
							xx_njxx_id+"','"+xx_jbxx_id+"','"+njmc+"','"+rxnf+"','"+jyjd+"','"+djdm+"','"+njzt+"')";
					this.update(sql);
					this.getMsgBean(true,"增加成功！");
				}else {
					this.getMsgBean(false,"空白内容不能保存！");
				}
			}else {				
				sql += "update zxxs_xx_njxx set xx_jbxx_id='"+xx_jbxx_id+"',njmc='"+njmc+"',rxnf='"+rxnf+
						"',jyjd='"+jyjd+"',XJNJDM='"+djdm+"',njzt='"+njzt+"' where xx_njxx_id='"+xx_njxx_id+"'";
				update(sql);
				this.getMsgBean(true,"修改成功！");
			}			
		} catch (Exception e) {
			e.printStackTrace();
			this.getMsgBean(false,"修改失败！");
		}
		return MsgBean;
	}
		
}
