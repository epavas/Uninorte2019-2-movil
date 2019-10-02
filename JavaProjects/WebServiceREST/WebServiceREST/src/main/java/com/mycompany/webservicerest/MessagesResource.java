/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.webservicerest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mycompany.bd.MessagesDB;
import com.mycompany.bd.UserDB;
import com.server.objects.DateRange;
import com.server.objects.Message;
import com.server.objects.User;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * REST Web Service
 *
 * @author elvis
 */
@Path("messages")
@RequestScoped
public class MessagesResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of MessagesResource
     */
    public MessagesResource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMessages() throws ParseException{
        ArrayList<Message> response = MessagesDB.getMessages();
        String jsonR =  new Gson().toJson(response);
        return Response.ok(jsonR, MediaType.APPLICATION_JSON).build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response writeMessage(String body) throws ParseException{
        Message message = new Gson().fromJson(body, Message.class);
        if(MessagesDB.writeMessage(message)){
            User oldUser = UserDB.getUser(message.getSender());
            User user = UserDB.getUser(message.getSender());
            user.setLastSeen(message.getMessage_timestamp());
            if(UserDB.modifyUser(user.getUsername(), User.compare(oldUser, user))){
                return Response.ok("El mensaje fue enviado con exito!", MediaType.TEXT_PLAIN).build();
                
            }else{
                return Response.ok("Estamos teniendo problemas con la conexion , porfavor intente mas tarde", MediaType.TEXT_PLAIN).build();
            }
        }else{
            return Response.ok("The message has been successfully sent!", MediaType.TEXT_PLAIN).build();
           
        }
    }
    
    @POST
    @Path("/withinDate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMessagesWithinRange(String body) throws ParseException{
        
        DateRange dpr = new Gson().fromJson(body, DateRange.class);
        ArrayList<Message> response = MessagesDB.getMessagesWithinDate(dpr.getFirst_Date(), dpr.getLast_Date());
        return Response.ok(new Gson().toJson(response)).build();
        
    }
    
    @POST
    @Path("/{limite}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMessagesWithinDateLimited(@PathParam("limite") int limite, String body) throws ParseException, SQLException{
        DateRange dpr = new Gson().fromJson(body, DateRange.class);
        ArrayList<Message> response 
            = MessagesDB.getMessagesWithinDateLimited(dpr.getFirst_Date(), dpr.getLast_Date(), limite);
        return Response.ok(new Gson().toJson(response)).build();
        
    }
}
