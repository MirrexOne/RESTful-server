package dev.mirrex.restserver.dto.request;

import dev.mirrex.restserver.util.ValidationConstants;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateTodoStatusRequest {
    @NotNull(message = ValidationConstants.TODO_STATUS_NOT_NULL)
    private Boolean status;
}
