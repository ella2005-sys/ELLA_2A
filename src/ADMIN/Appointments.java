/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ADMIN;

import javax.swing.table.DefaultTableModel;
import CONFIG.config;
import USER.BookingForm;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingConstants;


public class Appointments extends javax.swing.JFrame {
    
    config cfg = new config();

   
    public Appointments() {
        initComponents();
        new CONFIG.config().sessionGuard(this);
        setLocationRelativeTo(null);
    
        // 1. SETUP TABLE FIRST
    setupTable();
    
    // 2. COLORS
    java.awt.Color navy = new java.awt.Color(35, 66, 106);
    java.awt.Color brown = new java.awt.Color(106, 75, 35);
    java.awt.Color warningRed = new java.awt.Color(180, 40, 40);
    java.awt.Color refreshColor = new java.awt.Color(40, 100, 40); 

    // 3. APPLY STYLES (Diri nimo i-set ang design sa buttons)
    applyAdminButtonStyle(jButton1, navy);    // Back to Home
   
    
    styleAdminButtons(Edit, navy);
    styleAdminButtons(Delete, warningRed); // Pula na ang Delete button
    styleAdminButtons(jButton5, new java.awt.Color(60, 120, 60));// Search Button (Green)

    // 4. LOAD DATA
    displayAllAppointments();
    
     
    }
    
     private void styleAdminButtons(javax.swing.JButton btn, java.awt.Color baseColor) {
    btn.setBorderPainted(false);
    btn.setFocusPainted(false);
    btn.setContentAreaFilled(false);
    btn.setOpaque(true);
    btn.setBackground(baseColor);
    btn.setForeground(java.awt.Color.WHITE);
    btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    btn.setFont(new java.awt.Font("Segoe UI Bold", java.awt.Font.PLAIN, 13));

    btn.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            btn.setBackground(baseColor.brighter()); // Mo-light inig tapat sa mouse
        }
        @Override
        public void mouseExited(java.awt.event.MouseEvent evt) {
            btn.setBackground(baseColor); // Mobalik sa original color
        }
    });
}

    public void displayAllAppointments() {
    try {
        CONFIG.config conf = new CONFIG.config();
        java.sql.Connection conn = conf.connectDB();
        
        // SQL JOIN para makita ang tinuod nga names sa table
        String sql = "SELECT b.b_id, u.user_name, s.s_name, b.b_date, b.b_address, b.b_total, b.b_status " +
                     "FROM tbl_bookings b " +
                     "JOIN tbl_users u ON b.u_id = u.user_id " +
                     "JOIN tbl_services s ON b.s_id = s.s_id";
        
        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
        java.sql.ResultSet rs = pst.executeQuery();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("b_id"),
                rs.getString("user_name"), // Name sa Customer
                rs.getString("s_name"),    // Name sa Service
                rs.getString("b_date"),
                rs.getString("b_address"),
                "₱" + rs.getString("b_total"),
                rs.getString("b_status")
            });
        }
    } catch (Exception e) {
        System.out.println("Display Error: " + e.getMessage());
    }
}

    private void setupTable() {
    String[] headers = {"ID", "Customer Name", "Service", "Date", "Address", "Amount", "Status"};
    DefaultTableModel model = new DefaultTableModel(null, headers) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Dili ma-edit ang cells diretso sa table
        }
    };
    jTable1.setModel(model);
    
    // Aesthetics - Para "Modern" ang look
    jTable1.setRowHeight(35); // Mas dako gamay para dili pilit
    jTable1.setShowGrid(false); // Tangtangon ang grid lines para limpyo
    jTable1.setIntercellSpacing(new java.awt.Dimension(0, 0));
    
    // Header Style
    jTable1.getTableHeader().setBackground(new java.awt.Color(35, 66, 106));
    jTable1.getTableHeader().setForeground(java.awt.Color.WHITE);
    jTable1.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
    jTable1.getTableHeader().setReorderingAllowed(false); // Dili ma-drag ang columns
}

private void applyAdminButtonStyle(JButton btn, Color baseColor) {
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setBackground(baseColor);
        btn.setForeground(Color.WHITE);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                btn.setBackground(baseColor.brighter());
                btn.setBorder(BorderFactory.createMatteBorder(0, 5, 0, 0, Color.WHITE));
            }
            @Override
            public void mouseExited(MouseEvent evt) {
                btn.setBackground(baseColor);
                btn.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
            }
        });
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        Edit = new javax.swing.JButton();
        Delete = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(35, 66, 106));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/Blue White Modern Minimalist Interior Designer Personal Branding Logo(1)(1).jpg"))); // NOI18N
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 13, -1, 97));

        jButton1.setBackground(new java.awt.Color(35, 66, 106));
        jButton1.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Back to Home");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 260, 40));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 309, 620));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 270, 560, 380));

        jPanel3.setBackground(new java.awt.Color(35, 66, 106));

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 0, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("APPOINTMENTS TABLE");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(70, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(59, 59, 59))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 40, 550, -1));

        Edit.setText("Update");
        Edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditActionPerformed(evt);
            }
        });
        jPanel1.add(Edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 230, 100, -1));

        Delete.setText("Delete");
        Delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteActionPerformed(evt);
            }
        });
        jPanel1.add(Delete, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 230, 100, -1));
        jPanel1.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 230, 100, -1));

        jButton5.setText("Search");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 230, 100, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 955, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 691, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       admindashboard ad = new admindashboard();
    ad.setVisible(true);
    this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
      String find = jTextField1.getText();
    try {
        CONFIG.config conf = new CONFIG.config();
        java.sql.Connection conn = conf.connectDB();
        
        String sql = "SELECT b.b_id, u.user_name, s.s_name, b.b_date, b.b_address, b.b_total, b.b_status " +
             "FROM tbl_bookings b " +
             "JOIN tbl_users u ON b.u_id = u.user_id " +
             "JOIN tbl_services s ON b.s_id = s.s_id"; // <-- Siguruha nga 's_id' jud ang naa sa tbl_services
        
        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, "%" + find + "%");
        pst.setString(2, "%" + find + "%");
        pst.setString(3, "%" + find + "%");
        
        java.sql.ResultSet rs = pst.executeQuery();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("b_id"),
                rs.getString("user_name"),
                rs.getString("s_name"),
                rs.getString("b_date"),
                rs.getString("b_address"),
                "₱" + rs.getString("b_total"),
                rs.getString("b_status")
            });
        }
    } catch (Exception e) {
        System.out.println("Search Error: " + e.getMessage());
    }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void EditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditActionPerformed
      int row = jTable1.getSelectedRow();
    if (row == -1) {
        javax.swing.JOptionPane.showMessageDialog(this, "Palihug pili og booking sa table!");
        return;
    }
    
    String id = jTable1.getValueAt(row, 0).toString();
    String currentStatus = jTable1.getValueAt(row, 6).toString();
    
    // Listahan sa Status choices
    String[] options = {"APPROVED", "COMPLETED", "CANCELLED"};
    int choice = javax.swing.JOptionPane.showOptionDialog(this, 
            "Update status for Booking ID: " + id, 
            "Update Status", 
            javax.swing.JOptionPane.DEFAULT_OPTION, 
            javax.swing.JOptionPane.QUESTION_MESSAGE, 
            null, options, options[0]);

    if (choice != -1) { // Kung naay gipili
        String newStatus = options[choice];
        try {
            CONFIG.config conf = new CONFIG.config();
            String sql = "UPDATE tbl_bookings SET b_status = ? WHERE b_id = ?";
            java.sql.Connection conn = conf.connectDB();
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, newStatus);
            pst.setString(2, id);
            
            pst.executeUpdate();
            javax.swing.JOptionPane.showMessageDialog(this, "Status updated to " + newStatus);
            displayAllAppointments(); // Refresh table
        } catch (Exception e) {
            System.out.println("Update Error: " + e.getMessage());
        }
    }
    }//GEN-LAST:event_EditActionPerformed

    private void DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DeleteActionPerformed

    /**
     * @param args the command line arguments
     */
   public static void main(String args[]) {
    // ... (Keep your existing Look and Feel try-catch block here) ...

    /* Create and display the form */
    java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            // Check if user is logged in using your Session class logic
            if (!CONFIG.Session.isLoggedIn()) {
                javax.swing.JOptionPane.showMessageDialog(
                    null, 
                    "Please Login First!", 
                    "Access Denied", 
                    javax.swing.JOptionPane.ERROR_MESSAGE
                );

                // Redirect to login
                MAIN.login loginFrame = new MAIN.login();
                loginFrame.setVisible(true);
                loginFrame.pack();
                loginFrame.setLocationRelativeTo(null);
            } else {
                // If logged in, show the Appointments window
                new Appointments().setVisible(true);
            }
        }
    });
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Delete;
    private javax.swing.JButton Edit;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
