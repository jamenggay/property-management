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
public class LeasesFilter {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/vertexdatabase";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Q@st0y2130g0702";

    // Filter by specific Lease ID (exact match)
    public static void filterByLeaseID(JTable leasesTable, String leaseID) {
        String sql = "SELECT * FROM leases WHERE lease_id = ?";
        filterLeases(leasesTable, sql, leaseID);
    }

    // Filter by Tenant ID (exact match)
    public static void filterByTenantID(JTable leasesTable, String tenantID) {
        String sql = "SELECT * FROM leases WHERE tenant_id = ?";
        filterLeases(leasesTable, sql, tenantID);
    }

    // Filter by Property ID (exact match)
    public static void filterByPropertyID(JTable leasesTable, String propertyID) {
        String sql = "SELECT * FROM leases WHERE property_id = ?";
        // Convert propertyID to integer if needed; here we assume the DB accepts the string
        filterLeases(leasesTable, sql, propertyID);
    }

    // Filter only "Active" leases
    public static void filterActiveLeases(JTable leasesTable) {
        String sql = "SELECT * FROM leases WHERE lease_status = 'active'";
        filterLeases(leasesTable, sql);
    }

    // Filter only "Expired" leases
    public static void filterExpiredLeases(JTable leasesTable) {
        String sql = "SELECT * FROM leases WHERE lease_status = 'expired'";
        filterLeases(leasesTable, sql);
    }

    // Filter only "Terminated" leases
    public static void filterTerminatedLeases(JTable leasesTable) {
        String sql = "SELECT * FROM leases WHERE lease_status = 'terminated'";
        filterLeases(leasesTable, sql);
    }
    
    

    // Generic filtering method
    private static void filterLeases(JTable leasesTable, String sql, Object... params) {
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pst = con.prepareStatement(sql)) {

            // Bind parameters to the PreparedStatement
            for (int i = 0; i < params.length; i++) {
                // If the parameter is an integer, bind it as such; otherwise, bind as string
                if (params[i] instanceof Integer) {
                    pst.setInt(i + 1, (Integer) params[i]);
                } else {
                    pst.setString(i + 1, params[i].toString());
                }
            }

            ResultSet rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) leasesTable.getModel();
            model.setRowCount(0); // Clear the table before adding new rows

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("lease_id"),
                    rs.getInt("property_id"),
                    rs.getInt("tenant_id"),
                    rs.getDate("lease_start"),
                    rs.getDate("lease_end"),
                    rs.getString("lease_status"),
                    rs.getString("payment_frequency"),
                    rs.getDouble("security_deposit"),
                    rs.getDouble("rent_amount")
                };
                model.addRow(row);
            }
            
            // If no rows were added, notify the user
            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, 
                    "No leases found for Property ID: " + params[0], 
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

