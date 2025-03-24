package br.com.biblioteca.mapper;

import br.com.biblioteca.domain.Author;
import br.com.biblioteca.domain.Book;
import br.com.biblioteca.domain.StatusEnum;
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
class GetBookByBookIdRowMapperTest {

    @InjectMocks
    GetBookByBookIdRowMapper tested;
    @Mock
    ResultSet resultSet;
    @Mock
    StatementContext statementContext;

    @Test
    @DisplayName("Should map row mapper")
    void shouldMapRowMapper() throws SQLException {
        when(resultSet.getString("name")).thenReturn("Name test");
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("resume")).thenReturn("Resume test");
        when(resultSet.getString("releaseYear")).thenReturn("2020");
        when(resultSet.getString("genre")).thenReturn("Genre test");
        when(resultSet.getString("status")).thenReturn("AVAILABLE");

        Book result = tested.map(resultSet, statementContext);

        assertAll("Assert result map", () -> {
            assertEquals("Name test", result.getName());
            assertEquals(1, result.getId());
            assertEquals("Resume test", result.getResume());
            assertEquals("2020", result.getReleaseYear());
            assertEquals("Genre test", result.getGenre());
            assertEquals(StatusEnum.AVAILABLE, result.getStatus());
        });
    }
}
