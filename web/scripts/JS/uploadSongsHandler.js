//IMPORTANT: Install jsmedia tags first
// Go to cdnjs and just grab that script file and put into our <head></head>
const jsmediatags = window.jsmediatags;
// State object (Need to access and change in the future)
let mp3Files;
let input = document.querySelector(".songUploadForm__input");
let uploadBtn = document.querySelector(".upload__confirm-upload");
let inputFiles;

function handleRemoveRow(obj) {
  let parent;
  console.log("[REMOVE ELEMENTS] :: Removing parent div (<tr>) ");
  parent = obj.closest("tr");
  parent.remove();
}

function handleRemoveFile(obj, index) {
  let title = obj.querySelector(".song-title").textContent.trim();
  let targetFile = title + ".mp3";
  let newSongs = mp3Files.filter((file) => {
    return file.name !== targetFile;
  });
  mp3Files = newSongs;
  console.log(mp3Files);
  handleRemoveRow(obj, index);
}

uploadBtn.addEventListener("click", (evt) => {
  let uploadBtn = evt.currentTarget;

  let loadingHTML = document.createElement("div");
  loadingHTML.classList.add("loader");

  uploadBtn.replaceWith(loadingHTML);
  const formData = new FormData();
  const msgHTML = document.querySelector("#upload__msg");
  console.log(msgHTML);
  if (mp3Files === undefined) {
    msgHTML.innerText = "Please choose a music file";
    msgHTML.classList.remove("hide");
  } else {
    mp3Files.forEach((file) => {
      formData.append("songsUpload", file);
    });
    formData.append("action", "uploadSong");
    console.log(formData.getAll("songsUpload"));
    console.log(formData.getAll("action"));
    console.log(msgHTML);
    fetch("UploadSong", {
      method: "POST",
      body: formData,
    })
      .then((response) => {
        finalURL = response.url;
        return response.text();
      })
      .then((data) => {
        console.log("Server resposne");
        console.log(data);
        // Do stuff if success
        msgHTML.classList.remove("hide");
        msgHTML.textContent = data;
      })
      .catch((error) => {
        console.error(error);
        // Do stuff if failed
        msgHTML.classList.remove("hide");
        msgHTML.textContent = error;
      })
      .finally(() => {
        loadingHTML.replaceWith(uploadBtn);
      });
    // form.submit();

    console.log("Bruh just refresh the page Im too lazy to handle it for you");
  }
});

console.log("Upload songs handler is alive !");

function onMusicChanged(evt) {
  console.log("Reading files");
  inputFiles = evt.target.files;
  mp3Files = Array.from(inputFiles);

  if (mp3Files === undefined) {
    msgHTML.innerText = "Please choose a music file";
  } else {
    const songsTableBody = document.getElementById("upload__table__body");
    songsTableBody.innerHTML = ""; // Clear previous entries
    console.log("Input files: ");
    console.log(inputFiles);

    mp3Files.forEach((file, index) => {
      jsmediatags.read(file, {
        onSuccess: function (tag) {
          let imgSrc = "";
          if (tag.tags.picture) {
            const data = tag.tags.picture.data;
            const format = tag.tags.picture.format;
            let base64String = ""; // Convert array to base 64
            for (let i = 0; i < data.length; i++) {
              base64String += String.fromCharCode(data[i]);
            }
            imgSrc = `data:${format};base64,${window.btoa(base64String)}`;
          }
          const row = document.createElement("tr");
          row.innerHTML = `
                        <th class="song-img">
                            <img src="${imgSrc}" alt="User picture here" />
                        </th>
                        <td class="song-title">
                            ${tag.tags.title || ""}
                        </td>
                        <td class="song-artist">${tag.tags.artist || ""}</td>
                        <td class="song-album">
                            ${tag.tags.album || ""}
                        </td>
                        <td>
                            <button class="upload__rmv-btn">
                                <i class="fa-solid fa-xmark"></i>
                            </button>
                        </td>
                `;
          songsTableBody.appendChild(row);
          // Attach event listener programmatically
          const removeButton = row.querySelector(".upload__rmv-btn");
          removeButton.addEventListener("click", () => handleRemoveFile(row, index));
        },
        onError: function (e) {
          alert(e);
        },
      });
    });
  }
}

function convertSecsToMins(secs) {
  let minutes = Math.floor(secs / 60);
  let seconds = Math.floor(secs % 60);
  if (seconds < 10) {
    seconds = `0${seconds}`;
  }
  return `${minutes}:${seconds}`;
}

input.addEventListener("change", onMusicChanged);
