package com.nutech.mar.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_transaction")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String invoiceNumber;
    private String transactionType;
    private String description;
    private BigDecimal totalAmount;
    private LocalDateTime createdOn;
    private String serviceCode;
    private String serviceName;
    private Integer userId;
}
