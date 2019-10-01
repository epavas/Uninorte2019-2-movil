/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.webservicerest;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.mycompany.bd.UserDB;
import com.server.objects.User;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jboss.logging.Param;
import sun.jvm.hotspot.utilities.soql.SOQLException;

/**
 * REST Web Service
 *
 * @author elvis
 */
@Path("users")
@RequestScoped
public class UsersResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of UsersResource
     */
    public UsersResource() {
    }

    /**
     * Retrieves representation of an instance of
     * com.mycompany.webservicerest.UsersResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("prueba")
    @Produces(MediaType.TEXT_PLAIN)
    public String getXml() {
        //TODO return proper representation object
        return "funciona";
    }

    @GET
    @Path("pw")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUserWithPW() {
        try {
            System.out.println("obteniendo:::...");
            List<User> users =  UserDB.getAllUsersWithPW();
            
            String json = new Gson().toJson(users);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.SEE_OTHER)
                    .entity("Error al consultar los datos"+ex.getMessage())
                    .build();
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUser() {
        try {
            List<User> users = UserDB.getAllUsers();
            String json = new Gson().toJson(users);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.SEE_OTHER)
                    .entity("Error al consultar los datos"+ex.getMessage())
                    .build();
        }
    }
    
    
    
    @GET
    @Path("{username}/{password}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserWithPwd(@PathParam("username") String username, @PathParam("password") String pwd) {
        try {
            User user = UserDB.getUserWithPwd(username, pwd);
            String json = new Gson().toJson(user);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.SEE_OTHER)
                    .entity("Error al consultar los datos"+ex.getMessage())
                    .build();
        }
    }

    @GET
    @Path("{username}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("username") String username) {
        try {
            User user = UserDB.getUser(username);
            String json = new Gson().toJson(user);
            if(user.getUsername()== null){
                return Response.status(Response.Status.SEE_OTHER)
                    .entity("Error: No existe usuario")
                    .build();
            }
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.SEE_OTHER)
                    .entity("Error al consultar los datos")
                    .build();
        }
    }
    
    @GET
    @Path("pw/{username}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserWithPw(@PathParam("username") String username) {
        try {
            User user = UserDB.getUserWithPw(username);
            String json = new Gson().toJson(user);
            if(user.getUsername()== null){
                return Response.status(Response.Status.SEE_OTHER)
                    .entity("Error: No existe usuario")
                    .build();
            }
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.SEE_OTHER)
                    .entity("Error al consultar los datos")
                    .build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(String userRegistro) {
        String s = "asdas";
        System.out.println("ad" + s);
        User registro = new Gson().fromJson(userRegistro, User.class);
        
        
        try{
            
            UserDB.writeUser(registro);

            System.out.println("::: Se ha añadido - addUser"+registro.getUsername());
            String json ="{ \"username\": \"" + String.valueOf(registro.getUsername()) + "\" }";
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        }catch(Exception er){
            return Response.status(Response.Status.SEE_OTHER)
                    .entity("No se pudo insertar Registo: "+ registro.getUsername())
                    .build();
        }
    }

    @PUT
    @Path("{username}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("username") String username, String userRegistro) {
        System.out.println("Llego una actualizacion");
        
        //User registro = new Gson().fromJson(userRegistro, User.class);
        User oldOne = null;
        User newOne = null;
        try {
            oldOne = UserDB.getUser(username);
            newOne = new Gson().fromJson(userRegistro, User.class);
            HashMap<String, String> changes = User.compare(oldOne, newOne);
            if(changes.size() > 0){
                if(UserDB.modifyUser(username, changes)){

                    return Response.status(Response.Status.OK)
                        .entity("Los cambios han sido aplicados: " + username)
                        .build();
                }else{
                    return Response.status(Response.Status.SEE_OTHER)
                        .entity("Parece ser un problema de conexión, por favor intente nuevamente más tarde: " + username)
                        .build();   
                }
            }else{
                return Response.status(Response.Status.SEE_OTHER)
                        .entity("No hay cambios a: " + username + "")
                        .build();
            }
        } catch (Exception er) {
            return Response.status(Response.Status.SEE_OTHER)
                    .entity("No se pudo actualizar Registo: " + username)
                    .build();
        }
    }
    
    @PUT
    @Path("pw/{username}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUserWhithPw(@PathParam("username") String username, String userRegistro) {
        System.out.println("Llego una actualizacion");
        
        
        User oldOne = null;
        User newOne = null;
        try {
            oldOne = UserDB.getUserWithPw(username);
            newOne = new Gson().fromJson(userRegistro, User.class);
            HashMap<String, String> changes = User.compareWhithPw(oldOne, newOne);
            if(changes.size() > 0){
                if(UserDB.modifyUser(username, changes)){

                    return Response.status(Response.Status.OK)
                        .entity("Los cambios han sido aplicados: " + username)
                        .build();
                }else{
                    return Response.status(Response.Status.SEE_OTHER)
                        .entity("Parece ser un problema de conexión, por favor intente nuevamente más tarde: " + username)
                        .build();   
                }
            }else{
                
                return Response.status(Response.Status.OK)
                        .entity("No hay cambios a: " + username + "")
                        .build();
            }
        } catch (Exception er) {
            return Response.status(Response.Status.SEE_OTHER)
                    .entity("No se pudo actualizar Registo: " + username)
                    .build();
        }
    }

    @DELETE
    @Path("{username}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response delUser(@PathParam("username") String username) {
        System.out.println("Eliminando...");
        try {
            String s="";
            UserDB.delUser(username);

            System.out.println("::: Se ha borrado - addUser" + username);
            String json = "{ \"username\": \"" + String.valueOf(username) + "\" }";
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (Exception er) {
            return Response.status(Response.Status.SEE_OTHER)
                    .entity("No se pudo eliminar Registo: " + username)
                    .build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {

    }

}
