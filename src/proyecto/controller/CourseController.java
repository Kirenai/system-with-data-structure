package proyecto.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import proyecto.model.Course;
import proyecto.model.CourseModel;
import proyecto.model.Enrollment;
import proyecto.model.EnrollmentModel;
import proyecto.view.CourseView;

public class CourseController implements ActionListener {

    public CourseController(CourseView courseView) {
        this.courseView = courseView;

        this.courseList = CourseModel.getCourseList();
        
        addListeners();
    }

    private void addListeners() {
        courseView.btnSave.addActionListener(this);
        courseView.btnUpdate.addActionListener(this);
        courseView.btnDelete.addActionListener(this);
        courseView.tableCourse.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int rowSelected = courseView.tableCourse.rowAtPoint(e.getPoint());

                courseView.txtCode.setText(courseView.tableCourse.getValueAt(rowSelected, 0).toString());
                courseView.txtSubject.setText(courseView.tableCourse.getValueAt(rowSelected, 1).toString());
                courseView.txtCycle.setText(courseView.tableCourse.getValueAt(rowSelected, 2).toString());
                courseView.txtCredits.setText(courseView.tableCourse.getValueAt(rowSelected, 3).toString());
                courseView.txtHours.setText(courseView.tableCourse.getValueAt(rowSelected, 4).toString());

                courseView.btnSave.setEnabled(false);
                courseView.btnUpdate.setEnabled(true);
                courseView.btnDelete.setEnabled(true);

                courseView.txtCode.setEnabled(false);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (courseView.btnSave == e.getSource()) {
            addCourse();
        } else if (courseView.btnUpdate == e.getSource()) {
            updateCourse();
        } else if (courseView.btnDelete == e.getSource()) {
            if (JOptionPane.showConfirmDialog(courseView, "Desea Eliminar el Curso?") == 0) {
                deleteCourse();
            } else {
                JOptionPane.showMessageDialog(courseView, "No procede");
                courseView.btnSave.setEnabled(true);
                courseView.btnUpdate.setEnabled(false);
                courseView.btnDelete.setEnabled(false);
                courseView.txtCode.setEnabled(true);
                clearTextFields();
            }
        }
    }

    private void addCourse() {
        int code = Integer.parseInt(courseView.txtCode.getText());
        if (compareCodeCourse(code)) {
            String asignatura = courseView.txtSubject.getText();
            int cycle = Integer.parseInt(courseView.txtCycle.getText());
            int credits = Integer.parseInt(courseView.txtCredits.getText());
            int hours = Integer.parseInt(courseView.txtHours.getText());
            Course course = new Course(code, asignatura, cycle, credits, hours);
            courseList.add(course);
            
            try {
                writeCourse();
            } catch (IOException ex) {
                System.out.println("Error E/S " + ex.getMessage());
            }
            
            sortedByCode();
            showDataOnTable();
            clearTextFields();
        } else {
            JOptionPane.showMessageDialog(courseView, "El código del curso no debe ser el mismo");
        }
    }

    private void updateCourse() {
        int code = Integer.parseInt(courseView.txtCode.getText());
        String subject = courseView.txtSubject.getText();
        int cycle = Integer.parseInt(courseView.txtCycle.getText());
        int credits = Integer.parseInt(courseView.txtCredits.getText());
        int hours = Integer.parseInt(courseView.txtHours.getText());

        int indexCourse = searchIndex(code);
        
        Course course = courseList.get(indexCourse);
        course.setCourseCode(code);
        course.setSubject(subject);
        course.setCycle(cycle);
        course.setCredits(credits);
        course.setHours(hours);
        
        courseList.set(indexCourse, course);

        try {
            writeCourse();
        } catch (IOException ex) {
            System.out.println("Error E/S " + ex.getMessage());
        }
        
        showDataOnTable();
        clearTextFields();

        courseView.btnSave.setEnabled(true);
        courseView.btnUpdate.setEnabled(false);
        courseView.btnDelete.setEnabled(false);
        
        courseView.txtCode.setEnabled(true);
    }

    private void deleteCourse() {
        //Eliminar el curso si no tiene alumnos matriculados al curso
        int codeCourse = Integer.parseInt(courseView.txtCode.getText());
        List<Enrollment> enrolledList = EnrollmentModel.getEnrollmentList();
        if (compareStudentBelongToCourse(enrolledList, codeCourse)) {
            int indexCourse = searchIndex(codeCourse);
            courseList.remove(indexCourse);
            
            try {
                writeCourse();
            } catch (IOException ex) {
                System.out.println("Error E/S " + ex.getMessage());
            }
            
            showDataOnTable();

            courseView.btnSave.setEnabled(true);
            courseView.btnUpdate.setEnabled(false);
            courseView.btnDelete.setEnabled(false);
            courseView.txtCode.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(courseView, "El curso no se puede eliminar por tener alumnos matriculados");

            courseView.btnSave.setEnabled(true);
            courseView.btnUpdate.setEnabled(false);
            courseView.btnDelete.setEnabled(false);
            courseView.txtCode.setEnabled(true);
        }
        clearTextFields();
    }

    public void showDataOnTable() {
        DefaultTableModel newModel = new DefaultTableModel();
        Object columns[] = {"Código", "Asignatura", "Ciclo", "Créditos", "Horas"};
        for (Object column : columns) {
            newModel.addColumn(column);
        }
        if (!courseList.isEmpty()) {
            Object rows[] = new Object[columns.length];
            for (Course curso : courseList) {
                rows[0] = curso.getCourseCode();
                rows[1] = curso.getSubject();
                rows[2] = curso.getCycle();
                rows[3] = curso.getCredits();
                rows[4] = curso.getHours();
                newModel.addRow(rows);
            }
        }
        courseView.tableCourse.setModel(newModel);
    }

    private void clearTextFields() {
        courseView.txtCode.setText("");
        courseView.txtSubject.setText("");
        courseView.txtCycle.setText("");
        courseView.txtCredits.setText("");
        courseView.txtHours.setText("");
    }

    private boolean compareCodeCourse(int codeCourse) {
        return courseList
                .stream()
                .noneMatch((course) -> (course.getCourseCode() == codeCourse));  //Si encuentra = false
    }

    private int searchIndex(int code) {
        int index = -1;
        int bound = courseList.size();
        for (int userInd = 0; userInd < bound; userInd++) {
            if (courseList.get(userInd).getCourseCode() == code) {
                index = userInd;
                break;
            }
        }
        return index;
    }

    private boolean compareStudentBelongToCourse(List<Enrollment> enrolledList, int codeCourse) {
        int cnt = 0;
        cnt = enrolledList
                .stream()
                .filter((enrollment) -> (codeCourse == enrollment.getCourseCode()))
                .map((item) -> 1)
                .reduce(cnt, Integer::sum);
        return cnt == 0;
    }
    
    private void sortedByCode() {
        courseList
                .sort(Comparator.comparing(code -> code.getCourseCode()));
    }
    
    private void writeCourse() throws IOException {
        PrintWriter write = new PrintWriter(
                new FileWriter("cursos.txt")
        );
        String line;
        for(Course course: courseList) {
            line = String.format("%s;%s;%s;%s;%s\n", course.getCourseCode(),
                                    course.getSubject(), course.getCycle(),
                                    course.getCredits(), course.getHours());
            write.write(line);
        }
        write.close();
    }
    
    public void loadCourses() {
        String list[];
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader("cursos.txt"));
            while ((line = br.readLine()) != null) {
                list = line.split(";");
                Course course = new Course(Integer.parseInt(list[0]), list[1], 
                                Integer.parseInt(list[2]), Integer.parseInt(list[3]),
                                Integer.parseInt(list[4])
                );
                courseList.add(course);
            }
            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error E/S: " + e.getMessage());
            
        } catch (IOException ex) {
            System.out.println("Error E/S: " + ex.getMessage());
        }
    }
    
    public void clearListStudents() {
        courseList.clear();
    }

    private final CourseView courseView;
    private List<Course> courseList;
}
