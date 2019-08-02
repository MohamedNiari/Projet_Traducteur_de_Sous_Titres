package com.subtitlor.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.subtitlor.beans.Subtitles;
import com.subtitlor.utilities.SubtitlesHandler;

public class DaoRequestSQLImplementation implements DaoRequestSQL {
	private DaoFactory daoFactory;

	DaoRequestSQLImplementation(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	public List<String> readSubtitled() {

		List<String> translatedSubtitles = new ArrayList<String>();

		Connection connexion = null;
		Statement statement = null;
		ResultSet resultat = null;

		try {
			connexion = daoFactory.getConnection();
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

	public void saveSubtitles(SubtitlesHandler subtitles) {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			connexion = daoFactory.getConnection();
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

		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			connexion = daoFactory.getConnection();
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

	public List<String> getTranslatedSubtitled() {

		List<String> translatedSubtitles = new ArrayList<String>();
		Connection connexion = null;
		Statement statement = null;
		ResultSet resultat = null;

		try {
			connexion = daoFactory.getConnection();
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

		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			connexion = daoFactory.getConnection();

			preparedStatement = connexion.prepareStatement("delete FROM translation.texte");

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}