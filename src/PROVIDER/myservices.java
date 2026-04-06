
package PROVIDER;

import ADMIN.AddService;
import CONFIG.Session;
import CONFIG.config;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;


public class myservices extends javax.swing.JFrame {
    
    config cfg = new config();
    
  

   public myservices() {
    initComponents();
    loadServices(); 

    // 1. Color Palette
    java.awt.Color navy = new java.awt.Color(35, 66, 106);
    java.awt.Color dangerRed = new java.awt.Color(150, 40, 40); 
    java.awt.Color greenSearch = new java.awt.Color(40, 167, 69); // Modern Green

    // 2. Sidebar Button Style
    applyAdminButtonStyle(home, navy);

   // 1. I-define ang imong Navy color scheme

java.awt.Color white = java.awt.Color.WHITE;

// 2. Row Styling
    jTable1.setRowHeight(50); // Mas hayahay nga rows
    jTable1.getColumnModel().getColumn(0).setPreferredWidth(60);
    jTable1.setFont(new java.awt.Font("Segoe UI", 0, 12));
    jTable1.setGridColor(new java.awt.Color(230, 230, 230)); 
    jTable1.setShowVerticalLines(false); // Modern look (horizontal lines ra)

// 3. I-style ang Table Header (Kini ang pinaka-importante)
 jTable1.getTableHeader().setFont(new java.awt.Font("Segoe UI Bold", 0, 13));
    jTable1.getTableHeader().setBackground(white); // Navy Blue
    jTable1.getTableHeader().setForeground(navy); // Puti nga text
    jTable1.getTableHeader().setOpaque(false);
    jTable1.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 40)); // Mas baga gamay nga header

// 3. Selection Color (Inig click nimo sa row)
    jTable1.setSelectionBackground(new java.awt.Color(220, 230, 250)); // Light Blue highlight
    jTable1.setSelectionForeground(new java.awt.Color(35, 66, 106)); // Navy Blue text

    // 4. ScrollPane Fix
    jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 240, 240)));
    jScrollPane1.getViewport().setBackground(java.awt.Color.WHITE);

    // 4. Action Buttons Styling
    applyDashboardStyle(AddService, navy);      // ADD
    applyDashboardStyle(jButton1, navy);        // EDIT
    applyDashboardStyle(jButton2, dangerRed);   // DELETE
    applyDashboardStyle(jButton4, greenSearch); // SEARCH (Kini na gyud ang Green!)

    // 5. Search Field Styling
    txtSearch.setBorder(javax.swing.BorderFactory.createCompoundBorder(
        javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 200, 200)),
        javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
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
   
   
  private void loadServices() {
    try {
        Connection conn = cfg.getConnection();
        int currentId = CONFIG.Session.getUserId(); 

        // SELECT * mokuha sa tanang columns base sa order sa imong screenshot
        String sql = "SELECT * FROM tbl_services WHERE provider_id = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, currentId);
        ResultSet rs = pst.executeQuery();

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
// I-set ang column names nga sakto sa imong database scheme
String[] columnNames = {"ID", "Service Name", "Category", "Price", "Status"};
model.setColumnIdentifiers(columnNames); 
model.setRowCount(0);

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getObject(1), // s_id (Index 1)
                rs.getObject(2), // s_name (Index 2)
                rs.getObject(3), // s_category (Index 3)
                rs.getObject(4), // s_price (Index 4)
                rs.getObject(5)  // s_status (Index 5)
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
     this.dispose();
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
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        AddService = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(35, 66, 106));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/Blue White Modern Minimalist Interior Designer Personal Branding Logo(1)(1).jpg"))); // NOI18N
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 30, -1, 71));

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

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 320, 660));

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

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 300, 540, 330));

        AddService.setBackground(new java.awt.Color(35, 66, 106));
        AddService.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        AddService.setForeground(new java.awt.Color(255, 255, 255));
        AddService.setText("ADD ");
        AddService.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddServiceActionPerformed(evt);
            }
        });
        jPanel1.add(AddService, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 250, 80, 40));
        jPanel1.add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 250, 110, 40));

        jButton1.setText("EDIT");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 250, 90, 40));

        jButton2.setText("DELETE");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 250, 90, 40));

        jButton4.setText("SEARCH");
        jPanel1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 250, 100, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 942, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 716, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void AddServiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddServiceActionPerformed
       AddService add = new AddService(CONFIG.Session.getUserId()); // pass provider ID
    add.setVisible(true);
     this.dispose();
    }//GEN-LAST:event_AddServiceActionPerformed

    private void homeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_homeActionPerformed
       providerdashboard dash = new providerdashboard();
    dash.setVisible(true);
    this.dispose();
    }//GEN-LAST:event_homeActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
     int row = jTable1.getSelectedRow();
     
     String sql = "UPDATE tbl_services SET s_name=?, s_category=?, s_price=?, s_status=? WHERE s_id=?";
    
    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Palihug choose a row in the table!");
    } else {
        String id = jTable1.getValueAt(row, 0).toString();
        String name = jTable1.getValueAt(row, 1).toString();
        String cat = jTable1.getValueAt(row, 2).toString();
        String price = jTable1.getValueAt(row, 3).toString();
        String status = jTable1.getValueAt(row, 4).toString();

        ADMIN.EditService edit = new ADMIN.EditService();
        
        // Tawgon ang setter nga atong gi-fix sa taas
        edit.setServiceData(id, name, cat, price, status);
        
        edit.setVisible(true);
        this.dispose(); // I-close ang current frame
    }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
      int row = jTable1.getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Please select a service to delete.");
    } else {
        String id = jTable1.getValueAt(row, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this, "Delete this service?", "Warning", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            cfg.deleteData("DELETE FROM tbl_services WHERE service_id = '" + id + "'");
            loadServices(); // Refresh table
        }
    }
    }//GEN-LAST:event_jButton2ActionPerformed

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
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
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
