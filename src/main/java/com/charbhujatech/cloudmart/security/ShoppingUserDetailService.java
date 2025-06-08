package com.charbhujatech.cloudmart.security;

import com.charbhujatech.cloudmart.Model.User;
import com.charbhujatech.cloudmart.dao.UserRepository;
import com.charbhujatech.cloudmart.util.ConstantsString;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ShoppingUserDetailService implements UserDetailsService {


    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException(ConstantsString.EMAIL_NOT_FOUND_PLEASE_REGISTER_THIS_EMAIL + username) );
        List<SimpleGrantedAuthority> authorityList = List.of(new SimpleGrantedAuthority(user.getRoles().toString()));
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword().toString(),authorityList);
    }
}
