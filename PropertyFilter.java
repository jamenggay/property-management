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
public class PropertyFilter {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/vertexdatabase";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Q@st0y2130g0702";

    // Filter by property type (e.g., Apartment, Condominium)
    public static void filterTableByPropertyType(JTable propertiesTable, String propertyType) {
        String sql = "SELECT * FROM properties WHERE type = ?";
        filterProperties(propertiesTable, sql, propertyType);
    }

    // Filter only vacant properties
    public static void filterVacantProperties(JTable propertiesTable) {
        String sql = "SELECT * FROM properties WHERE availability = 'vacant'";
        filterProperties(propertiesTable, sql);
    }
    
    // Filter only occupied properties
    public static void filterOccupiedProperties(JTable propertiesTable) {
        String sql = "SELECT * FROM properties WHERE availability = 'occupied'";
        filterProperties(propertiesTable, sql);
    }    
    
    // Filter properties by Property ID
    public static void filterByPropertyID(JTable propertiesTable, String propertyID) {
        String sql = "SELECT * FROM properties WHERE property_id = ?";
        filterProperties(propertiesTable, sql, propertyID);
    }

    // Generic filtering method
    private static void filterProperties(JTable propertiesTable, String sql, String... params) {
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pst = con.prepareStatement(sql)) {

            // If there are parameters, set them in the query
            for (int i = 0; i < params.length; i++) {
                pst.setString(i + 1, params[i]);
            }

            ResultSet rs = pst.executeQuery();
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
            JOptionPane.showMessageDialog(null, "Error fetching data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

