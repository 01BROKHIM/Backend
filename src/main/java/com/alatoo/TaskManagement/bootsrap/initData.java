
package com.alatoo.TaskManagement.bootsrap;

import com.alatoo.TaskManagement.entities.Task;
import com.alatoo.TaskManagement.entities.User;
import com.alatoo.TaskManagement.entities.UserRole;
import com.alatoo.TaskManagement.repositories.TaskRepository;
import com.alatoo.TaskManagement.repositories.UserRepository;
import com.alatoo.TaskManagement.repositories.UserRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Component
public class initData implements CommandLineRunner {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    Random random = new Random();

    public initData(TaskRepository taskRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        User admin = User.builder()
                .name("Admin")
                .username("admin")
                .email("admin@gmail.com")
                .roles(Set.of(UserRole.ADMIN, UserRole.STAFF))
                .password(passwordEncoder.encode("password"))
                .build();

        User staff = User.builder()
                .name("Staff")
                .username("staff")
                .email("staff@gmail.com")
                .password(passwordEncoder.encode("password"))
                .roles(Set.of(UserRole.STAFF))
                .build();
        userRepository.saveAll(List.of(admin, staff));

        try {
            Set<User> savedUsers = new HashSet<>();

            // Create 50 users
            for (int i = 0; i < 50; i++) {
                User user = User.builder()
                        .name("User " + i)
                        .username("user" + i)
                        .email("user" + i + "@gmail.com")
                        .password(passwordEncoder.encode("password"))
                        .roles(Set.of(UserRole.USER))
                        .build();
                savedUsers.add(user);
            }
            userRepository.saveAll(savedUsers);

            List<User> userList = new ArrayList<>(savedUsers);


            Set<Task> savedTasks = new HashSet<>();

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
