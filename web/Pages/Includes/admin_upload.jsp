<%-- 
    Document   : admin_upload
    Created on : Jun 20, 2024, 10:02:58 PM
    Author     : phamm
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="section" id="upload">
    <h3 class="title blue-text">Upload songs</h3>
    <!-- Song uploads form -->
    <form class="songUploadForm" action="songUpload" method="get">
        <label for="songsUpload" class="button button-action">Choose files to upload</label>
        <!-- Todo: handle multiple files ? -->
        <input type="file" name="songsUpload" id="songsUpload" />
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
                    <th scope="col" style="width: 5%"><i class="fa-regular fa-clock"></i></th>
                    <th scope="col" style="width: 5%"></th>
                </tr>
            </thead>
            <tbody>
                <tr>
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
                        <!-- TODO: On click, handle remove song from the input -->
                        <button onclick="handleRemoveFile(this)">
                            <i class="fa-solid fa-xmark"></i>
                        </button>
                    </td>
                </tr>

                <tr>
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
                        <!-- TODO: On click, handle remove song from the input -->
                        <button onclick="handleRemoveFile(this)">
                            <i class="fa-solid fa-xmark"></i>
                        </button>
                    </td>
                </tr>
                <tr>
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
                        <!-- TODO: On click, handle remove song from the input -->
                        <button onclick="handleRemoveFile(this)">
                            <i class="fa-solid fa-xmark"></i>
                        </button>
                    </td>
                </tr>
                <tr>
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
                        <!-- TODO: On click, handle remove song from the input -->
                        <button onclick="handleRemoveFile(this)">
                            <i class="fa-solid fa-xmark"></i>
                        </button>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
    <!-- Todo: Use javascript to fire submit on the form above -->
    <button type="submit" class="button button-confirm" onclick="submit(this)">
        Confirm
    </button>
</div>
