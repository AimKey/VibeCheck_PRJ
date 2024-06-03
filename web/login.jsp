<%-- 
    Document   : login
    Created on : Jun 2, 2024, 6:05:57 PM
    Author     : phamm
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
        var status = request.getAttribute("r");
        if (status != null) {
            boolean s = (boolean) status;
            out.println("<h1>Login failed!</h1>");
            out.println("Status: " + s);
        }
        %>

        <h1>Login</h1>
        <form action="LoginHandler" method="get">
            <label for="user">Username</label>
            <input type="text" id="user" name="user">
            <br>
            <label for="pass">Password</label>
            <input type="password" id="pass" name="pass">
            <button type="submit"> Submit </button>
        </form>
    </body>
</html>
