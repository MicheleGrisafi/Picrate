/*
CREATE DATABASE foto;
USE foto;*/

CREATE TABLE IF NOT EXISTS Utente(
	IDUser			INT NOT NULL AUTO_INCREMENT,
	username		VARCHAR(20) NULL,
	googleKey		VARCHAR(255) NULL UNIQUE,
	mail			VARCHAR(255) NOT NULL,
	score			SMALLINT,
	money			SMALLINT,
	CONSTRAINT PKUser PRIMARY KEY (IDUser)
);
CREATE TABLE IF NOT EXISTS Challenge (
	IDChallenge		SMALLINT NOT NULL AUTO_INCREMENT,
	description		VARCHAR(255) NOT NULL,
	title			VARCHAR(20)	NOT NULL,
	image			VARCHAR(255) NOT NULL,
	CONSTRAINT PKChallenge PRIMARY KEY(IDChallenge)
);
CREATE TABLE IF NOT EXISTS ChallengeSession(
	IDSession		INT NOT NULL AUTO_INCREMENT,
	image			VARCHAR(255) DEFAULT NULL,
	expiration		DATETIME,
	stato			INT(1) NOT NULL,	/* 1 = attivo, 0 = valutazione, -1 = scaduto*/
	IDChallenge		SMALLINT NOT NULL,
	CONSTRAINT PKChallengeSession PRIMARY KEY(IDSession),
	CONSTRAINT FKChallengeOfTheSession FOREIGN KEY (IDChallenge) REFERENCES Challenge(IDChallenge)
);
CREATE TABLE IF NOT EXISTS Photo(
	IDPhoto			INTEGER(10)	NOT NULL AUTO_INCREMENT,
	latitudine		DOUBLE,
	longitudine		DOUBLE,
	owner			INT	NOT NULL,
	challenge		INT NOT NULL,
	
	CONSTRAINT PKPhoto PRIMARY KEY (IDPhoto),
	CONSTRAINT FKPhotoOwner FOREIGN KEY (owner) REFERENCES Utente(IDUser),
	CONSTRAINT FKPhotoChallenge FOREIGN KEY (challenge) REFERENCES ChallengeSession(IDSession)
);
CREATE TABLE IF NOT EXISTS Rating(
	IDRating		SMALLINT NOT NULL AUTO_INCREMENT,
	IDPhoto			INTEGER(10) NOT NULL,
	IDUser			INT	NOT NULL,
	Rating			INT(1) DEFAULT NULL,
	Segnalazione	BOOLEAN DEFAULT NULL,
	
	CONSTRAINT PKRating		PRIMARY KEY (IDRating),
	CONSTRAINT FKRatingPhoto FOREIGN KEY (IDPhoto) REFERENCES Photo(IDPhoto),
	CONSTRAINT FKRatingUser FOREIGN KEY (IDUser) REFERENCES Utente(IDUser)
);
CREATE TABLE IF NOT EXISTS dataInfo(
	picturesFolder	VARCHAR(255),
	version			INT(2)
);



TRUNCATE TABLE `Challenge`;
INSERT INTO `Challenge` (`IDChallenge`, `description`, `title`,`image`) VALUES
(1, 'Questo challenge prevede la fotografia di un panorama', 'Panorama','1'),
(2, 'Fai una foto al tuo cane preferito', 'Cani','2'),
(3, 'Immortale la più bella scena pubblica che ci sia', 'Scena pubblica','3');

TRUNCATE TABLE `ChallengeSession`;
INSERT INTO `ChallengeSession` (`IDSession`, `image`, `expiration`, `stato`, `IDChallenge`) VALUES
(1, NULL, '2017-05-30 23:59:59', 0, 1),
(2, NULL, '2017-06-30 23:59:59', 0, 2),
(3, NULL, '2017-09-01 23:59:59', 1, 3);

TRUNCATE TABLE `Photo`;
INSERT INTO `Photo` (`IDPhoto`, `latitudine`, `longitudine`, `owner`, `challenge`) VALUES
(3, 0, 0, 1, 3),
(43, 0, 0, 10, 3),
(1, 0, 0, 1, 2),
(2, 0, 0, 1, 1);

TRUNCATE TABLE `Rating`;
INSERT INTO `Rating` (`IDRating`, `IDPhoto`, `IDUser`, `Rating`, `segnalazione`) VALUES
(1, 1, 2, 3, NULL);

TRUNCATE TABLE `Utente`;
INSERT INTO `Utente` (`IDUser`, `username`, `googleKey`, `mail`, `score`, `money`) VALUES
(10, 'michele', '12345678', 'miki426811@gmail.com', 70, 10),
(1, 'caterina', '12312345', 'caterina.battisti@studenti.unitn.it', 0, 3),
(2, 'Francesco', '87654321', 'franci@gmail.com', 0, 4);



