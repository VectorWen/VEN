package com.ven;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;
 

public abstract class BaseUIActivity extends Activity {
	/**
	 * ��ʼ��Layout UI �ȵ�
	 */
	protected abstract void initLayout();

	/**
	 * ��ʼ������
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
	 * ��һ��Class<?> cls����һ��Activity
	 * 
	 * @param cls
	 */
	public void openActivity(Class<?> cls) {
		this.startActivity(new Intent(this, cls));
	}

	/**
	 * ��һ��intent������һ��Activity
	 * 
	 * @param intent
	 */
	public void openActicity(Intent intent) {
		this.startActivity(intent);
	}
	/**
	 * �ر�Activity
	 */
	public void closeActivity(){
		this.finish();
	}

	/**
	 * ��װ��Toast��ֱ��toast��String content��
	 * 
	 * @param content
	 *            content of your want to Toast
	 */
	public void toast(String content) {
		Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
	}

	/**
	 * ������װfindViewById(int id)
	 * 
	 * @param id
	 * @return
	 */
	public View _getView(int id) {
		return this.findViewById(id);
	}
	/**
	 * ��class ��װһ��Intent;
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
