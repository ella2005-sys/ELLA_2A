
package ADMIN;

import CONFIG.config;
import MAIN.landingpage;
import java.awt.Image;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

public class usermanagement extends JFrame {
     private config cfg = new config();
    private JTable usersTable;

    public usermanagement() {
        initComponents(); 
        new CONFIG.config().sessionGuard(this);
        setLayout(null);
        
    // 1. SETUP TABLE MODEL (Diri ra dapat ni, ayaw ibalhin)
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] { "Profile", "ID", "Name", "Email", "Address", "Status", "Role" }
        ) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return (columnIndex == 0) ? javax.swing.Icon.class : Object.class;
            }
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        // 2. STYLING
        jTable1.setRowHeight(60);
        tableStyle();

        // 3. LOAD DATA
        displayUsers();
    
        
        // I-override ang model para sa Image Column (pananglitan Column index 0)
    

        // Button actions
       AddUSer.addActionListener(e -> addUser());
        jButton2.addActionListener(e -> updateUser());
        delete.addActionListener(e -> deleteUser());
      // Setup Colors
    java.awt.Color navy = new java.awt.Color(35, 66, 106);
    java.awt.Color brown = new java.awt.Color(106, 75, 35);
    java.awt.Color warningRed = new java.awt.Color(180, 40, 40);
    java.awt.Color refreshColor = new java.awt.Color(40, 100, 40); 
    
    // I-apply ang Hover Effect sa Sidebar Buttons
    applyAdminButtonStyle(jButton5, navy);  // Back to Home
   

    
   
    
    styleAdminButtons(AddUSer, navy);
    styleAdminButtons(jButton2, navy);
    styleAdminButtons(delete, warningRed); // Pula na ang Delete button
    styleAdminButtons(jButton4, new java.awt.Color(60, 120, 60)); // Greenish para sa Search
    
    jTable1.getTableHeader().setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer() {
    @Override
    public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        javax.swing.JLabel label = (javax.swing.JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        label.setBackground(new java.awt.Color(35, 66, 106)); // Navy Blue
        label.setForeground(java.awt.Color.WHITE); // White Text
        label.setHorizontalAlignment(javax.swing.JLabel.CENTER); // Center align labels
        label.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, java.awt.Color.WHITE)); // Subtle grid sa header
        return label;
    }
});
}

    

   void loadUsers() {
        displayUsers();
    }
   private void searchUser() {
    String query = jTextField1.getText().trim();
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    model.setRowCount(0);

    String sql = "SELECT user_image, user_id, user_name, user_email, user_address, u_status, user_role FROM tbl_users " +
                 "WHERE user_id LIKE ? OR user_name LIKE ? OR user_email LIKE ?";

    try (Connection conn = cfg.connectDB();
         PreparedStatement pst = conn.prepareStatement(sql)) {

        String likeQuery = "%" + query + "%";
        pst.setString(1, likeQuery);
        pst.setString(2, likeQuery);
        pst.setString(3, likeQuery);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            String imagePath = rs.getString("user_image");
            javax.swing.Icon icon = null;
            if (imagePath != null && !imagePath.isEmpty()) {
                java.io.File f = new java.io.File(imagePath.replace("\\", "/"));
                if (f.exists()) {
                    java.awt.Image img = new ImageIcon(f.getAbsolutePath()).getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
                    icon = new CircleIcon(img, 50);
                }
            }
            model.addRow(new Object[]{icon, rs.getString("user_id"), rs.getString("user_name"), rs.getString("user_email"), rs.getString("user_address"), rs.getString("u_status"), rs.getString("user_role")});
        }
    } catch (Exception e) {
        System.out.println("Search Error: " + e.getMessage());
    }
}
   
  private void tableStyle() {
    // 1. Header Styling (Kini ang mo-usab sa user_id, user_name, etc. into Blue)
    jTable1.getTableHeader().setFont(new java.awt.Font("Segoe UI Bold", 0, 13));
    jTable1.getTableHeader().setBackground(new java.awt.Color(35, 66, 106)); // Navy Blue
    jTable1.getTableHeader().setForeground(java.awt.Color.WHITE); // Puti nga text
    jTable1.getTableHeader().setOpaque(false);
    jTable1.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 40)); // Mas baga gamay nga header

    // 2. Row Styling
    jTable1.setRowHeight(50); // Mas hayahay nga rows
    jTable1.getColumnModel().getColumn(0).setPreferredWidth(60);
    jTable1.setFont(new java.awt.Font("Segoe UI", 0, 12));
    jTable1.setGridColor(new java.awt.Color(230, 230, 230)); 
    jTable1.setShowVerticalLines(false); // Modern look (horizontal lines ra)

    // 3. Selection Color (Inig click nimo sa row)
    jTable1.setSelectionBackground(new java.awt.Color(220, 230, 250)); // Light Blue highlight
    jTable1.setSelectionForeground(new java.awt.Color(35, 66, 106)); // Navy Blue text

    // 4. ScrollPane Fix
    jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 240, 240)));
    jScrollPane1.getViewport().setBackground(java.awt.Color.WHITE);
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
            btn.setBackground(baseColor.brighter()); // Mo-light inig tapat sa mouse
        }
        @Override
        public void mouseExited(java.awt.event.MouseEvent evt) {
            btn.setBackground(baseColor); // Mobalik sa original color
        }
    });
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
 
   public void displayUsers() {
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    model.setRowCount(0);

    String sql = "SELECT user_image, user_id, user_name, user_email, user_address, u_status, user_role FROM tbl_users";

    try (Connection conn = cfg.connectDB();
         PreparedStatement pst = conn.prepareStatement(sql);
         ResultSet rs = pst.executeQuery()) {

        while (rs.next()) {
            String dbPath = rs.getString("user_image");
            Icon finalIcon = null;

            if (dbPath != null && !dbPath.isEmpty()) {
                // KANI ANG FIX: I-convert ang backslash sa database ngadto sa forward slash
                String fixedPath = dbPath.replace("\\", "/");
                java.io.File file = new java.io.File(fixedPath);

                if (file.exists()) {
                    ImageIcon temp = new ImageIcon(file.getAbsolutePath());
                    java.awt.Image img = temp.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
                    finalIcon = new CircleIcon(img, 50);
                } else {
                    System.out.println("File not found at: " + fixedPath);
                }
            }

            // KUNG WALAY IMAGE (NULL): Butangan og default nga lingin para dili blangko tan-awon
            if (finalIcon == null) {
                // Mag-himo ta og empty circle placeholder para sa mga walay picture
                finalIcon = new Icon() {
                    @Override public void paintIcon(java.awt.Component c, java.awt.Graphics g, int x, int y) {
                        java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                        g2.setColor(java.awt.Color.LIGHT_GRAY);
                        g2.fillOval(x, y, 50, 50);
                        g2.dispose();
                    }
                    @Override public int getIconWidth() { return 50; }
                    @Override public int getIconHeight() { return 50; }
                };
            }

            model.addRow(new Object[]{
                finalIcon,
                rs.getString("user_id"),
                rs.getString("user_name"),
                rs.getString("user_email"),
                rs.getString("user_address"),
                rs.getString("u_status"),
                rs.getString("user_role")
            });
        }
    } catch (Exception e) {
        System.out.println("Display Error: " + e.getMessage());
    }
}
    
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        AddUSer = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        delete = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();

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
        jPanel2.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 230, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 280, 600));

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

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 210, 580, 410));

        jPanel3.setBackground(new java.awt.Color(35, 66, 106));

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("USERS TABLE");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addComponent(jLabel1)
                .addContainerGap(106, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 40, 440, 50));

        AddUSer.setBackground(new java.awt.Color(35, 66, 106));
        AddUSer.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        AddUSer.setForeground(new java.awt.Color(255, 255, 255));
        AddUSer.setText("Add User");
        AddUSer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddUSerActionPerformed(evt);
            }
        });
        jPanel1.add(AddUSer, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 160, 100, 30));

        jButton2.setBackground(new java.awt.Color(35, 66, 106));
        jButton2.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Update User");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 160, 130, 30));

        delete.setBackground(new java.awt.Color(35, 66, 106));
        delete.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        delete.setForeground(new java.awt.Color(255, 255, 255));
        delete.setText("Delete User");
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });
        jPanel1.add(delete, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 160, 120, 30));

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
        jPanel1.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 160, 90, 30));

        jButton4.setBackground(new java.awt.Color(35, 66, 6));
        jButton4.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Search");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 160, 90, 30));

        btnRefresh.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        btnRefresh.setForeground(new java.awt.Color(255, 255, 255));
        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });
        jPanel1.add(btnRefresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 110, 90, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 930, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 640, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void AddUSerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddUSerActionPerformed
    Userform form = new Userform(this); // pass parent
    form.setVisible(true);
    }//GEN-LAST:event_AddUSerActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
            // KANI NGA CODE NAA SA USERMANAGEMENT.JAVA
int row = jTable1.getSelectedRow();
if (row != -1) {
    // Column 0 = Profile Icon/Picture
    // Column 1 = Ang tinuod nga User ID (Numero)
    
    Object value = jTable1.getValueAt(row, 1); // USBA NI NGADTO SA 1
    String id = value.toString(); 
    
    UpdateUser up = new UpdateUser(id, this);
    up.setVisible(true);
}
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

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
      // I-call ang displayUsers para mapuno pag-usab ang jTable1
    displayUsers(); 
    
    // Optional: Isulti sa user nga na-update na ang table
    JOptionPane.showMessageDialog(null, "Table has been updated!");
    }//GEN-LAST:event_btnRefreshActionPerformed

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
   class CircleIcon implements javax.swing.Icon {
    private java.awt.Image image;
    private int size;
    public CircleIcon(java.awt.Image image, int size) { this.image = image; this.size = size; }
    @Override
    public void paintIcon(java.awt.Component c, java.awt.Graphics g, int x, int y) {
        java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        java.awt.geom.Ellipse2D.Double clip = new java.awt.geom.Ellipse2D.Double(x, y, size, size);
        g2.setClip(clip);
        g2.drawImage(image, x, y, size, size, null);
        g2.dispose();
    }
    @Override public int getIconWidth() { return size; }
    @Override public int getIconHeight() { return size; }
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddUSer;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton delete;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
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
