package com.lamcao1206.hcmut_ssps.core;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseFactory {

    public static <T> ResponseEntity<ApiResponse<T>> success(String message, T data) {
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK.value(), message, data));
    }

    public static <T> ResponseEntity<ApiResponse<T>> created(String message, T data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.of(HttpStatus.CREATED.value(), message, data));
    }

    public static <T> ResponseEntity<ApiResponse<T>> error(HttpStatus status, String message, T data) {
        return ResponseEntity.status(status).body(ApiResponse.of(status.value(), message, data));
    }
}