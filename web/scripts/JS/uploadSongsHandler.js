//IMPORTANT: Install jsmedia tags first
// Go to cdnjs and just grab that script file and put into our <head></head>
const jsmediatags = window.jsmediatags;
//const SONG_LOCATION = "/TestingUploadFiles/songs/Onlysong";
let userAudio;
// State object (Need to access and change in the future)
let mp3Files;


function onMusicChanged(evt) {
    console.log("Reading files");
    const files = evt.target.files;
    mp3Files = Array.from(files);
    userAudios = mp3Files;
    const songsTableBody = document.getElementById("upload__table__body");
    songsTableBody.innerHTML = ""; // Clear previous entries

    console.log(files);
    
    mp3Files.forEach((file, index) => {
        jsmediatags.read(file, {
            onSuccess: function (tag) {
                let imgSrc = '';
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
                    <tr>
                        <th class="song-img">
                            <img src="${imgSrc}" alt="User picture here" />
                        </th>
                        <td class="song-title">
                            ${tag.tags.title || ''}
                        </td>
                        <td class="song-artist">${tag.tags.artist || ''}</td>
                        <td class="song-album">
                            ${tag.tags.album || ''}
                        </td>
                        <td>
                            <!-- TODO: On click, handle remove song from the input -->
                            <button onclick="handleRemoveFile(this, ${index})">
                                <i class="fa-solid fa-xmark"></i>
                            </button>
                        </td>
                    </tr>
                `;
                songsTableBody.appendChild(row);
            },
            onError: function (e) {
                alert(e);
            }
        });
    });
    
}

function convertSecsToMins(secs) {
    let minutes = Math.floor(secs / 60);
    let seconds = Math.floor(secs % 60);
    if (seconds < 10) {
        seconds = `0${seconds}`;
    }
    return `${minutes}:${seconds}`;
}

// function getAudio(file, index) {
//     if (file) {
//         const audioURL = URL.createObjectURL(file);
//         let audioElement = new Audio(audioURL);
//         audioElement.addEventListener("loadedmetadata", function () {
//             document.getElementById(duration${index}).textContent = convertSecsToMins(audioElement.duration);
//         });

//         let audioPlayer = document.querySelector("#audio__controls");
//         audioPlayer.src = audioURL;
//     } else {
//         alert("No audio found");
//     }
// }

const input = document.querySelector(".songUploadForm__input");
input.addEventListener("change", onMusicChanged);

export default mp3Files;