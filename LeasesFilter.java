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
    // Update these credentials and DB name as needed
    private static final String DB_URL = "jdbc:mysql://localhost:3306/vertexdatabase";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Q@st0y2130g0702";

    
    public static void filterByLeaseID(JTable leasesTable, String leaseID) {
        String sql = "SELECT * FROM leases WHERE lease_id = ?";
        filterLeases(leasesTable, sql, leaseID);
    }

    
    public static void filterActiveLeases(JTable leasesTable) {
        String sql = "SELECT * FROM leases WHERE lease_status = 'active'";
        filterLeases(leasesTable, sql);
    }

    
    public static void filterExpiredLeases(JTable leasesTable) {
        String sql = "SELECT * FROM leases WHERE lease_status = 'expired'";
        filterLeases(leasesTable, sql);
    }

    
    public static void filterTerminatedLeases(JTable leasesTable) {
        String sql = "SELECT * FROM leases WHERE lease_status = 'terminated'";
        filterLeases(leasesTable, sql);
    }

    
    private static void filterLeases(JTable leasesTable, String sql, String... params) {
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pst = con.prepareStatement(sql)) {

            // Bind parameters to the PreparedStatement
            for (int i = 0; i < params.length; i++) {
                pst.setString(i + 1, params[i]);
            }

            ResultSet rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) leasesTable.getModel();
            model.setRowCount(0); // Clear existing rows in the table

            while (rs.next()) {
                // Match columns from your 'leases' table
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

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                "Error fetching data: " + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}

