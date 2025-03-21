SELECT b.id AS id,
       b.name AS name,
       b.resume AS resume,
       b.genre AS genre,
       b.release_year AS releaseYear,
       b.status AS status
FROM tbbook b
WHERE b.id = :id AND b.active IS TRUE