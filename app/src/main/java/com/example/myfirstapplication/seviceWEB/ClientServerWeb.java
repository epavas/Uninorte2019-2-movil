package com.example.myfirstapplication.seviceWEB;

public interface ClientServerWeb {
    void MessageReceived(
            String channel, String type,String message);

    void ErrorAtClientServerWeb(Exception error);
}
