package com.nutech.mar.model.request;


import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@Data
@AllArgsConstructor
public class JwtRequest implements Serializable {
    private static final long serialVersionUID = 5926468583005150707L;

    private String username;
    private String password;

    public JwtRequest()
    {

    }
}
