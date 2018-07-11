package com.jiajie.bean.examArrangement;

import java.math.BigDecimal;


/**
 * AbstractCusKwRoomstudent entity provides the base persistence definition of the CusKwRoomstudent entity. @author MyEclipse Persistence Tools
 */

public class CusKwRoomstudent  implements java.io.Serializable {
   
	 private static final long serialVersionUID = -5315533589392911555L;
	 private String apid;
     private String kcid;
     private String kdid;
     private String lcid;
     private String kmid;
     private String xh;
     private BigDecimal kscode;
     private Short x;
     private Short y;
     private Short zwh;


    // Constructors

    /** default constructor */
    public CusKwRoomstudent() {
    }

	/** minimal constructor */
    public CusKwRoomstudent(String kcid) {
        this.kcid = kcid;
    }
    
    /** full constructor */
    public CusKwRoomstudent(String kcid, String kdid, String lcid, String kmid, String xh, BigDecimal kscode, Short x, Short y, Short zwh) {
        this.kcid = kcid;
        this.kdid = kdid;
        this.lcid = lcid;
        this.kmid = kmid;
        this.xh = xh;
        this.kscode = kscode;
        this.x = x;
        this.y = y;
        this.zwh = zwh;
    }
   
    // Property accessors
    public String getApid() {
        return this.apid;
    }
    
    public void setApid(String apid) {
        this.apid = apid;
    }

    public String getKcid() {
        return this.kcid;
    }
    
    public void setKcid(String kcid) {
        this.kcid = kcid;
    }

    public String getKdid() {
        return this.kdid;
    }
    
    public void setKdid(String kdid) {
        this.kdid = kdid;
    }

    public String getLcid() {
        return this.lcid;
    }
    
    public void setLcid(String lcid) {
        this.lcid = lcid;
    }
    
    public String getKmid() {
        return this.kmid;
    }
    
    public void setKmid(String kmid) {
        this.kmid = kmid;
    }

    public String getXh() {
        return this.xh;
    }
    
    public void setXh(String xh) {
        this.xh = xh;
    }

    public BigDecimal getKscode() {
        return this.kscode;
    }
    
    public void setKscode(BigDecimal kscode) {
        this.kscode = kscode;
    }

    public Short getX() {
        return this.x;
    }
    
    public void setX(Short x) {
        this.x = x;
    }

    public Short getY() {
        return this.y;
    }
    
    public void setY(Short y) {
        this.y = y;
    }

    public Short getZwh() {
        return this.zwh;
    }
    
    public void setZwh(Short zwh) {
        this.zwh = zwh;
    }
   








}