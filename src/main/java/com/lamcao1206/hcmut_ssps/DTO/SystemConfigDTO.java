package com.lamcao1206.hcmut_ssps.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SystemConfigDTO {
    public int page;
    
    @JsonProperty("allowExtensions")
    public String allowExtentions;
}
