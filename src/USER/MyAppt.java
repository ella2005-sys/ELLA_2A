
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
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class MyAppt extends javax.swing.JFrame {

    CONFIG.config cfg = new CONFIG.config();
    
    public MyAppt() {
        initComponents();
        setupTable(); // Kini ang mo-apply sa tanang design
        loadAppointments();
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
    
    private void searchTable() {
    String searchText = jTextField1.getText().toLowerCase();
    String statusFilter = jComboBox1.getSelectedItem().toString();

    try {
        Connection conn = cfg.getConnection();
        int currentId = Session.getUserId();
        
        // Mag-JOIN ta para ma-search ang Service Name bisan naa sa laing table
        String sql = "SELECT b.b_id, s.s_name, b.b_date, b.b_address, b.b_total, b.b_status " +
                     "FROM tbl_bookings b " +
                     "JOIN tbl_services s ON b.s_id = s.\" s_id\" " +
                     "WHERE b.u_id = ? AND (LOWER(s.s_name) LIKE ? OR LOWER(b.b_address) LIKE ?)";

        // Kung naay gipili nga status (dili "All Status"), idugang sa query
        if (!statusFilter.equals("All Status")) {
            sql += " AND b.b_status = ?";
        }

        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, currentId);
        pst.setString(2, "%" + searchText + "%");
        pst.setString(3, "%" + searchText + "%");

        if (!statusFilter.equals("All Status")) {
            pst.setString(4, statusFilter);
        }

        ResultSet rs = pst.executeQuery();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("b_id"),
                rs.getString("s_name"),
                rs.getString("b_date"),
                rs.getString("b_address"),
                "₱" + rs.getString("b_total"),
                rs.getString("b_status")
            });
        }
        rs.close();
        pst.close();
    } catch (Exception e) {
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
    // 1. Headers (Pareha sa udashboard)
    String[] headers = {"ID", "Service", "Date", "Address", "Amount", "Status"};
    DefaultTableModel model = new DefaultTableModel(null, headers) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    jTable1.setModel(model);

    // 2. NAVY HEADER DESIGN (Navy Background + White Font)
    Color navy = new Color(35, 66, 106);
    jTable1.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
    jTable1.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 35));
    jTable1.getTableHeader().setReorderingAllowed(false);

    // Custom Header Renderer para mapugos ang Navy color sa font ug background
    DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
    headerRenderer.setBackground(navy);
    headerRenderer.setForeground(Color.WHITE);
    headerRenderer.setHorizontalAlignment(javax.swing.JLabel.CENTER);

    for (int i = 0; i < jTable1.getColumnCount(); i++) {
        jTable1.getTableHeader().getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
    }

    // 3. EQUAL/BALANCED COLUMN WIDTHS (Para han-ay tan-awon)
    jTable1.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
    jTable1.getColumnModel().getColumn(1).setPreferredWidth(120); // Service
    jTable1.getColumnModel().getColumn(2).setPreferredWidth(100); // Date
    jTable1.getColumnModel().getColumn(3).setPreferredWidth(120); // Address
    jTable1.getColumnModel().getColumn(4).setPreferredWidth(80);  // Amount
    jTable1.getColumnModel().getColumn(5).setPreferredWidth(100); // Status

    // 4. TABLE BODY LOOK
    jTable1.setRowHeight(25); // Pareha sa udashboard row height
    jTable1.setShowGrid(true);
    jTable1.setGridColor(new Color(230, 230, 230));
    jTable1.setSelectionBackground(new Color(220, 230, 240));

    // 5. CENTER TEXT SA TANAN COLUMNS & STATUS COLORS
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
        @Override
        public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setHorizontalAlignment(javax.swing.JLabel.CENTER);
            
            // Default font para sa body
            c.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            
            // Status Color Logic (Column 5)
            if (column == 5 && value != null) {
                String status = value.toString();
                if (status.equalsIgnoreCase("Pending")) {
                    c.setForeground(new Color(255, 140, 0)); // Dark Orange
                } else if (status.equalsIgnoreCase("Approved")) {
                    c.setForeground(new Color(0, 153, 51)); // Green
                } else {
                    c.setForeground(Color.BLACK);
                }
            } else {
                c.setForeground(Color.BLACK);
            }

            // Maintain selection color maski naay status color
            if (isSelected) {
                c.setForeground(table.getSelectionForeground());
            }
            
            return c;
        }
    };

    for (int i = 0; i < jTable1.getColumnCount(); i++) {
        jTable1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }
}
        // Apply style sa button
     

    public void loadAppointments() {
        try {
            Connection conn = cfg.getConnection();
            int currentId = Session.getUserId();
            String sql = "SELECT b_id, s_id, b_date, b_address, b_total, b_status FROM tbl_bookings WHERE u_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, currentId);
            ResultSet rs = pst.executeQuery();

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                int serviceId = rs.getInt("s_id");
                String serviceName = "Unknown Service";

                try {
                    // Note: Gamita ang \" s_id\" kung wala pa nimo na-rename ang column sa DB
                    String serviceSql = "SELECT s_name FROM tbl_services WHERE \" s_id\" = ?";
                    PreparedStatement pstService = conn.prepareStatement(serviceSql);
                    pstService.setInt(1, serviceId);
                    ResultSet rsService = pstService.executeQuery();
                    if (rsService.next()) {
                        serviceName = rsService.getString("s_name");
                    }
                    rsService.close();
                    pstService.close();
                } catch (Exception e) {
                    System.out.println("Service Name Error: " + e.getMessage());
                }

                model.addRow(new Object[]{
                    rs.getString("b_id"),
                    serviceName,
                    rs.getString("b_date"),
                    rs.getString("b_address"),
                    "₱" + rs.getString("b_total"),
                    rs.getString("b_status")
                });
            }
            rs.close();
            pst.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        cancel = new javax.swing.JButton();
        viewdetails = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
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

        cancel.setText("Cancel Booking");
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });
        jPanel2.add(cancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, 220, 30));

        viewdetails.setText("View Details");
        viewdetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewdetailsActionPerformed(evt);
            }
        });
        jPanel2.add(viewdetails, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, 220, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 281, 580));

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

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
       int row = jTable1.getSelectedRow();
    
    if (row == -1) {
        javax.swing.JOptionPane.showMessageDialog(null, "Please select a booking to cancel.");
        return;
    }

    // Kuhaa ang ID gikan sa first column (index 0)
    String id = jTable1.getValueAt(row, 0).toString();
    String service = jTable1.getValueAt(row, 1).toString();

    // Confirmation Dialog
    int confirm = javax.swing.JOptionPane.showConfirmDialog(null, 
            "Are you sure you want to cancel your booking for " + service + "?", 
            "Cancel Booking", javax.swing.JOptionPane.YES_NO_OPTION);

    if (confirm == javax.swing.JOptionPane.YES_OPTION) {
        try {
            // Option A: DELETE ang record (Kini ang kasagaran sa projects)
            String sql = "DELETE FROM tbl_bookings WHERE b_id = ?";
            
            /* // Option B: UPDATE status to 'Cancelled' (Mas maayo kung gusto nimo naay history)
            String sql = "UPDATE tbl_bookings SET b_status = 'Cancelled' WHERE b_id = ?";
            */

            java.sql.Connection conn = cfg.getConnection();
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, id);

            int result = pst.executeUpdate();
            if (result > 0) {
                javax.swing.JOptionPane.showMessageDialog(null, "Booking cancelled successfully!");
                loadAppointments(); // I-refresh ang table para mawala ang gi-cancel
            }
            
            pst.close();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
    }//GEN-LAST:event_cancelActionPerformed

    private void viewdetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewdetailsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_viewdetailsActionPerformed

    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed
        searchTable();
    }//GEN-LAST:event_searchActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        searchTable();
    }//GEN-LAST:event_jComboBox1ActionPerformed

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
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JButton search;
    private javax.swing.JButton viewdetails;
    // End of variables declaration//GEN-END:variables
}
