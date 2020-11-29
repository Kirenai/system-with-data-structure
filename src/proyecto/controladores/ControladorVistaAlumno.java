package proyecto.controladores;

import java.awt.event.*;
import java.util.*;
import javax.swing.JOptionPane;
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
        viewAlumno.btnActualizar.addActionListener(this);
        viewAlumno.btnEliminar.addActionListener(this);
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

                viewAlumno.btnActualizar.setEnabled(true);
                viewAlumno.btnGuardar.setEnabled(false);
                viewAlumno.txtDNI.setEnabled(false);
                viewAlumno.btnEliminar.setEnabled(true);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == viewAlumno.btnGuardar) {
            try {
                addAlumno();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(viewAlumno, ex.getMessage());
            }
        } else if (e.getSource() == viewAlumno.btnMostrar) {
            System.out.println(listAlumno);
        } else if (e.getSource() == viewAlumno.btnActualizar) {
            updateAlumno();
            viewAlumno.btnGuardar.setEnabled(true);
            viewAlumno.btnActualizar.setEnabled(false);
        } else if (e.getSource() == viewAlumno.btnEliminar) {
            try {
                if (JOptionPane.showConfirmDialog(viewAlumno, "Desea Eliminar al alumno?") == 0) {
                    deleteAlumno();
                } else {
                    JOptionPane.showMessageDialog(viewAlumno, "No procede");
                    viewAlumno.btnGuardar.setEnabled(true);
                    viewAlumno.btnActualizar.setEnabled(false);
                    viewAlumno.btnEliminar.setEnabled(false);
                    clearTextFields();
                }
            } catch (Exception ex) {
                clearTextFields();
                viewAlumno.btnGuardar.setEnabled(true);
                viewAlumno.btnActualizar.setEnabled(false);
                viewAlumno.btnEliminar.setEnabled(false);
                viewAlumno.txtDNI.setEnabled(true);
                JOptionPane.showMessageDialog(viewAlumno, ex.getMessage());
            }
        }
    }

    //Agregando alumno
    private void addAlumno() throws Exception {
        int codigo = codigoCorrelativo();
        String nombres = viewAlumno.txtNombres.getText();
        String apellidos = viewAlumno.txtApellidos.getText();
        String dni = viewAlumno.txtDNI.getText();
        if (!verificarDNI(dni)) {
            throw new Exception("Error dni duplicado");
        }
        int edad = Integer.parseInt(viewAlumno.txtEdad.getText());
        int celular = Integer.parseInt(viewAlumno.txtCelular.getText());
        int estado = Integer.parseInt(viewAlumno.txtEstado.getText());
        Alumno alumno = new Alumno(codigo, nombres, apellidos, dni, edad, celular, estado);
        listAlumno.add(alumno);

        showDataOnTable();
        clearTextFields();
    }

    /**
     *
     * @return Codigo correlativo 20200000 + 1 por alumno
     */
    private int codigoCorrelativo() {
        if (listAlumno.isEmpty()) {
            return 202010000;
        } else {
            return listAlumno.get(listAlumno.size() - 1).getCodAlumno() + 1;
        }
    }

    /**
     *
     * @param dni Argumento a compar
     * @return Retorna false si encuentra un DNI repetido en la BBDD fake de
     * alumno
     */
    private boolean verificarDNI(String dni) {
        return listAlumno.stream().noneMatch((alumno) -> (alumno.getDni().equals(dni)));
    }

    private void updateAlumno() {
        int codigo = Integer.parseInt(viewAlumno.txtCodigo.getText());
        String nombres = viewAlumno.txtNombres.getText();
        String apellidos = viewAlumno.txtApellidos.getText();
        String dni = viewAlumno.txtDNI.getText();
        int edad = Integer.parseInt(viewAlumno.txtEdad.getText());
        int celular = Integer.parseInt(viewAlumno.txtCelular.getText());
        int estado = Integer.parseInt(viewAlumno.txtEstado.getText());

        Alumno alumno = new Alumno(codigo, nombres, apellidos, dni, edad, celular, estado);

        int indexAlumno = searchIndex(codigo);
        listAlumno.set(indexAlumno, alumno);

        showDataOnTable();
        clearTextFields();

        viewAlumno.txtDNI.setEnabled(true);
        viewAlumno.btnGuardar.setEnabled(true);
        viewAlumno.btnEliminar.setEnabled(false);
    }

    private void deleteAlumno() throws Exception {
        int estado = Integer.parseInt(viewAlumno.txtEstado.getText());
        if (estado == 0) {
            int indexAlumno = searchIndex(Integer.parseInt(viewAlumno.txtCodigo.getText()));
            listAlumno.remove(indexAlumno);

            showDataOnTable();
            clearTextFields();

            viewAlumno.txtDNI.setEnabled(true);

            viewAlumno.btnGuardar.setEnabled(true);
            viewAlumno.btnEliminar.setEnabled(false);
            viewAlumno.btnActualizar.setEnabled(false);

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

    private int searchIndex(int codigo) {
        int index = -1;
        int bound = listAlumno.size();
        for (int userInd = 0; userInd < bound; userInd++) {
            if (listAlumno.get(userInd).getCodAlumno() == codigo) {
                index = userInd;
                break;
            }
        }
        return index;
    }

    private void clearTextFields() {
        viewAlumno.txtCodigo.setText("");
        viewAlumno.txtNombres.setText("");
        viewAlumno.txtApellidos.setText("");
        viewAlumno.txtDNI.setText("");
        viewAlumno.txtEdad.setText("");
        viewAlumno.txtCelular.setText("");
        viewAlumno.txtEstado.setText("");
    }

    public static List<Alumno> getListAlumno() {
        return listAlumno;
    }

    private final AlumnoVista viewAlumno;
    private static List<Alumno> listAlumno = new ArrayList<>();
}
