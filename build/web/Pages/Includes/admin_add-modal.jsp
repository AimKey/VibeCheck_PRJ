<%-- 
    Document   : admin_add-modal
    Created on : Jun 20, 2024, 10:19:19 PM
    Author     : phamm
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div
    class="modal fade"
    id="add-song-modal"
    tabindex="-1"
    aria-labelledby="exampleModalLabel"
    aria-hidden="true"
    >
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Select songs to add</h5>
                <button
                    type="button"
                    class="button button-danger"
                    data-bs-dismiss="modal"
                    aria-label="Close"
                    >
                    <i class="fa-solid fa-x"></i>
                </button>
            </div>
            <div class="modal-body">
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
                                        <i class="fa-solid fa-circle-plus"></i>
                                    </button>
                                </td>
                            </tr>

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
                                        <i class="fa-solid fa-circle-check"></i>
                                    </button>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="button button-grey" data-bs-dismiss="modal">
                    Close
                </button>
                <button type="button" class="button button-confirm">Save changes</button>
            </div>
        </div>
    </div>
</div>