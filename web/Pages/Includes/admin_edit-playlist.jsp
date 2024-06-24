<%-- 
    Document   : admin_edit-playlist
    Created on : Jun 20, 2024, 10:18:04 PM
    Author     : phamm
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="section" id="edit-playlist">
    <h3 class="title orange-text">Edit playlist</h3>

    <form action="" class="select-playlist">
        <select
            class="form-select edit-playlist__select"
            name="selectPlaylist"
            id="selectPlaylist"
            onchange="handleSelectPlaylist(this)"
            >
            <option value="null">Select a playlist</option>
            <c:forEach var="p" items="${playlists}">
                <option value="${p.playlistId}">${p.name}</option>
            </c:forEach>
        </select>
        <button class="button button-confirm">Select</button>
    </form>

    <form class="change-playlist" action="">
        <div class="input">
            <label for="playlistName" style="text-align: center"
                   ><h3>Change playlist name</h3></label
            >
            <input
                type="text"
                placeholder="Enter new playlist name"
                id="playlistName"
                name="playlistName"
                />
        </div>
    </form>

    <h3 class="title blue-text">Playlist's name songs</h3>
    <!-- Trigger the modal! -->
    <button
        class="button button-action"
        type="button"
        class="btn btn-primary"
        data-bs-toggle="modal"
        data-bs-target="#add-song-modal"
        >
        Add new songs to this playlist
    </button>
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
                <!-- TODO: User javascript to render this part -->
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
                    <td>
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

    <button class="button button-danger">Save changes</button>
</div>
