package com.alatoo.TaskManagement.services;

import com.alatoo.TaskManagement.dto.UserDTO;
import com.alatoo.TaskManagement.entities.User;
import com.alatoo.TaskManagement.mappers.UserMapper;
import com.alatoo.TaskManagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = (List<User>) userRepository.findAll();
        return users.stream()
                .map(userMapper::userToUserDto)
                .collect(Collectors.toList());
    }

    public Optional<UserDTO> getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(userMapper::userToUserDto);
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.userDtoToUser(userDTO);
        User createdUser = userRepository.save(user);
        return userMapper.userToUserDto(createdUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
