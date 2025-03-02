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

public class TenantSorting {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/vertexdatabase";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Q@st0y2130g0702";    
    
    public static void sortTenants(JTable tenantsTable, String sortBy) {
        String sql = "";
        
        switch (sortBy) {
            case "A-Z":
                sql = "SELECT * FROM tenants ORDER BY name ASC";
                break;
            case "Z-A":
                sql = "SELECT * FROM tenants ORDER BY name DESC";
                break;
            case "Newest - Oldest":
                sql = "SELECT * FROM tenants ORDER BY tenant_id DESC";
                break;
            case "Oldest - Newest":
                sql = "SELECT * FROM tenants ORDER BY tenant_id ASC";
                break;
            case "Highest - Lowest": // Using property_id as the sorting column
                sql = "SELECT * FROM tenants ORDER BY property_id DESC";
                break;
            case "Lowest - Highest":
                sql = "SELECT * FROM tenants ORDER BY property_id ASC";
                break;
            default:
                JOptionPane.showMessageDialog(null, "Invalid sort option!", "Sorting Error", JOptionPane.WARNING_MESSAGE);
                return;
        }
        
        applySorting(tenantsTable, sql);
    }
    
    /**
     * Executes the provided SQL query and updates the JTable model.
     * 
     * @param tenantsTable The JTable to update.
     * @param sql The SQL query to execute.
     */
    private static void applySorting(JTable tenantsTable, String sql) {
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
             
            DefaultTableModel model = (DefaultTableModel) tenantsTable.getModel();
            model.setRowCount(0); // Clear the table before adding sorted rows
            
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
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching sorted data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

