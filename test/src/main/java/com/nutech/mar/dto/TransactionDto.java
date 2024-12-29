package com.nutech.mar.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
public class TransactionDto {

    private BigDecimal totalAmount;
    private String invoiceNumber;
    private String serviceCode;
    private String serviceName;
    private String transactionType;
    private LocalDateTime createdOn;
}
