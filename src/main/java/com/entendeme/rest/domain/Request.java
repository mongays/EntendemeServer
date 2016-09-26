/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entendeme.rest.domain;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Request {

   // private static int contador = 0;
    private String idRequest;
    private String nombreUsuario;
    private String nombreImagen;
    private String formatoImagen;
    private String rutaImagen;
    private InputStream Imagen;

    public static String getNuevoId(String nombreUsuario) {
        Date myDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss");
        String myDateString = sdf.format(myDate);
        return nombreUsuario+myDateString;
    }

    @XmlAttribute(name = "idRequest")
    public String getIdRequest() {
        Date myDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss");
        String myDateString = sdf.format(myDate);
        return idRequest;
    }

    public void setIdRequest(String idRequest) {
        this.idRequest = idRequest;
    }

    @XmlElement(name = "nombreUsuario")
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombre) {
        this.nombreUsuario = nombre;
    }

    @XmlElement(name = "nombreImagen")
    public String getNombreImagen() {
        return nombreImagen;
    }

    public void setNombreImagen(String nombre) {
        this.nombreImagen = nombre;
    }

    @XmlElement(name = "formatoImagen")
    public String getFormatoImagen() {
        return formatoImagen;
    }

    public void setFormatoImagen(String formato) {
        this.formatoImagen = formato;
    }

    @XmlElement(name = "rutaImagen")
    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String ruta) {
        this.rutaImagen = ruta;
    }

    public InputStream getImagen() {
        return Imagen;
    }

    public void setImagen(InputStream Imagen) {
        this.Imagen = Imagen;
    }    
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Request other = (Request) obj;
        if (!this.idRequest.equals(other.idRequest)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.idRequest.hashCode();
        return hash;
    }
}
