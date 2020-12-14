
package proyecto.model;

public class Course {

    public Course() {}

    public Course(int courseCode, String subject, int cycle, int credits, int hours) {
        this.courseCode = courseCode;
        this.subject = subject;
        this.cycle = cycle;
        this.credits = credits;
        this.hours = hours;
    }
    
    public int getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(int courseCode) {
        this.courseCode = courseCode;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getCycle() {
        return cycle;
    }

    public void setCycle(int cycle) {
        this.cycle = cycle;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    @Override
    public String toString() {
        return "Course{" + "courseCode=" + courseCode + ", subject=" + subject + 
                ", cycle=" + cycle + ", credits=" + credits + ", hours=" + hours + '}';
    }
    
    private int courseCode;
    private String subject;
    private int cycle;
    private int credits;
    private int hours;
    
    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOURTH = 3;
    public static final int FIFTH = 4;
    public static final int SIXTH = 5;
}
