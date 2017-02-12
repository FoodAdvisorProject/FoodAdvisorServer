# Transaction (id_article,id_buyer) tuple has to be unique, 
# otherwise the logic of getArticleTravel(id_article,id_buyer) will be broken
# this can be an issue

SET foreign_key_checks=0;
DROP TABLE IF EXISTS ARTICOLO,TRANSAZIONE,UTENTE; 
SET foreign_key_checks=1;

CREATE TABLE UTENTE (
	id                     bigint       NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	login                  varchar(25)  NOT NULL UNIQUE,
	passw                  varchar(255) NOT NULL,
	email                  varchar(255) NOT NULL UNIQUE,
	name                   char(25)     NOT NULL , 
	second_name            char(25)     NOT NULL ,
	is_enterprise          bool         NOT NULL ,
	enterprise_description varchar(255), # a check that forces not null if is_azienda is missing
	photo                  blob          # allows null for debugging purpose
);

CREATE TABLE ARTICOLO (
	id           bigint       NOT NULL PRIMARY KEY AUTO_INCREMENT ,
	name         varchar(255) NOT NULL ,
	id_creator   bigint       NOT NULL ,
	description  varchar(255) NOT NULL,
	photo        blob,
	is_valid     bool         NOT NULL DEFAULT 1,
	FOREIGN KEY (id_creator) REFERENCES UTENTE(id),
	UNIQUE(name,id_creator)
);

#@FIX a check that forces that if id_venditore is null than id_compratore=id_creatore is missing
CREATE TABLE TRANSAZIONE (
	id            bigint NOT NULL PRIMARY KEY AUTO_INCREMENT ,
	id_article    bigint NOT NULL ,
	id_buyer      bigint NOT NULL ,
	id_seller     bigint          ,#NULL value identifies that id_buyer == id_creator
	longitude     float(10,6)     ,#allows null for debugging purpose
	latitude      float(10,6)     ,
	FOREIGN KEY   (id_article)  REFERENCES ARTICOLO(id),
	FOREIGN KEY   (id_buyer)    REFERENCES UTENTE(id),
	FOREIGN KEY   (id_seller )  REFERENCES UTENTE(id),
	UNIQUE(id_article,id_buyer,id_seller)
);

