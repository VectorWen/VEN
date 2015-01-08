package com.ven.net;

import android.os.AsyncTask;

/**
 * 异步下载文件
 * @author vector
 *
 */
public class AsynDownloadFile extends AsyncTask<String, String, Boolean> {
	private OnDownloadFileDone done;
	private DownloadFile downloadFile;

	/**
	 * 创建对象
	 * @param done 下载完文件的回调接口
	 */
	public AsynDownloadFile(OnDownloadFileDone done) {
		this.done = done;
		downloadFile = new DownloadFile();
	}
	
	public AsynDownloadFile() {
		downloadFile = new DownloadFile();
	}
	
	/**
	 * 设置回调接口
	 * @param done
	 */
	public void setOnDownloadFileDone(OnDownloadFileDone done){
		this.done = done;
	}
	
	public void addRequestHeader(String header){
		downloadFile.addRequestHeader(header);
	}
	
	/**
	 * params[0-3] url, param, path, filename
	 * 异步中...
	 */
	protected Boolean doInBackground(String... params) {
		return downloadFile.downFile(params[0], params[1], params[2], params[3]);
	}

	/**
	 * 异步前 ui
	 */
	protected void onPreExecute() {
		super.onPreExecute();
	}

	/**
	 * 异步后
	 */
	protected void onPostExecute(Boolean result) {
		if(done!=null)
			done.done(result);
	}

}
