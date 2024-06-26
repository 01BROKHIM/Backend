package com.alatoo.TaskManagement.repositories;

import com.alatoo.TaskManagement.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<UserRole, UserRole.Name>,
        JpaRepository<UserRole, UserRole.Name> {

}
