package com.example.courseservice.config;

import com.example.courseservice.entity.Course;
import com.example.courseservice.repository.CourseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final CourseRepository courseRepository;

    public DataLoader(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public void run(String... args) {
        courseRepository.save(new Course(null, "Introduction to Computer Science", "Basics of programming and computing", 1L));
        courseRepository.save(new Course(null, "Calculus II", "Advanced calculus concepts", 2L));
        courseRepository.save(new Course(null, "Academic English Writing", "Writing for academic purposes", 3L));
    }
}
