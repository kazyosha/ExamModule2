package src;

import Interface.IClass;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class ClassManage implements IClass {
    private final String FILE_PATH = "data/class.csv";
    List<SchoolClass> classes;

    public ClassManage(List<SchoolClass> classes) {
        this.classes = classes;
        readFromFile();
    }

    @Override
    public void addClass(List<Teacher> teachers) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập mã lớp: ");
        String idClass = sc.nextLine().trim();

        System.out.print("Nhập tên lớp: ");
        String className = sc.nextLine().trim();

        System.out.print("Nhập mã giáo viên chủ nhiệm: ");
        String idTeacher = sc.nextLine().trim();

        // Kiểm tra mã giáo viên có tồn tại không
        boolean exists = false;
        for (Teacher t : teachers) {
            if (t.getIdTeacher().equalsIgnoreCase(idTeacher)) {
                exists = true;
                break;
            }
        }

        if (!exists) {
            System.out.println("Mã giáo viên không tồn tại.");
            return;
        }

        SchoolClass schoolClass = new SchoolClass(idClass, className, idTeacher);
        classes.add(schoolClass);
        saveToFile();
        System.out.println("Thêm lớp học thành công.");
    }

    @Override
    public void saveToFile() {
        File file = new File(FILE_PATH);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("idClass,className,idTeacher");
            writer.newLine();
            for (SchoolClass c : classes) {
                String line = String.join(",", c.getIdClass(), c.getClassName(), c.getIdTeacher());
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Danh sách lớp đã được lưu vào file: " + FILE_PATH);
        } catch (IOException e) {
            System.out.println("Lỗi ghi file lớp học: " + e.getMessage());
        }
    }

    @Override
    public void readFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine(); // bỏ header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String idClass = parts[0];
                    String className = parts[1];
                    String idTeacher = parts[2];

                    SchoolClass schoolClass = new SchoolClass(idClass, className, idTeacher);
                    classes.add(schoolClass);
                }
            }
            System.out.println("Đã tải " + classes.size() + " lớp học từ file.");
        } catch (IOException e) {
            System.out.println("Lỗi đọc file lớp học: " + e.getMessage());
        }
    }
}

