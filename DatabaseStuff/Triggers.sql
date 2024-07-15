CREATE TRIGGER increaseSongCount
ON PlaylistSongs
AFTER INSERT
AS
BEGIN
    UPDATE p
    SET numberOfSongs = numberOfSongs + 1
    FROM playlist p
    INNER JOIN inserted i ON p.playlistId = i.playlistId;
END;

GO
CREATE TRIGGER decreaseSongCount
ON PlaylistSongs
AFTER DELETE
AS
BEGIN
    UPDATE p
    SET numberOfSongs = numberOfSongs - 1
    FROM playlist p
    INNER JOIN inserted i ON p.playlistId = i.playlistId;
END;
