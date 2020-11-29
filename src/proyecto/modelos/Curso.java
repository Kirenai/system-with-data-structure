
package proyecto.modelos;

public class Curso {

    public Curso() {}
    
    public Curso(int codCurso, String asignatura, int ciclo, int creditos, int horas) {
        this.codCurso = codCurso;
        this.asignatura = asignatura;
        this.ciclo = ciclo;
        this.creditos = creditos;
        this.horas = horas;
    }

    public int getCodCurso() {
        return codCurso;
    }

    public String getAsignatura() {
        return asignatura;
    }

    public int getCiclo() {
        return ciclo;
    }

    public int getCreditos() {
        return creditos;
    }

    public int getHoras() {
        return horas;
    }

    public void setCodCurso(int codCurso) {
        this.codCurso = codCurso;
    }

    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }

    public void setCiclo(int ciclo) {
        this.ciclo = ciclo;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }
    
    @Override
    public String toString() {
        return "Curso{" + "codCurso=" + codCurso + ", asignatura=" 
                + asignatura + ", ciclo=" + ciclo + ", creditos=" 
                + creditos + ", horas=" + horas + "}\n";
    }
    
    
    private int codCurso;
    private String asignatura;
    private int ciclo;
    private int creditos;
    private int horas;
    
    public static final int PRIMERO = 0;
    public static final int SEGUNDO = 1;
    public static final int TERCERO = 2;
    public static final int CUARTO = 3;
    public static final int QUINTO = 4;
    public static final int SEXTO = 5;
}
