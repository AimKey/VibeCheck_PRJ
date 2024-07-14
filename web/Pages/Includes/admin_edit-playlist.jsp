<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="section" id="edit-playlist">
    <h3 class="title orange-text">Create/Edit playlist</h3>

    <button type="button" class="button button-action" data-bs-toggle="modal" data-bs-target="#createPlaylistModal">Create New Playlist</button>

    <form action="PlaylistServlet" method="post">
        <input type="hidden" name="action" value="create" >
    </form>

    <form class="select-playlist">
        <select
            class="form-select edit-playlist__select"
            name="selectPlaylist"
            id="selectPlaylist"
            data-bs-toggle="tooltip"
            data-bs-placement="top"
            title="Choose a playlist"
            onchange="handlePlaylistChange(this.value)"
            >
            <option value="null">Select a playlist</option>
            <c:forEach var="p" items="${playlists}">
                <option value="${p.playlistId}" >${p.name}</option>
            </c:forEach>
        </select>
    </form>

    <div class="playlist-information hide">
        <h3 class="title blue-text playlist__name">Placeholder</h3>
        <form action="PlaylistServlet" method="post" class="playlist-information__change-name hide">
            <input type="hidden" name="action" value="updatePName" />
            <input type="hidden" name="pId" />
            <input type="text" name="pName" placeholder="New playlist name" autocomplete="off" />
        </form>
        <button class="playlist__toggle-change-name button-svg button-icon" data-bs-toggle="tooltip" title="Change playlist name">
            <i class="fa-regular fa-pen-to-square"></i>
        </button>
        <!-- Button to confirm change -->
        <button class="playlist__confirm-change button-svg button-icon hide" data-bs-toggle="tooltip" title="Confirm change" type="button">
            <i class="fa-solid fa-check"></i>
        </button>
        <!-- Button to cancel change -->
        <button class="playlist__cancel-change button-svg button-icon hide" data-bs-toggle="tooltip" title="Cancel change" type="button">
            <i class="fa-solid fa-xmark"></i>
        </button>
        <button class="playlist__add-songs button-svg button-icon" type="button" data-bs-toggle="modal" data-bs-target="#add-song-modal" title="Button to add songs to the playlist" type="button">
            <svg fill="#76abae"; enable-background="new 0 0 32 32" height="24px" id="svg2" version="1.1" viewBox="0 0 32 32" width="24px" xml:space="preserve" xmlns="http://www.w3.org/2000/svg" xmlns:cc="http://creativecommons.org/ns#" xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:inkscape="http://www.inkscape.org/namespaces/inkscape" xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:sodipodi="http://sodipodi.sourceforge.net/DTD/sodipodi-0.dtd" xmlns:svg="http://www.w3.org/2000/svg"><g id="background"><rect fill="none" height="32" width="32"/></g><g id="music_x5F_add"><path d="M28,15.518V4L8,0v16.35C7.373,16.127,6.702,16,6,16c-3.316,0-6,2.691-6,6c0,3.314,2.684,6,6,6c3.311,0,6-2.686,6-6V6l12,2   v6.059C23.671,14.022,23.338,14,23,14c-4.973,0-9,4.027-9,9c0,4.971,4.027,8.998,9,9c4.971-0.002,8.998-4.029,9-9   C31.999,19.879,30.411,17.132,28,15.518z M23,29.883c-3.801-0.009-6.876-3.084-6.885-6.883c0.009-3.801,3.084-6.876,6.885-6.885   c3.799,0.009,6.874,3.084,6.883,6.885C29.874,26.799,26.799,29.874,23,29.883z"/><g><polygon points="28,22 24.002,22 24.002,18 22,18 22,22 18,22 18,24 22,24 22,28 24.002,28 24.002,24 28,24   "/></g></g></svg>
        </button>
        <!-- Button to delete the playlist -->
        <form action="PlaylistServlet" method="post">
            <button class="button-svg button-icon" id="edit-playlist__rmv-btn">
                <i class="fa-solid fa-trash-can"></i>    
            </button>
            <input type="hidden" name="pId" id="pId"/>
            <input type="hidden" name="action" value="delete"/>
        </form>
        <!-- Play music button -->
        <button class="playlist__play-music button-svg button-icon" type="button" onclick="playMusic()" title="Play music from the playlist">
            <i class="fa-solid fa-play"></i>
        </button>
    </div>

    <div class="songs-display">
        <table class="songs-display__table" id="edit-playlist__table">
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
            </tbody>
        </table>
    </div>

    <!-- Create Playlist Modal -->
    <div class="modal fade" id="createPlaylistModal" tabindex="-1" aria-labelledby="createPlaylistModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="createPlaylistModalLabel">Create New Playlist</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="createPlaylistForm" action="PlaylistServlet" method="post">
                        <input type="hidden" name="action" value="create">
                            <div class="mb-3">
                                <label for="playlistName" class="form-label">Playlist Name</label>
                                <input type="text" class="form-control" id="playlistName" name="playlistName" required>
                            </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                    <button type="button" class="btn btn-primary" onclick="document.getElementById('createPlaylistForm').submit();">Create</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Audio player example-->
<!--    <div class="audio-controls">
        <button id="backwardButton" class="button-svg button-icon" style="display:none;">
            <i class="fa-solid fa-backward"></i>
        </button>
        <audio id="audioPlayer" controls style="display:none;"></audio>
        <button id="forwardButton" class="button-svg button-icon" style="display:none;">
            <i class="fa-solid fa-forward"></i>
        </button>
        <button id="shuffleButton" class="button-svg button-icon" title="Shuffle">
            <i class="fa-solid fa-random"></i>
        </button>
        <button id="loopButton" class="button-svg button-icon" title="Loop">
            <i class="fa-solid fa-sync"></i>
        </button>
    </div>-->
</div>

<!--<script src="scripts/JS/JS_play-playlist.js"></script>-->
