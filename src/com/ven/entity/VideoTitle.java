package com.ven.entity;

import com.google.gson.Gson;

public class VideoTitle {
	
	public static VideoTitle createByJson(String json){
		Gson gson = new Gson();
		return gson.fromJson(json, VideoTitle.class); 
	}
	
	/**
	 * 级别说明
	 */
	private String CourseName;
	/**
	 * 课程信息
	 */
	private Lesson Lesson;
	public class Lesson{
		/**
		 * 课程中文名
		 */
		private String LessonName;
		/**
		 * 课程英文名称
		 */
		private String LessonEnglishName;
		/**
		 * 课程描述
		 */
		private String LessonDescr;
		/**
		 * 图片地址
		 */
		private String LessonMediaSrc;
		public String getLessonName() {
			return LessonName;
		}
		public void setLessonName(String lessonName) {
			LessonName = lessonName;
		}
		public String getLessonEnglishName() {
			return LessonEnglishName;
		}
		public void setLessonEnglishName(String lessonEnglishName) {
			LessonEnglishName = lessonEnglishName;
		}
		public String getLessonDescr() {
			return LessonDescr;
		}
		public void setLessonDescr(String lessonDescr) {
			LessonDescr = lessonDescr;
		}
		public String getLessonMediaSrc() {
			return LessonMediaSrc;
		}
		public void setLessonMediaSrc(String lessonMediaSrc) {
			LessonMediaSrc = lessonMediaSrc;
		}
		@Override
		public String toString() {
			return "Lesson [LessonName=" + LessonName + ", LessonEnglishName="
					+ LessonEnglishName + ", LessonDescr=" + LessonDescr
					+ ", LessonMediaSrc=" + LessonMediaSrc + "]";
		}
		
	}
	public String getCourseName() {
		return CourseName;
	}
	public void setCourseName(String courseName) {
		CourseName = courseName;
	}
	public Lesson getLesson() {
		return Lesson;
	}
	public void setLesson(Lesson lesson) {
		Lesson = lesson;
	}
	@Override
	public String toString() {
		return "VideoTitle [CourseName=" + CourseName + ", Lesson=" + Lesson
				+ "]";
	}
	
}
