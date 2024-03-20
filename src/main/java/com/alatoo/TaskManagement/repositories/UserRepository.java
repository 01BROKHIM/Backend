package com.alatoo.TaskManagement.repositories;

import com.alatoo.TaskManagement.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
