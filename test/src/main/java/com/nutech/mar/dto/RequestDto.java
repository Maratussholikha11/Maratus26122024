package com.nutech.mar.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class RequestDto {

    private BigDecimal topUpAmount;
    private String serviceCode;
}
