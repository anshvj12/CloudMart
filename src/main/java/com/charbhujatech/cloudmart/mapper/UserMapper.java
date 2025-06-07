package com.charbhujatech.cloudmart.mapper;

import com.charbhujatech.cloudmart.Model.User;
import com.charbhujatech.cloudmart.dto.UserRequestDTO;
import com.charbhujatech.cloudmart.enums.Roles;

public class UserMapper {

    public static UserRequestDTO mapToUserDTO(User user, UserRequestDTO userRequestDTO)
    {
        userRequestDTO.setFirstName(user.getFirstName());

        if(user.getLastName() != null)
            userRequestDTO.setLastName(user.getLastName());

        userRequestDTO.setEmail(user.getEmail());
        userRequestDTO.setPhone(user.getPhone());
        userRequestDTO.setRole(user.getRoles().toString());
        return userRequestDTO;
    }

    public static User maptoUser(UserRequestDTO userRequestDTO, User user)
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
        return user;
    }

}
