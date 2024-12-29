package com.nutech.mar.controller;

import com.nutech.mar.config.JwtTokenUtil;
import com.nutech.mar.dto.ApiResponseDto;
import com.nutech.mar.model.User;
import com.nutech.mar.model.request.JwtRequest;
import com.nutech.mar.model.response.JwtResponse;
import com.nutech.mar.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailService userDetailService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ApiResponseDto createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        try{
            Integer pswd = authenticationRequest.getPassword().length();
            if (pswd < 8){
                return new ApiResponseDto().custom(102, "Minimal panjang password 8 karakter");
            }else if (!isValidEmail(authenticationRequest.getUsername())) {
                return new ApiResponseDto().custom(102, "Format email tidak valid");
            }else{
                authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
                final UserDetails userDetails = userDetailService
                        .loadUserByUsername(authenticationRequest.getUsername());
                if(userDetails != null){
                    final String token = jwtTokenUtil.generateToken(userDetails);
                    return new ApiResponseDto().custom(0, "Login sukses", new JwtResponse(token));
                }else{
                    return new ApiResponseDto().custom(103, "Username atau password salah");
                }

            }
        }catch (Exception e){
            return new ApiResponseDto().custom(103, "Username atau password salah");
        }

    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ApiResponseDto saveUser(@RequestBody User user) throws Exception {
        Integer pswd = user.getPassword().length();
        if (pswd < 8){
            return new ApiResponseDto().custom(102, "Minimal panjang password 8 karakter");
        }else if (!isValidEmail(user.getEmail())) {
            return new ApiResponseDto().custom(102, "Format email tidak valid");
        }else{
            ApiResponseDto response = userDetailService.save(user);
            return response;
        }
        // validasi format email

    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email != null && email.matches(emailRegex);
    }
}
