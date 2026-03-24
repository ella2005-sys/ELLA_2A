
package MAIN;

import java.awt.Cursor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import CONFIG.config;
import javax.swing.JOptionPane;
import USER.udashboard;
import ADMIN.admindashboard;
import PROVIDER.providerdashboard;
import CONFIG.Session;



/**
 *
 * @author Jingkie
 */
public class login extends javax.swing.JFrame {

    /**
     * Creates new form login
     */
   public login() {
    initComponents();
    

    jLabel5.setText(
        "<html>Don’t have an account? <a href=''>Sign Up here.</a></html>"
    );
    jLabel5.setCursor(new Cursor(Cursor.HAND_CURSOR));

    jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            register reg = new register(); // open register form
            reg.setVisible(true);
            reg.pack();
            reg.setLocationRelativeTo(null); // center screen
            dispose(); // close login form (optional)
        }
    });
    
    jLabel1.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 26)); // Title
    jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", java.awt.Font.PLAIN, 14)); // Label Email
    jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", java.awt.Font.PLAIN, 14)); // Label Password
    
    // 2. Style the Input Fields (Rounded/Modern Look)
    styleTextField(jTextField1);
    styleTextField(jTextField2);
    
    // 3. Style the Login Button
    styleLoginButton(jButton1);

    // 4. Style the "Sign Up" Link
    jLabel5.setText("<html>Don’t have an account? <span style='color:#23426A;'><b>Sign Up here.</b></span></html>");
    jLabel5.setCursor(new Cursor(Cursor.HAND_CURSOR));
    
    // 5. Remove the error-prone "jLabel5register" and use proper listener
    jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            register reg = new register();
            reg.setVisible(true);
            reg.setLocationRelativeTo(null);
            dispose();
        }
    });
}

   
   private void styleTextField(javax.swing.JTextField field) {
    field.setBackground(new java.awt.Color(250, 250, 250));
    field.setBorder(javax.swing.BorderFactory.createCompoundBorder(
        javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 200, 200), 1),
        javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10))); // Adds padding inside
}

private void styleLoginButton(javax.swing.JButton btn) {
    btn.setBackground(new java.awt.Color(35, 66, 106));
    btn.setForeground(java.awt.Color.WHITE);
    btn.setFocusPainted(false);
    btn.setBorderPainted(false);
    btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    
    btn.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            btn.setBackground(new java.awt.Color(45, 86, 136));
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
            btn.setBackground(new java.awt.Color(35, 66, 106));
        }
    });
}
   
   

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(35, 66, 106));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/Blue White Modern Minimalist Interior Designer Personal Branding Logo(1)(1).jpg"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(jLabel6)
                .addContainerGap(53, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(175, 175, 175)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 503));

        jLabel1.setBackground(new java.awt.Color(35, 66, 106));
        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(35, 66, 106));
        jLabel1.setText("Sign In to Local Helper");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 40, -1, 41));

        jTextField1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(355, 129, 375, 36));

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 0, 15)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(35, 66, 106));
        jLabel2.setText("Email");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(389, 99, -1, 24));

        jTextField2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(355, 213, 375, 33));

        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 0, 15)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(35, 66, 106));
        jLabel3.setText("Password");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(389, 176, -1, -1));

        jCheckBox1.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox1.setFont(new java.awt.Font("Segoe UI Black", 0, 13)); // NOI18N
        jCheckBox1.setForeground(new java.awt.Color(35, 66, 106));
        jCheckBox1.setText("Remember me");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });
        jPanel1.add(jCheckBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(355, 248, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI Black", 0, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(35, 66, 106));
        jLabel4.setText("Forgot Password?");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(617, 253, -1, 16));

        jButton1.setBackground(new java.awt.Color(35, 66, 106));
        jButton1.setFont(new java.awt.Font("Segoe UI Black", 0, 20)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Login");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(355, 313, 375, 35));

        jLabel5.setFont(new java.awt.Font("Segoe UI Black", 0, 20)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(35, 66, 106));
        jLabel5.setText("Don't have an account? Sign Up here.");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(362, 397, -1, -1));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/mail.png"))); // NOI18N
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(355, 99, 28, -1));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/lock.png"))); // NOI18N
        jLabel8.setText("k");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(355, 176, 28, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 835, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
  String email = jTextField1.getText().trim();
    String password = jTextField2.getText().trim();

    if (email.isEmpty() || password.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter email and password.", "Warning", JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {
        Connection conn = config.connectDB();
        String sql = "SELECT * FROM tbl_users WHERE user_email = ? AND user_password = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, email);
        pst.setString(2, password);

        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            String status = rs.getString("u_status");
            String role = rs.getString("user_role");

            // Check account status
            if (status.equalsIgnoreCase("Pending")) {
                JOptionPane.showMessageDialog(this, "Account pending approval. Please wait for the Admin.", "Access Denied", JOptionPane.WARNING_MESSAGE);
            } else if (status.equalsIgnoreCase("Approved") || role.equalsIgnoreCase("ADMIN")) {

                // **Fill the session using the proper setter**
                Session.setSession(
                    rs.getInt("user_id"),          // userId
                    rs.getString("user_name"),     // name
                    rs.getString("user_address"),  // address
                    rs.getString("user_number"),   // number
                    rs.getString("user_email"),    // email
                    role,                          // role
                    status                         // status
                );

                JOptionPane.showMessageDialog(this, "Welcome, " + Session.getName() + "!");

                // Redirect based on role
                if (role.equalsIgnoreCase("ADMIN")) {
                    new admindashboard().setVisible(true);
                } else if (role.equalsIgnoreCase("PROVIDER")) {
                    new providerdashboard().setVisible(true);
                } else {
                    new udashboard().setVisible(true);
                }
                this.dispose();

            } else {
                JOptionPane.showMessageDialog(this, "Account status: " + status + ". Access denied.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(this, "Invalid Email or Password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }

        rs.close();
        pst.close();
        conn.close();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
    }
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables

    private static class jLabel5register {

        private static void setText(String htmlDont_have_an_account_a_hrefSign_Up_he) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        private static void setCursor(Cursor cursor) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        public jLabel5register() {
        }
    }
}
