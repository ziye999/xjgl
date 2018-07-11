package com.jiajie.service.exambasic;

import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.bean.exambasic.CusKwExamst;
import com.jiajie.util.bean.MBspInfo;
import java.io.File;
import java.util.List;
import java.util.Map;

public abstract interface ExamstService
{
  public abstract PageBean getList(List<String> paramList, MBspInfo paramMBspInfo);

  public abstract void delSts(String paramString);

  public abstract List<Map<String, String>> getKm();

  public abstract List<Map<String, String>> getXnxq();

  public abstract List<Map<String, String>> getKsmc(String paramString);

  public abstract List<Map<String, String>> getKskm(String paramString);

  public abstract List<Map<String, String>> getTx();

  public abstract List<Map<String, String>> getXz();

  public abstract List<Map<String, String>> getNd();

  public abstract MsgBean optExamst(CusKwExamst paramCusKwExamst, List<String> paramList, MBspInfo paramMBspInfo);

  public abstract List<String> getOptInfo(String paramString);

  public abstract Map<String, Object> paperClear(String paramString1, String paramString2);

  public abstract PageBean getDwxs(String paramString1, String paramString2, String paramString3);

  public abstract MsgBean exportKsInfo(File paramFile, MBspInfo paramMBspInfo, String paramString);

  public abstract MsgBean exportBkKsInfo(File paramFile, MBspInfo paramMBspInfo, String paramString);

  public abstract MsgBean ExportKsZkzPdf();
}