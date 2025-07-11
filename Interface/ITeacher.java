package Interface;

import src.Teacher;

public interface ITeacher {
    void displayTeacherById(String idTeacher);
    void addTeacher();
    void saveToFile();
    void readFromFile();
}
