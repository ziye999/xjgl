package exam;

import java.io.Serializable;
import java.util.Map;

public class PaperAnswer implements Serializable{

	/**
	 *  
	 */
	private static final long serialVersionUID = 1L;

	private String ksid;
	
	/*
	 * 描述：學生所有答案信息，
	 * 格式：試題id_答案id
	 */
	private Map<String,String> answerInfos;

	public String getKsid() {
		return ksid;
	}

	public void setKsid(String ksid) {
		this.ksid = ksid;
	}

	public Map<String, String> getAnswerInfos() {
		return answerInfos;
	}

	public void setAnswerInfos(Map<String, String> answerInfos) {
		this.answerInfos = answerInfos;
	}
 
}
