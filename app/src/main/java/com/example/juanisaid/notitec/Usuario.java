package com.example.juanisaid.notitec;

/**
 * Created by Juan Isaid on 26/02/2018.
 */

public class Usuario
{
    private String  nombreUsuario,correoElectronico,departamento,contraseña;

    public Usuario(String nombreUsuario, String correoElectronico, String contrseña, String departamento) {

        this.nombreUsuario = nombreUsuario;
        this.correoElectronico = correoElectronico;
        this.departamento = departamento;
        this.contraseña = contrseña;
    }
    public Usuario()
    {
        super();
        this.nombreUsuario = null;
        this.correoElectronico = null;
        this.departamento = null;
        this.contraseña = null;
    }
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getContrseña() {
        return contraseña;
    }

    public void setContrseña(String contrseña) {
        this.contraseña = contrseña;
    }
}
