
package PROVIDER;

import ADMIN.AddService;
import CONFIG.Session;
import CONFIG.config;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class myservices extends javax.swing.JFrame {
    
    config cfg = new config();
    
  

   public myservices() {
        initComponents();
        loadServices(); // load provider services on open
        
        // Color Palette
    java.awt.Color navy = new java.awt.Color(35, 66, 106);

    // Style Sidebar Buttons
    applyDashboardStyle(home, navy);
    applyDashboardStyle(jButton3, navy); // Ratings & Feedback

    // Style the ADD Button
    applyDashboardStyle(AddService, navy);
    
    // Modernize the Table Look
jTable1.setRowHeight(30);
jTable1.getTableHeader().setFont(new java.awt.Font("Segoe UI Bold", java.awt.Font.PLAIN, 12));
jTable1.getTableHeader().setBackground(new java.awt.Color(35, 66, 106));
jTable1.getTableHeader().setForeground(java.awt.Color.BLACK);
jTable1.setSelectionBackground(new java.awt.Color(45, 86, 136));
    }
   
   
  private void loadServices() {
    try {
        Connection conn = cfg.getConnection();
        int currentId = CONFIG.Session.getUserId(); 

        // Query everything for this specific provider
        String sql = "SELECT * FROM tbl_services WHERE provider_id = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, currentId);
        ResultSet rs = pst.executeQuery();

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getObject(1), // Use 1 instead of "s_id"
                rs.getObject(2), // Use 2 instead of "s_name"
                rs.getObject(3), // Use 3 instead of "s_category"
                rs.getObject(4), // Use 4 instead of "s_price"
                rs.getObject(5)  // Use 5 instead of "s_status"
            });
        }
        
        rs.close();
        pst.close();
        conn.close();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Table Load Error: " + e.getMessage());
    }
}





   
   
   private void addService() {
    try {
        String name = JOptionPane.showInputDialog(this, "Enter Service Name:");
        if (name == null || name.trim().isEmpty()) return;

        String category = JOptionPane.showInputDialog(this, "Enter Service Category:");
        if (category == null || category.trim().isEmpty()) return;

        String priceStr = JOptionPane.showInputDialog(this, "Enter Service Price:");
        if (priceStr == null || priceStr.trim().isEmpty()) return;
        double price = Double.parseDouble(priceStr);

        // Insert into database
        Connection conn = cfg.getConnection();
        String sql = "INSERT INTO tbl_services (service_name, category, price, provider_id) VALUES (?, ?, ?, ?)";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, name);
        pst.setString(2, category);
        pst.setDouble(3, price);
        pst.setInt(4, Session.getUserId());

        int inserted = pst.executeUpdate();
        if (inserted > 0) {
            JOptionPane.showMessageDialog(this, "Service added successfully!");
            loadServices(); // reload table
        }

        pst.close();
        conn.close();

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Invalid price value!");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error adding service: " + e.getMessage());
    }
}
   
   private void applyDashboardStyle(javax.swing.JButton btn, java.awt.Color baseColor) {
    btn.setBorderPainted(false);
    btn.setFocusPainted(false);
    btn.setContentAreaFilled(false); 
    btn.setOpaque(true); 
    btn.setBackground(baseColor);
    btn.setForeground(java.awt.Color.WHITE);
    btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    btn.setFont(new java.awt.Font("Segoe UI Semibold", java.awt.Font.PLAIN, 13));
    
    btn.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            btn.setBackground(new java.awt.Color(45, 86, 136)); // Hover effect
        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent evt) {
            btn.setBackground(baseColor);
        }
    });
}

   
     
      
 




    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        home = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        AddService = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(35, 66, 106));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/Blue White Modern Minimalist Interior Designer Personal Branding Logo(1)(1).jpg"))); // NOI18N
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, -1, 71));

        home.setBackground(new java.awt.Color(35, 66, 106));
        home.setFont(new java.awt.Font("Segoe UI Black", 0, 13)); // NOI18N
        home.setForeground(new java.awt.Color(255, 255, 255));
        home.setText("Back to Home");
        home.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                homeActionPerformed(evt);
            }
        });
        jPanel2.add(home, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 210, 160, -1));

        jButton3.setBackground(new java.awt.Color(35, 66, 106));
        jButton3.setFont(new java.awt.Font("Segoe UI Black", 0, 13)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Ratings & Feedback");
        jPanel2.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 290, 160, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 320, 720));

        jPanel3.setBackground(new java.awt.Color(35, 66, 106));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("MY SERVICES");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 30, -1, -1));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 40, 437, 109));

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

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 300, 437, 266));

        AddService.setBackground(new java.awt.Color(35, 66, 106));
        AddService.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        AddService.setForeground(new java.awt.Color(255, 255, 255));
        AddService.setText("ADD ");
        AddService.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddServiceActionPerformed(evt);
            }
        });
        jPanel1.add(AddService, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 250, 70, 36));
        jPanel1.add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 250, 110, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 892, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 716, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void AddServiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddServiceActionPerformed
       AddService add = new AddService(CONFIG.Session.getUserId()); // pass provider ID
    add.setVisible(true);
    }//GEN-LAST:event_AddServiceActionPerformed

    private void homeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_homeActionPerformed
       providerdashboard dash = new providerdashboard();
    dash.setVisible(true);
    this.dispose();
    }//GEN-LAST:event_homeActionPerformed

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
            java.util.logging.Logger.getLogger(myservices.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(myservices.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(myservices.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(myservices.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new myservices().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddService;
    private javax.swing.JButton home;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables

   
}
