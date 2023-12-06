package com.example.rentalsystem.database;

import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class RequestDB {
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
            String sql = "Select max(Id) from requests";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            return pstmt.executeQuery();
        }catch (SQLException sqle){
            return null;
        }
    }
    // Add a request to the database
    public static void addRequest( int apt_num, String area, String description, Timestamp date_time, String image) {
        try {

            String sql = "INSERT INTO requests(apt_num,area,description,date_time,image,status) VALUES ( ?,?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, apt_num);
            pstmt.setString(2, area);
            pstmt.setString(3,description);
            pstmt.setString(4, date_time.toString());
            pstmt.setString(5,image);
            pstmt.setBoolean(6,false);
            pstmt.executeUpdate();

        }catch (SQLException sqle){
            throw new IllegalStateException("Request was not added");

        }
    }
    public static void completeRequest(int id) {
        try {

            String sql = "UPDATE requests SET status=? WHERE Id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setBoolean(1, true);
            pstmt.setInt(2,id);
            pstmt.executeUpdate();

        }catch (SQLException sqle){
            System.err.println("request was not moved");
        }
    }
    public static ResultSet browseRequests() {
        try{
            String sql = "Select * from requests";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            return pstmt.executeQuery();
        }catch (SQLException sqle){
            return null;
        }

    }

}
