package br.com.biblioteca.repository;

import br.com.biblioteca.domain.Book;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.springframework.stereotype.Repository;

@Repository
@UseClasspathSqlLocator
public interface BookRepository {

    @SqlUpdate
    @GetGeneratedKeys
    Integer create(@BindBean("book") Book book);
}
