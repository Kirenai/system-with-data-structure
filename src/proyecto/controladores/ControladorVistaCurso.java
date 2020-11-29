package proyecto.controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import proyecto.modelos.Curso;
import proyecto.modelos.Matricula;
import proyecto.vistas.CursoVista;

public class ControladorVistaCurso implements ActionListener {

    public ControladorVistaCurso(CursoVista viewCourse) {
        this.viewCourse = viewCourse;

        addListeners();
    }

    private void addListeners() {
        viewCourse.btnGuardar.addActionListener(this);
        viewCourse.btnActualizar.addActionListener(this);
        viewCourse.btnEliminar.addActionListener(this);
        viewCourse.btnMostrar.addActionListener(this);
        viewCourse.tableCurso.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int rowSelected = viewCourse.tableCurso.rowAtPoint(e.getPoint());

                viewCourse.txtCodigo.setText(viewCourse.tableCurso.getValueAt(rowSelected, 0).toString());
                viewCourse.txtAsignatura.setText(viewCourse.tableCurso.getValueAt(rowSelected, 1).toString());
                viewCourse.txtCiclo.setText(viewCourse.tableCurso.getValueAt(rowSelected, 2).toString());
                viewCourse.txtCreditos.setText(viewCourse.tableCurso.getValueAt(rowSelected, 3).toString());
                viewCourse.txtHoras.setText(viewCourse.tableCurso.getValueAt(rowSelected, 4).toString());

                viewCourse.btnGuardar.setEnabled(false);
                viewCourse.btnActualizar.setEnabled(true);
                viewCourse.btnEliminar.setEnabled(true);

                viewCourse.txtCodigo.setEnabled(false);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (viewCourse.btnGuardar == e.getSource()) {
            addCourse();
        } else if (viewCourse.btnMostrar == e.getSource()) {
            System.out.println(listCourse);
        } else if (viewCourse.btnActualizar == e.getSource()) {
            updateCourse();
        } else if (viewCourse.btnEliminar == e.getSource()) {
            if (JOptionPane.showConfirmDialog(viewCourse, "Desea Eliminar el Curso?") == 0) {
                deleteCourse();
            } else {
                JOptionPane.showMessageDialog(viewCourse, "No procede");
                viewCourse.btnGuardar.setEnabled(true);
                viewCourse.btnActualizar.setEnabled(false);
                viewCourse.btnEliminar.setEnabled(false);
                viewCourse.txtCodigo.setEnabled(true);
                clearTextFields();
            }
        }
    }

    private void addCourse() {
        int codigo = Integer.parseInt(viewCourse.txtCodigo.getText());
        if (compareCodeCourse(codigo)) {
            String asignatura = viewCourse.txtAsignatura.getText();
            int ciclo = Integer.parseInt(viewCourse.txtCiclo.getText());
            int creditos = Integer.parseInt(viewCourse.txtCreditos.getText());
            int horas = Integer.parseInt(viewCourse.txtHoras.getText());
            Curso course = new Curso(codigo, asignatura, ciclo, creditos, horas);
            listCourse.add(course);

            sortedByCode();
            showDataOnTable();
            clearTextFields();
        } else {
            JOptionPane.showMessageDialog(viewCourse, "El código del curso no debe ser el mismo");
        }
    }

    private void updateCourse() {
        int codigo = Integer.parseInt(viewCourse.txtCodigo.getText());
        String asignatura = viewCourse.txtAsignatura.getText();
        int ciclo = Integer.parseInt(viewCourse.txtCiclo.getText());
        int creditos = Integer.parseInt(viewCourse.txtCreditos.getText());
        int horas = Integer.parseInt(viewCourse.txtHoras.getText());

        int indexCourse = searchIndex(codigo);
        
        Curso course = listCourse.get(indexCourse);
        course.setCodCurso(codigo);
        course.setAsignatura(asignatura);
        course.setCiclo(ciclo);
        course.setCreditos(creditos);
        course.setHoras(horas);
        
        listCourse.set(indexCourse, course);

        showDataOnTable();
        clearTextFields();

        viewCourse.btnGuardar.setEnabled(true);
        viewCourse.btnActualizar.setEnabled(false);
        viewCourse.btnEliminar.setEnabled(false);
        
        viewCourse.txtCodigo.setEnabled(true);
    }

    private void deleteCourse() {
        //Eliminar el curso si no tiene alumnos matriculados al curso
        int codeCourse = Integer.parseInt(viewCourse.txtCodigo.getText());
        List<Matricula> listRegistration = ControladorVistaMatricula.getListRegistration();
        if (compareStudentBelongToCourse(listRegistration, codeCourse)) {
            int indexCourse = searchIndex(codeCourse);
            listCourse.remove(indexCourse);
            showDataOnTable();

            viewCourse.btnGuardar.setEnabled(true);
            viewCourse.btnActualizar.setEnabled(false);
            viewCourse.btnEliminar.setEnabled(false);
            viewCourse.txtCodigo.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(viewCourse, "El curso no se puede eliminar por tener alumnos matriculados");

            viewCourse.btnGuardar.setEnabled(true);
            viewCourse.btnActualizar.setEnabled(false);
            viewCourse.btnEliminar.setEnabled(false);
            viewCourse.txtCodigo.setEnabled(true);
        }
        clearTextFields();
    }

    public void showDataOnTable() {
        DefaultTableModel newModel = new DefaultTableModel();
        Object columns[] = {"Código", "Asignatura", "Ciclo", "Créditos", "Horas"};
        for (Object column : columns) {
            newModel.addColumn(column);
        }
        if (!listCourse.isEmpty()) {
            Object rows[] = new Object[columns.length];
            for (Curso curso : listCourse) {
                rows[0] = curso.getCodCurso();
                rows[1] = curso.getAsignatura();
                rows[2] = curso.getCiclo();
                rows[3] = curso.getCreditos();
                rows[4] = curso.getHoras();
                newModel.addRow(rows);
            }
        }
        viewCourse.tableCurso.setModel(newModel);
    }

    private void clearTextFields() {
        viewCourse.txtCodigo.setText("");
        viewCourse.txtAsignatura.setText("");
        viewCourse.txtCiclo.setText("");
        viewCourse.txtCreditos.setText("");
        viewCourse.txtHoras.setText("");
    }

    private boolean compareCodeCourse(int codCourse) {
        return listCourse
                .stream()
                .noneMatch((course) -> (course.getCodCurso() == codCourse));  //Si encuentra = false
    }

    private int searchIndex(int codigo) {
        int index = -1;
        int bound = listCourse.size();
        for (int userInd = 0; userInd < bound; userInd++) {
            if (listCourse.get(userInd).getCodCurso() == codigo) {
                index = userInd;
                break;
            }
        }
        return index;
    }

    private boolean compareStudentBelongToCourse(List<Matricula> listRegistration, int codeCourse) {
        int cnt = 0;
        cnt = listRegistration
                .stream()
                .filter((matricula) -> (codeCourse == matricula.getCodCurso()))
                .map((item) -> 1)
                .reduce(cnt, Integer::sum);
        return cnt == 0;
    }
    
    private void sortedByCode() {
        listCourse
                .sort(Comparator.comparing(code -> code.getCodCurso()));
    }
    

    public static List<Curso> getListCourse() {
        return listCourse;
    }

    private final CursoVista viewCourse;
    private static List<Curso> listCourse = new ArrayList<>();
}
