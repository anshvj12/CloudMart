package com.charbhujatech.cloudmart.service;

import com.charbhujatech.cloudmart.Model.User;
import com.charbhujatech.cloudmart.dto.UserRequestDTO;
import com.charbhujatech.cloudmart.dto.UserResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    Optional<User> findUserById(Long id);

    User saveUser(UserRequestDTO user);

    List<UserResponseDTO> findAllUsers();

    Optional<UserResponseDTO> findUserByEmail(String email);

    UserResponseDTO updateUser(String email, UserRequestDTO userRequestDTO);

    Boolean deleteUser(String email);
}
