package com.ven.save;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

		// 创建Word 表
		String sql = "create table word(_id INTEGER PRIMARY KEY AUTOINCREMENT,count integer,remark nvarchar(128),query nvarchar(64) not null, translation nvarchar(64),phonetic nvarchar(64), explains nvarchar(128))";
		db.execSQL(sql);
		
		//创建Sentence 表
		sql = "create table sentence(_id INTEGER PRIMARY KEY AUTOINCREMENT,tts nvarchar(128),content nvarchar(512),note nvarchar(512), picture nvarchar(128),picture2 nvarchar(128), dateline nvarchar(128),number integer,total integer)";
		db.execSQL(sql);
		
System.out.println("DBOpenHelper onCreate() -- >sql == " + sql);
	}

	/**
	 * 更新数据库的时候自动调用，就是版本号不一样的时候
	 */
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}
