package com.alatoo.TaskManagement;

import com.alatoo.TaskManagement.entities.Task;
import com.alatoo.TaskManagement.repositories.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;


    @Test
    void testFindAllTasks() {
        // Arrange
        Task task1 = Task.builder()
                .title("Task 1")
                .description("Task 1 description")
                .deadline(LocalDate.now())
                .completed(false)
                .build();
        Task task2 = Task.builder()
                .title("Task 2")
                .description("Task 2 description")
                .deadline(LocalDate.now())
                .completed(true)
                .build();
        taskRepository.save(task1);
        taskRepository.save(task2);

        // Act
        Iterable<Task> allTasks = taskRepository.findAll();

        // Assert
        assertThat(allTasks).isNotEmpty().hasSize(2); // Assuming 2 tasks were added
    }

    @Test
    void testFindById() {
        // Arrange
        Task task = Task.builder()
                .title("Test Task")
                .description("Test task description")
                .deadline(LocalDate.now())
                .completed(false)
                .build();
        taskRepository.save(task);

        // Act
        Optional<Task> foundTask = taskRepository.findById(task.getId());

        // Assert
        assertThat(foundTask).isPresent();
        assertThat(foundTask.get().getTitle()).isEqualTo("Test Task");
        assertThat(foundTask.get().getDescription()).isEqualTo("Test task description");
        assertThat(foundTask.get().getDeadline()).isEqualTo(LocalDate.now());
        assertThat(foundTask.get().isCompleted()).isFalse();
    }

    @Test
    void testSaveTask() {
        // Arrange
        Task task = Task.builder()
                .title("Test Task")
                .description("Test task description")
                .deadline(LocalDate.now())
                .completed(false)
                .build();

        // Act
        Task savedTask = taskRepository.save(task);

        // Assert
        assertThat(savedTask).isNotNull();
        assertThat(savedTask.getId()).isNotNull();
        assertThat(savedTask.getTitle()).isEqualTo(task.getTitle());
        assertThat(savedTask.getDescription()).isEqualTo(task.getDescription());
        assertThat(savedTask.getDeadline()).isEqualTo(task.getDeadline());
        assertThat(savedTask.isCompleted()).isEqualTo(task.isCompleted());
    }

    @Test
    void testDeleteTask() {
        // Arrange
        Task task = Task.builder()
                .title("Task to delete")
                .description("Task to delete description")
                .deadline(LocalDate.now())
                .completed(false)
                .build();
        taskRepository.save(task);

        // Act
        taskRepository.deleteById(task.getId());

        // Assert
        Optional<Task> deletedTask = taskRepository.findById(task.getId());
        assertFalse(deletedTask.isPresent());
    }
}
