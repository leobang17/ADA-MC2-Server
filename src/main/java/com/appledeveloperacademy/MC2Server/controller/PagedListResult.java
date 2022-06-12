package com.appledeveloperacademy.MC2Server.controller;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Data
@Getter @Setter
@NoArgsConstructor
public class PagedListResult<T> {
    private int offset;
    private int limit;
    private int count;
    private int totalCount;

    private List<T> data;

    public PagedListResult(List<T> data, int offset, int limit) {
        this.data = data;
        this.offset = offset;
        this.limit = limit;
        this.count = data.size();
    }
}
