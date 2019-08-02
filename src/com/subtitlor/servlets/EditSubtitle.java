package com.subtitlor.servlets;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.subtitlor.dao.DaoFactory;
import com.subtitlor.dao.DaoRequestSQL;
import com.subtitlor.utilities.SubtitlesHandler;

@WebServlet("/EditSubtitle")
public class EditSubtitle extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static String FILE_NAME = "/WEB-INF/password_presentation.srt";
	private static String TRANSLATED_FILE_NAME = "/WEB-INF/translated_password_presentation.srt";

	SubtitlesHandler subtitles = new SubtitlesHandler();
	Boolean recordedTranslation;
	
    private DaoRequestSQL daoRequestSQL;

    public void init() throws ServletException {    	
        DaoFactory daoFactory = DaoFactory.getInstance(); 
        this.daoRequestSQL = daoFactory.getDaoRequest();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		recordedTranslation = false;
		
		ServletContext context = getServletContext();
		System.out.println("Chemin du fichier à traduire : " + context.getRealPath(FILE_NAME));
		System.out.println("Le working path est : " + new File(".").getAbsoluteFile());
		
		subtitles.readSubtitlesFile(context.getRealPath(FILE_NAME));

		request.setAttribute("recordedTranslation", recordedTranslation);
		request.setAttribute("originalSubtitles", subtitles.getOriginalSubtitles());

		this.getServletContext().getRequestDispatcher("/edit_subtitle.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		recordedTranslation = true;
		
		ServletContext context = getServletContext();
		String[] texteTraduit = request.getParameterValues(("texteTraduit"));

		subtitles.createNewSubtitlesFile(context.getRealPath(FILE_NAME), context.getRealPath(TRANSLATED_FILE_NAME));
		System.out.println("Chemin du fichier traduit : " + context.getRealPath(TRANSLATED_FILE_NAME));
		
		subtitles.readSubtitlesFile(context.getRealPath(FILE_NAME));
		subtitles.saveTranslatedSubtitles(texteTraduit, context.getRealPath(TRANSLATED_FILE_NAME));

		daoRequestSQL.initSubtitles();
		daoRequestSQL.saveSubtitles(subtitles);

		request.setAttribute("originalSubtitles", subtitles.getOriginalSubtitles());
		request.setAttribute("translatedSubtitles", subtitles.getTranslatedSubtitles());
		request.setAttribute("recordedTranslation", recordedTranslation);

		this.getServletContext().getRequestDispatcher("/edit_subtitle.jsp").forward(request, response);

	}

}
