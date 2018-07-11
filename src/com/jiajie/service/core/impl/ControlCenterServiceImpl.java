package com.jiajie.service.core.impl;
/****
 * 控制中心
 */
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import com.jiajie.bean.PageBean;
import com.jiajie.bean.basicsinfo.CusKwBuilding;
import com.jiajie.dao.DaoSupportImpl;
import com.jiajie.exceptions.SystemException;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.basicsinfo.BuildingService;
import com.jiajie.service.core.ControlCenterService;
import com.jiajie.service.core.ExcelService;
import com.jiajie.service.core.PdfService;
import com.jiajie.util.ExcelUtil;
import com.jiajie.util.PdfUtil;
import com.jiajie.bean.MsgBean;
@Service("controlCenterService")
@SuppressWarnings("all")
public class ControlCenterServiceImpl extends ServiceBridge implements ControlCenterService {
	
	public List findUserByE2ID(String e2id){
		StringBuilder sb = new StringBuilder();
		sb.append("select * from t_qxgl_userinfo where e2id='").append(e2id).append("'");
		return getSession().createSQLQuery(sb.toString()).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
	}

}
