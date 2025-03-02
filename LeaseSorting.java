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
public class LeaseSorting {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/vertexdatabase";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Q@st0y2130g0702";

    public static void sortLeases(JTable leasesTable, String sortBy) {
        String sql = "";

        switch (sortBy) {
            case "A-Z":
                // Sort by lease_status in ascending (alphabetical) order
                sql = "SELECT * FROM leases ORDER BY lease_status ASC";
                break;
            case "Z-A":
                // Sort by lease_status in descending (reverse alphabetical) order
                sql = "SELECT * FROM leases ORDER BY lease_status DESC";
                break;
            case "Newest - Oldest":
                // Sort by lease_id descending
                sql = "SELECT * FROM leases ORDER BY lease_id DESC";
                break;
            case "Oldest - Newest":
                // Sort by lease_id ascending
                sql = "SELECT * FROM leases ORDER BY lease_id ASC";
                break;
            case "Highest - Lowest":
                // Sort by rent_amount descending
                sql = "SELECT * FROM leases ORDER BY rent_amount DESC";
                break;
            case "Lowest - Highest":
                // Sort by rent_amount ascending
                sql = "SELECT * FROM leases ORDER BY rent_amount ASC";
                break;
            default:
                JOptionPane.showMessageDialog(null, 
                        "Invalid sort option!", 
                        "Sorting Error", 
                        JOptionPane.WARNING_MESSAGE);
                return;
        }

        applySorting(leasesTable, sql);
    }

    private static void applySorting(JTable leasesTable, String sql) {
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            DefaultTableModel model = (DefaultTableModel) leasesTable.getModel();
            model.setRowCount(0); // Clear the table before adding sorted rows

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

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                    "Error fetching sorted data: " + e.getMessage(), 
                    "Database Error", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}

