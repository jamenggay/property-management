package com.mycompany.vertexui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class MaintenanceSorting {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/vertexdatabase";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Q@st0y2130g0702";

    // Sort Maintenance Table
    public static void sortMaintenanceRequests(JTable maintenanceTable, String sortBy) {
        String sql = "";

        switch (sortBy) {
            case "A-Z":
                sql = "SELECT * FROM maintenanceTable ORDER BY issue_type ASC";
                break;
            case "Z-A":
                sql = "SELECT * FROM maintenanceTable ORDER BY issue_type DESC";
                break;
            case "Newest - Oldest":
                sql = "SELECT * FROM maintenanceTable ORDER BY request_id DESC";
                break;
            case "Oldest - Newest":
                sql = "SELECT * FROM maintenanceTable ORDER BY request_id ASC";
                break;
            case "Highest - Lowest": // Sorting by priority
                sql = "SELECT * FROM maintenanceTable ORDER BY priority DESC";
                break;
            case "Lowest - Highest": // Sorting by priority
                sql = "SELECT * FROM maintenanceTable ORDER BY priority ASC";
                break;
            default:
                JOptionPane.showMessageDialog(null, "Invalid sort option!", "Sorting Error", JOptionPane.WARNING_MESSAGE);
                return;
        }

        applySorting(maintenanceTable, sql);
    }

    private static void applySorting(JTable maintenanceTable, String sql) {
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            DefaultTableModel model = (DefaultTableModel) maintenanceTable.getModel();
            model.setRowCount(0); // Clear the table before adding new rows

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("request_id"),
                    rs.getInt("tenant_id"),
                    rs.getInt("property_id"),
                    rs.getString("issue_type"),
                    rs.getString("description"),
                    rs.getString("status"),
                    rs.getString("priority")
                };
                model.addRow(row);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching sorted data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
