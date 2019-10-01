/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author elvis
 */
public class ConexionBD {
    static final String url = "C:\\Users\\elvis\\StudioProjects\\Uninorte2019-2\\JavaProjects\\WebServiceREST\\WebServiceREST\\src\\";
    Connection conexion;
    Connection connection = null;
    Statement statement = null;
    DefaultTableModel modelo=new DefaultTableModel();
    
    public static Connection getConexion(){
        Connection conn = null;
        
        String path = "jdbc:sqlite:"+url+"db.db";
        try {
            
            Class.forName("org.sqlite.JDBC");
            
            conn = DriverManager.getConnection(path);
            
        }catch(Exception error){
            System.out.println("Error"+error.getMessage());
        }
        
        return conn;
    }
    
    
    public void cerrarConexion(){
        
    }
    
}
