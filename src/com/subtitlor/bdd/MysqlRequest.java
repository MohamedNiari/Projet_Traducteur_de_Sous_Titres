package com.subtitlor.bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.subtitlor.utilities.SubtitlesHandler;

public class MysqlRequest {
	private Connection connexion;
	private PreparedStatement preparedStatement;

	public void saveSubtitles(SubtitlesHandler subtitles) {

		connexion = null;
		preparedStatement = null;

		try {
			loadDatabase();
			for (int i = 0; i < subtitles.getOriginalSubtitles().size(); i++) {

				preparedStatement = connexion
						.prepareStatement("INSERT INTO translation.texte(texteATraduire, texteTraduit) VALUES(?, ?);");

				preparedStatement.setString(1, subtitles.getOriginalSubtitles().get(i));
				preparedStatement.setString(2, subtitles.getTranslatedSubtitles().get(i));

				preparedStatement.executeUpdate();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void updateSubtitles(SubtitlesHandler subtitles) {

		connexion = null;
		preparedStatement = null;

		try {
			loadDatabase();
			for (int i = 0; i < subtitles.getOriginalSubtitles().size(); i++) {

				preparedStatement = connexion
						.prepareStatement("UPDATE translation.texte set texteTraduit=? WHERE texteATraduire=?");

				preparedStatement.setString(1, subtitles.getTranslatedSubtitles().get(i));
				preparedStatement.setString(2, subtitles.getOriginalSubtitles().get(i));

				preparedStatement.executeUpdate();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public List<String> readSubtitled() {

		List<String> translatedSubtitles = new ArrayList<String>();

		connexion = null;
		preparedStatement = null;
		Statement statement = null;
		ResultSet resultat = null;

		try {
			loadDatabase();
			statement = connexion.createStatement();
			resultat = statement.executeQuery("SELECT `texteTraduit` FROM translation.texte;");

			while (resultat.next()) {
				String textTranslated = resultat.getString("texteTraduit");

				translatedSubtitles.add(textTranslated);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return translatedSubtitles;
	}

	public void initSubtitles() {

		connexion = null;
		preparedStatement = null;

		try {
			loadDatabase();

			preparedStatement = connexion.prepareStatement("delete FROM translation.texte");

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void loadDatabase() {

		try {              
			Class.forName("com.mysql.cj.jdbc.Driver");
			connexion = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/translation?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"hbstudent", "hbstudent");
			
			if (connexion != null) {
				System.out.println("Connected to the database");
			}
		} catch (ClassNotFoundException e) {
			System.out.println("issue driver mysql");

		} catch (SQLException e) {
			System.out.println("issue connecting to the database");
			e.printStackTrace();
		}
	}


}
