package com.mycompany.vertexui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class TransactionSorting {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/vertexdatabase";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Q@st0y2130g0702";

    // Sort Transactions Table
    public static void sortTransactions(JTable transactionsTable, String sortBy) {
        String sql = "";

        switch (sortBy) {
            case "A-Z":
                sql = "SELECT * FROM transactionsTable ORDER BY transaction_type ASC";
                break;
            case "Z-A":
                sql = "SELECT * FROM transactionsTable ORDER BY transaction_type DESC";
                break;
            case "Newest - Oldest":
                sql = "SELECT * FROM transactionsTable ORDER BY transaction_date DESC";
                break;
            case "Oldest - Newest":
                sql = "SELECT * FROM transactionsTable ORDER BY transaction_date ASC";
                break;
            case "Highest - Lowest": // Sorting by amount
                sql = "SELECT * FROM transactionsTable ORDER BY amount DESC";
                break;
            case "Lowest - Highest": // Sorting by amount
                sql = "SELECT * FROM transactionsTable ORDER BY amount ASC";
                break;
            default:
                JOptionPane.showMessageDialog(null, "Invalid sort option!", "Sorting Error", JOptionPane.WARNING_MESSAGE);
                return;
        }

        applySorting(transactionsTable, sql);
    }

    private static void applySorting(JTable transactionsTable, String sql) {
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            DefaultTableModel model = (DefaultTableModel) transactionsTable.getModel();
            model.setRowCount(0); // Clear the table before adding new rows

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("transaction_id"),
                    rs.getInt("tenant_id"),
                    rs.getInt("property_id"),
                    rs.getInt("lease_id"),
                    rs.getInt("m_request_id"),
                    rs.getString("transaction_type"),
                    rs.getDouble("amount"),
                    rs.getString("payment_method"),
                    rs.getDate("transaction_date"),
                    rs.getString("payment_status"),
                    rs.getInt("reference_number")
                };
                model.addRow(row);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching sorted data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
