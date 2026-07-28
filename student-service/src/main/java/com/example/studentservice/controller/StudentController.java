package com.example.studentservice.controller;

import com.example.studentservice.common.ApiResponse;
import com.example.studentservice.dto.StudentRequest;
import com.example.studentservice.dto.StudentResponse;
import com.example.studentservice.service.StudentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
@Tag(name = "Student API")
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<ApiResponse<StudentResponse>> createStudent(@RequestBody StudentRequest request) {
        StudentResponse created = studentService.createStudent(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.of(HttpStatus.CREATED, "Student created successfully", created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponse>> getStudentById(@PathVariable Long id) {
        StudentResponse student = studentService.getStudentById(id);
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Student retrieved successfully", student));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponse>> updateStudent(@PathVariable Long id, @RequestBody StudentRequest request) {
        StudentResponse updated = studentService.updateStudent(id, request);
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Student updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Student deleted successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<StudentResponse>>> getAllStudents() {
        List<StudentResponse> students = studentService.getAllStudents();
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Students retrieved successfully", students));
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<ApiResponse<List<StudentResponse>>> getStudentsByCourseId(@PathVariable Long courseId) {
        List<StudentResponse> students = studentService.getStudentsByCourseId(courseId);
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Students retrieved successfully", students));
    }

    @PostMapping("/{studentId}/enroll/{courseId}")
    public ResponseEntity<ApiResponse<StudentResponse>> enrollStudentInCourse(@PathVariable Long studentId, @PathVariable Long courseId) {
        StudentResponse student = studentService.enrollStudentInCourse(studentId, courseId);
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Student enrolled successfully", student));
    }
}
