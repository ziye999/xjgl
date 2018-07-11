package com.jiajie.service.examResults;

import java.util.List;
import java.util.Map;

import com.jiajie.bean.PageBean;

public interface ScoresQueryService {
	public PageBean getStudentScores(Map<String, String> map);
	public List<Map<String, Object>> getKskm(String lcid);
	public List<Map<String, Object>> printStudentScores(Map<String, String> map);
}
