
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

    public void setNumRetiro(int numRetiro) {
        this.numRetiro = numRetiro;
    }

    public void setNumMatricula(int numMatricula) {
        this.numMatricula = numMatricula;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        return "Retiro{" + "numRetiro=" + numRetiro + ", numMatricula=" 
                + numMatricula + ", fecha=" + fecha + ", hora=" + hora + '}';
    }
    
    
    
    private int numRetiro;  //correlativo (200001)
    private int numMatricula;
    private String fecha;   //“dd/mm/aaaa”
    private String hora;    //hh:mm:ss
}
