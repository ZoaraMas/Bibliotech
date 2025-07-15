<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>WELCOME, please login</title>
</head>
<body>
    <% 
    // Check for error messages from request attributes (from controller)
    if(request.getAttribute("error") != null) { 
    %>
        <p style="color: red;"><%=  request.getAttribute("error") %></p>
    <% } %>
    
    <% 
    // Check for error messages from URL parameters (from filter)
    String errorParam = request.getParameter("error");
    if(errorParam != null) { 
        String errorMessage = "";
        switch(errorParam) {
            case "not_authenticated":
                errorMessage = "Please log in to access this page.";
                break;
            case "insufficient_privileges":
                errorMessage = "You don't have sufficient privileges to access this page. Admin access required.";
                break;
            case "user_not_found":
                errorMessage = "User session is invalid. Please log in again.";
                break;
            case "login_failed":
                errorMessage = "Invalid username or password.";
                break;
            default:
                errorMessage = "An error occurred. Please try again. " + errorParam;
        }
    %>
        <p style="color: red;"><%= errorMessage %></p>
    <% } %>
    
    <form action="login" method="post">
        <input type="text" name="user" value="admin" placeholder="mail">
        <input type="password" name="password" value="" placeholder="password">
        <input type="submit">
    </form>
</body>
</html>