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
<a href="pret/form-pret">Preter un livre</a>
<a href="remise-livre/form-remise">Remettre un livre</a>
<a href="reservation/form-reservation">Demander a reserver un livre</a>
<a href="etat-reservation/form-etat">Valider ou Annuler une reservation</a>
</body>
</html>
