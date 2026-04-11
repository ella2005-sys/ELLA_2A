
package ADMIN;

import CONFIG.config;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.ImageIcon;
import java.awt.Image;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import javax.swing.JLabel;

public class adminprofile extends javax.swing.JFrame {

    // Constructor: pass the logged-in admin's ID
   public adminprofile() {
        initComponents();
        loadAdminProfile();
        new CONFIG.config().sessionGuard(this);
        makeCircle(p_image);
        
       // I-set ang icon as null sa sugod ug butangi og "+" text
    if (p_image.getIcon() == null) {
        p_image.setText("<html><body style='text-align:center'><font size='10' color='#23426A'>+</font><br><font size='2' color='#888888'>Add Photo</font></body></html>");
    }
    p_image.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        
        // Color Palette
    java.awt.Color navy = new java.awt.Color(35, 66, 106);

   jPanel3.setBackground(java.awt.Color.WHITE); // Puti para professional
    jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 230, 230), 1));
    
    // 3. Button Styling
    styleAdminButtons(changephoto, navy);
    styleAdminButtons(remove, new java.awt.Color(180, 40, 40)); // Red para sa Remove
    applyAdminButtonStyle(jButton1, navy); // Sidebar button
    applyAdminButtonStyle(changeprofile, navy); 
    
    // Manual Action Listener for Home Redirection
    jButton1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            admindashboard ad = new admindashboard();
            ad.setVisible(true);
            ad.setLocationRelativeTo(null);
            dispose();
        }
    });
    

    java.awt.Color brown = new java.awt.Color(106, 75, 35);
    java.awt.Color warningRed = new java.awt.Color(180, 40, 40);
    java.awt.Color refreshColor = new java.awt.Color(40, 100, 40); 
    
    // I-apply ang Hover Effect sa Sidebar Buttons
    applyAdminButtonStyle(jButton1, navy);  // Back to Home
    }
   
    private void applyAdminButtonStyle(javax.swing.JButton btn, java.awt.Color baseColor) {
    btn.setBorderPainted(false);
    btn.setFocusPainted(false);
    btn.setContentAreaFilled(false);
    btn.setOpaque(true);
    btn.setBackground(baseColor);
    btn.setForeground(java.awt.Color.WHITE);
    btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    btn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    // Initial padding
    btn.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 20, 0, 0));

    btn.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            // Inig tapat sa mouse: mo-bright ug naay puti nga linya sa left
            btn.setBackground(baseColor.brighter());
            btn.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 5, 0, 0, java.awt.Color.WHITE));
        }
        @Override
        public void mouseExited(java.awt.event.MouseEvent evt) {
            // Inig kaws sa mouse: mobalik sa normal
            btn.setBackground(baseColor);
            btn.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 20, 0, 0));
        }
    });
}
   
   private void makeCircle(JLabel label) {
    label = new JLabel() {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setClip(new Ellipse2D.Double(0, 0, getWidth(), getHeight()));
            super.paintComponent(g2);
        }
    };
}
   
   String selectedImagePath = "";

    private void browseImage() {
    JFileChooser browseImageFile = new JFileChooser();
    FileNameExtensionFilter fnef = new FileNameExtensionFilter("IMAGES", "png", "jpg", "jpeg");
    browseImageFile.addChoosableFileFilter(fnef);
    
    int showOpenDialogue = browseImageFile.showOpenDialog(null);

    if (showOpenDialogue == JFileChooser.APPROVE_OPTION) {
        File selectedFile = browseImageFile.getSelectedFile();
        selectedImagePath = selectedFile.getAbsolutePath();
        
        // 1. I-display sa screen
        displayImage(selectedImagePath);
        
        // 2. I-SAVE SA DATABASE DAYON
        saveImageToDatabase(selectedImagePath);
    }
}

    private void saveImageToDatabase(String path) {
    int userId = CONFIG.Session.getUserId();
    String sql = "UPDATE tbl_users SET user_image = ? WHERE user_id = ?";

    try (java.sql.Connection conn = CONFIG.config.connectDB();
         java.sql.PreparedStatement pst = conn.prepareStatement(sql)) {

        pst.setString(1, path);
        pst.setInt(2, userId);

        int result = pst.executeUpdate();
        if (result > 0) {
            JOptionPane.showMessageDialog(null, "Profile picture saved successfully!");
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Save Error: " + e.getMessage());
    }
}

// Helper method para i-resize ang image sa label
    private void displayImage(String path) {
    try {
        ImageIcon ii = new ImageIcon(path);
        // Kinahanglan square ang Width ug Height sa p_image sa Design tab (e.g., 120x120)
        int labelWidth = p_image.getWidth();
        int labelHeight = p_image.getHeight();
        
        if (labelWidth <= 0 || labelHeight <= 0) {
            labelWidth = 120; // Default fallback
            labelHeight = 120;
        }

        Image img = ii.getImage().getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);
        p_image.setIcon(new ImageIcon(img));
        p_image.setText(""); 
    } catch (Exception e) {
        System.out.println("Display Image Error: " + e.getMessage());
    }
}

    private void loadAdminProfile() {
    int userId = CONFIG.Session.getUserId();
    String sql = "SELECT * FROM tbl_users WHERE user_id = ?";

    try (java.sql.Connection conn = CONFIG.config.connectDB();
         java.sql.PreparedStatement pst = conn.prepareStatement(sql)) {

        pst.setInt(1, userId);
        java.sql.ResultSet rs = pst.executeQuery(); // Diri gi-declare ang rs

        if (rs.next()) {
            // Text data
            lblAccountId.setText("<html><font color='#888888'>Account ID:</font> &nbsp; " + rs.getString("user_id") + "</html>");
            lblName.setText("<html><font color='#888888'>Full Name:</font> &nbsp; " + rs.getString("user_name") + "</html>");
            lblEmail.setText("<html><font color='#888888'>Email Address:</font> &nbsp; " + rs.getString("user_email") + "</html>");
            
            String status = rs.getString("u_status");
            String color = status.equalsIgnoreCase("Active") ? "#27ae60" : "#e67e22";
            lblStatus.setText("<html><font color='#888888'>Account Status:</font> &nbsp; <font color='" + color + "'><b>" + status + "</b></font></html>");
            
            // Image data - NAA NI SA SULOD SA IF(RS.NEXT()) PARA DILI ERROR
            String imagePath = rs.getString("user_image");

            if (imagePath != null && !imagePath.isEmpty()) {
                displayImage(imagePath);
            } else {
                p_image.setIcon(null);
                p_image.setText("<html><center><font size='6' color='#23426A'>+</font><br><font size='2' color='#888888'>ADD PHOTO</font></center></html>");
            }
        }
    } catch (Exception e) {
        System.out.println("Load Admin Profile error: " + e.getMessage());
    }
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
    
    private void applyDashboardStyle(javax.swing.JButton btn, java.awt.Color baseColor) {
    btn.setBorderPainted(false);
    btn.setFocusPainted(false);
    btn.setContentAreaFilled(false); 
    btn.setOpaque(true); 
    btn.setBackground(baseColor);
    btn.setForeground(java.awt.Color.WHITE);
    btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    btn.setFont(new java.awt.Font("Segoe UI Semibold", java.awt.Font.PLAIN, 16));
    
    btn.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            // Lighter navy effect on hover
            btn.setBackground(new java.awt.Color(45, 86, 136)); 
        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent evt) {
            btn.setBackground(baseColor);
        }
    });
}
    
    private void updateProfile() {
    int userId = CONFIG.Session.getUserId();
    String sql = "UPDATE tbl_users SET user_image = ? WHERE user_id = ?";

    try (java.sql.Connection conn = CONFIG.config.connectDB();
         java.sql.PreparedStatement pst = conn.prepareStatement(sql)) {

        pst.setString(1, selectedImagePath); // Ang path gikan sa browseImage()
        pst.setInt(2, userId);

        int rows = pst.executeUpdate();
        if (rows > 0) {
            JOptionPane.showMessageDialog(null, "Profile Picture Updated Successfully!");
        }
    } catch (Exception e) {
        System.out.println("Update Profile Error: " + e.getMessage());
    }
}
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        changeprofile = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        lblName = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        lblAccountId = new javax.swing.JLabel();
        p_image = new javax.swing.JLabel() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

                // Maghimo og circle clip base sa size sa label
                int diameter = Math.min(getWidth(), getHeight());
                int x = (getWidth() - diameter) / 2;
                int y = (getHeight() - diameter) / 2;

                g2.setClip(new java.awt.geom.Ellipse2D.Double(x, y, diameter, diameter));
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        p_image.setOpaque(false);
        changephoto = new javax.swing.JButton();
        remove = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(35, 66, 106));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setFont(new java.awt.Font("Segoe UI Black", 0, 13)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Back to Home");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 162, 224, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/Blue White Modern Minimalist Interior Designer Personal Branding Logo(1)(1).jpg"))); // NOI18N
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(66, 13, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 0, 15)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("MENU");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(116, 123, -1, -1));

        changeprofile.setFont(new java.awt.Font("Segoe UI Black", 0, 13)); // NOI18N
        changeprofile.setForeground(new java.awt.Color(255, 255, 255));
        changeprofile.setText("Edit Profile");
        changeprofile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeprofileActionPerformed(evt);
            }
        });
        jPanel2.add(changeprofile, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, 220, 30));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 280, 580));

        jPanel3.setBackground(new java.awt.Color(245, 245, 245));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblName.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        lblName.setForeground(new java.awt.Color(35, 66, 106));
        lblName.setText("Name: ");
        jPanel3.add(lblName, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 230, -1, -1));

        lblEmail.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        lblEmail.setForeground(new java.awt.Color(35, 66, 106));
        lblEmail.setText("Email:");
        jPanel3.add(lblEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 320, -1, -1));

        lblStatus.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        lblStatus.setForeground(new java.awt.Color(35, 66, 106));
        lblStatus.setText("Status:");
        jPanel3.add(lblStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 320, -1, -1));

        lblAccountId.setBackground(new java.awt.Color(35, 66, 106));
        lblAccountId.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        lblAccountId.setForeground(new java.awt.Color(35, 66, 106));
        lblAccountId.setText("User ID: ");
        jPanel3.add(lblAccountId, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, -1, -1));

        p_image.setPreferredSize(new java.awt.Dimension(120, 120));
        jPanel3.add(p_image, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 20, 120, 100));

        changephoto.setBackground(new java.awt.Color(35, 66, 106));
        changephoto.setFont(new java.awt.Font("Segoe UI Black", 0, 15)); // NOI18N
        changephoto.setForeground(new java.awt.Color(255, 255, 255));
        changephoto.setText("Change Photo");
        changephoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changephotoActionPerformed(evt);
            }
        });
        jPanel3.add(changephoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 150, 150, -1));

        remove.setBackground(new java.awt.Color(35, 66, 106));
        remove.setFont(new java.awt.Font("Segoe UI Black", 0, 15)); // NOI18N
        remove.setForeground(new java.awt.Color(255, 255, 255));
        remove.setText("Remove");
        remove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeActionPerformed(evt);
            }
        });
        jPanel3.add(remove, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 150, 150, -1));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 140, 500, 380));

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(35, 66, 106));
        jLabel1.setText("MY PROFILE");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 40, 230, 51));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 868, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 632, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       ADMIN.admindashboard ad = new ADMIN.admindashboard();
    ad.setVisible(true);
    ad.setLocationRelativeTo(null);
    this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void changephotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changephotoActionPerformed
       
        browseImage();
    
    }//GEN-LAST:event_changephotoActionPerformed

    private void removeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeActionPerformed
      int confirm = javax.swing.JOptionPane.showConfirmDialog(null, "Remove photo?", "Confirm", javax.swing.JOptionPane.YES_NO_OPTION);
    if (confirm == javax.swing.JOptionPane.YES_OPTION) {
        String sql = "UPDATE tbl_users SET user_image = NULL WHERE user_id = ?";
        try (java.sql.Connection conn = CONFIG.config.connectDB();
             java.sql.PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, CONFIG.Session.getUserId());
            pst.executeUpdate();
            loadAdminProfile(); // I-refresh ang screen
        } catch (Exception e) { System.out.println(e.getMessage()); }
    }
    }//GEN-LAST:event_removeActionPerformed

    private void changeprofileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeprofileActionPerformed
   // 1. Kuhaa ang ID sa admin
    int id = CONFIG.Session.getUserId();

    // 2. Kuhaa ang current Name ug Email, unya limpyohi ang HTML tags ug "&nbsp;"
    String cleanName = lblName.getText().replaceAll("<[^>]*>", "").replace("Full Name:", "").replace("&nbsp;", "").trim();
    String cleanEmail = lblEmail.getText().replaceAll("<[^>]*>", "").replace("Email Address:", "").replace("&nbsp;", "").trim();

    // 3. GI-FIX: Gidugangan og "this" para mo-match sa bag-ong constructor
    // Kay 4 na kabuok parameters ang gipangayo sa EditAdminProfile krun
    EditAdminProfile edit = new EditAdminProfile(id, cleanName, cleanEmail, this); 
    
    edit.setVisible(true);
    edit.setLocationRelativeTo(null);

    // 4. Listener para sa Refresh
    edit.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosed(java.awt.event.WindowEvent e) {
            loadAdminProfile(); 
        }
    });
    }//GEN-LAST:event_changeprofileActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(adminprofile.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Launch the form */
        java.awt.EventQueue.invokeLater(() -> {
            if (CONFIG.Session.getUserId() == 0) { // use getter
                JOptionPane.showMessageDialog(
                        null,
                        "Please Login First!",
                        "Access Denied",
                        JOptionPane.ERROR_MESSAGE
                );
                MAIN.login loginFrame = new MAIN.login();
                loginFrame.setVisible(true);
                loginFrame.pack();
                loginFrame.setLocationRelativeTo(null);
            } else {
                new adminprofile().setVisible(true); // open profile if logged in
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton changephoto;
    private javax.swing.JButton changeprofile;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblAccountId;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel p_image;
    private javax.swing.JButton remove;
    // End of variables declaration//GEN-END:variables

   
}
