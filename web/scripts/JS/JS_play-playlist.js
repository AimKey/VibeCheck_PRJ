let currentPlaylist = [];
let currentSongIndex = 0;
let isShuffle = false;
let isLoop = false;

function handlePlaylistChange(playlistId) {
    console.log("Playlist changed to: " + playlistId);
}

function playMusic() {
    const selectPlaylist = document.getElementById("selectPlaylist");
    const playlistId = selectPlaylist.value;
    if (playlistId && playlistId !== "null") {
        fetch(`PlaylistServlet?action=getSongs&playlistId=${playlistId}`)
            .then(response => response.json())
            .then(data => {
                if (data.length > 0) {
                    currentPlaylist = data;
                    currentSongIndex = 0;
                    if (isShuffle) {
                        shufflePlaylist();
                    }
                    const audioPlayer = document.getElementById("audioPlayer");
                    audioPlayer.src = currentPlaylist[currentSongIndex];
                    audioPlayer.style.display = "block";
                    audioPlayer.play();
                    updateButtons();
                }
            })
            .catch(error => console.error('Error:', error));
    } else {
        alert("Please select a playlist first.");
    }
}

function playNextSong() {
    if (currentSongIndex < currentPlaylist.length - 1) {
        currentSongIndex++;
    } else if (isLoop) {
        currentSongIndex = 0;
    } else {
        alert("End of playlist.");
        return;
    }
    const audioPlayer = document.getElementById("audioPlayer");
    audioPlayer.src = currentPlaylist[currentSongIndex];
    audioPlayer.play();
    updateButtons();
}

function playPreviousSong() {
    if (currentSongIndex > 0) {
        currentSongIndex--;
    } else {
        alert("Already at the beginning of the playlist.");
        return;
    }
    const audioPlayer = document.getElementById("audioPlayer");
    audioPlayer.src = currentPlaylist[currentSongIndex];
    audioPlayer.play();
    updateButtons();
}

function updateButtons() {
    const forwardButton = document.getElementById("forwardButton");
    const backwardButton = document.getElementById("backwardButton");

    forwardButton.style.display = currentSongIndex < currentPlaylist.length - 1 || isLoop ? "inline" : "none";
    backwardButton.style.display = currentSongIndex > 0 ? "inline" : "none";
}

function shufflePlaylist() {
    for (let i = currentPlaylist.length - 1; i > 0; i--) {
        const j = Math.floor(Math.random() * (i + 1));
        [currentPlaylist[i], currentPlaylist[j]] = [currentPlaylist[j], currentPlaylist[i]];
    }
}

function toggleShuffle() {
    isShuffle = !isShuffle;
    document.getElementById("shuffleButton").classList.toggle("active", isShuffle);
    if (isShuffle) {
        shufflePlaylist();
    }
    console.log("Shuffle is now " + (isShuffle ? "on" : "off"));
}

function toggleLoop() {
    isLoop = !isLoop;
    document.getElementById("loopButton").classList.toggle("active", isLoop);
    console.log("Loop is now " + (isLoop ? "on" : "off"));
}

document.addEventListener('DOMContentLoaded', () => {
    const audioPlayer = document.getElementById("audioPlayer");
    audioPlayer.addEventListener('ended', playNextSong);
    document.getElementById("forwardButton").addEventListener('click', playNextSong);
    document.getElementById("backwardButton").addEventListener('click', playPreviousSong);
    document.getElementById("shuffleButton").addEventListener('click', toggleShuffle);
    document.getElementById("loopButton").addEventListener('click', toggleLoop);
});
