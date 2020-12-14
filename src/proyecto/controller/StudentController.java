package proyecto.controller;

import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import proyecto.model.Student;
import proyecto.model.StudentModel;
import proyecto.view.StudentView;

public class StudentController implements ActionListener {

    public StudentController(StudentView viewStudent) {
        this.viewStudent = viewStudent;
        
        this.studentList = StudentModel.getStudentList();
        
        addListenners();
    }

    private void addListenners() {
        viewStudent.btnSave.addActionListener(this);
        viewStudent.btnUpdate.addActionListener(this);
        viewStudent.btnDelete.addActionListener(this);
        viewStudent.tableStudent.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int rowSelected = viewStudent.tableStudent.rowAtPoint(e.getPoint());

                viewStudent.txtCode.setText(viewStudent.tableStudent.getValueAt(rowSelected, 0).toString());
                viewStudent.txtName.setText(viewStudent.tableStudent.getValueAt(rowSelected, 1).toString());
                viewStudent.txtLastName.setText(viewStudent.tableStudent.getValueAt(rowSelected, 2).toString());
                viewStudent.txtDNI.setText(viewStudent.tableStudent.getValueAt(rowSelected, 3).toString());
                viewStudent.txtAge.setText(viewStudent.tableStudent.getValueAt(rowSelected, 4).toString());
                viewStudent.txtCellular.setText(viewStudent.tableStudent.getValueAt(rowSelected, 5).toString());
                viewStudent.txtState.setText(viewStudent.tableStudent.getValueAt(rowSelected, 6).toString());

                viewStudent.btnUpdate.setEnabled(true);
                viewStudent.btnSave.setEnabled(false);
                viewStudent.txtDNI.setEnabled(false);
                viewStudent.btnDelete.setEnabled(true);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == viewStudent.btnSave) {
            try {
                addStudent();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(viewStudent, ex.getMessage());
            }
        } else if (e.getSource() == viewStudent.btnUpdate) {
            updateStudent();
            viewStudent.btnSave.setEnabled(true);
            viewStudent.btnUpdate.setEnabled(false);
        } else if (e.getSource() == viewStudent.btnDelete) {
            try {
                if (JOptionPane.showConfirmDialog(viewStudent, "Desea Eliminar al alumno?") == 0) {
                    deleteStudent();
                } else {
                    JOptionPane.showMessageDialog(viewStudent, "No procede");
                    viewStudent.btnSave.setEnabled(true);
                    viewStudent.btnUpdate.setEnabled(false);
                    viewStudent.btnDelete.setEnabled(false);
                    clearTextFields();
                }
            } catch (Exception ex) {
                clearTextFields();
                viewStudent.btnSave.setEnabled(true);
                viewStudent.btnUpdate.setEnabled(false);
                viewStudent.btnDelete.setEnabled(false);
                viewStudent.txtDNI.setEnabled(true);
                JOptionPane.showMessageDialog(viewStudent, ex.getMessage());
            }
        }
    }

    //Agregando alumno
    private void addStudent() throws Exception {
        int code = correlativeCode();
        String name = viewStudent.txtName.getText();
        String lastName = viewStudent.txtLastName.getText();
        String dni = viewStudent.txtDNI.getText();
        if (!verifyDNI(dni)) {
            throw new Exception("Error dni duplicado");
        }
        int age = Integer.parseInt(viewStudent.txtAge.getText());
        int cellular = Integer.parseInt(viewStudent.txtCellular.getText());
        int state = Integer.parseInt(viewStudent.txtState.getText());
        Student student = new Student(code, name, lastName, dni, age, cellular, state);
        studentList.add(student);
        
        writeStudents();
        
        showDataOnTable();
        clearTextFields();
    }

    private int correlativeCode() {
        if (studentList.isEmpty()) {
            return 202010000;
        } else {
            return studentList.get(studentList.size() - 1).getStudentCode()+ 1;
        }
    }

    private boolean verifyDNI(String dni) {
//        for (Student alumno : studentList) {
//            if (alumno.getDni().equals(dni)) {
//                return false;
//            }
//        }
//        return true;
        return studentList.stream()
                .noneMatch((student) -> (student.getDni().equals(dni)));
    }

    private void updateStudent() {
        int code = Integer.parseInt(viewStudent.txtCode.getText());
        String name = viewStudent.txtName.getText();
        String lastName = viewStudent.txtLastName.getText();
        String dni = viewStudent.txtDNI.getText();
        int age = Integer.parseInt(viewStudent.txtAge.getText());
        int cellular = Integer.parseInt(viewStudent.txtCellular.getText());
        int state = Integer.parseInt(viewStudent.txtState.getText());

        Student student = new Student(code, name, lastName, dni, age, cellular, state);

        int indexAlumno = searchIndex(code);
        studentList.set(indexAlumno, student);
        
        try {
            writeStudents();
        } catch (IOException e) {
            System.out.println("Error E/S " +e.getMessage());
        }
        
        showDataOnTable();
        clearTextFields();

        viewStudent.txtDNI.setEnabled(true);
        viewStudent.btnSave.setEnabled(true);
        viewStudent.btnDelete.setEnabled(false);
    }

    private void deleteStudent() throws Exception {
        int state = Integer.parseInt(viewStudent.txtState.getText());
        if (state == 0) {
            int indexStudent = searchIndex(Integer.parseInt(viewStudent.txtCode.getText()));
            studentList.remove(indexStudent);
            
            writeStudents();
            
            showDataOnTable();
            clearTextFields();

            viewStudent.txtDNI.setEnabled(true);

            viewStudent.btnSave.setEnabled(true);
            viewStudent.btnDelete.setEnabled(false);
            viewStudent.btnUpdate.setEnabled(false);

        } else {
            throw new Exception("Solo se puede eliminar alumnos en el estado registrado (0)");
        }
    }

    public void showDataOnTable() {
        DefaultTableModel newModel = new DefaultTableModel();
        Object columns[] = {"Código", "Nombres", "Apellidos", "DNI", "Edad",
            "Celular", "Estado"};
        for (Object column : columns) {
            newModel.addColumn(column);
        }
        if (!studentList.isEmpty()) {
            Object rows[] = new Object[columns.length];
            for (Student alumno : studentList) {
                rows[0] = alumno.getStudentCode();
                rows[1] = alumno.getName();
                rows[2] = alumno.getLastName();
                rows[3] = alumno.getDni();
                rows[4] = alumno.getAge();
                rows[5] = alumno.getCellular();
                rows[6] = alumno.getState();
                newModel.addRow(rows);
            }
        }
        viewStudent.tableStudent.setModel(newModel);
    }

    private int searchIndex(int code) {
        int index = -1;
        int bound = studentList.size();
        for (int i = 0; i < bound; i++) {
            if (studentList.get(i).getStudentCode()== code) {
                index = i;
                break;
            }
        }
        return index;
    }

    private void clearTextFields() {
        viewStudent.txtCode.setText("");
        viewStudent.txtName.setText("");
        viewStudent.txtLastName.setText("");
        viewStudent.txtDNI.setText("");
        viewStudent.txtAge.setText("");
        viewStudent.txtCellular.setText("");
        viewStudent.txtState.setText("");
    }
    
    private void writeStudents() throws IOException {
        PrintWriter write = new PrintWriter(
                new FileWriter("alumnos.txt")
        );
        String line;
        for(Student student: studentList) {
            line = String.format("%s;%s;%s;%s;%s;%s;%s\n", student.getStudentCode(),
                                        student.getName(), student.getLastName(),
                                        student.getDni(), student.getAge(), 
                                        student.getCellular(), student.getState());
            write.write(line);
        }
        write.close();
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
                studentList.add(student);
            }
            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error E/S " + e.getMessage());
        } catch (IOException ex) {
            System.out.println("Error E/S " + ex.getMessage());
        }
        
    }
    
    public void clearListStudents() {
        studentList.clear();
    }

    private final StudentView viewStudent;
    private List<Student> studentList;
}
