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
import proyecto.model.Enrollment;
import proyecto.model.EnrollmentModel;
import proyecto.model.Retirement;
import proyecto.model.RetirementModel;
import proyecto.model.StudentModel;
import proyecto.view.RetirementView;

public class RetirementController implements ActionListener {

    public RetirementController(RetirementView retirementView) {
        this.retirementView = retirementView;

        this.retirementList = RetirementModel.getRetirementList();
        
        addListeners();
    }

    private void addListeners() {
        retirementView.btnSave.addActionListener(this);
        retirementView.btnDelete.addActionListener(this);
        retirementView.tableRetirement.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int rowSelected = retirementView.tableRetirement.rowAtPoint(e.getPoint());

                retirementView.txtRetirementNumber.setText(retirementView.tableRetirement.getValueAt(rowSelected, 0).toString());
                retirementView.txtEnrollmentNumber.setText(retirementView.tableRetirement.getValueAt(rowSelected, 1).toString());

                retirementView.txtRetirementNumber.setEnabled(false);
                retirementView.txtEnrollmentNumber.setEnabled(false);

                retirementView.btnSave.setEnabled(false);
                retirementView.btnDelete.setEnabled(true);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == retirementView.btnSave) {
            addRetirement();
        } else if (e.getSource() == retirementView.btnDelete) {
            if (JOptionPane.showConfirmDialog(retirementView, "Desea Eliminar el Curso?") == 0) {
                deleteRetirement();
            } else {
                JOptionPane.showMessageDialog(retirementView, "No procede");

                retirementView.btnSave.setEnabled(true);
                retirementView.btnDelete.setEnabled(false);

                retirementView.txtEnrollmentNumber.setEnabled(true);

                clearTextFields();
            }
        }
    }

    private void addRetirement() {
        int code = correlativeCode();
        int enrollmentNumber = Integer.parseInt(retirementView.txtEnrollmentNumber.getText());
        String currentDate = getCurrentDate();
        String currentTime = getCurrentTime();

        List<Student> studentList = StudentModel.getStudentList();
        List<Enrollment> enrollmentList = EnrollmentModel.getEnrollmentList();

        Retirement retirement = new Retirement(code, enrollmentNumber, currentDate, currentTime);

        retirementList.add(retirement);

        settingRetiredState(enrollmentNumber, enrollmentList, studentList);

        showDataOnTable();
        clearTextFields();
    }

    private void deleteRetirement() {
        int retirementNumber = Integer.parseInt(retirementView.txtRetirementNumber.getText());
        int enrollmentNumber = Integer.parseInt(retirementView.txtEnrollmentNumber.getText());
        int indexRetirement = searchIndexRetirement(retirementNumber);
        List<Student> studentList = StudentModel.getStudentList();
        List<Enrollment> enrollmentList = EnrollmentModel.getEnrollmentList();
        if (validEliminationRetirement(enrollmentNumber, enrollmentList, studentList)) {
            retirementList.remove(indexRetirement);
        } else {
            JOptionPane.showMessageDialog(retirementView, "El alumno sólo podra "
                    + "cancelar el retiro si su estado es 2");
        }

        showDataOnTable();
        clearTextFields();

        retirementView.txtEnrollmentNumber.setEnabled(true);

        retirementView.btnSave.setEnabled(true);
        retirementView.btnDelete.setEnabled(false);
    }

    //Código correlativo
    private int correlativeCode() {
        if (retirementList.isEmpty()) {
            return 200000;
        } else {
            return retirementList.get(retirementList.size() - 1).getRetirementNumber()+ 1;
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
    private void settingRetiredState(int enrollmentNumber,
            List<Enrollment> enrollmentList,
            List<Student> studentList) {
        for (Enrollment register : enrollmentList) {
            if (register.getEnrollmentNumber()== enrollmentNumber) {
                int indexStudent = searchIndexStudent(studentList, register.getStudentCode());
                Student student = studentList.get(indexStudent);
                student.setState(Student.REGISTERED);
                studentList.set(indexStudent, student);
            }
        }
    }

    //Valida eliminación de retiro
    private boolean validEliminationRetirement(int enrollmentNumber,
            List<Enrollment> enrollmentList,
            List<Student> studentList) {
        for (Enrollment register : enrollmentList) {
            if (register.getEnrollmentNumber()== enrollmentNumber) {
                int indexStudent = searchIndexStudent(studentList, register.getStudentCode());
                Student student = studentList.get(indexStudent);
                return student.getState()== 2;
            }
        }
        return true;
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

    //Obtener el index del alumno, para cambiar el estado a matrículado
    private int searchIndexRetirement(int code) {
        int index = -1;
        int bound = retirementList.size();
        for (int userInd = 0; userInd < bound; userInd++) {
            if (retirementList.get(userInd).getRetirementNumber()== code) {
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
        if (!retirementList.isEmpty()) {
            Object rows[] = new Object[columns.length];
            for (Retirement retirement : retirementList) {
                rows[0] = retirement.getRetirementNumber();
                rows[1] = retirement.getEnrollmentNumber();
                rows[2] = retirement.getDate();
                rows[3] = retirement.getTime();
                newModel.addRow(rows);
            }
        }
        retirementView.tableRetirement.setModel(newModel);
    }

    private void clearTextFields() {
        retirementView.txtEnrollmentNumber.setText("");
        retirementView.txtRetirementNumber.setText("");
    }
    
    private final RetirementView retirementView;
    private List<Retirement> retirementList;
}
