
package USER;


import ADMIN.Appointments;
import java.awt.Color;
import java.awt.Font;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.border.EmptyBorder;


public class BookingForm extends javax.swing.JFrame {

    int selectedServiceId; 
    double selectedServicePrice;
    
    // Signature Colors
    Color navy = new Color(35, 66, 106);
    Color brown = new Color(106, 75, 35);
    Color bgLight = new Color(245, 245, 245);

    
    javax.swing.JFrame callerFrame;
    
    // Sa babaw, ilisdi ang 'javax.swing.JFrame parent;' ani:

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
    styleForm();
}


    
    public BookingForm(int serviceId, String serviceName, double price) {
        initComponents();
        this.selectedServiceId = serviceId;
        this.selectedServicePrice = price;
        
        // Display Text
        jLabel2.setText("Service: " + serviceName);
        jLabel3.setText("Total Price: ₱" + price);
        
        styleForm();
        setLocationRelativeTo(null);
    }

    private void styleForm() {
        // 1. Main Panels
        jPanel3.setBackground(brown); 
        jPanel2.setBackground(navy); 
        
        // 2. Title Styling
        jLabel1.setFont(new Font("Segoe UI Black", Font.BOLD, 28));
        jLabel1.setForeground(Color.WHITE);

        // 3. Labels Styling (White & Modern Font)
        Font labelFont = new Font("Segoe UI Semibold", Font.PLAIN, 16);
        JLabel[] labels = {jLabel2, jLabel3, jLabel4, jLabel5};
        for (JLabel lbl : labels) {
            lbl.setForeground(Color.WHITE);
            lbl.setFont(labelFont);
        }

        // 4. Input Fields Styling (White with Rounded Padding)
        jTextField1.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        // 5. Button Styling
        styleModernButton(confirm, navy, Color.WHITE);
        styleModernButton(cancel, Color.WHITE, brown);
    }

    private void styleModernButton(JButton btn, Color bg, Color fg) {
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(10, 20, 10, 20));
        btn.setFont(new Font("Segoe UI Bold", Font.PLAIN, 16));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover Effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(bg.brighter());
                if(bg.equals(Color.WHITE)) btn.setBackground(new Color(230, 230, 230));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(bg);
            }
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
        confirm.setFont(new java.awt.Font("Segoe UI Black", 0, 20)); // NOI18N
        confirm.setForeground(new java.awt.Color(255, 255, 255));
        confirm.setText("CONFIRM BOOKING");
        confirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmActionPerformed(evt);
            }
        });
        jPanel3.add(confirm, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 490, 240, 70));

        cancel.setBackground(new java.awt.Color(35, 66, 106));
        cancel.setFont(new java.awt.Font("Segoe UI Black", 0, 20)); // NOI18N
        cancel.setForeground(new java.awt.Color(255, 255, 255));
        cancel.setText("CANCEL");
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });
        jPanel3.add(cancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 490, 240, 70));

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

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
      // Kani ang redirect padulong sa User Dashboard
        new udashboard().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_cancelActionPerformed

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
