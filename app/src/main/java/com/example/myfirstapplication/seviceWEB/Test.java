package com.example.myfirstapplication.seviceWEB;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Test {



    public String enviarDatosGET(String ip){
        URL url= null;
        String linea = "";
        int respuesta = 0;
        StringBuilder resul =  null;
        String endpoint="http://"+ip+":8080/WebServiceREST/resources/server";
        String methodType="GET";
        String action="";

        try{
            url=new URL(endpoint+"/"+action);
            HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();
            respuesta = urlConnection.getResponseCode();

            resul = new StringBuilder();

            if (respuesta == HttpURLConnection.HTTP_OK){
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                while ((linea=reader.readLine())!=null){
                    resul.append(linea);
                }
            }

        }catch(Exception error) {
            System.out.println(error.getMessage());
        }
        return  resul.toString();

    }

    public int obtDatosJSON(String response){
        int res=0;
        try{
            JSONArray json = new JSONArray(response);
            res = json.length();
        }catch(Exception er){
            System.out.println(er.getMessage());
        }
        return res;
    }





}
