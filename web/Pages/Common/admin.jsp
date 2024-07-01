<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%-- 
    Document   : admin
    Created on : Jun 20, 2024, 4:39:02 PM
    Author     : phamm
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <header class="container-fluid bg-primary fixed-top">
            <nav class="navbar navbar-expand-lg navbar-expand-md justify-content-between">
                <div class="navbar-brand rounded ms-2">
                    <img src="${user.profilePicPath}" alt="userimg" width="32px"/>
                    Hello, ${user.username}
                </div>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <ul class="navbar-nav text-white">
                    <li class="nav-item">
                        <a class="nav-link" href="#user">User</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#user-list">User list</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#upload">Upload songs</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#edit-song">Edit songs</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#edit-playlist">Edit playlist</a>
                    </li>
                    <li class="nav-item">
                        <form action="LoginHandler" method="get" class="container-fluid justify-content-start">
                            <input type="button" name="logout" value="Logout" class="btn btn-outline-danger me-2">
                        </form>
                    </li>
                </ul>
            </nav>
        </header>
        <main class="main container-fluid">
            <div class="main__menus">
                <h1>Settings</h1>

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