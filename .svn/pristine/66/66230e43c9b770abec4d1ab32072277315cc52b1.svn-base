package com.jiajie.service.exambasic;

import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.bean.exambasic.CusKwExamst;
import com.jiajie.util.bean.MBspInfo;
import java.io.File;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;

public abstract interface ExamzjService
{
  public abstract PageBean getList(List<String> paramList, MBspInfo paramMBspInfo);

  public abstract void delSts(String paramString);

  public abstract List<Map<String, String>> getKm();

  public abstract MsgBean optExamst(CusKwExamst paramCusKwExamst, MBspInfo paramMBspInfo);

  public abstract MsgBean saveSetInfo(Map<String, String> paramMap, MBspInfo paramMBspInfo);

  public abstract MsgBean getSzInfo(String paramString);

  public abstract MsgBean saveSetExamInfo(String paramString, Map<String, Map<String, String>> paramMap, MBspInfo paramMBspInfo);

  public abstract List<Map<String, Object>> getExamInfo(String paramString);

  public abstract MsgBean saveKmblExamInfo(String paramString, Map<String, String> paramMap, MBspInfo paramMBspInfo);

  public abstract MsgBean getKmszInfo(String paramString1, String paramString2, MBspInfo paramMBspInfo);

  public abstract MsgBean ksZj(String paramString1, String paramString2, MBspInfo paramMBspInfo, ServletContext paramServletContext);

  public abstract int saveExamPaperInfo(File paramFile, String paramString1, String paramString2);

  public abstract String getRoomName(String paramString);

  public abstract Map<String, Object> downExamPaper(String paramString1, String paramString2, boolean paramBoolean, String paramString3);

  public abstract MsgBean ksZj2(String paramString1, String paramString2, MBspInfo paramMBspInfo, ServletContext paramServletContext);

}