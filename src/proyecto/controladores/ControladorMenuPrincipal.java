
package proyecto.controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import proyecto.vistas.AlumnoVista;
import proyecto.vistas.CursoVista;
import proyecto.vistas.MatriculaVista;
import proyecto.vistas.MenuPrincipal;

public class ControladorMenuPrincipal implements ActionListener {

    public ControladorMenuPrincipal(MenuPrincipal mainMenu) {
        this.mainMenu = mainMenu;
        
        addEvents();
    }
    
    private void addEvents() {
        mainMenu.miAlumno.addActionListener(this);
        mainMenu.miCurso.addActionListener(this);
        mainMenu.miMatricula.addActionListener(this);
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
                    viewAlumno.btnActualizar.setEnabled(false);
                    viewAlumno.btnEliminar.setEnabled(false);
                }
                
            });
        } else if (mainMenu.miCurso == e.getSource()) {
            CursoVista viewCourse = new CursoVista();
            viewCourse.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            viewCourse.setVisible(true);
            viewCourse.setLocationRelativeTo(null);
            viewCourse.setTitle("Curso CRUD");
            
            viewCourse.addWindowListener(new WindowAdapter() {
                @Override
                public void windowOpened(WindowEvent e) {
                    new ControladorVistaCurso(viewCourse).showDataOnTable();
                    viewCourse.btnActualizar.setEnabled(false);
                    viewCourse.btnEliminar.setEnabled(false);
                }
                
            });
        } else if (mainMenu.miMatricula == e.getSource()) {
            MatriculaVista viewRegistration = new MatriculaVista();
            viewRegistration.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            viewRegistration.setVisible(true);
            viewRegistration.setLocationRelativeTo(null);
            viewRegistration.setTitle("Matrícula CRUD");
            
            viewRegistration.addWindowListener(new WindowAdapter() {
                @Override
                public void windowOpened(WindowEvent e) {
                    ControladorVistaMatricula controllerViewRegistrator = new ControladorVistaMatricula(viewRegistration);
                    controllerViewRegistrator.showDataOnTable();
                    controllerViewRegistrator.showDataAlumnoOnJComboBox();
                    viewRegistration.txtMatricula.setEnabled(false);
                }
            });
        }
    }
    
    private final MenuPrincipal mainMenu;
}
