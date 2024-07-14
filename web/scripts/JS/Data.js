function getSongs() {
    let songs = [];
    document.querySelectorAll('input[id="srcsong"]').forEach(element => {
        const songFilePath = element.value;
        const songID = element.name;
        
         console.log(`Retrieved songFilePath: ${songFilePath}`);
        console.log(`Retrieved songID: ${songID}`);
        songs.push({ songFilePath, songID });
    });
    return songs;
}
  export { getSongs };
