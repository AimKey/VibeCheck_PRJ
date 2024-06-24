USE AppMusicDatabase
GO
-- This one will help you setup some inital values
-- Insert some random values
INSERT INTO AppUser (username, password, email, dateJoined ,profilePicPath, isAdmin)
VALUES ('Minhkiet', '123456', 'phamminhkiet24@gmail.com', GETDATE(), 'users/Minhkiet.jpg', 1);

-- Other artist
INSERT INTO Artist (artistName, bio, profilePicturePath)
VALUES ('Shibayan Records', 'A touhou circle', 'artists/fallback.jpeg'),
('Imase', 'A 23-years old male singer from Gifu', 'artists/fallback.jpeg');
-- INSERT Some song
INSERT INTO Song (artistId, title, album, duration, songFilePath, songImagePath)
VALUES (1, 'Full Moon Samba', 'Song album here', 248, 'songs/Full Moon Samba/Full Moon Samba.mp3', 'songs/fallback.jpg'),
 (2, 'Night dancer', 'Song album here', 248, 'songs/NIGHT DANCER/NIGHT DANCER.mp3', 'songs/fallback.jpg');

 -- If encounter any errors, check the id of the songs and artists
 -- INSERT some playlist
 INSERT INTO Playlist (name, numberOfSongs, userId, creationDate)
 VALUES ('Minhkiet''s playlist', 2, 1, GETDATE())
-- INSERT some song to the playlist
INSERT INTO PlaylistSongs (playlistId, songId)
VALUES (1, 1),
(1, 2)