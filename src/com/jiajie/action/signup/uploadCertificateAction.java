package com.jiajie.action.signup;

import org.springframework.beans.factory.annotation.Autowired;

import com.jiajie.action.BaseAction;
import com.jiajie.bean.MsgBean;
import com.jiajie.service.signup.uploadCertificateService;

public class uploadCertificateAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String bmlcid;
	private String pzpath;
	private String lxr;
	private String lxdh;
	private String lxrdh;
	private String dwname;
	private String dwaddress;
	private String dpno;
	private String email;
	private String bankName;
	private String bankNum;
	private String phone;
	private String remark;
	private String number;
	private String companyName;

	private String orderNum;
	private String NAME;
	private String DUTY;
	private String ADDRESS;
	private String TELLPHONE;
	private String EMAIL;
	private String BANKNAME;
	private String BANKNUM;
	private String PHONE;
	private String REMARK;
	private String NUMBER;
	private String state;
	@Autowired
	private uploadCertificateService service;

	public void saveCertificate() {
		MsgBean msgBean = service.save(bmlcid,pzpath,lxr,lxdh);
		writerPrint(msgBean);
		
	}

	public void getKsrs() {
		MsgBean msgBean = service.getKsrs(bmlcid);
		writerPrint(msgBean);
	}
	
	public void getInvoice(){
		MsgBean msgBean = service.getInvoice(bmlcid);
		writerPrint(msgBean);
	}

	public void getInvoice2(){
		MsgBean msgBean = service.getInvoice2(orderNum);
		writerPrint(msgBean);
	}

	public void updateInvoice(){
		writerPrint(service.updateInvoice(orderNum,NAME,DUTY,ADDRESS,TELLPHONE,EMAIL,BANKNAME,BANKNUM,PHONE,REMARK,NUMBER,bmlcid,state));
	}

	public void updateUse(){
		writerPrint(service.updateUse(orderNum,bmlcid,state));
	}
	
	public void saveInvoice(){
		MsgBean msgBean = service.saveInvoice(bmlcid,dwname,dpno,dwaddress,lxrdh,email,bankName,bankNum,phone,remark,number);
		writerPrint(msgBean);
	}

	public void getListPage(){
		writerPrint(service.getListPage(companyName,orderNum,state));
	}
	
	public void getPage(){
		writerPrint(service.getPage(bmlcid));
	}
	
	public void getSum(){
		writerPrint(service.getSum(bmlcid));
	}
	
	public void updateFpzt(){
		writerPrint(service.updateFpzt(bmlcid));
	}
	
	public String getBmlcid() {
		return bmlcid;
	}

	public void setBmlcid(String bmlcid) {
		this.bmlcid = bmlcid;
	}

	public String getPzpath() {
		return pzpath;
	}

	public void setPzpath(String pzpath) {
		this.pzpath = pzpath;
	}
	public String getLxr() {
		return lxr;
	}

	public void setLxr(String lxr) {
		this.lxr = lxr;
	}

	public String getLxdh() {
		return lxdh;
	}

	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}

	public String getLxrdh() {
		return lxrdh;
	}

	public void setLxrdh(String lxrdh) {
		this.lxrdh = lxrdh;
	}

	public String getDwname() {
		return dwname;
	}

	public void setDwname(String dwname) {
		this.dwname = dwname;
	}

	public String getDwaddress() {
		return dwaddress;
	}

	public void setDwaddress(String dwaddress) {
		this.dwaddress = dwaddress;
	}

	public String getDpno() {
		return dpno;
	}

	public void setDpno(String dpno) {
		this.dpno = dpno;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankNum() {
		return bankNum;
	}

	public void setBankNum(String bankNum) {
		this.bankNum = bankNum;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getNAME() {
		return NAME;
	}

	public void setNAME(String NAME) {
		this.NAME = NAME;
	}

	public String getDUTY() {
		return DUTY;
	}

	public void setDUTY(String DUTY) {
		this.DUTY = DUTY;
	}

	public String getADDRESS() {
		return ADDRESS;
	}

	public void setADDRESS(String ADDRESS) {
		this.ADDRESS = ADDRESS;
	}

	public String getTELLPHONE() {
		return TELLPHONE;
	}

	public void setTELLPHONE(String TELLPHONE) {
		this.TELLPHONE = TELLPHONE;
	}

	public String getEMAIL() {
		return EMAIL;
	}

	public void setEMAIL(String EMAIL) {
		this.EMAIL = EMAIL;
	}

	public String getBANKNAME() {
		return BANKNAME;
	}

	public void setBANKNAME(String BANKNAME) {
		this.BANKNAME = BANKNAME;
	}

	public String getBANKNUM() {
		return BANKNUM;
	}

	public void setBANKNUM(String BANKNUM) {
		this.BANKNUM = BANKNUM;
	}

	public String getPHONE() {
		return PHONE;
	}

	public void setPHONE(String PHONE) {
		this.PHONE = PHONE;
	}

	public String getREMARK() {
		return REMARK;
	}

	public void setREMARK(String REMARK) {
		this.REMARK = REMARK;
	}

	public String getNUMBER() {
		return NUMBER;
	}

	public void setNUMBER(String NUMBER) {
		this.NUMBER = NUMBER;
	}

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
