package com.p4r.paymentsystem.entities;

import java.time.LocalDate;

import com.p4r.paymentsystem.enums.PaymentStatus;
import com.p4r.paymentsystem.enums.PaymentType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private double quantity;

    private PaymentType type;

    private PaymentStatus status;

    private String file;

    @ManyToOne
    private Student student;
}
