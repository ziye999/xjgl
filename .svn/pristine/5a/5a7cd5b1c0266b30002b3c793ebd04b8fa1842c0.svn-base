package com.jiajie.service.signup;

import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;

public interface uploadCertificateService {
	public MsgBean getKsrs(String bmlcid);
	public MsgBean save(String bmlcid,String pzpath,String lxr,String lxdh);
	public MsgBean getInvoice(String bmlcid);
	public MsgBean saveInvoice(String bmlcid,String dwname,String dpno,String dwaddress,String lxdh,String email,String bankName,String bankNum,String phone,String remark,String number);
	public PageBean getListPage(String companyName,String orderNum,String state);

	public MsgBean getInvoice2(String orderNum);

	public MsgBean updateInvoice(String orderNum,String NAME,String DUTY,String ADDRESS,String TELLPHONE,String EMAIL,String BANKNAME,String BANKNUM,String PHONE,String REMARK,String NUMBER,String bmlcid,String state);


	public MsgBean updateUse(String orderNum,String bmlcid,String state);
	
	public PageBean getPage(String bmlcid);
	
	public MsgBean getSum(String bmlcid);
	
	public MsgBean updateFpzt(String bmlcid);
}
