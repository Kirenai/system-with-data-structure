package proyecto.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import proyecto.model.Student;
import proyecto.model.Course;
import proyecto.model.CourseModel;
import proyecto.model.Enrollment;
import proyecto.model.EnrollmentModel;
import proyecto.model.StudentModel;
import proyecto.view.EnrollmentView;

public class EnrollmentController implements ActionListener {

    public EnrollmentController(EnrollmentView enrollmentView) {
        this.enrollmentView = enrollmentView;
        
        this.enrollmentList = EnrollmentModel.getEnrollmentList();

        addListeners();
    }

    //Método que se encarga de dar acción a los oyentes, eventos.
    private void addListeners() {
        enrollmentView.btnSave.addActionListener(this);
        enrollmentView.btnUpdate.addActionListener(this);
        enrollmentView.btnDelete.addActionListener(this);
        enrollmentView.tableEnrollment.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int rowSelected = enrollmentView.tableEnrollment.rowAtPoint(e.getPoint());

                enrollmentView.txtEnrollmentNumber.setText(enrollmentView.tableEnrollment.getValueAt(rowSelected, 0).toString());
                enrollmentView.txtStudentCode.setText(enrollmentView.tableEnrollment.getValueAt(rowSelected, 1).toString());
                enrollmentView.txtCourseCode.setText(enrollmentView.tableEnrollment.getValueAt(rowSelected, 2).toString());

                enrollmentView.txtEnrollmentNumber.setEnabled(false);
                enrollmentView.txtStudentCode.setEnabled(false);

                enrollmentView.btnUpdate.setEnabled(true);
                enrollmentView.btnSave.setEnabled(false);
                enrollmentView.btnDelete.setEnabled(true);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (enrollmentView.btnSave == e.getSource()) {
            addEnrollment();
        } else if (enrollmentView.btnUpdate == e.getSource()) {
            updateEnrollment();
        } else if (enrollmentView.btnDelete == e.getSource()) {
            if (JOptionPane.showConfirmDialog(enrollmentView, "Desea Eliminar el Curso?") == 0) {
                deleteEnrollment();
            } else {
                JOptionPane.showMessageDialog(enrollmentView, "No procede");
                enrollmentView.btnSave.setEnabled(true);
                enrollmentView.btnUpdate.setEnabled(false);
                enrollmentView.btnDelete.setEnabled(false);
                
                enrollmentView.txtStudentCode.setEnabled(true);
                
                clearTextFields();
            }
        }
    }

    //agrega una nueva matricula
    private void addEnrollment() {
        int code = correlativeCode();
        int studentCode = Integer.parseInt(enrollmentView.txtStudentCode.getText());
        int courseCode = Integer.parseInt(enrollmentView.txtCourseCode.getText());
        List<Student> studentList = StudentModel.getStudentList();
        List<Course> courseList = CourseModel.getCourseList();
        //valida si existe el alumno y el curso en la fake database.
        if (thereStudent(studentList, studentCode) && thereCourse(courseList, courseCode)) {
            if (validStudentRegisteredOnlyOnce(studentCode)) {
                String currentDate = getCurrentDate();
                String currentTime = getCurrentTime();
                Enrollment enrollment = new Enrollment(code, studentCode, courseCode, currentDate, currentTime);

                int indexStudent = searchIndexStudent(studentList, studentCode);
                Student student = studentList.get(indexStudent);
                student.setState(Student.MATRICULATE);
                studentList.set(indexStudent, student);

                enrollmentList.add(enrollment);
            } else {
                JOptionPane.showMessageDialog(enrollmentView, "El alumno ya esta matrículado, solo se puede registrar una vez");
            }
        } else {
            JOptionPane.showMessageDialog(enrollmentView, "El curso o el alumno no existe");
        }

        showDataOnTable();
    }

    //Actualiza la matriícula, solo el código del curso matrículado
    private void updateEnrollment() {
        int enrollmentNumber = Integer.parseInt(enrollmentView.txtEnrollmentNumber.getText());
        int courseCode = Integer.parseInt(enrollmentView.txtCourseCode.getText());
        int indexEnrollment = searchIndexEnrollment(enrollmentNumber);
        Enrollment enrollment = enrollmentList.get(indexEnrollment);
        enrollment.setCourseCode(courseCode);
        enrollmentList.set(indexEnrollment, enrollment);

        showDataOnTable();
        clearTextFields();

        enrollmentView.btnSave.setEnabled(true);
        enrollmentView.btnUpdate.setEnabled(false);
        enrollmentView.btnDelete.setEnabled(false);

        enrollmentView.txtStudentCode.setEnabled(true);
    }

    //Elimina matrícula
    private void deleteEnrollment() {
        List<Student> studentList = StudentModel.getStudentList();
        int enrollmentNumber = Integer.parseInt(enrollmentView.txtEnrollmentNumber.getText());
        int studentCode = Integer.parseInt(enrollmentView.txtStudentCode.getText());
        if (validEliminationRegistration(studentList, studentCode)) {
            int indexEnrollment = searchIndexEnrollment(enrollmentNumber);
            enrollmentList.remove(indexEnrollment);
        } else {
            JOptionPane.showMessageDialog(enrollmentView, "El estado del alumno debe ser 0 o 1");
        }

        showDataOnTable();
        clearTextFields();

        enrollmentView.btnSave.setEnabled(true);
        enrollmentView.btnUpdate.setEnabled(false);
        enrollmentView.btnDelete.setEnabled(false);

        enrollmentView.txtStudentCode.setEnabled(true);
    }

    //genera codigo Correlativo
    private int correlativeCode() {
        if (enrollmentList.isEmpty()) {
            return 100000;
        } else {
            return enrollmentList.get(enrollmentList.size() - 1).getEnrollmentNumber()+ 1;
        }
    }

    //Muestra los datos de matricula, de la fake DDBB.
    public void showDataOnTable() {
        DefaultTableModel newModel = new DefaultTableModel();
        Object columns[] = {"Matrícula", "Código Alumno", "Código Curso", "fecha", "hora"};
        for (Object column : columns) {
            newModel.addColumn(column);
        }
        if (!enrollmentList.isEmpty()) {
            Object rows[] = new Object[columns.length];
            for (Enrollment matricula : enrollmentList) {
                rows[0] = matricula.getEnrollmentNumber();
                rows[1] = matricula.getStudentCode();
                rows[2] = matricula.getCourseCode();
                rows[3] = matricula.getDate();
                rows[4] = matricula.getTime();
                newModel.addRow(rows);
            }
        }
        enrollmentView.tableEnrollment.setModel(newModel);
    }

    //limpuea los JTextFields
    private void clearTextFields() {
        enrollmentView.txtEnrollmentNumber.setText("");
        enrollmentView.txtStudentCode.setText("");
        enrollmentView.txtCourseCode.setText("");
    }

    //Elimina la matrícula si el estado esta en 0 o 1. y no en 2
    private boolean validEliminationRegistration(List<Student> studentList, int studentCode) {
        int indexStudent = searchIndexStudent(studentList, studentCode);
        Student student = studentList.get(indexStudent);
        return student.getState()!= 2;
    }

    //genera con formato el fecha actual
    private String getCurrentDate() {
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
        return formatDate.format(new Date(System.currentTimeMillis()));
    }

    //genera con formato la hora actual
    private String getCurrentTime() {
        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
        return formatTime.format(new Date(System.currentTimeMillis()));
    }

    //valida si existe el alumno en la fake ddbb
    private boolean thereStudent(List<Student> studentList, int studentCode) {
        return studentList.stream().anyMatch((student) -> (student.getStudentCode()== studentCode));
    }

    //valida si existe el curso en la fake ddbb
    private boolean thereCourse(List<Course> courseList, int courseCode) {
        return courseList.stream().anyMatch((curso) -> (curso.getCourseCode()== courseCode));
    }

    //valida si el alumno ya esta registrado, si hay coincidencia en los códigos devuelve: false
    private boolean validStudentRegisteredOnlyOnce(int studentCode) {
        return enrollmentList
                .stream()
                .noneMatch((register) -> (register.getStudentCode()== studentCode));
    }

    //Obtener el index del alumno, para cambiar el estado a matrículado
    private int searchIndexStudent(List<Student> studentList, int code) {
        int index = -1;
        int bound = studentList.size();
        for (int userInd = 0; userInd < bound; userInd++) {
            if (studentList.get(userInd).getStudentCode()== code) {
                index = userInd;
                break;
            }
        }
        return index;
    }

    //Obtener el index de la matrícula, para cambiar el curso matriculado
    private int searchIndexEnrollment(int enrollmentNumber) {
        int index = -1;
        int bound = enrollmentList.size();
        for (int userInd = 0; userInd < bound; userInd++) {
            if (enrollmentList.get(userInd).getEnrollmentNumber()== enrollmentNumber) {
                index = userInd;
                break;
            }
        }
        return index;
    }

    private final EnrollmentView enrollmentView;
    private List<Enrollment> enrollmentList;
}
