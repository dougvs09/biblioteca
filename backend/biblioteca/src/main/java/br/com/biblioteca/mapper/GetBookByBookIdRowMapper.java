package br.com.biblioteca.mapper;

import br.com.biblioteca.domain.Book;
import br.com.biblioteca.domain.StatusEnum;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GetBookByBookIdRowMapper implements RowMapper<Book> {

    @Override
    public Book map(ResultSet rs, StatementContext ctx) throws SQLException {
        return Book.of(rs.getInt("id"), rs.getString("name"), rs.getString("resume"),
                rs.getString("releaseYear"), rs.getString("genre"),
                StatusEnum.valueOf(rs.getString("status")));
    }
}
