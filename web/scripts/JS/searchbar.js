const searchBar = document.getElementById("edit-song__search-bar");
let songList = document.getElementsByClassName("edit-song__selection");
let songs = [];

console.log("Search bar: " + searchBar);

if (searchBar != null) {
  songs = Array.from(songList).map((ele, index) => {
    let title = ele.querySelector(".song-title").textContent.trim().toLowerCase();
    let album = ele.querySelector(".song-album").textContent.trim().toLowerCase();
    let artist = ele.querySelector(".song-artist").textContent.trim().toLowerCase();
    return {
      element: ele,
      title: title,
      album: album,
      artist: artist,
    };
  });
  searchBar.addEventListener("input", (e) => {
    let value = e.target.value.toLowerCase();

    songs.forEach((song) => {
      const isVisible =
        song.title.includes(value) || song.album.includes(value) || song.artist.includes(value);
      song.element.classList.toggle("hide", !isVisible);
    });
  });
}

const mainSB = document.getElementById('main__middle__searchbar');
songList = document.getElementsByClassName("song");

console.log(mainSB);

if (mainSB != null) {
  songs = Array.from(songList).map((ele, index) => {
    let songLeft = ele.querySelector('.song-detailed');
    let songRight = ele.querySelector('.info');
    let title = songLeft.querySelector(".song-title").textContent.trim().toLowerCase();
    let artist = songLeft.querySelector(".artist-name").textContent.trim().toLowerCase();
    let album = songRight.querySelector(".album").textContent.trim().toLowerCase();
    return {
      element: ele,
      title: title,
      album: album,
      artist: artist,
    };
  });
  mainSB.addEventListener("input", (e) => {
    let value = e.target.value.toLowerCase();

    songs.forEach((song) => {
      const isVisible =
        song.title.includes(value) || song.album.includes(value) || song.artist.includes(value);
      song.element.classList.toggle("hide", !isVisible);
    });
  });
}