package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Student;
import java.util.ArrayList;
import java.util.List;

public class DataStore {
    public final List<Student> students = new ArrayList<>();
    public final List<Course> courses = new ArrayList<>();
}