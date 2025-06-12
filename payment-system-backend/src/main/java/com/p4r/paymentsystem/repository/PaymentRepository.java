package com.p4r.paymentsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.p4r.paymentsystem.entities.Payment;
import com.p4r.paymentsystem.enums.PaymentStatus;
import com.p4r.paymentsystem.enums.PaymentType;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByStudentCode(String code);

    List<Payment> findByStatus(PaymentStatus status);

    List<Payment> findByType(PaymentType type);

}
