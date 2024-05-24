package com.alatoo.TaskManagement.services;

import com.alatoo.TaskManagement.dto.UserDTO;
import com.alatoo.TaskManagement.dto.authorization.AuthRegistrationDTO;
import com.alatoo.TaskManagement.entities.User;
import com.alatoo.TaskManagement.entities.UserRole;
import com.alatoo.TaskManagement.mappers.UserMapper;
import com.alatoo.TaskManagement.repositories.UserRepository;
import com.alatoo.TaskManagement.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.userRoleRepository = userRoleRepository;
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
        UserRole userRole = userRoleRepository.findByRoleName(UserRole.Name.USER)
                .orElseThrow(() -> new RuntimeException("USER role not found")); // Handle if the role doesn't exist

        User user = userMapper.userDtoToUser(userDTO);
        user.setRoles(Set.of(userRole));
        user.setPassword(passwordEncoder.encode(user.getUsername() + LocalDate.now().getYear()));
        User savedUser = userRepository.save(user);
        return userMapper.userToUserDto(savedUser);
    }

    public UserDTO register(AuthRegistrationDTO authRegistrationDTO) {
        UserRole userRole = userRoleRepository.findByRoleName(UserRole.Name.USER)
                .orElseThrow(() -> new RuntimeException("USER role not found")); // Handle if the role doesn't exist
        User user = User.builder()
                .name(authRegistrationDTO.getName())
                .email(authRegistrationDTO.getEmail())
                .username(authRegistrationDTO.getUsername())
                .password(passwordEncoder.encode(authRegistrationDTO.getPassword()))
                .roles(Set.of(userRole))
                .build();
        User savedUser = userRepository.save(user);
        return userMapper.userToUserDto(savedUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
