import java.io.*;
import java.util.*;

// ======================== CUSTOM EXCEPTION ========================
class StudentNotFoundException extends Exception {
    public StudentNotFoundException(String msg) {
        super(msg);
    }
}

// ======================== ABSTRACT PERSON ========================
abstract class Person {
    protected String name;
    protected String email;

    public abstract void displayInfo();
}

// ======================== STUDENT CLASS ========================
class Student extends Person {
    int rollNo;
    String course;
    double marks;
    char grade;

    public Student() {}

    public Student(int rollNo, String name, String email, String course, double marks) {
        this.rollNo = rollNo;
        this.name = name;
        this.email = email;
        this.course = course;
        this.marks = marks;
        calculateGrade();
    }

    public void inputDetails(Scanner sc) {
        try {
            System.out.print("Enter Roll No: ");
            rollNo = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Name: ");
            name = sc.nextLine();

            System.out.print("Enter Email: ");
            email = sc.nextLine();

            System.out.print("Enter Course: ");
            course = sc.nextLine();

            System.out.print("Enter Marks: ");
            marks = sc.nextDouble();

            if (marks < 0 || marks > 100)
                throw new IllegalArgumentException("Marks must be between 0 and 100!");

            calculateGrade();
        }
        catch (Exception e) {
            System.out.println("Input Error: " + e.getMessage());
        }
    }

    public void calculateGrade() {
        if (marks >= 90) grade = 'A';
        else if (marks >= 75) grade = 'B';
        else if (marks >= 60) grade = 'C';
        else if (marks >= 50) grade = 'D';
        else grade = 'F';
    }

    @Override
    public void displayInfo() {
        System.out.println("Roll No: " + rollNo);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Course: " + course);
        System.out.println("Marks: " + marks);
        System.out.println("Grade: " + grade);
        System.out.println("-----------------------------");
    }

    @Override
    public String toString() {
        return rollNo + "," + name + "," + email + "," + course + "," + marks;
    }
}

// ======================== INTERFACE ========================
interface RecordActions {
    void addStudent(Scanner sc);
    void deleteStudent(String name) throws StudentNotFoundException;
    void updateStudent(String name, Scanner sc) throws StudentNotFoundException;
    void searchStudent(String name) throws StudentNotFoundException;
    void viewAllStudents();
}

// ======================== LOADING THREAD ========================
class Loader implements Runnable {
    @Override
    public void run() {
        try {
            System.out.print("Loading");
            for (int i = 0; i < 5; i++) {
                Thread.sleep(300);
                System.out.print(".");
            }
            System.out.println();
        } catch (Exception e) {
            System.out.println("Loading failed!");
        }
    }
}

// ======================== STUDENT MANAGER ========================
class StudentManager implements RecordActions {

    Map<Integer, Student> map = new HashMap<>();

    public void loadFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("students.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String data[] = line.split(",");

                int roll = Integer.parseInt(data[0]);
                String name = data[1];
                String email = data[2];
                String course = data[3];
                double marks = Double.parseDouble(data[4]);

                map.put(roll, new Student(roll, name, email, course, marks));
            }
        } catch (Exception e) {
            System.out.println("No existing file found. Starting fresh.");
        }
    }

    public void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("students.txt"))) {
            for (Student s : map.values()) {
                bw.write(s.toString());
                bw.newLine();
            }
            System.out.println("✔ Records saved successfully!");
        } catch (Exception e) {
            System.out.println("Error saving file!");
        }
    }

    @Override
    public void addStudent(Scanner sc) {
        Student s = new Student();
        s.inputDetails(sc);

        if (map.containsKey(s.rollNo)) {
            System.out.println("Error: Duplicate Roll No!");
            return;
        }

        Thread t = new Thread(new Loader());
        t.start();

        map.put(s.rollNo, s);
        System.out.println("✔ Student added!");
    }

    @Override
    public void deleteStudent(String name) throws StudentNotFoundException {
        for (Student s : map.values()) {
            if (s.name.equalsIgnoreCase(name)) {
                map.remove(s.rollNo);
                System.out.println("✔ Student deleted!");
                return;
            }
        }
        throw new StudentNotFoundException("Student not found!");
    }

    @Override
    public void updateStudent(String name, Scanner sc) throws StudentNotFoundException {
        for (Student s : map.values()) {
            if (s.name.equalsIgnoreCase(name)) {
                System.out.println("Enter new details:");
                s.inputDetails(sc);
                System.out.println("✔ Student updated!");
                return;
            }
        }
        throw new StudentNotFoundException("Student not found!");
    }

    @Override
    public void searchStudent(String name) throws StudentNotFoundException {
        for (Student s : map.values()) {
            if (s.name.equalsIgnoreCase(name)) {
                System.out.println("Student Info:");
                s.displayInfo();
                return;
            }
        }
        throw new StudentNotFoundException("Student not found!");
    }

    @Override
    public void viewAllStudents() {
        Iterator<Student> itr = map.values().iterator();
        while (itr.hasNext()) {
            itr.next().displayInfo();
        }
    }

    public void sortByMarks() {
        List<Student> list = new ArrayList<>(map.values());
        list.sort((a, b) -> Double.compare(b.marks, a.marks));

        System.out.println("Sorted Students by Marks:");

        for (Student s : list) {
            s.displayInfo();
        }
    }
}

// ======================== MAIN CLASS ========================
public class javaassignment5 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        StudentManager manager = new StudentManager();
        manager.loadFromFile();

        while (true) {
            System.out.println("\n===== Capstone Student Menu =====");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search by Name");
            System.out.println("4. Delete by Name");
            System.out.println("5. Update Student");
            System.out.println("6. Sort by Marks");
            System.out.println("7. Save & Exit");
            System.out.print("Enter choice: ");

            int ch = sc.nextInt();
            sc.nextLine();

            try {
                switch (ch) {
                    case 1 -> manager.addStudent(sc);
                    case 2 -> manager.viewAllStudents();
                    case 3 -> {
                        System.out.print("Enter name: ");
                        manager.searchStudent(sc.nextLine());
                    }
                    case 4 -> {
                        System.out.print("Enter name: ");
                        manager.deleteStudent(sc.nextLine());
                    }
                    case 5 -> {
                        System.out.print("Enter name: ");
                        manager.updateStudent(sc.nextLine(), sc);
                    }
                    case 6 -> manager.sortByMarks();
                    case 7 -> {
                        manager.saveToFile();
                        System.out.println("Exiting...");
                        return;
                    }
                    default -> System.out.println("Invalid choice!");
                }
            }
            catch (StudentNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
