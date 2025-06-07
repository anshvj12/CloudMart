package com.charbhujatech.cloudmart.controller;

import com.charbhujatech.cloudmart.dto.LoginRequestDTO;
import com.charbhujatech.cloudmart.dto.LoginResponseDTO;
import com.charbhujatech.cloudmart.util.ConstantsString;
import com.charbhujatech.cloudmart.security.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class LoginController {
    
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/loginApp")
    public ResponseEntity<LoginResponseDTO> loginApp(@RequestBody LoginRequestDTO loginRequestDTO)
    {
        Authentication authenticationObj = UsernamePasswordAuthenticationToken.unauthenticated(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationObj);
        if(authenticate.isAuthenticated())
        {
            String token = jwtUtil.generateToken(authenticate.getName(),authenticate.getAuthorities());
            LoginResponseDTO responseDTO = new LoginResponseDTO();
            responseDTO.setJwtToken(token);
            responseDTO.setHttpStatus(HttpStatus.OK);
            return new ResponseEntity<>(responseDTO,HttpStatus.OK);
        }
        else
        {
            throw new BadCredentialsException(ConstantsString.USER_NOT_FOUND);
        }

    }

}
