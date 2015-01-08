package com.ven.app;

import com.ven.save.TodayVideoInfoDao;

import android.app.Application;
import android.util.Log;
/**
 * 这个应用程序的Application 类
 * @author vector
 *
 */
public class App extends Application {

	@Override
	public void onCreate() {
		Log.i("ven", "App -- onCreate");
		//初始化TodayVideoInfo,保证数据是今天的
		new TodayVideoInfoDao(getApplicationContext());
		 Log.i("ven",TodayVideoInfoDao.getInfo().toString());
		super.onCreate();
	}
	
}
