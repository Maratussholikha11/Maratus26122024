package com.nutech.mar.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApiResponseDto {
    private Integer status;

    private String message;

    private Object data;



    public ApiResponseDto custom(Integer statusCode, String message, Object data) {
        this.status = statusCode;
        this.message = message;
        this.data = data;
        return this;
    }

    public ApiResponseDto custom(Integer statusCode, String message) {
        this.status = statusCode;
        this.message = message;
        this.data = data;
        return this;
    }
}
