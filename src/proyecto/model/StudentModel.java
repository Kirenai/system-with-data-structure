package proyecto.model;

import java.util.ArrayList;
import java.util.List;

public class StudentModel {
    
    public StudentModel() {
        
    }
    
    private static List<Student> studentList = new ArrayList<>();
    
    public static List<Student> getStudentList() {
        return studentList;
    }
}
