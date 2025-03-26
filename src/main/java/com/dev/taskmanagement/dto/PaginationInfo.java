package com.dev.taskmanagement.dto;

import lombok.Data;

@Data
public class PaginationInfo {

    private int pageNumber;
    private int pageSize;
    private boolean sorted;
    private boolean unsorted;
    private boolean empty;

    // Constructor
    public PaginationInfo(int pageNumber, int pageSize, boolean sorted, boolean unsorted, boolean empty) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sorted = sorted;
        this.unsorted = unsorted;
        this.empty = empty;
    }
}

