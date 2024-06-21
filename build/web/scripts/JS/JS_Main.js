function generateSongs() {
    let songsHTML = '';
    for (let i = 0; i < 20; i++) {
        songsHTML += `
            <div class="song">
            <div class="song-detailed">
                <img src="demo.jpg" alt="demo" class="img-fluid"> 
                <div class="song-info">
                    <h2 class="song-title">Song Title</h2>
                    <p class="artist-name">Artist Name</p>
                </div>
            </div>
            <div class="conkec">
                <p class="date-added">12-3-2024</p>
                <p class="duration">4:14</p>
            </div>
        `;
    }
    return songsHTML;
}

document.querySelector('.text-container').insertAdjacentHTML('afterend', generateSongs());