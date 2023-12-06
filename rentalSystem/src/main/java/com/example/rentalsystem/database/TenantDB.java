package com.example.rentalsystem.database;

import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDate;
@Component
public class TenantDB {
    private static Connection conn;
    // Connect to the database
    public static void connect() {
        try{
            conn = DriverManager.getConnection("jdbc:sqlite:identifier.sqlite");
            System.out.println("Connection Success");
        }catch(Exception e){
            System.out.println("Connection Fail");
        }
    }

    // Close the connection to the database
    public static void close() throws SQLException {
        conn.close();
    }
    public static ResultSet GetMaxID() throws SQLException {
        try {
            String sql = "Select max(Id) from tenants";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            return pstmt.executeQuery();
        }catch (SQLException sqle){
            return null;
        }
    }

    // Add a tenant to the database
    public static void addTenant( String name, String phoneNum, String email, String checkIn, String checkOut, int aptNum) throws SQLException{

        String sql = "INSERT INTO tenants(name,phone_number,email,check_in,check_out,apt_num) VALUES (?, ?,?,?,?,?)";

        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, name);

        pstmt.setString(2, phoneNum);

        pstmt.setString(3,email);

        pstmt.setString(4, checkIn);
        pstmt.setString(5,checkOut);
        pstmt.setInt(6,aptNum);
        pstmt.executeUpdate();




    }
    public static void moveTenant(int id, int apt_num) throws SQLException{


            String sql = "UPDATE tenants SET apt_num=? WHERE Id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, apt_num);
            pstmt.setInt(2,id);
            pstmt.executeUpdate();


    }
    // Remove a tenant from the database
    public static void removeTenant(int id) throws SQLException {
        String sql = "DELETE FROM tenants WHERE Id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
    }
    public static ResultSet browseTenants() {
        try{
            String sql = "Select * from tenants";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            return pstmt.executeQuery();
        }catch (SQLException sqle){
            return null;
        }

    }
}
