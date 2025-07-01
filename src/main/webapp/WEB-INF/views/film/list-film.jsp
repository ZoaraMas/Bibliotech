<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> --%>
<%@ page import="com.Entite.Film" %>
<%@ page import="com.Utility.MyDate" %>
<%@ page import="java.util.List" %>
<% List<Film> films = (List<Film>) request.getAttribute("films"); %>
<html>
<head>
    <title>Liste Film</title>
</head>
<body>
    <h1>Film</h1>
    <table border=1>
        <thead>
            <tr>
                <th>Nom</th>
                <th>duree</th>
                <!-- Add other columns as needed -->
            </tr>
        </thead>
        <tbody>
        <% for(Film film : films) { %>
                <tr>
                    <td><a href="fiche-film?id=<%= film.getId() %>"><% out.println(film.getNom()); %></a> 
                    <a href="form-film?id=<%= film.getId() %>">Modifier</a>  <a href="read-film?id=<%= film.getId() %>">Supprimer</a>  
                    <a href="fiche-film?id=<%= film.getId() %>">Fiche</a><</td>
                    <td><% out.println("" + film.getDuree()); %></td>
                    <!-- Add other fields as needed -->
                </tr>
        <% } %>
        </tbody>
    </table>
        <a href="../">home</a>
</body>
</html>