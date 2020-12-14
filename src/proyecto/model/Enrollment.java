
package proyecto.model;

public class Enrollment {

    public Enrollment() {}

    public Enrollment(int enrollmentNumber, int studentCode, int courseCode, String date, String time) {
        this.enrollmentNumber = enrollmentNumber;
        this.studentCode = studentCode;
        this.courseCode = courseCode;
        this.date = date;
        this.time = time;
    }

    public int getEnrollmentNumber() {
        return enrollmentNumber;
    }

    public int getStudentCode() {
        return studentCode;
    }

    public int getCourseCode() {
        return courseCode;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public void setEnrollmentNumber(int enrollmentNumber) {
        this.enrollmentNumber = enrollmentNumber;
    }

    public void setStudentCode(int studentCode) {
        this.studentCode = studentCode;
    }

    public void setCourseCode(int courseCode) {
        this.courseCode = courseCode;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Enrollment{" + "enrollmentNumber=" + enrollmentNumber + 
                ", studentCode=" + studentCode + ", courseCode=" + courseCode + 
                ", date=" + date + ", time=" + time + '}';
    }
    
    private int enrollmentNumber;   //correlative 100001++
    private int studentCode;
    private int courseCode;
    private String date;   //dd/mm/aa
    private String time;    //hh:mm:ss
}
