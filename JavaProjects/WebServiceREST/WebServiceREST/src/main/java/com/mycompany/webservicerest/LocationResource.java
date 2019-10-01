/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.webservicerest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mycompany.bd.LocationDB;
import com.mycompany.bd.UserDB;
import com.server.objects.CurrentLocation;
import com.server.objects.DateRange;
import com.server.objects.Location;
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
@Path("location")
@RequestScoped
public class LocationResource {
    private final Gson gson;
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of LocationResource
     */
 

    public LocationResource() {
        this.gson = new GsonBuilder()
            .setPrettyPrinting()
            .setDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
            .create();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLocations() throws ParseException{
        ArrayList<CurrentLocation> response = LocationDB.getCurrentLocations();
        return Response.ok(gson.toJson(response),MediaType.TEXT_PLAIN).build();
        
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response writeLocation(String body)
        throws ParseException, SQLException{
        Location location = gson.fromJson(body, Location.class);
        if(LocationDB.writeLocation(location)){
            System.out.println("[API] Location written!");
            User oldUser = UserDB.getUser(location.getUsername());
            User user = UserDB.getUser(location.getUsername());
            user.setLastLat(location.getLat());
            user.setLastLon(location.getLon());
            user.setLastSeen(location.getLastSeen());
            if(UserDB.modifyUser(user.getUsername(), User.compare(oldUser, user))){
                
                return Response.ok("Ubicacion actualizada con exito ",MediaType.TEXT_PLAIN).build();
            }else{
                return Response.ok("Parece ser un problema de conexión, por favor intente nuevamente más tarde.",MediaType.TEXT_PLAIN).build();
                
                
            }
        }else{
            
            return Response.ok("Parece ser un problema de conexión, por favor intente nuevamente más tarde.",MediaType.TEXT_PLAIN).build();
        }
    }
    
    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserLocations(@PathParam("username") String username) throws ParseException{
        ArrayList<Location> response = LocationDB.getUserLocations(username);
        return Response.ok(gson.toJson(response),MediaType.TEXT_PLAIN).build();
    }
    
    @POST
    @Path("/{username}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserLocationsWithinDate(@PathParam("username") String username, String body)
        throws ParseException{
        DateRange dpr = gson.fromJson(body, DateRange.class);
        ArrayList<Location> response 
            = LocationDB.getUserLocationsWithinDate(username, dpr.getFirst_Date(), dpr.getLast_Date());
        return Response.ok(gson.toJson(response),MediaType.TEXT_PLAIN).build();
    }
}
