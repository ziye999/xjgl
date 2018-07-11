package com.jiajie.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jiajie.util.bean.OrganTree;

public class Tools {
	static long CONST_WEEK = 3600 * 1000 * 24 * 7;

	static String[] units = { "", "十", "百", "千", "万", "十万", "百万", "千万", "亿",
			"十亿", "百亿", "千亿", "万亿" };
	static char[] numArray = { '零', '一', '二', '三', '四', '五', '六', '七', '八', '九' };

	public static int weekInterval(Date start, Date end) {
		Calendar before = Calendar.getInstance();
		Calendar after = Calendar.getInstance();
		before.setTime(start);
		after.setTime(end);
		int week = before.get(Calendar.DAY_OF_WEEK);
		before.add(Calendar.DATE, -week);
		week = after.get(Calendar.DAY_OF_WEEK);
		after.add(Calendar.DATE, 7 - week);
		int interval = (int) ((after.getTimeInMillis() - before
				.getTimeInMillis()) / CONST_WEEK);
		return interval;
	}

	public static String formatInteger(int num) {
		char[] val = String.valueOf(num).toCharArray();
		int len = val.length;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			String m = val[i] + "";
			int n = Integer.valueOf(m);
			boolean isZero = n == 0;
			String unit = units[(len - 1) - i];
			if (isZero) {
				if ('0' == val[i - 1]) {
					// not need process if the last digital bits is 0
					continue;
				} else {
					// no unit for 0
					sb.append(numArray[n]);
				}
			} else {
				sb.append(numArray[n]);
				sb.append(unit);
			}
		}
		return sb.toString();
	}
	
	public static List<OrganTree> packageOrganTree(List<Map<String, String>> list,String organCode,String type) {
		List<OrganTree> organTreeList = new ArrayList<OrganTree>();
		for (int i = 0; i < list.size(); i++) {
			String PARENT_CODE = list.get(i).get("PARENT_CODE");
			if(PARENT_CODE.equals(organCode)) {
				String text = list.get(i).get("REGION_EDU");
				
				OrganTree organTree = new OrganTree();
				if(!"1".equals(type))organTree.setChecked("false");
				organTree.setText(text);
				organTree.setId(list.get(i).get("REGION_CODE"));
				List<OrganTree> childrenList = packageOrganTree(list, list.get(i).get("REGION_CODE"),type);
				if(childrenList == null || childrenList.size() <= 0) {
					organTree.setLeaf("true");
				}
				organTree.setChildren(childrenList);
				organTreeList.add(organTree);
			}
		}
		return organTreeList;
	}
	public static List<OrganTree> packageGradeClassTree(List<Map<String, String>> list,String organCode,String type) {
		List<OrganTree> organTreeList = new ArrayList<OrganTree>();
		for (int i = 0; i < list.size(); i++) {
			String PARENT_CODE = list.get(i).get("PRCODE");
			if(PARENT_CODE.equals(organCode)) {
				String text = list.get(i).get("NAME");
				
				OrganTree organTree = new OrganTree();
				if(!"1".equals(type) && !"0".equals(type))organTree.setChecked("false");
				organTree.setText(text);
				organTree.setId(list.get(i).get("CODE"));
				List<OrganTree> childrenList = packageGradeClassTree(list, list.get(i).get("CODE"),type);
				if(childrenList == null || childrenList.size() <= 0) {
					organTree.setLeaf("true");
				}
				organTree.setChildren(childrenList);
				organTreeList.add(organTree);
			}
		}
		return organTreeList;
	}
	public static String getMap(List<Map<String, Object>> list,String value) {
		return list.get(0).get(value) == null?"":list.get(0).get(value).toString();
	}
	public static String getMapByNum(List<Map<String, Object>> list,String value,int num) {
		return list.get(num).get(value) == null?"":list.get(num).get(value).toString();
	}
}
