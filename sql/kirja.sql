CREATE TABLE kirja (
	id SERIAL NOT NULL UNIQUE,
	lukuvinkki_id INTEGER NOT NULL,
	nimi VARCHAR(30),
	PRIMARY KEY (id),
	FOREIGN KEY (lukuvinkki_id) REFERENCES lukuvinkki(id)
);
