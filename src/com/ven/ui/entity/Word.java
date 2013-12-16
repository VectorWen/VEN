package com.ven.ui.entity;

import java.util.Date;

/**
 * ��װWorld ���һ����¼���ֶ���ʲô��ʲô���ͣ�ȫ�����������
 * <p>
 * ����������ֶΣ�����Ҫ�����²�����ɣ� 1. ����private �ֶ� 2. ����get set ���� 3. ��FIELD ����������� 4.
 * ��FIELD_DATA_TYPE �������������Ӧ������ 5. ��parse() ����������� 6. ��get() �����������
 * </P>
 * 
 * @author Vector Huang
 * 
 */
public class Word {

	/**
	 * ��Ӧ��ı��������½����ʱ�����ȥ�õ�
	 */
	public static final String TABLE_NAME = "word";

	/**
	 * ���ȫ���ֶ�
	 */
	public static String FIELD[] = new String[] { "_id", "createDate",
			"addDate", "word", "trans", "phonetic", "associte", "grade",
			"progress", "wordType", "sentence" };
	/**
	 * ��Ӧ���ֶε�����
	 */
	public static String FIELD_DATA_TYPE[] = new String[] {
			"INTEGER PRIMARY KEY AUTOINCREMENT", "varchar", "varchar",
			"nvarchar(45)", "nvarchar(45)", "nvarchar(45)", "nvarchar(245)",
			"int", "int", "int", "nvarchar(245)" };

	/**
	 * ��һ���������ΪWord ʵ������FIELD ˳��һ��
	 * 
	 * @param filedValue
	 *            ��Ӧ��ֵ�������пյġ�
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
	 * �õ���FIELD ��Ӧ���ֶ�ֵ
	 * 
	 * @param field_index
	 *            ��0��ʼ
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
	 * רҵӢ������
	 */
	public static final int WORD_TYPE_specialty = 1;
	/**
	 * Ӣ��������
	 */
	public static final int WORD_TYPE_CET = 2;

	private int _id;

	/**
	 * ����ʱ��
	 */
	private Date createDate = new Date();
	/**
	 * ����ʱ��
	 */
	private Date addDate = new Date();
	/**
	 * ����
	 */
	private String word = " ";
	/**
	 * ���
	 */
	private String sentence = " ";
	/**
	 * ����
	 */
	private String trans = " ";
	/**
	 * ����
	 */
	private String phonetic = " ";
	/**
	 * ����
	 */
	private String associte = " ";
	/**
	 * �ȼ�
	 */
	private int grade = -1;
	/**
	 * ����
	 */
	private int progress = -1;
	/**
	 * ��������
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
