package com.ven.entity;

import com.google.gson.Gson;


/**
 * 教程视频的字幕句子
 * @author vector
 *
 */
public class VideoSentence {
	
	public static VideoSentence[] createByJson(String json) {
		return VideoInfo.createByJson(json).getSentences();
	}
	
	/**
	 * 对应视频的第几秒读到这个句子
	 */
	private int StartTimeInSecond;
	/**
	 * 英文
	 */
	private English Sentence;
	/**
	 * 名字
	 */
	private Name Character;
	/**
	 * 中文翻译
	 */
	private Trans Trans;
	public class English{
		private String Text;

		public String getText() {
			return Text;
		}

		public void setText(String text) {
			Text = text;
		}

		@Override
		public String toString() {
			return "English [Text=" + Text + "]";
		}

	}
	public class Name{
		private String Name;

		public String getName() {
			return Name;
		}

		public void setName(String name) {
			Name = name;
		}

		@Override
		public String toString() {
			return "Name [Name=" + Name + "]";
		}

	}
	public class Trans{
		
		private ZHCN ZHCN;
		public class ZHCN{

			private String Text;

			public String getText() {
				return Text;
			}

			public void setText(String text) {
				Text = text;
			}

			@Override
			public String toString() {
				return "ZHCN [Text=" + Text + "]";
			}
		}
		public ZHCN getZHCN() {
			return ZHCN;
		}
		public void setZHCN(ZHCN zHCN) {
			ZHCN = zHCN;
		}
		@Override
		public String toString() {
			return "Trans [ZHCN=" + ZHCN + "]";
		}
		

	}
	public int getStartTimeInSecond() {
		return StartTimeInSecond;
	}
	public void setStartTimeInSecond(int startTimeInSecond) {
		StartTimeInSecond = startTimeInSecond;
	}
	public English getSentence() {
		return Sentence;
	}
	public void setSentence(English sentence) {
		Sentence = sentence;
	}
	public Name getCharacter() {
		return Character;
	}
	public void setCharacter(Name character) {
		Character = character;
	}
	public Trans getTrans() {
		return Trans;
	}
	public void setTrans(Trans trans) {
		Trans = trans;
	}
	@Override
	public String toString() {
		return "VideoSentence [StartTimeInSecond=" + StartTimeInSecond
				+ ", Sentence=" + Sentence + ", Character=" + Character
				+ ", Trans=" + Trans + "]";
	}
	
}


