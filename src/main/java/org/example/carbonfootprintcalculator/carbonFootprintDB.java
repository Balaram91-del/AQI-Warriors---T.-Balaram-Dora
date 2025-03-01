package org.example.carbonfootprintcalculator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class carbonFootprintDB {
    // Method to insert carbon footprint data
    public static void insertCarbonFootprint(String username, int month, int year, double footprintValue,double carbonCredits) {
        String sql = "INSERT INTO usercarbonFootprint (username, month, year, carbonFootprint,carbonCredits) VALUES (?, ?, ?, ?,?)";

        // Get database connection from databaseConnection class
        databaseConnection dbConnection = new databaseConnection();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (conn == null) {
                System.err.println("❌ Failed to connect to database. Aborting insert.");
                return;
            }

            // Set parameters
            pstmt.setString(1, username);
            pstmt.setInt(2, month);
            pstmt.setInt(3, year);
            pstmt.setDouble(4, footprintValue);
            pstmt.setDouble(5, carbonCredits);
            // Execute the update
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("✅ Carbon footprint recorded successfully!");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error inserting carbon footprint data!");
            e.printStackTrace();
        }
    }
}
