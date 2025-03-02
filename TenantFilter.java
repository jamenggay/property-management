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
    // Update these credentials as needed
    private static final String DB_URL = "jdbc:mysql://localhost:3306/vertexdatabase";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Q@st0y2130g0702";

    /**
     * Filter tenants by Name (partial match).
     * If you want exact match, replace "LIKE" with "=" in the SQL query.
     */
    public static void filterByTenantName(JTable tenantsTable, String tenantName) {
        String sql = "SELECT * FROM tenants WHERE name LIKE ?";
        // Using '%' + name + '%' allows partial matches
        filterTenants(tenantsTable, sql, "%" + tenantName + "%");
    }

    /**
     * Filter tenants by Tenant ID (exact match).
     * If tenant_id is an INT, you can do "tenant_id = ?" 
     * and pass the string directly — MySQL will do an implicit cast. 
     */
    public static void filterByTenantID(JTable tenantsTable, String tenantID) {
        String sql = "SELECT * FROM tenants WHERE tenant_id = ?";
        filterTenants(tenantsTable, sql, tenantID);
    }

    /**
     * Filter tenants by Email (partial match).
     * If you want an exact match, replace "LIKE" with "=".
     */
    public static void filterByTenantEmail(JTable tenantsTable, String email) {
        String sql = "SELECT * FROM tenants WHERE email LIKE ?";
        filterTenants(tenantsTable, sql, "%" + email + "%");
    }

    /**
     * Optional: Filter tenants by Contact Number (partial match).
     * This helps match phone numbers that may include a +63 prefix or spacing.
     */
    public static void filterByContactNumber(JTable tenantsTable, String contactNumber) {
        String sql = "SELECT * FROM tenants WHERE contact_number LIKE ?";
        filterTenants(tenantsTable, sql, "%" + contactNumber + "%");
    }

    /**
     * Reusable method that executes the query and updates the JTable.
     * @param tenantsTable The JTable displaying tenant data.
     * @param sql The SQL query with placeholders (e.g., WHERE column = ?).
     * @param params The parameters to bind to the prepared statement.
     */
    private static void filterTenants(JTable tenantsTable, String sql, String... params) {
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pst = con.prepareStatement(sql)) {

            // Bind parameters to the PreparedStatement
            for (int i = 0; i < params.length; i++) {
                pst.setString(i + 1, params[i]);
            }

            // Execute query
            ResultSet rs = pst.executeQuery();

            // Get table model and clear existing rows
            DefaultTableModel model = (DefaultTableModel) tenantsTable.getModel();
            model.setRowCount(0);

            // Populate table with results
            while (rs.next()) {
                // Make sure these match your 'tenants' table columns
                Object[] row = {
                    rs.getInt("tenant_id"),
                    rs.getString("name"),
                    rs.getString("contact_number"),
                    rs.getString("email"),
                    rs.getInt("property_id")
                };
                model.addRow(row);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                "Error fetching data: " + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}


