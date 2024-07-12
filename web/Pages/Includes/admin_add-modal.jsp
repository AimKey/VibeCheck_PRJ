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
                            <c:if test="${songs == null}">
                            <h3>System songs is empty, go and add some !</h3>
                        </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="modal-footer">
                <form action="PlaylistSongsServlet" method="post" class="add-song-modal__form">
                    <input type="hidden" value="insertSongs" name="action">
                    <input type="hidden" value="" name="songIds">
                    <input type="hidden" value="" name="playlistId">
                    <button type="button" class="button button-confirm">
                        Save
                    </button>
                </form>
                <button type="button" class="button button-danger add-song-modal__cancel-btn" data-bs-dismiss="modal">Cancel</button>

                <!--<button type="button" class="button button-confirm">Save changes</button>-->
            </div>
        </div>
    </div>
</div>