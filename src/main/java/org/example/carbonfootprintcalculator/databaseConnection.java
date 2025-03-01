package org.example.carbonfootprintcalculator;

import java.sql.*;

public class databaseConnection {
    private static final String DATABASE_NAME = "appDatabase";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "@Balaram69";
    private static final String URL = "jdbc:mysql://localhost:3306/" + DATABASE_NAME +
            "?useSSL=false&allowPublicKeyRetrieval=true";

    private Connection databaseLink;

    public Connection getConnection() {
        try {
            databaseLink = DriverManager.getConnection(URL, DATABASE_USER, DATABASE_PASSWORD);
            System.out.println("✅ Database connection successful!");
            return databaseLink;  // Return valid connection
        } catch (SQLException e) {
            System.err.println("❌ Database connection failed!");
            System.err.println("Error Message: " + e.getMessage());
            e.printStackTrace();
            return null;  // Ensure a failed connection returns null
        }
    }
    public static void insertCarbonFootprint(String username, int month, int year, double footprintValue) {
        String sql = "INSERT INTO carbon_footprint (username, month, year, carbonFootprint) VALUES (?, ?, ?, ?)";

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

            // Execute the update
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Carbon footprint recorded successfully!");
            }
        } catch (SQLException e) {
            System.err.println("Error inserting carbon footprint data!");
            e.printStackTrace();
        }
    }
    public static int getTotalCredits() {
        String query = "SELECT SUM(carbonCredits) FROM userCarbonFootprint";
        return executeQuery(query);
    }

    // ✅ Get Yearly Carbon Credits
    public static int getYearlyCredits(int year) {
        String query = "SELECT SUM(carbonCredits) FROM userCarbonFootprint WHERE year = ?";
        return executeQueryWithParam(query, year);
    }

    // ✅ Get Monthly Carbon Credits
    public static int getMonthlyCredits(int month, int year) {
        String query = "SELECT SUM(carbonCredits) FROM userCarbonFootprint WHERE month = ? AND year = ?";
        return executeQueryWithParams(query, month, year);
    }

    // Helper method to execute query without parameters
    private static int executeQuery(String query) {
        databaseConnection dbConnection = new databaseConnection();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error executing query: " + query);
            e.printStackTrace();
        }
        return 0;
    }

    // Helper method to execute query with 1 parameter
    private static int executeQueryWithParam(String query, int param) {
        databaseConnection dbConnection = new databaseConnection();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, param);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error executing query with param: " + param);
            e.printStackTrace();
        }
        return 0;
    }

    // Helper method to execute query with 2 parameters
    private static int executeQueryWithParams(String query, int param1, int param2) {
        databaseConnection dbConnection = new databaseConnection();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, param1);
            stmt.setInt(2, param2);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error executing query with params: " + param1 + ", " + param2);
            e.printStackTrace();
        }
        return 0;
    }

}
