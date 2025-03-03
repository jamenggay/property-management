package com.mycompany.vertexui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TransactionFilter {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/vertexdatabase";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Q@st0y2130g0702";

    public static void filterByTenantID(JTable transactionsTable, String tenantID) {
        String sql = "SELECT * FROM transactions WHERE tenant_id = ?";
        filterTransactions(transactionsTable, sql, tenantID);
    }

    public static void filterByTransactionDate(JTable transactionsTable, String transactionDate) {
        String sql = "SELECT * FROM transactions WHERE transaction_date = ?";
        filterTransactions(transactionsTable, sql, transactionDate);
    }

    public static void filterByPaymentType(JTable transactionsTable, String paymentType) {
        String sql = "SELECT * FROM transactions WHERE payment_method = ?";
        filterTransactions(transactionsTable, sql, paymentType);
    }

    public static void filterByTransactionStatus(JTable transactionsTable, String status) {
        String sql = "SELECT * FROM transactions WHERE payment_status = ?";
        filterTransactions(transactionsTable, sql, status);
    }

    public static void filterByReferenceNumber(JTable transactionsTable, String referenceNumber) {
        String sql = "SELECT * FROM transactions WHERE reference_number = ?";
        filterTransactions(transactionsTable, sql, referenceNumber);
    }

    private static void filterTransactions(JTable transactionsTable, String sql, String... params) {
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pst = con.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++) {
                pst.setString(i + 1, params[i]);
            }

            ResultSet rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) transactionsTable.getModel();
            model.setRowCount(0);

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
            JOptionPane.showMessageDialog(null, "Error fetching filtered data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
