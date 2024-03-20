package com.alatoo.TaskManagement.mappersTest;


import com.alatoo.TaskManagement.entities.Task;
import com.alatoo.TaskManagement.entities.User;
import com.alatoo.TaskManagement.dto.TaskDTO;
import com.alatoo.TaskManagement.dto.UserDTO;
import com.alatoo.TaskManagement.mappers.TaskMapper;
import com.alatoo.TaskManagement.mappers.UserMapper;
import jdk.jfr.Event;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MappersTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    private final TaskMapper taskMapper = Mappers.getMapper(TaskMapper.class);

    @Test
    public void testUserDtoToUser() {
        // Given
        UserDTO userDTO = UserDTO.builder()
                .email("user.test@example.com")
                .username("usertest")
                .build();

        // When
        User user = userMapper.userDtoToUser(userDTO);

        // Then
        assertEquals(userDTO.getUsername(), user.getUsername());
        assertEquals(userDTO.getEmail(), user.getEmail());
    }

    @Test
    public void testUserToUserDto() {
        // Given
        User user = User.builder()
                .email("user.test@example.com")
                .username("usertest")
                .build();

        // When
        UserDTO userDTO = userMapper.userToUserDto(user);

        // Then
        assertEquals(user.getUsername(), userDTO.getUsername());
        assertEquals(user.getEmail(), userDTO.getEmail());
    }

    @Test
    public void testEventToEventDto() {
        // Given
        Task task = Task.builder()
                .title("testTask")
                .description("testTask description")
                .completed(true)
                .build();

        // When
        TaskDTO taskDTO = taskMapper.taskToTaskDto(task);

        //Then
        assertEquals(task.getTitle(), taskDTO.getTitle());
        assertEquals(task.getDescription(), taskDTO.getDescription());
        assertEquals(task.isCompleted(), taskDTO.isCompleted());
    }

    @Test
    public void testEventDtoToEvent() {
        // Given
        TaskDTO taskDTO = TaskDTO.builder()
                .title("testTask")
                .description("testTask description")
                .completed(true)
                .build();

        // When
        Task task = taskMapper.taskDtoToTask(taskDTO);

        //Then
        assertEquals(taskDTO.getTitle(), task.getTitle());
        assertEquals(taskDTO.getDescription(), task.getDescription());
        assertEquals(taskDTO.isCompleted(), task.isCompleted());
    }
}



