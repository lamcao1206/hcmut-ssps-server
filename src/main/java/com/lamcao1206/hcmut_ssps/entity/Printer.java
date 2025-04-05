package com.lamcao1206.hcmut_ssps.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
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
    private Status status;
    
    @ManyToMany(mappedBy = "printers")
    private Set<SPSO> spsos;
    
    @OneToMany
    @JoinColumn(name = "printer_id")
    private Set<Document> documents;
}
