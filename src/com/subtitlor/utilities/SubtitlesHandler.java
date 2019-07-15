package com.subtitlor.utilities;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SubtitlesHandler {
	private List<String> originalSubtitles;
	private List<String> translatedSubtitles;

	public void readSubtitlesFile(String fileName) {

		originalSubtitles = new ArrayList<String>();

		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

			originalSubtitles = stream.filter(line -> !line.matches("^[^a-z]+") && !line.isEmpty())
					.collect(Collectors.toList());

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	public void saveTranslatedSubtitles(String[] texteTraduit, String newFilename) throws IOException {

		translatedSubtitles = new ArrayList<String>();
		Path TO = Paths.get(newFilename);

		for (String text : texteTraduit) {
			translatedSubtitles.add(text);
		}

		
		for (int i = 0; i < texteTraduit.length; i++) {

			String boucleOriginalLines = originalSubtitles.get(i);
			String boucleTranslatedLines = translatedSubtitles.get(i);

			Stream<String> lines = Files.lines(TO);
			List<String> replaced = lines.map(line -> line.replace(boucleOriginalLines, boucleTranslatedLines))
					.collect(Collectors.toList());

			Files.write(TO, replaced);
			lines.close();

		}

	}

	public void createNewSubtitlesFile(String originalFilename, String newFilename) throws IOException {

		Path FROM = Paths.get(originalFilename);
		Path TO = Paths.get(newFilename);
		// overwrite the destination file if it exists, and copy
		// the file attributes, including the rwx permissions

		CopyOption[] options = new CopyOption[] { StandardCopyOption.REPLACE_EXISTING,
				StandardCopyOption.COPY_ATTRIBUTES };
		Files.copy(FROM, TO, options);

	}


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
