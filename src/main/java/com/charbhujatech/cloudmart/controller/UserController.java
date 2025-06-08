package com.charbhujatech.cloudmart.controller;


import com.charbhujatech.cloudmart.Model.User;
import com.charbhujatech.cloudmart.dto.ResponseDTO;
import com.charbhujatech.cloudmart.dto.UserRequestDTO;
import com.charbhujatech.cloudmart.dto.UserResponseDTO;
import com.charbhujatech.cloudmart.exception.EntityNotCreated;
import com.charbhujatech.cloudmart.exception.ResourceNotFoundException;
import com.charbhujatech.cloudmart.service.UserService;
import com.charbhujatech.cloudmart.util.ConstantsString;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/users/{email}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable
                                            @Email(message = "Enter a valid email") @NotEmpty String email) {
        UserResponseDTO userByEmail = userService.findUserByEmail(email).orElseThrow(
                    () ->{ throw new ResourceNotFoundException(ConstantsString.USER_NOT_FOUND+email);}
            );
            return new ResponseEntity<>(userByEmail,HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseDTO> createUser(@Valid @RequestBody UserRequestDTO user) {

        User savedUser = userService.saveUser(user);
        if(savedUser != null)
        {
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setStatusCode(HttpStatus.CREATED.toString());
            responseDTO.setStatusMessage(ConstantsString.USER_201);
            return new ResponseEntity<>(responseDTO,HttpStatus.CREATED);
        }
        else
        {
            throw new EntityNotCreated("User entity is not created");
        }
    }

    @GetMapping("/users")
    public List<UserResponseDTO> getUsers() {
        List<UserResponseDTO> users = userService.findAllUsers();
        return users;
    }

    @PutMapping("/users/{email}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable String email, @RequestBody UserRequestDTO userRequestDTO)
    {
        UserResponseDTO userResponseDTO = userService.updateUser(email, userRequestDTO);
        return new ResponseEntity<>(userResponseDTO,HttpStatus.OK);
    }

    @DeleteMapping("/users/{email}")
    public ResponseEntity<ResponseDTO> deleteUser(@PathVariable String email)
    {
        return userService.deleteUser(email) ?
                new ResponseEntity<>(new ResponseDTO(HttpStatus.OK.toString(), ConstantsString.USER_DELETED),HttpStatus.OK)
                : new ResponseEntity<>(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.toString(), ConstantsString.USER_DELETED_FAILED),HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
