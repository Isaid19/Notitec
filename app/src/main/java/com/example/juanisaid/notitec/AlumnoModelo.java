package com.example.juanisaid.notitec;

import android.util.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.in;

/**
 * Created by Juan Isaid on 26/02/2018.
 */

public class AlumnoModelo extends PublicacionModelo
{
    public AlumnoModelo(String departamento, String descripcion, String enlace, String fecha,String correoElectronico)
    {
        super(departamento, descripcion, enlace, fecha,correoElectronico);
    }

    public AlumnoModelo(){}

    public AlumnoModelo(JSONObject objetoJSON) {

    }

    public AlumnoModelo(String error, Object o, Object o1) {
    }
/*
    public List leerArrayAlumnos(JsonReader reader) throws IOException {
        // Lista temporal
        ArrayList animales = new ArrayList();

        reader.beginArray();
        while (reader.hasNext()) {
            // Leer objeto
            animales.add(leerAlumno(reader));
        }
        reader.endArray();
        return animales;
    }

    public List<AlumnoModelo> readJsonStream(InputStream in) throws IOException {
        // Nueva instancia JsonReader
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            // Leer Array
            return leerArrayAlumnos(reader);
        } finally {
            reader.close();
        }

    }

    public AlumnoModelo leerAlumno(JsonReader reader) throws IOException
    {
        String depa =null;
        String desc =null;
        String link =null;
        String dias =null;
        String email =null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "Departamento":
                    depa = reader.nextString();
                    break;
                case "Descripcion":
                    desc = reader.nextString();
                    break;
                case "Enlace":
                    link = reader.nextString();
                    break;
                case "Fecha":
                    dias = reader.nextString();
                    break;
                case "CorreoElectronico":
                    email = reader.nextString();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return new AlumnoModelo(depa,desc,link,dias,email);
    }

    private static String KEY_SUCCESS = "success";

    public void saveCorreo(Activity activity)
    {
        HttpClientManager httpclient = new HttpClientManager(activity);

        httpclient.addNameValue("isbn", getCorreo());
    }*/
}
