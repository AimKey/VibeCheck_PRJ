<%-- 
    Document   : admin_edit-song
    Created on : Jun 20, 2024, 10:06:33 PM
    Author     : phamm
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="section" id="edit-song">
    <h3 class="title orange-text">Edit a song information</h3>
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
                    <th scope="col" style="width: 10%"></th>
                    <th scope="col" style="width: 30%">Name</th>
                    <th scope="col" style="width: 20%">Artist</th>
                    <th scope="col" style="width: 30%">Album</th>
                    <th scope="col" style="width: 5%"><i class="fa-regular fa-clock"></i></th>
                    <th scope="col" style="width: 5%"></th>
                </tr>
            </thead>
            <tbody>
                <!-- TODO: When user select the song, OPEN and DISPLAY it in 
                the detail panel below,  -->
                <tr class="song-selection">
                    <th class="song-img">
                        <img src="assets/images/demo.jpg" alt="User picture here" />
                    </th>
                    <td class="song-title">
                        フォニイ (feat. 宵崎奏&朝比奈まふゆ&東雲絵名&暁山瑞希&MEIKO)
                    </td>
                    <td class="song-artist">Shibayan records</td>
                    <td class="song-album">
                        フォニイ (feat. 宵崎奏&朝比奈まふゆ&東雲絵名&暁山瑞希&MEIKO)
                    </td>
                    <td class="song-duration">4:12</td>
                    <td class="song-delete">
                        <!-- TODO: On click, query and delete song -->
                        <!-- IMPORTANT: use JS to send a request to server without redirecting user.
                          Then remove this element from the DOM -->
                        <button onclick="handleDeleteSong(this)">
                            <i class="fa-solid fa-trash-can"></i>
                        </button>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>

    <h3 class="title">Edit this song information</h3>

    <!-- No need for js here because we are going to reload the page -->
    <!-- Or not... -->
    <form class="songs-display detail editSongForm" action="#" method="post">
        <div class="picture__container song-img-container">
            <img src="assets/images/demo.jpg" alt="User picture here" />
            <div class="avatar__edit">
                <label for="songImg"> <i class="fa-solid fa-file-pen"></i></label>
                <input type="file" name="songImg" id="songImg" />
            </div>
        </div>
        <div class="songs-display__info">
            <div class="input">
                <label for="name">Name</label>
                <input type="text" name="name" id="name" placeholder="Enter new name here" />
            </div>
            <div class="input">
                <label for="artist">Artist</label>
                <input type="text" name="artist" id="artist" placeholder="Enter new artist here" />
            </div>
            <div class="input">
                <label for="album">Album</label>
                <input type="text" name="album" id="album" placeholder="Enter new album here" />
            </div>
        </div>
    </form>
    <!-- TODO: Fire the form above -->
    <button class="button button-confirm" onclick="handleEditForm(this)">Save changes</button>
</div>