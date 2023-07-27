package ru.practicum.shareit.common;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class CustomPageRequest implements Pageable {
    private final int offset;
    private final int limit;
    private final Sort sort;

    public CustomPageRequest(int offset, int limit, Sort sort) {
        if (offset < 0) throw new IllegalArgumentException("Начальный элемент страницы не может быть меньше нуля");
        if (limit <= 0) throw new IllegalArgumentException("Лимит страницы не может быть меньше или равен нулю");
        this.offset = offset;
        this.limit = limit;
        this.sort = sort;
    }

    @Override
    public int getPageNumber() {
        return offset / limit;
    }

    @Override
    public int getPageSize() {
        return limit;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Pageable next() {
        return new CustomPageRequest((int) (getOffset() + getPageSize()), getPageSize(), getSort());
    }

    public CustomPageRequest previous() {
        return hasPrevious() ? new CustomPageRequest((int) (getOffset() - getPageSize()), getPageSize(), getSort()) : this;
    }

    @Override
    public Pageable previousOrFirst() {
        return hasPrevious() ? previous() : first();
    }

    @Override
    public Pageable first() {
        return new CustomPageRequest(0, getPageSize(), getSort());
    }

    @Override
    public Pageable withPage(int pageNumber) {
        return Pageable.ofSize(pageNumber);
    }

    @Override
    public boolean hasPrevious() {
        return offset > limit;
    }
}
