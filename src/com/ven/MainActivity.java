package com.ven;

import java.io.IOException;
import java.io.InputStream;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ven.da.WordDao;
import com.ven.lg.controllers.DataMessager;
import com.ven.lg.controllers.Translator;
import com.ven.lg.handler.TranslationHandler;
import com.ven.lg.utils.FileUtils;
import com.ven.media.Media;
import com.ven.ui.entity.WebWord;
import com.ven.ui.entity.Word;
/**
 * ���̵����ҳ�棬�����ؼ�->init �ؼ�->�����ؼ�
 * @author vector
 *
 */
public class MainActivity extends BaseUIActivity implements OnClickListener {

	/**
	 * ҳ��Ͷ˵��ĸ��ؼ�
	 */

	private TextView txt_footer_word, txt_footer_trans, txt_footer_article,
			txt_footer_more;

	/**
	 * ҳ��ͷ���Ŀؼ�
	 */
	private TextView txt_header_title;

	/**
	 * �������ܿ鵼�벿��
	 */
	private RelativeLayout rl_frame_word, rl_frame_translation, rl_frame_more;

	/**
	 * frame_word ҳ��Ŀؼ�
	 */
	private TextView txt_frame_word, txt_frame_phonetic, txt_frame_sentence;
	private Button btn_pro, btn_next;
	private View iv_frame_laba;
	private Media media;

	/**
	 * translation ҳ��ؼ�, ��Ҫ������������handler
	 */
	private TextView txt_translation_trans;
	private EditText et_translation_query;
	private Button btn_translation_query;
	private TranslationHandler translationHandler;
	
	/**
	 * more ҳ��ؼ�
	 */
	private RelativeLayout rl_more_input_output;

	/**
	 * ��ǰ�ɼ���ģ��, һ��ʼӦ�ó�ʼ��
	 */
	private RelativeLayout rl_now_frame;
	private TextView txt_now_footer;
	// һ����ʱ����
	private int id = 1;

	/**
	 * �������ű�Ķ���
	 */
	private Word word;
	WordDao wordDao = new WordDao(this);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acty_ven_main);

		initLayout();
		initData();
	}

	@Override
	protected void initLayout() {
		initMainLayout();
		initFrameWord();
		initFrameTranslation();
		initFrameMore();
	}

	/**
	 * ��ʼ��id �������ֵĿؼ�
	 */
	private void initMainLayout() {
		// footer
		txt_footer_word = (TextView) _getView(R.id.txt_footer_word);
		txt_footer_word.setOnClickListener(this);
		txt_footer_trans = (TextView) _getView(R.id.txt_footer_trans);
		txt_footer_trans.setOnClickListener(this);
		txt_footer_article = (TextView) _getView(R.id.txt_footer_article);
		txt_footer_article.setOnClickListener(this);
		txt_footer_more = (TextView) _getView(R.id.txt_footer_more);
		txt_footer_more.setOnClickListener(this);
		// header
		txt_header_title = (TextView) _getView(R.id.txt_header_title);
		// frame
		rl_frame_word = (RelativeLayout) _getView(R.id.main_content_word);
		rl_frame_translation = (RelativeLayout) _getView(R.id.main_content_translation);
		rl_frame_more = (RelativeLayout) _getView(R.id.main_content_more);
	}
	/**
	 * ��ʼ��frame_more ҳ��ؼ�
	 */
	private void initFrameMore() { 
		rl_more_input_output = (RelativeLayout) _getView(R.id.rl_frame_input_output);
		rl_more_input_output.setOnClickListener(this);
	}
	/**
	 * ��ʼ��frame_word ҳ��ؼ�
	 */
	private void initFrameWord() {
		txt_frame_word = (TextView) _getView(R.id.txt_frame_word_word);
		txt_frame_phonetic = (TextView) _getView(R.id.txt_frame_word_phonetic);
		Typeface mFace = Typeface.createFromAsset(getAssets(),
				"font/segoeui.ttf");
		txt_frame_phonetic.setTypeface(mFace);
		txt_frame_sentence = (TextView) _getView(R.id.txt_frame_word_sentence);
		btn_pro = (Button) _getView(R.id.btn_frame_word_pro);
		btn_pro.setOnClickListener(this);
		btn_next = (Button) _getView(R.id.btn_frame_word_next);
		btn_next.setOnClickListener(this);
		
		iv_frame_laba = _getView(R.id.iv_frame_word_laba);
		iv_frame_laba.setOnClickListener(this);
		
		media = new Media(this);
	}

	/**
	 * ��ʼ��frame_translation ҳ��ؼ�
	 */
	private void initFrameTranslation() {
		txt_translation_trans = (TextView) _getView(R.id.txt_frame_translation_trans);
		Typeface mFace = Typeface.createFromAsset(getAssets(),
				"font/segoeui.ttf");
		txt_translation_trans.setTypeface(mFace);
		et_translation_query = (EditText) _getView(R.id.et_frame_translation_query);
		btn_translation_query = (Button) _getView(R.id.btn_frame_translation_query);
		btn_translation_query.setOnClickListener(this);
		translationHandler = new TranslationHandler(btn_translation_query,
				txt_translation_trans);
	}

	@Override
	protected void initData() {
		/**
		 * һ��ʼ��ģ��
		 */
		txt_now_footer = txt_footer_word;
		rl_now_frame = rl_frame_word;
		setData(id++);
	}

	/**
	 * �ı�ɼ�ģ��footer
	 * 
	 * @param now_footer
	 */
	private void changeFooter(TextView now_footer) {
		if (txt_now_footer == now_footer) {
			return;
		}
		txt_now_footer.setTextColor(Color.WHITE);
		txt_now_footer = now_footer;
		txt_now_footer.setTextColor(Color.RED);
	}

	/**
	 * �ı�ɼ�ģ��
	 * 
	 * @param now_footer
	 */
	private void changeFrame(RelativeLayout now_rl) {
		if (rl_now_frame == now_rl) {
			return;
		}
		rl_now_frame.setVisibility(View.GONE);
		rl_now_frame = now_rl;
		rl_now_frame.setVisibility(View.VISIBLE);
	}

	private void setData(int id) {
		word = wordDao.query(id);
		if (word == null) {
			return;
		}
		txt_frame_word.setText(word.getWord());

		txt_frame_sentence.setText(word.getTrans());
		txt_frame_phonetic.setText(word.getPhonetic());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_frame_word_next:
			id++;
			setData(id);
			break;
			
		case R.id.btn_frame_word_pro:
			id--;
			setData(id);
			break;
			
		case R.id.rl_frame_input_output:
			inputOutput();
			break;
			
		case R.id.iv_frame_word_laba:
			final String wordname = word.getWord();
			playWord(wordname);
			break;
			
		case R.id.txt_footer_trans:
			changeFooter(txt_footer_trans);
			changeFrame(rl_frame_translation);
			break;
			
		case R.id.txt_footer_more:
			changeFooter(txt_footer_more);
			changeFrame(rl_frame_more);
			break;
			
		case R.id.txt_footer_word:
			changeFooter(txt_footer_word);
			changeFrame(rl_frame_word);
			break;
			
		case R.id.btn_frame_translation_query:
			String query = et_translation_query.getText().toString();
			if (!"".equalsIgnoreCase(query)) {
				btn_translation_query.setClickable(false);
				txt_translation_trans.setText("��ѯ��...");
				new Thread(new Runnable() {

					@Override
					public void run() {
						trans();
					}
				}).start();
			}
			break;
			
		default:
			break;
		}
	}
	
	/**
	 * ���ŵ���
	 * @param word
	 */
	private void playWord(final String word) {
		toast("׼������");
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				//Toast.makeText(MainActivity.this, media.play(word), Toast.LENGTH_SHORT).show();
				System.out.println(media.play(word));
			}
		}).start();
	}

	/**
	 * ���뵼��
	 */
	private void inputOutput() {
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		builder.setTitle("��ʾ");
		/**
		 * ���Ӱ�ť��ֱ���ɻص�������
		 */
		builder.setPositiveButton("����", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				toast("���ڵ��룡");
				rl_more_input_output.setClickable(false);
				/**
				 * �첽����
				 */
				
				new AsyncTask<Void, Void, Void>() {
					protected Void doInBackground(Void... params) {
						try {
							InputStream in = getResources().getAssets().open("word_one.xml");
							DataMessager data = new DataMessager(MainActivity.this);
							data.XML2Db(in);
						} catch (IOException e) {
							e.printStackTrace();
						}
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						toast("����ɹ���");
						rl_more_input_output.setClickable(true);
					}

				}.execute();
			}
		});
		builder.setNegativeButton("����", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				toast("ok");
			}
		});
		builder.setNeutralButton("ȡ��", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				toast("ok");
			}
		});
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	private void trans() {
		String query = et_translation_query.getText().toString();
		WebWord webWord = null;
		try {
			webWord = new Translator().translation(query);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Message msg = translationHandler.obtainMessage();
		msg.obj = webWord;
		translationHandler.sendMessage(msg);
	}
}