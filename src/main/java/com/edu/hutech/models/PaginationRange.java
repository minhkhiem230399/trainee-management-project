package com.edu.hutech.models;

import lombok.Data;

@Data
public class PaginationRange {

    private int currentPage;

    private int totalPage;

    private int min;

    private int max;

}
