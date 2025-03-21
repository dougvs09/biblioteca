SELECT count(*) FROM tbbook b
WHERE (COALESCE(<genres>) IS NULL OR b.genre IN (<genres>))
      AND (COALESCE(<releaseYears>) IS NULL OR b.release_year IN (<releaseYears>))
      AND (:status IS NULL OR b.status = CAST(:status AS status))
      AND b.active IS TRUE