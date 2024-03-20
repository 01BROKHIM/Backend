package com.alatoo.TaskManagement.mappers;


import com.alatoo.TaskManagement.entities.User;
import com.alatoo.TaskManagement.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {

    @Mapping(target = "tasks", ignore = true)
    UserDTO userToUserDto(User user);

    @Mapping(target = "tasks", ignore = true)
    User userDtoToUser(UserDTO userDTO);
}
