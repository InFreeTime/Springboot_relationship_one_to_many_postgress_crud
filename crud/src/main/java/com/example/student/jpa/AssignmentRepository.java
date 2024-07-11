package com.example.student.jpa;

import com.example.student.model.Assignment;
import com.example.student.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByStudentId(Long studentId);

}
