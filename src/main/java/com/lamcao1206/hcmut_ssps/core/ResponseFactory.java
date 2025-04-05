package com.lamcao1206.hcmut_ssps.core;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseFactory {

    public static <T> ResponseEntity<com.lamcao1206.hcmut_ssps.response.ApiResponse<T>> success(String message, T data) {
        return ResponseEntity.ok(com.lamcao1206.hcmut_ssps.response.ApiResponse.of(HttpStatus.OK.value(), message, data));
    }

    public static <T> ResponseEntity<com.lamcao1206.hcmut_ssps.response.ApiResponse<T>> created(String message, T data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(com.lamcao1206.hcmut_ssps.response.ApiResponse.of(HttpStatus.CREATED.value(), message, data));
    }

    public static <T> ResponseEntity<com.lamcao1206.hcmut_ssps.response.ApiResponse<T>> error(HttpStatus status, String message, T data) {
        return ResponseEntity.status(status).body(com.lamcao1206.hcmut_ssps.response.ApiResponse.of(status.value(), message, data));
    }
}
