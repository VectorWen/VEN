package com.ven.net;

public interface OnDownloadFileDone {
	/**
	 * 下载文件结束后的回调方法
	 * @param result 下载成功 return TRUE
	 */
	public void done(boolean result);
}
