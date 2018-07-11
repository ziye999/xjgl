package com.jiajie.service.resultsStatisticalCollection;

import java.io.File;
import java.util.List;

import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.bean.resultsStatisticalCollection.CusKwExecise;

public interface ResultsCollectionService {

	PageBean getList(String dwid,String userType,String xnxq_id);
	MsgBean importResults(String userId,File upload,String lcid);
	PageBean getList(String lcid);
	public MsgBean updateResults(List<CusKwExecise> ckeList);
}
