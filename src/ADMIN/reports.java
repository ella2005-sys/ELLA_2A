
package ADMIN;

import javax.swing.table.DefaultTableModel;
import CONFIG.config;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.MessageFormat;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.SwingConstants;



public class reports extends javax.swing.JFrame {

     config cfg = new config();
    public reports() {
    new CONFIG.config().sessionGuard(this);
    initComponents();
    setLocationRelativeTo(null);
    setupTable();
    
    // Custom Colors
    java.awt.Color navy = new java.awt.Color(35, 66, 106);
    java.awt.Color successGreen = new java.awt.Color(40, 167, 69);
    java.awt.Color accentOrange = new java.awt.Color(255, 152, 0);

    applyAdminButtonStyle(jButton1, navy); 
    applyAdminButtonStyle(print, navy); 

    // --- STYLE PARA SA TOTAL REVENUE CARD ---
    jLabel_TotalRevenue.setOpaque(true);
    jLabel_TotalRevenue.setBackground(Color.WHITE);
    jLabel_TotalRevenue.setForeground(successGreen);
    jLabel_TotalRevenue.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel_TotalRevenue.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
        BorderFactory.createEmptyBorder(5, 15, 5, 15)
    ));

    // --- STYLE PARA SA TOP SERVICE CARD ---
    jLabel_MostBooked.setOpaque(true);
    jLabel_MostBooked.setBackground(Color.WHITE);
    jLabel_MostBooked.setForeground(navy);
    jLabel_MostBooked.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel_MostBooked.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
        BorderFactory.createEmptyBorder(5, 15, 5, 15)
    ));

    // --- MODERN TABLE RENDERER (Alternating Colors) ---
    jTable1.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
        @Override
        public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            // Alternating Row Colors
            if (!isSelected) {
                c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 248, 250));
            }
            
            // Specific Style for Revenue Column
            if(column == 3){
                c.setForeground(new Color(0, 102, 51));
                setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
            } else {
                c.setForeground(Color.BLACK);
            }
            
            setBorder(noFocusBorder);
            setHorizontalAlignment(column == 1 ? SwingConstants.LEFT : SwingConstants.CENTER);
            return c;
        }
    });

    displayReports();
}
  
   public void displayReports() {
    double grandTotal = 0;
    int maxBooked = -1;
    String topService = "None";

    try (java.sql.Connection conn = cfg.connectDB()) {
        // Gikuha na ang brackets ug spaces sa s_id
        // Gi-join nato ang tbl_services ug tbl_bookings para makuha ang presyo ug status
String sql = "SELECT ts.s_id, ts.s_name, " +
             "COUNT(CASE WHEN tb.b_status IN ('COMPLETED','Completed' ,'Approved', 'APPROVED') THEN tb.b_id END) AS total_booked, " +
             "SUM(CASE WHEN tb.b_status IN ('COMPLETED','Completed' ,'Approved', 'APPROVED') THEN tb.b_total ELSE 0 END) AS total_revenue " +
             "FROM tbl_services ts " +
             "LEFT JOIN tbl_bookings tb ON ts.s_id = tb.s_id " +
             "GROUP BY ts.s_id, ts.s_name";

        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
        java.sql.ResultSet rs = pst.executeQuery();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); 

        while (rs.next()) {
    int bookedCount = rs.getInt("total_booked");
    double revenue = rs.getDouble("total_revenue"); // Mao ni ang (Price x Completed)
    String sName = rs.getString("s_name");

    model.addRow(new Object[]{
        rs.getInt("s_id"),
        sName,
        bookedCount,
        "₱" + String.format("%,.2f", revenue) // Mugawas ang ₱1,600.00
    });

    grandTotal += revenue;
            
            // Logic para sa Top Performing Service
            if (bookedCount > maxBooked && bookedCount > 0) {
                maxBooked = bookedCount;
                topService = sName;
            }
        }
        
        // Update ang mga Labels (Grand Total ug Top Service)
        jLabel_TotalRevenue.setText("GRAND TOTAL: ₱" + String.format("%,.2f", grandTotal));
        
        if (maxBooked > 0) {
            jLabel_MostBooked.setText("★ TOP SERVICE: " + topService.toUpperCase() + " (" + maxBooked + " bookings)");
        } else {
            jLabel_MostBooked.setText("★ TOP SERVICE: NO DATA");
        }

        rs.close(); 
        pst.close();
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Report Error: " + e.getMessage());
    }
}
 
    
    
private void setupTable() {
    String[] headers = {"Service ID", "Service Name", "Times Booked", "Total Revenue"};
    jTable1.setModel(new javax.swing.table.DefaultTableModel(null, headers) {
        @Override
        public boolean isCellEditable(int row, int column) { return false; }
    });
    
    // Table Properties
    jTable1.setShowGrid(false);
    jTable1.setIntercellSpacing(new java.awt.Dimension(0, 0));
    jTable1.setSelectionBackground(new Color(232, 242, 252));
    jTable1.setSelectionForeground(Color.BLACK);
    
    // Modern Header
    jTable1.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
    jTable1.getTableHeader().setBackground(new java.awt.Color(35, 66, 106));
    jTable1.getTableHeader().setForeground(java.awt.Color.WHITE);
    jTable1.getTableHeader().setPreferredSize(new java.awt.Dimension(100, 40));
    jTable1.getTableHeader().setBorder(null);
    
    jTable1.setRowHeight(40);
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
 
 private void printBtnActionPerformed(java.awt.event.ActionEvent evt) {                                         
    // This creates the professional header and footer for your printed page
    MessageFormat header = new MessageFormat("LocalHelper - Service Revenue Report");
    MessageFormat footer = new MessageFormat("Page {0,number,integer}");
    
    try {
        // This command opens the system print dialog and formats your table
        boolean complete = jTable1.print(JTable.PrintMode.FIT_WIDTH, header, footer);
        
        if (complete) {
            System.out.println("Printing Complete");
        } else {
            System.out.println("Printing Cancelled");
        }
    } catch (java.awt.print.PrinterException e) {
        System.err.format("Cannot print %s%n", e.getMessage());
    }
}
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        print = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel_TotalRevenue = new javax.swing.JLabel();
        jLabel_MostBooked = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(35, 66, 106));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/Blue White Modern Minimalist Interior Designer Personal Branding Logo(1)(1).jpg"))); // NOI18N
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, -1, 91));

        jButton1.setBackground(new java.awt.Color(35, 66, 106));
        jButton1.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Back to Home");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, 260, 35));

        print.setBackground(new java.awt.Color(35, 66, 106));
        print.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        print.setForeground(new java.awt.Color(255, 255, 255));
        print.setText("Print Report");
        print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printActionPerformed(evt);
            }
        });
        jPanel2.add(print, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, 260, 40));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, 320, 600));

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

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 200, 567, 233));

        jPanel3.setBackground(new java.awt.Color(35, 66, 106));

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 0, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("SALES AND REVENUE REPORTS");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 60, -1, -1));

        jLabel_TotalRevenue.setText("jLabel3");
        jPanel1.add(jLabel_TotalRevenue, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 460, 220, 40));

        jLabel_MostBooked.setText("jLabel3");
        jPanel1.add(jLabel_MostBooked, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 460, 340, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1032, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 698, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       admindashboard ad = new admindashboard();
    ad.setVisible(true);
    this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printActionPerformed
     StringBuilder sb = new StringBuilder();
    
    // Step 1: HTML Header with CSS
    sb.append("<html><head><meta charset='utf-8'><style>"); // Added meta charset for Peso sign
    sb.append("table { border-collapse: collapse; width: 100%; font-family: 'Segoe UI', Arial; }");
    sb.append("th, td { border: 1px solid #dddddd; text-align: left; padding: 10px; }");
    sb.append("th { background-color: #23426A; color: white; }");
    sb.append("h2 { color: #23426A; font-family: 'Segoe UI Black', Arial; }");
    sb.append(".total { color: #28a745; font-size: 14pt; font-weight: bold; }");
    sb.append("</style></head><body>");
    
    // Step 2: Content
    sb.append("<h2>LOCALHELPER - SERVICE REVENUE REPORT</h2>");
    sb.append("<p>Report Date: ").append(new java.util.Date().toString()).append("</p>");
    
    sb.append("<table>");
    sb.append("<thead><tr><th>ID</th><th>Service Name</th><th>Booked</th><th>Revenue</th></tr></thead>");
    sb.append("<tbody>");

    // Loop through JTable rows
    // Inside your for loop
    for (int i = 0; i < jTable1.getRowCount(); i++) {
        sb.append("<tr>");
        sb.append("<td>").append(jTable1.getValueAt(i, 0)).append("</td>");
        sb.append("<td>").append(jTable1.getValueAt(i, 1)).append("</td>");
        sb.append("<td>").append(jTable1.getValueAt(i, 2)).append("</td>");

        // REMOVED the extra "₱" here because getValueAt(i, 3) already includes it
        sb.append("<td>").append(jTable1.getValueAt(i, 3)).append("</td>"); 
        sb.append("</tr>");
    }

    sb.append("</table><br>");

    // Summary Section
    sb.append("<div style='font-size: 14pt;'>");
    // Ensure your Label text isn't adding another one
    sb.append("<p style='color: #28a745;'><b>").append(jLabel_TotalRevenue.getText()).append("</b></p>");
    sb.append("<p><b>").append(jLabel_MostBooked.getText()).append("</b></p>");
    sb.append("</div>");

    try {
        // Create the file in the user's Documents folder instead of Temp 
        // usahay ang Temp folder naay restriction si Word.
        String userHome = System.getProperty("user.home");
        java.io.File reportFile = new java.io.File(userHome + "\\Desktop\\LocalHelper_Report.doc");

        // Write using UTF-8 to keep the ₱ symbol safe
        java.io.FileOutputStream fos = new java.io.FileOutputStream(reportFile);
        java.io.OutputStreamWriter osw = new java.io.OutputStreamWriter(fos, java.nio.charset.StandardCharsets.UTF_8);
        osw.write(sb.toString());
        osw.close();

        // Step 4: Open in Word
        if (java.awt.Desktop.isDesktopSupported()) {
            java.awt.Desktop.getDesktop().open(reportFile);
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Report saved to Desktop.");
        }
        
    } catch (java.io.IOException e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Error generating report: " + e.getMessage());
    }
    }//GEN-LAST:event_printActionPerformed

    /**
     * @param args the command line arguments
     */
   public static void main(String args[]) {
    // ... (Keep the Look and Feel try-catch) ...

    java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            if (!CONFIG.Session.isLoggedIn()) {
                javax.swing.JOptionPane.showMessageDialog(null, "Please Login First");
                new MAIN.login().setVisible(true);
            } else {
                new reports().setVisible(true);
            }
        }
    });
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel_MostBooked;
    private javax.swing.JLabel jLabel_TotalRevenue;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton print;
    // End of variables declaration//GEN-END:variables
}
