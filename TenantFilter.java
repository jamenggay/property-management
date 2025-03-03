/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.vertexui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Lorenzo
 */
public class TenantFilter {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/vertexdatabase";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Q@st0y2130g0702";

    // Filter by Tenant Name (partial match)
    public static void filterByTenantName(JTable tenantsTable, String tenantName) {
        String sql = "SELECT * FROM tenants WHERE name LIKE ?";
        filterTenants(tenantsTable, sql, "%" + tenantName + "%");
    }

    // Filter by Tenant ID (exact match)
    public static void filterByTenantID(JTable tenantsTable, String tenantID) {
        String sql = "SELECT * FROM tenants WHERE tenant_id = ?";
        filterTenants(tenantsTable, sql, tenantID);
    }

    // Filter by Contact Number (partial match)
    public static void filterByContactNumber(JTable tenantsTable, String contactNumber) {
        String sql = "SELECT * FROM tenants WHERE contact_number LIKE ?";
        filterTenants(tenantsTable, sql, "%" + contactNumber + "%");
    }

    // Filter by Email (partial match)
    public static void filterByTenantEmail(JTable tenantsTable, String email) {
        String sql = "SELECT * FROM tenants WHERE email LIKE ?";
        filterTenants(tenantsTable, sql, "%" + email + "%");
    }

    // Generic filtering method for tenants
    private static void filterTenants(JTable tenantsTable, String sql, String... params) {
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pst = con.prepareStatement(sql)) {

            // Bind parameters
            for (int i = 0; i < params.length; i++) {
                pst.setString(i + 1, params[i]);
            }

            ResultSet rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) tenantsTable.getModel();
            model.setRowCount(0); // Clear the table

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("tenant_id"),
                    rs.getString("name"),
                    rs.getString("contact_number"),
                    rs.getString("email"),
                    rs.getInt("property_id")
                };
                model.addRow(row);
            }

            // Optionally, notify the user if no rows were found
            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, 
                    "No tenants found for Tenant ID: " + params[0],
                    "No Results",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Error fetching data: " + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}