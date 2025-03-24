package br.com.biblioteca.mapper;

import br.com.biblioteca.domain.Author;
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
class GetAuthorByBookIdRowMapperTest {

    @InjectMocks
    GetAuthorsByBookIdRowMapper tested;
    @Mock
    ResultSet resultSet;
    @Mock
    StatementContext statementContext;

    @Test
    @DisplayName("Should map row mapper")
    void shouldMapRowMapper() throws SQLException {
        when(resultSet.getString("name")).thenReturn("Name test");
        when(resultSet.getInt("id")).thenReturn(1);

        Author result = tested.map(resultSet, statementContext);

        assertAll("Assert result map", () -> {
            assertEquals("Name test", result.getName());
            assertEquals(1, result.getId());
        });
    }
}
