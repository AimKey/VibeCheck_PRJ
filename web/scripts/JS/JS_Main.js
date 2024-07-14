import { getSongs } from './Data.js';
//State
let replay = false;


// DOM elements
let progress = document.getElementById('progress');
let song = document.getElementById('song');
let ctrlIcon = document.getElementById('ctrlIcon');
let volume = document.getElementById('volume');

// Initialize the first song
let songs = getSongs();
let currentSongIndex = songs[0] ? 0 : -1;

console.log(currentSongIndex);
if (currentSongIndex === -1)
    currentSongIndex = 0;

let bigImg = document.getElementById('bigImg');
let smallImg = document.getElementById('smallImg');

let bottomTitle = document.querySelector(".song-info.bottom .music-title");
let bottomArtist = document.querySelector(".song-info.bottom .artist-name");
let rightTitle = document.querySelector(".music-info .music-title");
let rightArtist = document.querySelector(".music-info .artist-name");
let nameAlbum = document.querySelector(".album-right .name-album");

////////////////Load the song///////////////////////////
function loadSong(index) {
    const selectedSong = songs[index];

    // Check if selectedSong is valid
    if (!selectedSong) {
        console.error(`Song at index ${index} not found.`);
        return;
    }

    console.log(`Loading song: ${selectedSong.songFilePath}`);
    song.src = selectedSong.songFilePath;
    song.load();
    currentSongIndex = index;  // Update the current song index
    console.log(`Loaded song: ${selectedSong.songFilePath}`);

    // Update song information (image, title, artist)
    let parent = document.querySelector(`.song input[name="${selectedSong.songID}"]`).closest(".song");
    let songSrc = parent.querySelector(".song-detailed img").src;
    let title = parent.querySelector(".song-detailed .song-info h2").textContent;
    let artist = parent.querySelector(".song-detailed .song-info p").textContent;
    let album = parent.querySelector(".info .album ").textContent;

    console.log("Move to: ", parent);
    console.log("Link:", songSrc);
    console.log("Name of music: ", title);
    console.log("Artist: ", artist);

    bigImg.src = songSrc;
    smallImg.src = songSrc;

    rightTitle.textContent = title;
    rightArtist.textContent = artist;
    bottomTitle.textContent = title;
    bottomArtist.textContent = artist;
    nameAlbum.textContent = album;
}

// Add event listeners to song images
document.querySelectorAll('.song img').forEach((img, index) => {
    img.addEventListener('click', () => {
        loadSong(index);
        song.play();
        infoUpNext();
    });
});
// Run the function to load the first song on page load
document.addEventListener('DOMContentLoaded', () => {
    loadSong(currentSongIndex);
});

song.onloadeddata = function () {
    progress.max = song.duration;
    progress.value = song.currentTime;
}

////////////// Play and pause the song/////////////
ctrlIcon.addEventListener('click', playPause);

// Add event listener for the play event on the song element
song.addEventListener('play', () => {
    ctrlIcon.innerHTML = '<ion-icon name="pause"></ion-icon>';
    ctrlIcon.classList.add("pause");
    ctrlIcon.classList.remove("play");
    icons.forEach(icon => icon.classList.add('playing'));
});

// Add event listener for the pause event on the song element
song.addEventListener('pause', () => {
    ctrlIcon.innerHTML = '<ion-icon name="play"></ion-icon>';
    ctrlIcon.classList.remove("pause");
    ctrlIcon.classList.add("play");
    icons.forEach(icon => icon.classList.remove('playing'));
});

// Existing playPause function
function playPause() {
    console.log("playPause function called");
    if (ctrlIcon.classList.contains("pause")) {
        song.pause();
    } else {
        song.play();
    }
}

/////////////Automatically play the next song///////////////////////////
song.addEventListener('ended', playNextSong);

function playNextSong() {
    if (replay === false) {
        currentSongIndex = (currentSongIndex + 1) % songs.length; // Move to the next song, wrap to the start if at the end
    }
    console.log(`Playing next song: index ${currentSongIndex}`);
    loadSong(currentSongIndex); // Load the next song
    song.play(); // Play the loaded song

}
/////////////Up next///////////////////////////
let nextTitle = document.querySelector(".song-info .next-song-title");
let nextArtist = document.querySelector(".song-info .next-artist-name");
let nextImage = document.getElementById('midImg');

function infoUpNext() {
    const nextSongIndex = (currentSongIndex + 1) % songs.length;
    const nextSong = songs[nextSongIndex];
    // Check if nextSong is valid
    if (!nextSong) {
        console.error(`Next song at index ${nextSongIndex} not found.`);
        return;
    }
    // Get the next song's details
    let parent = document.querySelector(`.song input[name="${nextSong.songID}"]`).closest(".song");
    let nextImageSrc = parent.querySelector(".song-detailed img").src;
    let upNextTitle = parent.querySelector(".song-detailed .song-info h2").textContent;
    let upNextArtist = parent.querySelector(".song-detailed .song-info p").textContent;

    // Update the DOM elements
    nextTitle.textContent = upNextTitle;
    nextArtist.textContent = upNextArtist;
    nextImage.src = nextImageSrc;

    console.log("Next song:", upNextTitle, ", Next Artist: ", upNextArtist);
}

// Call infoUpNext to display the next song info
document.addEventListener('DOMContentLoaded', () => {
    infoUpNext();
});

// Optionally, call infoUpNext whenever a song ends
song.addEventListener('ended', () => {
    playNextSong();
    infoUpNext();
});

/////////////Progress bar and volume bar///////////////////////////
song.addEventListener('play', () => {
    setInterval(() => {
        progress.value = song.currentTime;
    }, 500);
});

progress.addEventListener('input', () => {
    song.currentTime = progress.value;
});

volume.addEventListener('input', () => {
    song.volume = volume.value / 100;
});

//////////////////////Next and back button/////////////////////////
function skipToNextSong() {
    currentSongIndex = (currentSongIndex + 1) % songs.length;
    console.log(`Skipping to next song: index ${currentSongIndex}`);
    loadSong(currentSongIndex);
    song.play();
    infoUpNext();
}

function skipToPreviousSong() {
    currentSongIndex = (currentSongIndex - 1 + songs.length) % songs.length;
    console.log(`Skipping to previous song: index ${currentSongIndex}`);
    loadSong(currentSongIndex);
    song.play();
    infoUpNext();
}
document.getElementById('nextBtn').addEventListener('click', skipToNextSong);
document.getElementById('prevBtn').addEventListener('click', skipToPreviousSong);

///////////////////////Replay Song Button//////////////////////////
function replaySong(btn) {
    console.log(btn);
    replay = !replay;
}
document.getElementById('reBtn').addEventListener('click', (evt) => {
    let btn = evt.target;
    replaySong(btn);
    
});
///////////////////////Process Duration////////////////////////////
document.addEventListener('DOMContentLoaded', function () {
    const audioElement = document.querySelector('audio'); // Assuming you have an <audio> element
    const currentTimeElement = document.querySelector('.current-time');
    const totalTimeElement = document.querySelector('.total-time');

    // Function to format time from seconds to MM:SS
    function formatTime(timeInSeconds) {
        const minutes = Math.floor(timeInSeconds / 60);
        const seconds = Math.floor(timeInSeconds % 60);
        return `${minutes}:${seconds < 10 ? '0' : ''}${seconds}`;
    }

    // Update total time once the song is loaded
    audioElement.addEventListener('loadedmetadata', function () {
        const durationFormatted = formatTime(audioElement.duration);
        totalTimeElement.textContent = durationFormatted;
    });

    // Update current time as the song plays
    audioElement.addEventListener('timeupdate', function () {
        const currentTimeFormatted = formatTime(audioElement.currentTime);
        currentTimeElement.textContent = currentTimeFormatted;
    });
});

//////////////Change color button///////////////////
const icons = document.querySelectorAll('.allicon .icon');

icons.forEach(icon => {
    icon.addEventListener('click', () => {
        icon.classList.toggle('clicked');
    });
});

const prevBtn = document.getElementById('prevBtn');
const nextBtn = document.getElementById('nextBtn');

prevBtn.addEventListener('click', () => {
    prevBtn.classList.add('clicked');
    setTimeout(() => {
        prevBtn.classList.remove('clicked');
    }, 200); // remove the class after 200ms
});

nextBtn.addEventListener('click', () => {
    nextBtn.classList.add('clicked');
    setTimeout(() => {
        nextBtn.classList.remove('clicked');
    }, 200); // remove the class after 200ms
});