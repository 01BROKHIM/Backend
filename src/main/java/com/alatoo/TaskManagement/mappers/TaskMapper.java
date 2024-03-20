package com.alatoo.TaskManagement.mappers;

import com.alatoo.TaskManagement.entities.Task;
import com.alatoo.TaskManagement.dto.TaskDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TaskMapper {
    Task taskDtoToTask(TaskDTO taskDTO);

    @Mapping(target = "user", ignore = true)
    TaskDTO taskToTaskDto(Task task);
}

