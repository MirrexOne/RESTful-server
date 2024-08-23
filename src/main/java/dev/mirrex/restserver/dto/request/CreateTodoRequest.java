package dev.mirrex.restserver.dto.request;

import dev.mirrex.restserver.util.ValidationConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateTodoRequest {
    @NotBlank(message = ValidationConstants.TODO_TEXT_NOT_NULL)
    @Size(min = 3, max = 160, message = ValidationConstants.TODO_TEXT_SIZE_NOT_VALID)
    private String text;
}
