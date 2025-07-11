package src;

import Interface.ITeacher;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class TeacherManager implements ITeacher {
    private final String FILE_PATH = "data/teacher.csv";
    private List<Teacher> teachers;

    public TeacherManager(List<Teacher> teachers) {
        this.teachers = teachers;
        readFromFile();
    }

    @Override
    public void displayTeacherById(String idTeacher) {
        for (Teacher t : teachers) {
            if (t.getIdTeacher().equalsIgnoreCase(idTeacher)) {
                System.out.println("Thông tin giáo viên:");
                System.out.printf("ID: %s | Tên: %s | Ngày sinh: %s | Giới tính: %s | SĐT: %s%n",
                        t.getIdTeacher(), t.getTeacherName(),
                        t.getDateOfBirth(), t.getGender(), t.getPhoneNumber());
                return;
            }
        }

        System.out.println("Không tìm thấy giáo viên có mã: " + idTeacher);
    }

    @Override
    public void addTeacher() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập mã giáo viên: ");
        String id = sc.nextLine().trim();

        System.out.print("Nhập tên giáo viên: ");
        String name = sc.nextLine().trim();

        System.out.print("Nhập ngày sinh (dd/MM/yyyy): ");
        String dob = sc.nextLine().trim();

        System.out.print("Nhập giới tính: ");
        String gender = sc.nextLine().trim();

        System.out.print("Nhập số điện thoại: ");
        String phone = sc.nextLine().trim();

        Teacher teacher = new Teacher(id, name, dob, gender, phone);
        teachers.add(teacher);
        saveToFile();
        System.out.println("Thêm giáo viên thành công.");
    }

    @Override
    public void saveToFile() {
        File file = new File(FILE_PATH);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.newLine();
            for (Teacher t : teachers) {
                String line = String.join(",",
                        t.getIdTeacher(),
                        t.getTeacherName(),
                        t.getDateOfBirth(),
                        t.getGender(),
                        t.getPhoneNumber());
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Danh sách giáo viên đã được lưu vào file: " + FILE_PATH);
        } catch (IOException e) {
            System.out.println("Lỗi ghi file giáo viên: " + e.getMessage());
        }
    }

    @Override
    public void readFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine(); // bỏ dòng tiêu đề
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String id = parts[0];
                    String name = parts[1];
                    String dob = parts[2];
                    String gender = parts[3];
                    String phone = parts[4];

                    Teacher teacher = new Teacher(id, name, dob, gender, phone);
                    teachers.add(teacher);
                }
            }
            System.out.println("Đã tải " + teachers.size() + " giáo viên từ file.");
        } catch (IOException e) {
            System.out.println("Lỗi đọc file giáo viên: " + e.getMessage());
        }
    }
}


