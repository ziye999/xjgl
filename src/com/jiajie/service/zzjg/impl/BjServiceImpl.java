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
import com.jiajie.dao.DaoSupportImpl;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.zzjg.BjService;
import com.jiajie.util.StringUtil;
import com.jiajie.util.bean.MBspInfo;
import com.jiajie.bean.MsgBean;
@Service("bjService")
@SuppressWarnings("all")
public class BjServiceImpl extends ServiceBridge implements BjService {
	
	public PageBean getList(String xx_jbxx_id,String bjmc,MBspInfo bspInfo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select distinct bj.XX_BJXX_ID,bj.XX_JBXX_ID,bj.XX_NJXX_ID,bj.BH,");
		sb.append("bj.BJMC,bj.YXBZ,xx.XXMC,sf.mc ZT,nj.NJMC ");
		sb.append("from zxxs_xx_bjxx bj ");
		sb.append("left join zxxs_xx_jbxx xx on xx.xx_jbxx_id=bj.xx_jbxx_id ");
		sb.append("left join zxxs_xx_njxx nj on nj.xx_njxx_id=bj.xx_njxx_id and nj.xx_jbxx_id=xx.xx_jbxx_id ");
		sb.append("left join zd_sfbz sf on sf.dm=bj.yxbz where 0=0 ");
		if(StringUtil.isNotNullOrEmpty(xx_jbxx_id)) {
			sb.append(" and bj.xx_jbxx_id = '"+xx_jbxx_id+"'");
		}
		if(StringUtil.isNotNullOrEmpty(bjmc)) {
			sb.append(" and bj.BJMC like binary('%"+bjmc+"%')");
		}
		sb.append(" order by bj.bh desc");
		return getSQLPageBean(sb.toString());
	}
	
	public MsgBean del(String xx_bjxx_id) {
		String rtn = "";
		try {
			String sql = "delete from zxxs_xx_bjxx where xx_bjxx_id='"+xx_bjxx_id+"'";
			delete(sql);			
			this.getMsgBean(true,"删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
			this.getMsgBean(false,"删除失败！");
		}
		return MsgBean;
	}
	
	public List findBjmc(String bjmc,String xx_jbxx_id){
		String sql = "select * from zxxs_xx_bjxx where bjmc='"+bjmc+"' and XX_JBXX_ID='"+xx_jbxx_id+"'";
		return getListSQL(sql); 
	}
	
	private String getBjCode(String xx_njxx_id, String xx_jbxx_id){
		String sql = "select count(bj.xx_bjxx_id)+1 BJCODE,"+
				"ifnull((select rxnf from zxxs_xx_njxx where xx_njxx_id=bj.xx_njxx_id limit 1),"+
				"(select rxnf from zxxs_xx_njxx where xx_jbxx_id = '"+xx_jbxx_id+"' limit 1)) RXNF "+
				"from zxxs_xx_bjxx bj where xx_njxx_id='"+xx_njxx_id+"'";
		List ls = getListSQL(sql);
		String bjcode = "1";
		String rxnf = "";
		if (ls.size()>0) {
			Map result = (Map) ls.get(0);
			bjcode = result.get("BJCODE").toString();
			if (result.get("RXNF")==null) {
				return null;
			}
			rxnf = result.get("RXNF").toString();
		}
		bjcode = rxnf+String.format("%03d",Integer.valueOf(bjcode).intValue());
		return bjcode;
	}
	
	public com.jiajie.bean.MsgBean update(String xx_bjxx_id,String xx_jbxx_id,String bjmc,
			String xx_njxx_id,String bjzt) {
		try {
			String sql = "";
			if (xx_bjxx_id==null||"".equals(xx_bjxx_id)) {
				if (StringUtil.isNotNullOrEmpty(bjmc.trim())){
					List ls = findBjmc(bjmc,xx_jbxx_id);
					if(ls!=null&&ls.size()>0){
						this.getMsgBean(false,"课程已存在！");
						return MsgBean;
					}
					String bh = getBjCode(xx_njxx_id,xx_jbxx_id);
					if (bh==null) {
						this.getMsgBean(true,"请先设置等级，再设置课程！");
					}else {
						xx_bjxx_id = UUID.randomUUID().toString().replace("-","");
						sql = "insert into zxxs_xx_bjxx(XX_BJXX_ID,XX_JBXX_ID,XX_NJXX_ID,BH,BJMC,YXBZ) values('"+
								xx_bjxx_id+"','"+xx_jbxx_id+"','"+xx_njxx_id+"','"+bh+"','"+bjmc+"','"+bjzt+"')";
						this.update(sql);
						this.getMsgBean(true,"增加成功！");
					}					
				}else {
					this.getMsgBean(false,"空白内容不能保存！");
				}
			}else {
				List ls = findBjmc(bjmc,xx_jbxx_id);								
				if(ls!=null&&ls.size()>0){
					Map result = (Map) ls.get(0);
					String nj = result.get("XX_NJXX_ID").toString();
					String yx = result.get("YXBZ").toString();
					if (nj.equals(xx_njxx_id)&&yx.equals(bjzt)) {
						this.getMsgBean(false,"课程已存在！");
						return MsgBean;
					}
				}
				sql += "update zxxs_xx_bjxx set xx_jbxx_id='"+xx_jbxx_id+"',bjmc='"+bjmc+
						"',xx_njxx_id='"+xx_njxx_id+"',YXBZ='"+bjzt+"' where xx_bjxx_id='"+xx_bjxx_id+"'";
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
