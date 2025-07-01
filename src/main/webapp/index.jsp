<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<html>
<body>

<h1>Login:</h1>
<a href="user/form-login">LOGIN</a>
<% 
    if(session.getAttribute("auth") != null)  { %>
        <h1>unlog:</h1>
        <a href="user/unlog">UNLOG</a>
    <% }
%>
<h2> LIVRES</h2>
<a href="pret/demander-pret">Demander un pret</a>
</body>
</html>
