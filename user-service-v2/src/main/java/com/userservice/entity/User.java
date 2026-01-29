package com.userservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User {

    /**
     * This is the external user identifier.
     * Comes from JWT "sub"
     */
    @Id
    @Column(name = "user_id", nullable = false, updatable = false)
    private String userId;

    /**
     * Human-readable username (email / login id)
     */
    @Column(nullable = false, unique = true)
    private String username;

    /**
     * User status (ACTIVE, SUSPENDED, DELETED)
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    /**
     * Roles assigned to the user
     * MUST be EAGER for authorization checks
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
}
