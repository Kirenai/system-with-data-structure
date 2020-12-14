package proyecto.model;

import java.util.ArrayList;
import java.util.List;

public class RetirementModel {
    
    private static List<Retirement> retirementList = new ArrayList<>();
    
    public static List<Retirement> getRetirementList() {
        return retirementList;
    }
}
