/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ADMIN;

import MAIN.landingpage;
import MAIN.login;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import CONFIG.config;
import static CONFIG.config.connectDB;

/**
 *
 * @author Jingkie
 */
public class admindashboard extends javax.swing.JFrame {

   config cfg = new config();
    
public admindashboard() {
    initComponents();
    updateDashboardRevenue();
    
    // 1. Setup the Cards with Titles & Hover Effects
        // Top Row: Stats
    // 1. USERS PANEL -> moabli sa usermanagement.java
    setupCard(userCountLabel, p, () -> {
        new usermanagement().setVisible(true);
        this.dispose(); // I-close ang dashboard kung gusto nimo
    });
    // 2. PROVIDERS PANEL -> (ibutang ang iyang java file diri)
    setupCard(providerCountLabel, jPane15, () -> {
        new Providers () .setVisible(true);// pananglitan: new providermanagement().setVisible(true);
       this.dispose();
    });

    // 3. BOOKINGS PANEL -> (ibutang ang iyang java file diri)
    setupCard(appointmentCountLabel, jPane16, () -> {
        new Appointments () .setVisible(true);
        this.dispose();
    });

// Middle Row: Navigation
    setupCard(jPanel4, jLabel4, () -> {
        // I-abli ang imong profile frame
        new adminprofile().setVisible(true); 
        this.dispose();
        System.out.println("Profile clicked!");
    });
    
    // LOGOUT PANEL (jPanel5)
    setupCard(jPanel5, jLabel6, () -> {
    // 1. Maghimo og Yes/No pop-up window
    int confirm = javax.swing.JOptionPane.showConfirmDialog(
        this, 
        "Are you sure you want to logout?", 
        "Logout Confirmation", 
        javax.swing.JOptionPane.YES_NO_OPTION,
        javax.swing.JOptionPane.QUESTION_MESSAGE
    );

    // 2. Kung 'Yes' ang gi-click (value is 0)
    if (confirm == javax.swing.JOptionPane.YES_OPTION) {
        // I-close ang dashboard ug balik sa login page
        new login().setVisible(true); 
        this.dispose(); 
    }
    // Kung 'No' ang gi-click, wala'y mahitabo, pabilin ra sa dashboard
});
    setupCard(revenuePanel, rev_label, () -> {
        new reports().setVisible(true); 
    
    // 2. I-close ang current dashboard
    this.dispose(); 
    
    System.out.println("Redirecting to Reports...");
    });

        // 2. Load Real-Time Data from SQLite
        refreshStats();
     
        
        // 3. User & Session Initialization
        jLabel_Welcome.setText("Welcome, Admin: " + CONFIG.Session.getName());
        new CONFIG.config().sessionGuard(this);
        
        // 4. Sidebar Button Styling
        Color navy = new Color(35, 66, 106);
        Color logoutBrown = new Color(106, 75, 35);

    applyAdminButtonStyle(jButton1, navy);    // Users
    applyAdminButtonStyle(providers, navy);  // Providers
    applyAdminButtonStyle(appointments, navy); 
    applyAdminButtonStyle(reports, navy);
    applyAdminButtonStyle(jButton7, navy);    // Services
    applyAdminButtonStyle(jButton5, navy);    // Profile
    applyAdminButtonStyle(jButton6, logoutBrown); // Logout

    // 5. MODERN TABLE STYLING (The "Un-plain" fix)
    
    
    

    // Tawgon ang method para ma-load ang picture sa imong label
    displayProfilePicture(profile_pic);
    profile_pic.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    
    profile_pic.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        // 1. Tawgon ang imong My Profile frame
        // Siguroha nga 'adminprofile' ang saktong ngalan sa imong class
        adminprofile profileFrame = new adminprofile(); 
        profileFrame.setVisible(true);

        // 2. I-close o i-hide ang current dashboard (optional)
        // dispose(); 
    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent evt) {
        // Optional: Pwede nimo butangan og border para mo-highlight inig itunong ang mouse
        profile_pic.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.WHITE, 2));
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent evt) {
        // Tangtangon ang border inig layo sa mouse
        profile_pic.setBorder(null);
    }
});
    
    // I-set ang Welcome message gamit ang imong Session name
    // Welcome.setText("Welcome, Admin: " + CONFIG.Session.getName());


    
    // Style the ScrollPane to remove the old-school border
    

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
    
   private void setupCard(javax.swing.JPanel card, javax.swing.JLabel label, Runnable action) {
    java.awt.Color originalColor = card.getBackground();
    
    // Mouse Events para sa hover ug click
    card.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            card.setBackground(originalColor.brighter());
            card.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent evt) {
            card.setBackground(originalColor);
        }

        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            // Mao ni ang modagan inig click sa panel
            if (action != null) {
                action.run();
            }
        }
    });
}
   
   private void refreshStats() {
    String url = "jdbc:sqlite:hservice.db"; 
    java.awt.Font numFont = new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 22);
    
    try (java.sql.Connection conn = java.sql.DriverManager.getConnection(url);
         java.sql.Statement st = conn.createStatement()) {

        // 1. TOTAL USERS (Label: p)
        java.sql.ResultSet rs1 = st.executeQuery("SELECT COUNT(*) FROM tbl_users");
        if (rs1.next()) {
            p.setFont(numFont);
            p.setText(String.valueOf(rs1.getInt(1)));
        }

        // 2. PROVIDERS (Label: jPane15)
        java.sql.ResultSet rs2 = st.executeQuery("SELECT COUNT(*) FROM tbl_users WHERE user_role = 'Provider'");
        if (rs2.next()) {
            jPane15.setFont(numFont);
            jPane15.setText(String.valueOf(rs2.getInt(1)));
        }

        // 3. BOOKINGS (Label: jPane16)
        java.sql.ResultSet rs3 = st.executeQuery("SELECT COUNT(*) FROM tbl_bookings");
        if (rs3.next()) {
            jPane16.setFont(numFont);
            jPane16.setText(String.valueOf(rs3.getInt(1)));
        }

        // 4. REVENUE TODAY (Bag-ong Panel: lbl_revenue)
        // Gigamit ang b_total ug b_date base sa imong DB schema
        String revQuery = "SELECT SUM(b_total) FROM tbl_bookings"; // Tangtangon ang WHERE clause
        java.sql.ResultSet rsRev = st.executeQuery(revQuery);
            if (rsRev.next()) {
            double total = rsRev.getDouble(1);
            rev_label.setFont(numFont);
    // Siguroha nga husto ang formatting
            rev_label.setText(String.format("₱%,.2f", total));
}

    } catch (java.sql.SQLException e) {
        System.out.println("Database Error: " + e.getMessage());
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
   
   private void displayProfilePicture(JLabel label) {
    // 1. Usba ang path base sa kung asa ang imong .db file
    String url = "jdbc:sqlite:hservice.db"; 
    
    // 2. Gi-update ang column names: user_image ug user_id
    String sql = "SELECT user_image FROM tbl_users WHERE user_id = ?"; 

    try (java.sql.Connection conn = java.sql.DriverManager.getConnection(url);
         java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setInt(1, CONFIG.Session.getUserId()); 
        java.sql.ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            String imagePath = rs.getString("user_image");

            if (imagePath != null && !imagePath.isEmpty()) {
                java.io.File imgFile = new java.io.File(imagePath);
                
                if (imgFile.exists()) {
                    java.awt.image.BufferedImage master = javax.imageio.ImageIO.read(imgFile);
                    
                    int diameter = Math.min(master.getWidth(), master.getHeight());
                    java.awt.image.BufferedImage masked = new java.awt.image.BufferedImage(diameter, diameter, java.awt.image.BufferedImage.TYPE_INT_ARGB);
                    
                    java.awt.Graphics2D g2 = masked.createGraphics();
                    g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                    
                    g2.fillOval(0, 0, diameter, diameter);
                    g2.setComposite(java.awt.AlphaComposite.SrcIn);
                    g2.drawImage(master, 0, 0, null);
                    g2.dispose();

                    label.setIcon(new javax.swing.ImageIcon(masked.getScaledInstance(label.getWidth(), label.getHeight(), java.awt.Image.SCALE_SMOOTH)));
                    label.setText(""); 
                    return;
                }
            }
        }
        
        // Default icon kung blangko ang database
        label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/user-profile.png")));
        
    } catch (Exception e) {
        // I-print ang error para makita nato sa console kung naay problema sa connection
        System.out.println("Profile Pic Error: " + e.getMessage());
    }
}
   
   public void updateDashboardRevenue() {
    // I-filter nato para APPROVED ug COMPLETED ra ang i-add
    // Kani nga SQL ang gamita
String sql = "SELECT SUM(b_total) AS total FROM tbl_bookings WHERE b_status = 'COMPLETED'";
    
    try (java.sql.Connection conn = connectDB(); 
         java.sql.PreparedStatement pst = conn.prepareStatement(sql);
         java.sql.ResultSet rs = pst.executeQuery()) {

        if (rs.next()) {
            double total = rs.getDouble("total");
            
            // I-set ang text sa imong label
            this.rev_label.setText("₱" + String.format("%,.2f", total));
this.rev_label.revalidate(); // I-refresh ang layout
this.rev_label.repaint();    // I-redraw ang label sa screen
            
            System.out.println("Revenue Updated (Filtered): ₱" + total);
        }

    } catch (java.sql.SQLException e) {
        System.out.println("Revenue Update Error: " + e.getMessage());
        this.rev_label.setText("₱0.00");
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
        jLabel7 = new javax.swing.JLabel();
        providerCountLabel = new javax.swing.JPanel();
        jPane15 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        appointmentCountLabel = new javax.swing.JPanel();
        jPane16 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel_Welcome = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        revenuePanel = new javax.swing.JPanel();
        rev_label = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        profile_pic = new javax.swing.JLabel();

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
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(31, 180, 210, -1));

        providers.setBackground(new java.awt.Color(35, 66, 106));
        providers.setFont(new java.awt.Font("Segoe UI Black", 0, 13)); // NOI18N
        providers.setForeground(new java.awt.Color(255, 255, 255));
        providers.setText("Providers");
        providers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                providersActionPerformed(evt);
            }
        });
        jPanel2.add(providers, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 210, -1));

        appointments.setBackground(new java.awt.Color(35, 66, 106));
        appointments.setFont(new java.awt.Font("Segoe UI Black", 0, 13)); // NOI18N
        appointments.setForeground(new java.awt.Color(255, 255, 255));
        appointments.setText("Appointments");
        appointments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                appointmentsActionPerformed(evt);
            }
        });
        jPanel2.add(appointments, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, 210, -1));

        reports.setBackground(new java.awt.Color(35, 66, 106));
        reports.setFont(new java.awt.Font("Segoe UI Black", 0, 13)); // NOI18N
        reports.setForeground(new java.awt.Color(255, 255, 255));
        reports.setText("Reports");
        reports.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportsActionPerformed(evt);
            }
        });
        jPanel2.add(reports, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 360, 210, -1));

        jButton5.setBackground(new java.awt.Color(35, 66, 106));
        jButton5.setFont(new java.awt.Font("Segoe UI Black", 0, 13)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("My Profile");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 480, 210, -1));

        jButton6.setBackground(new java.awt.Color(102, 204, 255));
        jButton6.setFont(new java.awt.Font("Segoe UI Black", 0, 13)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Logout");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 560, 100, -1));

        jButton7.setBackground(new java.awt.Color(35, 66, 106));
        jButton7.setFont(new java.awt.Font("Segoe UI Black", 0, 13)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("Services");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 420, 210, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 0, 15)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("MENU");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 140, -1, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/Blue White Modern Minimalist Interior Designer Personal Branding Logo(1)(1).jpg"))); // NOI18N
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, -1, 80));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 260, 610));

        userCountLabel.setBackground(new java.awt.Color(255, 111, 94));
        userCountLabel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        p.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/user (4).png"))); // NOI18N
        userCountLabel.add(p, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, -1, 80));

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("USERS");
        userCountLabel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 50, -1));

        jPanel1.add(userCountLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 140, 150, 140));

        providerCountLabel.setBackground(new java.awt.Color(78, 205, 196));
        providerCountLabel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPane15.setBackground(new java.awt.Color(242, 133, 0));
        jPane15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/services (1).png"))); // NOI18N
        providerCountLabel.add(jPane15, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 140, 80));

        jLabel8.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("PROVIDERS");
        providerCountLabel.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, -1, -1));

        jPanel1.add(providerCountLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 140, 160, 140));

        appointmentCountLabel.setBackground(new java.awt.Color(255, 201, 113));
        appointmentCountLabel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPane16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/schedule.png"))); // NOI18N
        appointmentCountLabel.add(jPane16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 140, 60));

        jLabel9.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("BOOKINGS");
        appointmentCountLabel.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, -1, -1));

        jPanel1.add(appointmentCountLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 140, -1, 140));
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 40, 30, -1));

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(35, 36, 106));
        jLabel1.setText("ADMIN DASHBOARD");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 50, -1, -1));

        jLabel_Welcome.setFont(new java.awt.Font("Segoe Script", 0, 18)); // NOI18N
        jLabel_Welcome.setForeground(new java.awt.Color(35, 66, 106));
        jLabel_Welcome.setText("Welcome, ");
        jPanel1.add(jLabel_Welcome, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 20, 370, -1));

        jPanel4.setBackground(new java.awt.Color(109, 89, 122));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/user-profile.png"))); // NOI18N

        jLabel10.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("MY PROFILE");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel4)))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addGap(36, 36, 36))
        );

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 330, 150, 140));

        jPanel5.setBackground(new java.awt.Color(230, 57, 70));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/logout (3).png"))); // NOI18N

        jLabel11.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("LOGOUT");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 330, 160, 140));

        revenuePanel.setBackground(new java.awt.Color(69, 123, 157));

        rev_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/wallet.png"))); // NOI18N

        jLabel13.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("REVENUE");

        javax.swing.GroupLayout revenuePanelLayout = new javax.swing.GroupLayout(revenuePanel);
        revenuePanel.setLayout(revenuePanelLayout);
        revenuePanelLayout.setHorizontalGroup(
            revenuePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(revenuePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rev_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, revenuePanelLayout.createSequentialGroup()
                .addContainerGap(54, Short.MAX_VALUE)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );
        revenuePanelLayout.setVerticalGroup(
            revenuePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, revenuePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(rev_label, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        jPanel1.add(revenuePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 330, 170, 140));

        profile_pic.setMaximumSize(new java.awt.Dimension(50, 50));
        profile_pic.setPreferredSize(new java.awt.Dimension(65, 65));
        jPanel1.add(profile_pic, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 30, 80, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 890, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 646, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel_Welcome;
    private javax.swing.JLabel jPane15;
    private javax.swing.JLabel jPane16;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel p;
    private javax.swing.JLabel profile_pic;
    private javax.swing.JPanel providerCountLabel;
    private javax.swing.JButton providers;
    private javax.swing.JButton reports;
    private javax.swing.JLabel rev_label;
    private javax.swing.JPanel revenuePanel;
    private javax.swing.JPanel userCountLabel;
    // End of variables declaration//GEN-END:variables
}
