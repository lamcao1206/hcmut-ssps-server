package com.lamcao1206.hcmut_ssps.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "document")
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
    
    @Column(name = "url")
    private String url;
    
    @OneToOne(mappedBy = "document")
    private PrintOrder order;
}
