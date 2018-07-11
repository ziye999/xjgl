package com.jiajie.service.examArrangement;

import java.util.List;
import java.util.Map;

import com.jiajie.bean.MsgBean;

public interface ExamTimeArrangeService {
	public List<Map<String, Object>> getSubjectNo(String lcId,String kd);
	public List<Map<String, Object>> getDuration(String lcId);
	public List<Map<String, Object>> getDefault(String lcId,String kd);
	public String saveExamTimeArrange(String lcId,String kd,List<Map<String, Object>> list,String ids);
	public MsgBean createTemplate(String lcid);
	public MsgBean checkFile(String lcid);
}
