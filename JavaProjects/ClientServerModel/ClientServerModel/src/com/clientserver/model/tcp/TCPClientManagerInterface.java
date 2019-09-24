/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clientserver.model.tcp;

/**
 *
 * @author asaad
 */
public interface TCPClientManagerInterface {
    public void MessageReceived(String message);
    public void ErrorReceived(Exception error);
    
}
