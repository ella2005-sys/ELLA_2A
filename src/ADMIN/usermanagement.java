
package ADMIN;

import CONFIG.config;
import MAIN.landingpage;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class usermanagement extends JFrame {
     private config cfg = new config();
    private JTable usersTable;

    public usermanagement() {
        initComponents(); 
        new CONFIG.config().sessionGuard(this);
        setLayout(null);
        tableStyle();
        loadUsers();

        // Button actions
       AddUSer.addActionListener(e -> addUser());
        jButton2.addActionListener(e -> updateUser());
        delete.addActionListener(e -> deleteUser());
        
        java.awt.Color navy = new java.awt.Color(35, 66, 106);
    java.awt.Color brown = new java.awt.Color(106, 75, 35);

    // Styling the CRUD buttons
    styleAdminButtons(AddUSer, navy);
    styleAdminButtons(jButton2, navy);
    styleAdminButtons(delete, brown); // Brown for 'Delete' acts as a warning color
    styleAdminButtons(jButton2, navy); // Providers
    styleAdminButtons(jButton6, navy); // Appointments
    styleAdminButtons(jButton7, navy); // Reports
    styleAdminButtons(jButton8, navy); // Services
    styleAdminButtons(jButton9, navy); // My Profile
    styleAdminButtons(jButton10, navy); // My Profile
    styleAdminButtons(jButton11, brown); // My Profile
    
    
    
    
    
    // Styling the Back/Home button
    styleAdminButtons(jButton5, navy); 
    
    // Table Styling (To match the User Dashboard)
    jTable1.setRowHeight(30);
    jTable1.getTableHeader().setBackground(navy);
    jTable1.getTableHeader().setForeground(java.awt.Color.WHITE);

    }

   void loadUsers() {
        String sql = "SELECT user_id, user_name, user_email, user_address, user_number, u_status, user_role FROM tbl_users";
        cfg.displayData(sql, jTable1);
    }
   private void searchUser() {
        String query = jTextField1.getText().trim();
        String sql = "SELECT user_id, user_name, user_email, user_address, user_number, u_status, user_role " +
                     "FROM tbl_users " +
                     "WHERE user_id LIKE ? OR user_name LIKE ? OR user_email LIKE ?";
        try (Connection conn = cfg.connectDB();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            String likeQuery = "%" + query + "%";
            pst.setString(1, likeQuery);
            pst.setString(2, likeQuery);
            pst.setString(3, likeQuery);

            ResultSet rs = pst.executeQuery();
            cfg.displayResultSet(rs, jTable1);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error searching users: " + e.getMessage());
        }
    }
   
   private void tableStyle() {
    // 1. Basic Table Styling
    jTable1.setRowHeight(30); // Makes rows roomier and modern
    jTable1.setFont(new java.awt.Font("Segoe UI", 0, 12));
    jTable1.setGridColor(new java.awt.Color(230, 230, 230)); // Soft gray grid lines
    jTable1.setSelectionBackground(new java.awt.Color(35, 66, 106)); // Navy Blue selection
    jTable1.setSelectionForeground(java.awt.Color.WHITE);

    // 2. Styling the Header
    jTable1.getTableHeader().setFont(new java.awt.Font("Segoe UI Black", 0, 13));
    jTable1.getTableHeader().setBackground(new java.awt.Color(35, 66, 106)); // Navy Blue Header
    jTable1.getTableHeader().setForeground(java.awt.Color.WHITE);
    jTable1.getTableHeader().setOpaque(false);
    
    // 3. Remove the Border of the ScrollPane
    jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
    jScrollPane1.getViewport().setBackground(java.awt.Color.WHITE);
}


    private void addUser() {
        Userform form = new Userform(this);
        form.setVisible(true);
    }

     private void updateUser() {
        int row = jTable1.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user from the table.");
            return;
        }

        String userId = jTable1.getValueAt(row, 0).toString();
        UpdateUser updateForm = new UpdateUser(userId, this);
        updateForm.setVisible(true);
        updateForm.setLocationRelativeTo(this);
    }

 

  private void deleteUser() {
        int row = jTable1.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user first!");
            return;
        }

        String userId = jTable1.getValueAt(row, 0).toString();
        String userName = jTable1.getValueAt(row, 1).toString();

        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to delete user: " + userName + " (ID: " + userId + ")?",
            "Confirm Deletion",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                String sql = "DELETE FROM tbl_users WHERE user_id = ?";
                cfg.executeSQL(sql, userId);
                JOptionPane.showMessageDialog(this, "User successfully deleted!");
                loadUsers();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error deleting user: " + e.getMessage());
            }
        }
    }
  private void goBackHome() {
        admindashboard dashboard = new admindashboard();
        dashboard.setVisible(true);
        dashboard.setLocationRelativeTo(null);
        this.dispose();
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
            btn.setBackground(baseColor.brighter()); 
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
        jLabel3 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        AddUSer = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        delete = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(35, 66, 106));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/Blue White Modern Minimalist Interior Designer Personal Branding Logo(1)(1).jpg"))); // NOI18N
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, 160, 70));

        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("MENU");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 140, -1, -1));

        jButton5.setBackground(new java.awt.Color(35, 66, 106));
        jButton5.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Back to Home");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 170, 140, -1));

        jButton6.setBackground(new java.awt.Color(35, 66, 106));
        jButton6.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Providers");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 210, 140, -1));

        jButton7.setBackground(new java.awt.Color(35, 66, 106));
        jButton7.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("Appointments");
        jPanel2.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 250, 140, -1));

        jButton8.setBackground(new java.awt.Color(35, 66, 106));
        jButton8.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setText("Reports");
        jPanel2.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 290, 140, -1));

        jButton9.setBackground(new java.awt.Color(35, 66, 106));
        jButton9.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setText("Services");
        jPanel2.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 330, 140, -1));

        jButton10.setBackground(new java.awt.Color(35, 66, 106));
        jButton10.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jButton10.setForeground(new java.awt.Color(255, 255, 255));
        jButton10.setText("My Profile");
        jPanel2.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 370, 140, -1));

        jButton11.setBackground(new java.awt.Color(35, 66, 106));
        jButton11.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jButton11.setForeground(new java.awt.Color(255, 255, 255));
        jButton11.setText("Logout");
        jPanel2.add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 460, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 280, 502));

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

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 160, 520, 303));

        jPanel3.setBackground(new java.awt.Color(35, 66, 106));

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("USERS TABLE");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(102, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(100, 100, 100))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, 440, 50));

        AddUSer.setBackground(new java.awt.Color(35, 66, 106));
        AddUSer.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        AddUSer.setForeground(new java.awt.Color(255, 255, 255));
        AddUSer.setText("Add User");
        AddUSer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddUSerActionPerformed(evt);
            }
        });
        jPanel1.add(AddUSer, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 120, 100, 30));

        jButton2.setBackground(new java.awt.Color(35, 66, 106));
        jButton2.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Update User");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(407, 120, 130, 30));

        delete.setBackground(new java.awt.Color(35, 66, 106));
        delete.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        delete.setForeground(new java.awt.Color(255, 255, 255));
        delete.setText("Delete User");
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });
        jPanel1.add(delete, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 120, 120, 30));

        jTextField1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
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
        jPanel1.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 120, 90, 30));

        jButton4.setBackground(new java.awt.Color(35, 66, 6));
        jButton4.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Search");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 120, 90, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 864, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void AddUSerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddUSerActionPerformed
    Userform form = new Userform(this); // pass parent
    form.setVisible(true);
    }//GEN-LAST:event_AddUSerActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    int row = jTable1.getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Please select a user from the table.");
        return;
    }

    // Get the User ID from the first column (index 0)
    String userId = jTable1.getValueAt(row, 0).toString(); 

    // Pass 'this' as the second argument so UpdateUser can refresh this table
    UpdateUser updateForm = new UpdateUser(userId, this); 
    updateForm.setVisible(true);
    updateForm.setLocationRelativeTo(this);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
 deleteUser();
    }//GEN-LAST:event_deleteActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
     searchUser();
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
      searchUser();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        searchUser();
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        admindashboard ad = new admindashboard();
    ad.setVisible(true);
    ad.setLocationRelativeTo(null); // Keeps it centered
    this.dispose(); // Closes the user management window
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    /**
     * @param args the command line arguments
     */
   public static void main(String args[]) {
    /* Set the Nimbus look and feel (keep the existing try-catch block) */
    try {
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                javax.swing.UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
    } catch (Exception ex) {
        java.util.logging.Logger.getLogger(usermanagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }

    /* Create and display the form with Security Check */
    java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            // Check if a session exists (User ID should not be 0)
            if (CONFIG.Session.getUserId() == 0) {
                javax.swing.JOptionPane.showMessageDialog(
                    null, 
                    "Please Login First", 
                    "Security Alert", 
                    javax.swing.JOptionPane.ERROR_MESSAGE
                );
                
                // Redirect back to login
                MAIN.login loginFrame = new MAIN.login();
                loginFrame.setVisible(true);
            } else {
                // User is authenticated, proceed to User Management
                new usermanagement().setVisible(true);
            }
        }
    });
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddUSer;
    private javax.swing.JButton delete;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables

   

   

   

}
