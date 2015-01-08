package com.ven.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {
	
	private DateTimeUtils(){}
	
	/**
	 * 以合适每天一句的日期形式 14-5-24
	 * @return
	 */
	public static String getTodayDateLine(){
		Date nowTime=new Date(); 
		SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd"); 
		System.out.println(time.format(nowTime)); 
		return time.format(nowTime);
	}
}
