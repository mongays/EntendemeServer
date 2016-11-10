/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entendeme.rest.service;

import com.entendeme.rest.domain.Request;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.MediaType;


@Stateless
@Path("/listaRequest")
public class RequestsResource {

    final String URL = "http://52.43.54.198:8080/entendeme/listaRequest/";

    @EJB
    RequestResource requestResource;
    static List<Request> requests = new ArrayList<Request>();

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response guardar(Request p) {
        p.setIdRequest(Request.getNuevoId(p.getNombreUsuario()));
        p.setRutaImagen(URL+p.getIdRequest());
        requests.add(p);
        return Response.ok(p).build();
    }

    @GET
    @Produces("application/json")
    public List<Request> buscar(@QueryParam("nombre") @DefaultValue("") String nombre) {
        List<Request> lista = new ArrayList<Request>();
        for (Request persona : requests) {
            if (persona.getNombreUsuario().indexOf(nombre) >= 0) {
                lista.add(persona);
            }

        }
        return lista;
    }

    @Path("{idRequest}/")
    @GET
    @Produces( "application/json")
    public Request getRequest(@PathParam("idRequest") String id) {
        if (id == null) { //si no se pasó el ID...
            return null; //... termina el método
        }
        Request $temp = new Request(); //creamos un temporal...
        $temp.setIdRequest(id); //.. que tendrá el ID...
        if (requests.contains($temp)) { // ... para buscarlo en la colección. Para eso sirve el método equals(). Si existe en la colección...
            int idx = requests.indexOf($temp);  //... obtenemos su índice...
            Request actual = requests.get(idx); //... lo obtenemos de la colección...
          //  requestResource.setRequest(actual); //.. y lo marcamos como "actual"...
            return actual; //... para que lo devuelva al cliente.
        }
        return null; //.. si no lo encuentra, devuelve null
    }
    
    @Path("{idRequest}/")
    @DELETE
    public void borrarRequest(@PathParam("idRequest") String id) {
                if (id == null) { //si no se pasó el ID...
        }
        Request $temp = new Request(); //creamos un temporal...
        $temp.setIdRequest(id); //.. que tendrá el ID...
        if (requests.contains($temp)) { // ... para buscarlo en la colección. Para eso sirve el método equals(). Si existe en la colección...
            int idx = requests.indexOf($temp);  //... obtenemos su índice...
            Request actual = requests.get(idx); //... lo obtenemos de la colección...
            requests.remove(actual);
        }
   //     requests.remove(p); //... busca en la lista y borra el elemento
    }

    @Path("{idRequest}/")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces("application/json")
        public Request postimage(
            @FormDataParam("file") FormDataContentDisposition fileDetail,
            @FormDataParam("file") InputStream uploadedInputStream,
            
            @PathParam("idRequest") String id )
        {    
        if (id == null) { //si no se pasó el ID...
            return null; //... termina el método
        }
        Request $temp = new Request(); //creamos un temporal...
        $temp.setIdRequest(id); //.. que tendrá el ID...
        if (requests.contains($temp)) { // ... para buscarlo en la colección. Para eso sirve el método equals(). Si existe en la colección...
            int idx = requests.indexOf($temp);  //... obtenemos su índice...
            Request actual = requests.get(idx); //... lo obtenemos de la colección...
            String filePath = "./" + id + "/" + actual.getNombreImagen() + actual.getFormatoImagen();// + contentDispositionHeader.getFileName();
            actual.setPathImagenServer(filePath);
            writeToFile(uploadedInputStream,filePath);
            actual.EnhanceImage();
            actual.ConvertImage();
            //actual.settextoConvertido("Aqui va el Texto Convertido para el Request " + actual.getIdRequest());
            //actual.setconversionResult("OK");
  
            return actual;
            //return Response.ok().build(); //... para que lo devuelva al cliente.
        }
        $temp.setconversionResult("ERROR1");
        return $temp;
        //return Response.serverError().build(); //.. si no lo encuentra, devuelve null
    
    }

        /*
    @Path("{idRequest}/doc/")
    @GET
    @Produces(MediaType.MULTIPART_FORM_DATA)
        public InputStream getDoc(
            @PathParam("idRequest") String id )
        {    
        if (id == null) { //si no se pasó el ID...
            return null; //... termina el método
        }
        Request $temp = new Request(); //creamos un temporal...
        $temp.setIdRequest(id); //.. que tendrá el ID...
        if (requests.contains($temp)) { // ... para buscarlo en la colección. Para eso sirve el método equals(). Si existe en la colección...
            int idx = requests.indexOf($temp);  //... obtenemos su índice...
            Request actual = requests.get(idx); //... lo obtenemos de la colección...
            //String filePath = "./" + id + "/" + actual.getNombreImagen() + actual.getFormatoImagen();// + contentDispositionHeader.getFileName();
            return actual.getImagen();
           // return Response.ok().build(); //... para que lo devuelva al cliente.
        }
        return null; //.. si no lo encuentra, devuelve null
    
    }    */
        
    public void cambiarRequest(Request actual, Request p) {
        int idx = requests.indexOf(actual); //... obtiene la posición del actual
        if (idx >= 0) { //... si existe...
            p.setIdRequest(actual.getIdRequest()); //... ponerle el ID en el nuevo objeto...
            requests.set(idx, p); //..y reemplazar el objeto en la misma posición del anterior
        }
    }
    
    private void writeToFile(InputStream uploadedInputStream,
                                        String uploadedFileLocation) {

                        try {
                                        File file= new File(uploadedFileLocation);
                                        file.getParentFile().mkdirs();
                                        OutputStream out = new FileOutputStream(file);
                                        int read = 0;
                                        byte[] bytes = new byte[1024];

                                        //out = new FileOutputStream(new File(uploadedFileLocation));
                                        while ((read = uploadedInputStream.read(bytes)) != -1) {
                                                        out.write(bytes, 0, read);
                                        }
                                        out.flush();
                                        out.close();
                        } catch (IOException e) {

                                        e.printStackTrace();
                        }
    }       
}