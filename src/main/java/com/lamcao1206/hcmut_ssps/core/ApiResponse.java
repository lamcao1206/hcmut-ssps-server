package com.lamcao1206.hcmut_ssps.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private LocalDateTime timestamp;
    private int status;
    private String message;
    private T data;

    public static <T> ApiResponse<T> of(int status, String message, T data) {
        return new ApiResponse<>(LocalDateTime.now(), status, message, data);
    }
}
