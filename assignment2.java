import java.util.*;

// ================== ABSTRACT PERSON ==================
abstract class Person {
    protected String name;
    protected String email;

    Person() {}

    Person(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public abstract void displayInfo(); // abstract method
}

// ================== STUDENT CLASS ==================
class Student extends Person {

    private int rollNo;
    private String course;
    private double marks;
    private char grade;

    // Constructor Overloading
    Student() {}

    Student(int rollNo, String name, String email, String course, double marks) {
        super(name, email);
        this.rollNo = rollNo;
        this.course = course;
        this.marks = marks;
        calculateGrade();
    }

    // Method Overloading
    public void displayInfo(String note) {
        System.out.println(note);
        displayInfo();
    }

    private void calculateGrade() {
        if (marks >= 90) grade = 'A';
        else if (marks >= 75) grade = 'B';
        else if (marks >= 60) grade = 'C';
        else if (marks >= 40) grade = 'D';
        else grade = 'F';
    }

    // Overriding abstract method
    @Override
    public void displayInfo() {
        System.out.println("Student Info:");
        System.out.println("Roll No: " + rollNo);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Course: " + course);
        System.out.println("Marks: " + marks);
        System.out.println("Grade: " + grade);
        System.out.println("--------------------------------");
    }

    public int getRollNo() { return rollNo; }
}

// ================== INTERFACE ==================
interface RecordActions {
    void addStudent(Student s);
    void deleteStudent(int rollNo);
    void updateStudent(Student s);
    Student searchStudent(int rollNo);
    void viewAllStudents();
}

// ================== STUDENT MANAGER ==================
class StudentManager implements RecordActions {

    private Map<Integer, Student> map = new HashMap<>();

    @Override
    public void addStudent(Student s) {
        if (map.containsKey(s.getRollNo())) {
            System.out.println("Duplicate Roll Number! Cannot add student.");
        } else {
            map.put(s.getRollNo(), s);
            System.out.println("Student Added Successfully!");
        }
    }

    @Override
    public void deleteStudent(int rollNo) {
        if (map.remove(rollNo) != null) {
            System.out.println("Student Deleted Successfully.");
        } else {
            System.out.println("Student Not Found.");
        }
    }

    @Override
    public void updateStudent(Student s) {
        if (map.containsKey(s.getRollNo())) {
            map.put(s.getRollNo(), s);
            System.out.println("Student Updated Successfully!");
        } else {
            System.out.println("Student Not Found.");
        }
    }

    @Override
    public Student searchStudent(int rollNo) {
        return map.get(rollNo);
    }

    @Override
    public void viewAllStudents() {
        if (map.isEmpty()) {
            System.out.println("No student records available.");
        } else {
            for (Student s : map.values()) {
                s.displayInfo();
            }
        }
    }
}

// ================== MAIN CLASS ==================
public class assignment2 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        StudentManager manager = new StudentManager();

        while (true) {
            System.out.println("\n======= Student Management Menu =======");
            System.out.println("1. Add Student");
            System.out.println("2. Delete Student");
            System.out.println("3. Update Student");
            System.out.println("4. Search Student");
            System.out.println("5. View All Students");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter Roll No: ");
                    int r = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Name: ");
                    String n = sc.nextLine();

                    System.out.print("Enter Email: ");
                    String e = sc.nextLine();

                    System.out.print("Enter Course: ");
                    String c = sc.nextLine();

                    System.out.print("Enter Marks: ");
                    double m = sc.nextDouble();

                    manager.addStudent(new Student(r, n, e, c, m));
                    break;

                case 2:
                    System.out.print("Enter Roll No to Delete: ");
                    manager.deleteStudent(sc.nextInt());
                    break;

                case 3:
                    System.out.print("Enter Roll No: ");
                    int ur = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Name: ");
                    String un = sc.nextLine();

                    System.out.print("Enter Email: ");
                    String ue = sc.nextLine();

                    System.out.print("Enter Course: ");
                    String uc = sc.nextLine();

                    System.out.print("Enter Marks: ");
                    double um = sc.nextDouble();

                    manager.updateStudent(new Student(ur, un, ue, uc, um));
                    break;

                case 4:
                    System.out.print("Enter Roll No to Search: ");
                    Student s = manager.searchStudent(sc.nextInt());
                    if (s != null) s.displayInfo();
                    else System.out.println("Student Not Found.");
                    break;

                case 5:
                    manager.viewAllStudents();
                    break;

                case 6:
                    System.out.println("Exiting Program...");
                    return;

                default:
                    System.out.println("Invalid Choice! Try again.");
            }
        }
    }
}
