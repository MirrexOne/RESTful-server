package dev.mirrex.restserver.service;

import dev.mirrex.restserver.dto.request.CreateTodoRequest;
import dev.mirrex.restserver.dto.request.UpdateTodoStatusRequest;
import dev.mirrex.restserver.dto.request.UpdateTodoTextRequest;
import dev.mirrex.restserver.dto.response.PagedTodoResponse;
import dev.mirrex.restserver.dto.response.TodoResponse;

public interface TodoService {

    TodoResponse create(CreateTodoRequest request);

    PagedTodoResponse getPaginated(Integer page, Integer size, Boolean status);

    void patchStatus(Long id, UpdateTodoStatusRequest request);

    void patchText(Long id, UpdateTodoTextRequest request);

    void deleteAllReady();

    void delete(Long id);

    void updateAllStatuses(Boolean newStatus);
}
