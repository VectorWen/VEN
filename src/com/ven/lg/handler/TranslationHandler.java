package com.ven.lg.handler;

import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.TextView;

import com.ven.ui.entity.WebWord;


public class TranslationHandler extends Handler{
	Button btn_query;
	TextView txt_trans;



	public TranslationHandler(Button btn_query, TextView txt_trans) {
		this.btn_query = btn_query;
		this.txt_trans = txt_trans;
	}


	@Override
	public void handleMessage(Message msg) {
System.out.println("TranslationHandler 收到了信息");
		if (msg != null && msg.obj != null) {
			WebWord webWord= (WebWord) msg.obj;
			String trans ="/"+webWord.getPhonetic()+"/\n";
			trans+=webWord.getTranslation();
			txt_trans.setText(trans);
		}else{
			txt_trans.setText("网络错误");
		}
		btn_query.setClickable(true);
	}
}
