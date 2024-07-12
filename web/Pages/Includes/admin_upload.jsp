<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<div class="section" id="upload">
    <h3 class="title blue-text">Upload songs</h3>
    <!-- Song uploads form -->
    <form class="songUploadForm" action="UploadSong" method="post" enctype="multipart/form-data" multiple>
        <label for="songsUpload" class="button button-action">Choose files to upload</label>

        <input type="file" name="songsUpload" id="songsUpload" multiple class="songUploadForm__input"/>
        <input type="hidden" name="action" value="uploadSong" >
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
                    <th scope="col" style="width: 5%"></th>
                </tr>
            </thead>
            <tbody id="upload__table__body">
            </tbody>
        </table>
    </div>

    <button type="submit" class="button button-confirm upload__confirm-upload">
        Confirm
    </button>
    <h3 class="yellow-text hide" id="upload__msg"></h3>
</div>
