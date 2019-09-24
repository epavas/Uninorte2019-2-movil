/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.webservicerest;

import com.google.gson.Gson;
import com.server.objects.ServerQuery;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author asaad
 */
@Path("server")
@RequestScoped
public class ServerResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ServerResource
     */
    public ServerResource() {
    }

    /**
     * Retrieves representation of an instance of com.mycompany.webservicerest.ServerResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        return "{message:'Hi new programmer, add this to your LinkedIn skills'}";
    }

    /**
     * PUT method for updating or creating an instance of ServerResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
        
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public String executeAtServerSide(String serverQuery) {
        String response="";
        try{
            Gson gson=new Gson();
            ServerQuery inputObject=(ServerQuery)gson.fromJson(serverQuery, ServerQuery.class);
            String databaseResponse=executeQueryAtDatabase(inputObject.getQuery());
            inputObject.setResponse(databaseResponse);
            response=gson.toJson(inputObject);
            
        }catch (Exception ex) {
            
        }
        return response;
    }

    private String executeQueryAtDatabase(String query) {
        return "{['a','b']}";
    }
}
