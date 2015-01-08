package com.ven.media;

import java.util.Timer;
import java.util.TimerTask;

import javax.net.ssl.HandshakeCompletedListener;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View.OnClickListener;

import com.ven.utils.FileUtils;

/**
 * 音频播放
 * 
 * @author vector
 * 
 */
@SuppressLint("HandlerLeak")
public class Media implements Callback {

	public static final int MESSAGE_SEEKER = 0;

	private Context context;
	private MediaPlayer mediaPlayer;

	// video 相关的
	private SurfaceView sv;
	private SurfaceHolder sh;
	private int currentPosition = 0;
	private String path;

	// Timer
	private Timer timer;
	private TimerTask timerTask;
	private OnSeekerLinstener seekerLinstener;
	private SeekerHandler handler;

	public Media(Context context) {
		this.context = context;
		timer = new Timer();
	}

	/**
	 * 设计进度监听
	 */
	public void setOnSeekerLinstener(OnSeekerLinstener seekerLinstener) {
		this.seekerLinstener = seekerLinstener;
		handler = new SeekerHandler();
	}

	/**
	 * 播放一个音频文件
	 * 
	 * @param path
	 *            要播放的文件 格式 ven/add.mp3
	 * @return
	 */
	public int play(String path) {
		if (!FileUtils.hasSDCard()) {
			return -2;
		}
		// 如果文件不存在
		if (!FileUtils.existes(path)) {
			return -1;
		}

		mediaPlayer = MediaPlayer.create(context,
				Uri.parse(FileUtils.SDCardRoot + path));

		// 当文件是坏的时候
		if (mediaPlayer == null) {
			Log.i("ven", FileUtils.SDCardRoot + path + " 文件损坏");
			FileUtils.delete(path);
			return -1;
		}
		mediaPlayer
				.setOnCompletionListener(new CompletionListener(mediaPlayer));

		mediaPlayer.setLooping(false);

		mediaPlayer.start();

		return 1;
	}

	/**
	 * 准备一个视频文件
	 * 
	 * @param path
	 *            ven/video/1.1.1.f4v
	 * @param sv
	 * @return
	 */
	public int playVideo(String path, SurfaceView sv) {

		if (!FileUtils.hasSDCard()) {
			return -2;
		}
		// 如果文件不存在
		if (!FileUtils.existes(path)) {
			return -1;
		}

		if (path == null || sv == null)
			return -3;
		this.sv = sv;
		this.sh = sv.getHolder();
		this.path = path;
		// 设置Holder 的Callback
		sh.addCallback(this);
		return 0;
	}

	public void start() {
		if (mediaPlayer != null) {
			mediaPlayer.start();
			mediaPlayer.seekTo(currentPosition);
			if (seekerLinstener != null) {
				startTimer();
			}
		}
	}

	public void pause() {
		if (mediaPlayer != null) {
			mediaPlayer.pause();
			currentPosition = mediaPlayer.getCurrentPosition();
			if (seekerLinstener != null) {
				stopTimer();
			}
		}
	}

	public void resume() {
		if (mediaPlayer != null) {
			mediaPlayer.start();
			if (seekerLinstener != null) {
				startTimer();
			}
		}
	}

	public void stop() {
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			if (seekerLinstener != null) {
				stopTimer();
			}
		}
	}

	/**
	 * 在视频开始播放的时候，开始计时，
	 */
	private void startTimer() {
		if (timerTask != null) {
			timerTask.cancel();
		}

		timerTask = new TimerTask() {

			@Override
			public void run() {
				// 时间到的时候，发送一个信息给handler，之后调用回调函数
				if (mediaPlayer != null) {
					handler.sendEmptyMessage(MESSAGE_SEEKER);
				} else {
					timerTask.cancel();
				}
			}
		};
		timer.schedule(timerTask, 0, 1000);
	}

	private void stopTimer() {
		if (timer != null && timerTask != null) {
			timerTask.cancel();
			timer.purge();
		}
	}

	/**
	 * 音频播放监听
	 * 
	 * @author vector
	 * 
	 */
	class CompletionListener implements OnCompletionListener {
		MediaPlayer mediaPlayer;

		public CompletionListener(MediaPlayer mediaPlayer) {
			this.mediaPlayer = mediaPlayer;
		}

		@Override
		public void onCompletion(MediaPlayer mp) {
			mediaPlayer.release();
		}
	}

	// Surface 回调方法
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		try {
			// 注销后的mediaPlayer 是不能播放了，需要重新new 一个
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

			mediaPlayer.setDisplay(sv.getHolder());

			mediaPlayer.setDataSource(FileUtils.SDCardRoot + path);

			mediaPlayer.prepare();
			setFull();
			
			mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
			  
				@Override 
				public void onPrepared(MediaPlayer mp) {
					start(); 
				} 
			});
			 
			mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer mp) {
					mediaPlayer.release();
					mediaPlayer = null;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	/**
	 * 销毁的时候，保存现场，之后停止播放，注销资源，如果mediaPlayer 本来就是播放完了，就不用做这些工作
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		if (mediaPlayer == null) {
			return;
		}
		currentPosition = mediaPlayer.getCurrentPosition();
		mediaPlayer.stop();
		mediaPlayer.release();
		mediaPlayer = null;

	}

	private void setFull() {

		if (sv == null | mediaPlayer == null)
			return;

		int height = mediaPlayer.getVideoHeight();
		int width = mediaPlayer.getVideoWidth();
		sv.getHolder().setFixedSize(width, height);
	}

	/**
	 * 一秒钟定时回调接口
	 * 
	 * @author vector
	 * 
	 */
	public interface OnSeekerLinstener {
		public void callBack(int current);
	}

	public class SeekerHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MESSAGE_SEEKER:
				if (seekerLinstener != null&&mediaPlayer!=null) {
					seekerLinstener.callBack(mediaPlayer.getCurrentPosition());
				}
				break;

			default:
				break;
			}
		}

	}
}
