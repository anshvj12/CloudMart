package com.charbhujatech.cloudmart.mapper;

import com.charbhujatech.cloudmart.Model.User;
import com.charbhujatech.cloudmart.dto.UserRequestDTO;
import com.charbhujatech.cloudmart.dto.UserResponseDTO;
import com.charbhujatech.cloudmart.enums.Roles;

public class UserMapper {

    public static void mapToUserDTO(User user, UserResponseDTO userResponseDTO)
    {
        userResponseDTO.setFirstName(user.getFirstName());
        userResponseDTO.setUserId(user.getUserId());

        if(user.getLastName() != null)
            userResponseDTO.setLastName(user.getLastName());

        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setPhone(user.getPhone());
        userResponseDTO.setRole(user.getRoles().toString());
    }

    public static void maptoUser(UserRequestDTO userRequestDTO, User user)
    {
        user.setFirstName(userRequestDTO.getFirstName());

        if(userRequestDTO.getLastName() != null)
            user.setLastName(userRequestDTO.getLastName());

        user.setPassword(userRequestDTO.getPassword());
        user.setEmail(userRequestDTO.getEmail());
        user.setPhone(userRequestDTO.getPhone());

        for(Roles role : Roles.values())
        {
            if(role.toString().equals(userRequestDTO.getRole()))
            {
                user.setRoles(Roles.valueOf(userRequestDTO.getRole()));
                break;
            }
        }
    }

}
