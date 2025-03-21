package br.com.biblioteca.mapper;

import br.com.biblioteca.domain.Book;
import br.com.biblioteca.domain.PaginationWrapper;
import org.openapitools.model.ListBooksResponse;
import org.openapitools.model.ListBooksResponseContentInner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListBooksResponseMapper {

    public ListBooksResponse toResponse(PaginationWrapper<Book> paginationWrapper) {
        ListBooksResponse listBooksResponse = new ListBooksResponse();

        listBooksResponse.setContent(toContentResponse(paginationWrapper.getContent()));
        listBooksResponse.setHasNext(paginationWrapper.getHasNext());
        listBooksResponse.setHasPrevious(paginationWrapper.getHasPrevious());
        listBooksResponse.setPage(paginationWrapper.getPage());
        listBooksResponse.setLimit(paginationWrapper.getLimit());
        listBooksResponse.setIsEmpty(paginationWrapper.getIsEmpty());
        listBooksResponse.setTotal(paginationWrapper.getTotal());
        listBooksResponse.setTotalPages(paginationWrapper.getTotalPages());

        return listBooksResponse;
    }

    private List<ListBooksResponseContentInner> toContentResponse(List<Book> books) {
        return books.stream().map(b -> {
            ListBooksResponseContentInner listBooksResponseContentInner = new ListBooksResponseContentInner();

            listBooksResponseContentInner.setStatus(ListBooksResponseContentInner.StatusEnum.fromValue(b.getStatus().getValue()));
            listBooksResponseContentInner.setId(b.getId());
            listBooksResponseContentInner.setName(b.getName());

            return listBooksResponseContentInner;
        }).toList();
    }
}
