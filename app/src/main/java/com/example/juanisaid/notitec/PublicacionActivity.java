package com.example.juanisaid.notitec;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PublicacionActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPublicacion;
    private RecyclerViewAdaptador adaptadorPublicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicacion);

        recyclerViewPublicacion=(RecyclerView)findViewById(R.id.ReciclerPublicacion);
        recyclerViewPublicacion.setLayoutManager(new LinearLayoutManager(this));
        //adaptadorPublicacion = new RecyclerViewAdaptador(data);
        adaptadorPublicacion = new RecyclerViewAdaptador(obtenerPublicacionDB());
        recyclerViewPublicacion.setAdapter(adaptadorPublicacion);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Intent intent = new Intent(getApplicationContext(), CrearPublicacionActivity.class);
                startActivity(intent);


            }
        });
    }
    protected class AsyncMostrarPublicacion extends AsyncTask<Void, JSONObject, List<PublicacionModelo>>
    {
        @Override
        protected List<PublicacionModelo> doInBackground(Void... params)
        {
            List<PublicacionModelo> publicacionModelo = null;
            RestAPI api = new RestAPI();
            try {

                JSONObject jsonObj = api.GetPublicacion();
                JSONParser parser = new JSONParser();
                //publicacionModelo = parser.parsePublicacionDetails(jsonObj);
                publicacionModelo=parser.parseDept(jsonObj);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncMostrarPublicacion", e.getMessage());
            }
            return publicacionModelo;
        }


        @Override
        protected void onPostExecute(List<PublicacionModelo> publicacionModelos) {
            super.onPostExecute(publicacionModelos);
            for (int i = 0; i < publicacionModelos.size(); i++)
            {
                publicacionModelos = new ArrayList<>();
                //publicacionModelos.add(new PublicacionModelo());
                //data.add(String.valueOf(new PublicacionModelo(publicacionModelos.get(i).getDepartamento(),publicacionModelos.get(i).getDescripcion(),publicacionModelos.get(i).getEnlace(),publicacionModelos.get(i).getFecha())));
                //data.add(publicacionModelos.get(i).getDescripcion());
                //data.add(publicacionModelos.get(i).getEnlace());
                //data.add(publicacionModelos.get(i).getFecha());
                //data.add(publicacionModelos.get(i).getDepartamento(),publicacionModelos.get(i).getDescripcion(),publicacionModelos.get(i).getEnlace(), publicacionModelos.get(i).getFecha());
                publicacionModelos.add(new PublicacionModelo(publicacionModelos.get(i).getDepartamento(), publicacionModelos.get(i).getDescripcion(), publicacionModelos.get(i).getEnlace(), publicacionModelos.get(i).getFecha(), publicacionModelos.get(i).getCorreo()));
            }
            //for (int i = 0; i< publicacionModelos.size(); i++) { data.(publicacionModelos.get(i).getDepartamento() + " " + result.get(i).getName()); }
        }
    }
    public List<PublicacionModelo> obtenerPublicacion1()
    {
        List<PublicacionModelo> publicacion = new ArrayList<>();
        publicacion.add(new PublicacionModelo("Recursos Humanos","Descripción: Este es un ejemplo del mensaje","www.facebook.com"/*,R.drawable.itnl*/,"28-02-2018","jisaid94@gmail.com"));
        publicacion.add(new PublicacionModelo("Gestión Tecnológica y Vinculación","Concurso para ganar una consola: Si desean participar comuniquese al correo aqui en la descripción. ","..."/*,R.drawable.itnl*/,"21-02-2018","jis00000000000@hotmail.com"));
        publicacion.add(new PublicacionModelo("Centro de información","Solicitud de Servicio Social","www.facebook.com"/*,R.drawable.itnl*/,"01-12-2017",""));
        publicacion.add(new PublicacionModelo("Sistemas Computacionales","Vidéo resumen de la semana del simpusium","www.youtube.com"/*,R.drawable.itnl*/,"19-11-2017",""));

        return publicacion;
    }

    public Connection conexionDB()
    {
        Connection conexion =null;
        try
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            conexion= DriverManager.getConnection("jdbc:jtds:sqlserver://10.10.21.161;databaseName=notitec;user=sa;password=123;");
            //conexion= DriverManager.getConnection("jdbc:jtds:sqlserver://itnuevolaredo.edu.mx;databaseName=administradores;user=sa;password=123;");
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return conexion;
    }

    public List<PublicacionModelo> obtenerPublicacionDB()
    {
        List<PublicacionModelo> publicacion = new ArrayList<>();
        try
        {
            Statement st =conexionDB().createStatement();
            ResultSet rs = st.executeQuery("select top 20 * from publicaciones order by fecha desc");
            while(rs.next())
            {
                publicacion.add(new PublicacionModelo(rs.getString("departamento"),rs.getString("descripcion"),rs.getString("enlace"),rs.getString("fecha"),rs.getString("correoElectronico")));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return publicacion;
    }

}
