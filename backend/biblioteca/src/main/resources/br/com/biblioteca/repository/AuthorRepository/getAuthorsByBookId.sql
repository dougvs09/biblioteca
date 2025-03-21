SELECT a.name AS name
FROM tbauthor a
INNER JOIN tbbookauthor ba ON ba.authorId = a.id
WHERE ba.bookid = :id AND a.active IS TRUE