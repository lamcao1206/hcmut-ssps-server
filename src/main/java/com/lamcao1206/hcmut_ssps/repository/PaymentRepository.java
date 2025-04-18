package com.lamcao1206.hcmut_ssps.repository;

import com.lamcao1206.hcmut_ssps.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByCustomerId(Long customerId);
}
