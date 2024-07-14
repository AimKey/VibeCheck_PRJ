<%-- 
    Document   : errorpage
    Created on : Jun 20, 2024, 8:51:26 PM
    Author     : phamm
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" isErrorPage="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Congrats, you bricked this page !</h1>
        <c:if test="${exception != null}">
            <h3>Here is the exception ${exception}</h3>
        </c:if>
        <c:if test="${errorMsg != null}">
            <h3>Here is error msg: ${errorMsg}</h3>
        </c:if>
    </body>
</html>
