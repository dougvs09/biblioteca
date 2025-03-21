INSERT INTO tbbook(name, resume, release_year, genre, active, status)
VALUES (:book.name, :book.resume, :book.releaseYear, :book.genre, :book.active, CAST(:book.status AS status))