package com.example.studentservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    private String studentName;
    private String email;
    private String phoneNumber;

    @ElementCollection
    @CollectionTable(name = "student_course", joinColumns = @JoinColumn(name = "student_id"))
    @Column(name = "course_id")
    private List<Long> enrolledCourseIds = new ArrayList<>();
}
