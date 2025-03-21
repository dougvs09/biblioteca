package br.com.biblioteca.repository;

import br.com.biblioteca.domain.Book;
import br.com.biblioteca.domain.StatusEnum;
import br.com.biblioteca.mapper.ListBooksRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.BindList;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.UseRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@UseClasspathSqlLocator
public interface BookRepository {

    @SqlUpdate
    @GetGeneratedKeys
    Integer create(@BindBean("book") Book book);

    @SqlQuery
    Integer countTotal(@Bind("author") String author,
                       @Bind("status") StatusEnum status,
                       @BindList(value = "genres", onEmpty = BindList.EmptyHandling.NULL) List<String> genres,
                       @BindList(value = "releaseYears", onEmpty = BindList.EmptyHandling.NULL) List<String> releaseYears);

    @SqlQuery
    @UseRowMapper(ListBooksRowMapper.class)
    List<Book> list(@Bind("limit") Integer limit,
                    @Bind("page") Integer page,
                    @Define("order") String order,
                    @Bind("author") String author,
                    @Bind("status") StatusEnum status,
                    @BindList(value = "genres", onEmpty = BindList.EmptyHandling.NULL) List<String> genres,
                    @BindList(value = "releaseYears", onEmpty = BindList.EmptyHandling.NULL) List<String> releaseYears);

}
