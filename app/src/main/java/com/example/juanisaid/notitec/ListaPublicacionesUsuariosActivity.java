package com.example.juanisaid.notitec;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListaPublicacionesUsuariosActivity extends AppCompatActivity
{

    private ListView Lv;
    Context context;
    ArrayAdapter<String> adapter;
    ArrayList<String> data;
    //String cadenaApi = "http://192.168.1.71/PublicWebServiceRest1/Api/Publicaciones";
    String cadenaApi = "http://core.itnuevolaredo.edu.mx/notitec/Api/Publicaciones";
    String correoCopiado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_publicaciones_usuarios);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        Lv = (ListView) findViewById(R.id.listView);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        context = this;
        Toast.makeText(this,"Loading Please Wait..",Toast.LENGTH_SHORT).show();

        new WSListar().execute();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent intent = new Intent(getApplicationContext(), CrearPublicacionActivity.class);
                startActivity(intent);
            }
        });
    }

    private class WSListar extends AsyncTask<String,Integer,Boolean>
    {
        private String[] notas;
        public List<String> notasListaDescripcion = new ArrayList<String>();

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
                notas = new String[respJSON.length()];
                notasListaDescripcion = new ArrayList<String>(respJSON.length());
                for(int i=0; i<respJSON.length(); i++)
                {
                    JSONObject obj = respJSON.getJSONObject(i);
                    String depa = obj.getString("Departamento");
                    String desc = obj.getString("Descripcion");
                    String link = obj.getString("Enlace");
                    String fecha = obj.getString("Fecha");
                    String correo = obj.getString("CorreoElectronico");
                    notas[i] = "\n" + depa + "\n"+ "\n" + desc + "\n"+ "\n" + link + "\n"+ "\n" + fecha + "\n"+ "\n" + correo +"\n";
                    notasListaDescripcion.add(""+ desc);
                    //correoCopiado = notasListaCorreos.set(i,correo);
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
                //Rellenamos la lista con los resultados
                ArrayAdapter<String> adaptador = new ArrayAdapter<String>(ListaPublicacionesUsuariosActivity.this, android.R.layout.simple_list_item_1, notas);
                Lv.setAdapter(adaptador);
                Toast.makeText(context,"Loading Completed",Toast.LENGTH_SHORT).show();

                Lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
                    {

                        Toast.makeText(ListaPublicacionesUsuariosActivity.this, "" + notasListaDescripcion.get(position), Toast.LENGTH_LONG).show();
                        //correoCopiado = notasListaCorreos.get(position);
                    }
                });
            }
        }
    }
}
