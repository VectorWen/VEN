package com.ven.ui.entity;
/**
 * 封装要翻译单词
 * @author vector
 *
 */
public class WebWord {
	/**
	 * 错误代码
	 */
	private int errorCode;
	
	/**
	 * 要翻译的词
	 */
	private String query;
	
	/**
	 * 翻译结果
	 */
	private String translation;
	/**
	 * 音标
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
