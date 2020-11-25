
package proyecto.modelos;

public class Retiro {

    public Retiro() {}

    public Retiro(int numRetiro, int numMatricula, String fecha, String hora) {
        this.numRetiro = numRetiro;
        this.numMatricula = numMatricula;
        this.fecha = fecha;
        this.hora = hora;
    }

    public int getNumRetiro() {
        return numRetiro;
    }

    public int getNumMatricula() {
        return numMatricula;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }
    
    private int numRetiro;  //correlativo (200001)
    private int numMatricula;
    private String fecha;   //“dd/mm/aaaa”
    private String hora;    //hh:mm:ss
}
