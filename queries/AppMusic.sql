CREATE DATABASE AppMusicDatabase
GO

USE AppMusicDatabase
GO
-- 
CREATE TABLE AppUser (
    userId INT PRIMARY KEY IDENTITY(1,1),
    username NVARCHAR(10),
    email VARCHAR(100),
    password VARCHAR(100),
    date_joined DATE,
    profile_picture_link VARCHAR(100)
);

CREATE TABLE Artist (
    artistId INT PRIMARY KEY IDENTITY(1,1),
    artistName VARCHAR(100),
    bio TEXT,
	profile_picture_link VARCHAR(100)
);

CREATE TABLE Song (
    songId INT PRIMARY KEY IDENTITY(1,1),
	artistId INT,
    title VARCHAR(100),
    duration TIME,
    file_path VARCHAR(255),
	genre VARCHAR(50),
	cover_picture_link VARCHAR(100)
	FOREIGN KEY (artistId) REFERENCES Artist(artistId)
);

CREATE TABLE Playlist (
    playlistId INT PRIMARY KEY IDENTITY(1,1),
    userId INT,
    name VARCHAR(100),
    numberOfSongs INT,
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

INSERT INTO AppUser (username, password, email, date_joined, profile_picture_link)
VALUES (N'kiet', '123456', 'phamminhkiet24@gmail.com', GETDATE(), 'Link here')

SELECT COUNT(*) FROM AppUser a WHERE a.username = 'kiet' AND a.password = '123456'
