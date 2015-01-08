package com.ven.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ven.MainActivity;
import com.ven.R;
import com.ven.entity.Word;
import com.ven.media.Media;
import com.ven.save.NetHelper;
import com.ven.save.NetHelper.OnDownloadPronunciationDone;
import com.ven.utils.FileUtils;

/**
 * 单词本列表适配器
 * 
 * @author vector
 * 
 */
public class WordListAdapter extends BaseAdapter {
	/**
	 * 要适配的数据
	 */
	private ArrayList<Word> alWordListData;
	private Context context;
	/**
	 * 用来反射布局文件的
	 */
	private LayoutInflater inflater = null;

	public WordListAdapter(ArrayList<Word> alWordListData, Context context) {
		this.alWordListData = alWordListData;
		this.context = context;
		inflater = LayoutInflater.from(this.context);
	}

	@Override
	public int getCount() {
		return alWordListData.size();
	}

	@Override
	public Object getItem(int position) {
		return alWordListData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(R.layout.item_lv_frame_word,
				null);
		Word word = alWordListData.get(position);
		TextView query= (TextView) convertView.findViewById(R.id.item_lv_frame_word_tv_word);
		query.setText(word.getQuery());
		
		TextView phonetic = (TextView) convertView.findViewById(R.id.item_lv_frame_word_tv_phonetic);
		phonetic.setText("/ "+word.getBasic().getPhonetic()+" /");
		
		TextView tran = (TextView) convertView.findViewById(R.id.item_lv_frame_word_tv_translation);
		tran.setText(word.getTranslation()[0]+"\n"+word.getBasic().getExplains()[0]);
		
		ImageView art = (ImageView) convertView.findViewById(R.id.item_lv_frame_word_iv_art);
		art.setOnClickListener(new ArtonClick(word.getQuery()));
		
		
		return convertView;
	}
	
	class ArtonClick implements OnClickListener{
		String tranQuery;
		
		
		public ArtonClick(String tranQuery) {
			this.tranQuery = tranQuery;
		}

		@Override
		public void onClick(View v) {
			String path = "ven/" + tranQuery + ".mp3";
			if (!FileUtils.existes(path)) {
				NetHelper net = new NetHelper();
				net.downloadPronunciation(tranQuery,
						new OnDownloadPronunciationDone() {

							@Override
							public void onFinish(boolean isSuccess) {
								// TODO Auto-generated method stub
								if (isSuccess) {
									new Media(context).play("ven/"
											+ tranQuery + ".mp3");
								}
							}
						});
			} else {
				new Media(context).play(path);
			}
		}
		
	}
}
