
const searchBar = document.getElementById("edit-song__search-bar");
let songList = document.getElementsByClassName("edit-song__selection");
let songs = []
songs = Array.from(songList).map((ele, index) => {
  let title = ele.querySelector('.song-title').textContent.trim().toLowerCase();
  let album = ele.querySelector('.song-album').textContent.trim().toLowerCase();
  let artist = ele.querySelector('.song-artist').textContent.trim().toLowerCase();
  return {
    element: ele,
    title: title,
    album: album,
    artist: artist,
  }
})
searchBar.addEventListener('input', (e) => {
  let value = e.target.value.toLowerCase();

  songs.forEach(song => {
    const isVisible = song.title.includes(value) || song.album.includes(value) || song.artist.includes(value);
    song.element.classList.toggle("hide", !isVisible)
  })
})