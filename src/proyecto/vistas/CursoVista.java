/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto.vistas;

/**
 *
 * @author Kire
 */
public class CursoVista extends javax.swing.JFrame {

    /**
     * Creates new form CursoVista
     */
    public CursoVista() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        txtAsignatura = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtCiclo = new javax.swing.JTextField();
        txtCreditos = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtHoras = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableCurso = new javax.swing.JTable();
        btnGuardar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnMostrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setPreferredSize(new java.awt.Dimension(843, 390));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Adicionar");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 30, -1, -1));

        jLabel3.setText("Código");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 90, -1, -1));
        jPanel1.add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 80, 110, -1));
        jPanel1.add(txtAsignatura, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 125, 110, -1));

        jLabel4.setText("Asignatura");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 130, -1, -1));

        jLabel5.setText("Ciclo");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 170, -1, -1));
        jPanel1.add(txtCiclo, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 165, 110, -1));
        jPanel1.add(txtCreditos, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 205, 110, -1));

        jLabel6.setText("N° de creditos");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 210, -1, -1));
        jPanel1.add(txtHoras, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 245, 110, -1));

        jLabel7.setText("Cantidad de horas");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 250, -1, -1));

        tableCurso.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tableCurso);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 20, -1, 370));

        btnGuardar.setText("Guardar");
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 300, -1, -1));

        btnActualizar.setText("Actualizar");
        jPanel1.add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 300, -1, -1));

        btnEliminar.setText("Eliminar");
        jPanel1.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 300, -1, -1));

        btnMostrar.setText("Mostrar");
        jPanel1.add(btnMostrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 90, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 890, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnActualizar;
    public javax.swing.JButton btnEliminar;
    public javax.swing.JButton btnGuardar;
    public javax.swing.JButton btnMostrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable tableCurso;
    public javax.swing.JTextField txtAsignatura;
    public javax.swing.JTextField txtCiclo;
    public javax.swing.JTextField txtCodigo;
    public javax.swing.JTextField txtCreditos;
    public javax.swing.JTextField txtHoras;
    // End of variables declaration//GEN-END:variables
}
