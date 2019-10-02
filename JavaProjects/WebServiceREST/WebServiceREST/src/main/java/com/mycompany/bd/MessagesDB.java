/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bd;

import com.server.objects.Message;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

/**
 *
 * @author elvis
 */
public class MessagesDB {
    
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
        Connection dbConnection = ConexionBD.getConexion();
        try{
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
        
        Connection dbConnection = ConexionBD.getConexion();
        try{
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
        String initialDate, String lastDate, int limite
    ) throws ParseException, SQLException{
        ArrayList<Message> queryResult = new ArrayList<>();
        String query 
            = "SELECT * FROM messages WHERE message_timestamp between ? AND ? LIMIT ?;";
        
        Connection dbConnection = ConexionBD.getConexion();
        try{
            
            PreparedStatement pstmt = dbConnection.prepareStatement(query);
            pstmt.setString(1, initialDate);
            pstmt.setString(2, lastDate);
            pstmt.setInt(3, limite);
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
            if (dbConnection != null) {
                System.out.println("[API] Closing connection...");
                dbConnection.close();
            }
        }
        return queryResult;
    }
}
