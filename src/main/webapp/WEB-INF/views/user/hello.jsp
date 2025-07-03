<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>WELCOME, please login</title>
</head>
<body>
    <% if(request.getAttribute("error") != null) { %>
        <p> <%=  request.getAttribute("error") %></p>
    <% } %>
    <h1> HELLO </h1>
   <form action="login" method="post">
        <input type="text" name="user" value="admin" placeholder="mail">
        <input type="password" name="password" value="" placeholder="password">
        <input type="submit">
   </form>
</body>
</html>