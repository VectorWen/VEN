package com.ven.acty;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.ven.BaseUIActivity;
import com.ven.R;
import com.ven.entity.VideoSentence;
import com.ven.net.DownloadVideo;
import com.ven.net.OnDownloadFileDone;
import com.ven.utils.FileUtils;

public class TestActy extends BaseUIActivity implements OnClickListener {

	private Button btnStart;
	private TextView txtReslut;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		initLayout();
		initData();
	}

	@Override
	public void onClick(View v) {
		
		String json = FileUtils.read("ven/video/1.1.1.json");
		VideoSentence[] vss = VideoSentence.createByJson(json);
		json = "";
		for(VideoSentence vs :vss){
			json += vs.toString();
		}
		txtReslut.setText(json);
	}

	@Override
	protected void initLayout() {
		btnStart = (Button) _getView(R.id.btn_start);
		btnStart.setOnClickListener(this);

		txtReslut = (TextView) _getView(R.id.txt_reslue);

	}

	@Override
	protected void initData() {

	}

}
