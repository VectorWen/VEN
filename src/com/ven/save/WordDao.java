package com.ven.save;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ven.entity.Word;
import com.ven.entity.Word.Basic;

/**
 * 对word 的crud
 * 
 * @author vector
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
	 * @return 插入记录条数
	 */
	public int insert(List<Word> words) {
		openDatabase();
		int count = 0;
		for (Word word : words) {
			// 没有这个单词才写
			int id = isHas(db, word.getQuery());
			if (id >= 0) {
				continue;
			}

			String field = "";

			ContentValues values = new ContentValues();

			if (word.getRemark() != null) {
				values.put("remark", word.getRemark());
				field += "remark";
			}
			values.put("count", word.getCount());
			field += ",count";
			values.put("query", word.getQuery());
			field += ",query";

			if (word.getTranslation() != null) {
				values.put("translation", word.getTranslation()[0]);
				field += ",translation";
			}
			if (word.getBasic() != null) {
				values.put("phonetic", word.getBasic().getPhonetic());
				field += ",phonetic";

				if (word.getBasic().getExplains() != null) {
					values.put("explains", word.getBasic().getExplains()[0]);
					field += ",explains";
				}
			}

			id = (int) db.insert("word", field, values);
			if (id >= 0) {
				count++;
			}
		}
		closeDatabase();
		return count;
	}

	/**
	 * 添加一个单词
	 * 
	 * @param word
	 *            要添加的单词
	 * @return 单词有了或者写入成功 - 返回插入后的id 号 ， 写入失败 返回 -1
	 */
	public int insert(Word word) {
		openDatabase();
		if (word == null) {
			return -1;
		}
		// 没有这个单词才写
		int id = isHas(db, word.getQuery());
		if (id >= 0) {
			closeDatabase();
			return id;
		}

		String field = "";

		ContentValues values = new ContentValues();

		if (word.getRemark() != null) {
			values.put("remark", word.getRemark());
			field += "remark";
		}
		values.put("count", word.getCount());
		field += ",count";
		values.put("query", word.getQuery());
		field += ",query";

		if (word.getTranslation() != null) {
			values.put("translation", word.getTranslation()[0]);
			field += ",translation";
		}
		if (word.getBasic() != null) {
			values.put("phonetic", word.getBasic().getPhonetic());
			field += ",phonetic";

			if (word.getBasic().getExplains() != null) {
				values.put("explains", word.getBasic().getExplains()[0]);
				field += ",explains";
			}
		}

		id = (int) db.insert("word", field, values);
		closeDatabase();
		return id;
	}

	/**
	 * 删除一个单词记录
	 * 
	 * @param id
	 *            要删除的记录id
	 * @return 受影响的行数
	 */
	public int delete(int id) {
		openDatabase();
		int count = db.delete("word", "_id=?", new String[] { id + "" });
		closeDatabase();
		return count;
	}

	/**
	 * 删除满足条件的记录
	 * 
	 * @param where
	 *            条件
	 * @return 受影响的行数
	 */
	public int delete(String where) {
		openDatabase();
		int count = db.delete("word", where, null);
		closeDatabase();
		return count;
	}

	/**
	 * 批量删除记录
	 * 
	 * @param words
	 *            要删除的记录,是以id 为查找条件
	 * @return 受影响的行数
	 */
	public int delete(List<Word> words) {
		int count = 0, id;
		openDatabase();
		for (Word word : words) {
			id = word.getId();
			count += db.delete("word", "_id=?", new String[] { id + "" });
		}
		closeDatabase();
		return count;
	}

	/**
	 * 更新记录
	 * 
	 * @param word
	 * @return 受影响的行数
	 */
	public int update(Word word) {
		openDatabase();

		ContentValues values = new ContentValues();

		if (word.getRemark() != null) {
			values.put("remark", word.getRemark());
		}
		values.put("count", word.getCount());
		values.put("query", word.getQuery());

		if (word.getTranslation() != null) {
			values.put("translation", word.getTranslation()[0]);
		}
		if (word.getBasic() != null) {
			values.put("phonetic", word.getBasic().getPhonetic());

			if (word.getBasic().getExplains() != null) {
				values.put("explains", word.getBasic().getExplains()[0]);
			}
		}

		int count = db.update("word", values, "_id=?",
				new String[] { word.getId() + "" });
		closeDatabase();

		return count;
	}

	/**
	 * 批量更新记录
	 * 
	 * @param word
	 * @return 受影响的行数
	 * 
	 */
	public int update(List<Word> words) {
		openDatabase();
		int count = 0;

		for (Word word : words) {
			ContentValues values = new ContentValues();

			if (word.getRemark() != null) {
				values.put("remark", word.getRemark());
			}
			values.put("count", word.getCount());
			values.put("query", word.getQuery());

			if (word.getTranslation() != null) {
				values.put("translation", word.getTranslation()[0]);
			}
			if (word.getBasic() != null) {
				values.put("phonetic", word.getBasic().getPhonetic());

				if (word.getBasic().getExplains() != null) {
					values.put("explains", word.getBasic().getExplains()[0]);
				}
			}

			count += db.update("word", values, "_id=?",
					new String[] { word.getId() + "" });
		}
		closeDatabase();

		return count;
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
		Word word = new Word();
		String sql = "select * FROM word where _id=?";
		Cursor cursor = db.rawQuery(sql, new String[] { id + "" });
		System.out.println("WordDao gerWord() -- >> 结果条数:  "
				+ cursor.getCount());
		if (cursor.getCount() != 0) {
			cursor.moveToFirst();
			word.setId(id);

			word.setCount(cursor.getInt(cursor.getColumnIndexOrThrow("count")));
			word.setRemark(cursor.getString(cursor
					.getColumnIndexOrThrow("remark")));
			word.setQuery(cursor.getString(cursor
					.getColumnIndexOrThrow("query")));
			word.setTranslation(new String[] { cursor.getString(cursor
					.getColumnIndexOrThrow("translation")) });

			Basic basic = word.new Basic();
			;
			basic.setPhonetic(cursor.getString(cursor
					.getColumnIndexOrThrow("phonetic")));
			basic.setExplains(new String[] { cursor.getString(cursor
					.getColumnIndexOrThrow("explains")) });
			word.setBasic(basic);
		}
		closeDatabase();
		return word;
	}

	/**
	 * 通过where 查找合适的list
	 * 
	 * @param where
	 * @return 如果找到就装进list 返回，否则list 里面为空
	 */
	public List<Word> query(String where) {
		List<Word> words = new ArrayList<Word>();
		openDatabase();

		String sql = "select * FROM word where " + where;
		Cursor cursor = db.rawQuery(sql, null);
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			Word word = new Word();
			word.setId(cursor.getInt(cursor.getColumnIndexOrThrow("_id")));
			word.setCount(cursor.getInt(cursor.getColumnIndexOrThrow("count")));
			word.setRemark(cursor.getString(cursor
					.getColumnIndexOrThrow("remark")));
			word.setQuery(cursor.getString(cursor
					.getColumnIndexOrThrow("query")));
			word.setTranslation(new String[] { cursor.getString(cursor
					.getColumnIndexOrThrow("translation")) });

			Basic basic = word.new Basic();
			;
			basic.setPhonetic(cursor.getString(cursor
					.getColumnIndexOrThrow("phonetic")));
			basic.setExplains(new String[] { cursor.getString(cursor
					.getColumnIndexOrThrow("explains")) });
			word.setBasic(basic);
			words.add(word);
		}

		closeDatabase();
		return words;

	}

	/**
	 * 分页查找
	 * 
	 * @param number
	 *            -- 一页有多少条数据
	 * @param page
	 *            -- 现在是多少页
	 * @return
	 */
	public List<Word> query(int number, int page) {
		List<Word> words = new ArrayList<Word>();

		int start = getCount() - number * page;
		if(start<0){
			number += start; 
		}
		
		if(number<=0){
			return words;
		}
		
		String sql = "select * from (select * FROM word limit "
				+ start + " , " + number
				+ ") as T order by _id DESC";

		openDatabase();

		System.out.println(sql + db);
		Cursor cursor = db.rawQuery(sql, null);
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			Word word = new Word();
			word.setId(cursor.getInt(cursor.getColumnIndexOrThrow("_id")));
			word.setCount(cursor.getInt(cursor.getColumnIndexOrThrow("count")));
			word.setRemark(cursor.getString(cursor
					.getColumnIndexOrThrow("remark")));
			word.setQuery(cursor.getString(cursor
					.getColumnIndexOrThrow("query")));
			word.setTranslation(new String[] { cursor.getString(cursor
					.getColumnIndexOrThrow("translation")) });

			Basic basic = word.new Basic();
			;
			basic.setPhonetic(cursor.getString(cursor
					.getColumnIndexOrThrow("phonetic")));
			basic.setExplains(new String[] { cursor.getString(cursor
					.getColumnIndexOrThrow("explains")) });
			word.setBasic(basic);
			words.add(word);
		}

		closeDatabase();
		return words;

	}

	/**
	 * 返回word 表的记录数目
	 * 
	 * @return
	 */
	public int getCount() {
		openDatabase();
		Cursor cursor = db.rawQuery("select count(*) as cut from word", null);
		cursor.moveToFirst();
		int count = cursor.getInt(0);
		closeDatabase();
		return count;
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
	 * 查看表中有没有这个单词
	 * 
	 * @param word
	 *            要查询的单词
	 * @return 有 返回id 没 返回-1
	 */
	private int isHas(SQLiteDatabase db, String query) {
		String sql = "select * FROM word where query=?";
		Cursor cursor = db.rawQuery(sql, new String[] { query });
		if (cursor.getCount() == 0) {
			return -1;
		}
		cursor.moveToFirst();
		return cursor.getInt(cursor.getColumnIndex("_id"));
	}

	/**
	 * 查看表中有没有这个单词
	 * 
	 * @param word
	 *            要查询的单词
	 * @return 有 返回id 没 返回-1
	 */
	public int isHas(String query) {
		openDatabase();
		int is = isHas(db, query);
		closeDatabase();
		return is;
	}

}
