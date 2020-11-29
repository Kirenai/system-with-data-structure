package proyecto.controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import proyecto.modelos.Alumno;
import proyecto.modelos.Curso;
import proyecto.modelos.Matricula;
import proyecto.modelos.Retiro;
import proyecto.vistas.AlumnoVista;
import proyecto.vistas.ConsultasVista;
import proyecto.vistas.CursoVista;
import proyecto.vistas.MatriculaVista;
import proyecto.vistas.MenuPrincipal;
import proyecto.vistas.RetiroVista;

public class ControladorMenuPrincipal implements ActionListener {

    public ControladorMenuPrincipal(MenuPrincipal mainMenu) {
        this.mainMenu = mainMenu;

        viewQuery = new ConsultasVista();

        addEvents();
    }

    private void addEvents() {
        mainMenu.miAlumno.addActionListener(this);
        mainMenu.miCurso.addActionListener(this);
        mainMenu.miMatricula.addActionListener(this);
        mainMenu.miRetiro.addActionListener(this);

        mainMenu.btnAlumnos.addActionListener(this);
        mainMenu.btnCursos.addActionListener(this);
        mainMenu.btnMatriculas.addActionListener(this);
        mainMenu.btnRetiro.addActionListener(this);
        
        mainMenu.btnReporteAlumno.addActionListener(this);
        mainMenu.btnReporteCurso.addActionListener(this);
        mainMenu.btnReporteMatriculas.addActionListener(this);
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
                    ControladorVistaMatricula controllerViewRegistrator
                            = new ControladorVistaMatricula(viewRegistration);
                    controllerViewRegistrator.showDataOnTable();

                    viewRegistration.txtMatricula.setEnabled(false);
                    viewRegistration.btnActualizar.setEnabled(false);
                    viewRegistration.btnEliminar.setEnabled(false);
                }
            });
        } else if (mainMenu.miRetiro == e.getSource()) {
            RetiroVista viewRetirement = new RetiroVista();
            viewRetirement.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            viewRetirement.setVisible(true);
            viewRetirement.setLocationRelativeTo(null);
            viewRetirement.setTitle("Retiro CRUD");

            viewRetirement.addWindowListener(new WindowAdapter() {
                @Override
                public void windowOpened(WindowEvent e) {
                    ControladorVistaRetiro controllerViewRetirement
                            = new ControladorVistaRetiro(viewRetirement);
                    controllerViewRetirement.showDataOnTable();

                    viewRetirement.txtNumRetiro.setEnabled(false);

                    viewRetirement.btnEliminar.setEnabled(false);
                }
            });
        } else if (mainMenu.btnAlumnos == e.getSource()) {
            queryStudents(viewQuery);
        } else if (mainMenu.btnCursos == e.getSource()) {
            queryCourses(viewQuery);
        } else if (mainMenu.btnMatriculas == e.getSource()) {
            queryEnrollment(viewQuery);
        } else if (mainMenu.btnRetiro == e.getSource()) {
            queryRetirement(viewQuery);
        } else if (mainMenu.btnReporteAlumno == e.getSource()) {
            reportStudents(viewQuery);
        } else if (mainMenu.btnReporteMatriculas == e.getSource()) {
            reportStudentsEnrolled(viewQuery);
        } else if (mainMenu.btnReporteCurso == e.getSource()) {
            reportEnrollments(viewQuery);
        }
    }

    private void queryStudents(ConsultasVista viewQuery) {
        int queryStudent = Integer.parseInt(mainMenu.txtAlumnos.getText());

        List<Alumno> listStudents = ControladorVistaAlumno.getListAlumno();
        List<Matricula> listEnrollment = ControladorVistaMatricula.getListRegistration();
        List<Curso> listCourses = ControladorVistaCurso.getListCourse();

        Alumno student;
        try {
            student = getStudent(listStudents, queryStudent);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(mainMenu, ex.getMessage());
            return;
        }

        viewQuery.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        viewQuery.setVisible(true);
        viewQuery.setLocationRelativeTo(null);
        viewQuery.setTitle("Consultas Alumnos");

        viewQuery.taConsulta.setText("");

        if (studentItIsRegistered(student, listEnrollment)) {
            Curso course = getCourseOfStudent(student, listEnrollment, listCourses);

            viewQuery.taConsulta.append(String.format("Código del Alumno: %s\n"
                    + "Nombres: %s\nApellidos: %s\nDNI: %s\nEdad: %s\n"
                    + "Celular %s\nEstado: %s\n\nCurso:\nCódigo Curso: %s\n"
                    + "Asignatura: %s\nCiclo: %s\nCréditos: %s\nHoras: %s",
                    student.getCodAlumno(), student.getNombre(), student.getApellidos(),
                    student.getDni(), student.getEdad(), student.getCelular(),
                    student.getEstado(), course.getCodCurso(), course.getAsignatura(),
                    course.getCiclo(), course.getCreditos(), course.getHoras()));
        } else {
            viewQuery.taConsulta.append(String.format("Código del Alumno: %s\n"
                    + "Nombres: %s\nApellidos: %s\nDNI: %s\nEdad: %s\n"
                    + "Celular %s\nEstado: %s", student.getCodAlumno(),
                    student.getNombre(), student.getApellidos(), student.getDni(),
                    student.getEdad(), student.getCelular(), student.getEstado()));
        }

    }

    private void queryCourses(ConsultasVista viewQuery) {
        int queryStudent = Integer.parseInt(mainMenu.txtCursos.getText());

        List<Curso> listCourses = ControladorVistaCurso.getListCourse();

        Curso course;
        try {
            course = getCourse(listCourses, queryStudent);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(mainMenu, ex.getMessage());
            return;
        }

        viewQuery.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        viewQuery.setVisible(true);
        viewQuery.setLocationRelativeTo(null);
        viewQuery.setTitle("Consultas Cursos");

        viewQuery.taConsulta.setText("");

        viewQuery.taConsulta.append(String.format("Curso: \n\nCódigo del Curso: %s\n"
                + "Asignatura: %s\nCiclo: %s\nCréditos: %s\nHoras: %s\n",
                course.getCodCurso(), course.getAsignatura(), course.getCiclo(),
                course.getCreditos(), course.getHoras()));
    }

    private void queryEnrollment(ConsultasVista viewQuery) {
        int queryEnrollment = Integer.parseInt(mainMenu.txtMatriculas.getText());
        
        List<Alumno> listStudents = ControladorVistaAlumno.getListAlumno();
        List<Matricula> listEnrollment = ControladorVistaMatricula.getListRegistration();
        List<Curso> listCourses = ControladorVistaCurso.getListCourse();
        
        Matricula enrollment;
        Alumno student;
        Curso course;
        try {
            enrollment = getEnrollment(listEnrollment, queryEnrollment);
            student = getStudent(listStudents, enrollment.getCodAlumno());
            course = getCourse(listCourses, enrollment.getCodCurso());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(mainMenu, ex.getMessage());
            return;
        }
        
        viewQuery.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        viewQuery.setVisible(true);
        viewQuery.setLocationRelativeTo(null);
        viewQuery.setTitle("Consultas Matrículas");
        
        viewQuery.taConsulta.setText("");
        
        viewQuery.taConsulta.append(String.format("Código del Alumno: %s\n"
            + "Nombres: %s\nApellidos: %s\nDNI: %s\nEdad: %s\n"
            + "Celular %s\nEstado: %s\n\nCurso:\nCódigo Curso: %s\n"
            + "Asignatura: %s\nCiclo: %s\nCréditos: %s\nHoras: %s",
            student.getCodAlumno(), student.getNombre(), student.getApellidos(),
            student.getDni(), student.getEdad(), student.getCelular(),
            student.getEstado(), course.getCodCurso(), course.getAsignatura(),
            course.getCiclo(), course.getCreditos(), course.getHoras()));
        
    }

    private void queryRetirement(ConsultasVista viewQuery) {
        int queryRetirement = Integer.parseInt(mainMenu.txtRetiros.getText());

        List<Alumno> listStudents = ControladorVistaAlumno.getListAlumno();
        List<Matricula> listEnrollment = ControladorVistaMatricula.getListRegistration();
        List<Curso> listCourses = ControladorVistaCurso.getListCourse();
        List<Retiro> listRetirement = ControladorVistaRetiro.getListRetirement();
        
        Matricula enrollment;
        Alumno student;
        Curso course;
        Retiro retirement;
        try {
            retirement = getRetirement(listRetirement, queryRetirement);
            enrollment = getEnrollment(listEnrollment, retirement.getNumMatricula());
            student = getStudent(listStudents, enrollment.getCodAlumno());
            course = getCourse(listCourses, enrollment.getCodCurso());
            System.out.println(retirement);
            System.out.println(enrollment);
            System.out.println(course);
            System.out.println(student);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(mainMenu, ex.getMessage());
            return;
        }
        
        viewQuery.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        viewQuery.setVisible(true);
        viewQuery.setLocationRelativeTo(null);
        viewQuery.setTitle("Consultas Retiros");
        
        viewQuery.taConsulta.setText("");
        
        viewQuery.taConsulta.append(String.format("Código del Alumno: %s\n"
            + "Nombres: %s\nApellidos: %s\nDNI: %s\nEdad: %s\n"
            + "Celular %s\nEstado: %s\n\nCurso:\nCódigo Curso: %s\n"
            + "Asignatura: %s\nCiclo: %s\nCréditos: %s\nHoras: %s",
            student.getCodAlumno(), student.getNombre(), student.getApellidos(),
            student.getDni(), student.getEdad(), student.getCelular(),
            student.getEstado(), course.getCodCurso(), course.getAsignatura(),
            course.getCiclo(), course.getCreditos(), course.getHoras()));
    }
    
    private void reportStudents(ConsultasVista viewQuery) {
        List<Alumno> listStudents = ControladorVistaAlumno.getListAlumno();
        List<Alumno> listOfStudentsRegistered = listStudents
                .stream()
                .filter(student -> student.getEstado() == 0)
                .collect(Collectors.toList());
        
        viewQuery.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        viewQuery.setVisible(true);
        viewQuery.setLocationRelativeTo(null);
        viewQuery.setTitle("Reporte Alumnos Registrados");
        viewQuery.taConsulta.setText("");
        viewQuery.taConsulta.append(String.format("Alumnos con matrícula vigente\n\n"));
        listOfStudentsRegistered.forEach((student) -> {
            viewQuery.taConsulta.append(String.format(
                    "Código del Alumno: %s\nNombres: %s\nApellidos: %s\n"
                    + "DNI: %s\nEdad: %s\nCelular %s\nEstado: %s\n\n", 
                    student.getCodAlumno(),student.getNombre(), 
                    student.getApellidos(), student.getDni(),
                    student.getEdad(), student.getCelular(), 
                    student.getEstado())
            );
        });
    }

    private void reportStudentsEnrolled(ConsultasVista viewQuery) {
        List<Alumno> listStudents = ControladorVistaAlumno.getListAlumno();
        List<Alumno> listOfStudentsEnrolled = listStudents
                .stream()
                .filter(student -> student.getEstado() == 1)
                .collect(Collectors.toList());
        
        viewQuery.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        viewQuery.setVisible(true);
        viewQuery.setLocationRelativeTo(null);
        viewQuery.setTitle("Reporte Alumnos Matrículados");
        viewQuery.taConsulta.setText("");
        viewQuery.taConsulta.append(String.format("Alumnos con matrícula pendiente\n\n"));
        listOfStudentsEnrolled.forEach((student) -> {
            viewQuery.taConsulta.append(String.format(
                    "Código del Alumno: %s\nNombres: %s\nApellidos: %s\n"
                    + "DNI: %s\nEdad: %s\nCelular %s\nEstado: %s\n\n", 
                    student.getCodAlumno(),student.getNombre(), 
                    student.getApellidos(), student.getDni(),
                    student.getEdad(), student.getCelular(), 
                    student.getEstado())
            );
        });
    }

    private void reportEnrollments(ConsultasVista viewQuery) {
        List<Matricula> listRegistration  = ControladorVistaMatricula.getListRegistration();
        List<Alumno> listStudents = ControladorVistaAlumno.getListAlumno();
        
        viewQuery.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        viewQuery.setVisible(true);
        viewQuery.setLocationRelativeTo(null);
        viewQuery.setTitle("Reporte Alumnos Matrículados");
        viewQuery.taConsulta.setText("");
        viewQuery.taConsulta.append(String.format("Alumnos matriculados por curso\n\n"));
        listRegistration.forEach((Matricula register) -> {
            try {
                Alumno student = getStudent(listStudents, register.getCodAlumno());
                if (student.getEstado() == 1) {
                    viewQuery.taConsulta.append(String.format(
                        "Nombres del Alumno Matrículado en un Curso: %s\n", 
                        student.getNombre())
                    );
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        });
    }

    private Alumno getStudent(List<Alumno> listStudents, int queryStudent) throws Exception {
        for (Alumno student : listStudents) {
            if (student.getCodAlumno() == queryStudent) {
                return student;
            }
        }
        throw new Exception("No existe el Estudiante");
    }
    
    private Curso getCourse(List<Curso> listCourses, int queryStudent) 
            throws Exception {
        for (Curso course : listCourses) {
            if (course.getCodCurso() == queryStudent) {
                return course;
            }
        }
        throw new Exception("No existe el Curso");
    }
    
    private Matricula getEnrollment(List<Matricula> listEnrollments, 
            int queryEnrollment) throws Exception {
        for (Matricula register : listEnrollments) {
            if (register.getNumMatricula() == queryEnrollment) {
                return register;
            }
        }
        throw new Exception("No existe la Matrícula");
    }
    
    private Retiro getRetirement(List<Retiro> listRetirements, 
            int queryRetirement) throws Exception {
        for (Retiro retirement : listRetirements) {
            if (retirement.getNumRetiro() == queryRetirement) {
                return retirement;
            }
        }
        throw new Exception("No existe el Retiro");
    }

    private Curso getCourseOfStudent(Alumno student,
            List<Matricula> listEnrollments,
            List<Curso> listCourses) {
        for (Matricula registered : listEnrollments) {
            if (student.getCodAlumno() == registered.getCodAlumno()) {
                for (Curso course : listCourses) {
                    if (registered.getCodCurso() == course.getCodCurso()) {
                        return course;
                    }
                }
            }
        }
        return null;
    }

    private boolean studentItIsRegistered(Alumno student,
            List<Matricula> listEnrollment) {
        return listEnrollment
                .stream()
                .anyMatch((registered) -> (registered.getCodAlumno() == student.getCodAlumno()));
    }

    private final MenuPrincipal mainMenu;
    private final ConsultasVista viewQuery;
}
