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

    // Filter Maintenance Table based on Request Status (Dropdown)
    public static void filterByRequestStatus(JTable maintenanceTable, String status) {
        String sql = "SELECT * FROM maintenance WHERE status = ?";
        filterMaintenance(maintenanceTable, sql, status);
    }

    // Filter Maintenance Table based on Priority Type (Dropdown)
    public static void filterByPriorityType(JTable maintenanceTable, String priority) {
        String sql = "SELECT * FROM maintenance WHERE priority = ?";
        filterMaintenance(maintenanceTable, sql, priority);
    }

    // Filter Maintenance Table based on Issue Type (Dropdown)
    public static void filterByIssueType(JTable maintenanceTable, String issueType) {
        String sql = "SELECT * FROM maintenance WHERE issue_type = ?";
        filterMaintenance(maintenanceTable, sql, issueType);
    }

    // Filter Maintenance Table based on Property ID (Input)
    public static void filterByPropertyID(JTable maintenanceTable, String propertyID) {
        String sql = "SELECT * FROM maintenance WHERE property_id = ?";
        filterMaintenance(maintenanceTable, sql, propertyID);
    }

    // Filter Maintenance Table based on Request ID (Input)
    public static void filterByRequestID(JTable maintenanceTable, String requestID) {
        String sql = "SELECT * FROM maintenance WHERE request_id = ?";
        filterMaintenance(maintenanceTable, sql, requestID);
    }

    // Generic method to apply filtering
    private static void filterMaintenance(JTable maintenanceTable, String sql, String param) {
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, param);
            ResultSet rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) maintenanceTable.getModel();
            model.setRowCount(0); // Clear table

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
            JOptionPane.showMessageDialog(null, "Error fetching filtered data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
