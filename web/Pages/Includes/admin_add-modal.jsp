<%-- 
    Document   : admin_add-modal
    Created on : Jun 20, 2024, 10:19:19 PM
    Author     : phamm
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div
    class="modal fade add-song"
    id="add-song-modal"
    tabindex="-1"
    aria-labelledby="exampleModalLabel"
    aria-hidden="true"
    >
    <div class="modal-dialog modal-dialog-centered box-shadow add-song__modal">
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
                            <c:if test="${songs != null}">
                                <c:forEach items="${songs}" var="s">
<!--                                    <tr class="song-selection">
                                        <th class="song-img">
                                            <img src="${s.songImagePath}" alt="${s.title}" />
                                        </th>
                                        <td class="song-title">${s.title}</td>
                                        <td class="song-artist">${s.artist}</td>
                                        <td class="song-album">${s.album}</td>
                                        <td class="song-duration">${s.duration}</td>
                                        <td>
                                             TODO: On click, query and delete song 
                                             IMPORTANT: use JS to send a request to server without redirecting user.
                                            Then remove this element from the DOM 
                                            <button onclick="handleAddSongToPlaylist(this)">
                                                <i class="fa-solid fa-circle-plus"></i>
                                            </button>
                                        </td>
                                    </tr>-->
                                </c:forEach>
                            </c:if>

                            <c:if test="${songs == null}">
                            <h3>System songs is empty, go and add some !</h3>
                        </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="button button-grey" data-bs-dismiss="modal">
                    Close
                </button>
                <!--<button type="button" class="button button-confirm">Save changes</button>-->
            </div>
        </div>
    </div>
</div>