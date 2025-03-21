package br.com.biblioteca.mapper;

import br.com.biblioteca.domain.User;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GetUserByEmailRowMapper implements RowMapper<User> {


    @Override
    public User map(ResultSet rs, StatementContext ctx) throws SQLException {
        return User.of(rs.getInt("id"), rs.getString("name"), rs.getString("email"),
                rs.getString("password"));
    }
}
