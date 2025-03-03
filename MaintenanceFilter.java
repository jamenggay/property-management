package com.mycompany.vertexui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class MaintenanceFilter {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/vertexdatabase";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Q@st0y2130g0702";

    // Filter Pending Requests
    public static void filterPendingRequests(JTable maintenanceTable) {
        String sql = "SELECT * FROM maintenance WHERE status = 'Pending'";
        filterMaintenance(maintenanceTable, sql);
    }

    // Filter In Progress Requests
    public static void filterInProgressRequests(JTable maintenanceTable) {
        String sql = "SELECT * FROM maintenance WHERE status = 'In Progress'";
        filterMaintenance(maintenanceTable, sql);
    }

    // Filter Resolved Requests
    public static void filterResolvedRequests(JTable maintenanceTable) {
        String sql = "SELECT * FROM maintenance WHERE status = 'Completed'";
        filterMaintenance(maintenanceTable, sql);
    }

    // Filter Low Priority Requests
    public static void filterLowPriority(JTable maintenanceTable) {
        String sql = "SELECT * FROM maintenance WHERE priority = 'Non-urgent'";
        filterMaintenance(maintenanceTable, sql);
    }

    // Filter Medium Priority Requests (Does not exist but included in the filter)
    public static void filterMediumPriority(JTable maintenanceTable) {
        String sql = "SELECT * FROM maintenance WHERE priority = 'Medium'";
        filterMaintenance(maintenanceTable, sql);
    }

    // Filter High Priority Requests
    public static void filterHighPriority(JTable maintenanceTable) {
        String sql = "SELECT * FROM maintenance WHERE priority = 'Urgent'";
        filterMaintenance(maintenanceTable, sql);
    }

    // Filter by Property ID
    public static void filterByPropertyID(JTable maintenanceTable, String propertyID) {
        String sql = "SELECT * FROM maintenance WHERE property_id = ?";
        filterMaintenance(maintenanceTable, sql, propertyID);
    }

    // Generic filtering method
    private static void filterMaintenance(JTable maintenanceTable, String sql, String... params) {
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pst = con.prepareStatement(sql)) {

            // If there are parameters, set them in the query
            for (int i = 0; i < params.length; i++) {
                pst.setString(i + 1, params[i]);
            }

            ResultSet rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) maintenanceTable.getModel();
            model.setRowCount(0); // Clear the table before adding new rows

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("request_id"), 
                    rs.getInt("tenant_id"),
                    rs.getInt("property_id"),
                    rs.getString("issue_type"),
                    rs.getString("description"),
                    rs.getString("priority"),
                    rs.getString("status")
                };
                model.addRow(row);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
