package com.lamcao1206.hcmut_ssps.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = { "id", "status", "document" }, allowGetters = true)
public class PrintOrderDTO {
    private Long id;
    private String status;
    private String orientation;
    private int numCopies;
    private DocumentDTO document;
    private Long printerId;
    private int page;
            
}
