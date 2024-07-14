<%-- 
    Document   : login
    Created on : Jun 21, 2024, 12:04:04 AM
    Author     : phamm
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="errorpage.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>LoginPage</title>
        <link rel="stylesheet" href="assets/CSS/bootstrap.css" />
        <link rel="stylesheet" href="assets/CSS/login.css" />
    </head>
    <body>
        <header>
            <h2 class="logo">Logo</h2>
            <nav class="navigation">
<!--                <a href="#">Home</a>
                <a href="#">About</a>
                <a href="#">Service</a>
                <a href="#">Contact</a>-->
                <button class="btnLogin-popup">Login</button>
            </nav>
        </header>

        <div class="wrapper hide">
            <div class="iconClose">
                <span class="icon-close">
                    <ion-icon name="close-outline"></ion-icon>
                </span>
            </div>
            <div class="form-box login">
                <h2>Login</h2>
                <form action="LoginHandler" method="post">
                    <div class="input-box">
                        <span class="icon">
                            <ion-icon name="person-outline"></ion-icon>
                        </span>
                        <input type="text" name="username" required value="Minhkiet"/>
                        <label>Username</label>
                    </div>
                    <div class="input-box">
                        <span class="icon">
                            <ion-icon name="lock-closed-outline"></ion-icon>
                        </span>
                        <input type="password" name="pass" required value="123456"/>
                        <label>Password</label>
                    </div>
                    <div class="forgot">
                        <a href="#">Forgot Password?</a>
                    </div>
                    <button type="submit" class="btn-submit">Login</button>
                </form>
                <div class="signup">
                    <p>
                        Don't have an account?
                        <a href="#" class="signup-link">Sign Up</a>
                    </p>
                </div>
            </div>

            <div class="form-box signup">
                <h2>Sign Up</h2>
                <form action="RegisterServlet" method="post">
                    <div class="input-box">
                        <span class="icon">
                            <ion-icon name="person-outline"></ion-icon>
                        </span>
                        <input type="text" name="username" required value="Huydaucac"/>
                        <label>Username</label>
                    </div>
                    <div class="input-box">
                        <span class="icon">
                            <ion-icon name="lock-closed-outline"></ion-icon>
                        </span>
                        <input type="password" name="password" required value="123456"/>
                        <label>Password</label>
                    </div>
                    <div class="input-box">
                        <span class="icon">
                            <ion-icon name="mail-outline"></ion-icon>
                        </span>
                        <input type="text" name="email" required />
                        <label>Email</label>
                    </div>
                    <button type="submit" class="btn-submit">Sign Up</button>
                    <div class="signup">
                        <p>
                            Already have an account?
                            <a href="#" class="login-link">Log in</a>
                        </p>
                    </div>
                </form>
                <c:if test="${param.status == 'fail'}">
                    <p style="color:red;">Registration failed. Please try again.</p>
                </c:if>
            </div>
        </div>

        <div id="video-container">
            <video id="video-bg" preload="auto" autoplay="true" loop="loop" muted="muted" volume="0">
                <source src="assets/images/login.mp4" type="video/mp4">
                Sorry, your browser does not support HTML5 video.
            </video>
        </div>

        

        <script src="scripts/JS/JS_LogIn.js"></script>
        <script src="scripts/JS/bootstrap.js"></script>
        <script
            type="module"
            src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"
        ></script>
        <script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
    </body>
</html>
