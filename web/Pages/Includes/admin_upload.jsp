<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Upload Songs</title>
    <script src="scripts/JS/JS_Upload.js"></script>
</head>
<body>
    <div class="section" id="upload">
        <h3 class="title blue-text">Upload songs</h3>
        <!-- Song uploads form -->
        <form class="songUploadForm" action="UploadSong" method="post" enctype="multipart/form-data">
            <label for="songsUpload" class="button button-action">Choose files to upload</label>
            <input type="file" name="songsUpload" id="songsUpload" />
        </form>

        <h3 class="title">The following song(s) will be uploaded</h3>
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
                    <c:if test="${not empty title}">
                        <tr>
                            <th class="song-img"><img src="${songImagePath}" alt="Cover image here" /></th>
                            <td class="song-title">${title}</td>
                            <td class="song-artist">${artist}</td>
                            <td class="song-album">${album}</td>
                            <td class="song-duration">${duration}</td>
                            <td><button onclick="handleRemoveFile()"><i class="fa-solid fa-xmark"></i></button></td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>

        <!-- Confirmation form -->
        <form class="confirm-form" action="SaveSong" method="post">
            <input type="hidden" name="title" value="${title}" />
            <input type="hidden" name="artist" value="${artist}" />
            <input type="hidden" name="album" value="${album}" />
            <input type="hidden" name="duration" value="${duration}" />
            <input type="hidden" name="songFilePath" value="${songFilePath}" />
            <input type="hidden" name="songImagePath" value="${songImagePath}" />
            <button type="submit" class="button button-confirm">
                Confirm
            </button>
        </form>
    </div>
</body>
</html>
