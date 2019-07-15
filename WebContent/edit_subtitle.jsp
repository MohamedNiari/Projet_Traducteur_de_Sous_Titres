<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Editer les sous-titres</title>
<link type="text/css" rel="stylesheet" href="css/style.css">
</head>
<body>

	<div id="header">
		<h1>Traducteur de sous titres</h1>
	</div>


	<div id="banniere">
		<div>
			<input type="button" value="Page d'accueil"
				onclick="window.location.href='EditSubtitle'; return false;"
				class="bouton" />
		</div>
		<div>
			<input type="button" value="Modifier la traduction"
				onclick="window.location.href='UpdateTranslation'; return false;"
				class="bouton" />
		</div>

		<div>
			<input type="button" value="Exporter la traduction"
				onclick="window.location.href='ExportTranslation'; return false;"
				class="bouton" />
		</div>

		<div>
			<input type="button" value="Nouveau fichier à traduire"
				onclick="window.location.href='UploadTranslation'; return false;"
				class="bouton" />
		</div>


	</div>


	<form method="post" action="EditSubtitle">
		<div id="liste">
			<div>
				<table>
					<c:set var="size" value="${fn:length(originalSubtitles)}" />
					<c:forEach var="i" begin="0" end="${size-1}">
						<tr>
							<td><c:out value="${ originalSubtitles[i] }" /></td>
							<c:if test="${recordedTranslation == false }">
								<td><input type="text" name="texteTraduit" size="35"
									value="${translatedSubtitles[i] }" /></td>
							</c:if>
							<c:if test="${recordedTranslation == true}">
								<td><input type="text" name="texteTraduit" size="35"
									value="${translatedSubtitles[i] }" disabled /></td>
							</c:if>
						</tr>
					</c:forEach>
				</table>
			</div>

			<div id="envoyer">
				<input type="submit" value="Envoyer" 
					onclick="return confirm('Etes-vous sûre de vouloir enregistrer la traduction ?');"
					class="bouton" />
			</div>
		</div>
	</form>


</body>
</html>