package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.Grade;
import edu.ccrm.domain.Student;
import edu.ccrm.exception.DuplicateEnrollmentException;
import edu.ccrm.exception.MaxCreditLimitExceededException;

public class EnrollmentService {
    private static final int MAX_CREDITS_PER_SEMESTER = 18;

    public void enrollStudent(Student student, Course course)
            throws DuplicateEnrollmentException, MaxCreditLimitExceededException {

        assert student != null : "Student cannot be null for enrollment.";
        assert course != null : "Course cannot be null for enrollment.";

        boolean alreadyEnrolled = student.getEnrolledCourses().stream()
                .anyMatch(e -> e.getCourse().getCode().equals(course.getCode()));
        if (alreadyEnrolled) {
            throw new DuplicateEnrollmentException("Student is already enrolled in course " + course.getCode());
        }

        int currentCredits = student.getEnrolledCourses().stream()
                .filter(e -> e.getCourse().getSemester() == course.getSemester())
                .mapToInt(e -> e.getCourse().getCredits())
                .sum();
        if (currentCredits + course.getCredits() > MAX_CREDITS_PER_SEMESTER) {
            throw new MaxCreditLimitExceededException(
                    "Enrolling in this course exceeds the max credit limit of " + MAX_CREDITS_PER_SEMESTER);
        }

        Enrollment enrollment = new Enrollment(student, course);
        student.enrollCourse(enrollment);
    }

    public void assignGrade(Student student, String courseCode, Grade grade) {
        student.getEnrolledCourses().stream()
                .filter(e -> e.getCourse().getCode().equals(courseCode))
                .findFirst()
                .ifPresent(enrollment -> enrollment.setGrade(grade));
    }
}