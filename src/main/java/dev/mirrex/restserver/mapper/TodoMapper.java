package dev.mirrex.restserver.mapper;

import dev.mirrex.restserver.dto.response.PagedTodoResponse;
import dev.mirrex.restserver.dto.response.TodoResponse;
import dev.mirrex.restserver.entity.Todo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TodoMapper {

    TodoResponse toResponse(Todo todo);

    @Mapping(target = "content", source = "page.content")
    @Mapping(target = "numberOfElements", source = "totalElements")
    @Mapping(target = "ready", source = "readyCount")
    @Mapping(target = "notReady", source = "notReadyCount")
    @Mapping(target = "page", source = "page.number")
    @Mapping(target = "size", source = "page.size")
    @Mapping(target = "totalPages", source = "page.totalPages")
    PagedTodoResponse toPagedResponse(Page<TodoResponse> page, Long totalElements, Long readyCount, Long notReadyCount);
}
