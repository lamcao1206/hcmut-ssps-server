package com.lamcao1206.hcmut_ssps.DTO;


import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDTO {
    public int page;
    public Long price;
    public Date timestamp;
}
