<%-- 
    Document   : navbar
    Created on : Jul 2, 2024, 10:36:06?AM
    Author     : phamm
--%>

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
