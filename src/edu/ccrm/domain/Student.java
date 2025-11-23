package edu.ccrm.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Student extends Person {
    private int id;
    private String regNo;
    private LocalDate registrationDate;
    private List<Enrollment> enrolledCourses;

    public Student(int id, String regNo, String fullName, String email) {
        this.id = id;
        this.regNo = regNo;
        this.fullName = fullName;
        this.email = email;
        this.registrationDate = LocalDate.now();
        this.enrolledCourses = new ArrayList<>();
    }

    @Override
    public String getProfile() {
        return String.format("Student ID: %d\nReg No: %s\nName: %s\nEmail: %s\nRegistered On: %s",
                id, regNo, fullName, email, registrationDate);
    }

    @Override
    public String toString() {
        return String.format("ID: %-5d | RegNo: %-10s | Name: %s", id, regNo, fullName);
    }

    public void enrollCourse(Enrollment enrollment) {
        this.enrolledCourses.add(enrollment);
    }

    public void unenrollCourse(String courseCode) {
        this.enrolledCourses.removeIf(e -> e.getCourse().getCode().equals(courseCode));
    }

    public int getId() {
        return id;
    }

    public String getRegNo() {
        return regNo;
    }

    public List<Enrollment> getEnrolledCourses() {
        return enrolledCourses;
    }
}