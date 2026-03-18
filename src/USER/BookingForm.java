
package USER;

import ADMIN.Appointments;


public class BookingForm extends javax.swing.JFrame {

    int selectedServiceId; 
    double selectedServicePrice;
    
    // Sa babaw, ilisdi ang 'javax.swing.JFrame parent;' ani:
javax.swing.JFrame callerFrame; 

// Update imong Constructor:
public BookingForm(javax.swing.JFrame frame) {
    initComponents();
    this.callerFrame = frame; // Diri nato i-save ang Appointments frame
    setLocationRelativeTo(null);
    new CONFIG.config().sessionGuard(this);
    
    this.selectedServiceId = 0; 
    this.selectedServicePrice = 0.0;
    
    jLabel2.setText("Admin Booking Mode");
    jLabel3.setText("Manual Entry");
    styleBookingUI();
}

    
    public BookingForm(int serviceId, String serviceName, double price) {
    initComponents();
    new CONFIG.config().sessionGuard(this);
   this.selectedServiceId = serviceId; // Save the ID passed from the dashboard
    this.selectedServicePrice = price;

    // Display the info on your labels
    // Note: You should give your labels better names in Design View 
    // for easier coding (e.g., lbl_name instead of jLabel2)
    jLabel2.setText("<html><font color='#555555'>Service:</font> <font color='#23426A'>" + serviceName + "</font></html>");
    jLabel3.setText("<html><font color='#555555'>Total Price:</font> <font color='#27ae60'><b>₱" + price + "</b></font></html>");
    
    styleBookingUI();
}

    private void styleBookingUI() {
    // 1. Background (Light color ni, so dapat dark ang text)
    jPanel3.setBackground(new java.awt.Color(248, 249, 250)); 
    jPanel2.setBackground(new java.awt.Color(35, 66, 106)); 

    // 2. I-force ang color sa tanan labels para makita jud sila
    java.awt.Color darkText = new java.awt.Color(50, 50, 50); // Dark Gray
    
    jLabel1.setForeground(java.awt.Color.WHITE); // Title (kay Navy man ang header)
    jLabel2.setForeground(darkText); // Service Name
    jLabel3.setForeground(darkText); // Price
    jLabel4.setForeground(darkText); // Date
    jLabel5.setForeground(darkText); // Address
    
    // Modernize labels font
    java.awt.Font modernFont = new java.awt.Font("Segoe UI Semibold", java.awt.Font.PLAIN, 16);
    jLabel2.setFont(modernFont);
    jLabel3.setFont(modernFont);
    jLabel4.setFont(modernFont);
    jLabel5.setFont(modernFont);
    
    // Ang uban nimo nga code (Inputs, Buttons, etc.)
    // ... (keep your existing spinner and button styles) ...
}
 
 
    
private void styleButton(javax.swing.JButton btn, java.awt.Color bg) {
    btn.setBackground(bg);
    btn.setFocusPainted(false);
    btn.setBorderPainted(false);
    btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    btn.setFont(new java.awt.Font("Segoe UI Bold", java.awt.Font.PLAIN, 15));
}
   
   private void styleInput(javax.swing.JComponent input) {
    input.setBorder(javax.swing.BorderFactory.createCompoundBorder(
        javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 200, 200), 1),
        javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
    input.setBackground(java.awt.Color.WHITE);
}

private void styleBookingButton(javax.swing.JButton btn, java.awt.Color bg) {
    btn.setBackground(bg);
    btn.setForeground(java.awt.Color.WHITE);
    btn.setFocusPainted(false);
    btn.setBorderPainted(false);
    btn.setFont(new java.awt.Font("Segoe UI Bold", java.awt.Font.PLAIN, 16));
    btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    
    // Hover Animation
    btn.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseEntered(java.awt.event.MouseEvent evt) { btn.setBackground(bg.brighter()); }
        public void mouseExited(java.awt.event.MouseEvent evt) { btn.setBackground(bg); }
    });
}
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        confirm = new javax.swing.JButton();
        cancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(106, 75, 35));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(35, 66, 106));

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Book a Service Form");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 807, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 0, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Service Name:");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(47, 134, 480, 70));

        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 0, 20)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Service Price:");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(47, 223, 500, 45));

        jSpinner1.setModel(new javax.swing.SpinnerDateModel());
        jPanel3.add(jSpinner1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 300, 250, 44));

        jLabel4.setFont(new java.awt.Font("Segoe UI Black", 0, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Booking Date:");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 310, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI Black", 0, 20)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Service Address:");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 380, 175, 41));
        jPanel3.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 380, 249, 41));

        confirm.setBackground(new java.awt.Color(35, 66, 106));
        confirm.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        confirm.setForeground(new java.awt.Color(255, 255, 255));
        confirm.setText("CONFIRM BOOKING");
        confirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmActionPerformed(evt);
            }
        });
        jPanel3.add(confirm, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 490, 270, 70));

        cancel.setBackground(new java.awt.Color(35, 66, 106));
        cancel.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        cancel.setForeground(new java.awt.Color(255, 255, 255));
        cancel.setText("CANCEL");
        jPanel3.add(cancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 490, 270, 70));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void confirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmActionPerformed
    String address = jTextField1.getText().trim();
    java.util.Date spinnerDate = (java.util.Date) jSpinner1.getValue();
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String formattedDateTime = sdf.format(spinnerDate);

    if(address.isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(this, "Please enter a service address.");
        return;
    }

    try {
        CONFIG.config conf = new CONFIG.config();
        String sql = "INSERT INTO tbl_bookings (u_id, s_id, b_date, b_status, b_total, b_address) VALUES (?, ?, ?, 'PENDING', ?, ?)";
        
        java.sql.Connection conn = conf.connectDB();
        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
        
        pst.setInt(1, CONFIG.Session.getUserId());
        pst.setInt(2, this.selectedServiceId);
        pst.setString(3, formattedDateTime);
        pst.setDouble(4, this.selectedServicePrice);
        pst.setString(5, address);

        pst.executeUpdate();
javax.swing.JOptionPane.showMessageDialog(this, "Booking Successful!");

// Check kung gikan ba sa Appointments ang nag-abli
if (callerFrame != null && callerFrame instanceof Appointments) {
    ((Appointments)callerFrame).displayAllAppointments(); // Refresh Admin Table
    this.dispose(); 
} else {
    // Kung user ang nag book
    new udashboard().setVisible(true);
    this.dispose();
}
        
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    }
    }//GEN-LAST:event_confirmActionPerformed

    /**
     * @param args the command line arguments
     */
   public static void main(String args[]) {
    // ... (Keep the Nimbus look and feel code) ...

    java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            if (!CONFIG.Session.isLoggedIn()) {
                javax.swing.JOptionPane.showMessageDialog(null, "Please log in to book a service.");
                new MAIN.login().setVisible(true);
            } else {
                // If running directly for test, it uses placeholders
                new BookingForm(0, "Test Service", 0.0).setVisible(true);
            }
        }
    });
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancel;
    private javax.swing.JButton confirm;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables

   
}
