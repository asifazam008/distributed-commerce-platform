package com.userservice.repository;

import com.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    @Query("""
        SELECT u FROM User u
        JOIN FETCH u.roles r
        JOIN FETCH r.permissions
        WHERE u.userId = :userId
    """)
    Optional<User> findWithRolesAndPermissions(String userId);
}