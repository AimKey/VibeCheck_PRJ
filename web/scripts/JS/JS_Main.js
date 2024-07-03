import {songList} from './Data.js';

// DOM elements
let progress = document.getElementById('progress');
let song = document.getElementById('song'); 
let ctrlIcon = document.getElementById('ctrlIcon');
let volume = document.getElementById('volume');
// let volumeIcon = document.getElementById('volumeIcon');

console.log("Contents of songList from Data.js:", songList);

// Initialize the first song
let currentSongIndex = songList.findIndex(song => song.url.includes("As It Was.mp3")); 
if (currentSongIndex === -1) currentSongIndex = 0;

console.log("Current song:", songList[currentSongIndex]);

function loadSong(index) {
    const selectedSong = songList[index];
    song.src = selectedSong.url;
    song.load();
    console.log("Loading song:", selectedSong);
}

song.onloadeddata = function () {
    progress.max = song.duration;
    progress.value = song.currentTime;
}
function playPause(){
    console.log("playPause function called");
    if(ctrlIcon.classList.contains("pause")){
        song.pause();
        ctrlIcon.innerHTML = '<ion-icon name="play"></ion-icon>';
        ctrlIcon.classList.remove("pause");
        ctrlIcon.classList.add("play");

    }
    else{
        song.play();
        ctrlIcon.innerHTML = '<ion-icon name="pause"></ion-icon>';
        ctrlIcon.classList.add("pause");
        ctrlIcon.classList.remove("play");

    }
}

ctrlIcon.addEventListener('click', playPause);

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

function skipToNextSong() {
    currentSongIndex = (currentSongIndex + 1) % songList.length;
    loadSong(currentSongIndex);
    playPause();
}

function skipToPreviousSong() {
    currentSongIndex = (currentSongIndex - 1 + songList.length) % songList.length;
    loadSong(currentSongIndex);
    playPause();
}

// Load the initial song
loadSong(currentSongIndex);

// Event listeners for control buttons
document.getElementById('nextBtn').addEventListener('click', skipToNextSong);
document.getElementById('prevBtn').addEventListener('click', skipToPreviousSong);


