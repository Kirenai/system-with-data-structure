
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
}
