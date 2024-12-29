package com.nutech.mar.service;

import com.nutech.mar.dto.ApiResponseDto;
import com.nutech.mar.model.User;
import com.nutech.mar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username atau password salah");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                new ArrayList<>());
    }

    public ApiResponseDto save(User user) {
        try{
            User newUser = new User();
            newUser.setUsername(user.getEmail());
            newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
            newUser.setEmail(user.getEmail());
            newUser.setFirstName(user.getFirstName());
            newUser.setLastName(user.getLastName());
            newUser.setBalance(new BigDecimal(0));
            userRepository.save(newUser);
            return new ApiResponseDto().custom(0, "Registrasi berhasil silahkan login");
            // validasi email
        }catch (Exception e){
            return new ApiResponseDto().custom(201, "Gagal Registrasi");
        }
    }




}
