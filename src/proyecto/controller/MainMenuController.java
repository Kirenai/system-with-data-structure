package proyecto.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import proyecto.model.Student;
import proyecto.model.Course;
import proyecto.model.CourseModel;
import proyecto.model.Enrollment;
import proyecto.model.EnrollmentModel;
import proyecto.model.Retirement;
import proyecto.model.RetirementModel;
import proyecto.model.StudentModel;
import proyecto.view.StudentView;
import proyecto.view.QueryView;
import proyecto.view.CourseView;
import proyecto.view.EnrollmentView;
import proyecto.view.MainMenuView;
import proyecto.view.RetirementView;

public class MainMenuController implements ActionListener {

    public MainMenuController(MainMenuView mainMenuView) {
        this.mainMenuView = mainMenuView;

        queryView = new QueryView();

        addListenners();
    }

    private void addListenners() {
        mainMenuView.miStudent.addActionListener(this);
        mainMenuView.miCourse.addActionListener(this);
        mainMenuView.miEnrollment.addActionListener(this);
        mainMenuView.miRetirement.addActionListener(this);

        mainMenuView.btnQueryStudents.addActionListener(this);
        mainMenuView.btnQueryCourses.addActionListener(this);
        mainMenuView.btnQueryEnrollment.addActionListener(this);
        mainMenuView.btnQueryRetirement.addActionListener(this);
        
        mainMenuView.btnReportPendingEnrollment.addActionListener(this);
        mainMenuView.btnReportEnrollmentCourse.addActionListener(this);
        mainMenuView.btnReportCurrentEnrollment.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (mainMenuView.miStudent == e.getSource()) {
            StudentView studentView = new StudentView(); //objeto
            studentView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            studentView.setVisible(true);
            studentView.setLocationRelativeTo(null);
            studentView.setTitle("Alumno CRUD");

            studentView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowOpened(WindowEvent e) {
                    StudentController studentController = 
                            new StudentController(studentView);
                    studentController.clearListStudents();
                    studentController.loadStudents();
                    studentController.showDataOnTable();

                    studentView.btnUpdate.setEnabled(false);
                    studentView.btnDelete.setEnabled(false);
                }
            });
        } else if (mainMenuView.miCourse == e.getSource()) {
            CourseView courseView = new CourseView();
            courseView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            courseView.setVisible(true);
            courseView.setLocationRelativeTo(null);
            courseView.setTitle("Curso CRUD");

            courseView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowOpened(WindowEvent e) {
                    CourseController courseController = 
                            new CourseController(courseView);
                    courseController.clearListStudents();
                    courseController.loadCourses();
                    courseController.showDataOnTable();
                    
                    courseView.btnUpdate.setEnabled(false);
                    courseView.btnDelete.setEnabled(false);
                }

            });
        } else if (mainMenuView.miEnrollment == e.getSource()) {
            EnrollmentView enrollmentView = new EnrollmentView();
            enrollmentView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            enrollmentView.setVisible(true);
            enrollmentView.setLocationRelativeTo(null);
            enrollmentView.setTitle("Matrícula CRUD");

            enrollmentView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowOpened(WindowEvent e) {
                    EnrollmentController enrollmentController
                            = new EnrollmentController(enrollmentView);
                    enrollmentController.showDataOnTable();

                    enrollmentView.txtEnrollmentNumber.setEnabled(false);
                    enrollmentView.btnUpdate.setEnabled(false);
                    enrollmentView.btnDelete.setEnabled(false);
                }
            });
        } else if (mainMenuView.miRetirement == e.getSource()) {
            RetirementView retirementView = new RetirementView();
            retirementView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            retirementView.setVisible(true);
            retirementView.setLocationRelativeTo(null);
            retirementView.setTitle("Retiro CRUD");

            retirementView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowOpened(WindowEvent e) {
                    RetirementController retirementController
                            = new RetirementController(retirementView);
                    retirementController.showDataOnTable();

                    retirementView.txtRetirementNumber.setEnabled(false);

                    retirementView.btnDelete.setEnabled(false);
                }
            });
        } else if (mainMenuView.btnQueryStudents == e.getSource()) {
            queryStudents(queryView);
        } else if (mainMenuView.btnQueryCourses == e.getSource()) {
            queryCourses(queryView);
        } else if (mainMenuView.btnQueryEnrollment == e.getSource()) {
            queryEnrollment(queryView);
        } else if (mainMenuView.btnQueryRetirement == e.getSource()) {
            queryRetirement(queryView);
        } else if (mainMenuView.btnReportPendingEnrollment == e.getSource()) {
            reportPendingEnrollment(queryView);
        } else if (mainMenuView.btnReportCurrentEnrollment == e.getSource()) {
            reportCurrentEnrolled(queryView);
        } else if (mainMenuView.btnReportEnrollmentCourse == e.getSource()) {
            reportEnrollmentCourses(queryView);
        }
    }

    private void queryStudents(QueryView queryView) {
        int queryStudent = Integer.parseInt(mainMenuView.txtQueryStudent.getText());

        List<Student> studentList = StudentModel.getStudentList();
        List<Enrollment> enrollmentList = EnrollmentModel.getEnrollmentList();
        List<Course> courseList = CourseModel.getCourseList();

        Student student;
        try {
            student = getStudent(studentList, queryStudent);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(mainMenuView, ex.getMessage());
            return;
        }

        queryView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        queryView.setVisible(true);
        queryView.setLocationRelativeTo(null);
        queryView.setTitle("Consultas Alumnos");

        queryView.taQuery.setText("");

        if (studentItIsRegistered(student, enrollmentList)) {
            Course course = getCourseOfStudent(student, enrollmentList, courseList);

            queryView.taQuery.append(String.format("Código del Alumno: %s\n"
                    + "Nombres: %s\nApellidos: %s\nDNI: %s\nEdad: %s\n"
                    + "Celular %s\nEstado: %s\n\nCurso:\nCódigo Curso: %s\n"
                    + "Asignatura: %s\nCiclo: %s\nCréditos: %s\nHoras: %s",
                    student.getStudentCode(), student.getName(), student.getLastName(),
                    student.getDni(), student.getAge(), student.getCellular(),
                    student.getState(), course.getCourseCode(), course.getSubject(),
                    course.getCycle(), course.getCredits(), course.getHours()));
        } else {
            queryView.taQuery.append(String.format("Código del Alumno: %s\n"
                    + "Nombres: %s\nApellidos: %s\nDNI: %s\nEdad: %s\n"
                    + "Celular %s\nEstado: %s", student.getStudentCode(), 
                    student.getName(), student.getLastName(),student.getDni(), 
                    student.getAge(), student.getCellular(),student.getState()));
        }

    }

    private void queryCourses(QueryView queryView) {
        int queryCourse = Integer.parseInt(mainMenuView.txtQueryCourse.getText());

        List<Course> courseList = CourseModel.getCourseList();

        Course course;
        try {
            course = getCourse(courseList, queryCourse);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(mainMenuView, ex.getMessage());
            return;
        }

        queryView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        queryView.setVisible(true);
        queryView.setLocationRelativeTo(null);
        queryView.setTitle("Consultas Cursos");

        queryView.taQuery.setText("");

        queryView.taQuery.append(String.format("Curso: \n\nCódigo del Curso: %s\n"
                + "Asignatura: %s\nCiclo: %s\nCréditos: %s\nHoras: %s\n",
                course.getCourseCode(), course.getSubject(), course.getCycle(),
                course.getCredits(), course.getHours()));
    }

    private void queryEnrollment(QueryView queryView) {
        int queryEnrollment = Integer.parseInt(mainMenuView.txtQueryEnrollment.getText());
        
        List<Student> studentList = StudentModel.getStudentList();
        List<Enrollment> enrollmentList = EnrollmentModel.getEnrollmentList();
        List<Course> courseList = CourseModel.getCourseList();
        
        Enrollment enrollment;
        Student student;
        Course course;
        try {
            enrollment = getEnrollment(enrollmentList, queryEnrollment);
            student = getStudent(studentList, enrollment.getStudentCode());
            course = getCourse(courseList, enrollment.getCourseCode());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(mainMenuView, ex.getMessage());
            return;
        }
        
        queryView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        queryView.setVisible(true);
        queryView.setLocationRelativeTo(null);
        queryView.setTitle("Consultas Matrículas");
        
        queryView.taQuery.setText("");
        
        queryView.taQuery.append(String.format("Código del Alumno: %s\n"
            + "Nombres: %s\nApellidos: %s\nDNI: %s\nEdad: %s\n"
            + "Celular %s\nEstado: %s\n\nCurso:\nCódigo Curso: %s\n"
            + "Asignatura: %s\nCiclo: %s\nCréditos: %s\nHoras: %s",
            student.getStudentCode(), student.getName(), student.getLastName(),
            student.getDni(), student.getAge(), student.getCellular(),
            student.getState(), course.getCourseCode(), course.getSubject(), course.getCycle(),
                course.getCredits(), course.getHours()));
        
    }

    private void queryRetirement(QueryView queryView) {
        int queryRetirement = Integer.parseInt(mainMenuView.txtQueryRetirement.getText());

        List<Student> studentList = StudentModel.getStudentList();
        List<Enrollment> enrollmentList = EnrollmentModel.getEnrollmentList();
        List<Course> courseList = CourseModel.getCourseList();
        List<Retirement> retirementList = RetirementModel.getRetirementList();
        
        Enrollment enrollment;
        Student student;
        Course course;
        Retirement retirement;
        try {
            retirement = getRetirement(retirementList, queryRetirement);
            enrollment = getEnrollment(enrollmentList, retirement.getEnrollmentNumber());
            student = getStudent(studentList, enrollment.getStudentCode());
            course = getCourse(courseList, enrollment.getCourseCode());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(mainMenuView, ex.getMessage());
            return;
        }
        
        queryView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        queryView.setVisible(true);
        queryView.setLocationRelativeTo(null);
        queryView.setTitle("Consultas Retiros");
        
        queryView.taQuery.setText("");
        
        queryView.taQuery.append(String.format("Código del Alumno: %s\n"
            + "Nombres: %s\nApellidos: %s\nDNI: %s\nEdad: %s\n"
            + "Celular %s\nEstado: %s\n\nCurso:\nCódigo Curso: %s\n"
            + "Asignatura: %s\nCiclo: %s\nCréditos: %s\nHoras: %s",
            student.getStudentCode(), student.getName(), student.getLastName(),
            student.getDni(), student.getAge(), student.getCellular(),
            student.getState(), course.getCourseCode(), course.getSubject(), course.getCycle(),
                course.getCredits(), course.getHours()));
    }
    
    private void reportPendingEnrollment(QueryView queryView) {
        List<Student> studentList = StudentModel.getStudentList();
        List<Student> listOfStudentsRegistered = studentList
                .stream()
                .filter(student -> student.getState()== 0)
                .collect(Collectors.toList());
        
        queryView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        queryView.setVisible(true);
        queryView.setLocationRelativeTo(null);
        queryView.setTitle("Reporte Alumnos Registrados");
        
        queryView.taQuery.setText("");
        queryView.taQuery.append(String.format("Alumnos con matrícula pendiente\n\n"));
        listOfStudentsRegistered.forEach((student) -> {
            queryView.taQuery.append(String.format(
                    "Código del Alumno: %s\nNombres: %s\nApellidos: %s\n"
                    + "DNI: %s\nEdad: %s\nCelular %s\nEstado: %s\n\n", 
                    student.getStudentCode(),student.getName(), 
                    student.getLastName(), student.getDni(),
                    student.getAge(), student.getCellular(), 
                    student.getState())
            );
        });
    }

    private void reportCurrentEnrolled(QueryView queryView) {
        List<Student> studentList = StudentModel.getStudentList();
        List<Student> listOfStudentsEnrolled = studentList
                .stream()
                .filter(student -> student.getState() == 1)
                .collect(Collectors.toList());
        
        queryView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        queryView.setVisible(true);
        queryView.setLocationRelativeTo(null);
        queryView.setTitle("Reporte Alumnos Matrículados");
        
        queryView.taQuery.setText("");
        queryView.taQuery.append(String.format("Alumnos con matrícula vigente\n\n"));
        listOfStudentsEnrolled.forEach((student) -> {
            queryView.taQuery.append(String.format(
                    "Código del Alumno: %s\nNombres: %s\nApellidos: %s\n"
                    + "DNI: %s\nEdad: %s\nCelular %s\nEstado: %s\n\n", 
                    student.getStudentCode(),student.getName(), 
                    student.getLastName(), student.getDni(),
                    student.getAge(), student.getCellular(), 
                    student.getState())
            );
        });
    }

    private void reportEnrollmentCourses(QueryView queryView) {
        List<Enrollment> enrollmentList  = EnrollmentModel.getEnrollmentList();
        List<Student> studentList = StudentModel.getStudentList();
        
        queryView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        queryView.setVisible(true);
        queryView.setLocationRelativeTo(null);
        queryView.setTitle("Reporte Alumnos Matrículados");
        
        queryView.taQuery.setText("");
        queryView.taQuery.append(String.format("Alumnos matriculados por curso\n\n"));
        enrollmentList.forEach((Enrollment register) -> {
            try {
                Student student = getStudent(studentList, register.getStudentCode());
                if (student.getState() == 1) {
                    queryView.taQuery.append(String.format(
                        "Nombres del Alumno Matrículado en un Curso: %s\n", 
                        student.getName())
                    );
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        });
    }

    private Student getStudent(List<Student> studentList, int queryStudent) throws Exception {
        for (Student student : studentList) {
            if (student.getStudentCode()== queryStudent) {
                return student;
            }
        }
        throw new Exception("No existe el Estudiante");
    }
    
    private Course getCourse(List<Course> courseList, int queryStudent) 
            throws Exception {
        for (Course course : courseList) {
            if (course.getCourseCode()== queryStudent) {
                return course;
            }
        }
        throw new Exception("No existe el Curso");
    }
    
    private Enrollment getEnrollment(List<Enrollment> enrollmentList, 
            int queryEnrollment) throws Exception {
        for (Enrollment register : enrollmentList) {
            if (register.getEnrollmentNumber()== queryEnrollment) {
                return register;
            }
        }
        throw new Exception("No existe la Matrícula");
    }
    
    private Retirement getRetirement(List<Retirement> retirementList, 
            int queryRetirement) throws Exception {
        for (Retirement retirement : retirementList) {
            if (retirement.getRetirementNumber()== queryRetirement) {
                return retirement;
            }
        }
        throw new Exception("No existe el Retiro");
    }

    private Course getCourseOfStudent(Student student,
            List<Enrollment> enrollmentList,
            List<Course> courseList) {
        for (Enrollment registered : enrollmentList) {
            if (student.getStudentCode()== registered.getStudentCode()) {
                for (Course course : courseList) {
                    if (registered.getCourseCode()== course.getCourseCode()) {
                        return course;
                    }
                }
            }
        }
        return null;
    }

    private boolean studentItIsRegistered(Student student,
            List<Enrollment> enrollmentList) {
        return enrollmentList
                .stream()
                .anyMatch((registered) -> (registered.getStudentCode()== student.getStudentCode()));
    }
    
    public void loadStudents() {
        String list[];
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader("alumnos.txt"));
            while ((line = br.readLine()) != null) {
                list = line.split(";");
                Student student = new Student(Integer.parseInt(list[0]), list[1], 
                        list[2], list[3], Integer.parseInt(list[4]), 
                        Integer.parseInt(list[5]), Integer.parseInt(list[6])
                );
                StudentModel.getStudentList().add(student);
            }
            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error E/S " + e.getMessage());
        } catch (IOException ex) {
            System.out.println("Error E/S " + ex.getMessage());
        }
        
    }

    private final MainMenuView mainMenuView;
    private final QueryView queryView;
}
