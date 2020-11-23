
package proyecto.controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import proyecto.vistas.AlumnoVista;
import proyecto.vistas.MenuPrincipal;

public class ControladorMenuPrincipal implements ActionListener {

    public ControladorMenuPrincipal(MenuPrincipal mainMenu) {
        this.mainMenu = mainMenu;
        
        addEvents();
    }
    
    private void addEvents() {
        mainMenu.miAlumno.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (mainMenu.miAlumno == e.getSource()) {
            AlumnoVista viewAlumno = new AlumnoVista();
            viewAlumno.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            viewAlumno.setVisible(true);
            viewAlumno.setLocationRelativeTo(null);
            viewAlumno.setTitle("Alumno CRUD");
            
            viewAlumno.addWindowListener(new WindowAdapter() {
                @Override
                public void windowOpened(WindowEvent e) {
                    new ControladorVistaAlumno(viewAlumno).showDataOnTable();
                }
                
            });
        }
    }
    
    private final MenuPrincipal mainMenu;
}
