package PROVIDER;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import ADMIN.EditAdminProfile;

/**
 * @author Jingkie
 */
public class providerprofile extends javax.swing.JFrame {

    javax.swing.JFrame parent;
    
    
   public providerprofile() {
        initComponents();
        loadProviderProfile();
        new CONFIG.config().sessionGuard(this);

        Color navy = new Color(35, 66, 106);
        Color lightGray = new Color(245, 245, 245);

        // Modernize the Background of the Profile Card
        jPanel3.setBackground(Color.WHITE);
        jPanel3.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));

        // Style the Back Button
        applyAdminButtonStyle(jButton3, navy); 
        applyAdminButtonStyle(jButton4, navy);

        // Style Photo Buttons
        styleActionButtons(jButton1, navy); 
        styleActionButtons(jButton2, new Color(180, 50, 50)); 

        // Image frame styling
        jLabel7.setBorder(BorderFactory.createLineBorder(navy, 2));
        jLabel7.setOpaque(true);
        jLabel7.setBackground(lightGray);

        if (jLabel7.getIcon() == null) {
            jLabel7.setText("<html><center><b>+</b><br>PROFILE</center></html>");
            jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
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

    // GI-UPDATE: Gihimong PUBLIC para ma-refresh sa Userform
    public void loadProviderProfile() {
        int userId = CONFIG.Session.getUserId();
        String sql = "SELECT * FROM tbl_users WHERE user_id = ?";

        try (java.sql.Connection conn = CONFIG.config.connectDB();
             java.sql.PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, userId);
            java.sql.ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                jLabel3.setText("User ID: " + rs.getInt("user_id"));
                jLabel4.setText("Name: " + rs.getString("user_name"));
                jLabel5.setText("Email: " + rs.getString("user_email"));
                jLabel6.setText("User Status: " + rs.getString("u_status"));

                String imagePath = rs.getString("user_image");
                if (imagePath != null && !imagePath.isEmpty()) {
                    displayImage(imagePath);
                } else {
                    jLabel7.setIcon(null);
                    jLabel7.setText("+ Add Photo");
                }
            }
        } catch (Exception e) {
            System.out.println("Load Error: " + e.getMessage());
        }
    }

    private void displayImage(String path) {
        try {
            ImageIcon ii = new ImageIcon(path);
            Image img = ii.getImage().getScaledInstance(
                jLabel7.getWidth(),
                jLabel7.getHeight(),
                Image.SCALE_SMOOTH
            );
            jLabel7.setIcon(new ImageIcon(img));
            jLabel7.setText("");
        } catch (Exception e) {
            System.out.println("Image Error: " + e.getMessage());
        }
    }

    private void browseImage() {
        javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Images", "jpg", "png", "jpeg"));

        int result = chooser.showOpenDialog(null);
        if (result == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File file = chooser.getSelectedFile();
            String path = file.getAbsolutePath();
            displayImage(path);
            saveImage(path);
        }
    }

    private void saveImage(String path) {
        String sql = "UPDATE tbl_users SET user_image = ? WHERE user_id = ?";
        try (java.sql.Connection conn = CONFIG.config.connectDB();
             java.sql.PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, path);
            pst.setInt(2, CONFIG.Session.getUserId());
            if (pst.executeUpdate() > 0) {
                javax.swing.JOptionPane.showMessageDialog(null, "Profile picture updated!");
            }
        } catch (Exception e) {
            System.out.println("Save Error: " + e.getMessage());
        }
    }

    private void removePhoto() {
        int confirm = javax.swing.JOptionPane.showConfirmDialog(null, "Remove photo?", "Confirm", javax.swing.JOptionPane.YES_NO_OPTION);
        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
            String sql = "UPDATE tbl_users SET user_image = NULL WHERE user_id = ?";
            try (java.sql.Connection conn = CONFIG.config.connectDB();
                 java.sql.PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setInt(1, CONFIG.Session.getUserId());
                pst.executeUpdate();
                loadProviderProfile();
            } catch (Exception e) {
                System.out.println("Remove Error: " + e.getMessage());
            }
        }
    }

    private void styleActionButtons(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFont(new java.awt.Font("Segoe UI Bold", 0, 12));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(bg.brighter());
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(bg);
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
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(35, 66, 106));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/Blue White Modern Minimalist Interior Designer Personal Branding Logo(1)(1).jpg"))); // NOI18N
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, -1, 98));

        jButton3.setFont(new java.awt.Font("Segoe UI Black", 0, 13)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Back to Home");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 260, 30));

        jButton4.setBackground(new java.awt.Color(35, 66, 106));
        jButton4.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Edit Profile");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 260, 30));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, 300, 590));

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(35, 66, 106));
        jLabel3.setText("User ID:");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 184, 400, 41));

        jLabel4.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(35, 66, 106));
        jLabel4.setText("Name:");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 243, 400, 38));

        jLabel5.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(35, 66, 106));
        jLabel5.setText("Email:");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 299, 400, 38));

        jLabel6.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(35, 66, 106));
        jLabel6.setText("User Status:");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 355, 400, 36));
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(175, 22, 152, 123));

        jButton1.setText("Add Photo");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(119, 152, 108, -1));

        jButton2.setText("Remove ");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(266, 152, 95, -1));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 140, 510, 450));

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 0, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(35, 66, 106));
        jLabel2.setText("MY PROFILE");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 50, 230, 80));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 945, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 670, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    browseImage();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       removePhoto();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
new providerdashboard().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
 try {
            int id = CONFIG.Session.getUserId();
            String name = jLabel4.getText().replace("Name: ", "").trim();
            String email = jLabel5.getText().replace("Email: ", "").trim();
            
            // GI-FIX: null ang ipasa sa parent frame
            EditAdminProfile eap = new EditAdminProfile(id, name, email, null);
            
            eap.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    loadProviderProfile(); // Refresh labels inig close sa edit window
                }
            });

            eap.setVisible(true);
            eap.setLocationRelativeTo(null);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }//GEN-LAST:event_jButton4ActionPerformed

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
            java.util.logging.Logger.getLogger(providerprofile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(providerprofile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(providerprofile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(providerprofile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new providerprofile().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    // End of variables declaration//GEN-END:variables
}
