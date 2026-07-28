package com.example.studentservice.service.impl;

import com.example.studentservice.dto.CourseDto;
import com.example.studentservice.dto.StudentRequest;
import com.example.studentservice.dto.StudentResponse;
import com.example.studentservice.entity.Student;
import com.example.studentservice.repository.StudentRepository;
import com.example.studentservice.service.CourseServiceClient;
import com.example.studentservice.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final CourseServiceClient courseServiceClient;

    @Override
    public List<StudentResponse> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public StudentResponse getStudentById(Long id) {
        Student student = findStudentById(id);
        return toResponse(student);
    }

    @Override
    public StudentResponse createStudent(StudentRequest request) {
        Student student = new Student();
        student.setStudentName(request.getStudentName());
        student.setEmail(request.getEmail());
        student.setPhoneNumber(request.getPhoneNumber());
        student.setEnrolledCourseIds(request.getCourseIds() != null ? new ArrayList<>(request.getCourseIds()) : new ArrayList<>());
        Student saved = studentRepository.save(student);
        return toResponse(saved);
    }

    @Override
    public StudentResponse updateStudent(Long id, StudentRequest request) {
        Student existing = findStudentById(id);
        existing.setStudentName(request.getStudentName());
        existing.setEmail(request.getEmail());
        existing.setPhoneNumber(request.getPhoneNumber());
        if (request.getCourseIds() != null) {
            existing.setEnrolledCourseIds(new ArrayList<>(request.getCourseIds()));
        }
        Student saved = studentRepository.save(existing);
        return toResponse(saved);
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public List<StudentResponse> getStudentsByCourseId(Long courseId) {
        return studentRepository.findByEnrolledCourseId(courseId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public StudentResponse enrollStudentInCourse(Long studentId, Long courseId) {
        Student student = findStudentById(studentId);
        if (!student.getEnrolledCourseIds().contains(courseId)) {
            student.getEnrolledCourseIds().add(courseId);
            studentRepository.save(student);
        }
        return toResponse(student);
    }

    private Student findStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
    }

    private StudentResponse toResponse(Student student) {
        List<CourseDto> courses = student.getEnrolledCourseIds().stream()
                .map(courseId -> courseServiceClient.getCourseById(courseId).getPayload())
                .collect(Collectors.toList());
        return new StudentResponse(
                student.getStudentId(),
                student.getStudentName(),
                student.getEmail(),
                student.getPhoneNumber(),
                courses
        );
    }
}
