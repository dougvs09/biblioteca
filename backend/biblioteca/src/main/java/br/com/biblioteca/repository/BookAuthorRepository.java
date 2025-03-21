package br.com.biblioteca.repository;

import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.springframework.stereotype.Repository;

@Repository
@UseClasspathSqlLocator
public interface BookAuthorRepository {

    @SqlUpdate
    @GetGeneratedKeys
    Integer create(@Bind("bookId") Integer bookId, @Bind("authorId") Integer authorId);
}
