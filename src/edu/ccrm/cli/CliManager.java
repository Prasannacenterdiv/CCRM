package edu.ccrm.cli;

import edu.ccrm.config.AppConfig;
import edu.ccrm.domain.*;
import edu.ccrm.exception.DuplicateEnrollmentException;
import edu.ccrm.exception.MaxCreditLimitExceededException;
import edu.ccrm.io.FileService;
import edu.ccrm.service.DataStore;
import edu.ccrm.service.EnrollmentService;
import edu.ccrm.service.TranscriptService;
import edu.ccrm.util.RecursiveFileUtils;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CliManager {
    private final Scanner scanner = new Scanner(System.in);
    private final DataStore dataStore = new DataStore();
    private final EnrollmentService enrollmentService = new EnrollmentService();
    private final TranscriptService transcriptService = new TranscriptService();
    private final FileService fileService = new FileService();

    public void start() {

        loadSampleData();

        boolean exit = false;
        while (!exit) {
            printMenu();
            System.out.print("Enter your choice: ");
            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    manageStudents();
                    break;
                case "2":
                    manageCourses();
                    break;
                case "3":
                    manageEnrollment();
                    break;
                case "4":
                    handleFileOperations();
                    break;
                case "5":
                    showReports();
                    break;
                case "6":
                    System.out.println(getJavaPlatformInfo());
                    break;
                case "7":
                    exit = true;
                    System.out.println("Exiting application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n===== Campus Course & Records Manager (CCRM) =====");
        System.out.println("1. Manage Students");
        System.out.println("2. Manage Courses");
        System.out.println("3. Manage Enrollment & Grades");
        System.out.println("4. File Operations (Import/Export/Backup)");
        System.out.println("5. View Reports");
        System.out.println("6. Show Java Platform Info");
        System.out.println("7. Exit");
        System.out.println("==================================================");
    }

    private void manageStudents() {
        System.out.println("\n--- Student Management ---");
        System.out.println("1. List All Students");
        System.out.println("2. View Student Transcript");
        System.out.print("Choose an option: ");
        String choice = scanner.nextLine();
        if (choice.equals("1")) {
            dataStore.students.forEach(System.out::println);
        } else if (choice.equals("2")) {
            System.out.print("Enter Student ID: ");
            try {
                int id = Integer.parseInt(scanner.nextLine());
                dataStore.students.stream()
                        .filter(s -> s.getId() == id)
                        .findFirst()
                        .ifPresentOrElse(
                                transcriptService::printTranscript,
                                () -> System.out.println("Student not found."));
            } catch (NumberFormatException e) {
                System.out.println("Invalid ID format.");
            }
        }
    }

    private void manageCourses() {
        System.out.println("\n--- Course Management ---");
        System.out.println("1. List All Courses");
        System.out.println("2. Search/Filter Courses");
        System.out.print("Choose an option: ");
        String choice = scanner.nextLine();
        if (choice.equals("1")) {
            dataStore.courses.forEach(System.out::println);
        } else if (choice.equals("2")) {
            System.out.print("Enter search term (department, instructor, etc.): ");
            String term = scanner.nextLine().toLowerCase();
            List<Course> results = dataStore.courses.stream()
                    .filter(c -> c.getDepartment().toLowerCase().contains(term) ||
                            c.getInstructor().toLowerCase().contains(term))
                    .collect(Collectors.toList());
            System.out.println("Search Results:");
            results.forEach(System.out::println);
        }
    }

    private void manageEnrollment() {
        System.out.println("\n--- Enrollment & Grading ---");
        System.out.println("1. Enroll Student in Course");
        System.out.println("2. Assign Grade");
        System.out.print("Choose an option: ");
        String choice = scanner.nextLine();

        try {
            if (choice.equals("1")) {
                System.out.print("Enter Student ID: ");
                int studentId = Integer.parseInt(scanner.nextLine());
                System.out.print("Enter Course Code: ");
                String courseCode = scanner.nextLine();

                Student student = dataStore.students.stream().filter(s -> s.getId() == studentId).findFirst()
                        .orElse(null);
                Course course = dataStore.courses.stream().filter(c -> c.getCode().equals(courseCode)).findFirst()
                        .orElse(null);

                if (student != null && course != null) {
                    enrollmentService.enrollStudent(student, course);
                    System.out.println("Enrollment successful!");
                } else {
                    System.out.println("Student or Course not found.");
                }
            } else if (choice.equals("2")) {
                System.out.print("Enter Student ID: ");
                int studentId = Integer.parseInt(scanner.nextLine());
                System.out.print("Enter Course Code: ");
                String courseCode = scanner.nextLine();
                System.out.print("Enter Grade (S, A, B, C, D, E, F): ");
                Grade grade = Grade.valueOf(scanner.nextLine().toUpperCase());

                Student student = dataStore.students.stream().filter(s -> s.getId() == studentId).findFirst()
                        .orElse(null);
                if (student != null) {
                    enrollmentService.assignGrade(student, courseCode, grade);
                    System.out.println("Grade assigned successfully.");
                } else {
                    System.out.println("Student not found.");
                }
            }

        } catch (NumberFormatException e) {
            System.err.println("Error: Invalid number format.");
        } catch (IllegalArgumentException e) {
            System.err.println("Error: Invalid grade entered.");
        } catch (DuplicateEnrollmentException | MaxCreditLimitExceededException e) {
            System.err.println("Enrollment Error: " + e.getMessage());
        }
    }

    private void handleFileOperations() {
        System.out.println("\n--- File Operations ---");
        System.out.println("1. Import Data from CSV");
        System.out.println("2. Export Current Data");
        System.out.println("3. Create Backup of Exported Data");
        System.out.println("4. Calculate Backup Directory Size (Recursive)");
        System.out.print("Choose an option: ");
        String choice = scanner.nextLine();

        String exportDir = AppConfig.getInstance().getDataExportDirectory();

        try {
            if (choice.equals("1")) {
                dataStore.students.clear();
                dataStore.courses.clear();
                dataStore.students.addAll(fileService.importStudents("test-data/students.csv"));
                dataStore.courses.addAll(fileService.importCourses("test-data/courses.csv"));
                System.out.println("Data imported successfully from 'test-data' folder.");
            } else if (choice.equals("2")) {
                fileService.exportData(exportDir, dataStore.students, dataStore.courses);
                System.out.println("Data exported successfully to '" + exportDir + "' folder.");
            } else if (choice.equals("3")) {
                fileService.createBackup(exportDir);
            } else if (choice.equals("4")) {
                long size = RecursiveFileUtils.calculateDirectorySize(Paths.get("backups"));
                System.out.printf("Total size of 'backups' directory: %.2f KB\n", size / 1024.0);
            }
        } catch (IOException e) {
            System.err.println("File operation failed: " + e.getMessage());
        }
    }

    private void showReports() {
        System.out.println("\n--- Reports ---");

        System.out.println("Top Students (Sorted by GPA):");
        dataStore.students.stream()
                .sorted(Comparator.comparingDouble(s -> -calculateGpa(s)))
                .limit(3)
                .forEach(s -> System.out.printf(" - %s (GPA: %.2f)\n", s.getFullName(), calculateGpa(s)));
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

    private void loadSampleData() {

        Student s1 = new Student(101, "S101", "Alice Johnson", "alice@example.com");
        Student s2 = new Student(102, "S102", "Bob Smith", "bob@example.com");
        dataStore.students.addAll(Arrays.asList(s1, s2));

        Course c1 = new Course.CourseBuilder("CS101", "Intro to Programming").credits(3).instructor("Dr. Turing")
                .semester(Semester.FALL).department("Computer Science").build();
        Course c2 = new Course.CourseBuilder("MA201", "Calculus I").credits(4).instructor("Dr. Newton")
                .semester(Semester.FALL).department("Mathematics").build();
        dataStore.courses.addAll(Arrays.asList(c1, c2));

        try {
            enrollmentService.enrollStudent(s1, c1);
            enrollmentService.enrollStudent(s2, c1);
            enrollmentService.enrollStudent(s2, c2);
            enrollmentService.assignGrade(s1, "CS101", Grade.A);
            enrollmentService.assignGrade(s2, "CS101", Grade.B);
            enrollmentService.assignGrade(s2, "MA201", Grade.S);
        } catch (Exception e) {
        }
    }

    private String getJavaPlatformInfo() {
        return """
                --- Java Platform Differentiation ---
                * Java SE (Standard Edition): The core Java platform. It's used for developing desktop applications, servers, and console applications like this one. It contains the fundamental libraries (java.lang, java.util, etc.), the JVM, and the JDK.
                * Java EE (Enterprise Edition): Built on top of Java SE. It provides a larger framework for building large-scale, multi-tiered, and reliable enterprise applications (e.g., web services, large banking systems). It includes APIs for things like Servlets, WebSockets, and more.
                * Java ME (Micro Edition): A subset of Java SE designed for resource-constrained devices like mobile phones (before Android), IoT devices, and embedded systems. It has a smaller footprint and specialized libraries for mobile development.
                """;
    }
}