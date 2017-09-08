/*
CREATE DATABASE foto;
USE foto;*/

CREATE TABLE Utente(
	IDUser			INT NOT NULL AUTO_INCREMENT,
	username		VARCHAR(20) NULL,
	googleKey		VARCHAR(255) NULL UNIQUE,
	mail			VARCHAR(255) NOT NULL,
	score			SMALLINT,
	money			SMALLINT,
	CONSTRAINT PKUser PRIMARY KEY (IDUser)
);
CREATE TABLE Challenge(
	IDChallenge		SMALLINT NOT NULL AUTO_INCREMENT,
	description		VARCHAR(255) NOT NULL,
	title			VARCHAR(20)	NOT NULL,
	image			VARCHAR(255) NOT NULL,
	CONSTRAINT PKChallenge PRIMARY KEY(IDChallenge)
);
CREATE TABLE ChallengeSession(
	IDSession		INT NOT NULL AUTO_INCREMENT,
	image			VARCHAR(255) DEFAULT NULL,
	expiration		DATETIME,
	stato			INT(1) NOT NULL,	/* 1 = attivo, 0 = valutazione, -1 = scaduto*/
	IDChallenge		SMALLINT NOT NULL,
	CONSTRAINT PKChallengeSession PRIMARY KEY(IDSession),
	CONSTRAINT FKChallengeOfTheSession FOREIGN KEY (IDChallenge) REFERENCES Challenge(IDChallenge)
);
CREATE TABLE Photo(
	IDPhoto			INTEGER(10)	NOT NULL AUTO_INCREMENT,
	latitudine		DOUBLE,
	longitudine		DOUBLE,
	owner			INT	NOT NULL,
	challenge		INT NOT NULL,
	
	CONSTRAINT PKPhoto PRIMARY KEY (IDPhoto),
	CONSTRAINT FKPhotoOwner FOREIGN KEY (owner) REFERENCES Utente(IDUser),
	CONSTRAINT FKPhotoChallenge FOREIGN KEY (challenge) REFERENCES ChallengeSession(IDSession)
);
CREATE TABLE Rating(
	IDRating		SMALLINT NOT NULL AUTO_INCREMENT,
	IDPhoto			INTEGER(10) NOT NULL,
	IDUser			INT	NOT NULL,
	Rating			INT(1) DEFAULT NULL,
	
	CONSTRAINT PKRating		PRIMARY KEY (IDRating),
	CONSTRAINT FKRatingPhoto FOREIGN KEY (IDPhoto) REFERENCES Photo(IDPhoto),
	CONSTRAINT FKRatingUser FOREIGN KEY (IDUser) REFERENCES Utente(IDUser)
);
CREATE TABLE dataInfo(
	picturesFolder	VARCHAR(255),
	version			INT(2)
);

/* QUERY PROVA */
INSERT INTO Utente 				VALUES(10,"michele","12345678","miki426811@gmail.com",0,0);
INSERT INTO Challenge 			VALUES(1,"Questo challenge prevede la fotografia di un panorama","Panorama","http://fotografandoapp.altervista.org/challengePictures/panorama1");
INSERT INTO Challenge 			VALUES(2,"Fai una foto al tuo cane preferito","Cani","http://fotografandoapp.altervista.org/challengePictures/panorama1");
INSERT INTO Challenge 			VALUES(3,"Immortale la più bella scena pubblica che ci sia","Scena pubblica","http://fotografandoapp.altervista.org/challengePictures/panorama1");

INSERT INTO ChallengeSession 	VALUES(1,NULL,'2017-05-30 23:59:59',1,1);
INSERT INTO ChallengeSession 	VALUES(2,NULL,'2017-06-30 23:59:59',1,2);
INSERT INTO ChallengeSession 	VALUES(3,NULL,'2017-06-15 23:59:59',1,3);

INSERT INTO Utente				VALUES(1,"Caterina",12345679,"caterina@gmail");
INSERT INTO Utente				VALUES(2,"Francesco",12345671,"francesco@gmail");

INSERT INTO Photo				VALUES(1,0,0,1,1);
INSERT INTO Photo				VALUES(2,0,0,1,2);
INSERT INTO Photo				VALUES(3,0,0,1,3);

