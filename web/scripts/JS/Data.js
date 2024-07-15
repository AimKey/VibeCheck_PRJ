function getSongs() {
    let songs = [];
    document.querySelectorAll('input[id="srcsong"]').forEach(element => {
        const songFilePath = element.value;
        const songID = element.name;
        const songElement = element.closest('.song');
        const songSrc = songElement.querySelector('.song-detailed img').src;
        const title = songElement.querySelector('.song-detailed .song-info h2').textContent;
        const artist = songElement.querySelector('.song-detailed .song-info p').textContent;

        console.log(`Retrieved songFilePath: ${songFilePath}`);
        console.log(`Retrieved songID: ${songID}`);
        console.log(`Retrieved songElement: ${songElement}`);
        console.log(`Retrieved songSrc: ${songSrc}`);
        console.log(`Retrieved title: ${title}`);
        console.log(`Retrieved artist: ${artist}`);


        songs.push({songFilePath, songID, songSrc, title, artist});
    });
    return songs;
}

export { getSongs };
