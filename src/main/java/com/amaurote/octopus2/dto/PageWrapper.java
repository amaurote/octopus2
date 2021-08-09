package com.amaurote.octopus2.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class PageWrapper<T> implements Serializable {

    private List<T> elements;

    private long totalCount;

    private long totalPages;
}
