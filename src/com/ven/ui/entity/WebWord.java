package com.ven.ui.entity;
/**
 * ��װҪ���뵥��
 * @author vector
 *
 */
public class WebWord {
	/**
	 * �������
	 */
	private int errorCode;
	
	/**
	 * Ҫ����Ĵ�
	 */
	private String query;
	
	/**
	 * ������
	 */
	private String translation;
	/**
	 * ����
	 */
	private String phonetic;
	/**
	 * 
	 */
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getTranslation() {
		return translation;
	}
	public void setTranslation(String translation) {
		this.translation = translation;
	}
	public String getPhonetic() {
		return phonetic;
	}
	public void setPhonetic(String phonetic) {
		this.phonetic = phonetic;
	}
	@Override
	public String toString() {
		return "WebWord [errorCode=" + errorCode + ", query=" + query
				+ ", translation=" + translation + ", phonetic=" + phonetic
				+ "]";
	}
	
	
}
