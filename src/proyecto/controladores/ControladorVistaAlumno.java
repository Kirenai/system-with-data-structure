package proyecto.controladores;

import java.awt.event.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;
import proyecto.modelos.Alumno;
import proyecto.vistas.AlumnoVista;

public class ControladorVistaAlumno implements ActionListener {

    public ControladorVistaAlumno(AlumnoVista viewAlumno) {
        this.viewAlumno = viewAlumno;

        addEvents();
    }

    private void addEvents() {
        viewAlumno.btnGuardar.addActionListener(this);
        viewAlumno.btnMostrar.addActionListener(this);
        viewAlumno.tableAlumno.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int rowSelected = viewAlumno.tableAlumno.rowAtPoint(e.getPoint());
                
                viewAlumno.txtCodigo.setText(viewAlumno.tableAlumno.getValueAt(rowSelected, 0).toString());
                viewAlumno.txtNombres.setText(viewAlumno.tableAlumno.getValueAt(rowSelected, 1).toString());
                viewAlumno.txtApellidos.setText(viewAlumno.tableAlumno.getValueAt(rowSelected, 2).toString());
                viewAlumno.txtDNI.setText(viewAlumno.tableAlumno.getValueAt(rowSelected, 3).toString());
                viewAlumno.txtEdad.setText(viewAlumno.tableAlumno.getValueAt(rowSelected, 4).toString());
                viewAlumno.txtCelular.setText(viewAlumno.tableAlumno.getValueAt(rowSelected, 5).toString());
                viewAlumno.txtEstado.setText(viewAlumno.tableAlumno.getValueAt(rowSelected, 6).toString());
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == viewAlumno.btnGuardar) {
            try {
                addAlumno();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        } else if (e.getSource() == viewAlumno.btnMostrar) {
            System.out.println(listAlumno);
        }
    }

    private void addAlumno() throws Exception {
        int codigo = codigoCorrelativo();
        String nombres = viewAlumno.txtNombres.getText();
        String apellidos = viewAlumno.txtApellidos.getText();
        String dni = viewAlumno.txtDNI.getText();
        if (!verificarDNI(listAlumno, dni)) {
            throw new Exception("Error dni duplicado");
        }
        int edad = Integer.parseInt(viewAlumno.txtEdad.getText());
        int celular = Integer.parseInt(viewAlumno.txtCelular.getText());
        int estado = Integer.parseInt(viewAlumno.txtEstado.getText());
        Alumno alumno = new Alumno(codigo, nombres, apellidos, dni, edad, celular, estado);
        listAlumno.add(alumno);

        showDataOnTable();
    }

    private int codigoCorrelativo() {
        if (listAlumno.isEmpty()) {
            return 202010000;
        } else {
            return listAlumno.get(listAlumno.size() - 1).getCodAlumno() + 1;
        }
    }

    private boolean verificarDNI(List<Alumno> listAlumnos, String dni) {
        return listAlumnos.stream().noneMatch((alumno) -> (alumno.getDni().equals(dni)));
    }

    public void showDataOnTable() {
        DefaultTableModel newModel = new DefaultTableModel();
        Object columns[] = {"Código", "Nombres", "Apellidos", "DNI", "Edad",
            "Celular", "Estado"};
        for (Object column : columns) {
            newModel.addColumn(column);
        }
        if (!listAlumno.isEmpty()) {
            Object rows[] = new Object[columns.length];
            for (Alumno alumno : listAlumno) {
                rows[0] = alumno.getCodAlumno();
                rows[1] = alumno.getNombre();
                rows[2] = alumno.getApellidos();
                rows[3] = alumno.getDni();
                rows[4] = alumno.getEdad();
                rows[5] = alumno.getCelular();
                rows[6] = alumno.getEstado();
                newModel.addRow(rows);
            }
        }
        viewAlumno.tableAlumno.setModel(newModel);
    }

    private final AlumnoVista viewAlumno;
    private static List<Alumno> listAlumno = new ArrayList<>();
}
