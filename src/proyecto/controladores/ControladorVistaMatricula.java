package proyecto.controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import proyecto.modelos.Alumno;
import proyecto.modelos.Curso;
import proyecto.modelos.Matricula;
import proyecto.vistas.MatriculaVista;

public class ControladorVistaMatricula implements ActionListener {

    public ControladorVistaMatricula(MatriculaVista viewRegistration) {
        this.viewRegistration = viewRegistration;

        addListeners();
    }

    //Método que se encarga de dar acción a los oyentes, eventos.
    private void addListeners() {
        viewRegistration.btnGuardar.addActionListener(this);
        viewRegistration.btnActualizar.addActionListener(this);
        viewRegistration.btnEliminar.addActionListener(this);
        viewRegistration.tableMatricula.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int rowSelected = viewRegistration.tableMatricula.rowAtPoint(e.getPoint());

                viewRegistration.txtMatricula.setText(viewRegistration.tableMatricula.getValueAt(rowSelected, 0).toString());
                viewRegistration.txtCodAlumno.setText(viewRegistration.tableMatricula.getValueAt(rowSelected, 1).toString());
                viewRegistration.txtCodCurso.setText(viewRegistration.tableMatricula.getValueAt(rowSelected, 2).toString());

                viewRegistration.txtMatricula.setEnabled(false);
                viewRegistration.txtCodAlumno.setEnabled(false);

                viewRegistration.btnActualizar.setEnabled(true);
                viewRegistration.btnGuardar.setEnabled(false);
                viewRegistration.btnEliminar.setEnabled(true);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (viewRegistration.btnGuardar == e.getSource()) {
            addRegistration();
        } else if (viewRegistration.btnActualizar == e.getSource()) {
            updateRegistration();
        } else if (viewRegistration.btnEliminar == e.getSource()) {
            if (JOptionPane.showConfirmDialog(viewRegistration, "Desea Eliminar el Curso?") == 0) {
                deleteRegistration();
            } else {
                JOptionPane.showMessageDialog(viewRegistration, "No procede");
                viewRegistration.btnGuardar.setEnabled(true);
                viewRegistration.btnActualizar.setEnabled(false);
                viewRegistration.btnEliminar.setEnabled(false);
                
                viewRegistration.txtCodAlumno.setEnabled(true);
                
                clearTextFields();
            }
        }
    }

    //agrega una nueva matricula
    private void addRegistration() {
        int codigo = codigoCorrelativo();
        int codAlumno = Integer.parseInt(viewRegistration.txtCodAlumno.getText());
        int codCurso = Integer.parseInt(viewRegistration.txtCodCurso.getText());
        List<Alumno> listAlumno = ControladorVistaAlumno.getListAlumno();
        List<Curso> listCourse = ControladorVistaCurso.getListCourse();
        //valida si existe el alumno y el curso en la fake database.
        if (thereStudent(listAlumno, codAlumno) && thereCourse(listCourse, codCurso)) {
            if (validStudentRegisteredOnlyOnce(codAlumno)) {
                String currentDate = getCurrentDate();
                String currentTime = getCurrentTime();
                Matricula matricula = new Matricula(codigo, codAlumno, codCurso, currentDate, currentTime);

                int indexStudent = searchIndexStudent(listAlumno, codAlumno);
                Alumno student = listAlumno.get(indexStudent);
                student.setEstado(Alumno.MATRICULADO);
                listAlumno.set(indexStudent, student);

                listRegistration.add(matricula);
            } else {
                JOptionPane.showMessageDialog(viewRegistration, "El alumno ya esta matrículado, solo se puede registrar una vez");
            }
        } else {
            JOptionPane.showMessageDialog(viewRegistration, "El curso o el alumno no existe");
        }

        showDataOnTable();
    }

    //Actualiza la matriícula, solo el código del curso matrículado
    private void updateRegistration() {
        int codeRegister = Integer.parseInt(viewRegistration.txtMatricula.getText());
        int codeCourse = Integer.parseInt(viewRegistration.txtCodCurso.getText());
        int indexRegister = searchIndexRegister(codeRegister);
        Matricula register = listRegistration.get(indexRegister);
        register.setCodCurso(codeCourse);
        listRegistration.set(indexRegister, register);

        showDataOnTable();
        clearTextFields();

        viewRegistration.btnGuardar.setEnabled(true);
        viewRegistration.btnActualizar.setEnabled(false);
        viewRegistration.btnEliminar.setEnabled(false);

        viewRegistration.txtCodAlumno.setEnabled(true);
    }

    //Elimina matrícula
    private void deleteRegistration() {
        List<Alumno> listStudents = ControladorVistaAlumno.getListAlumno();
        int codeRegister = Integer.parseInt(viewRegistration.txtMatricula.getText());
        int codeStudent = Integer.parseInt(viewRegistration.txtCodAlumno.getText());
        if (validEliminationRegistration(listStudents, codeStudent)) {
            int indexRegister = searchIndexRegister(codeRegister);
            listRegistration.remove(indexRegister);
        } else {
            JOptionPane.showMessageDialog(viewRegistration, "El estado del alumno debe ser 0 o 1");
        }

        showDataOnTable();
        clearTextFields();

        viewRegistration.btnGuardar.setEnabled(true);
        viewRegistration.btnActualizar.setEnabled(false);
        viewRegistration.btnEliminar.setEnabled(false);

        viewRegistration.txtCodAlumno.setEnabled(true);
    }

    //genera codigo Correlativo
    private int codigoCorrelativo() {
        if (listRegistration.isEmpty()) {
            return 100000;
        } else {
            return listRegistration.get(listRegistration.size() - 1).getNumMatricula() + 1;
        }
    }

    //Muestra los datos de matricula, de la fake DDBB.
    public void showDataOnTable() {
        DefaultTableModel newModel = new DefaultTableModel();
        Object columns[] = {"Matrícula", "Código Alumno", "Código Curso", "fecha", "hora"};
        for (Object column : columns) {
            newModel.addColumn(column);
        }
        if (!listRegistration.isEmpty()) {
            Object rows[] = new Object[columns.length];
            for (Matricula matricula : listRegistration) {
                rows[0] = matricula.getNumMatricula();
                rows[1] = matricula.getCodAlumno();
                rows[2] = matricula.getCodCurso();
                rows[3] = matricula.getFecha();
                rows[4] = matricula.getHora();
                newModel.addRow(rows);
            }
        }
        viewRegistration.tableMatricula.setModel(newModel);
    }

    //limpuea los JTextFields
    private void clearTextFields() {
        viewRegistration.txtMatricula.setText("");
        viewRegistration.txtCodAlumno.setText("");
        viewRegistration.txtCodCurso.setText("");
    }

    //Elimina la matrícula si el estado esta en 0 o 1. y no en 2
    private boolean validEliminationRegistration(List<Alumno> listStudents, int codeStudent) {
        int indexStudent = searchIndexStudent(listStudents, codeStudent);
        Alumno student = listStudents.get(indexStudent);
        return student.getEstado() != 2;
    }

    //Muestra los códigos de los alumnos de la fake database
    public void showDataAlumnoOnJComboBox() {
        DefaultComboBoxModel<Integer> modelComboBox = new DefaultComboBoxModel<>();
        for (Alumno alumno : ControladorVistaAlumno.getListAlumno()) {
            modelComboBox.addElement(alumno.getCodAlumno());
        }
        viewRegistration.jcbCodAlumno.setModel(modelComboBox);
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
    private boolean thereStudent(List<Alumno> listAlumno, int codAlumno) {
        return listAlumno.stream().anyMatch((alumno) -> (alumno.getCodAlumno() == codAlumno));
    }

    //valida si existe el curso en la fake ddbb
    private boolean thereCourse(List<Curso> listCourse, int codCourse) {
        return listCourse.stream().anyMatch((curso) -> (curso.getCodCurso() == codCourse));
    }

    //valida si el alumno ya esta registrado, si hay coincidencia en los códigos devuelve: false
    private boolean validStudentRegisteredOnlyOnce(int codeStudent) {
        return listRegistration
                .stream()
                .noneMatch((register) -> (register.getCodAlumno() == codeStudent));
    }

    //Obtener el index del alumno, para cambiar el estado a matrículado
    private int searchIndexStudent(List<Alumno> listStudent, int codigo) {
        int index = -1;
        int bound = listStudent.size();
        for (int userInd = 0; userInd < bound; userInd++) {
            if (listStudent.get(userInd).getCodAlumno() == codigo) {
                index = userInd;
                break;
            }
        }
        return index;
    }

    //Obtener el index de la matrícula, para cambiar el curso matriculado
    private int searchIndexRegister(int codigoRegister) {
        int index = -1;
        int bound = listRegistration.size();
        for (int userInd = 0; userInd < bound; userInd++) {
            if (listRegistration.get(userInd).getNumMatricula() == codigoRegister) {
                index = userInd;
                break;
            }
        }
        return index;
    }

    public static List<Matricula> getListRegistration() {
        return listRegistration;
    }

    private final MatriculaVista viewRegistration;
    private static List<Matricula> listRegistration = new ArrayList<>();
}
