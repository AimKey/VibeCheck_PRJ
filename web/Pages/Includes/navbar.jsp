<%-- 
    Document   : navbar
    Created on : Jul 2, 2024, 10:36:06?AM
    Author     : phamm
--%>

<header class="bg-primary">
    <nav class="navbar">
        <div class="navbar-brand rounded ms-2">
            <img src="${user.profilePicPath}" alt="userimg" width="32px"/>
            Hello, ${user.username}
        </div>
        <ul class="navbar-nav text-white flex-column">
            <li class="nav-item">
                <a class="nav-link" href="main"><i class="fa-solid fa-home"></i> Home</a>
            </li>

            <li class="nav-item">
                <a class="nav-link" href="#user"><i class="fa-solid fa-user"></i> User</a>
            </li>
            <c:if test="${user.isAdmin == true}">
                <li class="nav-item">
                    <a class="nav-link" href="#user-list"><i class="fa-solid fa-users"></i> User list</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#upload"><i class="fa-solid fa-upload"></i> Upload songs</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#edit-song"><i class="fa-solid fa-pen-to-square"></i> Edit songs</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#edit-playlist"><i class="fa-solid fa-sliders"></i> Edit playlist</a>
                </li>
            </c:if>
            <li class="nav-item" style="align-self: center;">
                <form action="LoginHandler" method="get" class="container-fluid justify-content-start">
                    <input type="submit" name="logout" value="Logout" class="btn btn-outline-danger me-2">
                </form>
            </li>
        </ul>
    </nav>
</header>
