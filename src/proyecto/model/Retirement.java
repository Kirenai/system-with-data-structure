
package proyecto.model;

public class Retirement {

    public Retirement() {}    

    public Retirement(int retirementNumber, int enrollmentNumber, String date, String time) {
        this.retirementNumber = retirementNumber;
        this.enrollmentNumber = enrollmentNumber;
        this.date = date;
        this.time = time;
    }

    public int getRetirementNumber() {
        return retirementNumber;
    }

    public int getEnrollmentNumber() {
        return enrollmentNumber;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public void setRetirementNumber(int retirementNumber) {
        this.retirementNumber = retirementNumber;
    }

    public void setEnrollmentNumber(int enrollmentNumber) {
        this.enrollmentNumber = enrollmentNumber;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Retirement{" + "retirementNumber=" + retirementNumber + 
                ", enrollmentNumber=" + enrollmentNumber + ", date=" + date + 
                ", time=" + time + '}';
    }
    
    private int retirementNumber;  //correlativo (200001)
    private int enrollmentNumber;
    private String date;   //“dd/mm/aaaa”
    private String time;    //hh:mm:ss
}
