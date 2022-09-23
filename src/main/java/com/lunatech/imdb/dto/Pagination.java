package com.lunatech.imdb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pagination {
    private int page;
    private int perPage;
    private int start;

    public void setPage(int page) {
        this.page = (page > 0) ? page : 1;
    }

    public void setPerPage(int perPage) {
        this.perPage = (perPage > 50) ? 50 : (perPage > 0 && perPage <= 50) ? perPage : 10;
    }

    public int getStart() {
        this.start = (page == 1) ? 0 : ((page * perPage) - (perPage - 1));
        return this.start;
    }
}
