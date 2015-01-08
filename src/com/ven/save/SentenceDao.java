package com.ven.save;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ven.entity.Sentence;

/**
 * 对 sentence 的 crud
 * 
 * @author vector
 * 
 */
public class SentenceDao {

	SQLiteDatabase db = null;
	DBOpenHelper dbOpenHelper = null;
	private Context context;

	public SentenceDao(Context context) {
		this.context = context;
	}

	/**
	 * 插入多条记录，是一条一条的插入的, 如果有的不插入。
	 * 
	 * @param sentences
	 * @return 插入记录条数
	 */
	public int insert(List<Sentence> sentences) {
		if(sentences == null){
			return -1;
		}
		openDatabase();
		int count = 0;
		for (Sentence sentence : sentences) {
			// 没有这个单词才写
			int id = isHas(db, sentence.getDateline());
			if (id >= 0) {
				continue;
			}

			String field = "";

			ContentValues values = new ContentValues();

			if (sentence.getTts() != null) {
				values.put("tts", sentence.getTts());
				field += "tts";
			}
			values.put("number", sentence.getNumber());
			field += ",number";
			values.put("total", sentence.getTotal());
			field += ",total";

			if (sentence.getContent() != null) {
				values.put("content", sentence.getContent());
				field += ",content";
			}
			if (sentence.getContent() != null) {
				values.put("note", sentence.getNote());
				field += ",note";
			}
			if (sentence.getContent() != null) {
				values.put("picture", sentence.getPicture());
				field += ",picture";
			}
			if (sentence.getContent() != null) {
				values.put("picture2", sentence.getPicture2());
				field += ",picture2";
			}
			if (sentence.getContent() != null) {
				values.put("dateline", sentence.getDateline());
				field += ",dateline";
			}

			id = (int) db.insert("sentence", field, values);
			if (id >= 0) {
				count++;
			}
		}
		closeDatabase();
		return count;
	}

	/**
	 * 添加一个句子
	 * 
	 * @param sentence
	 *            要添加的句子
	 * @return 句子有了或者写入成功 - 返回插入后的id 号 ， 写入失败 返回 -1
	 */
	public int insert(Sentence sentence) {
		if(sentence == null){
			return -1;
		}
		openDatabase();
		// 没有这个单词才写
		int id = isHas(db, sentence.getDateline());
		if (id >= 0) {
			closeDatabase();
			return id;
		}

		String field = "";

		ContentValues values = new ContentValues();

		if (sentence.getTts() != null) {
			values.put("tts", sentence.getTts());
			field += "tts";
		}
		values.put("number", sentence.getNumber());
		field += ",number";
		values.put("total", sentence.getTotal());
		field += ",total";

		if (sentence.getContent() != null) {
			values.put("content", sentence.getContent());
			field += ",content";
		}
		if (sentence.getContent() != null) {
			values.put("note", sentence.getNote());
			field += ",note";
		}
		if (sentence.getContent() != null) {
			values.put("picture", sentence.getPicture());
			field += ",picture";
		}
		if (sentence.getContent() != null) {
			values.put("picture2", sentence.getPicture2());
			field += ",picture2";
		}
		if (sentence.getContent() != null) {
			values.put("dateline", sentence.getDateline());
			field += ",dateline";
		}

		id = (int) db.insert("sentence", field, values);
		closeDatabase();
		return id;
	}

	/**
	 * 删除一个句子记录
	 * 
	 * @param id
	 *            要删除的记录id
	 * @return 受影响的行数
	 */
	public int delete(int id) {
		openDatabase();
		int count = db.delete("sentence", "_id=?", new String[] { id + "" });
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
		int count = db.delete("sentence", where, null);
		closeDatabase();
		return count;
	}

	/**
	 * 批量删除记录
	 * 
	 * @param sentences
	 *            要删除的记录,是以id 为查找条件
	 * @return 受影响的行数
	 */
	public int delete(List<Sentence> sentences) {
		int count = 0, id;
		openDatabase();
		for (Sentence sentence : sentences) {
			id = sentence.getId();
			count += db.delete("sentence", "_id=?", new String[] { id + "" });
		}
		closeDatabase();
		return count;
	}

	/**
	 * 更新记录
	 * 
	 * @param sentence
	 * @return 受影响的行数
	 */
	public int update(Sentence sentence) {
		openDatabase();

		ContentValues values = new ContentValues();

		if (sentence.getTts() != null) {
			values.put("tts", sentence.getTts());
		}
		values.put("number", sentence.getNumber());
		values.put("total", sentence.getTotal());

		if (sentence.getContent() != null) {
			values.put("content", sentence.getContent());
		}
		if (sentence.getContent() != null) {
			values.put("note", sentence.getNote());
		}
		if (sentence.getContent() != null) {
			values.put("picture", sentence.getPicture());
		}
		if (sentence.getContent() != null) {
			values.put("picture2", sentence.getPicture2());
		}
		if (sentence.getContent() != null) {
			values.put("dateline", sentence.getDateline());
		}

		int count = db.update("sentence", values, "_id=?",
				new String[] { sentence.getId() + "" });
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
	public int update(List<Sentence> sentences) {
		openDatabase();
		int count = 0;

		for (Sentence sentence : sentences) {
			ContentValues values = new ContentValues();

			if (sentence.getTts() != null) {
				values.put("tts", sentence.getTts());
			}
			values.put("number", sentence.getNumber());
			values.put("total", sentence.getTotal());

			if (sentence.getContent() != null) {
				values.put("content", sentence.getContent());
			}
			if (sentence.getContent() != null) {
				values.put("note", sentence.getNote());
			}
			if (sentence.getContent() != null) {
				values.put("picture", sentence.getPicture());
			}
			if (sentence.getContent() != null) {
				values.put("picture2", sentence.getPicture2());
			}
			if (sentence.getContent() != null) {
				values.put("dateline", sentence.getDateline());
			}

			count += db.update("sentence", values, "_id=?",
					new String[] { sentence.getId() + "" });
		}
		closeDatabase();

		return count;
	}

	/**
	 * 查询 Word表的一个句子
	 * 
	 * @param id
	 *            要查询单词的id
	 * @return 有 返回实例，没 返回null
	 */
	public Sentence query(int id) {
		openDatabase();
		Sentence sentence = new Sentence();

		String sql = "select * FROM sentence where _id=?";
		Cursor cursor = db.rawQuery(sql, new String[] { id + "" });
		if (cursor.getCount() != 0) {
			cursor.moveToFirst();
			sentence.setId(id);

			sentence.setTts(cursor.getString(cursor
					.getColumnIndexOrThrow("tts")));
			sentence.setContent(cursor.getString(cursor
					.getColumnIndexOrThrow("content")));
			sentence.setNote(cursor.getString(cursor
					.getColumnIndexOrThrow("note")));
			sentence.setPicture(cursor.getString(cursor
					.getColumnIndexOrThrow("picture")));
			sentence.setPicture2(cursor.getString(cursor
					.getColumnIndexOrThrow("picture2")));
			sentence.setDateline(cursor.getString(cursor
					.getColumnIndexOrThrow("dateline")));
			sentence.setNumber(cursor.getInt(cursor
					.getColumnIndexOrThrow("number")));
			sentence.setTotal(cursor.getInt(cursor
					.getColumnIndexOrThrow("total")));
		}
		closeDatabase();
		return sentence;
	}

	/**
	 * 通过where 查找合适的list
	 * 
	 * @param where
	 * @return 如果找到就装进list 返回，否则list 里面为空
	 */
	public List<Sentence> query(String where) {
		List<Sentence> sentences = new ArrayList<Sentence>();
		openDatabase();

		String sql = "select * FROM sentence where " + where;
		Cursor cursor = db.rawQuery(sql, null);
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			Sentence sentence = new Sentence();
			
			sentence.setId(cursor.getInt(cursor.getColumnIndexOrThrow("_id")));
			sentence.setTts(cursor.getString(cursor
					.getColumnIndexOrThrow("tts")));
			sentence.setContent(cursor.getString(cursor
					.getColumnIndexOrThrow("content")));
			sentence.setNote(cursor.getString(cursor
					.getColumnIndexOrThrow("note")));
			sentence.setPicture(cursor.getString(cursor
					.getColumnIndexOrThrow("picture")));
			sentence.setPicture2(cursor.getString(cursor
					.getColumnIndexOrThrow("picture2")));
			sentence.setDateline(cursor.getString(cursor
					.getColumnIndexOrThrow("dateline")));
			sentence.setNumber(cursor.getInt(cursor
					.getColumnIndexOrThrow("number")));
			sentence.setTotal(cursor.getInt(cursor
					.getColumnIndexOrThrow("total")));
			sentences.add(sentence);
		}

		closeDatabase();
		return sentences;

	}

	/**
	 * 返回word 表的记录数目
	 * 
	 * @return
	 */
	public int getCount() {
		openDatabase();
		Cursor cursor = db.rawQuery("select count(*) as cut from sentence", null);
		cursor.moveToFirst();
		return cursor.getInt(0);
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
	 * 查看表中有没有这天的句子
	 * 
	 * @param dateline
	 *            要查询的日期
	 * @return 有 返回id 没 返回-1
	 */
	private int isHas(SQLiteDatabase db, String dateline) {
		String sql = "select * FROM sentence where dateline=?";
		Cursor cursor = db.rawQuery(sql, new String[] { dateline });
		if (cursor.getCount() == 0) {
			return -1;
		}
		cursor.moveToFirst();
		return cursor.getInt(cursor.getColumnIndex("_id"));
	}
}
