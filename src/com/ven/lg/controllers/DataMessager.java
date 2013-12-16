package com.ven.lg.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import android.content.Context;

import com.ven.da.WordDao;
import com.ven.ui.entity.Word;

/**
 * 管理整个程序的数据，协调不同数据之间的交互，如文件和数据库
 * 
 * @author vector
 * 
 */
public class DataMessager {
	
	Context context = null;
	
	public DataMessager(Context context) {
		this.context = context;
	}
	
	/**
	 * 把path文件中的单词导入到word 表中
	 * 
	 * @param path
	 * @throws IOException
	 * @throws DocumentException
	 */
	public void XML2Db(String path){
		try {
			List<Word> words = new ArrayList<Word>();
			File file = new File(path);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)));
			String xml = "";
			String line = "";
			while ((line = reader.readLine()) != null) {
				xml += line + "\n";
			}
			Document doc = DocumentHelper.parseText(xml);
			
			// 获取根节点 wordbook
			Element rootElt = doc.getRootElement();

			Iterator<Element> iter = rootElt.elementIterator();
			// 遍历item 节点, 一个item 为一个单词
			while (iter.hasNext()) {
				String fieldValue[] = new String[Word.FIELD.length];
				Element item = iter.next();
				// 遍历item 节点
				Iterator<Element> itemIter = item.elementIterator();
				while(itemIter.hasNext()){
					Element field = itemIter.next(); 
					//拿到字段名
					String name = field.getName();
					for(int i=0;i<Word.FIELD.length;i++){
						if(name.equalsIgnoreCase(Word.FIELD[i])){
							fieldValue[i] = field.getText();
						}
					}
				}
				words.add(Word.parse(fieldValue));
			}
			
			new WordDao(context).insert(words);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 把path文件中的单词导入到word 表中
	 * 
	 * @param path
	 * @throws IOException
	 * @throws DocumentException
	 */
	public void XML2Db(InputStream in){
		try {
			List<Word> words = new ArrayList<Word>();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					in));
			String xml = "";
			String line = "";
			while ((line = reader.readLine()) != null) {
				xml += line + "\n";
			}
			Document doc = DocumentHelper.parseText(xml);
			
			// 获取根节点 wordbook
			Element rootElt = doc.getRootElement();
			
			Iterator<Element> iter = rootElt.elementIterator();
			// 遍历item 节点, 一个item 为一个单词
			while (iter.hasNext()) {
				String fieldValue[] = new String[Word.FIELD.length];
				Element item = iter.next();
				// 遍历item 节点
				Iterator<Element> itemIter = item.elementIterator();
				while(itemIter.hasNext()){
					Element field = itemIter.next(); 
					//拿到字段名
					String name = field.getName();
					for(int i=0;i<Word.FIELD.length;i++){
						if(name.equalsIgnoreCase(Word.FIELD[i])){
							fieldValue[i] = field.getText();
						}
					}
				}
				words.add(Word.parse(fieldValue));
			}
			
			new WordDao(context).insert(words);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
