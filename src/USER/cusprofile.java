/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package USER;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

/**
 *
 * @author Jingkie
 */
public class cusprofile extends javax.swing.JFrame {

    /**
     * Creates new form cusprofile
     */
   public cusprofile() {
    initComponents();
    loadCustomerProfile();
    new CONFIG.config().sessionGuard(this);
    
    Color navy = new Color(35, 66, 106);
    Color redish = new Color(180, 50, 50); // Para sa remove photo
    
    // Sidebar Button (Back to Home)
    applyAdminButtonStyle(jButton1, navy);    
    applyAdminButtonStyle(jButton4, navy);    
    
    // Profile Action Buttons (Add ug Remove)
    styleActionButtons(jButton2, navy);      // jButton2: Add Photo
    styleActionButtons(jButton3, redish);    // jButton3: Remove Photo
    
    // Image Styling
    p_image.setBorder(BorderFactory.createLineBorder(navy, 2));
    p_image.setOpaque(true);
    p_image.setBackground(new Color(245, 245, 245));
    
    if (p_image.getIcon() == null) {
        p_image.setText("<html><center><font size='5'>+</font><br>Add Photo</center></html>");
        p_image.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
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
    
    
    private void loadCustomerProfile() {
    int userId = CONFIG.Session.getUserId();

    String sql = "SELECT * FROM tbl_users WHERE user_id = ?";

    try (java.sql.Connection conn = CONFIG.config.connectDB();
         java.sql.PreparedStatement pst = conn.prepareStatement(sql)) {

        pst.setInt(1, userId);
        java.sql.ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            lblID.setText("User ID: " + rs.getInt("user_id"));
            lblName.setText("Name: " + rs.getString("user_name"));
            lblEmail.setText("Email: " + rs.getString("user_email"));
            lblAddress.setText("Address: " + rs.getString("user_address"));

            String imagePath = rs.getString("user_image");

            if (imagePath != null && !imagePath.isEmpty()) {
                displayImage(imagePath);
            } else {
                p_image.setIcon(null);
                p_image.setText("+ Add Photo");
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
            p_image.getWidth(),
            p_image.getHeight(),
            Image.SCALE_SMOOTH
        );
        p_image.setIcon(new ImageIcon(img));
        p_image.setText("");
    } catch (Exception e) {
        System.out.println("Image Error: " + e.getMessage());
    }
}
    
    
    String selectedImagePath = "";

private void browseImage() {
    javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
    chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Images", "jpg", "png", "jpeg"));

    int result = chooser.showOpenDialog(null);

    if (result == javax.swing.JFileChooser.APPROVE_OPTION) {
        java.io.File file = chooser.getSelectedFile();
        selectedImagePath = file.getAbsolutePath();

        displayImage(selectedImagePath);
        saveImage(selectedImagePath);
    }
}

private void saveImage(String path) {
    String sql = "UPDATE tbl_users SET user_image = ? WHERE user_id = ?";

    try (java.sql.Connection conn = CONFIG.config.connectDB();
         java.sql.PreparedStatement pst = conn.prepareStatement(sql)) {

        pst.setString(1, path);
        pst.setInt(2, CONFIG.Session.getUserId());

        int result = pst.executeUpdate();

        if (result > 0) {
            javax.swing.JOptionPane.showMessageDialog(null, "Profile picture saved!");
        }

    } catch (Exception e) {
        System.out.println("Save Error: " + e.getMessage());
    }
}


private void removePhoto() {
    int confirm = javax.swing.JOptionPane.showConfirmDialog(
        null, "Remove photo?", "Confirm",
        javax.swing.JOptionPane.YES_NO_OPTION
    );

    if (confirm == javax.swing.JOptionPane.YES_OPTION) {
        String sql = "UPDATE tbl_users SET user_image = NULL WHERE user_id = ?";

        try (java.sql.Connection conn = CONFIG.config.connectDB();
             java.sql.PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, CONFIG.Session.getUserId());
            pst.executeUpdate();

            loadCustomerProfile();

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
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lblName = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        lblAddress = new javax.swing.JLabel();
        p_image = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        lblID = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(35, 66, 106));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/Blue White Modern Minimalist Interior Designer Personal Branding Logo(1)(1).jpg"))); // NOI18N

        jButton1.setBackground(new java.awt.Color(35, 66, 106));
        jButton1.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Back to Home");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(35, 66, 106));
        jButton4.setFont(new java.awt.Font("Segoe UI Black", 0, 13)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Edit Profile");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jButton1)
                .addGap(30, 30, 30)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(339, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(27, 28, -1, 590));

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 0, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(35, 66, 106));
        jLabel2.setText("MY PROFILE");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 40, 220, 70));

        jPanel3.setBackground(new java.awt.Color(245, 245, 245));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblName.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        lblName.setForeground(new java.awt.Color(35, 66, 106));
        lblName.setText("Name:");
        jPanel3.add(lblName, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 261, 35));

        lblEmail.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        lblEmail.setForeground(new java.awt.Color(35, 66, 106));
        lblEmail.setText("Email:");
        jPanel3.add(lblEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, 285, 38));

        lblAddress.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        lblAddress.setForeground(new java.awt.Color(35, 66, 106));
        lblAddress.setText("Address:");
        jPanel3.add(lblAddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, 346, 39));

        p_image.setBackground(new java.awt.Color(255, 255, 255));
        p_image.setPreferredSize(new java.awt.Dimension(120, 120));
        jPanel3.add(p_image, new org.netbeans.lib.awtextra.AbsoluteConstraints(171, 13, 159, 121));

        jButton2.setBackground(new java.awt.Color(35, 66, 106));
        jButton2.setFont(new java.awt.Font("Segoe UI Black", 0, 13)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Add Photo");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 150, 140, -1));

        jButton3.setBackground(new java.awt.Color(35, 66, 106));
        jButton3.setFont(new java.awt.Font("Segoe UI Black", 0, 13)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Remove Photo");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 150, 140, 30));

        lblID.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        lblID.setForeground(new java.awt.Color(35, 66, 106));
        lblID.setText("User ID:");
        jPanel3.add(lblID, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 380, 40));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 110, 530, 420));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 960, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 689, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
     browseImage();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
      removePhoto();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
           udashboard dash = new udashboard();
    dash.setVisible(true);
    this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
     int id = CONFIG.Session.getUserId();
    
    // Kuhaon ang data sa labels (I-replace ang labels sumala sa imong design)
    String name = lblName.getText().replace("Name: ", "").trim();
    String email = lblEmail.getText().replace("Email: ", "").trim();
    String address = lblAddress.getText().replace("Address: ", "").trim();
    
    // Tawgon ang bag-ong JFrame
    EditCustomerProfile ecp = new EditCustomerProfile(id, name, email, address, this);
    
    // Refresh listener
    ecp.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosed(java.awt.event.WindowEvent e) {
            loadCustomerProfile(); // Tawgon ang imong private method para refresh
        }
    });

    ecp.setVisible(true);
    ecp.setLocationRelativeTo(null);
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
            java.util.logging.Logger.getLogger(cusprofile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(cusprofile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(cusprofile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(cusprofile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new cusprofile().setVisible(true);
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblAddress;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel p_image;
    // End of variables declaration//GEN-END:variables
}
