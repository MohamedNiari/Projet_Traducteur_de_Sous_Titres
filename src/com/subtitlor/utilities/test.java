package com.subtitlor.utilities;

import java.io.File;
import java.io.IOException;

public class test {

	public static void main(String[] args) throws IOException {
		/*
		 * for testing purposes
		 */
	    File f = new File("WebContent/WEB-INF/password_presentation.srt");
	    System.out.println("Chemin absolu du fichier : " + f.getAbsolutePath());
	    System.out.println("Nom du fichier : " + f.getName());
	    System.out.println("Est-ce qu'il existe ? " + f.exists());
	    System.out.println("Est-ce un répertoire ? " + f.isDirectory());
	    System.out.println("Est-ce un fichier ? " + f.isFile());
		
		
	}

}
