package com.ven.ui.entity;

import java.util.Date;

/**
 * 封装World 表的一个记录。字段有什么，什么类型，全部在这里决定
 * <p>
 * 如果想增加字段，你需要按如下步骤完成： 1. 增加private 字段 2. 设置get set 方法 3. 在FIELD 数组最后增加 4.
 * 在FIELD_DATA_TYPE 数组最后增加相应的类型 5. 在parse() 方法最后增加 6. 在get() 方法最后增加
 * </P>
 * 
 * @author Vector Huang
 * 
 */
public class Word {

	/**
	 * 对应表的表名，在新建表的时候会拿去用的
	 */
	public static final String TABLE_NAME = "word";

	/**
	 * 表的全部字段
	 */
	public static String FIELD[] = new String[] { "_id", "createDate",
			"addDate", "word", "trans", "phonetic", "associte", "grade",
			"progress", "wordType", "sentence" };
	/**
	 * 对应的字段的类型
	 */
	public static String FIELD_DATA_TYPE[] = new String[] {
			"INTEGER PRIMARY KEY AUTOINCREMENT", "varchar", "varchar",
			"nvarchar(45)", "nvarchar(45)", "nvarchar(45)", "nvarchar(245)",
			"int", "int", "int", "nvarchar(245)" };

	/**
	 * 用一个数组解析为Word 实例，和FIELD 顺序一样
	 * 
	 * @param filedValue
	 *            对应的值，不能有空的。
	 * @return
	 */
	public static Word parse(String filedValue[]) {
		Word word = new Word();
		if (filedValue[0] != null)
			word.set_id(Integer.parseInt(filedValue[0]));
//		if (filedValue[1] != null)
//			word.setCreateDate(new Date(filedValue[1]));
//		if (filedValue[2] != null)
//			word.setAddDate(new Date(filedValue[2]));
		if (filedValue[3] != null)
			word.setWord(filedValue[3]);
		if (filedValue[4] != null)
			word.setTrans(filedValue[4]);
		if (filedValue[5] != null)
			word.setPhonetic(filedValue[5]);
		if (filedValue[6] != null)
			word.setAssocite(filedValue[6]);

		if (filedValue[7] != null)
			word.setGrade(Integer.parseInt(filedValue[7]));
		if (filedValue[8] != null)
			word.setProgress(Integer.parseInt(filedValue[8]));
		if (filedValue[9] != null)
			word.setWordType(Integer.parseInt(filedValue[9]));
		if (filedValue[10] != null)
			word.setSentence(filedValue[10]);
		return word;
	}

	/**
	 * 拿到和FIELD 对应的字段值
	 * 
	 * @param field_index
	 *            从0开始
	 * @return
	 */
	public Object get(int field_index) {

		switch (field_index) {
		case 0:
			return get_id();
		case 1:
			return getCreateDate();
		case 2:
			return getAddDate();
		case 3:
			return getWord();
		case 4:
			return getTrans();
		case 5:
			return getPhonetic();
		case 6:
			return getAssocite();
		case 7:
			return getGrade();
		case 8:
			return getProgress();
		case 9:
			return getWordType();
		case 10:
			return getSentence();
		default:
			return "";
		}
	}

	/**
	 * 专业英语类型
	 */
	public static final int WORD_TYPE_specialty = 1;
	/**
	 * 英语四六级
	 */
	public static final int WORD_TYPE_CET = 2;

	private int _id;

	/**
	 * 创建时间
	 */
	private Date createDate = new Date();
	/**
	 * 增加时间
	 */
	private Date addDate = new Date();
	/**
	 * 单词
	 */
	private String word = " ";
	/**
	 * 造句
	 */
	private String sentence = " ";
	/**
	 * 翻译
	 */
	private String trans = " ";
	/**
	 * 音标
	 */
	private String phonetic = " ";
	/**
	 * 联想
	 */
	private String associte = " ";
	/**
	 * 等级
	 */
	private int grade = -1;
	/**
	 * 进步
	 */
	private int progress = -1;
	/**
	 * 单词类型
	 */
	private int wordType = Word.WORD_TYPE_specialty;

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	public String getTrans() {
		return trans;
	}

	public void setTrans(String trans) {
		this.trans = trans;
	}

	public String getPhonetic() {
		return phonetic;
	}

	public void setPhonetic(String phonetic) {
		this.phonetic = phonetic;
	}

	public String getAssocite() {
		return associte;
	}

	public void setAssocite(String associte) {
		this.associte = associte;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public int getWordType() {
		return wordType;
	}

	public void setWordType(int wordType) {
		this.wordType = wordType;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	@Override
	public String toString() {
		return "Word [_id=" + _id + ", createDate=" + createDate + ", addDate="
				+ addDate + ", word=" + word + ", sentence=" + sentence
				+ ", trans=" + trans + ", phonetic=" + phonetic + ", associte="
				+ associte + ", grade=" + grade + ", progress=" + progress
				+ ", wordType=" + wordType + "]";
	}

}
