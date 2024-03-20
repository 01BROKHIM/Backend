package com.alatoo.TaskManagement.services;

import com.alatoo.TaskManagement.dto.TaskDTO;
import com.alatoo.TaskManagement.entities.Task;
import com.alatoo.TaskManagement.mappers.TaskMapper;
import com.alatoo.TaskManagement.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Autowired
    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    public List<TaskDTO> getAllTasks() {
        List<Task> tasks = (List<Task>) taskRepository.findAll();
        return tasks.stream()
                .map(taskMapper::taskToTaskDto)
                .collect(Collectors.toList());
    }

    public Optional<TaskDTO> getTaskById(Long id) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        return taskOptional.map(taskMapper::taskToTaskDto);
    }

    public TaskDTO createTask(TaskDTO taskDTO) {
        Task task = taskMapper.taskDtoToTask(taskDTO);
        Task createdTask = taskRepository.save(task);
        return taskMapper.taskToTaskDto(createdTask);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
