<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Choix d'un nouveau fichier</title>
</head>
<body>
    <c:if test="${ !empty fichier }">
    <p><c:out value="Le fichier ${ fichier } a été téléchargé !" />
    </p>
    </c:if>
    
    <form method="post" enctype="multipart/form-data">
        <p>
            <label for="fichier">Choix du nouveau fichier : </label>
            <input type="file" name="fichier" id="fichier" title=" " />
        </p>
        
        <input type="submit" value="Envoyer" />
    </form>
    
</body>
</html>