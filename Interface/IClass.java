package Interface;

import src.Teacher;

import java.util.List;

public interface IClass {
    void addClass(List<Teacher> teachers);
    void saveToFile();
    void readFromFile();
}
