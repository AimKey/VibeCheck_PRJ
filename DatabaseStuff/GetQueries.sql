USE AppMusicDatabase
GO

 -- Get Song by ID
 GO
 CREATE PROC usp_Song_getBySongId
 @sId INT
 AS
	 SELECT s.songId, a.artistName, s.album, s.duration, s.title, s.songFilePath, s.songImagePath
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

-- Get all Song with corresponding artist
SELECT s.songId, s.title, a.artistName, s.album, s.duration, s.songImagePath, s.songFilePath
FROM Song s
INNER JOIN Artist a
ON s.artistId = a.artistId

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

-- Get ALL available playlist of a User
SELECT p.playlistId, p.userId, p.name, p.numberOfSongs, p.creationDate
FROM Playlist p
WHERE p.userId = 1

-- Get ALL songs from a playlist
SELECT s.songId, s.title, a.artistName, s.album, s.duration, s.songFilePath, s.songImagePath
FROM PlaylistSongs pl
INNER JOIN Song s
ON s.songId = pl.songId
INNER JOIN Artist a
ON a.artistId = s.artistId
WHERE pl.playlistId = 1

-- Get unique songs that is not in a playlist
SELECT *
FROM PlaylistSongs pl
INNER JOIN Song s
ON s.songId = pl.songId
INNER JOIN Artist a
ON a.artistId = s.artistId
WHERE pl.playlistId = 1 AND pl.songId NOT IN (SELECT Song.songId FROM Song)

SELECT *
FROM Song s
INNER JOIN Artist a
ON a.artistId = s.artistId
WHERE s.songId NOT IN (SELECT pl.songId FROM PlaylistSongs pl WHERE pl.playlistId = 1)

