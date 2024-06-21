USE AppMusicDatabase
GO

-- Insert some random values
INSERT INTO AppUser (username, password, email, dateJoined ,profilePicPath)
VALUES ('Minh Kiet', '123456', 'phamminhkiet24@gmail.com', GETDATE(), 'users/Minh Kiet.jpg');

-- Fallback artist if not found
INSERT INTO Artist (artistId, artistName, profilePicturePath)
VALUES (0, 'Various Artist', 'artists/fallback.jpeg');
GO
-- Other artist
INSERT INTO Artist (artistName, bio, profilePicturePath)
VALUES ('Shibayan Records', 'A touhou circle', 'artists/fallback.jpeg'),
('Imase', 'A 23-years old male singer from Gifu', 'artists/fallback.jpeg');
-- INSERT Some song
INSERT INTO Song (artistId, title, album, duration, songFilePath, songImagePath)
VALUES (1, 'Full Moon Samba', 'Song album here', 248, 'songs/Full Moon Samba/Full Moon Samba.mp3', 'songs/fallback.jpg'),
 (2, 'Night dancer', 'Song album here', 248, 'songs/NIGHT DANCER/NIGHT DANCER.mp3', 'songs/fallback.jpg');

 -- INSERT some playlist
 INSERT INTO Playlist (name, numberOfSongs, userId, creationDate)
 VALUES ('AimKey playlist', 2, 1, GETDATE())
-- INSERT some song to the playlist
INSERT INTO PlaylistSongs (playlistId, songId)
VALUES (1, 1),
(1, 2)

 -- Get Song by ID
 GO
 CREATE PROC usp_Song_getBySongId
 @sId INT
 AS
	 SELECT *
	 FROM Song AS s
	 INNER JOIN Artist AS a 
	 ON s.artistId = a.artistId
	 WHERE s.songId = @sId

EXEC usp_Song_getBySongId @sId = 1
GO


-- Get app user
SELECT *
FROM AppUser a
WHERE a.userId = 1

-- Get Song by Name
SELECT *
FROM Song s
INNER JOIN Artist a
ON s.artistId = a.artistId
WHERE s.title LIKE N'%dancer%'

-- Get Artist by Id
SELECT * FROM Artist a
WHERE a.artistId = 1

-- Get Artist by Name
SELECT * FROM Artist a
WHERE a.artistName LIKE N'%Shibayan%'

-- Get All song of an artist
SELECT *
FROM Artist a
LEFT JOIN Song s
ON a.artistId = s.artistId
WHERE a.artistId = 1

-- Get ALL available playlist
SELECT * FROM Playlist

-- Get All songs from a playlist
SELECT p.playlistId, pl.songId, s.title
FROM Playlist p
INNER JOIN PlaylistSongs pl
ON p.playlistId = pl.playlistId
INNER JOIN Song s
ON s.songId = pl.songId
WHERE p.playlistId = 1