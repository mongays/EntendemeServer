/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entendeme.rest.domain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private String PathImagenServer;
    private String textoConvertido;
    private String conversionResult;

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

    public String getPathImagenServer() {
        return PathImagenServer;
    }

    public void setPathImagenServer(String PathImagenServer) {
        this.PathImagenServer = PathImagenServer;
    }    
    
    public String gettextoConvertido() {
        return textoConvertido;
    }
    
    public void settextoConvertido(String textoConvertido) {
        this.textoConvertido = textoConvertido;
    }  

    public void setconversionResult(String conversionResult) {
        this.conversionResult = conversionResult;
    }  
    
    public String getconversionResult() {
        return conversionResult;
    }
 
    public void EnhanceImage()  {
        String s;
        Process p;
        try {
            String textcleanerpath= "/opt/textcleaner ";
            String comando = textcleanerpath + "-g -e stretch -f 25 -o 5 -s 1 " + this.getPathImagenServer() + " ./" + this.getIdRequest() + "/" + this.getNombreImagen() + "enh.png";
            p = Runtime.getRuntime().exec(comando);
                        BufferedReader br = new BufferedReader(
                new InputStreamReader(p.getInputStream()));
                        BufferedReader be = new BufferedReader(
                new InputStreamReader(p.getErrorStream()));
            while ((s = be.readLine()) != null)
            System.out.println("line: " + s);
            while ((s = br.readLine()) != null)
            System.out.println("line: " + s);
            int exitval = p.waitFor();
            System.out.println ("exit: " + p.exitValue());
            p.destroy();
            this.setPathImagenServer("./" + this.getIdRequest() + "/" + this.getNombreImagen() + "enh.png");
            
        } catch (IOException ex) {
            Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    public void ConvertImage()  {
        String s;
        Process p;
        try {
            String tessdata_path= " --tessdata-dir /opt/tesseract-master/tessdata ";
            String comando = "tesseract" + tessdata_path +" -l tla -psm 6 " + this.getPathImagenServer() + " ./" + this.getIdRequest() + "/" + this.getNombreImagen();
            p = Runtime.getRuntime().exec(comando);
                        BufferedReader br = new BufferedReader(
                new InputStreamReader(p.getInputStream()));
                        BufferedReader be = new BufferedReader(
                new InputStreamReader(p.getErrorStream()));
            while ((s = be.readLine()) != null)
            System.out.println("line: " + s);
            while ((s = br.readLine()) != null)
            System.out.println("line: " + s);
            int exitval = p.waitFor();
            System.out.println ("exit: " + p.exitValue());
            p.destroy();
            this.CargarTextoConvertido();
            
        } catch (IOException ex) {
            Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   public void CargarTextoConvertido()  {   
       
       String textoPath = "./" + this.getIdRequest() + "/" + this.getNombreImagen() + ".txt";
        try {
            this.settextoConvertido(readFile(textoPath,Charset.defaultCharset()));
            this.setconversionResult("OK");
           } catch (IOException ex) {

            Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
        }
       
   }

        static String readFile(String path, Charset encoding) 
          throws IOException 
        {
          byte[] encoded = Files.readAllBytes(Paths.get(path));
          return new String(encoded, encoding);
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
