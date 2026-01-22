package com.userservice.repository;

import com.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    @Query("""
        select distinct u from User u
        left join fetch u.roles r
        left join fetch r.permissions
        where u.userId = :userId
    """)
    Optional<User> findWithRolesAndPermissions(String userId);
}
