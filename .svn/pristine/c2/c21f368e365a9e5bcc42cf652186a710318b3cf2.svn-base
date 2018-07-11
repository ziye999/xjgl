package com.jiajie.service.dailyManagement.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jiajie.bean.PageBean;
import com.jiajie.bean.basicsinfo.CusKwTeacher;
import com.jiajie.bean.dailyManagement.CusXjBreakstudy;
import com.jiajie.bean.zzjg.CusKwCkdw;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.dailyManagement.ApplyForStudyService;
import com.jiajie.util.StringUtil;
import com.jiajie.util.bean.MBspInfo;
@Service("applyForStudyService")
@SuppressWarnings("all")
public class ApplyForStudyServiceImpl extends ServiceBridge implements ApplyForStudyService {

	public PageBean getList(String nj, String bj, String xmxjh, String flag,MBspInfo mf) {
		String organcode = mf.getOrgan().getOrganCode();
		String sql = "select xs.XM,xs.grbsm PID,xs.SFZJH,ifnull(xs.CSRQ,substr(xs.SFZJH,7,8)) AS CSRQ,"+
				"nj.NJMC,bj.BJMC,br.FLAG,date_format(br.cdate,'%Y-%m-%d') CDATE,br.REASON,br.YYID,br.FILEPATH,"+
				"br.MEMO,xs.XS_JBXX_ID,xx.XXMC,xx.XXBSM,xb.mc XB,"+
				"case br.FLAG when '1' then '已审核' when '0' then '未审核' else '未申请' end as ZT "+
				"from zxxs_xs_jbxx xs "+
				"left join cus_xj_breakstudy br on xs.grbsm=br.xjh "+
				"left join zxxs_xx_jbxx xx on xs.xx_jbxx_id=xx.xx_jbxx_id "+
				"left join zxxs_xx_njxx nj on xs.xx_njxx_id=nj.xx_njxx_id "+
				"left join zxxs_xx_bjxx bj on xs.xx_bjxx_id=bj.xx_bjxx_id "+
				"left join zd_xb xb on xs.xbm=xb.dm "+
				"where (xs.xx_jbxx_id='"+organcode+"' or xs.xx_jbxx_id IN (select xx_jbxx_id from zxxs_xx_jbxx where SSZGJYXZDM in ('"+organcode+"'))"+
				"or xs.xx_jbxx_id IN (select xx_jbxx_id from zxxs_xx_jbxx where SSZGJYXZDM in (select REGION_CODE from COM_MEMS_ORGAN where PARENT_CODE='"+organcode+"'))"+
				"or xs.xx_jbxx_id IN (select xx_jbxx_id from zxxs_xx_jbxx where SSZGJYXZDM in (select REGION_CODE from COM_MEMS_ORGAN where PARENT_CODE in (select REGION_CODE from COM_MEMS_ORGAN where PARENT_CODE='"+organcode+"'))))";
		if(nj!=null && !"".equals(nj)){
			sql += " and xs.xx_njxx_id='"+nj+"'";
		}
		if(bj!=null && !"".equals(bj)){
			sql += " and xs.xx_bjxx_id='"+bj+"'";
		}
		if(xmxjh !=null && !"".equals(xmxjh)){
			sql += " and (xs.xm like binary('%"+xmxjh+"%') or xs.grbsm like binary('%"+xmxjh+"%'))";
		}
		if(flag !=null && !"".equals(flag)&&!"3".equals(flag)){
			sql += " and br.flag='"+flag+"'"; 
		}
		if("3".equals(flag)){
			sql += " and (br.flag='' or br.flag is null)";
		}
		return getSQLPageBean(sql);
	}

	public com.jiajie.bean.MsgBean saveApply(CusXjBreakstudy breakstudy) {
		try {
			saveOrUpdate(breakstudy);
			this.getMsgBean(true,MsgBean.SAVE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			this.getMsgBean(false,MsgBean.SAVE_ERROR);
		}
		return MsgBean;
	}

	public com.jiajie.bean.MsgBean delApply(HttpServletRequest request,String yyids) {
		try {
			String sql = "from CusXjBreakstudy where yyid in ("+yyids+")";
			List ls = createHQLQuery(sql).list();
			if (ls.size()>0) {
				CusXjBreakstudy bs = (CusXjBreakstudy)ls.get(0);
				String savePath = request.getSession().getServletContext().getRealPath(File.separator+"uploadFile");
				File savefile = new File(new File(savePath), bs.getFilepath());
				StringUtil.deleteDir(savefile);
				String sqlDel = "delete from cus_xj_breakstudy where yyid in ("+yyids+")";
				delete(sqlDel);
				return getMsgBean(true, "删除成功！");
			}else {
				return getMsgBean(false, "删除失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return getMsgBean(false, "删除失败！");
		}
	}

	public com.jiajie.bean.MsgBean updateApply(String yyid, String yy,
			String sqrq, String yysm,String filename) {
		try {
			String sql = "";
			if("".equals(filename)){
				sql = "update cus_xj_breakstudy set reason='"+yy+"',cdate=STR_TO_DATE('"+sqrq+"','%Y-%m-%d %H:%i:%s'),memo='"+yysm+"' where yyid='"+yyid+"'";
			}else{
				sql = "update cus_xj_breakstudy set reason='"+yy+"',cdate=STR_TO_DATE('"+sqrq+"','%Y-%m-%d %H:%i:%s'),memo='"+yysm+"',filePath='"+filename+"' where yyid='"+yyid+"'";
			}
			update(sql);
			return getMsgBean(true, "修改成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return getMsgBean(false, "修改失败！");
		}
	}

	public com.jiajie.bean.MsgBean uploadFile(HttpServletRequest request,
			File upload, String filename) {
		filename = UUID.randomUUID().toString().replaceAll("-", "")+filename.substring(filename.lastIndexOf("."));
		try {
			String savePath = request.getSession().getServletContext().getRealPath(File.separator+"uploadFile");
			File file = new File(savePath);
			//判断上传文件的保存目录是否存在
			if (!file.exists() && !file.isDirectory()) {
				System.out.println(savePath+"目录不存在，需要创建");
				//创建目录
				file.mkdir();
			}
			if (upload != null) {
				File savefile = new File(new File(savePath), filename);
				if (!savefile.getParentFile().exists())
					savefile.getParentFile().mkdirs();
				FileUtils.copyFile(upload, savefile);
			}
			this.getMsgBean(true,"上传成功！");
			MsgBean.setData(filename);
		} catch (Exception e) {
			e.printStackTrace();
			this.getMsgBean(false,"上传失败！");
			MsgBean.setData("");
		}
		return MsgBean;
	}

}
