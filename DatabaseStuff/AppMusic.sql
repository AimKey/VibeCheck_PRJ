CREATE DATABASE AppMusicDatabase
GO
USE AppMusicDatabase;
GO

-- Check and drop AppUser table if it exists
IF OBJECT_ID('AppUser', 'U') IS NOT NULL
    DROP TABLE AppUser;
GO

-- Create AppUser table
CREATE TABLE AppUser (
    userId INT PRIMARY KEY IDENTITY(1,1),
    username NVARCHAR(10) NOT NULL,
    email VARCHAR(100),
    password VARCHAR(100) NOT NULL,
    dateJoined DATE,
    profilePicPath VARCHAR(100),
	isAdmin BIT NOT NULL,
);

-- Check and drop Artist table if it exists
IF OBJECT_ID('Artist', 'U') IS NOT NULL
    DROP TABLE Artist;
GO

-- Create Artist table
CREATE TABLE Artist (
    artistId INT PRIMARY KEY IDENTITY(1,1),
    artistName VARCHAR(100) NOT NULL,
    bio NVARCHAR(MAX),
    profilePicturePath VARCHAR(100) -- If this is null, assign to default image artist path
);
GO

-- Check and drop Song table if it exists
IF OBJECT_ID('Song', 'U') IS NOT NULL
    DROP TABLE Song;
GO

-- Create Song table
CREATE TABLE Song (
    songId INT IDENTITY (1,1) PRIMARY KEY,
    artistId INT NOT NULL, -- IF artist is null, assign to the default artist id (0)
    duration INT NOT NULL,
    title NVARCHAR(50) NOT NULL,
    album NVARCHAR(100),
    songFilePath NVARCHAR(200) NOT NULL,
	songImagePath NVARCHAR(200) NOT NULL, -- If this is null, assign it to default song image path
    FOREIGN KEY (artistId) REFERENCES Artist(artistId)
);
GO

-- Check and drop Playlist table if it exists
IF OBJECT_ID('Playlist', 'U') IS NOT NULL
    DROP TABLE Playlist;
GO

-- Create Playlist table
CREATE TABLE Playlist (
    playlistId INT PRIMARY KEY IDENTITY(1,1),
    userId INT NOT NULL,
    name NVARCHAR(100) NOT NULL,
    numberOfSongs INT, -- Can be null if user hasn't add anything
    creationDate DATE, -- Is this necessary ?
    FOREIGN KEY (userId) REFERENCES AppUser(userId)
);
GO

-- Check and drop Playlist_Songs table if it exists
IF OBJECT_ID('Playlist_Songs', 'U') IS NOT NULL
    DROP TABLE PlaylistSongs;
GO

-- Create Playlist_Songs table
CREATE TABLE PlaylistSongs(
    playlistId INT,
    songId INT,
    PRIMARY KEY (playlistId, songId),
    FOREIGN KEY (playlistId) REFERENCES Playlist(playlistId),
    FOREIGN KEY (songId) REFERENCES Song(songId)
);
GO
