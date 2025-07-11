import Interface.IClass;
import Interface.IStudent;
import Interface.ITeacher;
import exception.StudentNotFoundException;
import src.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        List<Teacher> teachers = new ArrayList<>();
        List<SchoolClass> classes = new ArrayList<>();

        ITeacher teacherService = new TeacherManager(teachers);
        IClass classService = new ClassManage(classes);
        IStudent studentService = new StudentManager(classes);

        while (true) {
            System.out.println("\n========== MENU QUẢN LÝ HỌC SINH ==========");
            System.out.println("1. Thêm học sinh");
            System.out.println("2. Hiển thị danh sách học sinh");
            System.out.println("3. Xóa học sinh theo ID");
            System.out.println("4. Tìm kiếm học sinh theo tên");
            System.out.println("5. Xem thông tin giáo viên theo mã");
            System.out.println("6. Thêm giáo viên");
            System.out.println("7. Thêm lớp học");
            System.out.println("0. Thoát");
            System.out.print("Nhập lựa chọn: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    studentService.addStudent();
                    break;
                case "2":
                    studentService.displayAllStudents();
                    break;
                case "3":
                    try {
                        studentService.removeStudent();
                    } catch (StudentNotFoundException e) {
                        System.out.println("Error" + e.getMessage());
                    }
                    break;
                case "4":
                    System.out.print("Nhập từ khóa tên sinh viên: ");
                    String keyword = sc.nextLine();
                    studentService.searchByName(keyword);
                    break;
                case "5":
                    System.out.print("Nhập mã giáo viên: ");
                    String idTeacher = sc.nextLine();
                    teacherService.displayTeacherById(idTeacher);
                    break;
                case "6":
                    teacherService.addTeacher();
                    break;
                case "7":
                    classService.addClass(teachers);
                    break;
                case "0":
                    System.out.println("Thoát chương trình. Hẹn gặp lại!");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn từ 0 đến 7.");
            }
        }
    }
}
