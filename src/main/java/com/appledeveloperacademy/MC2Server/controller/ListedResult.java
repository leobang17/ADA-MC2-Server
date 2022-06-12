package com.appledeveloperacademy.MC2Server.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter @Setter
@AllArgsConstructor
public class ListedResult<T> {
    protected int count;
    protected List<T> data;

    public ListedResult(List<T> data) {
        this.data = data;
        this.count = data.size();
    }
}
