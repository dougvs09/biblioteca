CREATE TYPE status AS ENUM ('AVAILABLE', 'UNAVAILABLE');

CREATE TABLE tbuser (
	id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	name VARCHAR(100) NOT NULL,
	email VARCHAR(255) UNIQUE NOT NULL,
	password VARCHAR(255) NOT NULL,
	active BOOLEAN NOT NULL
);

CREATE TABLE tbauthor (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(100) NOT NULL,
    active BOOLEAN NOT NULL
);

CREATE TABLE tbbook (
	id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	name VARCHAR(255) NOT NULL,
	resume TEXT,
	release_year VARCHAR(4),
	genre VARCHAR(100),
	status status NOT NULL,
	active BOOLEAN NOT NULL
);

CREATE TABLE tbbookauthor (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    authorid INTEGER NOT NULL REFERENCES tbauthor,
    bookid INTEGER NOT NULL REFERENCES tbbook,
    active BOOLEAN NOT NULL
);

CREATE INDEX idx_user
ON tbuser(id, email, active);

CREATE INDEX idx_author
ON tbauthor(id, name, active);

CREATE INDEX idx_book
ON tbbook(id, name, active);

CREATE INDEX idx_tbbookauthor
ON tbbookauthor(id, bookid, authorid);