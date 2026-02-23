
package CONFIG;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;


public class config {
    //Connection Method to SQLITE
public static Connection connectDB() {
        Connection con = null;
        try {
            Class.forName("org.sqlite.JDBC"); // Load the SQLite JDBC driver
            con = DriverManager.getConnection("jdbc:sqlite:hservice.db"); // Establish connection
            System.out.println("Connection Successful");
        } catch (Exception e) {
            System.out.println("Connection Failed: " + e);
        }
        return con;
    }

    public static Connection getConnection() {
    return connectDB();
}

    
    public void addRecord(String sql, Object... values) {
    try (Connection conn = this.connectDB(); // Use the connectDB method
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        // Loop through the values and set them in the prepared statement dynamically
        for (int i = 0; i < values.length; i++) {
            if (values[i] instanceof Integer) {
                pstmt.setInt(i + 1, (Integer) values[i]); // If the value is Integer
            } else if (values[i] instanceof Double) {
                pstmt.setDouble(i + 1, (Double) values[i]); // If the value is Double
            } else if (values[i] instanceof Float) {
                pstmt.setFloat(i + 1, (Float) values[i]); // If the value is Float
            } else if (values[i] instanceof Long) {
                pstmt.setLong(i + 1, (Long) values[i]); // If the value is Long
            } else if (values[i] instanceof Boolean) {
                pstmt.setBoolean(i + 1, (Boolean) values[i]); // If the value is Boolean
            } else if (values[i] instanceof java.util.Date) {
                pstmt.setDate(i + 1, new java.sql.Date(((java.util.Date) values[i]).getTime())); // If the value is Date
            } else if (values[i] instanceof java.sql.Date) {
                pstmt.setDate(i + 1, (java.sql.Date) values[i]); // If it's already a SQL Date
            } else if (values[i] instanceof java.sql.Timestamp) {
                pstmt.setTimestamp(i + 1, (java.sql.Timestamp) values[i]); // If the value is Timestamp
            } else {
                pstmt.setString(i + 1, values[i].toString()); // Default to String for other types
            }
        }

        pstmt.executeUpdate();
        System.out.println("Record added successfully!");
    } catch (SQLException e) {
        System.out.println("Error adding record: " + e.getMessage());
    }
}
    public boolean authenticate(String sql, Object... values) {
    try (Connection conn = connectDB();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        for (int i = 0; i < values.length; i++) {
            pstmt.setObject(i + 1, values[i]);
        }

        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return true;
            }
        }
    } catch (SQLException e) {
        System.out.println("Login Error: " + e.getMessage());
    }
    return false;
}
    
   public void displayData(String sql, JTable table) {
        try (Connection conn = connectDB()) {
            if (conn == null) {
                System.out.println("Connection is null! Cannot display data.");
                return;
            }
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            System.out.println("Error displaying data: " + e.getMessage());
        }
    }

   public void executeSQL(String sql, Object... params) {
        try (Connection conn = connectDB()) {
            if (conn == null) {
                System.out.println("Connection is null! Cannot execute SQL.");
                return;
            }
            PreparedStatement pst = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                pst.setObject(i + 1, params[i]);
            }
            int rows = pst.executeUpdate();
            System.out.println(rows + " row(s) affected.");
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

    public ResultSet getData(String sql) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
   public void sessionGuard(javax.swing.JFrame currentFrame) {
    // Use the Session getter since userId is now private
    if (CONFIG.Session.getUserId() == 0) {
        // Message box appears first
        javax.swing.JOptionPane.showMessageDialog(currentFrame, 
            "Please Login First!", 
            "Authentication Required", 
            javax.swing.JOptionPane.ERROR_MESSAGE);
            
        // Redirection logic triggers after OK is clicked
        MAIN.login loginWindow = new MAIN.login();
        loginWindow.setVisible(true);
        loginWindow.pack();
        loginWindow.setLocationRelativeTo(null);
        
        // Close the current unauthorized window
        currentFrame.dispose();
    }
}

    // Method to update records in the database
public void updateRecord(String sql, Object... values) {
    try (Connection conn = this.connectDB();
         java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {

        // Set parameters dynamically
        for (int i = 0; i < values.length; i++) {
            pstmt.setObject(i + 1, values[i]);
        }

        int rowsAffected = pstmt.executeUpdate();
        
        if (rowsAffected > 0) {
            System.out.println("Update Successful! Rows changed: " + rowsAffected);
        } else {
            // If this prints, your ID in the TextField doesn't exist in the DB
            System.out.println("Update Failed: No row found with ID " + values[values.length - 1]);
        }

    } catch (java.sql.SQLException e) {
        System.out.println("SQL Error: " + e.getMessage());
    }
}

public void displayResultSet(ResultSet rs, JTable table) {
        try {
            if (rs == null) return;

            ResultSetMetaData meta = rs.getMetaData();
            int columnCount = meta.getColumnCount();

            // Create column names dynamically
            String[] columnNames = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {
                columnNames[i] = meta.getColumnLabel(i + 1);
            }

            // Create table model
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);

            // Add rows
            while (rs.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    rowData[i] = rs.getObject(i + 1);
                }
                model.addRow(rowData);
            }

            table.setModel(model);
        } catch (Exception e) {
            System.out.println("Error displaying ResultSet: " + e.getMessage());
        }
    }

    public ResultSet getDataWithParam(String sql, int userId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   public boolean insertData(String sql) {
    try {
        java.sql.PreparedStatement pst = getConnection().prepareStatement(sql);
        int rowsAffected = pst.executeUpdate();
        pst.close();
        return rowsAffected > 0;
    } catch (java.sql.SQLException e) {
        System.out.println("Error inserting data: " + e.getMessage());
        return false;
    }
}
    
}
    
