
package ADMIN;

import javax.swing.table.DefaultTableModel;
import CONFIG.config;
import java.text.MessageFormat;
import javax.swing.JTable;



public class reports extends javax.swing.JFrame {

     config cfg = new config();
    public reports() {
       new CONFIG.config().sessionGuard(this);
        initComponents();
        setLocationRelativeTo(null);
    setupTable();
    
    java.awt.Color navy = new java.awt.Color(35, 66, 106);
    java.awt.Color logoutBrown = new java.awt.Color(106, 75, 35);
     applyAdminButtonStyle(jButton1, navy); // Users
     applyAdminButtonStyle(print, navy); // Users
    displayReports();
    
    
    }
  
    public void displayReports() {
    try {
        java.sql.Connection conn = cfg.getConnection();

        // We use [ s_id] because your scan showed a leading space in the column name
        // We also use [tbl_services] and [tbl_bookings] to be safe
        String sql = "SELECT ts.[ s_id], ts.s_name, " +
                     "COUNT(tb.b_id) AS total_booked, " +
                     "SUM(CASE WHEN tb.b_total IS NULL THEN 0 ELSE tb.b_total END) AS total_revenue " +
                     "FROM tbl_services ts " +
                     "LEFT JOIN tbl_bookings tb ON ts.[ s_id] = tb.s_id " +
                     "GROUP BY ts.[ s_id], ts.s_name";

        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
        java.sql.ResultSet rs = pst.executeQuery();

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); 

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getInt(1),                                // ID
                rs.getString(2),                             // Name
                rs.getInt(3),                                // Booked Count
                "₱" + String.format("%.2f", rs.getDouble(4)) // Revenue
            });
        }
        
        rs.close();
        pst.close();
        conn.close();
    } catch (Exception e) {
        e.printStackTrace();
        javax.swing.JOptionPane.showMessageDialog(null, "Database Error: " + e.getMessage());
    }
}
 
    
    
private void setupTable() {
    String[] headers = {"Service ID", "Service Name", "Times Booked", "Total Revenue"};
    jTable1.setModel(new javax.swing.table.DefaultTableModel(null, headers));
    
    // UI Styling
    jTable1.setRowHeight(30);
    jTable1.getTableHeader().setBackground(new java.awt.Color(35, 66, 106));
    jTable1.getTableHeader().setForeground(java.awt.Color.WHITE);
}

 private void applyAdminButtonStyle(javax.swing.JButton btn, java.awt.Color baseColor) {
    btn.setBorderPainted(false);
    btn.setFocusPainted(false);
    btn.setContentAreaFilled(false); 
    btn.setOpaque(true); 
    btn.setBackground(baseColor);
    btn.setForeground(java.awt.Color.WHITE);
    btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    btn.setFont(new java.awt.Font("Segoe UI Semibold", java.awt.Font.PLAIN, 14));
    
    btn.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            btn.setBackground(baseColor.brighter()); 
        }
        @Override
        public void mouseExited(java.awt.event.MouseEvent evt) {
            btn.setBackground(baseColor);
        }
    });
}
 
 private void printBtnActionPerformed(java.awt.event.ActionEvent evt) {                                         
    // This creates the professional header and footer for your printed page
    MessageFormat header = new MessageFormat("LocalHelper - Service Revenue Report");
    MessageFormat footer = new MessageFormat("Page {0,number,integer}");
    
    try {
        // This command opens the system print dialog and formats your table
        boolean complete = jTable1.print(JTable.PrintMode.FIT_WIDTH, header, footer);
        
        if (complete) {
            System.out.println("Printing Complete");
        } else {
            System.out.println("Printing Cancelled");
        }
    } catch (java.awt.print.PrinterException e) {
        System.err.format("Cannot print %s%n", e.getMessage());
    }
}
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        print = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(35, 66, 106));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/Blue White Modern Minimalist Interior Designer Personal Branding Logo(1)(1).jpg"))); // NOI18N
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, -1, 91));

        jButton1.setBackground(new java.awt.Color(35, 66, 106));
        jButton1.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Back to Home");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 190, 150, 35));

        print.setBackground(new java.awt.Color(35, 66, 106));
        print.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        print.setForeground(new java.awt.Color(255, 255, 255));
        print.setText("Print Report");
        print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printActionPerformed(evt);
            }
        });
        jPanel2.add(print, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 270, 150, 40));

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

        jPanel3.setBackground(new java.awt.Color(35, 66, 106));

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 0, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("SALES AND REVENUE REPORTS");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 567, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(56, 56, 56))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 161, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(124, 124, 124))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       admindashboard ad = new admindashboard();
    ad.setVisible(true);
    this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printActionPerformed
      // 1. Create the professional header and footer
    MessageFormat header = new MessageFormat("LocalHelper - Service Revenue Report");
    MessageFormat footer = new MessageFormat("Page {0,number,integer}");
    
    try {
        // 2. This command opens the system print dialog and formats your table
        // FIT_WIDTH ensures the table doesn't get cut off on the paper
        boolean complete = jTable1.print(JTable.PrintMode.FIT_WIDTH, header, footer);
        
        if (complete) {
            javax.swing.JOptionPane.showMessageDialog(this, "Printing Complete!", "Printer", javax.swing.JOptionPane.INFORMATION_MESSAGE);
        } else {
            System.out.println("Printing Cancelled");
        }
    } catch (java.awt.print.PrinterException e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Printer Error: " + e.getMessage());
    }      // Closes the dashboard so only one window is open
    }//GEN-LAST:event_printActionPerformed

    /**
     * @param args the command line arguments
     */
   public static void main(String args[]) {
    // ... (Keep the Look and Feel try-catch) ...

    java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            if (!CONFIG.Session.isLoggedIn()) {
                javax.swing.JOptionPane.showMessageDialog(null, "Please Login First");
                new MAIN.login().setVisible(true);
            } else {
                new reports().setVisible(true);
            }
        }
    });
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton print;
    // End of variables declaration//GEN-END:variables
}
