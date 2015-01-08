package com.ven.entity;

import com.google.gson.Gson;

/**
 * 封装句子属性
 * 
 * @author vector
 * 
 */
public class Sentence {

	public static Sentence createByJson(String json) {
		Gson gson = new Gson();
		return gson.fromJson(json, Sentence.class);
	}

	private int id;
	
	/**
	 * mp3 下载路径
	 */
	private String tts;

	private String content;

	/**
	 * 中文解析
	 */
	private String note;

	/**
	 * 小图片下载路径
	 */
	private String picture;

	/**
	 * 大图片下载路径
	 */
	private String picture2;

	/**
	 * 日期，也是mp3 文件名称
	 */
	private String dateline;

	/**
	 * 通过的次数
	 */
	private int number;

	/**
	 * 玩的总数
	 */
	private int total;

	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getTts() {
		return tts;
	}

	public void setTts(String tts) {
		this.tts = tts;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getPicture2() {
		return picture2;
	}

	public void setPicture2(String picture2) {
		this.picture2 = picture2;
	}

	public String getDateline() {
		return dateline;
	}

	public void setDateline(String dateline) {
		this.dateline = dateline;
	}

	@Override
	public String toString() {
		return "Sentence [id=" + id + ", tts=" + tts + ", content=" + content
				+ ", note=" + note + ", picture=" + picture + ", picture2="
				+ picture2 + ", dateline=" + dateline + ", number=" + number
				+ ", total=" + total + "]";
	}

	

}
