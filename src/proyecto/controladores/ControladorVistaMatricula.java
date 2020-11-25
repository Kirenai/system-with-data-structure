package proyecto.controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    private void addListeners() {
        viewRegistration.btnGuardar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (viewRegistration.btnGuardar == e.getSource()) {
            addRegistration();
        }
    }

    private void addRegistration() {
        int codigo = codigoCorrelativo();
        int codAlumno = Integer.parseInt(viewRegistration.txtCodAlumno.getText());
        int codCurso = Integer.parseInt(viewRegistration.txtCodCurso.getText());
        List<Alumno> listAlumno = ControladorVistaAlumno.getListAlumno();
        List<Curso> listCourse = ControladorVistaCurso.getListCourse();
        if (thereStudent(listAlumno, codAlumno) && thereCourse(listCourse, codCurso)) {
            String currentDate = getCurrentDate();
            String currentTime = getCurrentTime();

            Matricula matricula = new Matricula(codigo, codAlumno, codCurso, currentDate, currentTime);

            listRegistration.add(matricula);
        } else {
            JOptionPane.showMessageDialog(viewRegistration, "El curso o el alumno no existe");
        }

        showDataOnTable();
    }

    private int codigoCorrelativo() {
        if (listRegistration.isEmpty()) {
            return 100000;
        } else {
            return listRegistration.get(listRegistration.size() - 1).getNumMatricula() + 1;
        }
    }

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
    
    public void showDataAlumnoOnJComboBox() {
        DefaultComboBoxModel<Integer> modelComboBox = new DefaultComboBoxModel<>();
        for (Alumno alumno : ControladorVistaAlumno.getListAlumno()) {
            modelComboBox.addElement(alumno.getCodAlumno());
        }
        viewRegistration.jcbCodAlumno.setModel(modelComboBox);
    }

    private String getCurrentDate() {
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
        return formatDate.format(new Date(System.currentTimeMillis()));
    }

    private String getCurrentTime() {
        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
        return formatTime.format(new Date(System.currentTimeMillis()));
    }

    private boolean thereStudent(List<Alumno> listAlumno, int codAlumno) {
        return listAlumno.stream().anyMatch((alumno) -> (alumno.getCodAlumno() == codAlumno));
    }

    private boolean thereCourse(List<Curso> listCourse, int codCourse) {
        return listCourse.stream().anyMatch((curso) -> (curso.getCodCurso() == codCourse));
    }

    private final MatriculaVista viewRegistration;
    private static List<Matricula> listRegistration = new ArrayList<>();
}
