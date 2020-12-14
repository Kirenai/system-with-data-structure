package proyecto.model;

import java.util.ArrayList;
import java.util.List;

public class CourseModel {
    
    private static List<Course> courseList = new ArrayList<>();
    
    public static List<Course> getCourseList() {
        return courseList;
    }
    
}
