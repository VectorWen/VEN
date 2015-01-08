package com.ven.save;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLEncoder;

import android.os.AsyncTask;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.ven.entity.Sentence;
import com.ven.entity.Word;
import com.ven.utils.FileUtils;

/**
 * 网络帮助者，可以网络下载数据
 * 
 * @author vector
 * 
 */
public class NetHelper {

	private AsyncHttpClient client = new AsyncHttpClient();

	/**
	 * 下载
	 */
	public void downloadSentence(OnSentenceDone done) {
		String urlSentence = "http://open.iciba.com/dsapi/";

		client.get(urlSentence, new SentenceResponseHandler(done));
	}

	/**
	 * 翻译单词
	 * 
	 * @param query
	 *            要翻译的单词
	 * @param done
	 *            翻译完成的回调接口
	 */
	public void translation(String query, OnTranslationDone done) {

		String urlTranslation = "http://fanyi.youdao.com/opena"
				+ "pi.do?keyfrom=VectorHuang&key=2066"
				+ "326006&type=data&doctype=json&version=1." + "1&q=";

		try {
			urlTranslation += URLEncoder.encode(query.trim(), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		client.get(urlTranslation, new TranslationResponseHandler(done));
	}

	/**
	 * 下载句子读音
	 * 
	 * @param url
	 *            地址
	 * @param done
	 *            下载完成的回调接口
	 * @param fileName
	 *            保存文件用的名字 : sentence
	 */
	public void downloadPronunciation(String url, String fileName,
			OnDownloadPronunciationDone done) {

		try {
			client.get(url, new DownloadSentencePronunciationResponseHandler(
					done, FileUtils.createFile(fileName+".mp3", "ven/sentence")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 下载单词读音, 如果有就覆盖
	 * 
	 * @param query
	 *            要下载的单词读音
	 * @param done
	 *            下载完成的回调接口
	 */
	public void downloadPronunciation(String query,
			OnDownloadPronunciationDone done) {

		new DownloadPronunciationResponseHandler(query, done).execute("");

	}

	/**
	 * 翻译响应
	 * 
	 * @author vector
	 * 
	 */
	class TranslationResponseHandler extends AsyncHttpResponseHandler {
		private OnTranslationDone done;
		private Word word;

		public TranslationResponseHandler(OnTranslationDone done) {
			this.done = done;
		}

		@Override
		public void onFinish() {
			if (done != null)
				done.onDone(word);
		}

		@Override
		public void onSuccess(String json) {
			word = Word.createByJson(json);
		}

	}

	/**
	 * 下载句子响应
	 * 
	 * @author vector
	 * 
	 */
	class SentenceResponseHandler extends AsyncHttpResponseHandler {
		private OnSentenceDone done;
		private Sentence sentence;

		public SentenceResponseHandler(OnSentenceDone done) {
			this.done = done;
		}

		@Override
		public void onFinish() {
			if (done != null)
				done.onDone(sentence);
		}

		@Override
		public void onSuccess(String json) {
			sentence = Sentence.createByJson(json);
		}

	}

	/**
	 * 下载句子读音响应
	 * 
	 * @author vector
	 * 
	 */
	class DownloadSentencePronunciationResponseHandler extends
			FileAsyncHttpResponseHandler {

		private OnDownloadPronunciationDone done;
		private boolean isSuccess = false;

		public DownloadSentencePronunciationResponseHandler(
				OnDownloadPronunciationDone done, File file) {
			super(file);
			this.done = done;
		}

		@Override
		public void onSuccess(File file) {
			System.out.println(file.toString());
			isSuccess = true;
		}

		@Override
		public void onFinish() {
			if (done != null)
				done.onFinish(isSuccess);
		}

	}

	class DownloadPronunciationResponseHandler extends
			AsyncTask<String, String, Boolean> {
		private OnDownloadPronunciationDone done;
		private String query;

		public DownloadPronunciationResponseHandler(String query,
				OnDownloadPronunciationDone done) {
			this.query = query;
			this.done = done;
		}

		/**
		 * 异步中...
		 */
		protected Boolean doInBackground(String... params) {
			Socket socket = null;
			try {

				socket = new Socket("translate.google.cn", 80);

				OutputStream out = socket.getOutputStream();
				out.write(getHeader(query).getBytes());
				socket.shutdownOutput();

				InputStream in = socket.getInputStream();
				del(in);
				FileUtils.writeFile("ven", query + ".mp3", in);
				return true;
			} catch (Exception e) {
				return false;
			}
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
			if (done != null)
				done.onFinish((Boolean) result);
		}

	}

	/**
	 * 翻译完成的回调
	 * 
	 * @author vector
	 * 
	 */
	public interface OnTranslationDone {
		/**
		 * @param word
		 *            可能为null
		 */
		public void onDone(Word word);
	}

	/**
	 * 下载每天一句完成的回调函数
	 * 
	 * @author vector
	 * 
	 */
	public interface OnSentenceDone {
		/**
		 * @param sentence
		 *            可能为null
		 */
		public void onDone(Sentence sentence);
	}

	/**
	 * 下载读音完成的回调函数
	 * 
	 * @author vector
	 * 
	 */
	public interface OnDownloadPronunciationDone {
		/**
		 * 下载结束
		 * 
		 * @param isSuccess
		 *            是否下载成功
		 */
		public void onFinish(boolean isSuccess);
	}

	/**
	 * 组合请求头
	 * 
	 * @param query
	 * @return
	 */
	private String getHeader(String query) {
		StringBuffer sb = new StringBuffer();
		sb.append("GET /translate_tts?ie=UTF-8&q=" + query
				+ "&tl=en&total=1&idx=0&textlen=" + query.length()
				+ "&prev=input HTTP/1.1");
		sb.append("\n");
		sb.append("Host: translate.google.cn");
		sb.append("\n");
		sb.append("Connection: keep-alive");
		sb.append("\r\n\r\n");
		return sb.toString();
	}

	/**
	 * 删除响应头
	 * 
	 * @param in
	 * @throws IOException
	 */
	private void del(InputStream in) throws IOException {
		int c = 0;
		c = in.read();
		int lineCount = 0;
		while (c > -1) {
			if (c == 10 || c == 13) {
				if (lineCount == 3) {
					return;
				}
				lineCount++;
			} else {
				lineCount = 0;
			}
			c = in.read();
		}
	}

}
