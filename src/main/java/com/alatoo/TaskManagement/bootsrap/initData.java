package com.alatoo.TaskManagement.bootsrap;

import com.alatoo.TaskManagement.entities.Task;
import com.alatoo.TaskManagement.entities.User;
import com.alatoo.TaskManagement.repositories.TaskRepository;
import com.alatoo.TaskManagement.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Component
public class initData implements CommandLineRunner {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    Random random = new Random();

    public initData(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        try {
            Set<User> savedUsers = new HashSet<>();
            Set<Task> savedTasks = new HashSet<>();

            for (int i = 0; i < 10; i++) {
                User user = User.builder()
                        .username("user" + i)
                        .email("user" + i + "@gmail.com")
                        .build();
                User savedUser = userRepository.save(user);
                savedUsers.add(savedUser);
            }
            List<User> userList = new ArrayList<>(savedUsers);


            LocalDate startDate = LocalDate.of(2024, 3, 12); // Example date
            // Convert LocalDate to Date with explicit time zone
            ZoneId zoneId = ZoneId.systemDefault();
            Date initialDate = Date.from(startDate.atStartOfDay(zoneId).toInstant());
            for (int i = 0; i < 100; i++) {
                LocalDate newLocalDate = startDate.plusDays(i);
                Task task = Task.builder()
                        .title("task " + i)
                        .description("do task " + i + i + i + " times")
                        .deadline(newLocalDate)
                        .completed(random.nextBoolean())
                        .user(userList.get(random.nextInt(10)))
                        .build();
                Task savedTask = taskRepository.save(task);
                savedTasks.add(savedTask);
            }

        } catch (DataAccessException e) {
            // Handle database access exception
            e.printStackTrace();
        }
    }

}
