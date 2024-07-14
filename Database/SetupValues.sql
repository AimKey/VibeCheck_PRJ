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
VALUES 
(1, 'As It Was','Song album here',248, 'Song&Image/Onlysong/As It Was.mp3','Song&Image/OnlyImage/As It Was.jpeg'),
(2, 'Compared Child','Song album here',248, 'Song&Image/Onlysong/Compared Child.mp3','Song&Image/OnlyImage/Compared Child.jpeg'),
(1, 'Die For You','Song album here',248, 'Song&Image/Onlysong/Die For You.mp3','Song&Image/OnlyImage/Die For You.jpeg'),
(2, 'Everything Goes On','Song album here',248, 'Song&Image/Onlysong/Everything Goes On.mp3','Song&Image/OnlyImage/Everything Goes On.jpeg'),
(1, 'everything sucks','Song album here',248, 'Song&Image/Onlysong/everything sucks.mp3','Song&Image/OnlyImage/everything sucks.jpeg'),
(2, 'Fire Again','Song album here',248, 'Song&Image/Onlysong/Fire Again.mp3','Song&Image/OnlyImage/Fire Again.jpeg');


 -- If encounter any errors, check the id of the songs and artists
 -- INSERT some playlist
 INSERT INTO Playlist (name, numberOfSongs, userId, creationDate)
 VALUES ('Minhkiet''s playlist', 2, 1, GETDATE())
-- INSERT some song to the playlist
INSERT INTO PlaylistSongs (playlistId, songId)
VALUES (1, 1),
(1, 2)