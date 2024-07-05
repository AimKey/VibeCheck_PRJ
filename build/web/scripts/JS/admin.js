import mp3Files from "./uploadSongsHandler.js";

// Perserve scroll postiion function
document.addEventListener("DOMContentLoaded", function (event) {
  var scrollpos = sessionStorage.getItem("scrollpos");
  if (scrollpos) window.scrollTo(0, scrollpos);
});

window.onbeforeunload = function (e) {
  sessionStorage.setItem("scrollpos", window.scrollY);
};

// Setup for display loading
let loadingHTML = document.createElement("div");
loadingHTML.classList.add("loader");

/**
 * A function to handle delete a row in a table <br>
 * This function will search for the neareast row of a child element <br>
 * Use for display purpose only
 * @param {*} obj An element inside the row
 */
function handleRemoveRow(obj) {
  let parent;
  console.log("[REMOVE ELEMENTS] :: Removing parent div (<tr>) ");
  parent = obj.closest("tr");
  console.log(parent);
  parent.remove();
}
/**
 * Remove file from user input (User is upload multiple files, remove one of them)
 */
let handleRemoveFile = (obj, index) => {
  handleRemoveRow(obj, index);
  console.log("TODO: Remove user file uploaded: ");
};

let uploadBtn = document.querySelector(".upload__confirm-upload");

uploadBtn.addEventListener("click", (evt) => {
  evt.preventDefault();
  console.log("Target: ");
  console.log(uploadForm);
  uploadForm.submit();
});

/**
 * Delete song from database and update display accordingly
 */
let deleteSongBtn = document.querySelectorAll(".edit-song__rmv-btn");
// console.log(deleteSongBtn);

deleteSongBtn.forEach((btn) => {
  btn.addEventListener("click", (evt) => {
    let obj = evt.currentTarget;
    console.log(obj);
    let id = obj.getAttribute("data-songId");
    handleRemoveRow(obj);
    console.log("TODO: DELETE song from system , id: " + id);
  });
});

let handleDeleteSongFromPlaylist = (obj, songId, playlistId) => {
  handleRemoveRow(obj);
  console.log(`TODO: DELETE songId:${songId} from playlist ${playlistId}`);
};
/**
 * Make call to server for playlist songs and update display when the playlist is changed
 */
const playlistInformation = document.getElementsByClassName("playlist-information")[0];
let playlistId;
let playlistSelect = document.querySelector(".edit-playlist__select");
playlistSelect.addEventListener("change", async (evt) => {
  let obj = evt.currentTarget;
  playlistId = obj.value;
  let index = obj.selectedIndex;
  let playlistName = obj.options[index].text;
  // Show the information of the playlist
  playlistInformation.classList.toggle("hide");
  let playlistNameHTML = playlistInformation.querySelector(".playlist__name");
  playlistNameHTML.textContent = playlistName;

  console.log("Calling servet to get songs from playlist: " + playlistId);
  if (playlistId !== "null") {
    let table = document.getElementById("edit-playlist__table");
    let tbody = table.querySelector("tbody");

    tbody.appendChild(loadingHTML); // Funny little loading guy

    // Make call to the getPlaylistSongs
    let response = await fetch(`PlaylistSongsServlet?action=get&playlistId=${playlistId}`);
    if (response !== null || response.ok) {
      let songObj = await response.json();

      tbody.innerHTML = ``;
      // Set the display of songs
      for (var s of songObj) {
        let songHTML = document.createElement("tr");
        songHTML.classList.add("song-selection");
        songHTML.innerHTML = `
                <tr class="song-selection">
                    <th class="song-img">
                        <img src="${s.songImagePath}" alt="User picture here" />
                    </th>
                    <td class="song-title">
                        ${s.title}
                    </td>
                    <td class="song-artist">${s.artist}</td>
                    <td class="song-album">
                        ${s.album}
                    </td>
                    <td class="song-duration">${s.duration}</td>
                    <td>
                        <button class="edit-playlist__delete-btn">
                            <i class="fa-solid fa-trash-can"></i>
                        </button>
                    </td>
                </tr>`;
        // Add the event listener dynamically because I'm dumb
        let delBtn = songHTML.querySelector(".edit-playlist__delete-btn");
        delBtn.addEventListener("click", () => {
          handleDeleteSongFromPlaylist(delBtn, s.songId, playlistId);
        });
        tbody.appendChild(songHTML);
      }
    }
  } else {
    console.log("Default playlist selected");
  }
});

// Handling change name part
const pNameToggleEdit = playlistInformation.getElementsByClassName(
  "playlist__toggle-change-name"
)[0];
const pNameConfirmBtn = pNameToggleEdit.nextElementSibling;
const pNameCancelBtn = pNameConfirmBtn.nextElementSibling;
const pNameInput = playlistInformation.querySelector("input");
const pNameTitle = playlistInformation.querySelector("h3");
const updateForm = playlistInformation.querySelector(".playlist-information__change-name");
/*
 * Handle when toggle edit playlist name is clicked:
 *
 * 1. Hide self, turn on the confirm and cancel buttons
 *
 * 2. Turn the playlist name into input
 */
pNameToggleEdit.addEventListener("click", (evt) => {
  evt.preventDefault();
  console.log("You clicked change name!");

  // Toggle display the buttons
  pNameConfirmBtn.classList.toggle("hide");
  pNameCancelBtn.classList.toggle("hide");
  pNameToggleEdit.classList.toggle("hide");

  // Toggle the input for user to enter
  updateForm.classList.toggle("hide");
  pNameTitle.classList.toggle("hide");
});
/**
 * When toggle is on, and confirm is clicked:
 *
 * 1. Make calls to server base on the input value
 *
 * 2. Wait for server response and update the title, setting the elements
 *
 * 3. If error, don't update (Or implement some kind of pop-up)
 */
pNameConfirmBtn.addEventListener("click", (evt) => {
  evt.preventDefault();

  console.log("TODO: Handle update playlist name here");

  // Lets say the the call succeed, set the title name here

  // Toggle display the buttons
  pNameConfirmBtn.classList.toggle("hide");
  pNameCancelBtn.classList.toggle("hide");
  pNameToggleEdit.classList.toggle("hide");

  // Turn off input
  updateForm.classList.toggle("hide");
  pNameTitle.classList.toggle("hide");

  // Get the playlistId first
  let pIdInp = updateForm.querySelector("input[name='pId']");
  pIdInp.value = playlistId;
  console.log(pIdInp);
  updateForm.submit();
});

/**
 * When toggle is on, and cancel is clicked:
 *
 * 1. Revert back to the initial state by setting the elements
 */
pNameCancelBtn.addEventListener("click", (evt) => {
  evt.preventDefault();
  console.log("Playlist name change canceled");
  // Toggle display the buttons
  pNameConfirmBtn.classList.toggle("hide");
  pNameCancelBtn.classList.toggle("hide");
  pNameToggleEdit.classList.toggle("hide");

  // Turn off input
  updateForm.classList.toggle("hide");
  pNameTitle.classList.toggle("hide");
});

const addSongModal = document.getElementById("add-song-modal");
const pAddSong = document.querySelector(".playlist__add-songs");
/**
 * Get unique songs so that user can choose it to add into their playlist
 *
 * This is triggered when the add-song modal is open
 */
pAddSong.addEventListener("click", () => {
  let option = document.querySelector("#selectPlaylist");
  let playlistId = option.value;
  let tbody = addSongModal.querySelector("tbody");
  tbody.appendChild(loadingHTML);
  // Make call to the getPlaylistSongs
  fetch(`PlaylistSongsServlet?action=getUnique&playlistId=${playlistId}`)
    .then((r) => r.json())
    .then((rJSON) => {
      tbody.innerHTML = ``;
      for (var s of rJSON) {
        let songHTML = document.createElement("tr");
        songHTML.classList.add("song-selection");
        songHTML.innerHTML = `<tr class="song-selection">
                                        <th class="song-img">
                                            <img src="${s.songImagePath}" alt="${s.title}" />
                                        </th>
                                        <td class="song-title">${s.title}</td>
                                        <td class="song-artist">${s.artist}</td>
                                        <td class="song-album">${s.album}</td>
                                        <td class="song-duration">${s.duration}</td>
                                        <td>
                                            <button class="edit-playlist__add-song-btn">
                                                <i class="fa-solid fa-circle-plus"></i>
                                            </button>
                                        </td>
                                    </tr>`;
        // Add the event listener dynamically because I'm dumb
        let addBtn = songHTML.querySelector(".edit-playlist__add-song-btn");
        addBtn.addEventListener("click", () => {
            handleAddSongToPlaylist(addBtn, s.songId);
        });
        tbody.appendChild(songHTML);
      }
    })
    .catch((error) => console.error(error));
});
let songsToAdd = [];
/**
 * 1. Add the songId into a temp array,
 * 
 * 2. Remove the songs div from the body
 * (The cancel and confirm part will be handled later)
 * @param {type} obj
 * @param {type} sId
 * @returns {undefined}
 */
function handleAddSongToPlaylist(obj, sId) {
  console.log(`You clicked on add song to playlist: songid: ${sId}, playlistId: ${playlistId}`);
  songsToAdd.push(sId);
  let row = obj.closest("tr");
  obj.innerHTML = `<i class="fa-solid fa-check" style="color: #76abae;"></i>`;
  obj.disabled = true;
  obj.style.opacity = 0.8;
  console.log("Current songs: " + songsToAdd);
//   console.log(row);
//   handleRemoveRow(obj);
}

let cancelAddSongBtn = document.querySelector(".add-song-modal__cancel-btn");
console.log(cancelAddSongBtn);
cancelAddSongBtn.addEventListener("click", (evt) => {
    console.log("Resting songs: " + songsToAdd);
    songsToAdd = [];
});

/**
 * Submit the form with action and value is playlistId "1,2,3,4"
 */
const modalConfirm = document.querySelector(".add-song-modal__form");

modalConfirm.addEventListener("click", (e) => {
  console.log("Current songs to add: ");
  console.log(songsToAdd);
  let idString = songsToAdd.join(",");
  console.log(idString);
  let formInputs = modalConfirm.querySelectorAll("input");
  // 1 is action, 2 is songIds, 3 is playlistId (Playlist id is already determined above)
  formInputs[1].value = idString;
  formInputs[2].value = playlistId;
  modalConfirm.submit();
});
