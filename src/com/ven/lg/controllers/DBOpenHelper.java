package com.ven.lg.controllers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ven.ui.entity.Word;

public class DBOpenHelper extends SQLiteOpenHelper {
	/**
	 * ���ݿ�Ӧ�������µģ������߲���֪�����ڵİ汾���Ƕ��٣�֪�������µľͿ�����
	 */
	private final static int VEN_DB_VERSION = 2;
	/**
	 * ���ݿ����һ���ͺ��ˣ������½���ô��
	 */
	private final static String DB_NAME = "VEN_DB";

	/**
	 * ����һ��ָ���������ݿ�Ķ���
	 * 
	 * @param context
	 *            �����Ķ���
	 * @param factory
	 *            CursorFactory����
	 */
	public DBOpenHelper(Context context) {
		super(context, DB_NAME, null, VEN_DB_VERSION);
	}

	/**
	 * ���δ������ݿ��ʱ���Զ����ã������������ݿ��
	 */
	public void onCreate(SQLiteDatabase db) {
		// ֻ���½�����֪��Ҫ�½�ʲô���ı�

		// ����Word ��

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
	 * �������ݿ��ʱ���Զ����ã����ǰ汾�Ų�һ����ʱ��
	 */
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}
