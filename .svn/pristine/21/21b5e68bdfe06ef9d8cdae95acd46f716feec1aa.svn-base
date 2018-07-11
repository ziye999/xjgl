package com.jiajie.util;

/**
 * 
 * @author clu
 * @version BarCode(生成条形码类),created on 2009-09-01
 *
 */
public class BarCode {

	/**
	 * 条形码
	 * @param str 输入的字符串
	 * @param ch 要显示每一条条形码的高度，50
	 * @param cw 要显示每一条条形码的宽度，1
	 * @return String
	 */
	public String createBarCode(Object str, int ch, int cw) {
		String strTmp = ("*"+str+"*").toString();;
		// toLowerCase()将strTmp转化成小写形式的副本，返回是使用指定区域的性的大小写规则。
		strTmp = strTmp.toLowerCase();
		int height = ch;
		int width = cw;
		// 将传入的参数转化为对应的横、竖代码
		strTmp = strTmp.replace("0", "_|_|__||_||_|"); ;
		strTmp = strTmp.replace("1", "_||_|__|_|_||");
		strTmp = strTmp.replace("2", "_|_||__|_|_||");
		strTmp = strTmp.replace("3", "_||_||__|_|_|");
		strTmp = strTmp.replace("4", "_|_|__||_|_||");
		strTmp = strTmp.replace("5", "_||_|__||_|_|");
		strTmp = strTmp.replace("7", "_|_|__|_||_||");
		strTmp = strTmp.replace("6", "_|_||__||_|_|");
		strTmp = strTmp.replace("8", "_||_|__|_||_|");
		strTmp = strTmp.replace("9", "_|_||__|_||_|");
		strTmp = strTmp.replace("a", "_||_|_|__|_||");
		strTmp = strTmp.replace("b", "_|_||_|__|_||");
		strTmp = strTmp.replace("c", "_||_||_|__|_|");
		strTmp = strTmp.replace("d", "_|_|_||__|_||");
		strTmp = strTmp.replace("e", "_||_|_||__|_|");
		strTmp = strTmp.replace("f", "_|_||_||__|_|");
		strTmp = strTmp.replace("g", "_|_|_|__||_||");
		strTmp = strTmp.replace("h", "_||_|_|__||_|");
		strTmp = strTmp.replace("i", "_|_||_|__||_|");
		strTmp = strTmp.replace("j", "_|_|_||__||_|");
		strTmp = strTmp.replace("k", "_||_|_|_|__||");
		strTmp = strTmp.replace("l", "_|_||_|_|__||");
		strTmp = strTmp.replace("m", "_||_||_|_|__|");
		strTmp = strTmp.replace("n", "_|_|_||_|__||");
		strTmp = strTmp.replace("o", "_||_|_||_|__|");
		strTmp = strTmp.replace("p", "_|_||_||_|__|");
		strTmp = strTmp.replace("r", "_||_|_|_||__|");
		strTmp = strTmp.replace("q", "_|_|_|_||__||");
		strTmp = strTmp.replace("s", "_|_||_|_||__|");
		strTmp = strTmp.replace("t", "_|_|_||_||__|");
		strTmp = strTmp.replace("u", "_||__|_|_|_||");
		strTmp = strTmp.replace("v", "_|__||_|_|_||");
		strTmp = strTmp.replace("w", "_||__||_|_|_|");
		strTmp = strTmp.replace("x", "_|__|_||_|_||");
		strTmp = strTmp.replace("y", "_||__|_||_|_|");
		strTmp = strTmp.replace("z", "_|__||_||_|_|");
		strTmp = strTmp.replace("-", "_|__|_|_||_||");
		strTmp = strTmp.replace("*", "_|__|_||_||_|");
		strTmp = strTmp.replace("/", "_|__|__|_|__|");
		strTmp = strTmp.replace("%", "_|_|__|__|__|");
		strTmp = strTmp.replace("+", "_|__|_|__|__|");
		strTmp = strTmp.replace(".", "_||__|_|_||_|");
		// 将横、竖代码转化成html代码
		strTmp = strTmp.replace("_", "<div style=\"float:left; height:" + height + ";border-left-style:solid;border-left-width:" + width + ";border-left-color:#FFFFFF;\"></div>");
		strTmp = strTmp.replace("|", "<div style=\"float:left; height:" + height + ";border-left-style:solid;border-left-width:" + width + ";border-left-color:#000000;\"></div>");

		return strTmp;
	}
}
