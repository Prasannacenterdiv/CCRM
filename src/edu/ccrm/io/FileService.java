package edu.ccrm.io;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Semester;
import edu.ccrm.domain.Student;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileService {

    public List<Student> importStudents(String filePath) throws IOException {
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            return lines.skip(1)
                    .map(line -> {
                        String[] p = line.split(",");
                        return new Student(Integer.parseInt(p[0]), p[1], p[2], p[3]);
                    }).collect(Collectors.toList());
        }
    }

    public List<Course> importCourses(String filePath) throws IOException {
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            return lines.skip(1)
                    .map(line -> {
                        String[] p = line.split(",");

                        return new Course.CourseBuilder(p[0], p[1])
                                .credits(Integer.parseInt(p[2]))
                                .instructor(p[3])
                                .semester(Semester.valueOf(p[4].toUpperCase()))
                                .department(p[5])
                                .build();
                    }).collect(Collectors.toList());
        }
    }

    public void exportData(String dir, List<Student> students, List<Course> courses) throws IOException {
        Path dirPath = Paths.get(dir);
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }

        List<String> studentLines = students.stream()
                .map(s -> String.join(",", String.valueOf(s.getId()), s.getRegNo(), s.getFullName(), s.getEmail()))
                .collect(Collectors.toList());
        studentLines.add(0, "id,regNo,fullName,email");
        Files.write(dirPath.resolve("students_export.csv"), studentLines);

        List<String> courseLines = courses.stream()
                .map(c -> String.join(",", c.getCode(), c.getTitle(), String.valueOf(c.getCredits()), c.getInstructor(),
                        c.getSemester().name(), c.getDepartment()))
                .collect(Collectors.toList());
        courseLines.add(0, "code,title,credits,instructor,semester,department");
        Files.write(dirPath.resolve("courses_export.csv"), courseLines);
    }

    public void createBackup(String sourceDir) throws IOException {
        Path sourcePath = Paths.get(sourceDir);
        if (!Files.exists(sourcePath)) {
            System.out.println("Source directory for backup does not exist: " + sourceDir);
            return;
        }

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        Path backupPath = Paths.get("backups", "backup_" + timestamp);
        Files.createDirectories(backupPath);

        try (Stream<Path> stream = Files.walk(sourcePath)) {
            stream.forEach(source -> {
                try {

                    Files.copy(source, backupPath.resolve(sourcePath.relativize(source)),
                            StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    System.err.println("Failed to copy file to backup: " + e.getMessage());
                }
            });
        }
        System.out.println("Backup created successfully at: " + backupPath.toAbsolutePath());
    }
}