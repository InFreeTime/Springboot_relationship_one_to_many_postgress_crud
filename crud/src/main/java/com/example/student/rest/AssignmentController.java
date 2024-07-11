package com.example.student.rest;

import com.example.student.exception.NotFoundException;
import com.example.student.jpa.AssignmentRepository;
import com.example.student.jpa.StudentRepository;
import com.example.student.model.Assignment;
import com.example.student.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AssignmentController {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/students/{studentId}/assignments")
    public List<Assignment> getContactByStudentId(@PathVariable Long studentId) {

        if(!studentRepository.existsById(studentId)) {
            throw new NotFoundException("Student not found!");
        }

        return assignmentRepository.findByStudentId(studentId);
    }

    @PostMapping("/students/{studentId}/assignments")
    public Assignment addAssignment(@PathVariable Long studentId,
                                     @RequestBody Assignment assignment) {
        return studentRepository.findById(studentId)
                .map(student -> {
                    assignment.setStudent(student);
                    return assignmentRepository.save(assignment);
                }).orElseThrow(() -> new NotFoundException("Student not found!"));
    }

    @PutMapping("/students/{studentId}/assignments/{assignmentId}")
    public Assignment updateAssignment(@PathVariable Long studentId,
                                       @PathVariable Long assignmentId,
                                        @RequestBody Assignment assignmentUpdated) {

        if(!studentRepository.existsById(studentId)) {
            throw new NotFoundException("Student not found!");
        }

        return assignmentRepository.findById(assignmentId)
                .map(assignment -> {
                    assignment.setName(assignmentUpdated.getName());
                    assignment.setGrade(assignmentUpdated.getGrade());
                    return assignmentRepository.save(assignment);
                }).orElseThrow(() -> new NotFoundException("Assignment not found!"));
    }

    @DeleteMapping("/students/{studentId}/assignments/{assignmentId}")
    public String deleteAssignment(@PathVariable Long studentId,
                                   @PathVariable Long assignmentId) {

        if(!studentRepository.existsById(studentId)) {
            throw new NotFoundException("Student not found!");
        }

        return assignmentRepository.findById(assignmentId)
                .map(assignment -> {
                    assignmentRepository.delete(assignment);
                    return "Deleted Successfully!";
                }).orElseThrow(() -> new NotFoundException("Contact not found!"));
    }

}
