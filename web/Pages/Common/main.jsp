<%@page contentType="text/html" pageEncoding="UTF-8" import="java.util.ArrayList, Model.Song, Model.Daos.SongDao" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>MainPage</title>
        <link rel ="stylesheet" href = "assets/CSS/bootstrap.css">
        <link rel="stylesheet" href="assets/CSS/main.css">
    </head>

    <body>
        <div class="container-fluid main">
            <div class="row main__upper">
                <div class="col-1">
                    <div class="col-9">
                        <div class="long-bar">
                            <a href="settings#user">
                                <img src="${user.profilePicPath}" alt="demo" class="logo">
                            </a>
                            <c:if test="${user.isAdmin == true}">
                                <a href="settings#user-list">
                                    <span class="icon">
                                        <ion-icon name="settings-outline"></ion-icon>
                                    </span>
                                </a>
                                <a href="settings#upload">
                                    <span class="icon">
                                        <ion-icon name="cloud-upload-outline"></ion-icon>
                                    </span>
                                </a>
                                <a href="settings#edit-song">
                                    <span class="icon">
                                        <ion-icon name="musical-notes-outline"></ion-icon>
                                    </span>
                                </a>
                            </c:if>
                            <a href="settings#edit-playlist">
                                <span class="icon">
                                    <svg width="30px" height="30px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" stroke="#eeeeee"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round" stroke="#CCCCCC" stroke-width="0.192"></g><g id="SVGRepo_iconCarrier"> <path d="M18 18H20M22 18H20M20 18V16M20 18V20" stroke="<svg viewBox=" 0="" 24="" 24"="" fill="none" xmlns="http://www.w3.org/2000/svg"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <path d="M18 18H20M22 18H20M20 18V16M20 18V20" stroke="" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"></path> <path d="M2 11L20 11" stroke="" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"></path> <path d="M2 17L14 17" stroke="" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"></path> <path d="M2 5L20 5" stroke="" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"></path> </g>" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"&gt;</path> <path d="M2 11L20 11" stroke="<svg viewBox=" 0="" 24="" 24"="" fill="none" xmlns="http://www.w3.org/2000/svg"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <path d="M18 18H20M22 18H20M20 18V16M20 18V20" stroke="" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"></path> <path d="M2 11L20 11" stroke="" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"></path> <path d="M2 17L14 17" stroke="" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"></path> <path d="M2 5L20 5" stroke="" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"></path> </g>" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"&gt;</path> <path d="M2 17L14 17" stroke="<svg viewBox=" 0="" 24="" 24"="" fill="none" xmlns="http://www.w3.org/2000/svg"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <path d="M18 18H20M22 18H20M20 18V16M20 18V20" stroke="" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"></path> <path d="M2 11L20 11" stroke="" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"></path> <path d="M2 17L14 17" stroke="" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"></path> <path d="M2 5L20 5" stroke="" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"></path> </g>" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"&gt;</path> <path d="M2 5L20 5" stroke="<svg viewBox=" 0="" 24="" 24"="" fill="none" xmlns="http://www.w3.org/2000/svg"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <path d="M18 18H20M22 18H20M20 18V16M20 18V20" stroke="" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"></path> <path d="M2 11L20 11" stroke="" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"></path> <path d="M2 17L14 17" stroke="" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"></path> <path d="M2 5L20 5" stroke="" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"></path> </g>" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"&gt;</path> </g></svg>
                                </span>
                            </a>
                            <a href="LoginHandler?">
                                <span class="icon" style="color: #bf616a;">
                                    <ion-icon name="log-out-outline"></ion-icon>
                                </span>
                            </a>
                        </div>
                    </div>
                </div>
                <!------------------------------------------------------------------------------------------->
                <div class="col main__middle">
                    <div class="search-bar" id="main__middle__searchbar">
                        <span class="search-icon">
                            <ion-icon name="search-outline"></ion-icon>
                        </span>
                        <input type="text" class="search-input" placeholder="What are you looking for ~">
                    </div>

                    <div class="select-playlist__wrapper">
                        <select
                            class="form-select playlist__select"
                            name="selectPlaylist"
                            id="selectPlaylist"
                            data-bs-toggle="tooltip"
                            data-bs-placement="top"
                            title="Choose a playlist"
                            >
                            <!-- 0 equals system! -->
                            <option value="0">System</option> 
                            <c:forEach var="p" items="${playlists}">
                                <option value="${p.playlistId}">${p.name}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div id="playlist__info">
                        <h3 class="blue-text">${curPlaylist.name}</h3>
                        <p class="sub-text">(${curPlaylist.numberOfSongs}) songs</p>
                        <span class="icon">
                            <ion-icon name="play-circle-outline"></ion-icon>
                        </span>
                    </div>

                    <div class="text-container">
                        <p class="text2">Details</p>
                        <p class="text3">Album</p>
                        <p class="text4">Duration</p>
                    </div>

                    <div class="List-Song">
                            <c:forEach var="s" items="${songs}">
                            <div class="song" >
                                <div class="song-detailed">
                                    <img src="${s.songImagePath}" alt="demo" class="img-fluid">
                                    <div class="song-info">
                                        <h2 class="song-title">${s.title}</h2>
                                        <p class="artist-name">${s.artist}</p>
                                        <input type="hidden" name="${s.songId}" 
                                                                      value ="${s.songFilePath}" 
                                                                       id="srcsong" >
                                    </div>
                                </div>
                                <div class="info">
                                    <p class="album">${s.album}</p>
                                    <p class="duration">${s.duration}</p>
                                </div>
                            </div>
                        </c:forEach>
                    </div>

                    <audio id="song" src="">

                    </audio> 

                </div>

                <!------------------------------------------------------------------------------------------->
                <div class="col-3 main__side-info">
                    <div class="infomusic" id="infomusic" >
                        <div class="music">
                            <div class="music__info-wrapper">
                                <img src="" id="bigImg" alt="demo" >
                                <div class="music-info">
                                    <h4 class="music-title">Song Title</h4>
                                    <p class="artist-name">Artist Name</p>
                                </div>
                                <div class="album-right">
                                    <h4 class="music-album">Album</h4>
                                    <p class="name-album"></p>
                                </div>
                            </div>

                            <div class="allnext">   
                                <h5 class="up-next">Up Next:</h5>
                                <div class="songnext">
                                    <div class="song-detailed">
                                        <img src="assets/images/demo1.jpg" alt="demo" id="midImg">
                                        <div class="song-info">
                                            <h2 class="next-song-title">Song Title</h2>
                                            <p class="next-artist-name">Artist Name</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="queue" id="queue" style="display: none;">
                        <h3>Queue</h3>
                        <div class="List-Song" id="queueList">
                            <div class="song">
                                <img src="" alt="Song Image">
                                <div class="song-info">
                                    <h2 class="song-title"></h2>
                                    <p class="artist-name"></p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                
                <!------------------------------------------------------------------------------------------->
            </div>
            <div class="row2">
                <div class="control-bar">
                    <div class="col">
                        <div class="song-play">
                            <img src="assets/images/demo.jpg" alt="demo" id="smallImg">
                            <div class="song-info bottom">
                                <h4 class="music-title">Song Title</h4>
                                <p class="artist-name">Artist Name</p>
                            </div>
                        </div>
                    </div>

                    <div class="col">
                        <div class="control">
                            <div class="allicon">
                                <span class="icon active" id="shuffBtn">
                                    <ion-icon name="shuffle-outline"></ion-icon>
                                </span>
                                <span class="icon active" id="prevBtn">
                                    <ion-icon name="play-skip-back-outline" ></ion-icon>
                                </span>
                                <button class="icon-play" id="ctrlIcon">
                                    <ion-icon name="play" ></ion-icon>
                                </button>
                                <span class="icon active" id="nextBtn" >
                                    <ion-icon name="play-skip-forward-outline"></ion-icon>
                                </span>
                                <span class="icon active" id="reBtn">
                                    <ion-icon name="repeat-outline"></ion-icon>
                                </span>
                            </div>

                            <div class="progress-bar">
                                <span class="current-time"> </span>
                                <input type="range" class="progress" value="0" id="progress">
                                <span class="total-time"> </span>
                            </div>
                        </div>
                    </div>

                    <div class="col">
                        <div class="volume">
                            <span class="icon active" id="listBtn">
                                <ion-icon name="list-outline"></ion-icon>
                            </span>
                            <span class="icon">
                                <ion-icon name="volume-low-outline"></ion-icon>
                            </span>
                            <input type="range" class="volume" value="100" id="volume">
                            </span>
                        </div>
                    </div>
                </div>
            </div>

        </div>
        <script type="module" src="scripts/JS/JS_Main.js"></script> 
        <!-- <script src="scripts/JS/JS_Main/test.js"></script> -->
        <script src="scripts/JS/bootstrap.js"></script>

        <script src="https://kit.fontawesome.com/ba8c3661b4.js" crossorigin="anonymous"></script>
        <script 
            type="module" 
            src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js">
        </script>
        <script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
        <script src="scripts/JS/searchbar.js"></script>
    </body>

</html>