package com.example.instructorservice.service;

import com.example.instructorservice.entity.Instructor;

import java.util.List;

public interface InstructorService {
    List<Instructor> getAllInstructors();
    Instructor getInstructorById(Long id);
    Instructor createInstructor(Instructor instructor);
    Instructor updateInstructor(Long id, Instructor instructor);
    void deleteInstructor(Long id);
}
