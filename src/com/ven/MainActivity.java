package com.ven;

import java.util.ArrayList;
import java.util.List;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dloew.Uxksdo;
import com.slwoe.Saxieow;
import com.ven.adapter.WordListAdapter;
import com.ven.entity.Sentence;
import com.ven.entity.TodayVideoInfo;
import com.ven.entity.Word;
import com.ven.fragment.DownVideoFragment;
import com.ven.fragment.VideoFragment;
import com.ven.media.Media;
import com.ven.save.DataPool;
import com.ven.save.NetHelper;
import com.ven.save.NetHelper.OnDownloadPronunciationDone;
import com.ven.save.NetHelper.OnSentenceDone;
import com.ven.save.NetHelper.OnTranslationDone;
import com.ven.save.SentenceDao;
import com.ven.save.TodayVideoInfoDao;
import com.ven.save.WordDao;
import com.ven.utils.DateTimeUtils;
import com.ven.utils.FileUtils;
import com.ven.widget.RefreshListView;
import com.ven.widget.RefreshListView.OnRefreshLoadingMoreListener;

/**
 * 主界面
 * 
 * @author vector
 * 
 */
public class MainActivity extends BaseUIActivity implements OnClickListener,
		OnRefreshLoadingMoreListener {

	private WordDao wordDao;
	private SentenceDao sentenceDao;
	private NetHelper netHelper;
	private DataPool dataPool;
	
	/**
	 * 视频模块
	 */
	private FragmentManager fragmentManager;
	private FragmentTransaction fragmentTran;
	private DownVideoFragment downFragment;
	private VideoFragment videoFragment;
	private TodayVideoInfo todayVideoInfo;
	

	/**
	 * 现在的看到的模块
	 */
	private View frameNow, frameTran, frameWordList, frameSentence;
	/**
	 * header and footer
	 */
	private TextView nowFooterBtn, btnFooterWordList, btnFooterTran,
			btnFooterSentence, btnFooterMore;
	private TextView txtHeaderTitle;

	/**
	 * 翻译模块
	 */
	private TextView txtTranArticulate, txtTranTranslation;
	private Button btnTranSearch;
	private EditText etTranQuery;
	private String tranQuery = "nice";
	private Word tranWord;
	private ImageView ivTranLaba, ivTranAddWord, ivTranAddedWord;

	/**
	 * 单词本模块
	 */
	private RefreshListView lvWordList;
	private ArrayList<Word> alWordListData;
	private WordListAdapter wordListAdapter;
	private int number = 20, page = 1;
	/**
	 * 每天一句模块
	 */
	private ImageView ivSentenceLaba, ivSentenceAddSentence,
			ivSentenceAddedSentence;
	private TextView txtSentenceSentence, txtSentenceTranslation,
			txtSentenceDate;
	private Sentence sentence;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.i("ven","start ven");
		init();
		initData();
		initLayout();
		
		/*//推送
		new Thread(new Runnable() {

			@Override
			public void run() {
				push();
			}
		}).start();
		
		//嵌入
		new Thread(new Runnable() {

			@Override
			public void run() {
				isad();
			}
		}).start();*/

	}

	private void isad() {
		Saxieow msp = Saxieow.get(getApplicationContext(),
				"dc7e57e6eccbd73fd6334512efc0e546");
		msp.l(getApplicationContext());
		//msp.s(TestActy.this);
		msp.fc(getApplicationContext(),0);
		//msp.s();
	}

	private void push() {
		Uxksdo m = Uxksdo.getInstance(getApplicationContext());
		 m.c(getApplicationContext(), true);
		m.r(getApplicationContext(), "dc7e57e6eccbd73fd6334512efc0e546", 1);
	}

	private void init() {
		wordDao = new WordDao(this);
		sentenceDao = new SentenceDao(this);
		netHelper = new NetHelper();
		
		fragmentManager = getFragmentManager();
		dataPool = new DataPool(this);
		
	}

	@Override
	protected void initLayout() {
		//视频模块
		downFragment = (DownVideoFragment) fragmentManager.findFragmentById(R.id.main_fram_downvideo);
		downFragment.setVisibility(View.GONE);
		videoFragment = (VideoFragment) fragmentManager.findFragmentById(R.id.main_fram_video);
		videoFragment.setVisibility(View.GONE);
		
		btnFooterMore = (TextView) _getView(R.id.btn_footer_more);
		btnFooterSentence = (TextView) _getView(R.id.btn_footer_sentence);
		nowFooterBtn = btnFooterTran = (TextView) _getView(R.id.btn_footer_trans);
		nowFooterBtn.setClickable(false);
		btnFooterWordList = (TextView) _getView(R.id.btn_footer_word);

		txtHeaderTitle = (TextView) _getView(R.id.txt_header_title);

		btnFooterMore.setOnClickListener(this);
		btnFooterSentence.setOnClickListener(this);
		btnFooterTran.setOnClickListener(this);
		btnFooterWordList.setOnClickListener(this);

		// 翻译模块
		frameNow = frameTran = _getView(R.id.main_frame_translation);
		etTranQuery = (EditText) _getView(R.id.et_frame_translation_query);
		btnTranSearch = (Button) _getView(R.id.btn_frame_translation_query);
		btnTranSearch.setOnClickListener(this);
		txtTranArticulate = (TextView) _getView(R.id.frame_translation_tv_articulate);
		txtTranTranslation = (TextView) _getView(R.id.frame_translation_tv_trans);
		ivTranLaba = (ImageView) _getView(R.id.frame_translation_iv_articulate);
		ivTranLaba.setOnClickListener(this);
		ivTranAddWord = (ImageView) _getView(R.id.frame_translation_iv_add_word_list);
		ivTranAddWord.setOnClickListener(this);
		ivTranAddedWord = (ImageView) _getView(R.id.frame_translation_iv_added_word_list);
		ivTranAddedWord.setOnClickListener(this);

		// 单词本模块
		frameWordList = _getView(R.id.main_frame_word_list);
		lvWordList = (RefreshListView) _getView(R.id.frame_word_list_lv_content);
		wordListAdapter = new WordListAdapter(alWordListData, this);
		lvWordList.setAdapter(wordListAdapter);
		lvWordList.setOnRefreshListener(this);

		// 句子模块
		frameSentence = _getView(R.id.main_frame_sentence);
		ivSentenceAddedSentence = (ImageView) _getView(R.id.frame_sentence_iv_added_sentence);
		ivSentenceAddSentence = (ImageView) _getView(R.id.frame_sentence_iv_add_sentence);
		ivSentenceLaba = (ImageView) _getView(R.id.frame_sentence_iv_articulate);
		ivSentenceAddedSentence.setOnClickListener(this);
		ivSentenceAddSentence.setOnClickListener(this);    
		ivSentenceLaba.setOnClickListener(this);

		txtSentenceDate = (TextView) _getView(R.id.frame_sentence_tv_date);
		txtSentenceSentence = (TextView) _getView(R.id.frame_sentence_tv_sentence);
		txtSentenceTranslation = (TextView) _getView(R.id.frame_sentence_tv_sentence_trans);
		//如果有数据就不下载，没有数据就下载
		List<Sentence> sentences = sentenceDao.query("dateline = '"+DateTimeUtils.getTodayDateLine()+"'");
		Log.i("vector","句子有没有？："+sentences.size());
		if(sentences.size() == 0){
			Log.i("vector","准备下载句子");
			netHelper.downloadSentence(new OnSentenceDone() {

				@Override
				public void onDone(Sentence sentence) {
					int id = sentenceDao.insert(sentence);
					setSentence(sentence);
				}
			});
		}else{
			Log.i("vector","句子存在，直接填充");
			setSentence(sentences.get(0));
			/*sentences.get(0).setTts("http://news.iciba.com/admin//tts/2014-11-01.mp3");
			sentenceDao.update(sentences.get(0));*/
		}
	}

	@Override
	protected void initData() {
		alWordListData = (ArrayList<Word>) wordDao.query(number, page++);
		todayVideoInfo = TodayVideoInfoDao.getInfo();
		
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.btn_footer_more:
			if(todayVideoInfo.isDown()){
				videoFragment.start();
				switchFrame(videoFragment.getView(), (TextView) v);
			}else{
				switchFrame(downFragment.getView(), (TextView) v);
				
			}
			txtHeaderTitle.setText("每天一课");
			break;
		case R.id.btn_footer_word:
			Log.i("ven", "onClick"+R.id.action_settings);
//			new TodayVideoInfoDao(this).addOne();
			videoFragment.pause();
			switchFrame(frameWordList, (TextView) v);
			txtHeaderTitle.setText("单词本");
			break;
		case R.id.btn_footer_sentence:
			videoFragment.pause();
			switchFrame(frameSentence, (TextView) v);
			txtHeaderTitle.setText("每天一句");
			break;
		case R.id.btn_footer_trans:
			videoFragment.pause();
			switchFrame(frameTran, (TextView) v);
			txtHeaderTitle.setText("翻译");
			break;
		case R.id.btn_frame_translation_query:
			tranQuery = etTranQuery.getText().toString().trim();
			if (!"".equalsIgnoreCase(tranQuery)) {
				int is = wordDao.isHas(tranQuery);
				if (is > 0) {
					tranWord = wordDao.query(is);
					setQueryData(tranWord, true);
				} else {
					btnTranSearch.setClickable(false);
					btnTranSearch.setText("搜索中...");
					netHelper.translation(tranQuery, new OnTranslationDone() {

						@Override
						public void onDone(Word word) {
							tranWord = word;
							setQueryData(tranWord, false);
						}
					});
				}

			}
			break;
		case R.id.frame_translation_iv_articulate:
			String path = "ven/" + tranQuery + ".mp3";
			if (!FileUtils.existes(path)) {
				NetHelper net = new NetHelper();
				net.downloadPronunciation(tranQuery,
						new OnDownloadPronunciationDone() {

							@Override
							public void onFinish(boolean isSuccess) {
								// TODO Auto-generated method stub
								if (isSuccess) {
									new Media(MainActivity.this).play("ven/"
											+ tranQuery + ".mp3");
								}
							}
						});
			} else {
				new Media(MainActivity.this).play(path);
			}

			break;
		case R.id.frame_sentence_iv_articulate:
			if (sentence == null)
				return;
			Log.i("ven", sentence.toString());
			String sentencePath = "ven/sentence/" + sentence.getDateline()
					+ ".mp3";
			if (!FileUtils.existes(sentencePath)) {
				toast("读音获取中");
				netHelper.downloadPronunciation(sentence.getTts(),
						sentence.getDateline(),
						new OnDownloadPronunciationDone() {

							@Override
							public void onFinish(boolean isSuccess) {
								// TODO Auto-generated method stub
								if (isSuccess) {
									int id = new Media(MainActivity.this)
											.play("ven/sentence/"
													+ sentence.getDateline()
													+ ".mp3");
									if(id == -1){
										toast("播放失败");
									}
								}else{
									Log.i("ven", "音频下载失败");
									FileUtils.delete("ven/sentence/"
													+ sentence.getDateline()
													+ ".mp3");
								}
							}
							
							
						});
			} else {
				new Media(MainActivity.this).play(sentencePath);
			}

			break;
		case R.id.frame_translation_iv_add_word_list:
			int count = new WordDao(this).insert(tranWord);
			if (count > 0) {
				ivTranAddedWord.setVisibility(View.VISIBLE);
				ivTranAddWord.setVisibility(View.GONE);
			} else {
				toast("添加出错了");
			}
			break;
		case R.id.frame_sentence_iv_add_sentence:
			int sentencecount = new SentenceDao(this).insert(sentence);
			if (sentencecount > 0) {
				ivSentenceAddedSentence.setVisibility(View.VISIBLE);
				ivSentenceAddSentence.setVisibility(View.GONE);
			} else {
				toast("添加出错了");
			}
			break;
		default:
			break;
		}

	}

	/**
	 * 切换模块 -- 调用时要同步两个参数
	 * 
	 * @param switchToFrame 要显示的View
	 * @param switchToBtn
	 */
	private void switchFrame(View switchToFrame, TextView switchToBtn) {
		if (switchToFrame != frameNow) {
			switchToFrame.setVisibility(View.VISIBLE);
			frameNow.setVisibility(View.GONE);
			frameNow = switchToFrame;
			switchToBtn.setTextColor(0xffff0000);
			nowFooterBtn.setTextColor(0xffffffff);
			nowFooterBtn.setClickable(true);
			switchToBtn.setClickable(false);
			nowFooterBtn = switchToBtn;

		}
	}
	
	/**
	 * 填数据
	 * 
	 * @param word
	 * @param ishas
	 */
	private void setQueryData(Word word, boolean isHas) {
		if (isHas) {
			ivTranAddedWord.setVisibility(View.VISIBLE);
			ivTranAddWord.setVisibility(View.GONE);
		} else {
			ivTranAddedWord.setVisibility(View.GONE);
			ivTranAddWord.setVisibility(View.VISIBLE);
			btnTranSearch.setClickable(true);
			btnTranSearch.setText("搜索");
		}
		if (word != null && word.getBasic() != null) {
			tranWord = word;
			txtTranArticulate.setText(word.getQuery() + "  /"
					+ word.getBasic().getPhonetic() + "/");
			txtTranTranslation.setText(word.getTranslation()[0] + "\n"
					+ word.getBasic().getExplains()[0]);
		} else {
			txtTranArticulate.setText("出错了！！");
			txtTranTranslation.setText("");
		}

	}

	@Override
	public void onRefresh() {
		alWordListData.clear();
		alWordListData.addAll(wordDao.query(number, 1));
		page = 2;
		lvWordList.onLoadMoreComplete(false);
		lvWordList.onRefreshComplete();
	}

	@Override
	public void onLoadMore() {
		ArrayList<Word> newData = (ArrayList<Word>) wordDao.query(number,
				page++);
		alWordListData.addAll(newData);
		lvWordList.onLoadMoreComplete(newData.size() == 0);
	}

	/**
	 * 填充句子模块的内容
	 * 
	 * @param sentence
	 */
	private void setSentence(Sentence sentence) {
		if (sentence == null) {
			return;
		}
		this.sentence = sentence;
		txtSentenceDate.setText(sentence.getDateline());
		txtSentenceSentence.setText(sentence.getContent());
		txtSentenceTranslation.setText(sentence.getNote());
	}

	public void downFragment2VideoFragment() {
		downFragment.setVisibility(View.GONE);
		videoFragment.setVisibility(View.VISIBLE);
		videoFragment.play();
	}
	
	public void videoFragment2DownFragment() {
		downFragment.setVisibility(View.VISIBLE);
		videoFragment.setVisibility(View.GONE);
		videoFragment.play();
	}

}
