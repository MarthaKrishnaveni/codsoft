import java.util.*;

class Student {
    private String id;
    private String name;
    private List<Course> courses;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
        this.courses = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void addCourse(Course course) {
        if (!courses.contains(course)) {
            courses.add(course);
            System.out.println("Successfully registered for: " + course.getName());
        } else {
            System.out.println("Already registered for: " + course.getName());
        }
    }

    public void viewCourses() {
        if (courses.isEmpty()) {
            System.out.println(name + " has not registered for any courses.");
        } else {
            System.out.println(name + " is registered for the following courses:");
            for (Course course : courses) {
                System.out.println(course.getName());
            }
        }
    }
}

class Course {
    private String code;
    private String name;
    private int maxStudents;
    private List<Student> enrolledStudents;

    public Course(String code, String name, int maxStudents) {
        this.code = code;
        this.name = name;
        this.maxStudents = maxStudents;
        this.enrolledStudents = new ArrayList<>();
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public boolean isFull() {
        return enrolledStudents.size() >= maxStudents;
    }

    public void addStudent(Student student) {
        if (!isFull() && !enrolledStudents.contains(student)) {
            enrolledStudents.add(student);
            student.addCourse(this);
        } else if (isFull()) {
            System.out.println("Course " + name + " is full.");
        } else {
            System.out.println("Student is already registered for this course.");
        }
    }
}

class RegistrationSystem {
    private Map<String, Student> students;
    private Map<String, Course> courses;

    public RegistrationSystem() {
        students = new HashMap<>();
        courses = new HashMap<>();
    }

    public void addStudent(String id, String name) {
        if (!students.containsKey(id)) {
            students.put(id, new Student(id, name));
            System.out.println("Student " + name + " added.");
        } else {
            System.out.println("Student with ID " + id + " already exists.");
        }
    }

    public void addCourse(String code, String name, int maxStudents) {
        if (!courses.containsKey(code)) {
            courses.put(code, new Course(code, name, maxStudents));
            System.out.println("Course " + name + " added.");
        } else {
            System.out.println("Course with code " + code + " already exists.");
        }
    }

    public void registerStudentForCourse(String studentId, String courseCode) {
        Student student = students.get(studentId);
        Course course = courses.get(courseCode);

        if (student != null && course != null) {
            course.addStudent(student);
        } else {
            System.out.println("Invalid student ID or course code.");
        }
    }

    public void viewStudentCourses(String studentId) {
        Student student = students.get(studentId);
        if (student != null) {
            student.viewCourses();
        } else {
            System.out.println("Student with ID " + studentId + " not found.");
        }
    }

    public void viewCourseStudents(String courseCode) {
        Course course = courses.get(courseCode);
        if (course != null) {
            System.out.println("Students registered for " + course.getName() + ":");
            for (Student student : course.getCourses()) {
                System.out.println(student.getName());
            }
        } else {
            System.out.println("Course with code " + courseCode + " not found.");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RegistrationSystem system = new RegistrationSystem();

        // Add some students and courses
        system.addStudent("S001", "Alice");
        system.addStudent("S002", "Bob");
        system.addCourse("CSE101", "Computer Science Basics", 3);
        system.addCourse("MAT101", "Mathematics 101", 2);

        boolean exit = false;

        while (!exit) {
            System.out.println("\n==== Student Course Registration System ====");
            System.out.println("1. Register for a course");
            System.out.println("2. View courses of a student");
            System.out.println("3. View students registered in a course");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    // Register student for a course
                    System.out.print("Enter student ID: ");
                    String studentId = scanner.nextLine();
                    System.out.print("Enter course code: ");
                    String courseCode = scanner.nextLine();
                    system.registerStudentForCourse(studentId, courseCode);
                    break;

                case 2:
                    // View student courses
                    System.out.print("Enter student ID: ");
                    studentId = scanner.nextLine();
                    system.viewStudentCourses(studentId);
                    break;

                case 3:
                    // View students registered in a course
                    System.out.print("Enter course code: ");
                    courseCode = scanner.nextLine();
                    system.viewCourseStudents(courseCode);
                    break;

                case 4:
                    // Exit
                    exit = true;
                    System.out.println("Exiting the system...");
                    break;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }

        scanner.close();
    }
}
