package com.alatoo.TaskManagement.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TaskDTO {

    private Long id;

    private String title;
    private String description;
    private LocalDate deadline;
    private boolean completed;

    private UserDTO user;


}
