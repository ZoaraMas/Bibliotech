<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> --%>
<%@ page import="com.Entite.FilmCategorie" %>
<%@ page import="com.Entite.Film" %>
<%@ page import="com.Utility.MyDate" %>
<%@ page import="java.util.List" %>
<% Film film = (Film) request.getAttribute("film"); %>
<html>
<head>
    <title>Liste Film</title>
</head>
<body>
    <h1><%= film.getNom() %></h1>
    <h2>CATEGORIES:</h2>
    <ul>
        <% for(FilmCategorie filmCategorie : film.getFilmCategories()) { %>
            <li><%= filmCategorie.getCategorie().getLibelle() %></li>
        <% } %>
    </ul>
        <a href="../">home</a>
</body>
</html>