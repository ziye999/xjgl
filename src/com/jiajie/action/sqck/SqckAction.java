package com.jiajie.action.sqck;

import com.jiajie.action.BaseAction;
import com.jiajie.bean.MsgBean;
import com.jiajie.bean.zzjg.CusKwSqCkdw;
import com.jiajie.service.sqck.SqckService;
import com.jiajie.util.bean.MBspInfo;
import java.io.PrintStream;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

public class SqckAction extends BaseAction
{

  @Autowired
  private SqckService sqckService;
  private String SSZGJYXZDM;
  private String XXMC;
  private String XZXM;
  private String BGDH;
  private String YZBM;
  private String XXDZ;
  private String ckdw;
  private String zkdw;
  private String xxjbxxid;
  private String zt;

  public String savesqck()
  {
    String SSZGJYXZDM = getRequest().getParameter("SSZGJYXZDM") == null ? "" : getRequest().getParameter("SSZGJYXZDM").toString();
    String XXMC = getRequest().getParameter("XXMC") == null ? "" : getRequest().getParameter("XXMC").toString();

    CusKwSqCkdw sqCkdw = new CusKwSqCkdw();

    sqCkdw.setXxmc(XXMC);
    sqCkdw.setSszgjyxzdm(SSZGJYXZDM);
    sqCkdw.setXzxm(this.XZXM);
    sqCkdw.setBgdh(this.BGDH);
    sqCkdw.setYzbm(this.YZBM);
    sqCkdw.setXxdz(this.XXDZ);
    sqCkdw.setZt("0");
    long xx = System.currentTimeMillis();
    String xxbsm1 = String.valueOf(xx);
    sqCkdw.setXxbsm(xxbsm1);

    MsgBean m = this.sqckService.saveBean(sqCkdw);

    System.out.println(m.getMsg());
    getRequest().setAttribute("errormsg", m.getMsg());
    return "success";
  }

  public void getSq() {
    writerPrint(this.sqckService.getSq(this.ckdw, this.zkdw, this.zt));
  }
  public String getList() {
    List list = this.sqckService.getZk();
    getRequest().setAttribute("list", list);

    return "sqck";
  }

  public void upZt()
  {
    MBspInfo m = getBspInfo();
    writerPrint(this.sqckService.updateBean(this.xxjbxxid, m));
  }

  public String getZt()
  {
    return this.zt;
  }
  public void setZt(String zt) {
    this.zt = zt;
  }
  public String getXxjbxxid() {
    return this.xxjbxxid;
  }
  public void setXxjbxxid(String xxjbxxid) {
    this.xxjbxxid = xxjbxxid;
  }
  public String getCkdw() {
    return this.ckdw;
  }
  public void setCkdw(String ckdw) {
    this.ckdw = ckdw;
  }
  public String getZkdw() {
    return this.zkdw;
  }
  public void setZkdw(String zkdw) {
    this.zkdw = zkdw;
  }

  public String getSSZGJYXZDM()
  {
    return this.SSZGJYXZDM;
  }
  public void setSSZGJYXZDM(String sSZGJYXZDM) {
    this.SSZGJYXZDM = sSZGJYXZDM;
  }
  public String getXXMC() {
    return this.XXMC;
  }

  public void setXXMC(String xXMC)
  {
    this.XXMC = xXMC;
  }

  public String getXZXM()
  {
    return this.XZXM;
  }

  public void setXZXM(String xZXM)
  {
    this.XZXM = xZXM;
  }

  public String getBGDH()
  {
    return this.BGDH;
  }

  public void setBGDH(String bGDH)
  {
    this.BGDH = bGDH;
  }

  public String getYZBM()
  {
    return this.YZBM;
  }

  public void setYZBM(String yZBM)
  {
    this.YZBM = yZBM;
  }

  public String getXXDZ()
  {
    return this.XXDZ;
  }

  public void setXXDZ(String xXDZ)
  {
    this.XXDZ = xXDZ;
  }

  public SqckService getSqckService()
  {
    return this.sqckService;
  }

  public void setSqckService(SqckService sqckService)
  {
    this.sqckService = sqckService;
  }
}