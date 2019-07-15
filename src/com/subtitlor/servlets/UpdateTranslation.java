package com.subtitlor.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.subtitlor.bdd.MysqlRequest;
import com.subtitlor.utilities.SubtitlesHandler;

/**
 * Servlet implementation class UpdateTranslation
 */
@WebServlet("/UpdateTranslation")
public class UpdateTranslation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String FILE_NAME = "/WEB-INF/password_presentation.srt";
	private static final String TRANSLATED_FILE_NAME = "/WEB-INF/translated_password_presentation.srt";

	SubtitlesHandler subtitles = new SubtitlesHandler();
	Boolean recordedTranslation;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateTranslation() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		recordedTranslation = false;
		// System.out.println("recordedTranslation : " + recordedTranslation);

		ServletContext context = getServletContext();
		subtitles.readSubtitlesFile(context.getRealPath(FILE_NAME));

		MysqlRequest mysqlRequest = new MysqlRequest();
		List<String> listTranslatedText = mysqlRequest.readSubtitled();

		request.setAttribute("recordedTranslation", recordedTranslation);
		request.setAttribute("originalSubtitles", subtitles.getOriginalSubtitles());
		request.setAttribute("translatedSubtitles", listTranslatedText);

		this.getServletContext().getRequestDispatcher("/edit_subtitle.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ServletContext context = getServletContext();
		String[] texteTraduit = request.getParameterValues(("texteTraduit"));

		subtitles.createNewSubtitlesFile(context.getRealPath(FILE_NAME), context.getRealPath(TRANSLATED_FILE_NAME));
		System.out.println("\"Translated file\" located in " + context.getRealPath(TRANSLATED_FILE_NAME));

		subtitles.saveTranslatedSubtitles(texteTraduit, context.getRealPath(TRANSLATED_FILE_NAME));

		MysqlRequest mysqlRequest = new MysqlRequest();
		mysqlRequest.updateSubtitles(subtitles);

		subtitles.readSubtitlesFile(context.getRealPath(FILE_NAME));
		request.setAttribute("originalSubtitles", subtitles.getOriginalSubtitles());
		request.setAttribute("translatedSubtitles", subtitles.getTranslatedSubtitles());

		recordedTranslation = true;
		request.setAttribute("recordedTranslation", recordedTranslation);

		this.getServletContext().getRequestDispatcher("/edit_subtitle.jsp").forward(request, response);
	}

}
