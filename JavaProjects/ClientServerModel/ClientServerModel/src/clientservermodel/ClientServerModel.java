/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientservermodel;

import com.clientserver.model.tcp.TCPServerManager;

/**
 *
 * @author Administrador
 */
public class ClientServerModel {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TCPServerManager tCPServerManager=
                new TCPServerManager(9090);
    }
    
}
