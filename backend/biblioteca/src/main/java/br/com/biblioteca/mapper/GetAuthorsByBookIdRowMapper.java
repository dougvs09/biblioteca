package br.com.biblioteca.mapper;

import br.com.biblioteca.domain.Author;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GetAuthorsByBookIdRowMapper implements RowMapper<Author> {

    @Override
    public Author map(ResultSet rs, StatementContext ctx) throws SQLException {
        return Author.of(rs.getString("name"));
    }
}
