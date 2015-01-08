package com.ven.net;

import java.io.IOException;

import com.ven.utils.FileUtils;

import android.os.AsyncTask;

public class DownloadVideo {
	
	/**
	 * 同步下载英语教学视频
	 * @param one 1-14
	 * @param twe 1-8
	 * @param three 1-4
	 * @param fileName 保存视频的文件名
	 * @throws IOException
	 */
	public void downloadVideo(int one,int twe,int three,String fileName) throws IOException{
		DownloadFile download = new DownloadFile();
		
		download.addRequestHeader("Host: cnak2.englishtown.com");
		download.addRequestHeader("Connection: keep-alive");
		

		String param = "/juno/school/videos/"+one+"."+twe+"%20scene%20"+three+".f4v";
		
		download.downFile("cnak2.englishtown.com", param, "ven/video/",fileName);
	}
	
	/**
	 * 异步下载英语教学视频
	 * @param one 1-14
	 * @param twe 1-8
	 * @param three 1-4
	 * @param fileName 保存视频的文件名
	 * @param done 下载完的回调接口
	 */
	public void AsynDownloadVideo(int one,int twe,int three,String fileName,OnDownloadFileDone done){
		AsynDownloadFile download = new AsynDownloadFile(done);
		
		download.addRequestHeader("Host: cnak2.englishtown.com");
		download.addRequestHeader("Connection: keep-alive");

		String param = "/juno/school/videos/"+one+"."+twe+"%20scene%20"+three+".f4v";
		
		download.execute("cnak2.englishtown.com", param, "ven/video/",fileName);
	}
	/**
	 * 同步下载视频信息，里面包括视频的字幕，字幕与视频的对应关系
	 * @param one
	 * @param twe
	 * @param three
	 * @param fileName
	 */
	public void downloadVideoInfo(int one,int twe,int three,String fileName){
		int lesson_id = mumble2LessonId(one, twe, three);
		
		DownloadFile download = new DownloadFile();
		download.addRequestHeader("Host: www.englishtown.cn");
		download.addRequestHeader("Connection: keep-alive");
		
		String param = "/community/dailylesson/lessonhandler.ashx?operate=preloaddata&teachculturecode=en&lesson_id="+lesson_id+"&transculturecode=zh-CN&ss=EE&v=42-1";
		
		download.downFile("www.englishtown.cn", param, "ven/video/",fileName);
	}
	
	/**
	 * 异步下载视频信息，里面包括视频的字幕，字幕与视频的对应关系
	 * @param one
	 * @param twe
	 * @param three
	 * @param fileName
	 * @param done
	 */
	public void AsynDownloadVideoInfo(int one,int twe,int three,String fileName,OnDownloadFileDone done){
		int lesson_id = mumble2LessonId(one, twe, three);
		
		AsynDownloadFile download = new AsynDownloadFile(done);
		
		download.addRequestHeader("Host: www.englishtown.cn");
		download.addRequestHeader("Connection: keep-alive");
		
		String param = "/community/dailylesson/lessonhandler.ashx?operate=preloaddata&teachculturecode=en&lesson_id="+lesson_id+"&transculturecode=zh-CN&ss=EE&v=42-1";
		
		download.execute("www.englishtown.cn", param, "ven/video/",fileName);
	}
	
	/**
	 * 异步下载视频题目
	 * @param one
	 * @param twe
	 * @param three
	 * @param fileName 1.1.1title.json
	 * @param done
	 */
	public void AsynDownloadVideoTitle(int one,int twe,int three,String fileName,OnDownloadFileDone done){
		int lesson_id = mumble2LessonId(one, twe, three);
		
		AsynDownloadFile download = new AsynDownloadFile(done);
		
		download.addRequestHeader("Host: www.englishtown.cn");
		download.addRequestHeader("Connection: keep-alive");
		
		String param = "/community/dailylesson/lessonhandler.ashx?operate=getlessonbyid&lesson_id="+lesson_id+"&transculturecode=zh-CN&v=42-1";
		
		download.execute("www.englishtown.cn", param, "ven/video/",fileName);
	}
	
	
	private int mumble2LessonId(int one,int twe,int three){
		return 329 + (one -1) *32 + (twe - 1)*4 + (three - 1);
	}
	
}
