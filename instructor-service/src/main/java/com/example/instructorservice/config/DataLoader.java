package com.example.instructorservice.config;

import com.example.instructorservice.entity.Instructor;
import com.example.instructorservice.repository.InstructorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final InstructorRepository instructorRepository;

    public DataLoader(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    @Override
    public void run(String... args) {
        instructorRepository.save(new Instructor(null, "Dr. Sopheak", "sopheak@example.com"));
        instructorRepository.save(new Instructor(null, "Dr. Chenda", "chenda@example.com"));
        instructorRepository.save(new Instructor(null, "Ms. Lina", "lina@example.com"));
    }
}
