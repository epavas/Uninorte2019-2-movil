/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clientserver.model.tcp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author Administrador
 */
public class TCPClientManager extends Thread{
    
    Socket clientSocket;
    
    BufferedReader reader;
    PrintWriter writer;
    
    public boolean isListening=true;
    private String ipAddress;
    private int port;
    
    TCPClientManagerInterface caller;
    
    
    public TCPClientManager(Socket clientSocketParameter,
            TCPClientManagerInterface caller){
        this.clientSocket=clientSocketParameter;
        this.caller=caller;
        this.start();
    }
    
    public TCPClientManager(String ipAddress,int port,
            TCPClientManagerInterface caller){
        this.ipAddress=ipAddress;
        this.port=port;
        this.clientSocket=null;
        this.caller=caller;
        this.start();
    }
    
    public boolean InitializeClientSocket(String ipAddress,int port){
        try{
            this.clientSocket=new Socket(
                    InetAddress.getByName(ipAddress),port);
            return true;
        }catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return false;
    }
    
    public void ExtractStreams(){
        try{
            reader=new 
                BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
            writer=new 
                PrintWriter(
                        new OutputStreamWriter(
                                this.clientSocket.
                                        getOutputStream()),true);
        }catch(Exception error){
            System.err.println(error.getMessage());
        }
    }

    @Override
    public void run() {
        if(this.clientSocket==null){
            if(!InitializeClientSocket(ipAddress, port))
                return;
        }
        ExtractStreams();
        ReadingProcess();
    }
    
    public void ReadingProcess(){
        try{
            String incomingMessage=null;
            while(this.isListening){
                while((incomingMessage=
                        this.reader.readLine())!=null){
                    System.out.println(this.clientSocket.
                            getInetAddress().getHostName()+
                            ": "+ incomingMessage);
                    caller.MessageReceived(incomingMessage);
                }
            }
        }catch(Exception error){
            
        }
    }
    
    public void SendThisMessage(String message){
        try{
            if(this.writer!=null){
                if(this.clientSocket.isConnected()){
                    this.writer.write(message+"\n");
                    this.writer.flush();
                }
            }
        }catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    
    
}
