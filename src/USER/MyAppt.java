
package USER;

import CONFIG.config;
import CONFIG.Session;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

public class MyAppt extends javax.swing.JFrame {

    CONFIG.config cfg = new CONFIG.config();
    
    public MyAppt() {
        initComponents();
        setupTable(); // Kini ang mo-apply sa tanang design
        loadMyAppointments();
        setLocationRelativeTo(null);
        
        Color navy = new Color(35, 66, 106);
        
        applyAdminButtonStyle(jButton1, navy);    // Users
        applyAdminButtonStyle(viewdetails, navy); 
        applyAdminButtonStyle(cancel, new Color(150, 0, 0));
       
     
        
        
        // I-setup ang choices
    jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All Status", "Pending", "Approved" }));

// I-apply ang Navy Theme design
    applyComboBoxStyle(jComboBox1);
    
    // 2. DESIGN SA MGA BUTTONS
    applyAdminButtonStyle(search, new Color(35, 66, 106));
    applyAdminButtonStyle(cancel, new Color(35, 66, 106));
    applyAdminButtonStyle(viewdetails, new Color(35, 66, 106));

    // 3. SEARCH AS YOU TYPE (Document Listener)
    // Inig type nimo sa jTextField1, mo-trigger dayon ang searchTable()
    jTextField1.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
        @Override
        public void insertUpdate(javax.swing.event.DocumentEvent e) { searchTable(); }
        @Override
        public void removeUpdate(javax.swing.event.DocumentEvent e) { searchTable(); }
        @Override
        public void changedUpdate(javax.swing.event.DocumentEvent e) { searchTable(); }
    });
    }
    

  public void loadMyAppointments() {
    try {
        CONFIG.config conf = new CONFIG.config();
        int userId = CONFIG.Session.getUserId();

        // 1. Siguruha nga ang SQL columns nag-sunod sa imong JTable Headers
        // Order: b_id (ID), s_name (Service), p_id (Provider), b_date (Date), b_address (Address), b_total (Amount), b_status (Status)
        String sql = "SELECT b.b_id, s.s_name, b.p_id, b.b_date, b.b_address, b.b_total, b.b_status " +
                     "FROM tbl_bookings b " +
                     "JOIN tbl_services s ON b.s_id = s.s_id " +
                     "WHERE b.u_id = " + userId;

        java.sql.ResultSet rs = conf.getData(sql);
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tbl_appointments.getModel();
        model.setRowCount(0); 

        while (rs.next()) {
            // 2. KINI ANG CRITICAL: Kinahanglan ang pag-add sa row mo-match sa sequence sa imong JTable columns
            model.addRow(new Object[]{
                rs.getInt("b_id"),          // Column 0: ID
                rs.getString("s_name"),     // Column 1: Service
                rs.getInt("p_id"),          // Column 2: Provider
                rs.getString("b_date"),     // Column 3: Date
                rs.getString("b_address"),  // Column 4: Address (Kani ang gi-input sa user)
                rs.getDouble("b_total"),    // Column 5: Amount
                rs.getString("b_status")    // Column 6: Status (Pending/Approved/Cancelled)
            });
        }
        rs.close();

    } catch (Exception e) {
        System.out.println("Load Error: " + e.getMessage());
    }
}

private void searchTable() {
    String searchText = jTextField1.getText().toLowerCase();
    String statusFilter = jComboBox1.getSelectedItem().toString();

    String sql = "SELECT tbl_bookings.b_id, tbl_services.s_name, " +
                 "IFNULL(tbl_users.user_name, 'No Provider Assigned') AS provider_name, " +
                 "tbl_bookings.b_date, tbl_bookings.b_address, tbl_bookings.b_total, tbl_bookings.b_status " +
                 "FROM tbl_bookings " +
                 "JOIN tbl_services ON tbl_bookings.s_id = tbl_services.s_id " +
                 "LEFT JOIN tbl_users ON tbl_services.provider_id = tbl_users.user_id " +
                 "WHERE tbl_bookings.u_id = ? AND " +
                 "(LOWER(tbl_services.s_name) LIKE ? OR LOWER(tbl_bookings.b_address) LIKE ?)";

    if (!statusFilter.equals("All Status")) {
        sql += " AND tbl_bookings.b_status = ?";
    }

    try (Connection conn = CONFIG.config.connectDB();
         PreparedStatement pst = conn.prepareStatement(sql)) {

        pst.setInt(1, CONFIG.Session.getUserId());
        pst.setString(2, "%" + searchText + "%");
        pst.setString(3, "%" + searchText + "%");

        if (!statusFilter.equals("All Status")) {
            pst.setString(4, statusFilter);
        }

        try (ResultSet rs = pst.executeQuery()) {
            DefaultTableModel model = (DefaultTableModel) tbl_appointments.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("b_id"),
                    rs.getString("s_name"),
                    rs.getString("provider_name"),
                    rs.getString("b_date"),
                    rs.getString("b_address"),
                    "₱" + rs.getDouble("b_total"),
                    rs.getString("b_status")
                });
            }
        }

    } catch (java.sql.SQLException e) {
        System.out.println("Search Error: " + e.getMessage());
    }
}
    
    private void applyComboBoxStyle(javax.swing.JComboBox<String> combo) {
    combo.setFont(new Font("Segoe UI", Font.BOLD, 12));
    combo.setBackground(Color.WHITE); // Puti ang background para limpyo
    combo.setForeground(new Color(35, 66, 106)); // Navy ang text
    combo.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(35, 66, 106), 1));
    
    // Para mawala ang karaan nga "arrow" design (Optional: Depende sa Look and Feel)
    combo.setFocusable(false);
    
    // I-center ang text sa sulod sa dropdown
    ((javax.swing.JLabel)combo.getRenderer()).setHorizontalAlignment(javax.swing.JLabel.CENTER);
}

    
    private void setupTable() {
    // Updated Headers to include Provider
    String[] headers = {"ID", "Service", "Provider", "Date", "Address", "Amount", "Status"};
    DefaultTableModel model = new DefaultTableModel(null, headers) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    tbl_appointments.setModel(model);

    // NAVY HEADER DESIGN
    Color navy = new Color(35, 66, 106);
    tbl_appointments.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
    tbl_appointments.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 35));
    tbl_appointments.getTableHeader().setReorderingAllowed(false);

    DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
    headerRenderer.setBackground(navy);
    headerRenderer.setForeground(Color.WHITE);
    headerRenderer.setHorizontalAlignment(javax.swing.JLabel.CENTER);

    for (int i = 0; i < tbl_appointments.getColumnCount(); i++) {
        tbl_appointments.getTableHeader().getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
    }

    // COLUMN WIDTHS
    tbl_appointments.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
    tbl_appointments.getColumnModel().getColumn(1).setPreferredWidth(120); // Service
    tbl_appointments.getColumnModel().getColumn(2).setPreferredWidth(120); // Provider
    tbl_appointments.getColumnModel().getColumn(3).setPreferredWidth(100); // Date
    tbl_appointments.getColumnModel().getColumn(4).setPreferredWidth(120); // Address
    tbl_appointments.getColumnModel().getColumn(5).setPreferredWidth(80);  // Amount
    tbl_appointments.getColumnModel().getColumn(6).setPreferredWidth(100); // Status

    // TABLE BODY LOOK
    tbl_appointments.setRowHeight(25);
    tbl_appointments.setShowGrid(true);
    tbl_appointments.setGridColor(new Color(230, 230, 230));
    tbl_appointments.setSelectionBackground(new Color(220, 230, 240));

    // CENTER TEXT & STATUS COLORS
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
        @Override
        public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setHorizontalAlignment(javax.swing.JLabel.CENTER);
            c.setFont(new Font("Segoe UI", Font.PLAIN, 12));

            // Status Color Logic (Column 6)
            if (column == 6 && value != null) {
                String status = value.toString();
                if (status.equalsIgnoreCase("Pending")) {
                    c.setForeground(new Color(255, 140, 0)); // Orange
                } else if (status.equalsIgnoreCase("Approved")) {
                    c.setForeground(new Color(0, 153, 51)); // Green
                } else {
                    c.setForeground(Color.BLACK);
                }
            } else {
                c.setForeground(Color.BLACK);
            }

            if (isSelected) {
                c.setForeground(table.getSelectionForeground());
            }
            return c;
        }
    };

    for (int i = 0; i < tbl_appointments.getColumnCount(); i++) {
        tbl_appointments.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }
}
        // Apply style sa button
     


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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        viewdetails = new javax.swing.JButton();
        cancel = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_appointments = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        search = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(35, 66, 106));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/Blue White Modern Minimalist Interior Designer Personal Branding Logo(1)(1).jpg"))); // NOI18N
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, -1, -1));

        jButton1.setBackground(new java.awt.Color(35, 66, 106));
        jButton1.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Back to Home");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 220, 30));

        viewdetails.setText("View Details");
        viewdetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewdetailsActionPerformed(evt);
            }
        });
        jPanel2.add(viewdetails, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, 220, -1));

        cancel.setText("Cancel Booking");
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });
        jPanel2.add(cancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 220, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 281, 580));

        tbl_appointments.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tbl_appointments);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 260, 490, 350));

        jPanel3.setBackground(new java.awt.Color(35, 66, 106));

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 0, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("MY BOOKINGS");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(76, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(71, 71, 71))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 60, -1, -1));
        jPanel1.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 220, 130, 30));

        search.setText("Search");
        search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchActionPerformed(evt);
            }
        });
        jPanel1.add(search, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 220, 90, 30));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 220, 140, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 884, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 652, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        udashboard dash = new udashboard();
    dash.setVisible(true);
    this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void viewdetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewdetailsActionPerformed
                                               
    int row = tbl_appointments.getSelectedRow();
    
    if (row == -1) {
        javax.swing.JOptionPane.showMessageDialog(null, "Please select a booking first!");
        return;
    }

    try {
        int colCount = tbl_appointments.getColumnCount();

        // 1. Kuhaon ang data gikan sa table cells
        String id       = (colCount > 0) ? tbl_appointments.getValueAt(row, 0).toString() : "N/A";
        String service  = (colCount > 1) ? tbl_appointments.getValueAt(row, 1).toString() : "N/A";
        
        // Kani nga column (index 2) maoy naay Provider ID
        String providerId = (colCount > 2) ? tbl_appointments.getValueAt(row, 2).toString() : ""; 
        
        String date     = (colCount > 3) ? tbl_appointments.getValueAt(row, 3).toString() : "N/A";
        String address  = (colCount > 4) ? tbl_appointments.getValueAt(row, 4).toString() : "N/A";
        String total    = (colCount > 5) ? tbl_appointments.getValueAt(row, 5).toString() : "0.00";
        String status   = (colCount > 6) ? tbl_appointments.getValueAt(row, 6).toString() : "PENDING";

        // 2. SQL LOOKUP PARA SA USER_NAME (Base sa imong gi-upload nga tbl_users)
        String providerDisplay = "Not Assigned";
        
        if (!providerId.isEmpty() && !providerId.equals("0") && !providerId.equalsIgnoreCase("N/A")) {
            CONFIG.config conf = new CONFIG.config();
            // Gamiton nato ang 'user_name' base sa imong DB structure
            String sql = "SELECT user_name FROM tbl_users WHERE user_id = ?";
            
            try (java.sql.Connection conn = conf.connectDB();
                 java.sql.PreparedStatement pst = conn.prepareStatement(sql)) {
                
                pst.setString(1, providerId);
                java.sql.ResultSet rs = pst.executeQuery();
                
                if (rs.next()) {
                    providerDisplay = rs.getString("user_name");
                }
            } catch (java.sql.SQLException e) {
                System.out.println("Database Error: " + e.getMessage());
            }
        }

        // 3. UI logic para sa status badge color
        String statusColor = status.equalsIgnoreCase("Pending") ? "#f0ad4e" : "#5cb85c";

        // 4. HTML Receipt Layout
        String message = "<html>"
            + "<div style='width: 320px; background-color: white; padding: 20px; font-family: Segoe UI, sans-serif;'>"
            + "  <div style='text-align: center; border-bottom: 2px dashed #1a4d80; padding-bottom: 10px; margin-bottom: 15px;'>"
            + "    <h2 style='margin: 0; color: #1a4d80;'>LOCAL HELPER</h2>"
            + "    <p style='margin: 0; font-size: 10px; color: #888;'>OFFICIAL SERVICE RECEIPT</p>"
            + "  </div>"
            + "  <table style='width: 100%; border-collapse: collapse;'>"
            + "    <tr><td style='padding: 3px 0; color: #777;'>Booking ID:</td><td style='text-align: right;'><b>#" + id + "</b></td></tr>"
            + "    <tr><td style='padding: 3px 0; color: #777;'>Service:</td><td style='text-align: right;'>" + service + "</td></tr>"
            + "    <tr><td style='padding: 3px 0; color: #777;'>Provider:</td><td style='text-align: right; color: #1a4d80;'><b>" + providerDisplay + "</b></td></tr>"
            + "    <tr><td style='padding: 3px 0; color: #777;'>Schedule:</td><td style='text-align: right;'>" + date + "</td></tr>"
            + "  </table>"
            + "  <div style='margin-top: 15px; padding: 12px; background-color: #f9f9f9; border-radius: 8px; border: 1px solid #eee;'>"
            + "    <table style='width: 100%;'>"
            + "      <tr>"
            + "        <td><b style='font-size: 12px;'>Total Amount:</b></td>"
            + "        <td style='text-align: right; color: #d9534f; font-size: 16px;'><b>" + total + "</b></td>"
            + "      </tr>"
            + "      <tr>"
            + "        <td><small style='color: #777;'>Status:</small></td>"
            + "        <td style='text-align: right;'><span style='background-color: " + statusColor + "; color: white; padding: 2px 10px; border-radius: 12px; font-size: 10px; font-weight: bold;'>" + status.toUpperCase() + "</span></td>"
            + "      </tr>"
            + "    </table>"
            + "  </div>"
            + "  <div style='text-align: center; margin-top: 20px; border-top: 1px solid #eee; padding-top: 10px;'>"
            + "    <p style='font-size: 9px; color: #bbb; margin: 0;'>Address: " + address + "</p>"
            + "    <p style='font-size: 10px; color: #1a4d80; margin-top: 5px;'><b>Thank you for using LocalHelper!</b></p>"
            + "  </div>"
            + "</div></html>";

        javax.swing.JOptionPane.showMessageDialog(null, message, "Booking Details", javax.swing.JOptionPane.PLAIN_MESSAGE);

    } catch (Exception e) {
        System.out.println("View Details Error: " + e.getMessage());
        javax.swing.JOptionPane.showMessageDialog(null, "An error occurred while loading details.");
    }


    }//GEN-LAST:event_viewdetailsActionPerformed

    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed
        searchTable();
    }//GEN-LAST:event_searchActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        searchTable();
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
    int row = tbl_appointments.getSelectedRow();
    if (row == -1) {
        javax.swing.JOptionPane.showMessageDialog(null, "Please select a booking to cancel.");
        return;
    }

    String id = tbl_appointments.getValueAt(row, 0).toString();

    int confirm = javax.swing.JOptionPane.showConfirmDialog(null, 
            "Are you sure you want to cancel this booking?", 
            "Cancel Booking", javax.swing.JOptionPane.YES_NO_OPTION);

    if (confirm == javax.swing.JOptionPane.YES_OPTION) {
        String sql = "DELETE FROM tbl_bookings WHERE b_id = ?";
        
        try (java.sql.Connection conn = CONFIG.config.connectDB();
             java.sql.PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, id);
            int result = pst.executeUpdate();

            if (result > 0) {
                javax.swing.JOptionPane.showMessageDialog(null, "Cancelled successfully!");
                 loadMyAppointments();
            }
        } catch (java.sql.SQLException e) {
            javax.swing.JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
    }//GEN-LAST:event_cancelActionPerformed

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
            java.util.logging.Logger.getLogger(MyAppt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MyAppt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MyAppt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MyAppt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MyAppt().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancel;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JButton search;
    private javax.swing.JTable tbl_appointments;
    private javax.swing.JButton viewdetails;
    // End of variables declaration//GEN-END:variables
}
