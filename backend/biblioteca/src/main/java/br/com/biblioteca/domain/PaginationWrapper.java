package br.com.biblioteca.domain;

import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter
public class PaginationWrapper<T> {

    private final Integer page;
    private final Integer limit;
    private final Integer totalPages;
    private final Boolean hasPrevious;
    private final Boolean hasNext;
    private final Boolean isEmpty;
    private final Integer total;
    private final List<T> content;

    public PaginationWrapper(Integer page,
                             Integer limit,
                             Integer total,
                             List<T> content) {
        this.content = Objects.requireNonNullElse(content, Collections.emptyList());
        this.page = page;
        this.limit = limit;
        this.totalPages = (int) Math.ceil(total / (limit * 1f));
        this.hasPrevious = total > 0 && (page > 1 && page <= this.totalPages + 1);
        this.hasNext = total > 0 && (page * limit) < total;
        this.isEmpty = total <= 0 || this.content.isEmpty();
        this.total = total;
    }
}
