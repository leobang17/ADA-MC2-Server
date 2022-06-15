package com.appledeveloperacademy.MC2Server.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter @Setter
public class ListedRequest<T> {
    private List<T> data;
}
