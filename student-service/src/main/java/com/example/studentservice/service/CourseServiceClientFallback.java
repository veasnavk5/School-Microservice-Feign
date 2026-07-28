package com.example.studentservice.service;

import com.example.studentservice.common.ApiResponse;
import com.example.studentservice.dto.CourseDto;
import com.example.studentservice.dto.InstructorDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class CourseServiceClientFallback implements CourseServiceClient {

    @Override
    public ApiResponse<CourseDto> getCourseById(Long id) {
        InstructorDto instructorFallback = new InstructorDto(0L, "N/A", "n/a");
        CourseDto fallback = new CourseDto(id, "Unavailable (fallback)", "n/a", instructorFallback);
        return ApiResponse.of(HttpStatus.OK, "Course service unavailable, returning fallback", fallback);
    }
}
