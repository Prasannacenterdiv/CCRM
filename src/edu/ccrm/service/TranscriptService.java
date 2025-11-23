package edu.ccrm.service;

import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.Student;

public class TranscriptService {
    public void printTranscript(Student student) {
        System.out.println("\n--- TRANSCRIPT ---");
        System.out.println(student.getProfile());
        System.out.println("------------------");
        System.out.println("Enrolled Courses:");
        if (student.getEnrolledCourses().isEmpty()) {
            System.out.println("No courses enrolled.");
        } else {
            for (Enrollment e : student.getEnrolledCourses()) {
                String gradeStr = e.getGrade() != null ? e.getGrade().toString() : "Not Graded";
                System.out.printf("- %s (%s): %s\n", e.getCourse().getTitle(), e.getCourse().getCode(), gradeStr);
            }
        }
        System.out.printf("GPA: %.2f\n", calculateGpa(student));
        System.out.println("------------------");
    }

    private double calculateGpa(Student student) {
        double totalPoints = 0;
        int totalCredits = 0;

        for (Enrollment e : student.getEnrolledCourses()) {
            if (e.getGrade() != null) {
                totalPoints += e.getGrade().getGradePoint() * e.getCourse().getCredits();
                totalCredits += e.getCourse().getCredits();
            }
        }
        return totalCredits == 0 ? 0.0 : totalPoints / totalCredits;
    }
}