/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bd;

import com.server.objects.CurrentLocation;
import com.server.objects.Location;
import com.server.objects.Message;
import com.server.objects.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author elvis
 */
public class UserDB {
    
    public static final ArrayList<User> getAllUsers() throws ParseException{
        ArrayList<User> queryResult = new ArrayList<>();
        String query = "SELECT * FROM user;";
        Connection dbConnection = ConexionBD.getConexion();
        try(
            Statement stmt = dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
        ){
            System.out.println("[API] Fetching results...");
            while (rs.next()) {
                queryResult.add(
                    new User(
                        rs.getString("username"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("lastLat"),
                        rs.getString("lastLon"),
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

    public static final User getUserWithPwd(String username, String password) 
    throws ParseException, Exception {
        User queryResult = new User();
        String query = "SELECT * FROM user WHERE username == ? ;";
        Connection dbConnection = ConexionBD.getConexion();
        try{
            PreparedStatement pstmt = dbConnection.prepareStatement(query);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("[API] Fetching results...");
            while (rs.next()) {
                if(password.length() > 3){
                    if(rs.getString("password").length() > 3){
                        if(password.equals(rs.getString("password"))){
                            queryResult.setUsername(rs.getString("username"));
                            queryResult.setFirst_name(rs.getString("first_name"));
                            queryResult.setLast_name(rs.getString("last_name"));
                            queryResult.setFull_name(rs.getString("full_name"));
                            queryResult.setEmail(rs.getString("email"));
                            queryResult.setLastLat(rs.getString("lastLat"));
                            queryResult.setLastLon(rs.getString("lastLon"));
                            queryResult.setStatus(rs.getString("status"));
                            queryResult.setLastSeen(rs.getString("lastSeen"));
                        }else{
                            throw new Exception("Wrong password");
                        }
                    }else{
                        throw new Exception("The stored password in the system is invalid.");
                    }
                }else{
                    throw new Exception("The submitted password is invalid.");
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
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
    
    public static final User getUser(String username) throws ParseException {
        User queryResult = new User();
        String query = "SELECT * FROM user WHERE username == ? ;";
        Connection dbConnection = ConexionBD.getConexion();
        try{
            PreparedStatement pstmt = dbConnection.prepareStatement(query);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("[API] Fetching results...");
            while (rs.next()) {
                queryResult.setUsername(rs.getString("username"));
                queryResult.setFirst_name(rs.getString("first_name"));
                queryResult.setLast_name(rs.getString("last_name"));
                queryResult.setFull_name(rs.getString("full_name"));
                queryResult.setEmail(rs.getString("email"));
                queryResult.setLastLat(rs.getString("lastLat"));
                queryResult.setLastLon(rs.getString("lastLon"));
                queryResult.setStatus(rs.getString("status"));
                queryResult.setLastSeen(rs.getString("lastSeen"));
            }
        }catch(SQLException e){
            e.printStackTrace();
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
    
    public static final User getUserWithPw(String username) throws ParseException {
        User queryResult = new User();
        String query = "SELECT * FROM user WHERE username == ? ;";
        Connection dbConnection = ConexionBD.getConexion();
        try{
            PreparedStatement pstmt = dbConnection.prepareStatement(query);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("[API] Fetching results...");
            while (rs.next()) {
                
                queryResult.setUsername(rs.getString("username"));
                queryResult.setFirst_name(rs.getString("first_name"));
                queryResult.setLast_name(rs.getString("last_name"));
                queryResult.setFull_name(rs.getString("full_name"));
                queryResult.setEmail(rs.getString("email"));
                queryResult.setLastLat(rs.getString("lastLat"));
                queryResult.setLastLon(rs.getString("lastLon"));
                queryResult.setStatus(rs.getString("status"));
                queryResult.setLastSeen(rs.getString("lastSeen"));
                queryResult.setPassword(rs.getString("password"));
            }
        }catch(SQLException e){
            e.printStackTrace();
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
    
    public static final boolean writeUser(User newUser){
        boolean success = true;
        String query 
            = "INSERT INTO user(" +
                "username, " +
                "first_name, " +
                "last_name, " +
                "full_name, " +
                "email, " +
                "lastLat, " +
                "lastLon, " +
                "status, " +
                "lastSeen, " +
                "password" +
            ") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        Connection dbConnection = ConexionBD.getConexion();
        try{
            PreparedStatement pstmt = dbConnection.prepareStatement(query);
            pstmt.setString(1, newUser.getUsername());
            pstmt.setString(2, newUser.getFirst_name());
            pstmt.setString(3, newUser.getLast_name());
            pstmt.setString(4, newUser.getFull_name());
            pstmt.setString(5, newUser.getEmail());
            pstmt.setString(6, newUser.getLastLatString());
            pstmt.setString(7, newUser.getLastLonString());
            pstmt.setString(8, newUser.getStatus());
            pstmt.setString(9, newUser.getLastSeenISOFormatted());
            pstmt.setString(10, newUser.getPassword());
            System.out.println("[API] Fetching updates...");
            pstmt.executeUpdate();
            System.out.println("[API] Updates fetched!");
        }catch(SQLException e){
            success = false;
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
        
        return success;
    }

    public static final boolean modifyUser(String username, HashMap<String, String> changes){
        boolean success = true;
        boolean once = true;
        int index = 1;
        String query = "UPDATE user SET ";
        Set set = changes.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) {
            Map.Entry currentEntry = (Map.Entry)iterator.next();
            if(once){
                once = false;
                query += currentEntry.getKey().toString() +" = ?";
            }else{
                query += ", " +currentEntry.getKey().toString() +" = ?";
            }
        }
        query += " WHERE username = ?;";
        Connection dbConnection = ConexionBD.getConexion();
        try{
            PreparedStatement pstmt = dbConnection.prepareStatement(query);
            Set set2 = changes.entrySet();
            Iterator iterator2 = set2.iterator();
            while(iterator2.hasNext()) {
                Map.Entry currentEntry = (Map.Entry)iterator2.next();
                pstmt.setString(index, currentEntry.getValue().toString());
                index++;
            }
            pstmt.setString(index, username);
            System.out.println("[API] Fetching updates...");
            pstmt.executeUpdate();
            System.out.println("[API] Updates fetched!");
        }catch(SQLException e){
            success = false;
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
        
        return success;
    }
    
    public static final boolean delUser(String username){
        boolean success = true;
        String query = "delete FROM  user WHERE username == ? ;";
        Connection dbConnection = ConexionBD.getConexion();
        try{
            PreparedStatement pstmt = dbConnection.prepareStatement(query);
            pstmt.setString(1, username);
            System.out.println("[API] Fetching updates...");
            pstmt.executeUpdate();
            System.out.println("[API] Updates fetched!");
        }catch(SQLException e){
            success = false;
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
        
        return success;
    }

    public static final ArrayList<CurrentLocation> getCurrentLocations() throws ParseException{
        ArrayList<CurrentLocation> queryResult = new ArrayList<>();
        String query = "SELECT * FROM v_current_locations;";
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
                        rs.getString("lastLat"),
                        rs.getString("lastLon"),
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

    public static final boolean writeLocation(Location newLocation){
        boolean success = true;
        String query 
            = "INSERT INTO locations(" +
                "lat, " +
                "lon, " +
                "location_timestamp, " +
                "username" +
            ") VALUES(?, ?, ?, ?);";
        Connection dbConnection = ConexionBD.getConexion();
        try{
            PreparedStatement pstmt = dbConnection.prepareStatement(query);
            pstmt.setString(1, newLocation.getLatString());
            pstmt.setString(2, newLocation.getLonString());
            pstmt.setString(3, newLocation.getLocation_timestampISOFormatted());
            pstmt.setString(4, newLocation.getUsername());
            System.out.println("[API] Fetching updates...");
            pstmt.executeUpdate();
            System.out.println("[API] Updates fetched!");
        }catch(SQLException e){
            success = false;
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
        
        return success;
    }
    
    public static final ArrayList<Message> getMessages() throws ParseException{
        ArrayList<Message> queryResult = new ArrayList<>();
        String query = "SELECT * FROM messages;";
        Connection dbConnection = ConexionBD.getConexion();
        try{
            PreparedStatement pstmt = dbConnection.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("[API] Fetching results...");
            while (rs.next()) {
                queryResult.add(
                    new Message(
                        rs.getString("body"),
                        rs.getString("message_timestamp"),
                        rs.getString("sender")
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
    
    public static final boolean writeMessage(Message newMessage){
        boolean success = true;
        String query 
            = "INSERT INTO messages(" +
                "body, " +
                "message_timestamp, " +
                "sender" +
            ") VALUES(?, ?, ?);";
        Connection dbConnection = null;
        try{
            dbConnection = ConexionBD.getConexion();
        
            PreparedStatement pstmt = dbConnection.prepareStatement(query);
            pstmt.setString(1, newMessage.getBody());
            pstmt.setString(2, newMessage.getMessage_timestampISOFormatted());
            pstmt.setString(3, newMessage.getSender());
            System.out.println("[API] Fetching updates...");
            pstmt.executeUpdate();
            System.out.println("[API] Updates fetched!");
        }catch(SQLException e){
            success = false;
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
        
        return success;
    }

    public static final ArrayList<Message> getMessagesWithinDate(
        String initialDate, String lastDate
    ) throws ParseException {
        ArrayList<Message> queryResult = new ArrayList<>();
        String query 
            = "SELECT * FROM messages WHERE message_timestamp between ? AND ? ;";
        
        Connection dbConnection = null;
        try{
        dbConnection = ConexionBD.getConexion();
        
            PreparedStatement pstmt = dbConnection.prepareStatement(query);
            pstmt.setString(1, initialDate);
            pstmt.setString(2, lastDate);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("[API] Fetching results...");
            while (rs.next()) {
                queryResult.add(
                    new Message(
                        rs.getString("body"),
                        rs.getString("message_timestamp"),
                        rs.getString("sender")
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
    
    public static final ArrayList<Message> getMessagesWithinDateLimited(
        String initialDate, String lastDate
    ) throws ParseException{
        ArrayList<Message> queryResult = new ArrayList<>();
        String query 
            = "SELECT * FROM messages WHERE message_timestamp between ? AND ? LIMIT ?;";
        
        Connection dbConnection = null;
        try{
            dbConnection = ConexionBD.getConexion();
            PreparedStatement pstmt = dbConnection.prepareStatement(query);
            pstmt.setString(1, initialDate);
            pstmt.setString(2, lastDate);
            pstmt.setInt(3, 100);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("[API] Fetching results...");
            while (rs.next()) {
                queryResult.add(
                    new Message(
                        rs.getString("body"),
                        rs.getString("message_timestamp"),
                        rs.getString("sender")
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
    
}
