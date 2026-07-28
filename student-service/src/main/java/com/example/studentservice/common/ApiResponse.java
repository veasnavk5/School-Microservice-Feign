package com.example.studentservice.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> {

    private String success;
    private String status;
    private String message;
    private T payload;
    private String time;

    public static <T> ApiResponse<T> of(HttpStatus status, String message, T payload) {
        return ApiResponse.<T>builder()
                .success("true")
                .status(status.value() + " " + status.getReasonPhrase())
                .message(message)
                .payload(payload)
                .time(Instant.now().toString())
                .build();
    }

    public static ApiResponse<Void> of(HttpStatus status, String message) {
        return ApiResponse.<Void>builder()
                .success("true")
                .status(status.value() + " " + status.getReasonPhrase())
                .message(message)
                .time(Instant.now().toString())
                .build();
    }
}
