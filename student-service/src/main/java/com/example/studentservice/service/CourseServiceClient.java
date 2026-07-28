package com.example.studentservice.service;

import com.example.studentservice.common.ApiResponse;
import com.example.studentservice.dto.CourseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "courseService", url = "http://localhost:8082", fallback = CourseServiceClientFallback.class)
public interface CourseServiceClient {

    @GetMapping("/api/v1/courses/{id}")
    ApiResponse<CourseDto> getCourseById(@PathVariable("id") Long id);
}
