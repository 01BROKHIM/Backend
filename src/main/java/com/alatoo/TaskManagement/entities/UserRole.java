package com.alatoo.TaskManagement.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRole {

    public static final UserRole ADMIN = new UserRole(Name.ADMIN);
    public static final UserRole STAFF = new UserRole(Name.STAFF);
    public static final UserRole USER = new UserRole(Name.USER);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true) // Ensure role names are unique
    private Name roleName;

    public UserRole(Name roleName) {
        this.roleName = roleName;
    }

    public enum Name {
        ADMIN,
        STAFF,
        USER,
    }
}
