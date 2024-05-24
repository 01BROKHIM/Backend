package com.alatoo.TaskManagement.repositories;

import com.alatoo.TaskManagement.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRoleRepository extends CrudRepository<UserRole,String >, JpaRepository<UserRole,String> {
    Optional<UserRole> findByRoleName(UserRole.Name roleName);
}
