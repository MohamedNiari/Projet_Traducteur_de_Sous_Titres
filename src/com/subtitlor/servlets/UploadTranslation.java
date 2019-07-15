package com.subtitlor.servlets;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.subtitlor.utilities.SubtitlesHandler;

/**
 * Servlet implementation class UploadTranslation
 */
@MultipartConfig
@WebServlet("/UploadTranslation")
public class UploadTranslation extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	public static final int TAILLE_TAMPON = 10240;
	private static final String FILE_NAME = "/WEB-INF/password_presentation.srt";
	public static final String CHEMIN_FICHIERS = "/WEB-INF/"; // A changer

	// private static final String FILE_NAME =
	// "C:/Users/DELL/Downloads/subtitlor/WebContent/WEB-INF/password_presentation.srt";
	// public static final String CHEMIN_FICHIERS =
	// "C:/Users/DELL/Downloads/subtitlor/WebContent/WEB-INF/"; // A changer

	SubtitlesHandler subtitles = new SubtitlesHandler();
	Boolean recordedTranslation = false;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadTranslation() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		this.getServletContext().getRequestDispatcher("/uploadFile.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
				
		ServletContext context = getServletContext();

		File oldFileToDelete = new File(context.getRealPath(FILE_NAME));
		oldFileToDelete.delete();

		// On récupère le champ du fichier
		Part part = request.getPart("fichier");

		// On vérifie qu'on a bien reçu un fichier
		String nomFichier = getNomFichier(part);

		// Si on a bien un fichier
		if (nomFichier != null && !nomFichier.isEmpty()) {
			String nomChamp = part.getName();
			// Corrige un bug du fonctionnement d'Internet Explorer
			nomFichier = nomFichier.substring(nomFichier.lastIndexOf('/') + 1)
					.substring(nomFichier.lastIndexOf('\\') + 1);

			// On écrit définitivement le fichier sur le disque

			writeFichier(part, nomFichier, context.getRealPath(CHEMIN_FICHIERS));

			File oldName = new File(context.getRealPath(CHEMIN_FICHIERS) + nomFichier);
			File newName = new File(context.getRealPath(FILE_NAME));

			renameFichier(oldName, newName);

			request.setAttribute(nomChamp, nomFichier);
		}

		subtitles.readSubtitlesFile(context.getRealPath(FILE_NAME));

		request.setAttribute("recordedTranslation", recordedTranslation);
		request.setAttribute("originalSubtitles", subtitles.getOriginalSubtitles());

		this.getServletContext().getRequestDispatcher("/edit_subtitle.jsp").forward(request, response);

	}

	private void writeFichier(Part part, String nomFichier, String chemin) throws IOException {
		BufferedInputStream entree = null;
		BufferedOutputStream sortie = null;
		try {
			entree = new BufferedInputStream(part.getInputStream(), TAILLE_TAMPON);
			sortie = new BufferedOutputStream(new FileOutputStream(new File(chemin + nomFichier)), TAILLE_TAMPON);

			byte[] tampon = new byte[TAILLE_TAMPON];
			int longueur;
			while ((longueur = entree.read(tampon)) > 0) {
				sortie.write(tampon, 0, longueur);
			}
		} finally {
			try {
				sortie.close();
			} catch (IOException ignore) {
			}
			try {
				entree.close();
			} catch (IOException ignore) {
			}
		}
	}

	private static String getNomFichier(Part part) {
		for (String contentDisposition : part.getHeader("content-disposition").split(";")) {
			if (contentDisposition.trim().startsWith("filename")) {
				return contentDisposition.substring(contentDisposition.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}

	private void renameFichier(File oldFile, File newFile) {

		if (oldFile.exists()) {
			oldFile.renameTo(newFile);
			System.out.println("File rename completed....");
		} else {
			System.out.println("Original file not exist for renaming....");
		}

	}

}
