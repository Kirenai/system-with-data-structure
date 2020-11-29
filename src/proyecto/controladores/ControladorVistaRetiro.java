package proyecto.controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import proyecto.modelos.Alumno;
import proyecto.modelos.Matricula;
import proyecto.modelos.Retiro;
import proyecto.vistas.RetiroVista;

public class ControladorVistaRetiro implements ActionListener {

    public ControladorVistaRetiro(RetiroVista viewRetirement) {
        this.viewRetirement = viewRetirement;

        addListeners();
    }

    private void addListeners() {
        viewRetirement.btnGuardar.addActionListener(this);
        viewRetirement.btnEliminar.addActionListener(this);
        viewRetirement.tableRetiro.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int rowSelected = viewRetirement.tableRetiro.rowAtPoint(e.getPoint());

                viewRetirement.txtNumRetiro.setText(viewRetirement.tableRetiro.getValueAt(rowSelected, 0).toString());
                viewRetirement.txtNumMatricula.setText(viewRetirement.tableRetiro.getValueAt(rowSelected, 1).toString());

                viewRetirement.txtNumRetiro.setEnabled(false);
                viewRetirement.txtNumMatricula.setEnabled(false);

                viewRetirement.btnGuardar.setEnabled(false);
                viewRetirement.btnEliminar.setEnabled(true);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == viewRetirement.btnGuardar) {
            addRetirement();
        } else if (e.getSource() == viewRetirement.btnEliminar) {
            if (JOptionPane.showConfirmDialog(viewRetirement, "Desea Eliminar el Curso?") == 0) {
                deleteRetirement();
            } else {
                JOptionPane.showMessageDialog(viewRetirement, "No procede");

                viewRetirement.btnGuardar.setEnabled(true);
                viewRetirement.btnEliminar.setEnabled(false);

                viewRetirement.txtNumMatricula.setEnabled(true);

                clearTextFields();
            }
        }
    }

    private void addRetirement() {
        int codeRetirement = codigoCorrelativo();
        int numRegister = Integer.parseInt(viewRetirement.txtNumMatricula.getText());
        String currentDate = getCurrentDate();
        String currentTime = getCurrentTime();

        List<Alumno> listStudents = ControladorVistaAlumno.getListAlumno();
        List<Matricula> listRegistrations = ControladorVistaMatricula.getListRegistration();

        Retiro retirement = new Retiro(codeRetirement, numRegister, currentDate, currentTime);

        listRetirement.add(retirement);

        settingRetiredState(numRegister, listRegistrations, listStudents);

        showDataOnTable();
        clearTextFields();
    }

    private void deleteRetirement() {
        int codeRetirement = Integer.parseInt(viewRetirement.txtNumRetiro.getText());
        int CodeRegistration = Integer.parseInt(viewRetirement.txtNumMatricula.getText());
        int indexRetirement = searchIndexRetirement(codeRetirement);
        List<Alumno> listStudents = ControladorVistaAlumno.getListAlumno();
        List<Matricula> listRegistrations = ControladorVistaMatricula.getListRegistration();
        if (validEliminationRetirement(CodeRegistration, listRegistrations, listStudents)) {
            listRetirement.remove(indexRetirement);
        } else {
            JOptionPane.showMessageDialog(viewRetirement, "El alumno sólo podra "
                    + "cancelar el retiro si su estado es 2");
        }

        showDataOnTable();
        clearTextFields();

        viewRetirement.txtNumMatricula.setEnabled(true);

        viewRetirement.btnGuardar.setEnabled(true);
        viewRetirement.btnEliminar.setEnabled(false);
    }

    //Código correlativo
    private int codigoCorrelativo() {
        if (listRetirement.isEmpty()) {
            return 200000;
        } else {
            return listRetirement.get(listRetirement.size() - 1).getNumRetiro() + 1;
        }
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

    //Fijando estado a retirado
    private void settingRetiredState(int numRegister,
            List<Matricula> listRegistrations,
            List<Alumno> listStudents) {
        for (Matricula register : listRegistrations) {
            if (register.getNumMatricula() == numRegister) {
                int indexStudent = searchIndexStudent(listStudents, register.getCodAlumno());
                Alumno student = listStudents.get(indexStudent);
                student.setEstado(Alumno.RETIRADO);
                listStudents.set(indexStudent, student);
            }
        }
    }

    //Valida eliminación de retiro
    private boolean validEliminationRetirement(int code,
            List<Matricula> listRegistrations,
            List<Alumno> listStudents) {
        for (Matricula register : listRegistrations) {
            if (register.getNumMatricula() == code) {
                int indexStudent = searchIndexStudent(listStudents, register.getCodAlumno());
                Alumno student = listStudents.get(indexStudent);
                return student.getEstado() == 2;
            }
        }
        return true;
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

    //Obtener el index del alumno, para cambiar el estado a matrículado
    private int searchIndexRetirement(int codigo) {
        int index = -1;
        int bound = listRetirement.size();
        for (int userInd = 0; userInd < bound; userInd++) {
            if (listRetirement.get(userInd).getNumRetiro() == codigo) {
                index = userInd;
                break;
            }
        }
        return index;
    }

    //Muestra los datos de retiro, de la fake DDBB.
    public void showDataOnTable() {
        DefaultTableModel newModel = new DefaultTableModel();
        Object columns[] = {"Retiro", "Matricula", "Fecha", "Hora"};
        for (Object column : columns) {
            newModel.addColumn(column);
        }
        if (!listRetirement.isEmpty()) {
            Object rows[] = new Object[columns.length];
            for (Retiro retirement : listRetirement) {
                rows[0] = retirement.getNumRetiro();
                rows[1] = retirement.getNumMatricula();
                rows[2] = retirement.getFecha();
                rows[3] = retirement.getHora();
                newModel.addRow(rows);
            }
        }
        viewRetirement.tableRetiro.setModel(newModel);
    }

    private void clearTextFields() {
        viewRetirement.txtNumMatricula.setText("");
        viewRetirement.txtNumRetiro.setText("");
    }

    public static List<Retiro> getListRetirement() {
        return listRetirement;
    }
    
    private final RetiroVista viewRetirement;
    private static List<Retiro> listRetirement = new ArrayList<>();
}
