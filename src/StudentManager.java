package src;

import Interface.IStudent;
import exception.StudentNotFoundException;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentManager implements IStudent {
    private final String FILE_PATH = "data/student.csv";
    private List<Student> students = new ArrayList<>();
    private List<SchoolClass> classes;

    private Scanner sc = new Scanner(System.in);

    public StudentManager(List<SchoolClass> classes) {
        this.classes = classes;
        readFromFile();
    }

    private String inputName() {
        while (true) {
            System.out.print("Nhập tên học sinh (4–50 ký tự): ");
            String name = sc.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("❌ Tên không được để trống.");
            } else if (name.length() < 4 || name.length() > 50) {
                System.out.println("❌ Tên phải từ 4 đến 50 ký tự.");
            } else if (!name.matches("^[a-zA-ZÀ-ỹ\\s]+$")) {
                System.out.println("❌ Tên chỉ được chứa chữ cái và khoảng trắng.");
            } else {
                return name;
            }
        }
    }

    private String inputDateOfBirth() {
        while (true) {
            System.out.print("Nhập ngày sinh (dd/MM/yyyy): ");
            String dob = sc.nextLine().trim();
            if (!dob.matches("^\\d{2}/\\d{2}/\\d{4}$")) {
                System.out.println("Định dạng ngày sinh không hợp lệ.");
                continue;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            try {
                sdf.parse(dob);
                return dob;
            } catch (ParseException e) {
                System.out.println("Ngày không hợp lệ.");
            }
        }
    }

    private String inputGender() {
        while (true) {
            System.out.print("Nhập giới tính (Nam/Nữ): ");
            String gender = sc.nextLine().trim();
            if (gender.equalsIgnoreCase("Nam") || gender.equalsIgnoreCase("Nữ")) {
                return gender;
            } else {
                System.out.println("Giới tính chỉ được là Nam hoặc Nữ.");
            }
        }
    }

    private String inputPhoneNumber() {
        while (true) {
            System.out.print("Nhập số điện thoại (10 số bắt đầu bằng 0): ");
            String phone = sc.nextLine().trim();

            if (!phone.matches("^0\\d{9}$")) {
                System.out.println("❌ Số điện thoại không hợp lệ.");
                continue;
            }

            boolean exists = false;
            for (Student s : students) {
                if (s.getPhoneNumber().equals(phone)) {
                    exists = true;
                    break;
                }
            }

            if (exists) {
                System.out.println("❌ Số điện thoại đã tồn tại. Vui lòng nhập số khác.");
            } else {
                return phone;
            }
        }
    }

    private String inputClassId() {
        while (true) {
            System.out.print("Nhập mã lớp: ");
            String idClass = sc.nextLine().trim();
            if (idClass.isEmpty()) {
                System.out.println("Mã lớp không được để trống.");
            } else {
                return idClass;
            }
        }
    }

    private String getClassNameById(String idClass) {
        for (SchoolClass c : classes) {
            if (c.getIdClass().equalsIgnoreCase(idClass)) {
                return c.getClassName();
            }
        }
        return "Không rõ";
    }

    @Override
    public void addStudent() {
        if (classes.isEmpty()) {
            System.out.println(" Chưa có lớp học. Vui lòng thêm lớp trước.");
            return;
        }
        System.out.println("Nhập thông tin học sinh");
        String name = inputName();
        String dob = inputDateOfBirth();
        String gender = inputGender();
        String phone = inputPhoneNumber();
        String idClass = inputClassId();

        boolean classExists = false;
        for (SchoolClass c : classes) {
            if (c.getIdClass().equalsIgnoreCase(idClass)) {
                classExists = true;
                break;
            }
        }

        if (!classExists) {
            System.out.println("Mã lớp không tồn tại. Không thể thêm học sinh.");
            return;
        }

        Student student = new Student(name, dob, gender, phone, idClass);
        students.add(student);
        saveToFile();

        System.out.println("Thêm học sinh thành công.");
    }

    @Override
    public void removeStudent() {
        System.out.print("Nhập ID học sinh cần xóa: ");
        String idToDelete = sc.nextLine().trim();

        Student studentToRemove = null;
        for (Student s : students) {
            if (s.getId().equals(idToDelete)) {
                studentToRemove = s;
                break;
            }
        }

        if (studentToRemove == null) {
            throw new StudentNotFoundException("Không tìm thấy học sinh với ID: " + idToDelete);
        }

        System.out.println("Tìm thấy học sinh: " + studentToRemove.getStudentName());
        System.out.print("Bạn có chắc muốn xóa học sinh này? (yes/no): ");
        String confirm = sc.nextLine().trim();

        if (confirm.equalsIgnoreCase("yes")) {
            students.remove(studentToRemove);
            saveToFile(); //
            System.out.println("Đã xóa học sinh thành công.");
        } else {
            System.out.println("Hủy thao tác xóa.");
        }
    }

    @Override
    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("Danh sách học sinh trống.");
            return;
        }
        System.out.println("Danh sách học sinh:");
        for (Student s : students) {
            String className = getClassNameById(s.getIdClass());
            System.out.printf("ID: %s | Tên: %s | Ngày sinh: %s | Giới tính: %s | SĐT: %s | Lớp: %s%n",
                    s.getId(), s.getStudentName(), s.getDateOfBirth(),
                    s.getGender(), s.getPhoneNumber(), className);
        }
    }

    @Override
    public void saveToFile() {
        File file = new File(FILE_PATH);
        File parent = file.getParentFile();

        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.newLine();

            for (Student s : students) {
                String line = String.join(",",
                        s.getId(),
                        s.getStudentName(),
                        s.getDateOfBirth(),
                        s.getGender(),
                        s.getPhoneNumber(),
                        s.getIdClass()
                );
                writer.write(line);
                writer.newLine();
            }

            System.out.println("Danh sách học sinh đã được lưu vào file: " + FILE_PATH);
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file: " + e.getMessage());
        }
    }

    @Override
    public void readFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String id = parts[0];
                    String name = parts[1];
                    String dob = parts[2];
                    String gender = parts[3];
                    String phone = parts[4];
                    String classId = parts[5];

                    Student student = new Student(name, dob, gender, phone, classId);
                    student.setId(id);

                    students.add(student);
                }
            }
            System.out.println("Đã luu " + students.size() + " học sinh từ file.");
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
        }
    }

    @Override
    public void searchByName(String keyword) {
        keyword = keyword.toLowerCase();
        boolean found = false;

        System.out.println("Kết quả tìm kiếm với từ khóa: " + keyword);
        for (Student s : students) {
            String className = getClassNameById(s.getIdClass());
            String name = s.getStudentName().toLowerCase();
            if (name.contains(keyword)) {
                System.out.printf("ID: %s | Tên: %s | Ngày sinh: %s | Giới tính: %s | SĐT: %s | Lớp: %s%n",
                        s.getId(), s.getStudentName(), s.getDateOfBirth(),
                        s.getGender(), s.getPhoneNumber(), className);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Không tìm thấy sinh viên nào có tên gần giống từ khóa.");
        }
    }


}
