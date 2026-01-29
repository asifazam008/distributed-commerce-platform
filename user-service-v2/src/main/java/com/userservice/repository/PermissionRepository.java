package com.userservice.repository;

import com.userservice.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Optional<Permission> findByCode(String code);
}
