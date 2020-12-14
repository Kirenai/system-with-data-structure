package proyecto.model;

import java.util.ArrayList;
import java.util.List;

public class EnrollmentModel {
    
    private static List<Enrollment> enrollmentList = new ArrayList<>();
    
    public static List<Enrollment> getEnrollmentList() {
        return enrollmentList;
    }
}
