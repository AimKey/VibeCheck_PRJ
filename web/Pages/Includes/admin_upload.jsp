<%-- 
    Document   : admin_upload
    Created on : Jun 20, 2024, 10:02:58 PM
    Author     : phamm
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="section" id="upload">
    <h3 class="title blue-text">Upload songs</h3>
    <!-- Song uploads form -->
    <form class="songUploadForm" action="UploadSong" method="post">
        <label for="songsUpload" class="button button-action">Choose files to upload</label>
        <!-- Todo: handle multiple files ? -->
        <input type="file" name="songsUpload" id="songsUpload" multiple class="songUploadForm__input"/>
        <!-- Todo: Use javascript to fire submit on the form above -->
    </form>
    <h3 class="title">The following song(s) will be uploaded</h3>
    <!-- TODO: Use js to handle display the songs -->
    <div class="songs-display">
        <table class="songs-display__table">
            <thead>
                <tr>
                    <th scope="col" style="width: 10%"></th>
                    <th scope="col" style="width: 30%">Name</th>
                    <th scope="col" style="width: 20%">Artist</th>
                    <th scope="col" style="width: 30%">Album</th>
                    <!--<th scope="col" style="width: 5%"><i class="fa-regular fa-clock"></i></th>-->
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

</div>
