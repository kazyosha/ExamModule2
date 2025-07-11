package src;

public class SchoolClass {
    private String idClass;
    private String className;
    private String idTeacher; // Chỉ

    public SchoolClass(String idClass, String className, String idTeacher) {
        this.idClass = idClass;
        this.className = className;
        this.idTeacher = idTeacher;
    }

    public String getIdClass() {
        return idClass;
    }

    public void setIdClass(String idClass) {
        this.idClass = idClass;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getIdTeacher() {
        return idTeacher;
    }

    public void setIdTeacher(String idTeacher) {
        this.idTeacher = idTeacher;
    }
}
