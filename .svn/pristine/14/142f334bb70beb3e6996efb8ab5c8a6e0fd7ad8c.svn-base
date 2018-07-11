package com.jiajie.service.dailyManagement;


import java.io.File;

import javax.servlet.http.HttpServletRequest;

import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.bean.dailyManagement.CusXjBreakstudy;
import com.jiajie.util.bean.MBspInfo;

public interface ApplyForStudyService {
    PageBean getList(String nj,String bj,String xmxjh,String flag,MBspInfo mf);
    
    MsgBean saveApply(CusXjBreakstudy breakstudy);
    
    MsgBean delApply(HttpServletRequest request,String yyids);
    
    MsgBean updateApply(String yyid,String yy,String sqrq,String yysm,String filename);
    
    MsgBean uploadFile(HttpServletRequest request,File file,String filename);
}
