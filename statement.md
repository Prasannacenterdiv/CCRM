# **Project Statement – Campus Course & Records Manager (CCRM)**

## **1. Problem Statement**
Managing student information, course catalogs, enrollments, and academic performance is a core requirement for any educational institution. However, many institutes rely on manual systems or scattered digital files, leading to inconsistencies, data loss, and administrative delays.  
The **Campus Course & Records Manager (CCRM)** project addresses these issues by providing a structured, console-based Java application that organizes and automates these operations. It ensures accuracy, efficiency, and reliability through the use of modern Java features, object-oriented design, and file-based persistence.

---

## **2. Scope of the Project**a
The scope of the CCRM system includes the fundamental functionalities required for academic record management within small or medium-scale institutes. The system focuses on:

- Managing **student records**, including personal details and registration information.
- Maintaining **course data** using a builder pattern for flexible construction.
- Handling **student enrollments**, with validation rules and custom exceptions.
- Managing **grades** using enums to map grade types and values.
- Persisting data using **Java NIO.2 file I/O**.
- Creating **backups** of stored data with timestamps.
- Generating **reports** using Java Streams for sorting, filtering, and aggregation.
- Providing a **command-line interface** for simple and effective user interaction.

Out of Scope:
- No graphical user interface (GUI)
- No database or network support (file-based storage only)
- No authentication or multi-user role management

---

## **3. Target Users**
CCRM is intended for the following user groups:

### **• Academic Administrators**
To manage student details, course information, enrollments, and grades.

### **• Faculty Members**
To verify enrollment lists and student performance data.

### **• Students (Indirect Beneficiaries)**
Their academic records are maintained, though they do not interact directly with the application.

### **• Java Developers / Learners**
This project serves as a learning resource demonstrating:
- Object-Oriented Programming
- Java design patterns
- Exception handling
- NIO.2 file management
- Stream API usage
- CLI-based system design

---

## **4. High-Level Features**

### **Student Management**
- Add and view student data  
- Store and format registration timestamps  

### **Course Management**
- Create and maintain courses using a **Builder Pattern**  
- Use of static nested classes for structured course construction  

### **Enrollment Management**
- Enroll students with Duplicate and Max Credit validations  
- Custom exceptions for error handling  
- Assertions for ensuring input correctness  

### **Grades Management**
- Grade assignment using enums  
- Grade-based calculations and reporting  

### **File Storage & Backup**
- Store student, course, and enrollment data using NIO.2  
- Timestamped backup generation  
- Recursive directory size calculation  

### **Reports & Data Processing**
- Generate detailed reports using Stream API operations  
- Sorting, filtering, and aggregating enrollment and grade data  

### **CLI-Based Operation**
- Menu-driven interface handled by `CliManager`  
- Clean modular structure for easy extension  

---

