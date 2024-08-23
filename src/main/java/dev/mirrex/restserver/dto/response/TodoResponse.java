package dev.mirrex.restserver.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TodoResponse {

    private Long id;

    private String text;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Boolean status;
}
