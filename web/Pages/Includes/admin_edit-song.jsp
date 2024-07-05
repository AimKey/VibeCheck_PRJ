<%-- 
    Document   : admin_edit-song
    Created on : Jun 20, 2024, 10:06:33 PM
    Author     : phamm
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="section" id="edit-song">
    <h3 class="title orange-text">Edit a song information</h3>
    <c:choose>
        <c:when test="${songs != null}">
            <h3>Select a song</h3>
            <!-- TODO: Query current songs, then implement search using js -->
            <div class="search-bar">
                <input class="search" type="text" />
                <i class="fa-solid fa-magnifying-glass"></i>
            </div>

            <div class="songs-display">
                <table class="songs-display__table">
                    <thead>
                        <tr>
                            <th scope="col" style="width: 5%">ID</th>
                            <th scope="col" style="width: 10%">Image</th>
                            <th scope="col" style="width: 25%">Name</th>
                            <th scope="col" style="width: 20%">Artist</th>
                            <th scope="col" style="width: 30%">Album</th>
                            <th scope="col" style="width: 5%"><i class="fa-regular fa-clock"></i></th>
                            <th scope="col" style="width: 5%"></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${songs}" var="s">
                            <!-- Remind to self, change how this behave, maybe add a little edit button instead -->
                            <!-- TODO: When user select the song, OPEN and DISPLAY it in 
                        the detail panel below,  -->
                            <tr class="song-selection edit-song__selection">
                                <th class="song-id">${s.songId}</th>
                                <td class="song-img">
                                    <img src="${s.songImagePath}" alt="${s.title}" />
                                </td>
                                <td class="song-title">${s.title}</td>
                                <td class="song-artist">${s.artist}</td>
                                <td class="song-album">${s.album}</td>
                                <td class="song-duration">${s.duration}</td>
                                <td class="song-delete">
                                    <!-- TODO: On click, query and delete song -->
                                    <!-- IMPORTANT: use JS to send a request to server without redirecting user.
                                      Then remove this element from the DOM -->
                                    <button class="edit-song__rmv-btn" data-songId=${s.songId}>
                                        <i class="fa-solid fa-trash-can"></i>
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <h3 class="title">Edit this song information</h3>

            <!-- No need for js here because we are going to reload the page -->
            <!-- Or not... -->
            <form class="songs-display edit-song__detail" action="SongServlet" method="post" enctype="multipart/form-data">
                <div class="picture__container edit__selected-song-picture">
                    <img src="assets/images/demo.jpg" alt="User picture here" class="thumbnail"/>
                    <div class="avatar__edit">
                        <label for="songImg"> <i class="fa-solid fa-file-pen"></i></label>
                        <input type="file" name="songImg" id="songImg" class="user-upload-img"/>
                    </div>
                </div>
                <div class="songs-display__info">
                    <div class="input">
                        <input type="hidden" name="songId" id="songId" placeholder="Song ID here"/>
                    </div>
                    <div class="input">
                        <label for="name">Name</label>
                        <input type="text" name="title" id="title" placeholder="Enter new name here" />
                    </div>
                    <div class="input">
                        <label for="artist">Artist</label>
                        <input type="text" name="artist" id="artist" placeholder="Enter new artist here" />
                    </div>
                    <div class="input">
                        <label for="album">Album</label>
                        <input type="text" name="album" id="album" placeholder="Enter new album here" />
                    </div>
                    <input type="hidden" name="action" value="updateSong">
                </div>
            </form>
            <button class="button button-confirm edit-song__btn">Save changes</button>
        </c:when>
        <c:when test="${songs == null}">
            <h3>System songs is empty, go and add some !</h3>
        </c:when>
    </c:choose>

</div>