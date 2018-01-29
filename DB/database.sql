/*
CREATE DATABASE foto;
USE foto;*/
SET FOREIGN_KEY_CHECKS = 0; 
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
	expiration		DATETIME,
	stato			INT(1) NOT NULL,	/* 1 = rating, 0 = scaduto, 2 = attivo, 3 = upcoming*/
	IDChallenge		SMALLINT NOT NULL,
	CONSTRAINT PKChallengeSession PRIMARY KEY(IDSession),
	CONSTRAINT FKChallengeOfTheSession FOREIGN KEY (IDChallenge) REFERENCES Challenge(IDChallenge)
);
CREATE TABLE IF NOT EXISTS Photo(
	IDPhoto			INTEGER(10)	NOT NULL AUTO_INCREMENT,
	latitudine		DOUBLE,
	longitudine		DOUBLE,
	owner			INT	NOT NULL,
	challengeSession		INT NOT NULL,
	
	CONSTRAINT PKPhoto PRIMARY KEY (IDPhoto),
	CONSTRAINT FKPhotoOwner FOREIGN KEY (owner) REFERENCES Utente(IDUser),
	CONSTRAINT FKPhotoChallenge FOREIGN KEY (challengeSession) REFERENCES ChallengeSession(IDSession)
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
(1, 'Scova un panorama mozzafiato e fotografalo! Panorama di mare, di montagna o di pianura, non ci sono limiti!', 'Panorama','1'),
(2, "Immortala il mondo canino in tutta la sue bellezza! Lasciati trasportare dalla passione per il miglior amico dell'uomo!", 'Cani','2'),
(3, 'Una scena pubblica può essere tante cose, metti in mostra la tua idea!', 'Scena pubblica','3'),
(4, 'Scarpe di ogni tipo e colore, calzate e non calzate! Un mondo elegante e variopinto!', 'Scarpe','4'),
(5, "Aspetta ad usare le posate, usa la fotocamera prima per cogliere l'essenza del tuo piatto!",'Cibo','5'),
(6, "L'eleganza può celarsi in ogni cosa, immortalala ed esponila!",'Eleganza','6');

/* 1 = rating, 0 = scaduto, 2 = attivo, 3 = upcoming*/
TRUNCATE TABLE `ChallengeSession`;
INSERT INTO `ChallengeSession` (`IDSession`, `expiration`, `stato`, `IDChallenge`) VALUES
(1, '2017-05-30 23:59:59', 1, 1), 	/*Panorama - rating*/
(2, '2017-06-30 23:59:59', 1, 2), 	/*Cani - rating*/
(3, '2018-11-02 23:59:59', 2, 3), 	/*Scena pubblica - attiva*/
(4, '2018-10-02 12:00:00', 2, 4), 	/*Scarpe - attiva*/
(5, '2017-9-01 23:59:59', 0, 5), 	/*Cibo - scaduta*/
(6, '2017-9-01 23:59:59', 0, 6), 	/*Eleganza - scaduta*/
(7, '2018-12-02 12:00:00', 2, 5), 	/*Cibo - attiva*/
(8, '2017-12-02 12:00:00', 1, 6); 	/*Eleganza - rating*/

TRUNCATE TABLE `Utente`;
INSERT INTO `Utente` (`IDUser`, `username`, `googleKey`, `mail`, `score`, `money`) VALUES
(1, 'michele', '12345678', 'miki426811@gmail.com', 70, 60),
(2, 'caterina', '12312345', 'caterina.battisti@studenti.unitn.it', 20, 3),
(3, 'Francesco', '87654321', 'franci@gmail.com', 10, 20),
(4, 'Filippo', '525253523awd', 'filippo.nardin@gmail.com', 40, 10),
(5, 'Lorenzo', 'awdawdwad', 'lorenzo.gasperotti@gmail.com', 5, 10),
(6, 'Michele42', '115493936223318510837', 'miki426811@gmail.com', 0, 11),
(7, 'Tester', '103483067382538662122', 'caterina.battisti@studenti.unitn.it', 0, 1),
(8, 'MrCappello', '108668301161844492370', 'pasqua.gianluca@gmail.com', 0, 105),
(9, 'giacomo_gr', '105948216712354224440', 'grisafi.giacomo@gmail.com', 0, 103),
(10, 'Catykella ', '111315445737180212473', 'caccaterina11@gmail.com', 0, 104);

TRUNCATE TABLE `Photo`;
INSERT INTO `Photo` (`IDPhoto`, `latitudine`, `longitudine`, `owner`, `challengeSession`) VALUES
(3, 0, 0, 3, 3), /* Scena pubblica, attiva - francesco*/
(1, 0, 0, 3, 2), /* Cani, rating - francesco*/
(4, 0, 0, 4, 2), /* Cani, rating - Filippo*/
(5, 0, 0, 4, 2), /* Cani, rating - Filippo*/
(6, 41.9028, 12.4964, 5, 2), /* Cani, rating - Lorenzo*/
(7, 41.9028, 12.4964, 5, 2), /* Cani, rating - Lorenzo*/
(2, 41.9028, 12.4964, 2, 1), /* Panorama, rating - caterina*/
(8, 41.9028, 12.4964, 1, 5), /* Cibo, scaduta - michele*/
(9, 41.9028, 12.4964, 2, 6), /* Eleganza, scaduta - caterina*/
(10, 41.9028, 12.4964, 3, 5), /* Cibo, scaduta - francesco*/
(11, 41.9028, 12.4964, 4, 6), /* Eleganza, scaduta - Filippo*/
(12, 41.9028, 12.4964, 2, 5), /* Cibo, scaduta - caterina*/
(13, 0, 0, 1, 6), /* Eleganza, scaduta - michele*/
(23, 0, 0, 9, 4), /* Scarpe, attiva - giacomo_gr*/
(22, 0, 0, 9, 3), /* Scena pubblica, attiva - giacomo_gr*/
(24, 41.9028, 12.4964, 2, 1), /* Panorama, rating - caterina*/
(25, 41.9028, 12.4964, 6, 1), /* Panorama, rating - michele42*/
(26, 41.9028, 12.4964, 4, 1), /* Panorama, rating - filippo*/
(27, 41.9028, 12.4964, 4, 8), /* Eleganza, rating - filippo*/
(28, 41.9028, 12.4964, 5, 8), /* Eleganza, rating - Lorenzo*/
(29, 41.9028, 12.4964, 6, 8), /* Eleganza, rating - Michele42*/
(30, 41.9028, 12.4964, 7, 8); /* Eleganza, rating - Tester*/

TRUNCATE TABLE `Medal`;
INSERT INTO `Medal` (`IDMedal`,`IDPhoto`,`position`)VALUES
(1,8,1),
(2,9,1),
(3,10,2),
(4,11,2),
(5,12,3),
(6,13,3);

TRUNCATE TABLE `Rating`;
INSERT INTO `Rating` (`IDRating`, `IDPhoto`, `IDUser`, `Rating`, `segnalazione`) VALUES
(1, 1, 2, 3, NULL);
SET FOREIGN_KEY_CHECKS = 1; 




