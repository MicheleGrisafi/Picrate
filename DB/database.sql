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
	version			INT(2)
);
CREATE TABLE IF NOT EXISTS Medal(
	IDMedal			INTEGER(10) NOT NULL AUTO_INCREMENT,
	IDPhoto			INTEGER(10) NOT NULL,
	position		INTEGER(5) NOT NULL,
	CONSTRAINT PKMedal PRIMARY KEY (IDMedal),
	CONSTRAINT FKPhotoMedal FOREIGN KEY (IDPhoto) REFERENCES Photo(IDPhoto)
);



TRUNCATE TABLE `Challenge`;
INSERT INTO `Challenge` (`IDChallenge`, `description`, `title`,`image`) VALUES
(1, 'Questo challenge prevede la fotografia di un panorama', 'Panorama','1'),
(2, 'Fai una foto al tuo cane preferito', 'Cani','2'),
(3, 'Immortale la più bella scena pubblica che ci sia', 'Scena pubblica','3'),
(4, 'Scarpe di ogni tipo e colore!', 'Scarpe','4');

TRUNCATE TABLE `ChallengeSession`;
INSERT INTO `ChallengeSession` (`IDSession`, `image`, `expiration`, `stato`, `IDChallenge`) VALUES
(1, NULL, '2017-05-30 23:59:59', 0, 1),
(2, NULL, '2017-06-30 23:59:59', 0, 2),
(3, NULL, '2017-09-28 23:59:59', 1, 3),
(4, NULL, '2017-10-01 23:59:59', 1, 4);

TRUNCATE TABLE `Photo`;
INSERT INTO `Photo` (`IDPhoto`, `latitudine`, `longitudine`, `owner`, `challenge`) VALUES
(3, 0, 0, 3, 3),
(1, 0, 0, 3, 2),
(4, 0, 0, 4, 2),
(5, 0, 0, 4, 2),
(6, 0, 0, 5, 2),
(7, 0, 0, 5, 2),
(2, 0, 0, 2, 1);

TRUNCATE TABLE `Rating`;
INSERT INTO `Rating` (`IDRating`, `IDPhoto`, `IDUser`, `Rating`, `segnalazione`) VALUES
(1, 1, 2, 3, NULL);

TRUNCATE TABLE `Utente`;
INSERT INTO `Utente` (`IDUser`, `username`, `googleKey`, `mail`, `score`, `money`) VALUES
(1, 'michele', '12345678', 'miki426811@gmail.com', 70, 10),
(2, 'caterina', '12312345', 'caterina.battisti@studenti.unitn.it', 20, 3),
(3, 'Francesco', '87654321', 'franci@gmail.com', 10, 20),
(4, 'Filippo', '525253523awd', 'filippo.nardin@gmail.com', 40, 10),
(5, 'Lorenzo', 'awdawdwad', 'lorenzo.gasperotti@gmail.com', 5, 10);



