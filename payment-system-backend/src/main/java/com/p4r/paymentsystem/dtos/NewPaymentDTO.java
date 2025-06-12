package com.p4r.paymentsystem.dtos;

import java.time.LocalDate;

import com.p4r.paymentsystem.enums.PaymentType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewPaymentDTO {

    private double quantity;

    private PaymentType type;

    private LocalDate Date;

    private String studentCode;

}