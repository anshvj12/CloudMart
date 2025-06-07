package com.charbhujatech.cloudmart.service;

import com.charbhujatech.cloudmart.Model.User;
import com.charbhujatech.cloudmart.dto.UserRequestDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    Optional<User> findUserById(Long id);

    User saveUser(UserRequestDTO user);

    List<UserRequestDTO> findAllUsers();

    Optional<UserRequestDTO> findUserByEmail(String email);

    UserRequestDTO updateUser(String email, UserRequestDTO userRequestDTO);

    Boolean deleteUser(String email);
}
