# Campus Course & Records Manager (CCRM)

## Project Overview
This project is a console-based Java application for managing students, courses, enrollments, and grades for an educational institute. It demonstrates core Java principles, object-oriented programming, modern I/O, and common design patterns.

### How to Run
1.  **Prerequisites**: JDK 11 or higher must be installed.
2.  **Compile**: Open a terminal in the project's root directory and compile the source code:
    ```bash
    javac -d . src/edu/ccrm/Main.java src/edu/ccrm/cli/CliManager.java ... (include all .java files)
    ```
    A simpler way is to compile from the `src` directory:
    ```bash
    cd src
    javac edu/ccrm/Main.java
    cd ..
    ```
3.  **Run**: Execute the main class from the root directory:
    ```bash
    # Run normally
    java -cp src edu.ccrm.Main

    # Run with assertions enabled
    java -ea -cp src edu.ccrm.Main
    ```
---

## 1. Evolution of Java
* **1995**: Java 1.0 released by Sun Microsystems.
* **1998**: J2SE 1.2 (Java 2) released, introducing the Collections framework.
* **2004**: J2SE 5.0 (Tiger) released, adding Generics, Enums, and Annotations.
* **2011**: Java SE 7 released, adding Strings in switch, try-with-resources.
* **2014**: Java SE 8 released, a major update introducing Lambdas, the Stream API, and a new Date/Time API.
* **2017**: Java SE 9 introduced the Module System (Project Jigsaw).
* **2018-Present**: Java moves to a 6-month release cadence. Long-Term Support (LTS) versions (like 11, 17, 21) are released every few years.

---

## 2. Java ME vs SE vs EE

| Feature              | Java ME (Micro Edition)                             | Java SE (Standard Edition)                            | Java EE (Enterprise Edition)                            |
| -------------------- | --------------------------------------------------- | ----------------------------------------------------- | ------------------------------------------------------- |
| **Primary Use** | Resource-constrained devices (IoT, old mobiles)     | Desktop, server, console applications (core platform) | Large-scale, distributed enterprise applications        |
| **Core API** | A small subset of Java SE API                       | The complete core Java API                            | Extends Java SE with additional enterprise libraries    |
| **Example APIs** | `MIDlet`, `Generic Connection Framework`              | `java.lang`, `java.util`, `java.io`, `java.net`         | `Servlets`, `JPA`, `JMS`, `WebSockets`                    |
| **JVM** | Highly optimized, small-footprint JVMs (e.g., KVM)  | The standard Java Virtual Machine (JVM)               | Runs on an application server (e.g., GlassFish, WildFly)|
| **This Project Uses**| No                                                  | **Yes** | No                                                      |

---

## 3. Java Architecture: JDK, JRE, JVM

* **JVM (Java Virtual Machine)**: An abstract machine that provides a runtime environment in which Java bytecode can be executed. It's the component that makes Java "write once, run anywhere." It interprets the compiled `.class` files.
* **JRE (Java Runtime Environment)**: A software package that contains what is required to run a Java program. It includes the JVM and the core Java libraries (APIs). A user only needs the JRE to run Java applications; they do not need the JDK.
* **JDK (Java Development Kit)**: The full-featured development kit for Java. It includes everything in the JRE, plus development tools like the compiler (`javac`), debugger (`jdb`), and archiver (`jar`). You need the JDK to write and compile Java code.

**Interaction**: A developer writes Java code (`.java` file) -> uses the `javac` compiler from the **JDK** to create bytecode (`.class` file) -> a user runs the program, and the **JRE** provides the libraries and the **JVM** to execute that bytecode.

---

## 4. Setup & Installation (Windows)

1.  **Download JDK**: Download the JDK installer from Oracle or an OpenJDK provider like Adoptium.
2.  **Run Installer**: Run the installer and follow the on-screen instructions.
3.  **Set Environment Variables**:
    * Set `JAVA_HOME` to the JDK installation directory (e.g., `C:\Program Files\Java\jdk-17`).
    * Add the JDK's `bin` directory to the system's `Path` variable (`%JAVA_HOME%\bin`).
4.  **Verification**: Open Command Prompt and run `java -version`.

**My Screenshot:**
> ![alt text](Screenshots/image.png)

---

## 5. IDE Setup (VS Code)

1.  **Install Extension Pack**: Install the "Extension Pack for Java" from the Visual Studio Code Marketplace.
2.  **Create Project**: Create a root folder (e.g., `CCRMProject`). Open this folder in VS Code.
3.  **Create Structure**: Create the `src` directory and the package structure (`edu/ccrm/...`) inside it.
4.  **Run Application**: Open the `Main.java` file and click the "Run" button provided by the Java extension.

**My Screenshots:**
> ![alt text](<Screenshots/Screenshot 2025-09-24 224908.png>)
>
> ![alt text](<Screenshots/Screenshot 2025-09-24 225138.png>)
>
> ![alt text](<Screenshots/Screenshot 2025-09-24 225306.png>)
>
> ![alt text](<Screenshots/Screenshot 2025-09-24 225927.png>)
>
> ![alt text](<Screenshots/Screenshot 2025-09-24 230206.png>)

---

## 6. Syllabus Topic Mapping

| Syllabus Topic                      | File/Class/Method Where Demonstrated                                 |
| ----------------------------------- | -------------------------------------------------------------------- |
| **OOP - Encapsulation** | `Person.java`, `Student.java` (private fields with getters/setters)  |
| **OOP - Inheritance** | `Student.java` (extends `Person`)                                    |
| **OOP - Abstraction** | `Person.java` (abstract class with abstract `getProfile` method)     |
| **OOP - Polymorphism** | `Student.java` (overriding `getProfile` method)                      |
| **Design Pattern - Singleton** | `AppConfig.java`                                                     |
| **Design Pattern - Builder** | `Course.java` and its inner `CourseBuilder` class                    |
| **Exceptions (Custom)** | `DuplicateEnrollmentException.java`, `MaxCreditLimitExceededException.java` |
| **Exceptions (Handling)** | `CliManager.java` (try-catch, multi-catch in `manageEnrollment`)     |
| **File I/O (NIO.2)** | `FileService.java` (uses `Path`, `Files`, `Paths`)                   |
| **File I/O (Backup)** | `FileService.createBackup` (uses `Files.walk`, `Files.copy`)         |
| **Streams API (File I/O)** | `FileService.java` (uses `Files.lines` to read files)                |
| **Streams API (Data Processing)** | `CliManager.java` (`manageCourses`, `showReports`)                   |
| **Date/Time API** | `Student.java` (`registrationDate`), `FileService.java` (backup timestamp) |
| **Enums with Fields** | `Grade.java`, `Semester.java`                                        |
| **Lambdas** | `CliManager.showReports` (used in `Comparator.comparingDouble`)      |
| **Recursion** | `RecursiveFileUtils.calculateDirectorySize`                          |
| **Nested Classes (static)** | `Course.CourseBuilder` in `Course.java`                              |
| **`toString()` Override** | `Student.java`, `Course.java`                                        |
| **Assertions** | `EnrollmentService.enrollStudent` (checks for null inputs)           |