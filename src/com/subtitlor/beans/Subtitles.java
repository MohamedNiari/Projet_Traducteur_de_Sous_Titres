package com.subtitlor.beans;

import java.util.List;

public class Subtitles {

	private List<String> originalSubtitles;
	private List<String> translatedSubtitles;

	public List<String> getOriginalSubtitles() {
		return originalSubtitles;
	}

	public void setOriginalSubtitles(List<String> originalSubtitles) {
		this.originalSubtitles = originalSubtitles;
	}

	public List<String> getTranslatedSubtitles() {
		return translatedSubtitles;
	}

	public void setTranslatedSubtitles(List<String> translatedSubtitles) {
		this.translatedSubtitles = translatedSubtitles;
	}

}
