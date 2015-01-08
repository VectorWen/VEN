package com.ven.save;

import android.content.Context;
import android.util.Log;

import com.ven.entity.TodayVideoInfo;
import com.ven.utils.DateTimeUtils;
import com.ven.utils.FileUtils;

/**
 * 操作TodayVideoInfo 数据在SharedPreferend 的读写更新<p>
 * 
 * getInfo()拿到今天视频信息，保证是今天的，在没有手动删除视频的时候保存 info.IsDown() 也是正确的<br>
 * 
 * 建议在播放的时候要判断一下文件是否存在，如果出错，可以通过setDown(boolean) 修改
 * 
 * @author vector
 *
 */
public class TodayVideoInfoDao {
	//问题：在运行的时候，本来有视频文件的，但手动删除了，所以这里的info 也不是很准确
	//如果程序练习运行，都运行了一天了，这个单例的数据全部都应该改变了，怎么办呢？难道在其他地方还要用
	//	if(!info.getTodayDate().equals(DateTimeUtils.getTodayDateLine())){
	//		info.setTodayDate(DateTimeUtils.getTodayDateLine());
	//		addOne();
	//	}
	// 为了简单，不考虑这个情况
	
	public final static String SP_KEY_TODAYVIDEO = "todayVideo";
	
	/**
	 * 单例
	 */
	private static TodayVideoInfo info;
	
	/**
	 * @return
	 */
	public static TodayVideoInfo getInfo() {
		return info;
	}

	//这个创建的时候需要Context，不能是单例
	private DataPool dp;
	
	//创建对象后，info 保证是今天的信息，是最有效的
	public TodayVideoInfoDao(Context context){
		dp = new DataPool(context);
		//单例的需要判断
		if(info == null){
			info = (TodayVideoInfo) dp.get(SP_KEY_TODAYVIDEO);
			if(info == null){
				initInfo();
			}else{
				//如果不是今天的就更新为今天，之后视频类加1
				if(!info.getTodayDate().equals(DateTimeUtils.getTodayDateLine())){
					info.setTodayDate(DateTimeUtils.getTodayDateLine());
					addOne();
				}
			}
			//到了这里，日期是今天的，sp 里面有了数据，就差判断有没有视频文件
			if(FileUtils.existes("ven/video/"+info.getFileName())&&FileUtils.existes("ven/video/"+info.getInfoFileName())){
				info.setDown(true);
			}else{
				info.setDown(false);
			}
		}
	}
	
	/**
	 * 初始化一个info，之后写入sp
	 */
	private void initInfo(){
		info = new TodayVideoInfo(4, 1, 1, "4.1.1.f4v","4.1.1.json","4.1.1title.json", DateTimeUtils.getTodayDateLine(), true, false);
		dp.put(SP_KEY_TODAYVIDEO, info);
	}
	
	/**
	 * 增加一天，数据加1，之后写到了sp
	 */
	public void addOne() {
		
		if(info.getThree() < 4){
			info.setThree(info.getThree() +1);
		}else if(info.getTwe() < 8){
			info.setThree(1);
			info.setTwe(info.getTwe() +1);
		}else if(info.getOne()  < 14){
			info.setThree(1);
			info.setTwe(1);
			info.setOne(info.getOne() +1);
		}else  {
			info.setOne(1);
			info.setTwe(1);
			info.setThree(1);
		}
		
		changeFileName();
		dp.set(SP_KEY_TODAYVIDEO, info);
		Log.i("ven", "addOne");
		
		Log.i("vector",info.getFileName());
	}
	
	private void changeFileName(){
		info.setFileName(info.getOne()+"."+info.getTwe()+"."+info.getThree()+".f4v");
		info.setInfoFileName(info.getOne()+"."+info.getTwe()+"."+info.getThree()+".json");
		info.setTitleFileName(info.getOne()+"."+info.getTwe()+"."+info.getThree()+"title.f4v");
	}
	
	public void setDown(boolean down){
		info.setDown(down);
		dp.set(SP_KEY_TODAYVIDEO, info);
	}
}
