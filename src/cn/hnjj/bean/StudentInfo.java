package cn.hnjj.bean;

import java.io.Serializable;
import net.sf.json.JSONObject;

public class StudentInfo
  implements Serializable
{
  private static final long serialVersionUID = 87987236812367123L;
  private String kcid;
  private String xsid;
  private String pwd;
  private String xm;
  private String sfzh;
  private String pcid;
  private String zwh;
  private String zt = "0";
  
  public String toString()
  {
    JSONObject jsonObj = new JSONObject();
    jsonObj.put("kcid", getKcid());
    jsonObj.put("xsid", getXsid());
    jsonObj.put("pwd", getPwd());
    jsonObj.put("xm", getXm());
    jsonObj.put("sfzh", getSfzh());
    jsonObj.put("pcid", getPcid());
    jsonObj.put("zwh", getZwh());
    jsonObj.put("zt", getZt());
    
    return jsonObj.toString();
  }
  
  public String getKcid()
  {
    return this.kcid;
  }
  
  public void setKcid(String kcid)
  {
    this.kcid = kcid;
  }
  
  public String getXsid()
  {
    return this.xsid;
  }
  
  public void setXsid(String xsid)
  {
    this.xsid = xsid;
  }
  
  public String getPwd()
  {
    return this.pwd;
  }
  
  public void setPwd(String pwd)
  {
    this.pwd = pwd;
  }
  
  public String getXm()
  {
    return this.xm;
  }
  
  public void setXm(String xm)
  {
    this.xm = xm;
  }
  
  public String getSfzh()
  {
    return this.sfzh;
  }
  
  public void setSfzh(String sfzh)
  {
    this.sfzh = sfzh;
  }
  
  public String getPcid()
  {
    return this.pcid;
  }
  
  public void setPcid(String pcid)
  {
    this.pcid = pcid;
  }
  
  public String getZwh()
  {
    return this.zwh;
  }
  
  public void setZwh(String zwh)
  {
    this.zwh = zwh;
  }
  
  public String getZt()
  {
    return this.zt;
  }
  
  public void setZt(String zt)
  {
    this.zt = zt;
  }
}
