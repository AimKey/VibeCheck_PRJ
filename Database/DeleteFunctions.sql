USE AppMusicDatabase
GO

-- Delete a song by songId
CREATE PROCEDURE DeleteSong
    @songId INT
AS
BEGIN
    -- Delete the song from Playlist_Songs first to avoid foreign key constraint issues
    DELETE FROM Playlist_Songs WHERE songId = @songId;
    
    -- Delete the song from Song table
    DELETE FROM Song WHERE songId = @songId;
END
GO

-- Delete a playlist by playlistId
CREATE PROCEDURE DeletePlaylist
    @playlistId INT
AS
BEGIN
    -- Delete the playlist from Playlist_Songs first
    DELETE FROM Playlist_Songs WHERE playlistId = @playlistId;
    
    -- Delete the playlist from Playlist table
    DELETE FROM Playlist WHERE playlistId = @playlistId;
END
GO

-- Delete an artist and all their songs
CREATE PROCEDURE DeleteArtist
    @artistId INT
AS
BEGIN
    -- Delete songs of the artist from Playlist_Songs first to avoid foreign key constraint issues
    DELETE FROM Playlist_Songs WHERE songId IN (SELECT songId FROM Song WHERE artistId = @artistId);
    
    -- Delete songs of the artist from Song table
    DELETE FROM Song WHERE artistId = @artistId;
    
    -- Delete the artist from Artist table
    DELETE FROM Artist WHERE artistId = @artistId;
END
GO

-- Get the file path of a song by songId
CREATE FUNCTION GetSongFilePath (@songId INT)
RETURNS VARCHAR(MAX)
AS
BEGIN
    DECLARE @filePath VARCHAR(MAX);
    
    SELECT @filePath = filePath
    FROM Song
    WHERE songId = @songId;
    
    RETURN @filePath;
END
GO
