//package com.userservice.service;
//
//import com.userservice.dto.UserInternalResponse;
//import com.userservice.entity.Permission;
//import com.userservice.repository.UserRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class UserQueryService {
//
//    private final UserRepository userRepository;
//
//    public UserQueryService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    public List<String> getPermissions(String userId) {
//        return userRepository.findWithRolesAndPermissions(userId)
//                .stream()
//                .flatMap(u -> u.getRoles().stream())
//                .flatMap(r -> r.getPermissions().stream())
//                .map(Permission::getName)
//                .distinct()
//                .toList();
//    }
//
//    public UserInternalResponse getUser(String userId) {
//        return userRepository.findWithRolesAndPermissions(userId)
//                .map(user -> new UserInternalResponse(
//                        user.getUserId(),
//                        user.getUsername(),
//                        user.getStatus(),
//                        user.getRoles()
//                                .stream()
//                                .map(r -> r.getName())
//                                .toList()
//                ))
//                .orElseThrow(() -> new RuntimeException("User not found"));
//    }
//
//}
