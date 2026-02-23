/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package USER;

import ADMIN.usermanagement;
import MAIN.landingpage;
import javax.swing.JOptionPane;


public class udashboard extends javax.swing.JFrame {

    public udashboard() {
        initComponents();       
    applyModernFont(jLabel_Welcome, 18, true);
    jLabel_Welcome.setForeground(java.awt.Color.WHITE);

    // 2. Sidebar Buttons - Semi-bold and cleaner
    applyModernFont(jButton1, 14, true);     // Home
    applyModernFont(BookAService, 14, true); // Book Service
    applyModernFont(jButton5, 14, true);     // Profile
    
    // 3. The Dashboard Title
    applyModernFont(jLabel4, 28, true); // CUSTOMER DASHBOARD
    
    // 4. The Table Header (Crucial for a "Non-Plain" look)
    tbl_available_services.getTableHeader().setFont(new java.awt.Font("Segoe UI Semibold", java.awt.Font.PLAIN, 14));
        
        // 1. Table Spacing and Font
    tbl_available_services.setRowHeight(35); // Makes rows taller and modern
    tbl_available_services.setFont(new java.awt.Font("Segoe UI", 0, 14));
    tbl_available_services.setShowGrid(false); // Removes the old-school grid lines
    tbl_available_services.setIntercellSpacing(new java.awt.Dimension(0, 0));

// 2. Modern Blue Header
    tbl_available_services.getTableHeader().setBackground(new java.awt.Color(35, 66, 106)); // Your Navy Blue
    tbl_available_services.getTableHeader().setForeground(java.awt.Color.WHITE);
    tbl_available_services.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
    tbl_available_services.getTableHeader().setPreferredSize(new java.awt.Dimension(100, 40));

// 3. Selection Color
    tbl_available_services.setSelectionBackground(new java.awt.Color(106, 75, 35)); // Your Brown color for selection
    tbl_available_services.setSelectionForeground(java.awt.Color.WHITE);

    modernizeButton(jButton1);      // Home
    modernizeButton(BookAService);  // Book a Service
    modernizeButton(jButton3);      // Appointments
    modernizeButton(jButton4);      // History
    modernizeButton(jButton5);      // Profile
    
    // Style the Logout button differently (Reddish-brown)
    modernizeButton(jButton6);
    jButton6.setBackground(new java.awt.Color(106, 75, 35));
     
    
    jComboBox1.setBackground(new java.awt.Color(255, 255, 255)); // White background
    jComboBox1.setForeground(new java.awt.Color(35, 66, 106)); // Navy text
    jComboBox1.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
    jComboBox1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(35, 66, 106), 1));
        
        new CONFIG.config().sessionGuard(this); // Ensure user is logged in
        jLabel_Welcome.setText("Customer: " + CONFIG.Session.getName());
        loadAvailableServices();
    }
    
    private void styleButton(javax.swing.JButton btn) {
    btn.setBorderPainted(false);
    btn.setFocusPainted(false);
    btn.setContentAreaFilled(true);
    btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    
    btn.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            btn.setBackground(new java.awt.Color(45, 86, 136)); // Slightly lighter blue
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
            btn.setBackground(new java.awt.Color(35, 66, 106)); // Original Navy Blue
        }
    });
}
    
    
    private void modernizeButton(javax.swing.JButton btn) {
    // 1. Remove the default 'old' look
    btn.setBorderPainted(false);
    btn.setFocusPainted(false);
    btn.setContentAreaFilled(false); // Makes it opaque/transparent-ready
    btn.setOpaque(true); 
    btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    
    // 2. Set the modern font
    btn.setFont(new java.awt.Font("Segoe UI Semibold", java.awt.Font.PLAIN, 16));
    
    // 3. Add Hover Interactivity
    btn.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            // Lighter blue/opacity effect when hovering
            btn.setBackground(new java.awt.Color(45, 86, 136)); 
        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent evt) {
            // Return to original navy blue
            btn.setBackground(new java.awt.Color(35, 66, 106));
        }
    });
}
    
    private void applyModernFont(javax.swing.JComponent comp, int size, boolean isBold) {
    // Segoe UI is the standard "Modern Professional" font for Windows apps
    String fontName = "Segoe UI";
    int style = isBold ? java.awt.Font.BOLD : java.awt.Font.PLAIN;
    comp.setFont(new java.awt.Font(fontName, style, size));
}
    
    
    
    
    
    
    
     private void goHome() {
        // Placeholder: redirect to home page
        JOptionPane.showMessageDialog(this, "Go Home clicked");
    }

       private void bookService() {
        // Placeholder: redirect to booking page
        JOptionPane.showMessageDialog(this, "Book Service clicked");
    }

       private void openProfile() {
        new userprofile().setVisible(true);
        this.dispose();
    }
       private void logout() {
        landingpage lp = new landingpage();
        lp.setVisible(true);
        lp.setLocationRelativeTo(null);
        this.dispose();
    }
       
       
  public void loadAvailableServices() {
    try {
        CONFIG.config conf = new CONFIG.config();
        // Use "*" to grab all columns. This avoids the "no such column" error.
        String sql = "SELECT * FROM tbl_services";
        
        conf.displayData(sql, tbl_available_services); 
        
    } catch (Exception e) {
        // This will now tell us if the TABLE name itself is the problem
        javax.swing.JOptionPane.showMessageDialog(null, "Display Error: " + e.getMessage());
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        BookAService = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel_Welcome = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_available_services = new javax.swing.JTable();
        jComboBox1 = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(35, 66, 106));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setBackground(new java.awt.Color(35, 66, 106));
        jButton1.setFont(new java.awt.Font("Segoe UI Black", 0, 13)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Home");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 240, 145, -1));

        BookAService.setBackground(new java.awt.Color(35, 66, 106));
        BookAService.setFont(new java.awt.Font("Segoe UI Black", 0, 13)); // NOI18N
        BookAService.setForeground(new java.awt.Color(255, 255, 255));
        BookAService.setText("Book a Service");
        BookAService.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BookAServiceActionPerformed(evt);
            }
        });
        jPanel2.add(BookAService, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 300, 145, -1));

        jButton3.setBackground(new java.awt.Color(35, 66, 106));
        jButton3.setFont(new java.awt.Font("Segoe UI Black", 0, 13)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("My Appointments");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 360, -1, -1));

        jButton4.setBackground(new java.awt.Color(35, 66, 106));
        jButton4.setFont(new java.awt.Font("Segoe UI Black", 0, 13)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Service History");
        jPanel2.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 420, 145, -1));

        jButton5.setBackground(new java.awt.Color(35, 66, 106));
        jButton5.setFont(new java.awt.Font("Segoe UI Black", 0, 13)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("My Profile");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 480, 145, -1));

        jButton6.setBackground(new java.awt.Color(35, 66, 106));
        jButton6.setFont(new java.awt.Font("Segoe UI Black", 0, 13)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Logout");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 580, -1, 30));
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(174, 133, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/Blue White Modern Minimalist Interior Designer Personal Branding Logo(1)(1).jpg"))); // NOI18N
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 0, 15)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("MENU");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 200, -1, -1));

        jLabel_Welcome.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel_Welcome.setForeground(new java.awt.Color(204, 255, 255));
        jLabel_Welcome.setText("Welcome,");
        jPanel2.add(jLabel_Welcome, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 130, 110, 26));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 310, 640));

        jPanel3.setBackground(new java.awt.Color(35, 66, 106));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Segoe UI Black", 0, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("CUSTOMER DASHBOARD");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 460, 60));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, 490, 80));

        tbl_available_services.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tbl_available_services);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 190, 490, 170));

        jComboBox1.setBackground(new java.awt.Color(35, 66, 106));
        jComboBox1.setFont(new java.awt.Font("Segoe UI Black", 0, 13)); // NOI18N
        jComboBox1.setForeground(new java.awt.Color(255, 255, 255));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cleaning & Housekeeping", "Maintenance & Repair", "Landscaping & Outdoor", "Renovation & Contracting ", "Specialized Services " }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 160, 190, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 849, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void BookAServiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BookAServiceActionPerformed
        int row = tbl_available_services.getSelectedRow();
    
    if(row != -1) { // Check if a row is actually selected
        // 2. Extract data from the table (Adjust column indices as needed)
        int id = Integer.parseInt(tbl_available_services.getValueAt(row, 0).toString());
        String name = tbl_available_services.getValueAt(row, 1).toString();
        double price = Double.parseDouble(tbl_available_services.getValueAt(row, 3).toString());
        
        // 3. Open BookingForm and pass the data
        BookingForm bf = new BookingForm(id, name, price);
        bf.setVisible(true);
        bf.setLocationRelativeTo(null); // Centers the form
        
        // 4. Close the dashboard
        this.dispose(); 
    } else {
        javax.swing.JOptionPane.showMessageDialog(this, "Please select a service from the table first!");
    }
    }//GEN-LAST:event_BookAServiceActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
       new userprofile().setVisible(true);
       this.dispose();// optional (close dashboard)
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
      int confirm = javax.swing.JOptionPane.showConfirmDialog(null, 
            "Are you sure you want to logout?", "Logout", 
            javax.swing.JOptionPane.YES_NO_OPTION);
    if (confirm == javax.swing.JOptionPane.YES_OPTION) {
        new MAIN.login().setVisible(true);
        this.dispose();
    }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
String selectedCategory = jComboBox1.getSelectedItem().toString();
    try {
        CONFIG.config conf = new CONFIG.config();
        // Filters the table based on what you chose in the dropdown
        String sql = "SELECT * FROM tbl_services WHERE s_category = '" + selectedCategory + "'";
        conf.displayData(sql, tbl_available_services);
    } catch (Exception e) {
        System.out.println("Filter Error: " + e.getMessage());
    }    
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
     MyAppt appointmentsFrame = new MyAppt(); 
    
    // Show the new frame
    appointmentsFrame.setVisible(true);
    
    // Center it on the screen
    appointmentsFrame.setLocationRelativeTo(null);
    
    // Close the current dashboard so it doesn't stay open in the background
    this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed
/**
     * @param args the command line arguments
     */ // <--- You need this closing comment tag
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            if (CONFIG.Session.getUserId() == 0) {
                JOptionPane.showMessageDialog(null, "Please Login First!", "Access Denied", JOptionPane.ERROR_MESSAGE);
                new MAIN.login().setVisible(true);
            } else {
                new udashboard().setVisible(true);
            }
        });
    } // <--- Make sure this brace closes the main method
 // <--- This final brace closes the entire Class

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BookAService;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel_Welcome;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbl_available_services;
    // End of variables declaration//GEN-END:variables
}
