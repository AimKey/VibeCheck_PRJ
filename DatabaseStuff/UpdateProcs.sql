
-- Procedure to update a Song with no image path (Kiet)
ALTER PROCEDURE usp_Song_Update
	@sId INT,
	@sTitle NVARCHAR(50),
	@aName NVARCHAR(100),
	@sAlbum NVARCHAR(100),
	@songImagePath NVARCHAR(200)
AS	
BEGIN
BEGIN TRANSACTION
	DECLARE @aId INT
	SELECT @aId = a.artistId FROM Artist a WHERE a.artistName = @aName
	IF @aId IS NOT NULL
		BEGIN
		--Artist exist (Meaning artist does not change)
		UPDATE Song 
		SET 
		Song.title = @sTitle, Song.artistId = @aId, Song.album = @sAlbum,
		Song.songImagePath = @songImagePath
		WHERE Song.songId = @sId
		END
	ELSE 
		BEGIN
		-- Artist not exist (Changed artist)
		-- Insert new artist first
		DECLARE @newArtistId INT

		INSERT INTO Artist(artistName) VALUES (@aName)

		SET @newArtistId = SCOPE_IDENTITY();

		UPDATE Song 
		SET 
		Song.title = @sTitle, Song.artistId = @newArtistId, Song.album = @sAlbum,
		Song.songImagePath = @songImagePath -- If it is null, we don't change (Replacing it self)
		WHERE Song.songId = @sId
		END
		COMMIT TRANSACTION

END;


GO
SELECT * FROM Song
SELECT * FROM Artist
GO
-- This command should create a new artist, profile pic should not change, and change song info
EXEC usp_Song_Update 21, 'INTERNET SURVIVOR 2', 'Test new artist', 'Touhou collections', NULL
GO
-- This one should only change the name without the artist, change the songImgFilepath into something
EXEC usp_Song_Update 21, 'INTERNET SURVIVOR 22', 'Its me mario', 'Touhou collections 2', 'songs/fallback.jpg'