package com.example.studentservice.config;

import com.example.studentservice.entity.Student;
import com.example.studentservice.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final StudentRepository studentRepository;

    public DataLoader(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void run(String... args) {
        Student sokha = new Student(null, "Sokha", "sokha@example.com", "012345678", new ArrayList<>(List.of(1L, 2L)));
        Student dara = new Student(null, "Dara", "dara@example.com", "098765432", new ArrayList<>(List.of(3L)));

        studentRepository.save(sokha);
        studentRepository.save(dara);
    }
}
