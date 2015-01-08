package com.ven.entity;

import java.util.Arrays;

import com.google.gson.Gson;

/**
 * 翻译的单词
 * 
 * @author vector
 * 
 */
public class Word {

	private int count; // 看了这个单词多少次了
	private String remark;// 备注
	private int errorCode;
	private String query;// 要翻译的单词
	private String[] translation;// 有道翻译，有用的是translation[0]
	private Basic basic; // 有道词典-基本单词
	private Web web[];// 网络翻译 -- 扩充的翻译
	private int id;

	public static Word createByJson(String json) {
		Gson gson = new Gson();
		return gson.fromJson(json, Word.class);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String[] getTranslation() {
		return translation;
	}

	public void setTranslation(String[] translation) {
		this.translation = translation;
	}

	public Basic getBasic() {
		return basic;
	}

	public void setBasic(Basic basic) {
		this.basic = basic;
	}

	public Web[] getWeb() {
		return web;
	}

	public void setWeb(Web[] web) {
		this.web = web;
	}

	public class Basic {
		private String phonetic; // 音标
		private String[] explains; // 解析 -- 就是更详细的翻译 有用的是explains[0]

		public String getPhonetic() {
			return phonetic;
		}

		public void setPhonetic(String phonetic) {
			this.phonetic = phonetic;
		}

		public String[] getExplains() {
			return explains;
		}

		public void setExplains(String[] explains) {
			this.explains = explains;
		}

		@Override
		public String toString() {
			return "Basic [phonetic=" + phonetic + ", explains="
					+ Arrays.toString(explains) + "]";
		}

	}

	public class Web {
		private String key; // 意思，可以是对应的词组
		private String[] value; // 对应key 的翻译

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String[] getValue() {
			return value;
		}

		public void setValue(String[] value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return "Web [key=" + key + ", value=" + Arrays.toString(value)
					+ "]";
		}

	}

	@Override
	public String toString() {
		return "Word [errorCode=" + errorCode + ", query=" + query
				+ ", translation=" + Arrays.toString(translation) + ", basic="
				+ basic + ", web=" + Arrays.toString(web) + ", getErrorCode()="
				+ getErrorCode() + ", getQuery()=" + getQuery()
				+ ", getTranslation()=" + Arrays.toString(getTranslation())
				+ ", getBasic()=" + getBasic() + ", getWeb()="
				+ Arrays.toString(getWeb()) + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

}
