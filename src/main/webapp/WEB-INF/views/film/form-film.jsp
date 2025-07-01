<%@ page import="java.util.ArrayList" %>
<%@ page import="com.Entite.Film" %>
<%@ page import="com.Entite.Categorie" %>
<%@ page import="java.util.List" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
// sdf
    Film film = new Film(-1L, "", -1f);
    Film tempFilm = (Film)request.getAttribute("film");
    if(tempFilm != null) {
        film = tempFilm;
    }
    List<Categorie> listeCategorie = (List<Categorie>)request.getAttribute("listeCategorie");
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="CSS/main.css">
    <title>Formulaire Film</title>
</head>
<body>
    <h1>Formulaire Film</h1>
    <!-- Formulaire qui redirige vers resultat.jsp -->
    <form action="create-film" method="post">
        <label for="nom">Nom :</label><br>
        <input type="text" name="nom" value="<%= film.getNom() %>" required>
        <label for="idCategorie">Categorie:</label><br>
        <% for(Categorie categorie : listeCategorie) { %>
            <label>
                <input type="checkbox" name="idCategorie" value="<%= categorie.getId() %>" 
                <% if(film.getId() != -1f && film.categorieInFilm(categorie)) out.println("checked");%>>
             <%= categorie.getLibelle() %>. </label><br>
            
        <% } %>
        <%-- Duree --%>
        <label for="duree">Duree:</label><br>
        <input type="number" name="duree" step="0.01" value="<% if(film.getDuree() != -1f) out.print(film.getDuree());%>">
        <%-- Id en cas de update --%>
        <% if(film.getId() != -1L) { %>
            <input type="hidden" name="id" value="<%= film.getId()%>">
        <% } %>
        <button type="submit">Envoyer</button>
        <a href="../">home</a>
        
    </form>
</body>
</html>
