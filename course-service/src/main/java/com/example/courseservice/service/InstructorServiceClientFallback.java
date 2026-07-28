package com.example.courseservice.service;

import com.example.courseservice.common.ApiResponse;
import com.example.courseservice.dto.InstructorDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class InstructorServiceClientFallback implements InstructorServiceClient {

    @Override
    public ApiResponse<InstructorDto> getInstructorById(Long id) {
        InstructorDto fallback = new InstructorDto(id, "Unavailable (fallback)", "n/a");
        return ApiResponse.of(HttpStatus.OK, "Instructor service unavailable, returning fallback", fallback);
    }
}
