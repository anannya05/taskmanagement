package com.dev.taskmanagement.dto;

import com.dev.taskmanagement.model.Task;
import lombok.Data;
import org.springframework.data.domain.Page;
import java.util.List;
@Data
public class TaskPageResponse {

    private List<Task> tasks;  // List of tasks
    private PaginationInfo pageable;  // Pagination details
    private boolean last;
    private int totalPages;
    private long totalElements;
    private int size;
    private int number;
    private int numberOfElements;
    private boolean first;
    private boolean empty;

    // Constructor to populate from Page<Task>
    public TaskPageResponse(Page<Task> page) {
        this.tasks = page.getContent();  // Tasks list
        this.pageable = new PaginationInfo(
                page.getPageable().getPageNumber(),
                page.getPageable().getPageSize(),
                page.getPageable().getSort().isSorted(),
                page.getPageable().getSort().isUnsorted(),
                page.isEmpty()
        );
        this.last = page.isLast();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.size = page.getSize();
        this.number = page.getNumber();
        this.numberOfElements = page.getNumberOfElements();
        this.first = page.isFirst();
        this.empty = page.isEmpty();
    }

}
