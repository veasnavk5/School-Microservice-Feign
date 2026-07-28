package com.example.instructorservice.service.impl;

import com.example.instructorservice.entity.Instructor;
import com.example.instructorservice.repository.InstructorRepository;
import com.example.instructorservice.service.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InstructorServiceImpl implements InstructorService {

    private final InstructorRepository instructorRepository;

    @Override
    public List<Instructor> getAllInstructors() {
        return instructorRepository.findAll();
    }

    @Override
    public Instructor getInstructorById(Long id) {
        return instructorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Instructor not found with id: " + id));
    }

    @Override
    public Instructor createInstructor(Instructor instructor) {
        return instructorRepository.save(instructor);
    }

    @Override
    public Instructor updateInstructor(Long id, Instructor instructor) {
        Instructor existing = getInstructorById(id);
        existing.setInstructorName(instructor.getInstructorName());
        existing.setEmail(instructor.getEmail());
        return instructorRepository.save(existing);
    }

    @Override
    public void deleteInstructor(Long id) {
        instructorRepository.deleteById(id);
    }
}
