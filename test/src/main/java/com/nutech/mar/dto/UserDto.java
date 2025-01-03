package com.nutech.mar.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDto {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String profileImage;
}
