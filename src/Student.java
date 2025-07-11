package src;

public class Student {
    private static int nextId = 1;
    private String id;
    private String studentName;
    private String dateOfBirth;
    private String gender;
    private String phoneNumber;
    private String idClass;

    public Student(String studentName, String dateOfBirth, String gender, String phoneNumber, String idClass) {
        this.id = String.valueOf(nextId++);
        this.studentName = studentName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.idClass = idClass;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        try {
            int numericId = Integer.parseInt(id);
            if (numericId >= nextId) {
                nextId = numericId + 1;
            }
        } catch (NumberFormatException ignored) {
        }
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getIdClass() {
        return idClass;
    }

    public void setIdClass(String idClass) {
        this.idClass = idClass;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", studentName='" + studentName + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", gender='" + gender + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", idClass='" + idClass + '\'' +
                '}';
    }
}
