package com.lunatech.imdb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.ws.rs.QueryParam;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestParam {
    @QueryParam("genre")
    private String genre;
    @QueryParam("page")
    private int page = 1;
    @QueryParam("perPage")
    private int perPage = 10;
}
