package com.alatoo.TaskManagement.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDTO {

    private Long id;
    private String username;
    private String email;

    private List<TaskDTO> tasks;

}

