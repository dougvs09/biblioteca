package br.com.biblioteca.repository;

import br.com.biblioteca.domain.Author;
import br.com.biblioteca.mapper.GetAuthorByNameRowMapper;
import br.com.biblioteca.mapper.GetAuthorsByBookIdRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.UseRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@UseClasspathSqlLocator
public interface AuthorRepository {

    @SqlUpdate
    @GetGeneratedKeys
    Integer create(@BindBean("author") Author author);

    @SqlQuery
    @UseRowMapper(GetAuthorsByBookIdRowMapper.class)
    List<Author> getAuthorsByBookId(@Bind("id") Integer id);

    @SqlQuery
    @UseRowMapper(GetAuthorByNameRowMapper.class)
    Optional<Author> getAuthorByName(@Bind("name") String name);
}
