CREATE DATABASE AppMusicDatabase
GO

USE AppMusicDatabase
GO
-- 
CREATE TABLE AppUser (
    userId INT PRIMARY KEY IDENTITY(1,1),
    username NVARCHAR(10) NOT NULL,
    email VARCHAR(100),
    password VARCHAR(100) NOT NULL,
    date_joined DATE,
    profile_picture_link VARCHAR(100),
);

CREATE TABLE Artist (
    artistId INT PRIMARY KEY IDENTITY(1,1),
    artistName VARCHAR(100) NOT NULL,
    bio TEXT,
	profile_picture_link VARCHAR(100)
);

CREATE TABLE Song (
    songId INT PRIMARY KEY IDENTITY(1,1),
	artistId INT NOT NULL,
    title VARCHAR(100) NOT NULL,
    duration TIME NOT NULL,
    file_path VARCHAR(255) NOT NULL,
	genre VARCHAR(50) NOT NULL,
	cover_picture_link VARCHAR(100) NOT NULL,
	FOREIGN KEY (artistId) REFERENCES Artist(artistId)
);

CREATE TABLE Playlist (
    playlistId INT PRIMARY KEY IDENTITY(1,1),
    userId INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    numberOfSongs INT NOT NULL,
    creation_date DATE,
    FOREIGN KEY (userId) REFERENCES AppUser(userId),
);

CREATE TABLE Playlist_Songs(
	playlistId int,
	songId int,
	PRIMARY KEY (playlistId, songId),
	FOREIGN KEY (playlistId) REFERENCES Playlist(playlistId),
	FOREIGN KEY (songId) REFERENCES Song(songId)
);
