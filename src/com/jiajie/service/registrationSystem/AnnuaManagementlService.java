package com.jiajie.service.registrationSystem;

import java.util.List;

import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.bean.exambasic.CusKwExamround;
import com.jiajie.bean.registrationSystem.ZxxsXnxq;
import com.jiajie.bean.zzjg.CusKwCkdw;
import com.jiajie.bean.zzjg.CusKwZkdw;



public interface AnnuaManagementlService {
	//查询所有数据
	List<ZxxsXnxq> select();
	public PageBean getList();	
	//查询轮次数(查询有问题，数据不符合）
	int selCusKw(String xnmc);
	//查询轮次数
	List selectCuskw(String xnmc ,String xqmc);
	public List selectCuskwbm(String xnmc,String xqmc);
	
	//根据id查询数据
	ZxxsXnxq sle(String xnxq_id);
	public MsgBean getZxxs(String xnxqid);
	public CusKwCkdw selectCkdw(String xxjbxxid);
	
	//根据条件查询
	 PageBean getListPage(String xnmc,String xqmc ,
			String xxkssj,String xxjssj);
	List<ZxxsXnxq> selectZxxsXnxqs(String xnmc,String xqmc ,
			String xxkssj,String xxjssj);
	

	
	//添加数据
	public MsgBean saveZxq(ZxxsXnxq zxxsXnxq,String xxkssj,String xxjssj);
	
	//修改数据
	 void updateZxxsXnxq(String xnxqid,String xnmc,String xqmc ,
				String xxkssj,String xxjssj,String yxbz);
	 MsgBean updateZxq(String xnxqid,String xnmc,String xqmc ,
				String xxkssj,String xxjssj,String yxbz,String GXR); 
	//删除数据
	
	public MsgBean delZxxsXnxq(String xnxqid);
	//启动轮次
	 public MsgBean saveOrUpdateExamround(CusKwExamround examround, String usertype);

	//查询法制办
	 public  List<CusKwZkdw> getFzb();
	 
	 //查询参考单位
	 public List<CusKwCkdw> getCk(String SSZGJYXZDM);
	 
	
}
