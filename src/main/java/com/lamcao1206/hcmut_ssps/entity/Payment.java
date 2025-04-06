package com.lamcao1206.hcmut_ssps.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "timestamp")
    private Date timestamp;
    
    @Column(name = "page")
    private int page;
    
    @Column(name = "price")
    private Long price;
    
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
}
