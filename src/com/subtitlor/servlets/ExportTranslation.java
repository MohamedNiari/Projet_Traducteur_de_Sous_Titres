package com.subtitlor.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ExportTranslation
 */
@WebServlet("/ExportTranslation")
public class ExportTranslation extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ExportTranslation() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String filename = "translated_password_presentation.srt";
		String filepath = "/WEB-INF/";

		response.setContentType("APPLICATION/OCTET-STREAM");
		response.setHeader("Content-Disposition", "inline; filename=\"" + filename + "\"");

		// use inline if you want to view the content in browser, helpful for pdf file
		// response.setHeader("Content-Disposition","inline; filename=\"" + filename +
		// "\"");
		try {

			FileInputStream fileInputStream = new FileInputStream(
					new File(getServletContext().getRealPath(filepath + filename)));

			int i;
			while ((i = fileInputStream.read()) != -1) {
				out.write(i);
			}
			fileInputStream.close();
			out.close();

		} catch (FileNotFoundException e) {
			System.out.println("File to export not found");
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
