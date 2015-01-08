package com.ven.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ven.MainActivity;
import com.ven.R;
import com.ven.entity.TodayVideoInfo;
import com.ven.entity.VideoTitle;
import com.ven.net.DownloadVideo;
import com.ven.net.OnDownloadFileDone;
import com.ven.save.DataPool;
import com.ven.save.TodayVideoInfoDao;
import com.ven.utils.DateTimeUtils;
import com.ven.utils.FileUtils;
/**
 * 播放一个视频文件，在这之前需要下载好资源文件
 * @author vector
 *
 */
public class DownVideoFragment extends Fragment implements OnClickListener{

	private TextView tvInfo;
	private TextView btnDown;
	private View view;
	private DownloadVideo downloadVideo;
	private final String VIDEO_PATH = "ven/video/";
	private TodayVideoInfo todayVideoInfo;
	private MainActivity mainActy;
	
	private int isDone = 0; //当isDone == 2 时，下载完毕
	private boolean videoSucceed = false;
	private boolean infoSucceed = false;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.frame_downvideo, null);
		tvInfo = (TextView) view.findViewById(R.id.frame_downvideo_tv_info);
		btnDown = (TextView) view.findViewById(R.id.frame_downvideo_btn_down);
		btnDown.setOnClickListener(this);
		initData();
		
		return view;
	}

	private void initData() {
		mainActy = (MainActivity)getActivity();
		//读取one,todayVideoInfo.getTwe(),todayVideoInfo.getThree()
		todayVideoInfo = TodayVideoInfoDao.getInfo();
		downloadVideo = new DownloadVideo();
		
		//下载标题信息
		//检查是否有文件存在，没有就去下载
		if (!FileUtils.existes(VIDEO_PATH +todayVideoInfo.getTitleFileName())){
			downloadVideo.AsynDownloadVideoTitle(todayVideoInfo.getOne(), todayVideoInfo.getTwe(), todayVideoInfo.getThree(), todayVideoInfo.getTitleFileName(), new OnDownloadFileDone() {
				
				@Override
				public void done(boolean result) {
					setTitle();
				}
			});
		}else{
			setTitle();
		}
	}
	
	private void setTitle(){
		String tileJson = FileUtils.read(VIDEO_PATH+todayVideoInfo.getTitleFileName());
		VideoTitle title = VideoTitle.createByJson(tileJson);
		if(title!=null){
			String buffer = "今天课程\n课题名称：" + title.getLesson().getLessonName()+"\n";
			buffer += "课题级别：" + title.getCourseName()+"\n";
			buffer += "课程描述：" + title.getLesson().getLessonDescr();
			tvInfo.setText(buffer);
		}
	}

	@Override
	public void onClick(View v) {
		
		btnDown.setClickable(false);
		btnDown.setText("下载中...");
		down();
		
	}
	
	private void down(){
		
		//检查是否有文件存在，没有就去下载
		if (!FileUtils.existes(VIDEO_PATH +todayVideoInfo.getFileName())) {
			downloadVideo.AsynDownloadVideo(todayVideoInfo.getOne(), todayVideoInfo.getTwe(), todayVideoInfo.getThree(), todayVideoInfo.getFileName(), new OnDownloadFileDone() {
				     
				@Override
				public void done(boolean result) {
					isDone++;
					if(!result){
						//下载视频文件失败
						FileUtils.delete(VIDEO_PATH+todayVideoInfo.getFileName());
					}else{
						videoSucceed = true;
					}
					downDone();
				}
			});
		}else{
			isDone++;
			videoSucceed = true;
			downDone();
		}
		
		if (!FileUtils.existes(VIDEO_PATH+todayVideoInfo.getInfoFileName())) {
			Log.i("ven","正在下载视频信息"+VIDEO_PATH+todayVideoInfo.getInfoFileName());
			downloadVideo.AsynDownloadVideoInfo(todayVideoInfo.getOne(), todayVideoInfo.getTwe(), todayVideoInfo.getThree(), todayVideoInfo.getInfoFileName(), new OnDownloadFileDone() {
				
				@Override
				public void done(boolean result) {
					isDone++;
					if(!result){
						//下载视频文件失败
						FileUtils.delete(VIDEO_PATH+todayVideoInfo.getInfoFileName());
					}else{
						infoSucceed = true;
					}
					downDone();
				}
			});
		}else{
			isDone++;
			infoSucceed = true;
			downDone();
		}
	}
	
	private void downDone(){
		if(isDone==2){
			if(videoSucceed&&infoSucceed){
				//下载视频文件成功，跳转到播放界面
				new TodayVideoInfoDao(mainActy).setDown(true);
				tvInfo.setText("下载成功");
				setVisibility(View.GONE);
				mainActy.downFragment2VideoFragment();
			}else{
				tvInfo.setText("视频文件和视频信息文件下载都失败了，点击下面按钮重试。");
				btnDown.setText("重试");
				isDone = 0;
				btnDown.setClickable(true);
			}
		}
		
	}
	
	public void setVisibility(int visibility){
		if(view !=null)
			view.setVisibility(visibility);
	}
	
	public void test(){
		ScrollView view = new ScrollView(mainActy);
		view.scrollTo(1, 1);
	}
	
}
