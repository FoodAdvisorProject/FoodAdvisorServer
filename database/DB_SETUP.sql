CREATE TABLE UTENTE (
	id                  bigint NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	login               varchar(25) NOT NULL UNIQUE,
	passw               varchar(255) not null,
	email               varchar(255) not null unique,
	nome                char(25) not null, cognome char(25) not null,
	is_azienda          bool not null,
  descrizione_azienda varchar(255), # a check that forces not null if is_azienda is missing
  foto                blob         # allows null for debugging purpose
);

CREATE TABLE ARTICOLO (
	  id          bigint       NOT NULL PRIMARY KEY AUTO_INCREMENT ,
    nome        varchar(255) NOT NULL UNIQUE,
    id_creatore bigint       NOT NULL REFERENCES UTENTE(id),
    descrizione varchar(255) NOT NULL,
    foto        blob         #allows null for debugging purpose 
	
);

CREATE TABLE TRANSAZIONE (
	  id            bigint NOT NULL PRIMARY KEY AUTO_INCREMENT ,
    id_articolo   bigint NOT NULL REFERENCES ARTICOLO(id),
    id_compratore bigint NOT NULL REFERENCES UTENTE(id),
    id_venditore  bigint NOT NULL REFERENCES UTENTE(id),
    coordinate    float(10,6) #allows null for debugging purpose
);
