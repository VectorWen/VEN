package com.ven.da;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ven.lg.controllers.DBOpenHelper;
import com.ven.ui.entity.Word;

/**
 * ��Ӧ���ݿ� Word table ����װ��ȫ����������
 * 
 * @author Vector Huang
 * 
 */
public class WordDao {
	SQLiteDatabase db = null;
	DBOpenHelper dbOpenHelper = null;
	private Context context;

	public WordDao(Context context) {
		this.context = context;
	}

	/**
	 * ���������¼����һ��һ���Ĳ����, ����еĲ����롣
	 * 
	 * @param words
	 */
	public void insert(List<Word> words) {
		if (words == null || words.size() == 0) {
			return;
		}

		openDatabase();
		for (int j = 0; j < words.size(); j++) {
			Word word = words.get(j);
			int id = isHas(db, word.getWord());
			if (id == -1) {
				String field = "";
				ContentValues values = new ContentValues();
				int i = 0;
				for (i = 1; i < Word.FIELD.length - 1; i++) {
					field += Word.FIELD[i] + ",";
					values.put(Word.FIELD[i], word.get(i).toString());
				}
				field += Word.FIELD[i];
				values.put(Word.FIELD[i], word.get(i).toString());

				id = (int) db.insert(Word.TABLE_NAME, field, values);
			}
		}
		closeDatabase();
	}

	/**
	 * ��������¼,�������ͬ�ĵ���
	 * 
	 * @param words
	 *            Ҫ����ļ�¼�б�
	 */
	// public void insert(List<Word> words){
	// if(words == null||words.size()==0){
	// return;
	// }
	//
	// openDatabase();
	//
	//
	// //���Ϊ�����֣�1���ֶ� �� 2���ж���������select ?,? union all select ?,?
	// //3, ֵ����Ӧ�ʺ��ϵ�ֵ
	//
	//
	// //1������Ҫ������ֶ� a,b,c ��ʽ
	// String field = "";
	// String pro = ""; //�����ж��ٸ��ʺ� ?,?,? ��ʽ
	// int i=0;
	// //��Ҫid ��
	// for(i=1;i<Word.FIELD.length-1;i++){
	// field+=Word.FIELD[i]+",";
	// pro +=" '?',";
	// }
	// field+=Word.FIELD[i];
	// pro+="'?' ";
	//
	// //2, �����ж���������
	// String count = " select "+pro; //�����ж��������
	//
	// for(i=1;i<words.size();i++){
	// count+=" union all "+" select "+pro;
	// }
	//
	// //3, ��ţ� �õ�ֵ Object
	// Object o[] = new Object[(Word.FIELD.length-1)*words.size()];
	// for(i = 0; i<words.size();i++){
	// Word word = words.get(i);
	// for(int j=1;j<Word.FIELD.length;j++){
	// o[(j-1)+i*10] = word.get(j);
	// }
	// }
	// for(int p=0;p<o.length;p++)
	// System.out.println("WordDao insert(list<word>) -- >>object���� ��"+o[p]);
	// db.execSQL("inset into "+Word.TABLE_NAME+"("+field+")"+count,o);
	// //����Ҫ���������
	//
	// closeDatabase();
	// }
	//
	/**
	 * ���һ������
	 * 
	 * @param word
	 *            Ҫ��ӵĵ���
	 * @return �������˻���д��ɹ� - ���ز�����id �� �� д��ʧ�� ���� -1
	 */
	public int insert(Word word) {
		openDatabase();
		// û��������ʲ�д
		int id = isHas(db, word.getWord());
		if (id == -1) {
			String field = "";
			ContentValues values = new ContentValues();
			int i = 0;
			for (i = 1; i < Word.FIELD.length - 1; i++) {
				field += Word.FIELD[i] + ",";
				values.put(Word.FIELD[i], word.get(i).toString());
			}
			field += Word.FIELD[i];
			values.put(Word.FIELD[i], word.get(i).toString());

			id = (int) db.insert(Word.TABLE_NAME, field, values);
		}
		closeDatabase();
		return id;
	}

	/**
	 * ��ѯ Word���һ������
	 * 
	 * @param id
	 *            Ҫ��ѯ���ʵ�id
	 * @return �� ����ʵ����û ����null
	 */
	public Word query(int id) {
		openDatabase();
		Word word = null;

		String sql = "select * FROM " + Word.TABLE_NAME + " where _id=?";
		Cursor cursor = db.rawQuery(sql, new String[] { id + "" });
		System.out.println("WordDao gerWord() -- >> �������:  "
				+ cursor.getCount());
		if (cursor.getCount() != 0) {
			String fieldValue[] = new String[Word.FIELD.length];
			cursor.moveToFirst();
			for (int i = 0; i < Word.FIELD.length; i++) {
				fieldValue[i] = cursor.getString(cursor
						.getColumnIndex(Word.FIELD[i]));
System.out.println("WordDao query() -- >>��ѯ�Ľ��"
						+ fieldValue[i]);
			}
			word = Word.parse(fieldValue);
		}
		closeDatabase();
		return word;
	}
	/**
	 * ͨ��where ���Һ��ʵ�list
	 * @param where 
	 * @return ����ҵ���װ��list ���أ�����list ����Ϊ��
	 */
	public List<Word> rawQuery(String where) {
		openDatabase();
		
		List<Word> words = new ArrayList<Word>();
		
		String sql;
		
		if(where == null||"".equalsIgnoreCase(where.trim())){
			sql = "select * FROM " + Word.TABLE_NAME;
		}else{
			sql = "select * FROM " + Word.TABLE_NAME + " where "+where;
		}
		Cursor cursor = db.rawQuery(sql, null);
		
		String[] fieldValue = new String[Word.FIELD.length];
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
			for (int i = 0; i < Word.FIELD.length; i++) {
				fieldValue [i] = cursor.getString(cursor
						.getColumnIndex(Word.FIELD[i]));
System.out.println("WordDao rawQuery() -- >>��ѯ�Ľ��"
						+ fieldValue[i]);
			}
			words.add(Word.parse(fieldValue));
		}
		
		return words;
	}

	/**
	 * ��ѯһ�����ʼ�¼
	 * @param word �õ���ƥ��
	 * @return �ҵ� ����ʵ��  ���� null
	 */
	public Word query(String wordStr) {
		openDatabase();
		Word word = null;

		String sql = "select * FROM " + Word.TABLE_NAME + " where _id=?";
		Cursor cursor = db.rawQuery(sql, new String[] { wordStr });
		System.out.println("WordDao gerWord() -- >> �������:  "
				+ cursor.getCount());
		if (cursor.getCount() != 0) {
			String fieldValue[] = new String[Word.FIELD.length];
			cursor.moveToFirst();
			for (int i = 0; i < Word.FIELD.length; i++) {
				fieldValue[i] = cursor.getString(cursor
						.getColumnIndex(Word.FIELD[i]));
				System.out.println("WordDao gerWord() -- >>��ѯ�Ľ��"
						+ fieldValue[i]);
			}
			word = Word.parse(fieldValue);
		}
		closeDatabase();
		return word;
	}

	/**
	 * �鿴������û���������
	 * 
	 * @param word
	 *            Ҫ��ѯ�ĵ���
	 * @return �� ����id û ����-1
	 */
	private int isHas(SQLiteDatabase db, String word) {
		String sql = "select * FROM " + Word.TABLE_NAME + " where word=?";
		Cursor cursor = db.rawQuery(sql, new String[] { word });
		if (cursor.getCount() == 0) {
			return -1;
		}
		cursor.moveToFirst();
		return cursor.getInt(cursor.getColumnIndex("_id"));
	}

	/**
	 * �����ݿ�
	 */
	private void openDatabase() {
		if (dbOpenHelper == null) {
			dbOpenHelper = new DBOpenHelper(context);
			db = dbOpenHelper.getWritableDatabase();
		}

	}

	/**
	 * �ر����ݿ�
	 */
	private void closeDatabase() {
		if (dbOpenHelper != null) {
			dbOpenHelper.close();
			dbOpenHelper = null;
			db = null;
		}
	}

	/**
	 * ����word ��ļ�¼��Ŀ
	 * 
	 * @return
	 */
	public int getCount() {
		openDatabase();
		Cursor cursor = db.rawQuery("select count(*) as cut from "
				+ Word.TABLE_NAME, null);
		cursor.moveToFirst();
		return cursor.getInt(0);
	}
}
