package com.example.juanisaid.notitec;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Juan Isaid on 26/02/2018.
 */

public class PublicacionModelo
{
    @SerializedName("Departamento")
    public String departamento;

    @SerializedName("Descripcion")
    public String descripcion;

    @SerializedName("Enlace")
    public String enlace;

    @SerializedName("Fecha")
    public String fecha;

    @SerializedName("CorreoElectronico")
    public String correo;

    public PublicacionModelo(String departamento, String descripcion, String enlace, String fecha,String correo) {
        super();
        this.departamento = departamento;
        this.descripcion = descripcion;
        this.enlace = enlace;
        this.fecha = fecha;
        this.correo = correo;
    }

    public PublicacionModelo()
    {
        super();
        this.departamento=null;
        this.descripcion = null;
        this.enlace = null;
        this.fecha = null;
        this.correo = null;
    }

    public String getDepartamento() { return departamento; }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEnlace() {
        return enlace;
    }

    public void setEnlace(String enlace) {
        this.enlace = enlace;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCorreo(){ return correo; }

    public void setCorreo(String correo) { this.correo = correo; }

}
