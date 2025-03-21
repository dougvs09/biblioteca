package br.com.biblioteca.repository;

import br.com.biblioteca.domain.User;
import br.com.biblioteca.mapper.GetUserByEmailRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.UseRowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@UseClasspathSqlLocator
public interface UserRepository {

    @SqlUpdate
    @GetGeneratedKeys
    Integer create(@BindBean("user") User user);

    @SqlQuery
    @UseRowMapper(GetUserByEmailRowMapper.class)
    Optional<User> getUserByEmail(@Bind("email") String email);
}
