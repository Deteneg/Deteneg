package classes;

import java.io.Serializable;

public class Word implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Save information about a word
	 * 
	 */
	private String type;
	private String token;
	private String POS;
	private boolean beginSentence;
	private boolean endSentence;

	public Word() {
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPOS() {
		return POS;
	}

	public void setPOS(String pos) {
		POS = pos;
	}

	public boolean getbeginSentence() {
		return beginSentence;
	}

	public void setbeginSentence(boolean option) {
		beginSentence = option;
	}

	public boolean getendSentence() {
		return endSentence;
	}

	public void setendSentence(boolean option) {
		endSentence = option;
	}

}
