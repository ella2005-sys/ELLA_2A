/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ADMIN;

import MAIN.landingpage;

/**
 *
 * @author Jingkie
 */
public class admindashboard extends javax.swing.JFrame {

    /**
     * Creates new form admindashboard
     */
public admindashboard() {
    initComponents();
    
    // 1. Setup the Top Cards
    setupCard(userCountLabel, "TOTAL USERS", p);
    setupCard(providerCountLabel, "PROVIDERS", jPane15);
    setupCard(appointmentCountLabel, "BOOKINGS", jPane16);

    // 2. Load Data
    refreshStats();
    updateRecentTable();
    
    // 3. User & Session
    jLabel_Welcome.setText("Welcome, Admin: " + CONFIG.Session.getName());
    new CONFIG.config().sessionGuard(this);
    
    // 4. Sidebar Button Styling
    java.awt.Color navy = new java.awt.Color(35, 66, 106);
    java.awt.Color logoutBrown = new java.awt.Color(106, 75, 35);

    applyAdminButtonStyle(jButton1, navy);    // Users
    applyAdminButtonStyle(providers, navy);  // Providers
    applyAdminButtonStyle(appointments, navy); 
    applyAdminButtonStyle(reports, navy);
    applyAdminButtonStyle(jButton7, navy);    // Services
    applyAdminButtonStyle(jButton5, navy);    // Profile
    applyAdminButtonStyle(jButton6, logoutBrown); // Logout

    // 5. MODERN TABLE STYLING (The "Un-plain" fix)
    recentActivityTable.setRowHeight(45); 
    recentActivityTable.setShowGrid(false);
    recentActivityTable.setIntercellSpacing(new java.awt.Dimension(0, 0));
    recentActivityTable.setBackground(java.awt.Color.WHITE);
    
    // Style the ScrollPane to remove the old-school border
    jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 240, 240)));
    jScrollPane1.getViewport().setBackground(java.awt.Color.WHITE);

    // Modern Header
    recentActivityTable.getTableHeader().setBackground(java.awt.Color.WHITE);
    recentActivityTable.getTableHeader().setForeground(new java.awt.Color(120, 130, 150));
    recentActivityTable.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
    recentActivityTable.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 40));
    recentActivityTable.getTableHeader().setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(245, 245, 245)));

    // Apply the modern Status Renderer (Pill style) to Column index 2
    recentActivityTable.getColumnModel().getColumn(2).setCellRenderer(new ModernStatusRenderer());

    // Add padding to Name and Role columns (index 0 and 1)
    javax.swing.table.DefaultTableCellRenderer paddingRenderer = new javax.swing.table.DefaultTableCellRenderer() {
        @Override
        public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            javax.swing.JLabel l = (javax.swing.JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            l.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 15, 0, 0)); 
            l.setBackground(isSelected ? new java.awt.Color(245, 248, 255) : java.awt.Color.WHITE);
            return l;
        }
    };
    recentActivityTable.getColumnModel().getColumn(0).setCellRenderer(paddingRenderer);
    recentActivityTable.getColumnModel().getColumn(1).setCellRenderer(paddingRenderer);

    // Hover effect for rows
    recentActivityTable.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        @Override
        public void mouseMoved(java.awt.event.MouseEvent e) {
            int row = recentActivityTable.rowAtPoint(e.getPoint());
            if (row > -1) {
                recentActivityTable.setRowSelectionInterval(row, row);
                recentActivityTable.setSelectionBackground(new java.awt.Color(248, 250, 255));
            }
        }
    });
}

// Modern Pill-style Status Renderer
class ModernStatusRenderer extends javax.swing.table.DefaultTableCellRenderer {
    @Override
    public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        javax.swing.JLabel label = (javax.swing.JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        label.setHorizontalAlignment(javax.swing.JLabel.CENTER);
        label.setOpaque(true); 
        
        String status = (value != null) ? value.toString() : "";
        
        if ("Approved".equalsIgnoreCase(status)) {
            label.setForeground(new java.awt.Color(0, 150, 70)); // Dark Green text
            label.setBackground(new java.awt.Color(230, 250, 240)); // Very Light Green background
        } else if ("Pending".equalsIgnoreCase(status)) {
            label.setForeground(new java.awt.Color(200, 120, 0)); // Dark Orange text
            label.setBackground(new java.awt.Color(255, 245, 220)); // Light Orange background
        } else {
            label.setBackground(java.awt.Color.WHITE);
        }

        // Create the "Pill" shape using a border
        label.setBorder(javax.swing.BorderFactory.createLineBorder(label.getBackground(), 5, true));
        
        return label;
    }
}

    
    class StatusRenderer extends javax.swing.table.DefaultTableCellRenderer {
    @Override
    public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        javax.swing.JLabel label = (javax.swing.JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        label.setHorizontalAlignment(javax.swing.JLabel.CENTER);
        label.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 11));
        
        String status = (value != null) ? value.toString() : "";
        
        if ("Approved".equalsIgnoreCase(status)) {
            label.setText("● Approved");
            label.setForeground(new java.awt.Color(0, 180, 100)); // Emerald Green
        } else if ("Pending".equalsIgnoreCase(status)) {
            label.setText("● Pending");
            label.setForeground(new java.awt.Color(255, 160, 0)); // Amber
        } else {
            label.setForeground(new java.awt.Color(150, 150, 150));
        }

        // If the row is selected, keep a subtle hint of the color but show selection
        if (isSelected) {
            label.setBackground(new java.awt.Color(230, 240, 255));
        } else {
            label.setBackground(java.awt.Color.WHITE);
        }

        return label;
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
    btn.setFont(new java.awt.Font("Segoe UI Semibold", java.awt.Font.PLAIN, 14));
    
    // Align text to the left for a cleaner look
    btn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    btn.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 20, 0, 0));

    btn.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            btn.setBackground(new java.awt.Color(55, 86, 126)); // Slightly lighter navy
            // Add a small white "bar" on the left on hover
            btn.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 5, 0, 0, java.awt.Color.WHITE));
        }
        @Override
        public void mouseExited(java.awt.event.MouseEvent evt) {
            btn.setBackground(baseColor);
            btn.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 20, 0, 0));
        }
    });
}
    
 private void setupCard(javax.swing.JPanel card, String title, javax.swing.JLabel countLabel) {
    card.removeAll();
    card.setLayout(new java.awt.BorderLayout(0, 5));
    
    // Add a slight rounded border look if you aren't using a custom Panel class
    card.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255,255,255, 50), 1));
    
    // Title Styling
    javax.swing.JLabel titleLbl = new javax.swing.JLabel(title);
    titleLbl.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12)); // Slightly smaller
    titleLbl.setForeground(new java.awt.Color(255, 255, 255, 200)); // Semi-transparent white
    titleLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    titleLbl.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 0, 0, 0));
    
    // Count Styling
    countLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 32)); // Bigger number
    countLabel.setForeground(java.awt.Color.WHITE);
    
    card.add(titleLbl, java.awt.BorderLayout.NORTH);
    card.add(countLabel, java.awt.BorderLayout.CENTER);
    
    // Improved Hover Effect (Subtle glow instead of just bright)
    java.awt.Color originalColor = card.getBackground();
    card.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            card.setBackground(originalColor.brighter());
        }
        @Override
        public void mouseExited(java.awt.event.MouseEvent evt) {
            card.setBackground(originalColor);
        }
    });
    card.revalidate();
    card.repaint();
}
   
   private void refreshStats() {
    // 1. Correct URL for SQLite file
    String url = "jdbc:sqlite:hservice.db"; 

    // SQLite doesn't use user/password, so we can omit them in the connection
    try (java.sql.Connection conn = java.sql.DriverManager.getConnection(url);
         java.sql.Statement st = conn.createStatement()) {

        // 2. Count standard Users from tbl_users
        java.sql.ResultSet rs1 = st.executeQuery("SELECT COUNT(*) FROM tbl_users WHERE user_role = 'User'");
        if (rs1.next()) {
            p.setText(String.valueOf(rs1.getInt(1))); 
        }

        // 3. Count Providers from tbl_users (filtering by the user_role column)
        java.sql.ResultSet rs2 = st.executeQuery("SELECT COUNT(*) FROM tbl_users WHERE user_role = 'Provider'");
        if (rs2.next()) {
            jPane15.setText(String.valueOf(rs2.getInt(1)));
        }

        // 4. Count Bookings from tbl_bookings
        java.sql.ResultSet rs3 = st.executeQuery("SELECT COUNT(*) FROM tbl_bookings");
        if (rs3.next()) {
            jPane16.setText(String.valueOf(rs3.getInt(1)));
        }

    } catch (java.sql.SQLException e) {
        // This will print the exact error to your NetBeans Output console
        System.out.println("Database Error: " + e.getMessage());
        
        // Visual feedback that the connection failed
        p.setText("!");
        jPane15.setText("!");
        jPane16.setText("!");
    }
}

   
   private void updateRecentTable() {
    String url = "jdbc:sqlite:hservice.db";
    // DefaultTableModel allows us to easily add rows to the JTable
    javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) recentActivityTable.getModel();
    model.setRowCount(0); // Clear existing data

    // SQL Query to get the 5 most recent users
    String sql = "SELECT user_name, user_role, u_status FROM tbl_users ORDER BY user_id DESC LIMIT 5";

    try (java.sql.Connection conn = java.sql.DriverManager.getConnection(url);
         java.sql.Statement st = conn.createStatement();
         java.sql.ResultSet rs = st.executeQuery(sql)) {

        while (rs.next()) {
            // Adding data to the table rows matching your tbl_users columns
            model.addRow(new Object[]{
                rs.getString("user_name"),
                rs.getString("user_role"),
                rs.getString("u_status")  
            });
        }
    } catch (java.sql.SQLException e) {
        System.out.println("Table Error: " + e.getMessage());
    }
}
   
   public class StatusCellRenderer extends javax.swing.table.DefaultTableCellRenderer {
    @Override
    public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        javax.swing.JLabel label = (javax.swing.JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        label.setHorizontalAlignment(javax.swing.JLabel.CENTER);
        
        // Colors for different statuses
        if ("Approved".equals(value)) {
            label.setForeground(new java.awt.Color(46, 204, 113)); // Green text
            label.setText("● Approved");
        } else if ("Pending".equals(value)) {
            label.setForeground(new java.awt.Color(241, 196, 15)); // Yellow text
            label.setText("● Pending");
        }
        
        return label;
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
        providers = new javax.swing.JButton();
        appointments = new javax.swing.JButton();
        reports = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        userCountLabel = new javax.swing.JPanel();
        p = new javax.swing.JLabel();
        providerCountLabel = new javax.swing.JPanel();
        jPane15 = new javax.swing.JLabel();
        appointmentCountLabel = new javax.swing.JPanel();
        jPane16 = new javax.swing.JLabel();
        notificationBadge = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        recentActivityTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel_Welcome = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(35, 66, 106));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setBackground(new java.awt.Color(35, 66, 106));
        jButton1.setFont(new java.awt.Font("Segoe UI Black", 0, 13)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Users");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 230, 151, -1));

        providers.setBackground(new java.awt.Color(35, 66, 106));
        providers.setFont(new java.awt.Font("Segoe UI Black", 0, 13)); // NOI18N
        providers.setForeground(new java.awt.Color(255, 255, 255));
        providers.setText("Providers");
        providers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                providersActionPerformed(evt);
            }
        });
        jPanel2.add(providers, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 270, 151, -1));

        appointments.setBackground(new java.awt.Color(35, 66, 106));
        appointments.setFont(new java.awt.Font("Segoe UI Black", 0, 13)); // NOI18N
        appointments.setForeground(new java.awt.Color(255, 255, 255));
        appointments.setText("Appointments");
        appointments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                appointmentsActionPerformed(evt);
            }
        });
        jPanel2.add(appointments, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 310, 150, -1));

        reports.setBackground(new java.awt.Color(35, 66, 106));
        reports.setFont(new java.awt.Font("Segoe UI Black", 0, 13)); // NOI18N
        reports.setForeground(new java.awt.Color(255, 255, 255));
        reports.setText("Reports");
        reports.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportsActionPerformed(evt);
            }
        });
        jPanel2.add(reports, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 350, 150, -1));

        jButton5.setBackground(new java.awt.Color(35, 66, 106));
        jButton5.setFont(new java.awt.Font("Segoe UI Black", 0, 13)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("My Profile");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 430, 150, -1));

        jButton6.setBackground(new java.awt.Color(102, 204, 255));
        jButton6.setFont(new java.awt.Font("Segoe UI Black", 0, 13)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Logout");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 520, 100, -1));

        jButton7.setBackground(new java.awt.Color(35, 66, 106));
        jButton7.setFont(new java.awt.Font("Segoe UI Black", 0, 13)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("Services");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 390, 150, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 0, 15)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("MENU");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 200, -1, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/Blue White Modern Minimalist Interior Designer Personal Branding Logo(1)(1).jpg"))); // NOI18N
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, -1, 80));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 260, 560));

        userCountLabel.setBackground(new java.awt.Color(255, 111, 94));
        userCountLabel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        p.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/user (4).png"))); // NOI18N
        userCountLabel.add(p, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, -1, 80));

        jPanel1.add(userCountLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 140, 150, 140));

        providerCountLabel.setBackground(new java.awt.Color(78, 205, 196));
        providerCountLabel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPane15.setBackground(new java.awt.Color(242, 133, 0));
        jPane15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/services (1).png"))); // NOI18N
        providerCountLabel.add(jPane15, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, 80, 80));

        jPanel1.add(providerCountLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 140, 160, 140));

        appointmentCountLabel.setBackground(new java.awt.Color(255, 201, 113));
        appointmentCountLabel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPane16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/schedule.png"))); // NOI18N
        appointmentCountLabel.add(jPane16, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, 70, 60));

        jPanel1.add(appointmentCountLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 140, 160, 140));

        notificationBadge.setBackground(new java.awt.Color(255, 0, 0));
        notificationBadge.setForeground(new java.awt.Color(255, 255, 255));
        notificationBadge.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/notification-bell.png"))); // NOI18N
        jPanel1.add(notificationBadge, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 40, 30, -1));
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 40, 30, -1));

        recentActivityTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "User Name", "Role", "Status"
            }
        ));
        jScrollPane1.setViewportView(recentActivityTable);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 310, 520, 260));

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(35, 36, 106));
        jLabel1.setText("ADMIN DASHBOARD");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 50, -1, -1));

        jLabel_Welcome.setFont(new java.awt.Font("Segoe Script", 0, 18)); // NOI18N
        jLabel_Welcome.setForeground(new java.awt.Color(35, 66, 106));
        jLabel_Welcome.setText("Welcome, ");
        jPanel1.add(jLabel_Welcome, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 20, 370, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 890, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        ServiceManagement sm = new ServiceManagement();
        sm.setVisible(true);
        sm.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        int confirm = javax.swing.JOptionPane.showConfirmDialog(null,
            "Are you sure you want to logout?", "Logout",
            javax.swing.JOptionPane.YES_NO_OPTION);
        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
            new MAIN.login().setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        new adminprofile().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void reportsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reportsActionPerformed
        try {
            reports rpt = new reports();
            rpt.setVisible(true);
            this.dispose();
        } catch (Exception e) {
            // This will tell you EXACTLY why it's glitching in the output console
            javax.swing.JOptionPane.showMessageDialog(null, "Error opening Reports: " + e.getMessage());
        }
    }//GEN-LAST:event_reportsActionPerformed

    private void appointmentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_appointmentsActionPerformed
        Appointments appt = new Appointments();

        // Make it show up on screen
        appt.setVisible(true);

        // Center it so it doesn't appear in the corner
        appt.setLocationRelativeTo(null);

        // Close the current dashboard
        this.dispose();
    }//GEN-LAST:event_appointmentsActionPerformed

    private void providersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_providersActionPerformed
        Providers prov = new Providers();
        prov.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_providersActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        usermanagement um = new usermanagement();
        um.setVisible(true);
        um.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

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
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(admindashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            if (CONFIG.Session.getUserId() == 0) { // use getter
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
                new admindashboard().setVisible(true); // show dashboard
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel appointmentCountLabel;
    private javax.swing.JButton appointments;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel_Welcome;
    private javax.swing.JLabel jPane15;
    private javax.swing.JLabel jPane16;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel notificationBadge;
    private javax.swing.JLabel p;
    private javax.swing.JPanel providerCountLabel;
    private javax.swing.JButton providers;
    private javax.swing.JTable recentActivityTable;
    private javax.swing.JButton reports;
    private javax.swing.JPanel userCountLabel;
    // End of variables declaration//GEN-END:variables
}
