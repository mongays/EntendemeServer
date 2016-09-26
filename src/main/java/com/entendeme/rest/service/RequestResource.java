/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entendeme.rest.service;

/**
 *
 * @author utunproyecto
 */

import com.entendeme.rest.domain.Request;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;


@Stateless
@Path("request")
public class RequestResource {

    @EJB
    RequestsResource requestsResource;
    private Request request;

    @GET
    @Produces({"application/json", "application/xml"})
    public Request leer() {
        return request;
    }

    @POST
    @Consumes({"application/xml", "application/json"})
    public Response guardar(Request p) {
        request = p;
        return Response.ok(p).build();
    }

   /* @DELETE
    public void borrar() {
        requestsResource.borrarRequest(request);
    }

    @PUT
    @Consumes({"application/xml", "application/json"})
    public void actualizar(Request p) {
        requestsResource.cambiarRequest(request,p);
    }*/

    public void setRequest(Request request) {
        this.request = request;
    }
}
