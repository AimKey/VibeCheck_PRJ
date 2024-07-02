<%-- 
    Document   : user
    Created on : Jun 20, 2024, 8:49:11 PM
    Author     : phamm
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="section" id="user">
    <h3 class="user__title title green-text">Edit user profile</h3>
    <form class="user__form" action="UserServlet" method="post" enctype="multipart/form-data"> <!-- Enctype is VERY IMPORTANT -->
        <div class="user__avatar">
            <!-- On changed change user image too -->
            <div class="picture__container box-shadow">
                <img src="${user.profilePicPath}" alt="User picture here" />
                <label for="userImage"><i class="fa-solid fa-file-pen avatar__edit"></i></label>
                <input type="file" name="userImage" id="userImage" onchange="changeUserAvatar(this)"/>
            </div>
        </div>
        <!-- User update forms -->
        <div class="input">
            <label for="username">Username: </label>
            <input type="text" value="${user.username}" name="username" id="username" />
        </div>

        <div class="input">
            <label for="email">Email: </label>
            <input type="text" value="${user.email}" name="email" id="email" />
        </div>

        <div class="input">
            <label for="password">Change password: </label>
            <input
                type="password"
                name="password"
                id="password"
                placeholder="Enter new password"
                >
        </div>

        <div class="input">
            <label for="confirm-password">Confirm new password: </label>
            <input
                type="password"
                name="confirm-password"
                id="confirm-password"
                placeholder="Confirm new password"
                >
        </div>
        <!--This hidden form input is used for let the servlet know where to redirect-->
        <input type="hidden" name="url" value="admin">
        <input type="hidden" name="action" value="update">
        <!-- We may not need to use js here, plain old reload will work -->
        <button type="submit" class="button button-confirm">Confirm</button>
    </form>
</div>
