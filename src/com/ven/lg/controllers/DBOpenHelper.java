package com.ven.lg.controllers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ven.ui.entity.Word;

public class DBOpenHelper extends SQLiteOpenHelper {
	/**
	 * 数据库应该用最新的，调用者不必知道现在的版本号是多少，知道是最新的就可以了
	 */
	private final static int VEN_DB_VERSION = 2;
	/**
	 * 数据库就用一个就好了，不必新建那么多
	 */
	private final static String DB_NAME = "VEN_DB";

	/**
	 * 生成一个指向最新数据库的对象
	 * 
	 * @param context
	 *            上下文对象
	 * @param factory
	 *            CursorFactory类型
	 */
	public DBOpenHelper(Context context) {
		super(context, DB_NAME, null, VEN_DB_VERSION);
	}

	/**
	 * 初次创建数据库的时候自动调用，用来生成数据库表
	 */
	public void onCreate(SQLiteDatabase db) {
		// 只是新建表，不知道要新建什么样的表

		// 创建Word 表

		String field = "";
		String key = "";    
		String value = "";
		for (int i = 0; i < Word.FIELD.length; i++) {
			if (!"".equals(key))
				field += key + " " + value + ",";
			key = Word.FIELD[i];
			value = Word.FIELD_DATA_TYPE[i];
		}

		field += key + " " + value;

		String sql = "create table " + Word.TABLE_NAME + "(" + field + ")";
		db.execSQL(sql);
System.out.println("DBOpenHelper onCreate() -- >sql == " + sql);
	}

	/**
	 * 更新数据库的时候自动调用，就是版本号不一样的时候
	 */
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}
