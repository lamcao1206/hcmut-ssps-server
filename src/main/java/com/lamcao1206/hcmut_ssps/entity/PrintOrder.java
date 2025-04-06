package com.lamcao1206.hcmut_ssps.entity;

import com.lamcao1206.hcmut_ssps.entity.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "print_order")
public class PrintOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;
    
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "document_id", referencedColumnName = "id", nullable = false)
    private Document document;
    
    @ManyToOne
    @JoinColumn(name = "printer_id")
    private Printer printer;
}
