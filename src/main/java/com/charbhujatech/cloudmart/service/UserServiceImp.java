package com.charbhujatech.cloudmart.service;

import com.charbhujatech.cloudmart.Model.Card;
import com.charbhujatech.cloudmart.Model.User;
import com.charbhujatech.cloudmart.dao.CardRepository;
import com.charbhujatech.cloudmart.dao.UserRepository;
import com.charbhujatech.cloudmart.dto.UserRequestDTO;
import com.charbhujatech.cloudmart.dto.UserResponseDTO;
import com.charbhujatech.cloudmart.exception.BadRequestException;
import com.charbhujatech.cloudmart.exception.ResourceNotFoundException;
import com.charbhujatech.cloudmart.mapper.UserMapper;
import com.charbhujatech.cloudmart.util.ConstantsString;
import com.charbhujatech.cloudmart.enums.Roles;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    private final CardRepository cardRepository;

    private final PasswordEncoder passwordEncoder;


    @Override
    public Optional<User> findUserById(Long id) {
        Optional<User> byId = userRepository.findById(id);
        return byId;
    }



    @Override
    @Transactional
    public User saveUser(UserRequestDTO userRequestDTO) {
        if(!userRequestDTO.getPassword().equals(userRequestDTO.getEnterAgainPassword()))
        {
            throw new BadRequestException(ConstantsString.PASSWORD_MISMATCH+ userRequestDTO.getPassword());
        }

         userRepository.findByEmail(userRequestDTO.getEmail()).
                ifPresent((User userByEmail) -> {throw new BadRequestException(ConstantsString.EXIST_EMAIL+ userRequestDTO.getEmail());});
         userRepository.findByPhone(userRequestDTO.getPhone()).
                ifPresent((User userByPhone) -> { throw new BadRequestException(ConstantsString.EXIST_PHONENUMBER+ userRequestDTO.getPhone());});

        User user = new User();
        UserMapper.maptoUser(userRequestDTO,user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Card card = new Card();
        card.setUser(user);
        Card save = cardRepository.save(card);
        return userRepository.save(user);
    }

    @Override
    public List<UserResponseDTO> findAllUsers() {
        final List<User> allUser = userRepository.findAll();

        List<UserResponseDTO> userResponseDTOS = new ArrayList<>();

        for(User user : allUser)
        {
            UserResponseDTO userRequestDTO = new UserResponseDTO();
            UserMapper.mapToUserDTO(user,userRequestDTO);
            userResponseDTOS.add(userRequestDTO);
        }
        return userResponseDTOS;
    }

    @Override
    public Optional<UserResponseDTO> findUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(ConstantsString.USER_NOT_FOUND+email));
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        UserMapper.mapToUserDTO(user, userResponseDTO);
        return Optional.of(userResponseDTO);
    }

    @Override
    public UserResponseDTO updateUser(String email, UserRequestDTO userRequestDTO) {
        User userByEmail = userRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(ConstantsString.EXIST_EMAIL+email));

        if(userRequestDTO.getFirstName() != null)
        {
            userByEmail.setFirstName(userRequestDTO.getFirstName());
        }
        if(userRequestDTO.getLastName() != null)
        {
            userByEmail.setLastName(userRequestDTO.getLastName());
        }
        if(userRequestDTO.getPhone() != null)
        {
            userByEmail.setPhone(userRequestDTO.getPhone());
        }
        if(userRequestDTO.getRole() != null)
        {
            userByEmail.setRoles(Roles.valueOf(userRequestDTO.getRole()));
        }
        User saved = userRepository.save(userByEmail);
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        UserMapper.mapToUserDTO(saved, userResponseDTO);
        return userResponseDTO;
    }

    @Override
    @Transactional
    public Boolean deleteUser(String email) {
        User userByEmail = userRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(ConstantsString.EXIST_EMAIL+email));
        Card byCard = cardRepository.findByUser(userByEmail);
        cardRepository.deleteById(byCard.getCardId());
        userRepository.deleteById(userByEmail.getUserId());
        return userRepository.findByEmail(email).isEmpty();
    }
}
