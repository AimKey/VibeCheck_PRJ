<%-- 
    Document   : admin
    Created on : Jun 20, 2024, 4:39:02 PM
    Author     : phamm
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="errorpage.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link rel="stylesheet" href="assets/CSS/bootstrap.css" />
        <link rel="stylesheet" href="assets/CSS/admin-styles.css" />
        <script src="https://kit.fontawesome.com/ba8c3661b4.js" crossorigin="anonymous"></script>
        <title>Admin page</title>
    </head>
    <body>
        <div class="hidden-box"></div>
        <main class="main container-fluid">
            <div class="main__menus">
                <h1>Settings</h1>
                <%@include file="../Includes/navbar.jsp" %>
                
                <%@include file="../Includes/admin_user.jsp" %>

                <%@include file="../Includes/admin_user-list.jsp" %>

                <%@include file="../Includes/admin_upload.jsp" %>

                <%@include file="../Includes/admin_edit-song.jsp" %>

                <%@include file="../Includes/admin_edit-playlist.jsp" %>

                <!-- Modal -->
                <%@include file="../Includes/admin_add-modal.jsp" %>
            </div>
        </main>

        <!-- Scripts -->
        <script src="scripts/JS/bootstrap.bundle.js"></script>
        <script src="scripts/JS/admin.js"></script>
    </body>
</html>