/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package USER;

import ADMIN.usermanagement;
import MAIN.landingpage;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import CONFIG.config;
import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;

public class udashboard extends javax.swing.JFrame {

   public udashboard() {
    initComponents();
    loadUserProfile();
    
     Color navy = new Color(35, 66, 106);
     Color logoutBrown = new Color(106, 75, 35);
    
    applyAdminButtonStyle(jButton1, navy);    // Users
    applyAdminButtonStyle(BookAService, navy);  // Providers
    applyAdminButtonStyle(jButton3, navy); 
    applyAdminButtonStyle(jButton5, navy);   
    applyAdminButtonStyle(jButton6, logoutBrown);
    
    
    // My Bookings (Orange/Coral)
    stylePanelCard(jPanel4, new java.awt.Color(255, 111, 94));
    
    // Total Spent (Teal/Green)
    stylePanelCard(jPanel5, new java.awt.Color(78, 205, 196));
    
    // Account Status (Yellow/Gold)
    stylePanelCard(jPanel6, new java.awt.Color(255, 201, 113));
    
    // My Profile (Purple)
    stylePanelCard(jPanel7, new java.awt.Color(109, 89, 122));
    
    // Logout (Red)
    stylePanelCard(jPanel3, new java.awt.Color(230, 57, 70));
    
    styleAdminButtons(searchText, new java.awt.Color(60, 120, 60));// Logout
    
    // 1. Core Functions
    new CONFIG.config().sessionGuard(this); 
    jLabel_Welcome.setText("Welcome, Customer: " + CONFIG.Session.getName());

    // 2. Table Header Styling (Navy Blue & White Font)
    tbl_available_services.getTableHeader().setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer() {
        @Override
        public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            javax.swing.JLabel label = (javax.swing.JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            label.setBackground(new java.awt.Color(35, 66, 106)); // Navy Blue
            label.setForeground(java.awt.Color.WHITE); // White Text
            label.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
            label.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            label.setOpaque(true);
            label.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, java.awt.Color.WHITE));
            return label;
        }
    });

    // 3. Table Body Styling
    tbl_available_services.setRowHeight(35);
    tbl_available_services.setFont(new java.awt.Font("Segoe UI", 0, 14));
    tbl_available_services.setShowGrid(false);
    tbl_available_services.setIntercellSpacing(new java.awt.Dimension(0, 0));
    tbl_available_services.setDefaultEditor(Object.class, null); // Disable editing
    tbl_available_services.setSelectionBackground(new java.awt.Color(106, 75, 35)); // Brown Selection
    tbl_available_services.setSelectionForeground(java.awt.Color.WHITE);

    // 4. JComboBox Styling (Modern Look)
    jComboBox1.setBackground(java.awt.Color.WHITE);
    jComboBox1.setForeground(new java.awt.Color(35, 66, 106));
    jComboBox1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(35, 66, 106), 1));
    ((javax.swing.JLabel)jComboBox1.getRenderer()).setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    
    
    try {
        int userId = CONFIG.Session.getUserId();

        String sql = "SELECT user_image FROM tbl_users WHERE user_id = ?";

        try (java.sql.Connection conn = CONFIG.config.connectDB();
             java.sql.PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, userId);

            try (java.sql.ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    String imagePath = rs.getString("user_image");

                    if (imagePath != null && !imagePath.isEmpty()) {
                        c_navbar_pic.setVisible(true);
                        setCircularImage(c_navbar_pic, imagePath);
                    } else {
                        c_navbar_pic.setVisible(false);
                    }
                }
            }
        }
        
        

    } catch (Exception e) {
        c_navbar_pic.setVisible(false);
        System.out.println("Navbar Image Error: " + e.getMessage());
    }

    // 5. Load Data
    loadAvailableServices();
    displayCustomerStats();
    centerTableText(); 
}
   
   private void centerTableText() {
    javax.swing.table.DefaultTableCellRenderer centerRenderer = new javax.swing.table.DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(javax.swing.JLabel.CENTER);
    
    // I-apply sa tanan columns (usba ang numero base sa kapila ka columns imong table)
    for(int i = 0; i < tbl_available_services.getColumnCount(); i++){
        tbl_available_services.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }
    
    // 6. Data Loading
    
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
   
  public void loadAvailableServices() {
    try {
        String sql = "SELECT s.*, u.user_name " +
                     "FROM tbl_services s " +
                     "JOIN tbl_users u ON s.provider_id = u.user_id";

        try (java.sql.Connection conn = CONFIG.config.connectDB();
             java.sql.PreparedStatement pst = conn.prepareStatement(sql);
             java.sql.ResultSet rs = pst.executeQuery()) {

            // clear table first
            javax.swing.table.DefaultTableModel model =
                (javax.swing.table.DefaultTableModel) tbl_available_services.getModel();
            model.setRowCount(0);

            // display
            CONFIG.config conf = new CONFIG.config();
            conf.displayResultSet(rs, tbl_available_services);
        }

    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(null, "Display Error: " + e.getMessage());
    }
}

  
    
    
    public void displayCustomerStats() {

    int loggedInUser = CONFIG.Session.getUserId();

    // 🔹 1. Booking Count
    try {
        String sql = "SELECT COUNT(*) FROM tbl_bookings WHERE u_id = ?";

        try (java.sql.Connection conn = CONFIG.config.connectDB();
             java.sql.PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, loggedInUser);

            try (java.sql.ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    lbl_booking_count.setText(String.valueOf(rs.getInt(1)));
                }
            }
        }

    } catch (Exception e) {
        System.out.println("Stats Error: " + e.getMessage());
    }

    // 🔹 2. Total Spent
    try {
        String sql = "SELECT SUM(b_total) FROM tbl_bookings WHERE u_id = ?";

        try (java.sql.Connection conn = CONFIG.config.connectDB();
             java.sql.PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, loggedInUser);

            try (java.sql.ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    double total = rs.getDouble(1);
                    lbl_total_spent.setText("₱" + String.format("%.2f", total));
                }
            }
        }

    } catch (Exception e) {
        System.out.println("Spent Stats Error: " + e.getMessage());
    }

    // 🔹 3. User Status
    try {
        String sql = "SELECT u_status FROM tbl_users WHERE user_id = ?";

        try (java.sql.Connection conn = CONFIG.config.connectDB();
             java.sql.PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, loggedInUser);

            try (java.sql.ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    String status = rs.getString("u_status");
                    lbl_status.setText(status);

                    if (status.equalsIgnoreCase("Active") || status.equalsIgnoreCase("Approved")) {
                        lbl_status.setForeground(new java.awt.Color(0, 153, 0));
                    } else {
                        lbl_status.setForeground(java.awt.Color.RED);
                    }
                }
            }
        }

    } catch (Exception e) {
        System.out.println("Status Error: " + e.getMessage());
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
    
    
    
    
    
    
    
     private void goHome() {
        // Placeholder: redirect to home page
        JOptionPane.showMessageDialog(this, "Go Home clicked");
    }

       private void bookService() {
        // Placeholder: redirect to booking page
        JOptionPane.showMessageDialog(this, "Book Service clicked");
    }

       private void openProfile() {
        new cusprofile().setVisible(true);
        this.dispose();
    }
       private void logout() {
        landingpage lp = new landingpage();
        lp.setVisible(true);
        lp.setLocationRelativeTo(null);
        this.dispose();
    }
       private void MyAppt() {
        new cusprofile().setVisible(true);
        this.dispose();
    }
       
       private void stylePanelCard(javax.swing.JPanel panel, java.awt.Color originalColor) {
    panel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    
    panel.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            panel.setBackground(originalColor.brighter()); // Mo-light ang color
        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent evt) {
            panel.setBackground(originalColor); // Balik sa original
        }
    });
}
       
       private void displayImage(String path, javax.swing.JLabel label) {
    try {
        // 1. Load the image from the path
        ImageIcon ii = new ImageIcon(path);
        Image img = ii.getImage().getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
        
        // 2. Create a blank canvas (BufferedImage)
        java.awt.image.BufferedImage bufferedImage = new java.awt.image.BufferedImage(
            label.getWidth(), label.getHeight(), java.awt.image.BufferedImage.TYPE_INT_ARGB);
        
        // 3. Draw the circle
        java.awt.Graphics2D g2 = bufferedImage.createGraphics();
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setClip(new java.awt.geom.Ellipse2D.Float(0, 0, label.getWidth(), label.getHeight()));
        g2.drawImage(img, 0, 0, null);
        g2.dispose();
        
        // 4. Set the circle image to your Label
        label.setIcon(new ImageIcon(bufferedImage));
    } catch (Exception e) {
        System.out.println("Display Error: " + e.getMessage());
    }
}
       
     private void loadUserProfile() {
    int userId = CONFIG.Session.getUserId(); // Mukuha sa ID sa ga-login
    String sql = "SELECT user_image FROM tbl_users WHERE user_id = ?";

    try (java.sql.Connection conn = CONFIG.config.connectDB();
         java.sql.PreparedStatement pst = conn.prepareStatement(sql)) {

        pst.setInt(1, userId);
        java.sql.ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            String path = rs.getString("user_image");
            if (path != null && !path.isEmpty()) {
                // Tawgon ang displayImage method para sa jLabel2
                displayImage(path, jLabel2); 
            }
        }
    } catch (Exception e) {
        System.out.println("Database Error: " + e.getMessage());
    }
}
     
      public void setCircularImage(javax.swing.JLabel label, String path) {
    try {
        javax.swing.ImageIcon icon = new javax.swing.ImageIcon(path);
        java.awt.Image img = icon.getImage();
        
        // Paghimo og Buffered Image para sa rounding effect
        int width = label.getWidth();
        int height = label.getHeight();
        java.awt.image.BufferedImage bi = new java.awt.image.BufferedImage(width, height, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g2 = bi.createGraphics();
        
        // Smoothing / Antialiasing
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setClip(new java.awt.geom.Ellipse2D.Float(0, 0, width, height));
        g2.drawImage(img, 0, 0, width, height, null);
        g2.dispose();
        
        label.setIcon(new javax.swing.ImageIcon(bi));
    } catch (Exception e) {
        System.out.println("Error setting circular image: " + e.getMessage());
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
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_available_services = new javax.swing.JTable();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        lbl_booking_count = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        lbl_total_spent = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        lbl_status = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel_Welcome = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        searchText = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        c_navbar_pic = new javax.swing.JLabel();

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
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 260, -1));

        BookAService.setBackground(new java.awt.Color(35, 66, 106));
        BookAService.setFont(new java.awt.Font("Segoe UI Black", 0, 13)); // NOI18N
        BookAService.setForeground(new java.awt.Color(255, 255, 255));
        BookAService.setText("Book a Service");
        BookAService.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BookAServiceActionPerformed(evt);
            }
        });
        jPanel2.add(BookAService, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 260, -1));

        jButton3.setBackground(new java.awt.Color(35, 66, 106));
        jButton3.setFont(new java.awt.Font("Segoe UI Black", 0, 13)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("My Appointments");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 260, -1));

        jButton5.setBackground(new java.awt.Color(35, 66, 106));
        jButton5.setFont(new java.awt.Font("Segoe UI Black", 0, 13)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("My Profile");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 260, -1));

        jButton6.setBackground(new java.awt.Color(35, 66, 106));
        jButton6.setFont(new java.awt.Font("Segoe UI Black", 0, 13)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Logout");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 640, 160, 30));
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(174, 133, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/Blue White Modern Minimalist Interior Designer Personal Branding Logo(1)(1).jpg"))); // NOI18N
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 0, 15)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("MENU");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 200, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 310, 690));

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

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 510, 550, 230));

        jComboBox1.setBackground(new java.awt.Color(35, 66, 106));
        jComboBox1.setFont(new java.awt.Font("Segoe UI Black", 0, 13)); // NOI18N
        jComboBox1.setForeground(new java.awt.Color(255, 255, 255));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cleaning & Housekeeping", "Maintenance & Repair", "Landscaping & Outdoor", "Renovation & Contracting ", "Specialized Services " }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 470, 280, 30));

        jLabel4.setFont(new java.awt.Font("Segoe UI Black", 0, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(35, 66, 106));
        jLabel4.setText("CUSTOMER DASHBOARD");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 60, 460, 60));

        jPanel4.setBackground(new java.awt.Color(255, 111, 94));
        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel4MouseClicked(evt);
            }
        });
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("MY BOOKINGS");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 13, 118, -1));

        lbl_booking_count.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lbl_booking_count.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/clipboard.png"))); // NOI18N
        jPanel4.add(lbl_booking_count, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 120, 75));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 120, 160, 120));

        jPanel5.setBackground(new java.awt.Color(78, 205, 196));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("TOTAL SPENT");
        jPanel5.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 13, 137, -1));

        lbl_total_spent.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_total_spent.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/thin.png"))); // NOI18N
        jPanel5.add(lbl_total_spent, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 150, 110));

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 120, -1, 120));

        jPanel6.setBackground(new java.awt.Color(255, 201, 113));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("ACCOUNT STATUS");
        jPanel6.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 13, -1, -1));

        lbl_status.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_status.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/user (5).png"))); // NOI18N
        jPanel6.add(lbl_status, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 170, 86));

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 120, 170, 120));

        jPanel3.setBackground(new java.awt.Color(230, 57, 70));
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel3MouseClicked(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("LOGOUT");

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/logout (3).png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 250, 160, 120));

        jLabel_Welcome.setFont(new java.awt.Font("Segoe Script", 0, 18)); // NOI18N
        jLabel_Welcome.setForeground(new java.awt.Color(35, 66, 106));
        jLabel_Welcome.setText("Welcome,");
        jPanel1.add(jLabel_Welcome, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 40, 350, 40));

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });
        jPanel1.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 470, 120, 30));

        searchText.setText("Search");
        searchText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchTextActionPerformed(evt);
            }
        });
        jPanel1.add(searchText, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 470, 110, 30));

        jPanel7.setBackground(new java.awt.Color(109, 89, 122));
        jPanel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel7MouseClicked(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("MY PROFILE");

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/user-profile.png"))); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(31, 31, 31))
        );

        jPanel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 250, 160, 120));

        jLabel11.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(35, 66, 106));
        jLabel11.setText("To book an appointment, please select in the table");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 410, 510, 50));

        c_navbar_pic.setPreferredSize(new java.awt.Dimension(65, 65));
        jPanel1.add(c_navbar_pic, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 30, 80, 70));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 940, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 779, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void BookAServiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BookAServiceActionPerformed
    int row = tbl_available_services.getSelectedRow();
    if (row != -1) {
        int sid = Integer.parseInt(tbl_available_services.getValueAt(row, 0).toString());
        String sname = tbl_available_services.getValueAt(row, 1).toString();
        String price = tbl_available_services.getValueAt(row, 3).toString();
        int pid = Integer.parseInt(tbl_available_services.getValueAt(row, 5).toString()); // Provider ID

        // Fetch provider name from tbl_users (case-insensitive role)
       String pname = "Unknown Provider";
config conf = new config();
String sql = "SELECT user_name FROM tbl_users WHERE user_id = ?";
try (Connection conn = conf.connectDB();
     PreparedStatement pst = conn.prepareStatement(sql)) {
    pst.setInt(1, pid);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
        pname = rs.getString("user_name");
    }
} catch (SQLException e) {
    System.out.println("Error fetching provider: " + e.getMessage());
}
System.out.println("Provider Name: " + pname);

        // Open Book a Service form with details
        bookaservice bas = new bookaservice();
        bas.setBookingDetails(sname, pname, price, sid, pid);
        bas.setVisible(true);
        this.dispose();
    } else {
        JOptionPane.showMessageDialog(null, "Please select a service to book!");
    }
    }//GEN-LAST:event_BookAServiceActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
      cusprofile cp = new cusprofile();
    cp.setVisible(true);
    cp.setLocationRelativeTo(null); // center screen
    this.dispose(); // closes dashboard (optional)
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
       // 1. Siguraduhon nato nga dili null ang napili
Object selectedItem = jComboBox1.getSelectedItem();

if (selectedItem != null) {
    String selectedCategory = selectedItem.toString();
    
    try {
        CONFIG.config conf = new CONFIG.config();
        
        // 2. Mas maayo nga i-handle ang 'All' option kung naa man gani
        String sql;
        if (selectedCategory.equals("All")) { // Optional: kung gusto nimo naay 'Show All'
            sql = "SELECT * FROM tbl_services";
        } else {
            // Gigamit gihapon nato ang imong format, pero ge-siguro ang quotes
            sql = "SELECT * FROM tbl_services WHERE s_category = '" + selectedCategory + "'";
        }
        
        conf.displayData(sql, tbl_available_services);
        
    } catch (Exception e) {
        System.out.println("Filter Error: " + e.getMessage());
    }
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

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseClicked
      // 1. Maghimo og Confirm Dialog (Yes/No)
    int confirm = javax.swing.JOptionPane.showConfirmDialog(null, 
            "Are you sure you want to logout?", "Logout Confirmation", 
            javax.swing.JOptionPane.YES_NO_OPTION, 
            javax.swing.JOptionPane.QUESTION_MESSAGE);
    
    // 2. Kung 'Yes' ang gi-click sa user
    if (confirm == javax.swing.JOptionPane.YES_OPTION) {
        // I-clear ang session (optional pero maayo ni para safe)
        // CONFIG.Session.setUserId(0); 

        // 3. I-open ang Landing Page
        MAIN.landingpage lp = new MAIN.landingpage();
        lp.setVisible(true);
        lp.setLocationRelativeTo(null); // Center ang window
        
        // 4. I-close ang kani nga Dashboard
        this.dispose();
    }
    }//GEN-LAST:event_jPanel3MouseClicked

    private void searchTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTextActionPerformed
   try {
    CONFIG.config conf = new CONFIG.config();

    String sql = "SELECT * FROM tbl_services WHERE "
               + "(s_name LIKE ? OR s_category LIKE ?) "
               + "AND (s_status = 'Available' OR s_status = 'Active')";

    try (java.sql.Connection conn = CONFIG.config.connectDB();
         java.sql.PreparedStatement pst = conn.prepareStatement(sql)) {

        pst.setString(1, "%" + searchText + "%");
        pst.setString(2, "%" + searchText + "%");

        try (java.sql.ResultSet rs = pst.executeQuery()) {
            conf.displayResultSet(rs, tbl_available_services);
        }
    }

    centerTableText();

} catch (Exception e) {
    javax.swing.JOptionPane.showMessageDialog(null, "Search Error: " + e.getMessage());
}
    }//GEN-LAST:event_searchTextActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
                                            
   String searchText = jTextField1.getText().trim();

    try {
        CONFIG.config conf = new CONFIG.config();

        String sql = "SELECT * FROM tbl_services WHERE "
                   + "(s_name LIKE ? OR s_category LIKE ?) "
                   + "AND (s_status = 'Available' OR s_status = 'Active')";

        try (java.sql.Connection conn = CONFIG.config.connectDB();
             java.sql.PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, "%" + searchText + "%");
            pst.setString(2, "%" + searchText + "%");

            try (java.sql.ResultSet rs = pst.executeQuery()) {
                conf.displayResultSet(rs, tbl_available_services);
            }
        }

        centerTableText();

    } catch (Exception e) {
        System.out.println("Live Search Error: " + e.getMessage());
    }
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jPanel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseClicked
        openProfile();
    }//GEN-LAST:event_jPanel7MouseClicked

    private void jPanel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseClicked
        MyAppt();
    }//GEN-LAST:event_jPanel4MouseClicked
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
    private javax.swing.JLabel c_navbar_pic;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel_Welcome;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lbl_booking_count;
    private javax.swing.JLabel lbl_status;
    private javax.swing.JLabel lbl_total_spent;
    private javax.swing.JButton searchText;
    private javax.swing.JTable tbl_available_services;
    // End of variables declaration//GEN-END:variables
}
