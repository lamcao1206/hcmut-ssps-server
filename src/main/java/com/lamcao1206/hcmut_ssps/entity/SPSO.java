package com.lamcao1206.hcmut_ssps.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Entity
@Data
public class SPSO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name")
    private String username;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "password")
    private String password;
    
    @Column(name = "last_login")
    private Date lastLogin;
    
    @ManyToMany
    @JoinTable(
            name = "spso_printer",
            joinColumns = @JoinColumn(name = "spso_id"),
            inverseJoinColumns = @JoinColumn(name = "printer_id")
    )
    private Set<Printer> printers;
}
