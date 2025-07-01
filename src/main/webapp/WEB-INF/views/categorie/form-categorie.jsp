<%@ page import="java.util.ArrayList" %>
<%@ page import="com.Entite.Categorie" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    Categorie categorie = new Categorie(-1L, "");
    Categorie tempCategorie = (Categorie)request.getAttribute("categorie");
    if(tempCategorie != null) {
        categorie = tempCategorie;
    }
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="CSS/main.css">
    <title>Formulaire Categorie</title>
</head>
<body>
    <h1>Formulaire de categorie</h1>
    <!-- Formulaire qui redirige vers resultat.jsp -->
    <form action="create-categorie" method="post">
        <label for="libelle">Libelle :</label><br>
        <input type="text" name="libelle" value="<%= categorie.getLibelle() %>" required>
        <button type="submit">Envoyer</button>
    </form>
</body>
</html>
