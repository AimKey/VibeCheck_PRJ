USE AppMusicDatabase;
GO

-- Insert some random values
INSERT INTO AppUser (username, password, email, dateJoined, profilePicPath, isAdmin)
VALUES ('Minh Kiet', '123456', 'phamminhkiet24@gmail.com', GETDATE(), 'users/Minh Kiet.jpg', 1);
GO

-- Fallback artist if not found
INSERT INTO Artist (artistName, profilePicturePath)
VALUES ('Various Artist', 'artists/fallback.jpeg');
GO

-- Other artists
INSERT INTO Artist (artistName, bio, profilePicturePath)
VALUES 
('Shibayan Records', 'A touhou circle', 'artists/fallback.jpeg'),
('Imase', 'A 23-year-old male singer from Gifu', 'artists/fallback.jpeg');
GO

-- Insert some songs
INSERT INTO Song (artistId, title, album, duration, songFilePath, songImagePath)
VALUES 
(1, 'Full Moon Samba', 'Song album here', 248, 'songs/Full Moon Samba/Full Moon Samba.mp3', 'songs/Full Moon Samba/Full Moon Samba.jpg'),
(2, 'Night dancer', 'Song album here', 248, 'songs/NIGHT DANCER/NIGHT DANCER.mp3', 'songs/NIGHT DANCER/NIGHT DANCER.jpg');
GO
-- Insert some songs
INSERT INTO Song (artistId, title, album, duration, songFilePath, songImagePath)
VALUES 
(1, 'Drama', 'Song album here', 248, 'songs/Drama/Drama.mp3', 'songs/Drama/Drama.jpg');
GO

-- Get all songs
SELECT * 
FROM Song;
GO

-- Insert some playlists
INSERT INTO Playlist (name, numberOfSongs, userId, creationDate)
VALUES ('AimKey playlist', 2, 1, GETDATE());
GO

-- Insert some songs into the playlist
INSERT INTO PlaylistSongs (playlistId, songId)
VALUES 
(1, 1),
(1, 2);
GO

-- Create a stored procedure to get a song by ID
CREATE PROC usp_Song_getBySongId
    @sId INT
AS
BEGIN
    SELECT *
    FROM Song AS s
    INNER JOIN Artist AS a 
    ON s.artistId = a.artistId
    WHERE s.songId = @sId;
END;
GO

-- Execute the stored procedure
EXEC usp_Song_getBySongId @sId = 1;
GO

-- Get app user
SELECT *
FROM AppUser a
WHERE a.userId = 1;
GO

-- Get Song by Name
SELECT *
FROM Song s
INNER JOIN Artist a
ON s.artistId = a.artistId
WHERE s.title LIKE N'%dancer%';
GO

-- Get Artist by Id
SELECT * 
FROM Artist a
WHERE a.artistId = 1;
GO

-- Get Artist by Name
SELECT * 
FROM Artist a
WHERE a.artistName LIKE N'%Shibayan%';
GO

-- Get All songs of an artist
SELECT *
FROM Artist a
LEFT JOIN Song s
ON a.artistId = s.artistId
WHERE a.artistId = 1;
GO

-- Get all available playlists
SELECT * 
FROM Playlist;
GO

-- Get All songs from a playlist
SELECT p.playlistId, pl.songId, s.title
FROM Playlist p
INNER JOIN PlaylistSongs pl
ON p.playlistId = pl.playlistId
INNER JOIN Song s
ON s.songId = pl.songId
WHERE p.playlistId = 1;
GO
