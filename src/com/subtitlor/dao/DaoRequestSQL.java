package com.subtitlor.dao;

import java.util.List;

import com.subtitlor.beans.Subtitles;
import com.subtitlor.utilities.SubtitlesHandler;

public interface DaoRequestSQL {

	void saveSubtitles(SubtitlesHandler subtitles);

	void updateSubtitles(SubtitlesHandler subtitles);
	
	void initSubtitles();
	
	List<String> readSubtitled();

	List<String> getTranslatedSubtitled();

}
