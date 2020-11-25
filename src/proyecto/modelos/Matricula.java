
package proyecto.modelos;

public class Matricula {

    public Matricula() {}
    
    public Matricula(int numMatricula, int codAlumno, int codCurso, String fecha, String hora) {
        this.numMatricula = numMatricula;
        this.codAlumno = codAlumno;
        this.codCurso = codCurso;
        this.fecha = fecha;
        this.hora = hora;
    }

    public int getNumMatricula() {
        return numMatricula;
    }

    public int getCodAlumno() {
        return codAlumno;
    }

    public int getCodCurso() {
        return codCurso;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }
    
    
    
    private int numMatricula;   //correlativo 100001
    private int codAlumno;
    private int codCurso;
    private String fecha;   //dd/mm/aa
    private String hora;    //hh:mm:ss
}
