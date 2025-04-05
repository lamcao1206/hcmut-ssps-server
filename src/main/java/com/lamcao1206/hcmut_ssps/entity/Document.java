package com.lamcao1206.hcmut_ssps.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name")
    private String documentName;
    
    @Column(name = "type")
    private String documentType;
    
    @Column(name = "page")
    private String page;
    
    @ManyToOne
    @JoinColumn(name = "printer_id", nullable = false)
    private Printer printer;
}
