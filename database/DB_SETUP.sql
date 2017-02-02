CREATE TABLE UTENTE (
	id                  bigint NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	login               varchar(25) NOT NULL UNIQUE,
	passw               varchar(255) not null,
	email               varchar(255) not null UNIQUE,
	nome                char(25) not null, 
	cognome             char(25) not null,
	is_azienda          bool not null,
	descrizione_azienda varchar(255), # a check that forces not null if is_azienda is missing
	foto                blob          # allows null for debugging purpose
);

CREATE TABLE ARTICOLO (
	id          bigint       NOT NULL PRIMARY KEY AUTO_INCREMENT ,
	nome        varchar(255) NOT NULL ,
	id_creatore bigint       NOT NULL ,
	descrizione varchar(255) NOT NULL,
	foto        blob,
	FOREIGN KEY (id_creatore) REFERENCES UTENTE(id),
	UNIQUE(nome,id_creatore)
);

#@FIX a check that forces that if id_venditore is null than id_compratore=id_creatore is missing
CREATE TABLE TRANSAZIONE (
	id            bigint NOT NULL PRIMARY KEY AUTO_INCREMENT ,
	id_articolo   bigint NOT NULL ,
	id_compratore bigint NOT NULL ,
	id_venditore  bigint          ,#NULL value identifies that id_compratore == id_creatore
	coordinate    float(10,6)     ,#allows null for debugging purpose
	FOREIGN KEY (id_articolo)   REFERENCES ARTICOLO(id),
	FOREIGN KEY (id_compratore) REFERENCES UTENTE(id),
	FOREIGN KEY (id_venditore ) REFERENCES UTENTE(id),
	UNIQUE(id_articolo,id_compratore,id_venditore)
);
