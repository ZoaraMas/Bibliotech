<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> --%>
<%@ page import="com.Entite.Categorie" %>
<%@ page import="java.util.List" %>
<% List<Categorie> categories = (List<Categorie>) request.getAttribute("categories"); %>
<html>
<head>
    <title>Categorie</title>
</head>
<body>
    <h1>Categorie</h1>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Libelle</th>
                <!-- Add other columns as needed -->
            </tr>
        </thead>
        <tbody>
        <% for(Categorie categorie : categories) { %>
                <tr>
                    <td><% out.println(categorie.getId()); %></td>
                    <td><% out.println(categorie.getLibelle()); %></td>
                    <!-- Add other fields as needed -->
                </tr>
        <% } %>
        </tbody>
    </table>
</body>
</html>