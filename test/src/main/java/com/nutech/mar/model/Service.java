package com.nutech.mar.model;


import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_service")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer serviceId;
    private String serviceCode;
    private String serviceName;
    private String serviceIcon;
    private BigDecimal serviceTarif;
}
