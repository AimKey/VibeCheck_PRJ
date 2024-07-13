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
        <script src="scripts/JS/jsmediatags.min.js"></script>
        <title>Setting page</title>
    </head>
    <body>
        <%--  Main screen --%>
        <main class="main">
            <%-- Vertical Navbar --%>
            <%@include file="/Pages/Includes/navbar.jsp" %>
            
            <div class="main__menus">
                <h1>Settings</h1>

                <%@include file="/Pages/Includes/admin_user.jsp" %>
                
                <c:if test="${user.isAdmin == true}">
                    <%@include file="/Pages/Includes/admin_user-list.jsp" %>

                    <%@include file="/Pages/Includes/admin_upload.jsp" %>

                    <%@include file="/Pages/Includes/admin_edit-song.jsp" %>
                </c:if>

                <%@include file="/Pages/Includes/admin_edit-playlist.jsp" %>

                <!-- Modal -->
                <%@include file="/Pages/Includes/admin_add-modal.jsp" %>
            </div>
        </main>

        <!-- Scripts -->
        <script src="scripts/JS/bootstrap.bundle.js"></script>
        <script src="scripts/JS/admin.js"></script>
        <script src="scripts/JS/uploadSongsHandler.js"></script>
        <script src="scripts/JS/searchbar.js"></script>
    </body>
</html>