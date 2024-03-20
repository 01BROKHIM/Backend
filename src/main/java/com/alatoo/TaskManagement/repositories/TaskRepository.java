package com.alatoo.TaskManagement.repositories;

import com.alatoo.TaskManagement.entities.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Long> {
}
