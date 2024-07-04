document.addEventListener('DOMContentLoaded', function () {
    const form = document.querySelector('.songUploadForm');
    const fileInput = document.querySelector('#songsUpload');

    fileInput.addEventListener('change', handleFileUpload);

    function handleFileUpload(event) {
        const formData = new FormData(form);

        fetch('UploadSong', {
            method: 'POST',
            body: formData
        })
        .then(response => response.text())
        .then(data => {
            const parser = new DOMParser();
            const doc = parser.parseFromString(data, 'text/html');
            
            // Log the entire response for debugging
            console.log("Response Document:", doc.documentElement.outerHTML);
            
            const tbody = doc.querySelector('.songs-display tbody');
            if (tbody) {
                document.querySelector('.songs-display tbody').innerHTML = tbody.innerHTML;
            } else {
                console.error("Error: '.songs-display tbody' not found in the response");
            }

            const confirmForm = doc.querySelector('.confirm-form');
            if (confirmForm) {
                document.querySelector('.confirm-form').innerHTML = confirmForm.innerHTML;
            } else {
                console.error("Error: '.confirm-form' not found in the response");
            }

            // Log the metadata to console for debugging
            const title = doc.querySelector('input[name="title"]').value;
            const artist = doc.querySelector('input[name="artist"]').value;
            const album = doc.querySelector('input[name="album"]').value;
            const duration = doc.querySelector('input[name="duration"]').value;
            const songFilePath = doc.querySelector('input[name="songFilePath"]').value;
            const songImagePath = doc.querySelector('input[name="songImagePath"]').value;

            console.log("Title:", title);
            console.log("Artist:", artist);
            console.log("Album:", album);
            console.log("Duration:", duration);
            console.log("File Path:", songFilePath);
            console.log("Image Path:", songImagePath);
        })
        .catch(error => console.error('Error:', error));
    }
});
