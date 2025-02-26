/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.vertexproperties;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jamaine
 */
public class DataAccess {
    
    
    public static DefaultTableModel getProperties() {
        DefaultTableModel propertiesModel = new DefaultTableModel();
        propertiesModel.addColumn("Property ID");
        propertiesModel.addColumn("Property Name");
        propertiesModel.addColumn("Type");
        propertiesModel.addColumn("House Number");
        propertiesModel.addColumn("Street");
        propertiesModel.addColumn("Municipality");
        propertiesModel.addColumn("Province");
        propertiesModel.addColumn("Country");
        propertiesModel.addColumn("Availability");

        try (Connection con = DatabaseConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM properties")) {

            while (rs.next()) {
                propertiesModel.addRow(new Object[]{
                    rs.getInt("property_id"),
                    rs.getString("property_name"),
                    rs.getString("type"),
                    rs.getString("house_number"),
                    rs.getString("street"),
                    rs.getString("municipality"),
                    rs.getString("province"),
                    rs.getString("country"),
                    rs.getString("availability")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return propertiesModel;
    }
    
    public static DefaultTableModel getTenants() {
        DefaultTableModel tenantsModel = new DefaultTableModel();
        tenantsModel.addColumn("Tenant ID");
        tenantsModel.addColumn("Tenant Name");
        tenantsModel.addColumn("Contact Number");
        tenantsModel.addColumn("Email");
        tenantsModel.addColumn("Property ID");
 

        try (Connection con = DatabaseConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM tenants")) {

            while (rs.next()) {
                tenantsModel.addRow(new Object[]{
                    rs.getInt("tenant_id"),
                    rs.getString("name"),
                    rs.getString("contact_number"),
                    rs.getString("email"),
                    rs.getString("property_id")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tenantsModel;
    }
    
    public static DefaultTableModel getLeases() {
        DefaultTableModel leasesModel = new DefaultTableModel();
        leasesModel.addColumn("Lease ID");
        leasesModel.addColumn("Property ID");
        leasesModel.addColumn("Tenant ID");
        leasesModel.addColumn("Lease Start");   
        leasesModel.addColumn("Lease End");   
        leasesModel.addColumn("Lease Status");   
        leasesModel.addColumn("Payment Frequency");   
        leasesModel.addColumn("Security Deposit");   
        leasesModel.addColumn("Rent Amount");   
     

        try (Connection con = DatabaseConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM leases")) {

            while (rs.next()) {
                leasesModel.addRow(new Object[]{
                    rs.getInt("lease_id"),
                    rs.getString("property_id"),
                    rs.getString("tenant_id"),
                    rs.getString("lease_start"),
                    rs.getString("lease_end"),
                    rs.getString("lease_status"),
                    rs.getString("payment_frequency"),
                    rs.getString("security_deposit"),
                    rs.getString("rent_amount")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return leasesModel;
    }
    
     public static DefaultTableModel getMaintenancereq() {
        DefaultTableModel maintenanceModel = new DefaultTableModel();
        maintenanceModel.addColumn("Request ID");
        maintenanceModel.addColumn("Tenant ID");
        maintenanceModel.addColumn("Property ID");
        maintenanceModel.addColumn("Issue Type");
        maintenanceModel.addColumn("Description");
        maintenanceModel.addColumn("Priority");
        maintenanceModel.addColumn("Status");
        
        
     

        try (Connection con = DatabaseConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM maintenancerequest")) {

            while (rs.next()) {
                maintenanceModel.addRow(new Object[]{
                    rs.getInt("request_id"),
                    rs.getString("tenant_id"),
                    rs.getString("property_id"),
                    rs.getString("issue_type"),
                    rs.getString("description"),
                    rs.getString("priority"),
                    rs.getString("status")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  maintenanceModel;
    }
     
      public static DefaultTableModel getTransactions() {
        DefaultTableModel transactionsModel = new DefaultTableModel();
        transactionsModel.addColumn("Transaction ID");
        transactionsModel.addColumn("Tenant ID");
        transactionsModel.addColumn("Property ID");
        transactionsModel.addColumn("Lease ID");
        transactionsModel.addColumn("M.Request ID");
        transactionsModel.addColumn("Transaction Type");
        transactionsModel.addColumn("Amount");
        transactionsModel.addColumn("Payment Method");
        transactionsModel.addColumn("Transaction Date/Time");
        transactionsModel.addColumn("Payment Status");
        transactionsModel.addColumn("Reference No");
        

        try (Connection con = DatabaseConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM transactions")) {

            while (rs.next()) {
                transactionsModel.addRow(new Object[]{
                    rs.getInt("transaction_id"),
                    rs.getString("tenant_id"),
                    rs.getString("property_id"),
                    rs.getString("lease_id"),
                    rs.getString("m_request_id"),
                    rs.getString("transaction_type"),
                    rs.getString("amount"),
                    rs.getString("payment_method"),
                    rs.getString("transaction_date"),
                    rs.getString("payment_status"),
                    rs.getString("reference_number")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactionsModel;
    }
}
    

