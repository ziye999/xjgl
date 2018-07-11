package com.jiajie.service.statistics.impl;

import com.jiajie.bean.PageBean;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.statistics.ComMemsOrganService;
import org.springframework.stereotype.Service;
@Service("comMemsOrganService")
public class ComMemsOrganServiceImpl extends ServiceBridge implements ComMemsOrganService {
    @Override
    public PageBean getList(String year, String region) {
        String sql = "SELECT a.LCID AS LCID,a.examname AS examname,a.xqm AS xqm,a.xn AS xn,COUNT(b.lcid) AS clcid,SUM(CASE WHEN b.SHFLAG IN (0,1,2,3) AND TRIM(b.SHFLAG)!='' THEN 1 ELSE 0 END) AS csl,SUM(CASE WHEN b.SHFLAG = '1' AND TRIM(b.SHFLAG)!='' THEN 1 ELSE 0 END)" +
                "                AS cSHFLAG,IFNULL(SUM(CASE WHEN TRIM(b.SHFLAG)!='' AND b.SHFLAG is not null THEN b.SL ELSE 0 END),0) AS sl,COUNT(b.lcid) - SUM(CASE WHEN b.SHFLAG IN (0,1,2,3) AND TRIM(b.SHFLAG)!='' THEN 1 ELSE 0 END) AS ccount FROM" +
                "                cus_kw_examround a LEFT JOIN cus_kwbm_examround b ON a.LCID = b.LCID WHERE 1=1 ";
        if(year != null){
            int strEndIndex = year.indexOf("次");
            String result = year.substring(8, strEndIndex);
            String xn = year.substring(0, 4);
            sql += " and a.xqm = " + result;
            sql += " and a.xn like binary('%"+xn+"%') ";
        }
        if(region != null){
            sql += " and a.examname like binary('%"+region+"%') ";
        }
        sql += " GROUP BY a.LCID";
        return getSQLPageBean(sql);
    }

    @Override
    public PageBean getDetailedListPage(String LCID, String name,String isBao, String SHFLAG) {
        String sql = "SELECT BMLCID,examname,(CASE WHEN SHFLAG IN (0,1,2,3) AND TRIM(SHFLAG)!='' THEN SL" +
                " ELSE 0 END) as sl," +
                " (CASE WHEN SHFLAG='1' AND TRIM(SHFLAG)!='' THEN '是'" +
                " ELSE '否' END) AS SHFLAG"+
                " FROM cus_kwbm_examround WHERE 1=1 ";
        if(LCID != null){
            sql += " and LCID = '" + LCID + "'";
        }
        if(name != null && !name.equals("")){
            sql += " and examname like binary('%" + name + "%') ";
        }
        if(isBao != null && !isBao.equals("")){
            if(isBao.equals("0")){
                sql += " and SHFLAG IN (0,1,2,3) AND TRIM(SHFLAG)!=''";
            }else {
                sql += " AND (TRIM(SHFLAG) ='' OR SHFLAG IS NULL) ";
            }
        }
        if(SHFLAG != null && !SHFLAG.equals("")){
            if(SHFLAG.equals("0")){
                sql += " and SHFLAG IN (1) AND TRIM(SHFLAG)!='' ";
            }else {
                sql += " AND (SHFLAG IN (0,2,3)  OR TRIM(SHFLAG)='' OR SHFLAG IS NULL)";
            }
        }
        return getSQLPageBean(sql);
    }
}
