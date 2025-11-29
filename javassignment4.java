import java.io.*;
import java.util.*;

class Student {
    int rollNo;
    String name;
    String email;
    String course;
    double marks;

    public Student(int rollNo, String name, String email, String course, double marks) {
        this.rollNo = rollNo;
        this.name = name;
        this.email = email;
        this.course = course;
        this.marks = marks;
    }

    @Override
    public String toString() {
        return rollNo + "," + name + "," + email + "," + course + "," + marks;
    }
}

// ===================== FILE UTILITY CLASS ===================== //
class FileUtil {

    public static ArrayList<Student> loadStudents(String fileName) {
        ArrayList<Student> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                int roll = Integer.parseInt(data[0]);
                String name = data[1];
                String email = data[2];
                String course = data[3];
                double marks = Double.parseDouble(data[4]);

                list.add(new Student(roll, name, email, course, marks));
            }

            System.out.println("Loaded students from file:");
            for (Student s : list) {
                displayStudent(s);
            }

        } catch (IOException e) {
            System.out.println("File not found. Creating new empty student list.");
        }
        return list;
    }

    public static void saveStudents(String fileName, ArrayList<Student> list) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {

            for (Student s : list) {
                bw.write(s.toString());
                bw.newLine();
            }

            System.out.println("✔ Student records saved successfully!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void displayStudent(Student s) {
        System.out.println("Roll No: " + s.rollNo);
        System.out.println("Name: " + s.name);
        System.out.println("Email: " + s.email);
        System.out.println("Course: " + s.course);
        System.out.println("Marks: " + s.marks);
        System.out.println("-----------------------------");
    }
}

// ===================== STUDENT MANAGER ===================== //
class StudentManager {
    ArrayList<Student> list;

    public StudentManager(ArrayList<Student> list) {
        this.list = list;
    }

    public void addStudent(Scanner sc) {
        System.out.print("Enter Roll No: ");
        int roll = sc.nextInt(); sc.nextLine();

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        System.out.print("Enter Course: ");
        String course = sc.nextLine();

        System.out.print("Enter Marks: ");
        double marks = sc.nextDouble();

        list.add(new Student(roll, name, email, course, marks));
        System.out.println("✔ Student Added Successfully!");
    }

    public void viewAll() {
        if (list.isEmpty()) {
            System.out.println("No students to display.");
            return;
        }

        Iterator<Student> itr = list.iterator();

        while (itr.hasNext()) {
            FileUtil.displayStudent(itr.next());
        }
    }

    public void searchByName(String name) {
        for (Student s : list) {
            if (s.name.equalsIgnoreCase(name)) {
                FileUtil.displayStudent(s);
                return;
            }
        }
        System.out.println("Student not found.");
    }

    public void deleteByName(String name) {
        Iterator<Student> itr = list.iterator();

        while (itr.hasNext()) {
            if (itr.next().name.equalsIgnoreCase(name)) {
                itr.remove();
                System.out.println("✔ Student Deleted!");
                return;
            }
        }
        System.out.println("Student not found.");
    }

    public void sortByMarks() {
        list.sort(Comparator.comparingDouble(s -> s.marks));
        System.out.println("✔ Sorted by Marks!");
        viewAll();
    }

    public void showFileAttributes(String fileName) {
        File f = new File(fileName);

        System.out.println("\n==== File Attributes ====");
        System.out.println("File Name: " + f.getName());
        System.out.println("Path: " + f.getAbsolutePath());
        System.out.println("Size: " + f.length() + " bytes");
        System.out.println("Readable: " + f.canRead());
        System.out.println("Writable: " + f.canWrite());
    }

    public void randomReadExample(String fileName) {
        try (RandomAccessFile raf = new RandomAccessFile(fileName, "r")) {

            System.out.println("\n==== RandomAccessFile Demo ====");
            System.out.println("Reading first 50 bytes:");

            byte[] buffer = new byte[50];
            raf.read(buffer);

            System.out.println(new String(buffer));

        } catch (Exception e) {
            System.out.println("RandomAccessFile Error.");
        }
    }
}

// ===================== MAIN CLASS ===================== //
public class javassignment4 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String fileName = "students.txt";

        ArrayList<Student> list = FileUtil.loadStudents(fileName);
        StudentManager manager = new StudentManager(list);

        while (true) {
            System.out.println("\n===== Capstone Student Menu =====");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search by Name");
            System.out.println("4. Delete by Name");
            System.out.println("5. Sort by Marks");
            System.out.println("6. Show File Attributes");
            System.out.println("7. RandomAccessFile Demo");
            System.out.println("8. Save & Exit");
            System.out.print("Enter choice: ");

            int ch = sc.nextInt(); sc.nextLine();

            switch (ch) {
                case 1 -> manager.addStudent(sc);
                case 2 -> manager.viewAll();
                case 3 -> {
                    System.out.print("Enter Name: ");
                    manager.searchByName(sc.nextLine());
                }
                case 4 -> {
                    System.out.print("Enter Name: ");
                    manager.deleteByName(sc.nextLine());
                }
                case 5 -> manager.sortByMarks();
                case 6 -> manager.showFileAttributes(fileName);
                case 7 -> manager.randomReadExample(fileName);
                case 8 -> {
                    FileUtil.saveStudents(fileName, list);
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }
}
