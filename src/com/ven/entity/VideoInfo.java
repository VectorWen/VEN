package com.ven.entity;

import android.util.Log;

import com.google.gson.Gson;

public class VideoInfo {
	
	public VideoSentence[] getSentences(){
		try{
			VideoSentence[] vss = getSlides()[0].getLocalizedSlides().getEn().getDialogue().getSentences();
			return vss;
		}catch(NullPointerException e){
			return null;
		}
	}
	
	public static VideoInfo createByJson(String json) {
		Gson gson = new Gson();
		json = json.replaceAll("zh-CN", "ZHCN");
		return gson.fromJson(json, VideoInfo.class);
	}

	private int Id;
	
	

	private Slide Slides[];
	
	

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public Slide[] getSlides() {
		return Slides;
	}

	public void setSlides(Slide[] slides) {
		Slides = slides;
	}



	public  class Slide{
		private LocalizedSlides LocalizedSlides;
		

		public LocalizedSlides getLocalizedSlides() {
			return LocalizedSlides;
		}


		public void setLocalizedSlides(LocalizedSlides localizedSlides) {
			LocalizedSlides = localizedSlides;
		}


		public class LocalizedSlides{
			private En en;
			

			public En getEn() {
				return en;
			}


			public void setEn(En en) {
				this.en = en;
			}


			public class En{
				private Dialogue Dialogue;
				

				public Dialogue getDialogue() {
					return Dialogue;
				}


				public void setDialogue(Dialogue dialogue) {
					Dialogue = dialogue;
				}


				public class Dialogue{
					private VideoSentence Sentences[];

					public VideoSentence[] getSentences() {
						return Sentences;
					}

					public void setSentences(VideoSentence[] sentences) {
						this.Sentences = sentences;
					}
					
				}
			}
		}
	}
}
