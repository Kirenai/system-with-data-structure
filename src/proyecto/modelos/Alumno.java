/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto.modelos;

/**
 *
 * @author Kire
 */
public class Alumno {

    public Alumno() { }

    public Alumno(int codAlumno, String nombres, String apellidos, String dni, 
                    int edad, int celular, int estado) {
        this.codAlumno = codAlumno;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.dni = dni;
        this.edad = edad;
        this.celular = celular;
        this.estado = estado;
    }

    public int getCodAlumno() {
        return codAlumno;
    }

    public void setCodAlumno(int codAlumno) {
        this.codAlumno = codAlumno;
    }

    public String getNombre() {
        return nombres;
    }

    public void setNombre(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getCelular() {
        return celular;
    }

    public void setCelular(int celular) {
        this.celular = celular;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Alumno{" + "codAlumno=" + codAlumno + ", nombre=" + nombres + 
                ", apellidos=" + apellidos + ", dni=" + dni + ", edad=" + edad +
                ", celular=" + celular + ", estado=" + estado + '}';
    }
    
    private int codAlumno;
    private String nombres;
    private String apellidos;
    private String dni;
    private int edad;
    private int celular;
    private int estado;
    
    public static final int REGISTRADO = 0;
    public static final int MATRICULADO = 1;
    public static final int RETIRADO = 2;
}
