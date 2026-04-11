/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PROVIDER;



import static CONFIG.config.connectDB;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

public class PAppointments extends javax.swing.JFrame {

    public PAppointments() {
        // 1. KINI DAPAT UNAHA - I-initialize ang tanan components (buttons, tables, etc.)
        initComponents();
        
        // 2. Define colors
         java.awt.Color navy = new java.awt.Color(35, 66, 106);
    java.awt.Color brown = new java.awt.Color(106, 75, 35);
    java.awt.Color warningRed = new java.awt.Color(180, 40, 40);
    java.awt.Color refreshColor = new java.awt.Color(40, 100, 40);
        
        // 3. Apply styles (Segurado na nga dili null ang buttons kay humana ang initComponents)
        applyAdminButtonStyle(jButton1, navy); 
        styleAdminButtons(confirm, navy);
    styleAdminButtons(completed, navy);
    styleAdminButtons(delete, warningRed); // Pula na ang Delete button
    styleAdminButtons(jButton2, new java.awt.Color(60, 120, 60));
        
        // 4. Modernize Table Design
        tableStyle();
        
        // 5. Load Data
        loadAppointments();

        // 6. Setup Table Editor (Dropdown Status)
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{
            "Pending", "Approved", "Completed", "Cancelled"
        });
        jTable1.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(statusCombo));

        // 7. Auto-save listener
         jTable1.getTableHeader().setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer() {
    @Override
    public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        javax.swing.JLabel label = (javax.swing.JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        label.setBackground(new java.awt.Color(35, 66, 106)); // Navy Blue
        label.setForeground(java.awt.Color.WHITE); // White Text
        label.setHorizontalAlignment(javax.swing.JLabel.CENTER); // Center align labels
        label.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, java.awt.Color.WHITE)); // Subtle grid sa header
        return label;
    }
});
        
        class CircleIcon implements javax.swing.Icon {
    private java.awt.Image image;
    private int size;
    public CircleIcon(java.awt.Image image, int size) { this.image = image; this.size = size; }
    @Override
    public void paintIcon(java.awt.Component c, java.awt.Graphics g, int x, int y) {
        java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setClip(new java.awt.geom.Ellipse2D.Double(x, y, size, size));
        g2.drawImage(image, x, y, size, size, null);
        g2.dispose();
    }
    @Override public int getIconWidth() { return size; }
    @Override public int getIconHeight() { return size; }
}
    }

    // --- DESIGN METHODS ---

    private void tableStyle() {
    // 1. Header Styling (Kini ang mo-usab sa user_id, user_name, etc. into Blue)
    jTable1.getTableHeader().setFont(new java.awt.Font("Segoe UI Bold", 0, 13));
    jTable1.getTableHeader().setBackground(new java.awt.Color(35, 66, 106)); // Navy Blue
    jTable1.getTableHeader().setForeground(java.awt.Color.WHITE); // Puti nga text
    jTable1.getTableHeader().setOpaque(false);
    jTable1.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 40)); // Mas baga gamay nga header

    // 2. Row Styling
    jTable1.setRowHeight(50); // Mas hayahay nga rows
    jTable1.getColumnModel().getColumn(0).setPreferredWidth(60);
    jTable1.setFont(new java.awt.Font("Segoe UI", 0, 12));
    jTable1.setGridColor(new java.awt.Color(230, 230, 230)); 
    jTable1.setShowVerticalLines(false); // Modern look (horizontal lines ra)

    // 3. Selection Color (Inig click nimo sa row)
    jTable1.setSelectionBackground(new java.awt.Color(220, 230, 250)); // Light Blue highlight
    jTable1.setSelectionForeground(new java.awt.Color(35, 66, 106)); // Navy Blue text

    // 4. ScrollPane Fix
    jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 240, 240)));
    jScrollPane1.getViewport().setBackground(java.awt.Color.WHITE);
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

    private void applyAdminButtonStyle(JButton btn, Color baseColor) {
        if (btn == null) return; // Safety check para sa NullPointerException
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

    // --- DATABASE METHODS ---

    public void loadAppointments() {
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        model.setColumnIdentifiers(new String[]{"Booking ID", "Customer", "Date", "Status", "Total", "Address"});

        String sql = "SELECT b.b_id, u.user_name, b.b_date, b.b_status, b.b_total, b.b_address " +
                     "FROM tbl_bookings b JOIN tbl_users u ON b.u_id = u.user_id WHERE b.p_id = ?";

        try (Connection conn = connectDB(); PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, CONFIG.Session.getUserId());
            java.sql.ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("b_id"), rs.getString("user_name"), rs.getString("b_date"),
                    rs.getString("b_status"), rs.getDouble("b_total"), rs.getString("b_address")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateStatusDirectly(int id, String status) {
        String sql = "UPDATE tbl_bookings SET b_status = ? WHERE b_id = ?";
        try (Connection conn = connectDB(); PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, status);
            pst.setInt(2, id);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Status updated to " + status);
            loadAppointments();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Update Error: " + e.getMessage());
        }
    }

    private void updateBookingStatus(int row) {
        int bookingId = (int) jTable1.getValueAt(row, 0);
        String newStatus = jTable1.getValueAt(row, 3).toString();
        updateStatusDirectly(bookingId, newStatus);
    }
    
    
    
    
    // ... i-retain ang imong initComponents() ug button action events sa ubos ...
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        confirm = new javax.swing.JButton();
        completed = new javax.swing.JButton();
        delete = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(35, 66, 106));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Back to Home");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 250, 33));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/Blue White Modern Minimalist Interior Designer Personal Branding Logo(1)(1).jpg"))); // NOI18N
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 290, 620));

        jPanel3.setBackground(new java.awt.Color(35, 66, 106));

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("My Appointments");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                .addGap(24, 24, 24))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 50, 460, -1));

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

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 240, 490, 330));

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });
        jPanel1.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 200, 130, 30));

        jButton2.setText("Search");
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 200, 90, 30));

        confirm.setText("Confirm");
        confirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmActionPerformed(evt);
            }
        });
        jPanel1.add(confirm, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 200, -1, -1));

        completed.setText("Completed");
        completed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                completedActionPerformed(evt);
            }
        });
        jPanel1.add(completed, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 200, -1, -1));

        delete.setText("Delete");
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });
        jPanel1.add(delete, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 200, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 899, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 688, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
         providerdashboard pd = new providerdashboard();
        pd.setVisible(true);
        pd.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1KeyReleased

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
       int row = jTable1.getSelectedRow();
    if (row < 0) {
        JOptionPane.showMessageDialog(null, "Please select an appointment to delete.");
        return;
    }

    int bookingId = (int) jTable1.getValueAt(row, 0);
    int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to DELETE Booking #" + bookingId + "?\nThis cannot be undone.", "Warning", JOptionPane.YES_NO_OPTION);

    if (response == JOptionPane.YES_OPTION) {
        String sql = "DELETE FROM tbl_bookings WHERE b_id = ?";
        try (Connection conn = connectDB(); PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, bookingId);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Booking Deleted Successfully.");
            loadAppointments(); // I-refresh ang table
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Delete Error: " + e.getMessage());
        }
    }
    }//GEN-LAST:event_deleteActionPerformed

    private void confirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmActionPerformed
       int row = jTable1.getSelectedRow();
    if (row < 0) {
        JOptionPane.showMessageDialog(null, "Please select an appointment to confirm.");
        return;
    }

    int bookingId = (int) jTable1.getValueAt(row, 0);
    int response = JOptionPane.showConfirmDialog(null, "Approve Booking #" + bookingId + "?", "Confirm", JOptionPane.YES_NO_OPTION);

    if (response == JOptionPane.YES_OPTION) {
        updateStatusDirectly(bookingId, "APPROVED");
    }
    }//GEN-LAST:event_confirmActionPerformed

    private void completedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_completedActionPerformed
        int row = jTable1.getSelectedRow();
    if (row < 0) {
        JOptionPane.showMessageDialog(null, "Please select an appointment to mark as completed.");
        return;
    }

    int bookingId = (int) jTable1.getValueAt(row, 0);
    int response = JOptionPane.showConfirmDialog(null, "Mark Booking #" + bookingId + " as Completed?", "Confirm", JOptionPane.YES_NO_OPTION);

    if (response == JOptionPane.YES_OPTION) {
        updateStatusDirectly(bookingId, "Completed");
    }
    }//GEN-LAST:event_completedActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PAppointments.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PAppointments.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PAppointments.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PAppointments.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PAppointments().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton completed;
    private javax.swing.JButton confirm;
    private javax.swing.JButton delete;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
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
