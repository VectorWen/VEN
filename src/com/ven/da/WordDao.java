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
 * 对应数据库 Word table ，封装了全部处理方法。
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
	 * 插入多条记录，是一条一条的插入的, 如果有的不插入。
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
	 * 插入多个记录,不检查相同的单词
	 * 
	 * @param words
	 *            要插入的记录列表
	 */
	// public void insert(List<Word> words){
	// if(words == null||words.size()==0){
	// return;
	// }
	//
	// openDatabase();
	//
	//
	// //拆分为三部分，1，字段 。 2，有多少组数据select ?,? union all select ?,?
	// //3, 值，对应问号上的值
	//
	//
	// //1，保存要插入的字段 a,b,c 格式
	// String field = "";
	// String pro = ""; //保存有多少个问号 ?,?,? 格式
	// int i=0;
	// //不要id 号
	// for(i=1;i<Word.FIELD.length-1;i++){
	// field+=Word.FIELD[i]+",";
	// pro +=" '?',";
	// }
	// field+=Word.FIELD[i];
	// pro+="'?' ";
	//
	// //2, 看看有多少组数据
	// String count = " select "+pro; //保存有多个条数据
	//
	// for(i=1;i<words.size();i++){
	// count+=" union all "+" select "+pro;
	// }
	//
	// //3, 组号？ 好的值 Object
	// Object o[] = new Object[(Word.FIELD.length-1)*words.size()];
	// for(i = 0; i<words.size();i++){
	// Word word = words.get(i);
	// for(int j=1;j<Word.FIELD.length;j++){
	// o[(j-1)+i*10] = word.get(j);
	// }
	// }
	// for(int p=0;p<o.length;p++)
	// System.out.println("WordDao insert(list<word>) -- >>object数据 ："+o[p]);
	// db.execSQL("inset into "+Word.TABLE_NAME+"("+field+")"+count,o);
	// //保存要插入的数据
	//
	// closeDatabase();
	// }
	//
	/**
	 * 添加一个单词
	 * 
	 * @param word
	 *            要添加的单词
	 * @return 单词有了或者写入成功 - 返回插入后的id 号 ， 写入失败 返回 -1
	 */
	public int insert(Word word) {
		openDatabase();
		// 没有这个单词才写
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
	 * 查询 Word表的一个单词
	 * 
	 * @param id
	 *            要查询单词的id
	 * @return 有 返回实例，没 返回null
	 */
	public Word query(int id) {
		openDatabase();
		Word word = null;

		String sql = "select * FROM " + Word.TABLE_NAME + " where _id=?";
		Cursor cursor = db.rawQuery(sql, new String[] { id + "" });
		System.out.println("WordDao gerWord() -- >> 结果条数:  "
				+ cursor.getCount());
		if (cursor.getCount() != 0) {
			String fieldValue[] = new String[Word.FIELD.length];
			cursor.moveToFirst();
			for (int i = 0; i < Word.FIELD.length; i++) {
				fieldValue[i] = cursor.getString(cursor
						.getColumnIndex(Word.FIELD[i]));
System.out.println("WordDao query() -- >>查询的结果"
						+ fieldValue[i]);
			}
			word = Word.parse(fieldValue);
		}
		closeDatabase();
		return word;
	}
	/**
	 * 通过where 查找合适的list
	 * @param where 
	 * @return 如果找到就装进list 返回，否则list 里面为空
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
System.out.println("WordDao rawQuery() -- >>查询的结果"
						+ fieldValue[i]);
			}
			words.add(Word.parse(fieldValue));
		}
		
		return words;
	}

	/**
	 * 查询一个单词记录
	 * @param word 用单词匹配
	 * @return 找到 返回实例  否则 null
	 */
	public Word query(String wordStr) {
		openDatabase();
		Word word = null;

		String sql = "select * FROM " + Word.TABLE_NAME + " where _id=?";
		Cursor cursor = db.rawQuery(sql, new String[] { wordStr });
		System.out.println("WordDao gerWord() -- >> 结果条数:  "
				+ cursor.getCount());
		if (cursor.getCount() != 0) {
			String fieldValue[] = new String[Word.FIELD.length];
			cursor.moveToFirst();
			for (int i = 0; i < Word.FIELD.length; i++) {
				fieldValue[i] = cursor.getString(cursor
						.getColumnIndex(Word.FIELD[i]));
				System.out.println("WordDao gerWord() -- >>查询的结果"
						+ fieldValue[i]);
			}
			word = Word.parse(fieldValue);
		}
		closeDatabase();
		return word;
	}

	/**
	 * 查看表中有没有这个单词
	 * 
	 * @param word
	 *            要查询的单词
	 * @return 有 返回id 没 返回-1
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
	 * 打开数据库
	 */
	private void openDatabase() {
		if (dbOpenHelper == null) {
			dbOpenHelper = new DBOpenHelper(context);
			db = dbOpenHelper.getWritableDatabase();
		}

	}

	/**
	 * 关闭数据库
	 */
	private void closeDatabase() {
		if (dbOpenHelper != null) {
			dbOpenHelper.close();
			dbOpenHelper = null;
			db = null;
		}
	}

	/**
	 * 返回word 表的记录数目
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
