package com.jiajie.service.rollReport; 

import java.util.List;
import java.util.Map;

import com.jiajie.util.bean.MBspInfo;

public interface CertificatePrintService {
	public List<Map<String,Object>>  getStuCertiInfo(MBspInfo mp,String xjh,Integer zstype,String zgjyj);
}
