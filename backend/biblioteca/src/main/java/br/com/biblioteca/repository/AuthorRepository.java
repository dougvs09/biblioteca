package br.com.biblioteca.repository;

import br.com.biblioteca.domain.Author;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.springframework.stereotype.Repository;

@Repository
@UseClasspathSqlLocator
public interface AuthorRepository {

    @SqlUpdate
    @GetGeneratedKeys
    Integer create(@BindBean("author") Author author);
}
