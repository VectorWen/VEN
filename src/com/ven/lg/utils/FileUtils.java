package com.ven.lg.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.os.Environment;

/**
 * һЩ�����ļ��Ĺ��߷���
 * @author vector
 *
 */
public class FileUtils {
	/**
	 * sdcard �ĸ�Ŀ¼ 
	 */
	private static String SDCardRoot = "";
	/**
	 * ��ʼ��һЩ����
	 */
	static {
		// �õ���ǰ�ļ��ⲿ�洢Ŀ¼
		SDCardRoot = Environment.getExternalStorageDirectory()+File.separator;
	}
	
	/**
	 * ����ʵ����
	 */
	private FileUtils(){}
	
	/**
	 * ��SD���ϴ����ļ�
	 * @param fileName  Ҫ�������ļ���
	 * @param dir      Ҫ�����ļ���Ŀ¼
	 * @return
	 * @throws IOException
	 */
	public static  File createFileInSDCard(String fileName, String dir)
			throws IOException {
		File file = new File(SDCardRoot + dir + File.separator + fileName);
		file.createNewFile();
		return file;
	}

	/**
	 * ��SD���ϴ���Ŀ¼
	 * @param dir
	 * @return
	 * @throws IOException
	 */
	public static  File createSDDir(String dir) throws IOException {
		File dirFile = new File(SDCardRoot + dir + File.separator);
		dirFile.mkdirs();
		return dirFile;
	}
	
	/**
	 * �ж�sdcard ʱ�����
	 * @return
	 */
	public static boolean hasSDCard(){
		if(SDCardRoot!=null&&!"".equals(SDCardRoot)){
			return true;
		}
		return false;
	}

	/**
	 * �ж�SD���ϵ��ļ��л��ļ��Ƿ����
	 * @param fileName
	 * @param path
	 * @return
	 */
	public static  boolean ifFileExist(String fileName, String path) {
		File file = new File(SDCardRoot + path + File.separator + fileName);
		return file.exists();
	}

	/**
	 * ��InputStream���������д��SD������
	 * 
	 * @param path
	 *            --���ļ���·��
	 * @param fileName
	 *            --�ļ�������
	 * @param input
	 *            --���������������д��SD����
	 */
	public static  File write2SDFromInput(String path, String fileName,
			InputStream input) {
		File file = null;
		OutputStream output = null;
		try {
			// ����Ŀ¼�����˾����Ǵ�
			createSDDir(path);
			// �����ļ���
			file = createFileInSDCard(fileName, path);
			// �ѹܵ����������ɵ����У�׼��������д����
			output = new FileOutputStream(file);
			byte[] buffer = new byte[4 * 1024];
			int temp;

			// ��ʼ��д����
			// read(b)����д�����ݽ�ȥ����֪�����ٸ��ģ�
			while ((temp = input.read(buffer)) != -1) {
				output.write(buffer, 0, temp);
			}
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	/**
	 * ��ȡĿ¼�е�MP3�ļ������ֺʹ�С
	 */
//	public static  List<Mp3Info> getMp3Files(String path) {
//		List<Mp3Info> mp3Infos = new ArrayList<Mp3Info>();
//		File file = new File(SDCardRoot + path);
//		File[] files = file.listFiles();
//		for (int i = 0; i < files.length; i++) {
//			if (files[i].getName().endsWith("mp3")) {
//				Mp3Info mp3Info = new Mp3Info();
//				mp3Info.setMp3name(files[i].getName());
//				mp3Info.setMp3Size(files[i].length() + "");
//				mp3Infos.add(mp3Info);
//			}
//		}
//
//		return mp3Infos;
//	}

}
