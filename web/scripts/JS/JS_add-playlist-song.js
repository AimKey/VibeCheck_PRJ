// Perserve scroll postiion function
document.addEventListener("DOMContentLoaded", function (event) {
  var scrollpos = sessionStorage.getItem("scrollpos");
  if (scrollpos) window.scrollTo(0, scrollpos);
});

window.onbeforeunload = function (e) {
  sessionStorage.setItem("scrollpos", window.scrollY);
};

let loadingHTML = document.createElement("div");
loadingHTML.classList.add("loader");

function handleRemoveRow(obj) {
  let parent;
  console.log("[REMOVE ELEMENTS] :: Removing parent div (<tr>) ");
  parent = obj.closest("tr");
  console.log(parent);
  parent.remove();
}

const deleteSongBtn = document.querySelectorAll(".edit-song__rmv-btn");

deleteSongBtn.forEach((btn) => {
  btn.addEventListener("click", (evt) => {
    evt.stopPropagation();
    let obj = evt.currentTarget;
    console.log(obj);
    let id = obj.getAttribute("data-songId");
    handleRemoveRow(obj);
    console.log("TODO: DELETE song from system , id: " + id);
  });
});

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
    console.log(songIdInput);
    console.log(songIdInput.value);
  });
});

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
      console.log(data);
      msgHTML.classList.remove("hide");
      msgHTML.textContent = data;
    })
    .catch((error) => {
      console.error(error);
      msgHTML.classList.remove("hide");
      msgHTML.textContent = error;
    });
});

let handleDeleteSongFromPlaylist = (obj, songId, playlistId) => {
  handleRemoveRow(obj);
  console.log(`TODO: DELETE songId:${songId} from playlist ${playlistId}`);
};

const playlistInformation = document.getElementsByClassName("playlist-information")[0];
let playlistId;
let playlistSelect = document.querySelector(".edit-playlist__select");
playlistSelect.addEventListener("change", async (evt) => {
  let obj = evt.currentTarget;
  playlistId = obj.value;
  let index = obj.selectedIndex;
  let playlistName = obj.options[index].text;
  playlistInformation.classList.toggle("hide");
  let playlistNameHTML = playlistInformation.querySelector(".playlist__name");
  playlistNameHTML.textContent = playlistName;

  console.log("Calling servet to get songs from playlist: " + playlistId);
  if (playlistId !== "null") {
    let table = document.getElementById("edit-playlist__table");
    let tbody = table.querySelector("tbody");

    tbody.appendChild(loadingHTML);

    let response = await fetch(`PlaylistSongsServlet?action=get&playlistId=${playlistId}`);
    if (response !== null || response.ok) {
      let songObj = await response.json();

      tbody.innerHTML = ``;
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

const pNameToggleEdit = playlistInformation.getElementsByClassName(
  "playlist__toggle-change-name"
)[0];
const pNameConfirmBtn = pNameToggleEdit.nextElementSibling;
const pNameCancelBtn = pNameConfirmBtn.nextElementSibling;
const pNameInput = playlistInformation.querySelector("input");
const pNameTitle = playlistInformation.querySelector("h3");
const updateForm = playlistInformation.querySelector(".playlist-information__change-name");

pNameToggleEdit.addEventListener("click", (evt) => {
  evt.preventDefault();
  console.log("You clicked change name!");

  pNameConfirmBtn.classList.toggle("hide");
  pNameCancelBtn.classList.toggle("hide");
  pNameToggleEdit.classList.toggle("hide");

  updateForm.classList.toggle("hide");
  pNameTitle.classList.toggle("hide");
});

pNameConfirmBtn.addEventListener("click", (evt) => {
  evt.preventDefault();

  console.log("TODO: Handle update playlist name here");

  pNameConfirmBtn.classList.toggle("hide");
  pNameCancelBtn.classList.toggle("hide");
  pNameToggleEdit.classList.toggle("hide");

  updateForm.classList.toggle("hide");
  pNameTitle.classList.toggle("hide");

  let pIdInp = updateForm.querySelector("input[name='pId']");
  pIdInp.value = playlistId;
  console.log(pIdInp);
  updateForm.submit();
});

pNameCancelBtn.addEventListener("click", (evt) => {
  evt.preventDefault();
  console.log("Playlist name change canceled");
  pNameConfirmBtn.classList.toggle("hide");
  pNameCancelBtn.classList.toggle("hide");
  pNameToggleEdit.classList.toggle("hide");

  updateForm.classList.toggle("hide");
  pNameTitle.classList.toggle("hide");
});

const addSongModal = document.getElementById("add-song-modal");
const pAddSong = document.querySelector(".playlist__add-songs");

pAddSong.addEventListener("click", () => {
  let option = document.querySelector("#selectPlaylist");
  let playlistId = option.value;
  let tbody = addSongModal.querySelector("tbody");
  tbody.appendChild(loadingHTML);
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

function handleAddSongToPlaylist(obj, sId) {
  console.log(`You clicked on add song to playlist: songid: ${sId}, playlistId: ${playlistId}`);
  songsToAdd.push(sId);
  let row = obj.closest("tr");
  obj.innerHTML = `<i class="fa-solid fa-check" style="color: #76abae;"></i>`;
  obj.disabled = true;
  obj.style.opacity = 0.8;
  console.log("Current songs: " + songsToAdd);
}

let cancelAddSongBtn = document.querySelector(".add-song-modal__cancel-btn");
cancelAddSongBtn.addEventListener("click", (evt) => {
  console.log("Resting songs: " + songsToAdd);
  songsToAdd = [];
});

const modalConfirm = document.querySelector(".add-song-modal__form");

modalConfirm.addEventListener("click", (e) => {
  console.log("Current songs to add: ");
  console.log(songsToAdd);
  let idString = songsToAdd.join(",");
  console.log(idString);
  let formInputs = modalConfirm.querySelectorAll("input");
  formInputs[1].value = idString;
  formInputs[2].value = playlistId;
  modalConfirm.submit();
});
