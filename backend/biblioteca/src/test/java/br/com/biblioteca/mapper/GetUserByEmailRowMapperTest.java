package br.com.biblioteca.mapper;

import br.com.biblioteca.domain.Author;
import br.com.biblioteca.domain.User;
import org.jdbi.v3.core.statement.StatementContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetUserByEmailRowMapperTest {

    @InjectMocks
    GetUserByEmailRowMapper tested;
    @Mock
    ResultSet resultSet;
    @Mock
    StatementContext statementContext;

    @Test
    @DisplayName("Should map row mapper")
    void shouldMapRowMapper() throws SQLException {
        when(resultSet.getString("name")).thenReturn("Name test");
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("email")).thenReturn("Email test");
        when(resultSet.getString("password")).thenReturn("Password test");

        User result = tested.map(resultSet, statementContext);

        assertAll("Assert result map", () -> {
            assertEquals("Name test", result.getName());
            assertEquals(1, result.getId());
            assertEquals("Email test", result.getEmail());
            assertEquals("Password test", result.getPassword());
        });
    }
}
