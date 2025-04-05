package com.lamcao1206.hcmut_ssps.repository;

import com.lamcao1206.hcmut_ssps.entity.SPSO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SPSORepository extends JpaRepository<SPSO, Long> {
    Optional<SPSO> findByEmail(String email);
}
