

package CONFIG;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;


public class config {
    
    //Connection Method to SQLITE
public static Connection connectDB() {
    Connection con = null;
    try {
        Class.forName("org.sqlite.JDBC");

        // 1. Gamita ang tibuok path para sigurado nga usa ra ka file ang ablihan
        // PAHINUMDOM: Gamita ang forward slash (/) imbis nga backslash (\)
        String dbPath = "jdbc:sqlite:C:/Users/Jingkie/Videos/CANES UPDATED APR 6/ELLA_2A/hservice.db";
        
        con = DriverManager.getConnection(dbPath);

        // 🔥 Busy timeout
        java.sql.Statement stmt = con.createStatement();
        stmt.execute("PRAGMA busy_timeout = 5000");

        System.out.println("Connection Successful to: " + dbPath);
    } catch (Exception e) {
        System.out.println("Connection Failed: " + e);
    }
    return con;
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

    public ResultSet getData(String sql) {
    try {
        // Ayaw i-close ang connection DIREKTA diri kay kinahanglan pa ang ResultSet
        // Pero siguruha nga ang migamit ani nga method mo-close sa ResultSet sa iyang end
        Connection conn = connectDB();
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(sql);
    } catch (SQLException e) { // Mas maayo SQLException kaysa Exception lang
        System.out.println("GetData Error: " + e.getMessage());
        return null;
    }
}

   public boolean insertData(String sql) {
    // Gigamit nato ang try-with-resources para automatic ma-close ang connection
    try (java.sql.Connection conn = connectDB(); 
         java.sql.PreparedStatement pst = conn.prepareStatement(sql)) {

        int rowsAffected = pst.executeUpdate();
        
        if (rowsAffected > 0) {
            System.out.println("Data inserted successfully!");
            return true;
        } else {
            System.out.println("No rows affected.");
            return false;
        }

    } catch (java.sql.SQLException e) {
        // Importante ni para mahibalo ta kung ngano nag-error (e.g. Duplicate ID)
        System.out.println("Error inserting data: " + e.getMessage());
        return false;
    }
}
   
   // I-paste kini sa imong CONFIG/config.java nga class
public void deleteData(int id, String table, String column) {
    // Gigamit nato ang variable 'con' gikan sa imong connectDB() method
    String sql = "DELETE FROM " + table + " WHERE " + column + " = ?";
    
    try (java.sql.Connection con = connectDB(); 
         java.sql.PreparedStatement pst = con.prepareStatement(sql)) {
        
        // Pag-set sa ID nga i-delete
        pst.setInt(1, id);
        
        int rowsDeleted = pst.executeUpdate();
        
        if (rowsDeleted > 0) {
            javax.swing.JOptionPane.showMessageDialog(null, "Deleted Successfully!");
        } else {
            javax.swing.JOptionPane.showMessageDialog(null, "No record found with ID: " + id);
        }
        
    } catch (java.sql.SQLException e) {
        // I-print sa output para makita nato ang error details sa NetBeans
        System.out.println("Delete Error: " + e.getMessage());
        javax.swing.JOptionPane.showMessageDialog(null, "Delete Error: " + e.getMessage());
    }
}

    // Sa sulod sa CONFIG/config.java
public void getData(String sql, JTable table) {
    try (Connection conn = connectDB();
         PreparedStatement pst = conn.prepareStatement(sql);
         ResultSet rs = pst.executeQuery()) {

        table.setModel(DbUtils.resultSetToTableModel(rs));

    } catch (SQLException e) {
        System.out.println("Error: " + e.getMessage());
    }
}

        public void deleteData(String sql) {
    // Kinahanglan i-open ang connection sa sulod sa try block
    try (java.sql.Connection conn = connectDB();
         java.sql.PreparedStatement pst = conn.prepareStatement(sql)) {
        
        int rowsDeleted = pst.executeUpdate();
        
        if (rowsDeleted > 0) {
            javax.swing.JOptionPane.showMessageDialog(null, "Data deleted successfully!");
        } else {
            javax.swing.JOptionPane.showMessageDialog(null, "No record found to delete.");
        }
        
    } catch (java.sql.SQLException e) {
        System.out.println("Delete Error: " + e.getMessage());
        javax.swing.JOptionPane.showMessageDialog(null, "Delete Error: " + e.getMessage());
    }
}
        
public void updateData(String sql) {
    // Gigamit nato ang connectDB() para sa saktong path sa hservice.db
    try (java.sql.Connection conn = connectDB(); 
         java.sql.PreparedStatement pst = conn.prepareStatement(sql)) {

        int rowsUpdated = pst.executeUpdate();

        if (rowsUpdated > 0) {
            javax.swing.JOptionPane.showMessageDialog(null, "Successfully Updated!");
        } else {
            javax.swing.JOptionPane.showMessageDialog(null, "No record found to update.");
        }

    } catch (java.sql.SQLException e) {
        // I-print sa output para dali ra i-debug kung naay error sa SQL syntax
        System.out.println("Update Error: " + e.getMessage());
        javax.swing.JOptionPane.showMessageDialog(null, "Update Error: " + e.getMessage());
    }
}
public interface TableRefresher {
    void onDataChange();
}

    
}
    
