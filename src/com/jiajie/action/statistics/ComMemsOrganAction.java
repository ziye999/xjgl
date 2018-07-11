package com.jiajie.action.statistics;

import com.jiajie.action.BaseAction;
import com.jiajie.service.statistics.ComMemsOrganService;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("serial")
public class ComMemsOrganAction extends BaseAction {

    private String year;
    private String region;

    private String LCID;//单位ID
    private String name;//单位名称
    private String SHFLAG;//是否缴费
    private String isBao;//是否报名
    @Autowired
    private ComMemsOrganService comMemsOrganService;
    //获取统计数据列表
    public void getListPage() {
        writerPrint(comMemsOrganService.getList(year,region));
    }

    //根据LCID查询报名单位详细信息
    public void getDetailedListPage(){
        writerPrint(comMemsOrganService.getDetailedListPage(LCID,name,isBao,SHFLAG));
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getLCID() {
        return LCID;
    }

    public void setLCID(String LCID) {
        this.LCID = LCID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSHFLAG() {
        return SHFLAG;
    }

    public void setSHFLAG(String SHFLAG) {
        this.SHFLAG = SHFLAG;
    }

    public String getIsBao() {
        return isBao;
    }

    public void setIsBao(String isBao) {
        this.isBao = isBao;
    }
}
