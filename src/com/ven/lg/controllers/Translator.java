package com.ven.lg.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ven.ui.entity.WebWord;

public class Translator {
	public WebWord translation(String query) throws IOException {
		WebWord webWord = new WebWord();
		String xml = getTranslation2Xml(getURL(query));

		try {
			Document doc = DocumentHelper.parseText(xml);

			// 获取根节点 youdao-fanyi
			Element rootElt = doc.getRootElement();

			Iterator<Element> iter = rootElt.elementIterator();
			// 遍历根节点下的节点
			while (iter.hasNext()) {

				Element item = iter.next();
				String name = item.getName().trim();
System.out.println("Translator translation() -->> name  :  "+item.getName());
				if ("errorCode".equalsIgnoreCase(name)) {
System.out.println("Translator translation() -->> errorCode   :  "+item.getText());
					webWord.setErrorCode(Integer.parseInt(item.getText()));
				}
				if ("basic".equalsIgnoreCase(name)) {
					Iterator<Element> itemIter = item.elementIterator();
System.out.println("Translator translation() -->> basic : "+item.getName());
					while (itemIter.hasNext()) {
						Element item2 = itemIter.next();
						String item2Name = item2.getName();
System.out.println("Translator translation() -->> basic : "+item2Name);
						if ("phonetic".equalsIgnoreCase(item2Name)) {
System.out.println("Translator translation() -->> phonetic   :  "+item2.getText());
							webWord.setPhonetic(item2.getText());
						}
						if ("explains".equalsIgnoreCase(item2Name)) {
							Iterator<Element> itemIter2 = item2.elementIterator();
System.out.println("Translator translation() -->> phonetic   :  进入");
							String trans = "";
							while (itemIter2.hasNext()) {
								Element item3 = itemIter2.next();
								trans += item3.getText() + "\n";
System.out.println("Translator translation() -->> phonetic   :  "+trans);
							}
							webWord.setTranslation(trans);
						}
					}

				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		return webWord;
	}

	private String getTranslation2Xml(String url) throws IOException {
		String xml = "";

		// 1--要有请求方法，GET或POST
		HttpGet httpGet = new HttpGet(url);

		// 2--要有客户端HttpClient
		HttpClient httpClient = new DefaultHttpClient();

		// 3--客户端把请求发送出去,并得到响应HttpResponse httpResponse
		HttpResponse httpResponse = httpClient.execute(httpGet);

		// 4--最后拿到想要的实体HttpEntity实体
		HttpEntity httpEntity = httpResponse.getEntity();

		// 5--最后插管道拿到数据 , 转化为html文件

		xml = EntityUtils.toString(httpEntity);
		// inputStream = httpEntity.getContent();
		System.out.println("Translation getXml()-->>xml : " + xml);
		return xml;
	}

	private String getURL(String query) {
		String url = "";
		try {
			url = "http://fanyi.youdao.com/openapi.do?"
					+ "keyfrom=VectorHuang&key=2066326006"
					+ "&type=data&doctype=xml&version=1.1&q="
					+ URLEncoder.encode(query.trim(), "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Translation getUrl()-->>url : " + url);
		return url;
	}
}
