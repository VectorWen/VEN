package com.ven.entity;

import java.io.Serializable;

import com.ven.utils.FileUtils;
/**
 * 保存今天的视频信息，应该尽量的让数据是最新的，一旦发现过时，应该立即更新
 * @author vector
 *
 */
public class TodayVideoInfo implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private int one,twe,three;
	private String fileName,infoFileName,titleFileName;
	/**
	 * 今天的日期，如果不是今天的，表示这个已经过期，应该下载最新一天的,2014-11-03
	 */
	private String todayDate;
	
	
	//废弃了2014年11月4日
	private boolean isToday;
	
	/**
	 * 今天的视频下载了吗？
	 */
	private boolean isDown;
	
	
	
	
	public String getInfoFileName() {
		return infoFileName;
	}

	public TodayVideoInfo(int one, int twe, int three, String fileName,
			String infoFileName, String titleFileName, String todayDate,
			boolean isToday, boolean isDown) {
		super();
		this.one = one;
		this.twe = twe;
		this.three = three;
		this.fileName = fileName;
		this.infoFileName = infoFileName;
		this.titleFileName = titleFileName;
		this.todayDate = todayDate;
		this.isToday = isToday;
		this.isDown = isDown;
	}

	public String getTitleFileName() {
		return titleFileName;
	}

	public void setTitleFileName(String titleFileName) {
		this.titleFileName = titleFileName;
	}

	public void setInfoFileName(String infoFileName) {
		this.infoFileName = infoFileName;
	}

	public TodayVideoInfo() {
	}

	public boolean isDown() {
		
		if(FileUtils.existes("ven/video/"+getFileName())&&FileUtils.existes("ven/video/"+getInfoFileName())){
			setDown(true);
		}else{
			setDown(false);
		}
		
		return isDown;
	}

	public void setDown(boolean isDown) {
		this.isDown = isDown;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	public boolean isToday() {
		return isToday;
	}

	public void setToday(boolean isToday) {
		this.isToday = isToday;
	}

	public String getTodayDate() {
		return todayDate;
	}

	public void setTodayDate(String todayDate) {
		this.todayDate = todayDate;
	}

	public int getOne() {
		return one;
	}

	public void setOne(int one) {
		this.one = one;
	}

	public int getTwe() {
		return twe;
	}

	public void setTwe(int twe) {
		this.twe = twe;
	}

	public int getThree() {
		return three;
	}

	public void setThree(int three) {
		this.three = three;
	}

	@Override
	public String toString() {
		return "TodayVideoInfo [one=" + one + ", twe=" + twe + ", three="
				+ three + ", fileName=" + fileName + ", infoFileName="
				+ infoFileName + ", titleFileName=" + titleFileName
				+ ", todayDate=" + todayDate + ", isToday=" + isToday
				+ ", isDown=" + isDown + "]";
	}

}
