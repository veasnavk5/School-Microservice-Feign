package com.example.courseservice.service.impl;

import com.example.courseservice.dto.CourseRequest;
import com.example.courseservice.dto.CourseResponse;
import com.example.courseservice.dto.InstructorDto;
import com.example.courseservice.entity.Course;
import com.example.courseservice.repository.CourseRepository;
import com.example.courseservice.service.CourseService;
import com.example.courseservice.service.InstructorServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final InstructorServiceClient instructorServiceClient;

    @Override
    public List<CourseResponse> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CourseResponse getCourseById(Long id) {
        Course course = findCourseById(id);
        return toResponse(course);
    }

    @Override
    public CourseResponse createCourse(CourseRequest request) {
        Course course = new Course();
        course.setCourseName(request.getCourseName());
        course.setDescription(request.getDescription());
        course.setInstructorId(request.getInstructorId());
        Course saved = courseRepository.save(course);
        return toResponse(saved);
    }

    @Override
    public CourseResponse updateCourse(Long id, CourseRequest request) {
        Course existing = findCourseById(id);
        existing.setCourseName(request.getCourseName());
        existing.setDescription(request.getDescription());
        existing.setInstructorId(request.getInstructorId());
        Course saved = courseRepository.save(existing);
        return toResponse(saved);
    }

    @Override
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    private Course findCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
    }

    private CourseResponse toResponse(Course course) {
        InstructorDto instructor = instructorServiceClient.getInstructorById(course.getInstructorId()).getPayload();
        return new CourseResponse(course.getCourseId(), course.getCourseName(), course.getDescription(), instructor);
    }
}
