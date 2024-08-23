package dev.mirrex.restserver.controller;

import dev.mirrex.restserver.dto.request.CreateTodoRequest;
import dev.mirrex.restserver.dto.request.UpdateTodoStatusRequest;
import dev.mirrex.restserver.dto.request.UpdateTodoTextRequest;
import dev.mirrex.restserver.dto.response.PagedTodoResponse;
import dev.mirrex.restserver.dto.response.common.BaseSuccessResponse;
import dev.mirrex.restserver.dto.response.common.CustomSuccessResponse;
import dev.mirrex.restserver.dto.response.TodoResponse;
import dev.mirrex.restserver.service.TodoService;
import dev.mirrex.restserver.util.ValidationConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/todo")
@RequiredArgsConstructor
@Validated
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<CustomSuccessResponse<TodoResponse>> create(@Valid @RequestBody CreateTodoRequest request) {
        TodoResponse todo = todoService.create(request);
        return ResponseEntity.ok(new CustomSuccessResponse<>(todo));
    }

    @GetMapping
    public ResponseEntity<CustomSuccessResponse<PagedTodoResponse>> getPaginated(
            @RequestParam(defaultValue = "0") @Min(value = 0,
                    message = ValidationConstants.TASKS_PAGE_GREATER_OR_EQUAL_1) Integer page,
            @RequestParam(defaultValue = "10") @Min(value = 1,
                    message = ValidationConstants.TASKS_PER_PAGE_GREATER_OR_EQUAL_1)
            @Max(value = 100, message = ValidationConstants.TASKS_PER_PAGE_LESS_OR_EQUAL_100) Integer perPage,
            @RequestParam(required = false) Boolean status) {
        PagedTodoResponse todos = todoService.getPaginated(page, perPage, status);
        return ResponseEntity.ok(new CustomSuccessResponse<>(todos));
    }

    @PatchMapping
    public ResponseEntity<BaseSuccessResponse> updateAllStatuses(@Valid @RequestBody UpdateTodoStatusRequest request) {
        todoService.updateAllStatuses(request.getStatus());
        return ResponseEntity.ok(new BaseSuccessResponse());
    }

    @DeleteMapping
    public ResponseEntity<BaseSuccessResponse> deleteAllReady() {
        todoService.deleteAllReady();
        return ResponseEntity.ok(new BaseSuccessResponse());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseSuccessResponse> delete(
            @PathVariable @Positive(message = ValidationConstants.ID_MUST_BE_POSITIVE) Long id) {
        todoService.delete(id);
        return ResponseEntity.ok(new BaseSuccessResponse());
    }

    @PatchMapping("/status/{id}")
    public ResponseEntity<BaseSuccessResponse> patchStatus(
            @PathVariable @Positive(message = ValidationConstants.ID_MUST_BE_POSITIVE) Long id,
            @Valid @RequestBody UpdateTodoStatusRequest request) {
        todoService.patchStatus(id, request);
        return ResponseEntity.ok(new BaseSuccessResponse());
    }

    @PatchMapping("/text/{id}")
    public ResponseEntity<BaseSuccessResponse> patchText(
            @PathVariable @Positive(message = ValidationConstants.ID_MUST_BE_POSITIVE) Long id,
            @Valid @RequestBody UpdateTodoTextRequest request) {
        todoService.patchText(id, request);
        return ResponseEntity.ok(new BaseSuccessResponse());
    }
}
