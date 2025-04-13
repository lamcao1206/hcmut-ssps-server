package com.lamcao1206.hcmut_ssps.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    
    @Column(name = "name", unique = true)
    private String name;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "room")
    private String room;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PrinterStatus status;
    
    @OneToMany(mappedBy = "printer")
    private Set<PrintOrder> printOrders;
}
