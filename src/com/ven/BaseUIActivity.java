package com.ven;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;
 

public abstract class BaseUIActivity extends Activity {
	/**
	 * 初始化控件
	 */
	protected abstract void initLayout();

	/**
	 * 初始化数据，在initLayout() 前调用
	 */
	protected abstract void initData();

	@Override
	protected void onPause() {
		super.onPause();

	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	/**
	 * 
	 * @param cls
	 */
	public void openActivity(Class<?> cls) {
		this.startActivity(new Intent(this, cls));
	}

	/**
	 * 
	 * @param intent
	 */
	public void openActicity(Intent intent) {
		this.startActivity(intent);
	}
	/**
	 */
	public void closeActivity(){
		this.finish();
	}

	/**
	 * 
	 * @param content
	 *            content of your want to Toast
	 */
	public void toast(String content) {
		Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public View _getView(int id) {
		return this.findViewById(id);
	}
	/**
	 * @param cls
	 * @return
	 */
	public Intent wrapIntent(Class<?> cls){
		return new Intent(this,cls);
	}
	public Context getContext(){
		return this;
	}
}
