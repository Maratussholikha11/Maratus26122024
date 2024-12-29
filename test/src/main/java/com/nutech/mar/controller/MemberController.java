package com.nutech.mar.controller;

import com.nutech.mar.config.JwtTokenUtil;
import com.nutech.mar.dto.ApiResponseDto;
import com.nutech.mar.dto.UserDto;
import com.nutech.mar.model.User;
import com.nutech.mar.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping
public class MemberController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping(value = "/profile")
    public ApiResponseDto getProfile(HttpServletRequest request){
        final String requestTokenHeader = request.getHeader("Authorization");

        ApiResponseDto apiResponseDto = new ApiResponseDto();
        String username = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (ExpiredJwtException e) {
                System.out.println("Unable to get username from JWT token");
            }
        } else {
            System.out.println("JWT Token does not begin with Bearer String");
        }

        if(username != null){
            User user = this.userRepository.findByUsername(username);
            UserDto userDto = new UserDto();
            if(user != null){
                userDto.setEmail(user.getEmail());
                userDto.setFirstName(user.getFirstName());
                userDto.setLastName(user.getLastName());
                userDto.setProfileImage(user.getProfileImage());
            }
            apiResponseDto =  new ApiResponseDto().custom(0, "Sukses", userDto);
        }
        return apiResponseDto;
    }

    @PutMapping(value = "/profile/update")
    public ApiResponseDto updateProfile(@RequestBody User newData, HttpServletRequest request){
        final String requestTokenHeader = request.getHeader("Authorization");

        ApiResponseDto apiResponseDto = new ApiResponseDto();
        String username = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (ExpiredJwtException e) {
                System.out.println("Unable to get username from JWT token");
            }
        } else {
            System.out.println("JWT Token does not begin with Bearer String");
        }

        if(username != null){
            User user = this.userRepository.findByUsername(username);
            UserDto userDto = new UserDto();
            if(user != null){
                user.setFirstName(newData.getFirstName());
                user.setLastName(newData.getLastName());
                this.userRepository.save(user);
                userDto.setEmail(user.getEmail());
                userDto.setFirstName(user.getFirstName());
                userDto.setLastName(user.getLastName());
                userDto.setProfileImage(user.getProfileImage());
            }
            apiResponseDto =  new ApiResponseDto().custom(0, "Sukses", userDto);
        }
        return apiResponseDto;
    }

    @PutMapping(value = "/profile/image")
    public ApiResponseDto profileImage(@RequestParam("file")MultipartFile file, HttpServletRequest request){
        final String requestTokenHeader = request.getHeader("Authorization");

        ApiResponseDto apiResponseDto = new ApiResponseDto();
        String username = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (ExpiredJwtException e) {
                System.out.println("Unable to get username from JWT token");
            }
        } else {
            System.out.println("JWT Token does not begin with Bearer String");
        }

        try{
            String contentType = file.getContentType();
            if (!"image/jpeg".equals(contentType) && !"image/png".equals(contentType)) {
                return new ApiResponseDto().custom(102, "Format image tidak sesuai");
            }
        }catch (Exception e){
            return new ApiResponseDto().custom(102, "Format image tidak sesuai");
        }

        if(username != null){
            User user = this.userRepository.findByUsername(username);
            UserDto userDto = new UserDto();
            String fileName = file.getOriginalFilename();
            if(user != null){
                user.setProfileImage(fileName);
                this.userRepository.save(user);
                userDto.setEmail(user.getEmail());
                userDto.setFirstName(user.getFirstName());
                userDto.setLastName(user.getLastName());
                userDto.setProfileImage(user.getProfileImage());
            }
            apiResponseDto =  new ApiResponseDto().custom(0, "Sukses", userDto);
        }
        return apiResponseDto;
    }

}
