SELECT a.id AS id, a.name AS name
FROM tbauthor a
WHERE a.name = :name AND a.active IS TRUE