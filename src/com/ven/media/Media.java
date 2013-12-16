package com.ven.media;

import com.ven.lg.utils.FileUtils;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;

/**
 * ������Ƶ�ļ�����Ҫ����Ե�����Ƶ��д���߼�
 * @author vector
 *
 */
public class Media {
	/**
	 * ˭���õ�
	 */
	private Context context;
	/**
	 * ������
	 */
	private MediaPlayer mediaPlayer;
	/**
	 * Ҫ���ŵ��ļ�·��
	 */
	private String rootPath = "file:///sdcard/ven/";
	
	/**
	 * ��ʼ������
	 * @param context
	 */
	public Media(Context context) {
		this.context = context;
	}

	
	/**
	 * ����MP3�����ж��Ƿ���sdcard �����ж��ļ�ʱ�����
	 * @param path
	 */
	public int play(String word){
		//�ж��Ƿ���sdcard ��
		if(!FileUtils.hasSDCard()){
			return -1;
		}
		
		//�ж��ļ��Ƿ����
		if(!FileUtils.ifFileExist(word+".mp3", "ven")){
			//����ȥ
			return -2;
		}
		
		mediaPlayer = MediaPlayer.create(context, Uri.parse(rootPath+word+".mp3"));
		mediaPlayer.setOnCompletionListener(new CompletionListener(mediaPlayer));
		mediaPlayer.setLooping(false);
		mediaPlayer.start();
		
		return 1;
	}
	
	/**
	 * ��������
	 * @author vector
	 *
	 */
	class CompletionListener implements OnCompletionListener {
		MediaPlayer mediaPlayer;
		int playCount = 1;
		
		public CompletionListener(MediaPlayer mediaPlayer) {
			this.mediaPlayer = mediaPlayer;
		}
		@Override
		public void onCompletion(MediaPlayer mp) {
			if(playCount<3){
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				mediaPlayer.start();
				playCount++;
			}
		}
	}
}
