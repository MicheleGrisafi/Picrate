CREATE DATABASE FotografandoDB;
USE FotografandoDB;
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
	IDPhoto			INTEGER(10) NOT NULL,
	IDUser			INT	NOT NULL,
	Rating			INT(1) DEFAULT NULL,
	
	CONSTRAINT FKRatingPhoto FOREIGN KEY (IDPhoto) REFERENCES Photo(IDPhoto),
	CONSTRAINT FKRatingUser FOREIGN KEY (IDUser) REFERENCES Utente(IDUser)
);
CREATE TABLE dataInfo(
	picturesFolder	VARCHAR(255),
	version			INT(2)
);

/* QUERY PROVA */
INSERT INTO Utente 				VALUES(10,"michele","12345678","miki426811@gmail.com",0,0);
INSERT INTO Challenge 			VALUES(20,"Descrizione di prova","Panorama","127.0.0.1/challengePictures/panorama1");
INSERT INTO ChallengeSession 	VALUES(30,NULL,'2008-11-11 23:59:59',1,20);

