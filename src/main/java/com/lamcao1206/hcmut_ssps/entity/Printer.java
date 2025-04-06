package com.lamcao1206.hcmut_ssps.entity;

import com.lamcao1206.hcmut_ssps.entity.enums.PrinterStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "printer")
public class Printer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "building")
    private String building;
    
    @Column(name = "room")
    private String room;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PrinterStatus status;
    
    @ManyToMany(mappedBy = "printers")
    private Set<SPSO> spsos;
    
    @OneToMany(mappedBy = "printer")
    private Set<PrintOrder> printOrders;
}
