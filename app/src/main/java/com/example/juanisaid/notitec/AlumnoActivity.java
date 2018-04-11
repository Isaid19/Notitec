package com.example.juanisaid.notitec;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.in;

public class AlumnoActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPublicacion1;
    private RecyclerViewAdaptador1 adaptadorPublicacion1;
    private Button EnviarCorreo;
    Context context;
    String cadenaApi = "http://10.10.21.249/RealWebServiceRest/Api/Publicaciones";
    //private Button WebView;
    ArrayAdapter<String> adapter;
    //ArrayList<PublicacionModelo> dataListAlumno;
    //ArrayList<String> dataListAlumno;
    //List<String> data;
    List<AlumnoModelo> dataListAlumno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno);

        context = this;

        //dataListAlumno = new ArrayList<AlumnoModelo>();

        //AsyncMostrarNota mostrar = new AsyncMostrarNota();new AsyncMostrarNota().execute();
        /*try
        {
            JSONObject jsonObj = new JSONObject(cadenaApi);
            Gson gson = new Gson();
            AlumnoModelo order = gson.fromJson(String.valueOf(jsonObj), AlumnoModelo.class);
            for(int i = 0; i<dataListAlumno.size(); i++)
            {
                dataListAlumno.add(new AlumnoModelo(order.getDepartamento(), order.getDescripcion(),order.getEnlace(),
                        order.getFecha(),order.getCorreo()));
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "enviado desde la variable... "+ e.getMessage(), Toast.LENGTH_LONG).show();
        }*/


        recyclerViewPublicacion1 = (RecyclerView) findViewById(R.id.ReciclerPublicacion1);
        recyclerViewPublicacion1.setHasFixedSize(true);
        recyclerViewPublicacion1.setLayoutManager(new LinearLayoutManager(this));
        //recyclerViewPublicacion1.setAdapter(adapter);
        //mostrar.onPostExecute(dataListAlumno);

        adaptadorPublicacion1 = new RecyclerViewAdaptador1(getPub());
        recyclerViewPublicacion1.setAdapter(adaptadorPublicacion1);
        EnviarCorreo = (Button) findViewById(R.id.btnEnviarCorreo);

        EnviarCorreo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });

    }


    //Este método se encarga de precargar el mensaje que quieres mandarle al profesor
    protected void sendEmail() {
        String[] TO = {"ejemplo@gmail.com"}; //aquí pon tu correo
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
// Esto podrás modificarlo si quieres, el asunto y el cuerpo del mensaje
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Me interesa");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Saludos, estoy interesado en esta actividad. ¿Qué tengo que hacer?");

        try {
            startActivity(Intent.createChooser(emailIntent, "Enviar email..."));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getApplicationContext(),
                    "No tienes apps de email instalados.", Toast.LENGTH_SHORT).show();
        }
    }


   /* public void onClick(View view) {
        Intent i=new Intent(getApplicationContext(),WebViewActivity.class);
        startActivity(i);
    }*/

    protected class AsyncMostrarNota extends AsyncTask<Void, JSONObject, List<AlumnoModelo>> {

        @Override
        protected List<AlumnoModelo> doInBackground(Void... params) {
            WebServiceRest api = new WebServiceRest();
            //RestAPI api = new RestAPI();
            try {

                JSONObject jsonObj = api.GetPublicacion();
                JSONParser parser = new JSONParser();
                //publicacionModelo = parser.parsePublicacionDetails(jsonObj);
                dataListAlumno = parser.parseAlu(jsonObj);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncMostrarNota", e.getMessage());
                Toast.makeText(getApplicationContext(), "enviado desde el AsyncLoad", Toast.LENGTH_SHORT).show();
            }
            return dataListAlumno;
        }


        @Override
        protected void onPostExecute(List<AlumnoModelo> publicacionModelos) {
            super.onPostExecute(publicacionModelos);
            //dataListAlumno = new ArrayList<PublicacionModelo>();
            for (int i = 0; i < publicacionModelos.size(); i++) {
                //dataListAlumno.add(publicacionModelos.get(i).getDepartamento(),publicacionModelos.get(i).getDescripcion());

                dataListAlumno.add(new AlumnoModelo(publicacionModelos.get(i).getDepartamento(), publicacionModelos.get(i).getDescripcion(), publicacionModelos.get(i).getEnlace(), publicacionModelos.get(i).getFecha(), publicacionModelos.get(i).getCorreo()));
                //data.add(publicacionModelos.get(i).getDescripcion());
                //publicacionModelos.add(new AlumnoModelo(publicacionModelos.get(i).getDepartamento(), publicacionModelos.get(i).getDescripcion(), publicacionModelos.get(i).getEnlace(), publicacionModelos.get(i).getFecha(), publicacionModelos.get(i).getCorreo()));
            }
            adapter.notifyDataSetChanged();
            //for (int i = 0; i< publicacionModelos.size(); i++) { data.(publicacionModelos.get(i).getDepartamento() + " " + result.get(i).getName()); }
        }
    }
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
                arrayListAlu.add(new AlumnoModelo(jsonObj.getString("Departamento"), jsonObj.getString("Descripcion"),jsonObj.getString("Enlace")/*,jsonObj.getInt("foto")*/,jsonObj.getString("Fecha"),jsonObj.getString("CorreoElectronico")));
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
     protected class AsyncMostrarPublicacion extends AsyncTask<Void, JSONObject, String>
     {
         @Override
         protected String doInBackground(Void... params)
         {
             List<AlumnoModelo> alumnoModelo = null;
             WebServiceRest api = new WebServiceRest();
             //RestAPI api = new RestAPI();
             try {

                 JSONObject jsonObj = api.GetPublicacion();
                 JSONParser parser = new JSONParser();
                 //publicacionModelo = parser.parsePublicacionDetails(jsonObj);
                 alumnoModelo=parseAlu(jsonObj);

             } catch (Exception e) {
                 // TODO Auto-generated catch block
                 Log.d("AsyncMostrarPublicacion", e.getMessage());
             }
             return "alumnoModelo";
         }
         @Override
         protected void onPostExecute(String alumnoModelos) {
             super.onPostExecute(alumnoModelos);

             JSONObject json = null;
             JSONObject dataObject = null;
             try {
                 json = new JSONObject(String.valueOf(alumnoModelos));
                 dataObject = json.getJSONObject("data");
                 JSONArray items = dataObject.getJSONArray("items");
                 //for (int i = 0; i < alumnoModelos.size(); i++)
                 for(int i = 0; i<items.length(); i++)
                 {
                     //data.add(String.valueOf(new PublicacionModelo(publicacionModelos.get(i).getDepartamento(),publicacionModelos.get(i).getDescripcion(),publicacionModelos.get(i).getEnlace(),publicacionModelos.get(i).getFecha())));
                     // publicacionModelos.add(String.valueOf(new PublicacionModelo(publicacionModelos.get(i).getDepartamento(),publicacionModelos.get(i).getDescripcion(),publicacionModelos.get(i).getEnlace(),publicacionModelos.get(i).getFecha())));
                     //data.add(publicacionModelos.get(i).getDescripcion());
                     //data.add(publicacionModelos.get(i).getEnlace());
                     //data.add(publicacionModelos.get(i).getFecha());
                     //data.add(publicacionModelos.get(i).getDepartamento(),publicacionModelos.get(i).getDescripcion(),publicacionModelos.get(i).getEnlace(), publicacionModelos.get(i).getFecha());
                     JSONObject alumnoObject = items.getJSONObject(i);
                     AlumnoModelo alumnoModelo = new AlumnoModelo(alumnoObject.getString("departamento"),alumnoObject.getString("descripcion"),alumnoObject.getString("enlace"),alumnoObject.getString("fecha"),alumnoObject.getString("correoElectronico"));
                     //dataListAlumno.add(new AlumnoModelo(alumnoModelos.get(i).getDepartamento(), alumnoModelos.get(i).getDescripcion(), alumnoModelos.get(i).getEnlace(), alumnoModelos.get(i).getFecha(), alumnoModelos.get(i).getCorreo()));
                 }
             } catch (JSONException e) {
                 e.printStackTrace();
             }
             //for (int i = 0; i< publicacionModelos.size(); i++) { data.(publicacionModelos.get(i).getDepartamento() + " " + result.get(i).getName()); }
         }
     }



    //Ejemplo de como mostrar la publicación
    public List<AlumnoModelo> obtenerPublicacion1() {
        List<AlumnoModelo> alumno = new ArrayList<>();

        alumno.add(new AlumnoModelo("Recursos Humanos", "Descripción", "www.facebook.com",/*R.drawable.itnl,*/"01-12-2017", "jisaid94@gmail.com"));
        alumno.add(new AlumnoModelo("Gestión Tecnológica y Vinculación", "Evento eneit2017", "...",/*R.drawable.itnl,*/"29-11-2017", "jis00000000000@hotmail.com"));
        alumno.add(new AlumnoModelo("Centro de información", "Solicitud de Servicio Social", "www.facebook.com",/*R.drawable.itnl,*/"01-12-2017", ""));
        alumno.add(new AlumnoModelo("Sistemas Computacionales", "Vídeo resumen de la semana del simposium", "www.youtube.com",/*R.drawable.itnl,*/"19-11-2017", ""));
        return alumno;
    }

    private class TareaWSListar1 extends AsyncTask<String,Integer,Boolean>
    {
        private String[] notas;
        List <AlumnoModelo> list = new ArrayList<AlumnoModelo>();
        protected Boolean doInBackground(String... params)
        {
            boolean resul = true;
            HttpClient httpClient = new DefaultHttpClient();
            org.apache.http.client.methods.HttpGet del = new org.apache.http.client.methods.HttpGet(cadenaApi);
            del.setHeader("content-type", "application/json");
            try
            {
                HttpResponse resp = httpClient.execute(del);
                String respStr = EntityUtils.toString(resp.getEntity());
                JSONArray respJSON = new JSONArray(respStr);
                //notas = new String[respJSON.length()];


                for(int i=0; i<dataListAlumno.size(); i++)
                {
                    JSONObject obj = respJSON.getJSONObject(i);

                    dataListAlumno.add(new AlumnoModelo(obj.getString("Departamento"),obj.getString("Descripcion"),obj.getString("Enlace"),obj.getString("Fecha"),obj.getString("CorreoElectronico")));

                    /*String depa = obj.getString("Departamento");
                    String desc = obj.getString("Descripcion");
                    String link = obj.getString("Enlace");
                    String fecha = obj.getString("Fecha");
                    String correo = obj.getString("CorreoElectronico");
                    notas[i] = "" + depa +
                            "\n" + desc +
                            "\n" + link +
                            "\n" + fecha +
                            "\n" + correo;*/

                }
            }

            catch(Exception ex)
            {
                Log.e("ServicioRest","Error!", ex);
                resul = false;
                Toast.makeText(getApplicationContext(),"Error desde el AsyncTask" + ex.getMessage(),Toast.LENGTH_SHORT).show();
            }
            return resul;
        }

        protected void onPostExecute(Boolean result)
        {
            if (result)
            {
                adaptadorPublicacion1 = new RecyclerViewAdaptador1(dataListAlumno);
                recyclerViewPublicacion1.setAdapter(adaptadorPublicacion1);
                //Rellenamos la lista con los resultados
                /*ArrayAdapter<String> adaptador = new ArrayAdapter<String>(ListaPublicacionActivity.this, android.R.layout.simple_list_item_1, notas);
                Lv.setAdapter(adaptador);*/
            }
        }
    }

    public List<AlumnoModelo> getPub()
    {
        List<AlumnoModelo> lista = new ArrayList<>(); //inicializamos la lista donde almacenaremos los objetos Fruta

        JSONObject object = null; //Creamos un objeto JSON a partir de la cadena
        try
        {
            object = new JSONObject(cadenaApi);
            JSONArray json_array = object.optJSONArray("");
            for (int i = 0; i < json_array.length(); i++)
            {
                lista.add(new AlumnoModelo(json_array.getJSONObject(i))); //creamos un objeto Alumno y lo insertamos en la lista
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "enviado desde el método   "+e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return lista;
    }

    public List<AlumnoModelo> obtenerPublicacionPorApi() {
        //RestAPI api = new RestAPI();
        WebServiceRest api = new WebServiceRest();
        try {
            JSONObject jsonObj = api.GetPublicacion();
            JSONParser parser = new JSONParser();
            dataListAlumno = parser.parseAlu(jsonObj);
            for (int i = 0; i < dataListAlumno.size(); i++) {
                dataListAlumno.add(new AlumnoModelo(dataListAlumno.get(i).getDepartamento(), dataListAlumno.get(i).getDescripcion(), dataListAlumno.get(i).getEnlace(), dataListAlumno.get(i).getFecha(), dataListAlumno.get(i).getCorreo()));
            }
        } catch (Exception e) {

            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "enviado desde el método   "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        //dataListAlumno.add(new AlumnoModelo("Recursos Humanos1","Descripción","www.facebook.com",/*R.drawable.itnl,*/"01-12-2017","jisaid94@gmail.com"));

        //publicacionModelo = parser.parsePublicacionDetails(jsonObj);
        //alumnoModelo=parser.parseAlu(jsonObj);
        return dataListAlumno;
    }

    public List<AlumnoModelo> obtenerPublicacion() {
        List<AlumnoModelo> alumno = new ArrayList<>();
        alumno.add(new AlumnoModelo("El Departamento", "Descripcion", "www.ejemplo_enlace.com"/*,R.drawable.itnl*/, "01-01-2018", ""));
        alumno.add(new AlumnoModelo("El Departamento", "Descripcion", "www.ejemplo_enlace.com"/*,R.drawable.itnl*/, "01-01-2018", ""));
        alumno.add(new AlumnoModelo("El Departamento", "Descripcion", "www.ejemplo_enlace.com"/*,R.drawable.itnl*/, "01-01-2018", ""));
        return alumno;
    }

    public Connection conexionDB() {
        Connection conexion = null;
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.18.71;databaseName=notitec;user=sa;password=123;");
            //conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://core.itnuevolaredo.edu.mx/isaid/;databaseName=administradores;user=sa;password=123;");
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return conexion;
    }

    public List<AlumnoModelo> obtenerPublicacionDB() {
        ArrayList<AlumnoModelo> alumno = new ArrayList<>();
        try {
            Statement st = conexionDB().createStatement();
            ResultSet rs = st.executeQuery("select top 10 * from publicaciones order by fecha desc");
            while (rs.next()) {
                alumno.add(new AlumnoModelo(rs.getString("departamento"), rs.getString("descripcion"), rs.getString("enlace"), rs.getString("fecha"), rs.getString("correoElectronico")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alumno;
    }
}



