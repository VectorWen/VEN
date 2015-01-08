package com.ven.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ven.MainActivity;
import com.ven.R;
import com.ven.entity.TodayVideoInfo;
import com.ven.entity.VideoSentence;
import com.ven.media.Media;
import com.ven.media.Media.OnSeekerLinstener;
import com.ven.save.TodayVideoInfoDao;
import com.ven.utils.FileUtils;
/**
 * 播放一个视频文件，在这之前一定要下载好资源文件
 * @author vector
 *
 */
public class VideoFragment extends Fragment {
	private View view;
	private Media media = null;
	//控件
	private SurfaceView sv = null;
	private TextView tvSentences = null;
	
	//数据
	private VideoSentence[] vss;
	private TodayVideoInfo todayVideoInfo;
	private MainActivity mainActy;
	
	private int seeker = 0;
	private int oldSent = -1;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		view = inflater.inflate(R.layout.frame_video, null);
		media = new Media(getActivity());
		initLayout();
		play();
		return view;
	}
	
	public void play(){
		initData();
		media = new Media(getActivity());
		media.playVideo("ven/video/"+todayVideoInfo.getFileName(), sv);
		media.setOnSeekerLinstener(new OnSeekerLinstener() {
			
			@Override
			public void callBack(int current) {
				seeker ++;
				// TODO Auto-generated method stub
				Log.i("ven","seeker = "+ seeker);
				setSentences();
			}
		});
		//不能就这样开始播放了
		//media.start();
		initSentences();
	}
	
	private void initLayout(){
		tvSentences = (TextView) view.findViewById(R.id.frame_video_tv_sentences);
		sv = (SurfaceView) view.findViewById(R.id.frame_video_sv_video);
	}
	
	private void initData(){
		mainActy = ((MainActivity)getActivity());
		todayVideoInfo = TodayVideoInfoDao.getInfo();
		
		if (!FileUtils.existes("ven/video/"+todayVideoInfo.getInfoFileName())) {
			return;
		}
		vss = VideoSentence.createByJson(FileUtils.read("ven/video/"+todayVideoInfo.getInfoFileName()));
	}
	/**
	 * 显示字幕
	 * @param current 下载播放到哪里了
	 */
	private void setSentences(){
		
		//如果下一个句子的播放时间打过下载的时间，就可以返回
		if((oldSent+1)==vss.length||vss[oldSent+1].getStartTimeInSecond() > seeker){
			return;
		}
		
		//否则一定是等于的
		
		String sentences = "";
		
		oldSent ++; //那么句子就加1
		if(oldSent == 0){
			sentences += "<br>"+vss[oldSent].getCharacter().getName()+" : <font color=\"red\">" + vss[oldSent].getSentence().getText()+"</font></br>";
			sentences += "<br>" + vss[oldSent].getTrans().getZHCN().getText()+"</br>";
			sentences += "<br>"+vss[oldSent+1].getCharacter().getName()+" : " + vss[oldSent+1].getSentence().getText()+"</br>";
			sentences += "<br>" + vss[oldSent+1].getTrans().getZHCN().getText()+"</br>";
			sentences += "<br>"+vss[oldSent+2].getCharacter().getName()+" : " + vss[oldSent+2].getSentence().getText()+"</br>";
			sentences += "<br>" + vss[oldSent+2].getTrans().getZHCN().getText()+"</br>";
		}else if(oldSent == vss.length-1){
			sentences += "<br>"+vss[oldSent-2].getCharacter().getName()+" : " + vss[oldSent-2].getSentence().getText()+"</br>";
			sentences += "<br>" + vss[oldSent-2].getTrans().getZHCN().getText()+"</br>";
			sentences += "<br>"+vss[oldSent-1].getCharacter().getName()+" : " + vss[oldSent-1].getSentence().getText()+"</br>";
			sentences += "<br>" + vss[oldSent-1].getTrans().getZHCN().getText()+"</br>";
			sentences += "<br>"+vss[oldSent].getCharacter().getName()+" : <font color=\"red\">" + vss[oldSent].getSentence().getText()+"</font></br>";
			sentences += "<br>" + vss[oldSent].getTrans().getZHCN().getText()+"</br>";
		}else{
			sentences += "<br>"+vss[oldSent-1].getCharacter().getName()+" : " + vss[oldSent-1].getSentence().getText()+"</br>";
			sentences += "<br>" + vss[oldSent-1].getTrans().getZHCN().getText()+"</br>";
			sentences += "<br>"+vss[oldSent].getCharacter().getName()+" : <font color=\"red\">" + vss[oldSent].getSentence().getText()+"</font></br>";
			sentences += "<br>" + vss[oldSent].getTrans().getZHCN().getText()+"</br>";
			sentences += "<br>"+vss[oldSent+1].getCharacter().getName()+" : " + vss[oldSent+1].getSentence().getText()+"</br>";
			sentences += "<br>" + vss[oldSent+1].getTrans().getZHCN().getText()+"</br>";
		}
		
		tvSentences.setText(Html.fromHtml(sentences));
		
	}
	
	private void initSentences(){
		if(vss == null){
			return;
		}
		String sentences = "";
		
		sentences += "<br>"+vss[oldSent+1].getCharacter().getName()+" : <font color=\"red\">" + vss[oldSent+1].getSentence().getText()+"</font></br>";
		sentences += "<br>" + vss[oldSent+1].getTrans().getZHCN().getText()+"</br>";
		sentences += "<br>"+vss[oldSent+2].getCharacter().getName()+" : " + vss[oldSent+2].getSentence().getText()+"</br>";
		sentences += "<br>" + vss[oldSent+2].getTrans().getZHCN().getText()+"</br>";
		sentences += "<br>"+vss[oldSent+3].getCharacter().getName()+" : " + vss[oldSent+3].getSentence().getText()+"</br>";
		sentences += "<br>" + vss[oldSent+3].getTrans().getZHCN().getText()+"</br>";
		
		tvSentences.setText(Html.fromHtml(sentences));
	}
	
	public void setVisibility(int visibility){
		if(view !=null)
			view.setVisibility(visibility);
	}
	
	public void pause(){
		media.pause();
	}
	
	public void start() {
		media.start();
	}
}
