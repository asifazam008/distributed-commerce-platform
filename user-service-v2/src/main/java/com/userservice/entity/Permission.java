package com.userservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "permissions", schema = "user_schema")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;   // ORDER_CREATE, ORDER_READ

    @Column(nullable = false)
    private String method; // GET, POST, PUT, DELETE

    @Column(name = "path_pattern", nullable = false)
    private String pathPattern; // /orders/**

    private String description;
}
