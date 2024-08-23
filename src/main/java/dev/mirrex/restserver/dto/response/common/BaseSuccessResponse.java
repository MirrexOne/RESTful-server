package dev.mirrex.restserver.dto.response.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseSuccessResponse {

    private Integer statusCode = 0;

    private Boolean success = true;

    public BaseSuccessResponse(Integer statusCode) {
        this.statusCode = statusCode;
    }
}
