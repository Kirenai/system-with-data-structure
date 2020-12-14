/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto.model;

/**
 *
 * @author Kire
 */
public class Student {

    public Student() { }

    public Student(int studentCode, String name, String lastName, String dni, int age, int cellular, int state) {
        this.studentCode = studentCode;
        this.name = name;
        this.lastName = lastName;
        this.dni = dni;
        this.age = age;
        this.cellular = cellular;
        this.state = state;
    }

    public int getStudentCode() {
        return studentCode;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDni() {
        return dni;
    }

    public int getAge() {
        return age;
    }

    public int getCellular() {
        return cellular;
    }

    public int getState() {
        return state;
    }

    public void setStudentCode(int studentCode) {
        this.studentCode = studentCode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setCellular(int cellular) {
        this.cellular = cellular;
    }

    public void setState(int state) {
        this.state = state;
    }
    
    private int studentCode;
    private String name;
    private String lastName;
    private String dni;
    private int age;
    private int cellular;
    private int state;
    
    public static final int REGISTERED = 0;
    public static final int MATRICULATE = 1;
    public static final int RETIRED = 2;
}
