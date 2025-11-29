import java.util.ArrayList;
import java.util.Scanner;

// ================== ABSTRACT PERSON ==================
abstract class Person {
    protected String name;

    Person() {
        this.name = "";
    }

    Person(String name) {
        this.name = name;
    }

    public abstract void display();
}

// ================== STUDENT CLASS ==================
class Student extends Person {
    private int rollNo;
    private String course;
    private double marks;
    private char grade;

    Student() {
        super();
    }

    Student(int rollNo, String name, String course, double marks) {
        super(name);
        this.rollNo = rollNo;
        this.course = course;
        this.marks = marks;
        calculateGrade();
    }

    private void calculateGrade() {
        if (marks >= 90) grade = 'A';
        else if (marks >= 75) grade = 'B';
        else if (marks >= 60) grade = 'C';
        else if (marks >= 50) grade = 'D';
        else grade = 'F';
    }

    @Override
    public void display() {
        System.out.println("Roll No: " + rollNo);
        System.out.println("Name: " + name);
        System.out.println("Course: " + course);
        System.out.println("Marks: " + marks);
        System.out.println("Grade: " + grade);
        System.out.println("------------------------");
    }
}

// ================== MAIN CLASS (CHANGE TO YOUR FINAL NAME) ==================
public class javaassignment3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Student> list = new ArrayList<>();

        System.out.print("Enter number of students: ");
        int n = sc.nextInt();

        for (int i = 0; i < n; i++) {
            System.out.println("\nEnter details for Student " + (i + 1) + ":");
            System.out.print("Roll No: ");
            int roll = sc.nextInt();
            sc.nextLine(); // clear buffer

            System.out.print("Name: ");
            String name = sc.nextLine();

            System.out.print("Course: ");
            String course = sc.nextLine();

            System.out.print("Marks: ");
            double marks = sc.nextDouble();

            list.add(new Student(roll, name, course, marks));
        }

        System.out.println("\n=========== Student Details ===========");
        for (Student s : list) {
            s.display();
        }

        sc.close();
    }
}
