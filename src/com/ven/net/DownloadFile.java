package com.ven.net;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.ven.utils.FileUtils;

/**
 * 以Get 请求方式下载一个文件
 * @author vector
 *
 */
public class DownloadFile {

	/**
	 * 只有一个socket
	 */
	private Socket socket = null;
	
	private StringBuffer requestHeader = new StringBuffer();
	
	
	/**
	 * 下载文件到pathname 中,检查了文件是否下载成功，以isEmpty 为准
	 * @param url 主机  "www.baidu.com"
	 * @param param "/translate?ie=UTF-8&idx=9887"
	 * @param path 目录   ven/filepath/
	 * @param filename "name.txt"
	 * @return 下载成功返回TRUE，下载失败返回False
	 */
	public boolean downFile(String url,String param, String path,String filename){
		try {
			//连接主机
			socket = new Socket(url,80);
			
			//发送请求
			requestHeader.insert(0, "GET "+param+" HTTP/1.1\r\n");
			requestHeader.append("\r\n");
			OutputStream out = socket.getOutputStream();
			System.out.println(requestHeader.toString());
			out.write(requestHeader.toString().getBytes());
			socket.shutdownOutput();
			//读取数据到文件中
			 InputStream in = socket.getInputStream();
			 
			 delHeader(in);
			 
			 FileUtils.writeFile(path,filename, in);
			 in.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return !FileUtils.isEmpty(path+File.separator+filename);
	}
	
	private void delHeader(InputStream in) throws IOException {
		 int c = 0;
		  c = in.read();
		  int lineCount = 0;
		  while (c > -1) {
		   if (c == 10 || c == 13) {
		    if (lineCount == 3) {
		     return;
		    }
		    lineCount++;
		   } else {
		    lineCount = 0;
		   }
		   c = in.read();
		  }

	}

	public void addRequestHeader(String header){
		requestHeader.append(header);
		requestHeader.append("\r\n");
	}
}
