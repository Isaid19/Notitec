package com.example.juanisaid.notitec;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juan Isaid on 26/02/2018.
 */
public class JSONParser
{
    Context context;
    public JSONParser()
    {
        super();
    }

    public List<PublicacionModelo> parseDept(JSONObject object)
    {
        List<PublicacionModelo> arrayList= new ArrayList<PublicacionModelo>();
        try
        {
            JSONArray jsonArray=object.getJSONArray("");
            JSONObject jsonObj = null;
            for(int i=0; i < jsonArray.length();i++)
            {
                //String departamento, String descripcion, String enlace,                                                                     String fecha, int foto
                jsonObj=jsonArray.getJSONObject(i);
                arrayList.add(new PublicacionModelo(jsonObj.getString("Departamento"), jsonObj.getString("Descripcion"),
                        jsonObj.getString("Enlace"),jsonObj.getString("Fecha"),jsonObj.getString("CorreoElectronico")));
            }
        }
        catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.d("JSONParser => parseDept", e.getMessage());

        }
        return arrayList;
    }

    public ArrayList<AlumnoModelo> parseDepa(JSONObject object)
    {
        ArrayList<AlumnoModelo> arrayList=new ArrayList<AlumnoModelo>();
        try {
            JSONArray jsonArray=object.getJSONArray("");
            JSONObject jsonObj=null;
            for(int i=0;i<jsonArray.length();i++)
            {
                jsonObj=jsonArray.getJSONObject(i);
                arrayList.add(new AlumnoModelo(jsonObj.getString("Departamento"), jsonObj.getString("Descripcion"),
                        jsonObj.getString("Enlace"),jsonObj.getString("Fecha"),jsonObj.getString("CorreoElectronico")));
            }
        }
        catch (JSONException e)
        { // TODO Auto-generated catch block Log.d("JSONParser => parseDepartment", e.getMessage());
            Toast.makeText(context, "enviado desde el JSONPARSER" + e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return arrayList;
    }


    /*public List<PublicacionModelo> parseDept(JSONObject object)
    {
        List<PublicacionModelo> arrayList= new ArrayList<PublicacionModelo>();
        try
        {
            JSONArray jsonArray=object.getJSONArray("Value");
            JSONObject jsonObj = null;
            for(int i=0; i < jsonArray.length();i++)
            {
                //String departamento, String descripcion, String enlace,                                                                     String fecha, int foto
                jsonObj=jsonArray.getJSONObject(i);
                arrayList.add(new PublicacionModelo(jsonObj.getString("departamento"), jsonObj.getString("descripcion"),jsonObj.getString("enlace")*//*,jsonObj.getInt("foto")*//*,jsonObj.getString("fecha"),jsonObj.getString("correoElectronico")));
            }
        }
        catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.d("JSONParser => parseDept", e.getMessage());
        }
        return arrayList;
    }*/

    public List<AlumnoModelo> parseAlu(JSONObject object)
    {
        List<AlumnoModelo> arrayListAlu= new ArrayList<AlumnoModelo>();
        try
        {
            JSONArray jsonArray=object.getJSONArray("");
            JSONObject jsonObj = null;
            for(int i=0; i<jsonArray.length();i++)
            {//String departamento, String descripcion, String enlace,                                                                     String fecha, int foto
                jsonObj=jsonArray.getJSONObject(i);
                arrayListAlu.add(new AlumnoModelo(jsonObj.getString("Departamento"), jsonObj.getString("Descripcion"),
                        jsonObj.getString("Enlace"),jsonObj.getString("Fecha"),jsonObj.getString("CorreoElectronico")));
            }
        }
        catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.d("JSONParser => parseAlu", e.getMessage());
            Toast.makeText(context, "enviado desde el JSONPARSER" + e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return arrayListAlu;
    }

    public boolean parUserAu(JSONObject object)
    {     boolean userAtuh=false;
        try {
            userAtuh= object.getBoolean("Value");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d("JSONParser => parUserAu", e.getMessage());
            Toast.makeText(context, "enviado desde el JSONPARSER" + e.getMessage(),Toast.LENGTH_SHORT).show();
        }

        return userAtuh;
    }

    public Usuario parUserDt(JSONObject object)
    {
        Usuario userDetail=new Usuario();

        try {
            JSONObject jsonObj=object.getJSONArray("Value").getJSONObject(0);

            userDetail.setCorreoElectronico(jsonObj.getString("correoElectronico"));
            userDetail.setContrseña(jsonObj.getString("contraseña"));

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d("JSONParser => parUserDt", e.getMessage());
        }

        return userDetail;
    }

    public PublicacionModelo parPubDet(JSONObject object)
    {
        PublicacionModelo publicacionModelo = new PublicacionModelo();
        try {
            JSONObject jsonObj=object.getJSONArray("Value").getJSONObject(0);

            publicacionModelo.setDepartamento(jsonObj.getString("Departamento"));
            publicacionModelo.setDescripcion(jsonObj.getString("Descripcion"));
            publicacionModelo.setEnlace(jsonObj.getString("Enlace"));
            publicacionModelo.setFecha(jsonObj.getString("Fecha"));
            publicacionModelo.setCorreo(jsonObj.getString("CorreoElectronico"));

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d("JSONParser => parPubDet", e.getMessage());
        }
        return publicacionModelo;
    }
    public AlumnoModelo parPubAlu(JSONObject object)
    {
        AlumnoModelo alumnoModelo = new AlumnoModelo();
        try {
            JSONObject jsonObj=object.getJSONArray("").getJSONObject(0);

            alumnoModelo.setDepartamento(jsonObj.getString("Departamento"));
            alumnoModelo.setDescripcion(jsonObj.getString("Descripcion"));
            alumnoModelo.setEnlace(jsonObj.getString("Enlace"));
            alumnoModelo.setFecha(jsonObj.getString("Fecha"));
            alumnoModelo.setCorreo(jsonObj.getString("CorreoElectronico"));

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d("JSONParser => parPubAlu", e.getMessage());
        }
        return alumnoModelo;
    }
}
