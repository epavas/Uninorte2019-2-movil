/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bd;

import com.server.objects.CurrentLocation;
import com.server.objects.Location;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;

/**
 *
 * @author elvis
 */
public class LocationDB {
    public static final ArrayList<CurrentLocation> getCurrentLocations() throws ParseException{
        ArrayList<CurrentLocation> queryResult = new ArrayList<>();
        String query = "SELECT * FROM cuerrent_location";
        Connection dbConnection = ConexionBD.getConexion();
        try(
            Statement stmt = dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
        ){
            System.out.println("[API] Fetching results...");
            while (rs.next()) {
                queryResult.add(
                    new CurrentLocation(
                        rs.getString("username"),
                        rs.getString("full_name"),
                        rs.getString("lat"),
                        rs.getString("lon"),
                        rs.getString("status"),
                        rs.getString("lastSeen")
                    )
                );
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try {
                if (dbConnection != null) {
                    System.out.println("[API] Closing connection...");
                    dbConnection.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return queryResult;
    }

    public static final ArrayList<Location> getUserLocations(String username) throws ParseException{
        ArrayList<Location> queryResult = new ArrayList<>();
        String query = "SELECT * FROM locations WHERE username = ?;";
        Connection dbConnection = ConexionBD.getConexion();
        try{
            PreparedStatement pstmt = dbConnection.prepareStatement(query);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("[API] Fetching results...");
            while (rs.next()) {
                queryResult.add(
                    new Location(
                        rs.getString("lat"),
                        rs.getString("lon"),
                        rs.getString("location_timestamp"),
                        rs.getString("username")
                    )
                );
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try {
                if (dbConnection != null) {
                    System.out.println("[API] Closing connection...");
                    dbConnection.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return queryResult;
    }

    public static final ArrayList<Location> getUserLocationsWithinDate(
        String username, String initialDate, String lastDate
    ) throws ParseException {
        ArrayList<Location> queryResult = new ArrayList<>();
        String query 
            = "SELECT * FROM locations WHERE username = ? AND (location_timestamp between ? AND ?);";
        
        Connection dbConnection = ConexionBD.getConexion();
        try{
            PreparedStatement pstmt = dbConnection.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, initialDate);
            pstmt.setString(3, lastDate);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("[API] Fetching results...");
            while (rs.next()) {
                queryResult.add(
                    new Location(
                        rs.getString("lat"),
                        rs.getString("lon"),
                        rs.getString("location_timestamp"),
                        rs.getString("username")
                    )
                );
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try {
                if (dbConnection != null) {
                    System.out.println("[API] Closing connection...");
                    dbConnection.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return queryResult;
    }

    public static final boolean writeLocation(Location newLocation) throws SQLException{
        boolean success = true;
        String query 
                ="INSERT INTO location("+                
                "lat, " +
                "lon, " +
                "lastSeen, " +
                "username" +                
            ") VALUES('"
                + newLocation.getLatString() + "', '"
                + newLocation.getLonString() + "', '"
                + newLocation.getLastSeen() + "', '"
                + newLocation.getUsername() + "');";
        Connection dbConnection = ConexionBD.getConexion();
        try{
            PreparedStatement pstmt = dbConnection.prepareStatement(query);
            /*pstmt.setString(1, newLocation.getLatString());
            pstmt.setString(2, newLocation.getLonString());
            pstmt.setString(3, newLocation.getLastSeenISOFormatted());
            pstmt.setString(4, newLocation.getUsername());*/
            System.out.println("[API] Fetching updates...");
            
            pstmt.executeUpdate();
            
            System.out.println("[API] Updates fetched!");
        }catch(SQLException e){
            
            success = false;
            System.out.println("Error:" +e.getMessage());
        }finally{
            if (dbConnection != null) {
                System.out.println("[API] Closing connection...");
                dbConnection.close();
            }
        }
        
        return success;
    }
}
