/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.vertexproperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Jamaine
 */
public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/vertex_property";
    private static final String USER = "root";
    private static final String PASSWORD = "TuazonJamenggay_04"; 

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("❌ Connection failed: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
}
