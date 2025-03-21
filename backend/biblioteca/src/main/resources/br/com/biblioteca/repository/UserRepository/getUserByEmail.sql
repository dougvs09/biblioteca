SELECT u.id, u.email, u.name, u.password FROM tbuser u
WHERE u.email = :email AND u.active IS TRUE