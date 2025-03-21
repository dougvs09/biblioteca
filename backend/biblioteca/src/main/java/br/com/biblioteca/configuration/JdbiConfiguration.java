package br.com.biblioteca.configuration;

import br.com.biblioteca.repository.AuthorRepository;
import br.com.biblioteca.repository.BookAuthorRepository;
import br.com.biblioteca.repository.BookRepository;
import br.com.biblioteca.repository.UserRepository;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.spi.JdbiPlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import javax.sql.DataSource;
import java.util.List;

@Configuration
public class JdbiConfiguration {

    @Bean
    public Jdbi jdbi(DataSource ds, List<JdbiPlugin> plugins, List<RowMapper<?>> mappers) {
        TransactionAwareDataSourceProxy proxy = new TransactionAwareDataSourceProxy(ds);
        Jdbi jdbi = Jdbi.create(proxy);
        plugins.forEach(jdbi::installPlugin);
        mappers.forEach(jdbi::registerRowMapper);
        return jdbi;
    }

    @Bean
    public JdbiPlugin sqlObjectPlugin() {
        return new SqlObjectPlugin();
    }

    @Bean
    @Primary
    public BookRepository bookRepository(Jdbi jdbi) {
        return jdbi.onDemand(BookRepository.class);
    }

    @Bean
    @Primary
    public BookAuthorRepository bookAuthorRepository(Jdbi jdbi) {
        return jdbi.onDemand(BookAuthorRepository.class);
    }

    @Bean
    @Primary
    public AuthorRepository authorRepository(Jdbi jdbi) {
        return jdbi.onDemand(AuthorRepository.class);
    }

    @Bean
    @Primary
    public UserRepository userRepository(Jdbi jdbi) {
        return jdbi.onDemand(UserRepository.class);
    }
}
