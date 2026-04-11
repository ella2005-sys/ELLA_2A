
package ADMIN;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import CONFIG.config;


public class Userform extends javax.swing.JFrame {

     public String action = "Add"; // Kinahanglan naay default value para dili null
    public javax.swing.JFrame parentFrame; 
    config cfg = new config();
    String destination = "";

    // I-update ang constructor para generic
    public Userform(javax.swing.JFrame parent) {
        initComponents();
    this.parentFrame = parent;
    setLocationRelativeTo(parent);
    jTextField1.setEnabled(false); 
    new CONFIG.config().sessionGuard(this);
    

// User ID field
    }
    
    public Userform(usermanagement parent) {
        initComponents();
        setLocationRelativeTo(parent);
         jTextField1.setEnabled(false);
         new CONFIG.config().sessionGuard(this);
    }

    // Kani nga constructor ang mo-fetch sa data base sa ID
    // Kani nga constructor ang mo-fetch sa data base sa ID
    public Userform(String userId, javax.swing.JFrame parent) {
    initComponents();
    this.parentFrame = parent;
    this.action = "Update";
    setLocationRelativeTo(parent);
    jTextField1.setEnabled(false); // ID is read-only

    try {
        String sql = "SELECT * FROM tbl_users WHERE user_id = ?";
        try (java.sql.Connection conn = CONFIG.config.connectDB();
             java.sql.PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setInt(1, Integer.parseInt(userId));
            try (java.sql.ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    // KANI ANG MGA FIELDS NGA NAWALA KAGANINA:
                    jTextField1.setText(rs.getString("user_id"));
                    jTextField2.setText(rs.getString("user_name"));
                    jTextField3.setText(rs.getString("user_email"));
                    jTextField4.setText(rs.getString("user_address"));
                    jTextField5.setText(rs.getString("user_number"));
                    jTextField7.setText(rs.getString("u_status"));
                    jTextField6.setText(rs.getString("user_role"));
                    jTextField8.setText(rs.getString("user_password"));

                    // I-load ang Image
                    destination = rs.getString("user_image");
                    if (destination != null && !destination.isEmpty()) {
                        java.io.File imgFile = new java.io.File(destination);
                        if(imgFile.exists()){
                            javax.swing.ImageIcon ii = new javax.swing.ImageIcon(destination);
                            java.awt.Image image = ii.getImage().getScaledInstance(
                                    user_pic.getWidth(), user_pic.getHeight(), java.awt.Image.SCALE_SMOOTH);
                            user_pic.setIcon(new javax.swing.ImageIcon(image));
                            user_pic.setText("");
                        }
                    }

                    jLabel1.setText("UPDATE USER/PROVIDER");
                    AddUser.setText("UPDATE");
                }
            }
        }
    } catch (Exception e) {
        System.out.println("Error fetching user: " + e.getMessage());
    }
}
      public void setPicture(javax.swing.JLabel label, String path) {
    try {
        javax.swing.ImageIcon icon = new javax.swing.ImageIcon(path);
        java.awt.Image img = icon.getImage().getScaledInstance(label.getWidth(), label.getHeight(), java.awt.Image.SCALE_SMOOTH);
        label.setIcon(new javax.swing.ImageIcon(img));
    } catch (Exception e) {
        System.out.println("Error setting picture: " + e.getMessage());
    }
}

    Userform() {
      
    }
    
    public void copyFile(String sourcePath, String destinationPath) {
    try {
        java.nio.file.Path source = java.nio.file.Paths.get(sourcePath);
        java.nio.file.Path target = java.nio.file.Paths.get(destinationPath);
        java.nio.file.Files.copy(source, target, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
    } catch (java.io.IOException e) {
        System.out.println("Error copying file: " + e.getMessage());
    }
}
    
    public void setDefaultIcon() {
    // I-set ang path sa imong default icon (pananglitan "+" icon)
    // Siguroha nga naa ni sa imong project folder
    String defaultPath = "src/icons/add_user_photo.png"; 
    
    try {
        javax.swing.ImageIcon ii = new javax.swing.ImageIcon(defaultPath);
        java.awt.Image image = ii.getImage().getScaledInstance(user_pic.getWidth(), user_pic.getHeight(), java.awt.Image.SCALE_SMOOTH);
        user_pic.setIcon(new javax.swing.ImageIcon(image));
    } catch (Exception e) {
        // Kung wala ang icon, butangan lang nato og text
        user_pic.setText("+ ADD PHOTO");
        user_pic.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    }
}
    
    public void setFields(String id, String name, String email, String address, String num, String status, String role, String pass) {
    jTextField1.setText(id);      // ID
    jTextField2.setText(name);    // Name
    jTextField3.setText(email);   // Email
    jTextField4.setText(address); // Address
    jTextField5.setText(num);     // Number
    jTextField7.setText(status);  // Status
    jTextField6.setText(role);    // Role
    jTextField8.setText(pass);    // Password
}
    

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        AddUser = new javax.swing.JButton();
        Cancel = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        user_pic = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(106, 75, 35));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(35, 66, 106));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("User Form");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 236, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 817, 80));

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("User Id:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 120, -1, -1));

        jTextField1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 120, 202, 25));

        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Name: ");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 170, -1, -1));

        jTextField2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 170, 200, 25));

        jLabel4.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Email:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 220, -1, -1));

        jTextField3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 220, 200, 25));

        jLabel5.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Address:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 270, -1, -1));

        jTextField4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 270, 200, 25));

        jLabel6.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Number:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 320, -1, 28));

        jTextField5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(jTextField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 320, 200, 28));

        AddUser.setBackground(new java.awt.Color(36, 66, 106));
        AddUser.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        AddUser.setForeground(new java.awt.Color(255, 255, 255));
        AddUser.setText("ADD");
        AddUser.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        AddUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddUserActionPerformed(evt);
            }
        });
        jPanel1.add(AddUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 460, 136, 55));

        Cancel.setBackground(new java.awt.Color(36, 76, 106));
        Cancel.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        Cancel.setForeground(new java.awt.Color(255, 255, 255));
        Cancel.setText("CANCEL");
        Cancel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelActionPerformed(evt);
            }
        });
        jPanel1.add(Cancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 460, 138, 55));

        jLabel7.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Role:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 380, -1, -1));

        jTextField6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(jTextField6, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 380, 210, 30));

        jLabel8.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Status:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 320, -1, -1));

        jTextField7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(jTextField7, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 320, 210, 30));

        jLabel9.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Password:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 380, -1, -1));

        jTextField8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(jTextField8, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 380, 200, 25));

        jButton1.setText("Browse");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 230, -1, -1));

        jButton2.setText("Remove");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 230, -1, -1));

        user_pic.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel1.add(user_pic, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 110, 130, 100));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelActionPerformed
       this.dispose();
    }//GEN-LAST:event_CancelActionPerformed

    private void AddUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddUserActionPerformed
    String id = jTextField1.getText();
    String name = jTextField2.getText();
    String email = jTextField3.getText();
    String address = jTextField4.getText();
    String number = jTextField5.getText();
    String status = jTextField7.getText();
    String role = jTextField6.getText();
    String pass = jTextField8.getText();

    if(name.isEmpty() || email.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Name and Email are required!");
        return;
    }

    if ("Add".equals(action)) { 
        String sql = "INSERT INTO tbl_users(user_name, user_email, user_address, user_number, user_password, u_status, user_role, user_image) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        cfg.executeSQL(sql, name, email, address, number, pass, status, role, destination);
        JOptionPane.showMessageDialog(this, "Successfully Added!");
    } 
    else if ("Update".equals(action)) {
        String sql = "UPDATE tbl_users SET user_name=?, user_email=?, user_address=?, user_number=?, user_password=?, u_status=?, user_role=?, user_image=? WHERE user_id=?";
        cfg.executeSQL(sql, name, email, address, number, pass, status, role, destination, id);
        JOptionPane.showMessageDialog(this, "Successfully Updated!");
    }

    // --- REFRESH LOGIC ---
    if (parentFrame instanceof usermanagement) {
        ((usermanagement) parentFrame).displayUsers(); 
    } else if (parentFrame instanceof Providers) {
        ((Providers) parentFrame).displayProviders(); 
    }
    
    this.dispose();
    }//GEN-LAST:event_AddUserActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
     javax.swing.JFileChooser browseImageFile = new javax.swing.JFileChooser();
    javax.swing.filechooser.FileNameExtensionFilter fnef = new javax.swing.filechooser.FileNameExtensionFilter("IMAGES", "png", "jpg", "jpeg");
    browseImageFile.addChoosableFileFilter(fnef);
    
    int showOpenDialogue = browseImageFile.showOpenDialog(null);
    if (showOpenDialogue == javax.swing.JFileChooser.APPROVE_OPTION) {
        java.io.File selectedImageFile = browseImageFile.getSelectedFile();
        destination = selectedImageFile.getAbsolutePath();
        
        // 1. PAPASON ANG "+" NGA TEXT
        user_pic.setText(""); 
        
        // 2. I-DISPLAY ANG PICTURE
        javax.swing.ImageIcon ii = new javax.swing.ImageIcon(destination);
        java.awt.Image image = ii.getImage().getScaledInstance(user_pic.getWidth(), user_pic.getHeight(), java.awt.Image.SCALE_SMOOTH);
        user_pic.setIcon(new javax.swing.ImageIcon(image));
    }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
   user_pic.setIcon(null);         // Tangtangon ang picture
    user_pic.setText("+");          // Ibalik ang plus sign
    destination = "";
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
   public static void main(String args[]) {
    /* Set the Nimbus look and feel */
    //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
    try {
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                javax.swing.UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
    } catch (ClassNotFoundException ex) {
        java.util.logging.Logger.getLogger(Userform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
        java.util.logging.Logger.getLogger(Userform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
        java.util.logging.Logger.getLogger(Userform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
        java.util.logging.Logger.getLogger(Userform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /* Create and display the form */
    java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            if (CONFIG.Session.getUserId() == 0) { // <-- Use getter
                javax.swing.JOptionPane.showMessageDialog(
                    null, 
                    "Please Login First!", 
                    "Access Denied", 
                    javax.swing.JOptionPane.ERROR_MESSAGE
                );

                MAIN.login loginFrame = new MAIN.login();
                loginFrame.setVisible(true);
                loginFrame.pack();
                loginFrame.setLocationRelativeTo(null); 
            } else {
                // Launch the user management main window
                new usermanagement().setVisible(true);
            }
        }
    });
}


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddUser;
    private javax.swing.JButton Cancel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JLabel user_pic;
    // End of variables declaration//GEN-END:variables
}
