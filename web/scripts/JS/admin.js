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

// User edit js
const userEditForm = document.querySelector(".user__form");
const userEditFormBtn = userEditForm.querySelector(".button-confirm");

userEditFormBtn.addEventListener("click", (evt) => {
  evt.preventDefault();
  const msg = document.getElementById("user-edit__msg"); // Message element
  // Check if confirm password is correct
  let pass = userEditForm.querySelector('input[name="password"]').value;
  let cfnPass = userEditForm.querySelector('input[name="confirm-password"]').value;
  if (pass != cfnPass) {
    console.log("Password not match!: " + pass + "-" + cfnPass);
    msg.classList.remove("hide");
    msg.textContent = "Confirm password does not match!";
  } else {
    console.log("Submitting");
    userEditForm.submit();
  }
});

// TODO: Function to delete user
// TODO: Function to promote a user to admin

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
//
/**
 * Delete song from database and update display accordingly (Or just refresh if you modify the button into a form)
 */
const deleteSongBtn = document.querySelectorAll(".edit-song__rmv-btn");

deleteSongBtn.forEach((btn) => {
  btn.addEventListener("click", (evt) => {
    evt.stopPropagation(); // This one is here so that when click on the trash can, the selection will not triggered
    let obj = evt.currentTarget;
    let loadingHTML = document.createElement("div");
    loadingHTML.classList.add("loader");
    obj.replaceWith(loadingHTML);
    console.log(obj);
    let id = obj.getAttribute("data-songId");

    let formData = new FormData();
    formData.append("action", "deleteSong");
    formData.append("sId", id);
    fetch("SongServlet", {
      method: "POST",
      body: formData,
    })
      .then((response) => response.text())
      .then((msg) => {
        console.log("Delete Success!");
        loadingHTML.replaceWith(obj);
        handleRemoveRow(obj);
      })
      .catch((error) => {
        console.error("Fetch error:", error);
        evt.currentTarget.innerHTML = btn;
      });
  });
});
/**
 * Handle display when user click (Select on a song to edit)
 */
const songsToEdit = document.querySelectorAll(".edit-song__selection");
songsToEdit.forEach((btn) => {
  btn.addEventListener("click", (evt) => {
    evt.preventDefault();
    let element = evt.currentTarget;
    let editFrom = document.querySelector("#edit-song__container");
    editFrom.classList.remove("hide");
    console.log("You clicked on this element: ");
    console.log(element);
    const id = element.querySelector(".song-id").textContent;
    const title = element.querySelector(".song-title").textContent;
    const img = element.querySelector(".song-img img").src;
    const artist = element.querySelector(".song-artist").textContent;
    const album = element.querySelector(".song-album").textContent;

    const editSongBody = document.querySelector(".edit-song__detail");
    const songImg = editSongBody.querySelector("img");
    const songIdInput = editSongBody.querySelector('input[name="songId"]');
    const nameInput = editSongBody.querySelector('input[name="title"]');
    const artistInput = editSongBody.querySelector('input[name="artist"]');
    const albumInput = editSongBody.querySelector('input[name="album"]');

    songImg.src = img;
    songIdInput.value = id;
    nameInput.value = title;
    artistInput.value = artist;
    albumInput.value = album;
  });
});

/**
 * Handle update the image when user upload an image (Apply for both user and song edit)
 */
const uploadImgBtns = document.querySelectorAll(".user-upload-img");
uploadImgBtns.forEach((btn) => {
  btn.addEventListener("change", (evt) => {
    const input = evt.currentTarget;
    const file = evt.target.files[0];
    console.log("You changed image on this input");
    const picContainer = input.closest(".picture__container");
    const img = picContainer.querySelector("img");

    if (file) {
      const reader = new FileReader();
      reader.onload = function (e) {
        img.src = e.target.result;
      };
      reader.readAsDataURL(file);
    }
  });
});
/**
 * Confirm edit song button
 */
const editConfirmBtn = document.querySelector(".edit-song__btn");
editConfirmBtn.addEventListener("click", (evt) => {
  evt.preventDefault();
  let form = document.querySelector(".edit-song__detail");
  let msgHTML = document.getElementById("edit__msg");
  const formData = new FormData(form);
  fetch("SongServlet", {
    method: "POST",
    body: formData,
  })
    .then((response) => {
      return response.text();
    })
    .then((data) => {
      console.log("Server resposne");
      console.log("Success" + data);
      // Do stuff if success
      msgHTML.classList.remove("hide");
      msgHTML.textContent = data;
    })
    .catch((error) => {
      console.error("Error: " + error);
      // Do stuff if failed
      msgHTML.classList.remove("hide");
      msgHTML.textContent = error;
    });
});


/**
 * Function to delete a song from a playlist
 * @param {Object} btn
 * @param {Number} playlistId
 */
function handleDeleteSongFromPlaylist(btn, playlistId) {
  let songId = btn.getAttribute("data-songId");

  // WHAT THE ACUTAL FUCK JUST USE SEARCH PARAMS FOR GOD SAKE ???????
  let url = new URLSearchParams();
  url.append("action", "delSong");
  url.append("songId", songId);
  url.append("playlistId", playlistId);

  fetch("PlaylistSongsServlet", {
    method: "POST",
    body: url,
  })
    .then((response) => {
      console.log("Fetch response status: " + response.status);
      return response.text().then((text) => {
        return { status: response.status, text: text };
      });
    })
    .then((result) => {
      if (result.status === 200) {
        console.log("Server response: " + result.text);
        handleRemoveRow(btn); // Remove the song row from the table
      } else {
        throw new Error("Server error: " + result.text);
      }
    })
    .catch((error) => {
      console.error("Error deleting song:", error);
      alert("Failed to delete song. Please try again later.");
    });
}

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
  playlistInformation.classList.remove("hide");
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
                        <button class="edit-playlist__delete-btn" data-songId=${s.songId}>
                            <i class="fa-solid fa-trash-can"></i>
                        </button>
                    </td>
                </tr>`;
        tbody.appendChild(songHTML);
      }
      // Add the event listener dynamically because I'm dumb
      let delBtn = table.querySelectorAll(".edit-playlist__delete-btn");
      delBtn.forEach((btn) => {
        btn.addEventListener("click", () => {
          handleDeleteSongFromPlaylist(btn, playlistId);
        });
      });
    }
  } else {
    console.log("Default playlist selected");
  }
});

// Handling change name for playlist part
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
 * When toggle is on (In change playlist name mode), and confirm is clicked:
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

  console.log(updateForm);
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
      let ids = [];
      for (var s of rJSON) {
        console.log(s);
        let songHTML = document.createElement("tr");
        songHTML.classList.add("song-selection");
        songHTML.innerHTML = `
                                        <th class="song-img">
                                            <img src="${s.songImagePath}" alt="${s.title}" />
                                        </th>
                                        <td class="song-title">${s.title}</td>
                                        <td class="song-artist">${s.artist}</td>
                                        <td class="song-album">${s.album}</td>
                                        <td class="song-duration">${s.duration}</td>
                                        <td>
                                            <button class="edit-playlist__add-song-btn" data-songId=${s.songId}>
                                                <i class="fa-solid fa-circle-plus"></i>
                                            </button>
                                        </td>`;
        tbody.appendChild(songHTML);
      }
      let addBtns = document.querySelectorAll(".edit-playlist__add-song-btn");
      addBtns.forEach((btn) => {
        btn.addEventListener("click", () => {
          handleAddSongToPlaylist(btn, playlistId);
        });
      });
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
 */
function handleAddSongToPlaylist(obj, playlistId) {
  let sId = obj.getAttribute("data-songId");
  console.log(`You clicked on add song to playlist: songid: ${sId}, playlistId: ${playlistId}`);
  songsToAdd.push(sId);
  //   let row = obj.closest("tr");
  obj.innerHTML = `<i class="fa-solid fa-check" style="color: #76abae;"></i>`;
  obj.disabled = true;
  obj.style.opacity = 0.8;
  console.log("Current songs: " + songsToAdd);
  //   console.log(row);
  //   handleRemoveRow(obj);
}

let cancelAddSongBtn = document.querySelector(".add-song-modal__cancel-btn");
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

const removePlaylistBtn = document.getElementById("edit-playlist__rmv-btn");
removePlaylistBtn.addEventListener("click", (evt) => {
  evt.preventDefault();
  console.log("Deleting this playlist: " + playlistId);
  let form = evt.currentTarget.closest("form");
  console.log(form);
  let inputId = form.querySelector("input[name=pId]");
  inputId.value = playlistId;
  console.log(inputId);
  form.submit();
});
