package com.alatoo.TaskManagement.controllersTest;

import com.alatoo.TaskManagement.controllers.TaskController;
import com.alatoo.TaskManagement.dto.TaskDTO;
import com.alatoo.TaskManagement.services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }

    @Test
    public void testGetAllTasks() throws Exception {
        TaskDTO task1 = TaskDTO.builder().id(1L).title("Task 1").description("Description for Task 1").build();
        TaskDTO task2 = TaskDTO.builder().id(2L).title("Task 2").description("Description for Task 2").build();
        List<TaskDTO> tasks = Arrays.asList(task1, task2);

        when(taskService.getAllTasks()).thenReturn(tasks);

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(tasks.size()));
    }

    @Test
    public void testGetTaskById() throws Exception {
        TaskDTO task = TaskDTO.builder().id(1L).title("Task 1").description("Description for Task 1").build();
        when(taskService.getTaskById(1L)).thenReturn(Optional.of(task));

        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Task 1")) // Adjusted to match TaskDTO field name
                .andExpect(jsonPath("$.description").value("Description for Task 1"));
    }

    @Test
    public void testCreateTask() throws Exception {
        TaskDTO taskDTO = TaskDTO.builder().title("New Task").description("Description for New Task").build();
        TaskDTO createdTask = TaskDTO.builder().id(1L).title("New Task").description("Description for New Task").build();

        when(taskService.createTask(any(TaskDTO.class))).thenReturn(createdTask);

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"New Task\", \"description\": \"Description for New Task\"}")) // Adjusted field name in the JSON content
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("New Task")) // Adjusted to match TaskDTO field name
                .andExpect(jsonPath("$.description").value("Description for New Task"));
    }


    @Test
    public void testDeleteTask() throws Exception {
        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isNoContent());
    }
}

