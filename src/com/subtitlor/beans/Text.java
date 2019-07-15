package com.subtitlor.beans;

public class Text {

	private int id;
	private String textToTranslate;
	private String textTranslated;

	public String getTextToTranslate() {
		return textToTranslate;
	}

	public void setTextToTranslate(String textToTranslate) {
		this.textToTranslate = textToTranslate;
	}

	public String getTextTranslated() {
		return textTranslated;
	}

	public void setTextTranslated(String textTranslated) {
		this.textTranslated = textTranslated;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
