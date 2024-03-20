package com.alatoo.TaskManagement;

import com.alatoo.TaskManagement.entities.User;
import com.alatoo.TaskManagement.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindById() {
        // Arrange
        User user = User.builder()
                .username("testUser")
                .email("test@example.com")
                .build();
        userRepository.save(user);

        // Act
        Optional<User> foundUser = userRepository.findById(user.getId());

        // Assert
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("testUser");
        assertThat(foundUser.get().getEmail()).isEqualTo("test@example.com");
    }

    @Test
    void testFindAll() {
        // Arrange
        User user1 = User.builder()
                .username("testUser1")
                .email("test1@example.com")
                .build();
        User user2 = User.builder()
                .username("testUser2")
                .email("test2@example.com")
                .build();
        userRepository.save(user1);
        userRepository.save(user2);

        // Act
        List<User> users = (List<User>) userRepository.findAll();

        // Assert
        assertThat(users).isNotNull();
        assertThat(users).hasSize(2);
    }

    @Test
    void testSave() {
        // Arrange
        User user = User.builder()
                .username("testUser")
                .email("test@example.com")
                .build();

        // Act
        User savedUser = userRepository.save(user);

        // Assert
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo("testUser");
        assertThat(savedUser.getEmail()).isEqualTo("test@example.com");
    }

    @Test
    void testDelete() {
        // Arrange
        User user = User.builder()
                .username("testUser")
                .email("test@example.com")
                .build();
        userRepository.save(user);

        // Act
        userRepository.delete(user);

        // Assert
        assertThat(userRepository.findById(user.getId())).isEmpty();
    }
}

