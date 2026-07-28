package com.example.courseservice.service;

import com.example.courseservice.common.ApiResponse;
import com.example.courseservice.dto.InstructorDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "instructorService", url = "http://localhost:8083", fallback = InstructorServiceClientFallback.class)
public interface InstructorServiceClient {

    @GetMapping("/api/v1/instructors/{id}")
    ApiResponse<InstructorDto> getInstructorById(@PathVariable("id") Long id);
}
