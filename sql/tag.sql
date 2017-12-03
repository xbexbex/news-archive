CREATE TABLE tag(
	lukuvinkki_id INTEGER NOT NULL UNIQUE,
	tag VARCHAR(20) NOT NULL,
	PRIMARY KEY (lukuvinkki_id, tag),
	FOREIGN KEY (lukuvinkki_id) REFERENCES lukuvinkki(id)
);
