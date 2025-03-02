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
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Lorenzo
 */
public class PropertySorting {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/vertexdatabase";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Q@st0y2130g0702";

    // Sort Properties Table
    public static void sortProperties(JTable propertiesTable, String sortBy) {
        String sql = "";

        switch (sortBy) {
            case "A-Z":
                sql = "SELECT * FROM properties ORDER BY property_name ASC";
                break;
            case "Z-A":
                sql = "SELECT * FROM properties ORDER BY property_name DESC";
                break;
            case "Newest - Oldest":
                sql = "SELECT * FROM properties ORDER BY property_id DESC";
                break;
            case "Oldest - Newest":
                sql = "SELECT * FROM properties ORDER BY property_id ASC";
                break;
            case "Highest - Lowest": // Sorting by property_id
                sql = "SELECT * FROM properties ORDER BY property_id DESC";
                break;
            case "Lowest - Highest": // Sorting by property_id
                sql = "SELECT * FROM properties ORDER BY property_id ASC";
                break;
            default:
                JOptionPane.showMessageDialog(null, "Invalid sort option!", "Sorting Error", JOptionPane.WARNING_MESSAGE);
                return;
        }

        applySorting(propertiesTable, sql);
    }

    private static void applySorting(JTable propertiesTable, String sql) {
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            DefaultTableModel model = (DefaultTableModel) propertiesTable.getModel();
            model.setRowCount(0); // Clear the table before adding new rows

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("property_id"), 
                    rs.getString("property_name"),
                    rs.getString("type"),
                    rs.getString("house_number").replace(" ", ""),
                    rs.getString("street"),
                    rs.getString("municipality"),
                    rs.getString("province"),
                    rs.getString("country"),
                    rs.getString("availability")
                };
                model.addRow(row);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching sorted data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

