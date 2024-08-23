package dev.mirrex.restserver.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class PagedTodoResponse {

    private List<TodoResponse> content;

    private Long numberOfElements;

    private Long ready;

    private Long notReady;

    private Integer page;

    private Integer size;

    private Integer totalPages;
}
