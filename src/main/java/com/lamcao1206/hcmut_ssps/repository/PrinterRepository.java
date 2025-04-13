package com.lamcao1206.hcmut_ssps.repository;

import com.lamcao1206.hcmut_ssps.entity.Printer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrinterRepository extends JpaRepository<Printer, Long> {
    Optional<Printer> findByName(String printerName);
}
