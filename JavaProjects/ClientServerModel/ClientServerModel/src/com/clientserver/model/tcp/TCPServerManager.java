/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clientserver.model.tcp;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

/**
 *
 * @author Administrador
 */
public class TCPServerManager extends Thread implements TCPClientManagerInterface{
    
    ServerSocket serverSocket;
    int serverSocketPort;
    boolean IsAcceptingConnections=true;
    public Vector<TCPClientManager> childs=new Vector<>();
    
    public TCPServerManager(int portParameter){
        this.serverSocketPort=portParameter;
        this.start();
    }
    
    public boolean InitServerSocket(){
        try{
            this.serverSocket=
                    new ServerSocket(this.serverSocketPort);
            return true;
        }catch(Exception error){
            return false;
        }
    }
    
    public void AcceptingConnectionProcess(){
        try{
            while(IsAcceptingConnections){
                Socket incomingConnection= 
                        this.serverSocket.accept();
                childs.add(new TCPClientManager(incomingConnection,this));
            }
            
        }catch(Exception error){
            
        }
    }
    
    @Override
    public void run(){
        try{
            if(this.InitServerSocket()){
                this.AcceptingConnectionProcess();
            }
        }catch(Exception error){
            
        }
    }

    @Override
    public void MessageReceived(String message) {
        for(TCPClientManager current:this.childs){
            if(current!=null){
                current.SendThisMessage(message);
            }
        }
    }

    @Override
    public void ErrorReceived(Exception error) {
        
    }
    
}
