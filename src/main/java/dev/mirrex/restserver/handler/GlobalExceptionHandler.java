package dev.mirrex.restserver.handler;

import dev.mirrex.restserver.dto.response.common.BaseSuccessResponse;
import dev.mirrex.restserver.dto.response.common.CustomSuccessResponse;
import dev.mirrex.restserver.util.ErrorCode;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomSuccessResponse<Void>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<ErrorCode> errorCodes = ex.getBindingResult().getAllErrors().stream()
                .map(error -> ErrorCode.fromMessage(error.getDefaultMessage()))
                .distinct()
                .toList();
        List<Integer> codes = errorCodes.stream().map(ErrorCode::getCode).collect(Collectors.toList());
        return ResponseEntity.badRequest()
                .body(new CustomSuccessResponse<>(codes, true));
    }

    @ExceptionHandler({ResourceNotFoundException.class, NoHandlerFoundException.class})
    public ResponseEntity<Map<String, Object>> handleNotFoundException(Exception ex) {
        Map<String, Object> body = Map.of(
                "error", "Not Found"
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomSuccessResponse<Void>> handleCustomException(CustomException ex) {
        List<Integer> codes = List.of(ex.getErrorCode().getCode());
        return ResponseEntity.badRequest()
                .body(new CustomSuccessResponse<>(codes, true));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseSuccessResponse> handleGenericException(Exception ex) {
        return ResponseEntity.badRequest()
                .body(new BaseSuccessResponse(ErrorCode.UNKNOWN.getCode()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BaseSuccessResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest()
                .body(new BaseSuccessResponse(ErrorCode.HTTP_MESSAGE_NOT_READABLE_EXCEPTION.getCode(), true));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CustomSuccessResponse<Void>> handleConstraintViolation(ConstraintViolationException ex) {
        List<ErrorCode> errorCodes = ex.getConstraintViolations().stream()
                .map(violation -> ErrorCode.fromMessage(violation.getMessage()))
                .toList();
        List<Integer> codes = errorCodes.stream().map(ErrorCode::getCode).collect(Collectors.toList());
        return ResponseEntity.badRequest()
                .body(new CustomSuccessResponse<>(codes, true));
    }
}
