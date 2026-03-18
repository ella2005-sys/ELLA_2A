
package ADMIN;

import CONFIG.config;
import java.awt.Image;
import java.sql.ResultSet;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Providers extends javax.swing.JFrame {

   config cfg = new config();
   
    public Providers() {
        initComponents();
        setupTable();
         // Users// The "Back to Home" style
        setLocationRelativeTo(null);
        new CONFIG.config().sessionGuard(this);
        displayProviders();
        
        // 1. SETUP TABLE MODEL (With Image Support)
        setupProviderTable();

        // 2. APPLY BUTTON STYLES (Parehas sa User Management)
        java.awt.Color navy = new java.awt.Color(35, 66, 106);
        java.awt.Color warningRed = new java.awt.Color(180, 40, 40);
        java.awt.Color green = new java.awt.Color(60, 120, 60);

        styleAdminButtons(Add, navy);
        styleAdminButtons(update, navy);
        styleAdminButtons(delete, warningRed);
        styleAdminButtons(jButton5, green); // Search Button
        applySidebarButtonStyle(jButton1, navy); // Back to Home
        
        applyAdminButtonStyle(jButton1, navy);

        // 3. LOAD DATA
        displayProviders();
    }

    
    
    private void setupProviderTable() {
    DefaultTableModel model = new DefaultTableModel(
        new Object [][] {},
        new String [] { "Profile", "ID", "Name", "Email", "Contact", "Status" }
    ) {
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            // Mao ni ang sekreto: Isulti sa table nga ang Column 0 kay usa ka ICON
            if (columnIndex == 0) {
                return javax.swing.Icon.class;
            }
            return Object.class;
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    jTable1.setModel(model);

    // Aesthetics
    jTable1.setRowHeight(60);
    // ... ang uban nimo nga styling sa table header ...
}
    
    private void setupTable() {
        String[] headers = {"ID", "Full Name", "Email", "Contact", "Status"};
        jTable1.setModel(new DefaultTableModel(null, headers));
        
        // Modern Table Look
        jTable1.setRowHeight(30);
        jTable1.getTableHeader().setBackground(new java.awt.Color(35, 66, 106));
        jTable1.getTableHeader().setForeground(java.awt.Color.WHITE);
        jTable1.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
    }
    
    private void styleAdminButtons(javax.swing.JButton btn, java.awt.Color baseColor) {
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setBackground(baseColor);
        btn.setForeground(java.awt.Color.WHITE);
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent evt) { btn.setBackground(baseColor.brighter()); }
            @Override public void mouseExited(java.awt.event.MouseEvent evt) { btn.setBackground(baseColor); }
        });
    }
    
    private void applySidebarButtonStyle(javax.swing.JButton btn, java.awt.Color baseColor) {
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setBackground(baseColor);
        btn.setForeground(java.awt.Color.WHITE);
        btn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btn.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 20, 0, 0));
    }
    
    public void displayProviders() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        String sql = "SELECT user_image, user_id, user_name, user_email, user_number, u_status FROM tbl_users WHERE user_role = 'Provider'";

        try (Connection conn = cfg.connectDB();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                String dbPath = rs.getString("user_image");
                Icon finalIcon = null;

                if (dbPath != null && !dbPath.isEmpty()) {
                    String fixedPath = dbPath.replace("\\", "/");
                    java.io.File file = new java.io.File(fixedPath);
                    if (file.exists()) {
                        ImageIcon temp = new ImageIcon(file.getAbsolutePath());
                        java.awt.Image img = temp.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
                        finalIcon = new CircleIcon(img, 50);
                    }
                }

                if (finalIcon == null) {
                    finalIcon = new DefaultCircleIcon(50);
                }

                model.addRow(new Object[]{
                    finalIcon,
                    rs.getString("user_id"),
                    rs.getString("user_name"),
                    rs.getString("user_email"),
                    rs.getString("user_number"),
                    rs.getString("u_status")
                });
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
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
    
    // Padding para dili pilit sa kilid ang text
    btn.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 20, 0, 0));

    btn.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            // Inig tapat sa mouse: mo-bright ug naay puti nga linya sa left (Hover Effect)
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
   
   // KINI NGA MGA CODE NAA SA USERMANAGEMENT.JAVA
       // UPDATE BUTTON SA PROVIDERS.JAVA
    private void updateProvider() {
    int row = jTable1.getSelectedRow();
    if (row != -1) {
        // Index 0 = Profile Icon (Mao ni ang nagdala og error)
        // Index 1 = User ID (Mao ni ang numero nga atong kinahanglan)
        Object value = jTable1.getValueAt(row, 1); 
        String id = value.toString(); 
        
        // I-pass ang ID ug i-cast ang 'this' ngadto sa JFrame
        UpdateUser up = new UpdateUser(id, (usermanagement)(javax.swing.JFrame)this);
        up.setVisible(true);
    } else {
        JOptionPane.showMessageDialog(this, "Palihug pagpili og provider!");
    }
}

private void deleteProvider() {
    int row = jTable1.getSelectedRow();
    if (row != -1) {
        String id = jTable1.getValueAt(row, 1).toString();
        int confirm = JOptionPane.showConfirmDialog(this, "Delete ID: " + id + "?", "Confirm", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            cfg.executeSQL("DELETE FROM tbl_users WHERE user_id = ?", id);
            displayUsers(); // Refresh dayon ang table
        }
    } else {
        JOptionPane.showMessageDialog(this, "Select a provider first!");
    }
}

        public void displayUsers() {
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    model.setRowCount(0); // Limpyo sa table

    // 1. Siguraduha nga sakto ang order sa SQL columns
    String sql = "SELECT user_image, user_id, user_name, user_email, user_address, u_status, user_role FROM tbl_users";

    try (Connection conn = cfg.connectDB();
         PreparedStatement pst = conn.prepareStatement(sql);
         ResultSet rs = pst.executeQuery()) {

        while (rs.next()) {
            // 2. Pagkuha sa Image (Simple Version)
            String path = rs.getString("user_image");
            Icon pic = null;
            if (path != null && !path.isEmpty()) {
                java.io.File f = new java.io.File(path.replace("\\", "/"));
                if (f.exists()) {
                    Image img = new ImageIcon(f.getAbsolutePath()).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                    pic = new CircleIcon(img, 50);
                }
            }

            // 3. I-ADD SA ROW (KANI ANG IMPORTANTE)
            // Column 0 = Profile Icon
            // Column 1 = user_id (Mao ni ang gamiton sa Update/Delete)
            model.addRow(new Object[]{
                pic, 
                rs.getString("user_id"), 
                rs.getString("user_name"), 
                rs.getString("user_email"), 
                rs.getString("user_address"), 
                rs.getString("u_status"), 
                rs.getString("user_role")
            });
        }
    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
}
        
        
    
    
    
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        Add = new javax.swing.JButton();
        update = new javax.swing.JButton();
        delete = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(35, 66, 106));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/Blue White Modern Minimalist Interior Designer Personal Branding Logo(1)(1).jpg"))); // NOI18N
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 13, -1, 99));

        jButton1.setBackground(new java.awt.Color(35, 66, 106));
        jButton1.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Back to Home");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 240, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 0, 15)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("MENU");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 130, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 300, 578));

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

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 230, 530, 340));

        jPanel3.setBackground(new java.awt.Color(35, 66, 106));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 0, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("PROVIDERS MASTERLIST");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, 30));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 40, 463, 94));

        Add.setText("Add Provider");
        Add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddActionPerformed(evt);
            }
        });
        jPanel1.add(Add, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 190, 130, -1));

        update.setText("Update Provider");
        update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateActionPerformed(evt);
            }
        });
        jPanel1.add(update, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 190, 130, -1));

        delete.setText("Delete Provider");
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });
        jPanel1.add(delete, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 190, 130, -1));
        jPanel1.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 190, 80, 30));

        jButton5.setText("Search");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 190, -1, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 913, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
     admindashboard ad = new admindashboard();
    ad.setVisible(true);
    this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
       displayProviders();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
      int rowIndex = jTable1.getSelectedRow();

    if (rowIndex < 0) {
        JOptionPane.showMessageDialog(null, "Palihug pagpili og row nga i-delete!");
    } else {
        try {
            // COLUMN 1 gihapon ang ID diri
            String idString = jTable1.getValueAt(rowIndex, 1).toString();
            int id = Integer.parseInt(idString);

            int confirm = JOptionPane.showConfirmDialog(null, 
                    "Sigurado ka nga i-delete ang ID: " + id + "?", 
                    "Confirm Delete", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                cfg.deleteData(id, "tbl_users", "user_id");
                
                // Refresh ang table (Gamita ang displayProviders() kay mao nay naa sa imong file)
                displayProviders(); 
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
    }//GEN-LAST:event_deleteActionPerformed

    private void updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateActionPerformed
    int row = jTable1.getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Please select a provider from the table.");
    } else {
        // COLUMN 1 ang ID (dili 0 kay 0 ang Icon)
        String userId = jTable1.getValueAt(row, 1).toString(); 
        
        // I-pass ang ID ug 'this' lang (ayaw na i-cast sa usermanagement)
        // Siguraduha nga ang imong Userform constructor modawat og (String, JFrame)
        Userform uf = new Userform(userId, this); 
        uf.setVisible(true);
    }


    }//GEN-LAST:event_updateActionPerformed

    private void AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddActionPerformed
        Userform uf = new Userform(this); // 'this' refers to Providers frame
        uf.action = "Add"; 
        uf.setVisible(true);
    }//GEN-LAST:event_AddActionPerformed

    /**
     * @param args the command line arguments
     */
  public static void main(String args[]) {
    // ... (Keep the Look and Feel try-catch block as is) ...

    /* Create and display the form */
    java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            // Check if user is logged in using your Session class
            if (!CONFIG.Session.isLoggedIn()) {
                javax.swing.JOptionPane.showMessageDialog(
                    null, 
                    "Please Login First!", 
                    "Security Alert", 
                    javax.swing.JOptionPane.ERROR_MESSAGE
                );
                
                // Redirect to login
                new MAIN.login().setVisible(true);
            } else {
                new Providers().setVisible(true);
            }
        }
    });
}

  
  class CircleIcon implements Icon {
        private java.awt.Image image;
        private int size;
        public CircleIcon(java.awt.Image image, int size) { this.image = image; this.size = size; }
        @Override public void paintIcon(java.awt.Component c, java.awt.Graphics g, int x, int y) {
            java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
            g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setClip(new java.awt.geom.Ellipse2D.Double(x, y, size, size));
            g2.drawImage(image, x, y, size, size, null);
            g2.dispose();
        }
        @Override public int getIconWidth() { return size; }
        @Override public int getIconHeight() { return size; }
    }

    class DefaultCircleIcon implements Icon {
        private int size;
        public DefaultCircleIcon(int size) { this.size = size; }
        @Override public void paintIcon(java.awt.Component c, java.awt.Graphics g, int x, int y) {
            java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
            g2.setColor(java.awt.Color.LIGHT_GRAY);
            g2.fillOval(x, y, size, size);
            g2.dispose();
        }
        @Override public int getIconWidth() { return size; }
        @Override public int getIconHeight() { return size; }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Add;
    private javax.swing.JButton delete;
    private javax.swing.JButton jButton1;
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
    private javax.swing.JButton update;
    // End of variables declaration//GEN-END:variables
}
