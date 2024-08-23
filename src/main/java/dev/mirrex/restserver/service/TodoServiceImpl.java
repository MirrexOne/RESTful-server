package dev.mirrex.restserver.service;

import dev.mirrex.restserver.dto.request.UpdateTodoStatusRequest;
import dev.mirrex.restserver.dto.request.UpdateTodoTextRequest;
import dev.mirrex.restserver.dto.response.PagedTodoResponse;
import dev.mirrex.restserver.handler.CustomException;
import dev.mirrex.restserver.mapper.TodoMapper;
import dev.mirrex.restserver.dto.request.CreateTodoRequest;
import dev.mirrex.restserver.dto.response.TodoResponse;
import dev.mirrex.restserver.entity.Todo;
import dev.mirrex.restserver.repository.TodoRepository;
import dev.mirrex.restserver.util.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    private final TodoMapper todoMapper;

    @Override
    @Transactional
    public TodoResponse create(CreateTodoRequest request) {
        Todo todo = new Todo();
        todo.setText(request.getText());
        todo = todoRepository.save(todo);
        return todoMapper.toResponse(todo);
    }

    @Override
    public PagedTodoResponse getPaginated(Integer page, Integer size, Boolean status) {
        PageRequest pageRequest = PageRequest.of(page, size);

        Page<Todo> todoPage;
        if (status != null) {
            todoPage = todoRepository.findByStatus(status, pageRequest);
        } else {
            todoPage = todoRepository.findAll(pageRequest);
        }

        Page<TodoResponse> responsePage = todoPage.map(todoMapper::toResponse);

        Long totalElements = todoPage.getTotalElements();
        Long readyCount = todoRepository.countByStatus(true);
        Long notReadyCount = totalElements - readyCount;

        return todoMapper.toPagedResponse(responsePage, totalElements, readyCount, notReadyCount);
    }

    @Override
    @Transactional
    public void updateAllStatuses(Boolean newStatus) {
        List<Todo> todos = todoRepository.findAll();
        for (Todo todo : todos) {
            if (!todo.getStatus().equals(newStatus)) {
                todo.setStatus(newStatus);
            }
        }
        todoRepository.saveAll(todos);
    }

    @Override
    @Transactional
    public void deleteAllReady() {
        todoRepository.deleteAllByStatusTrue();
        resetIfEmpty();
    }

    @Override
    @Transactional
    public void patchStatus(Long id, UpdateTodoStatusRequest request) {
        Todo todo = getTodoById(id);
        todo.setStatus(request.getStatus());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        todoRepository.deleteById(id);
        resetIfEmpty();
    }

    @Override
    @Transactional
    public void patchText(Long id, UpdateTodoTextRequest request) {
        Todo todo = getTodoById(id);
        todo.setText(request.getText());
    }

    private Todo getTodoById(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.TASK_NOT_FOUND));
    }

    @Transactional
    void resetIfEmpty() {
        if (todoRepository.count() == 0) {
            todoRepository.truncateTableAndResetSequence();
            todoRepository.resetSequence();
        }
    }
}
