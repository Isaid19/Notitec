package com.example.juanisaid.notitec;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

//import com.loopj.android.http.HttpGet;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListaPublicacionActivity extends AppCompatActivity
{
    private ListView Lv;
    private Button EnviarCorreo;
    Context context;
    ArrayAdapter<String> adapter;
    ArrayList<String> data;
    //String cadenaApi = "http://192.168.1.71/PublicWebServiceRest1/Api/Publicaciones";
    String cadenaApi = "http://core.itnuevolaredo.edu.mx/notitec/Api/Publicaciones";
    String correoCopiado;
//https://geekytheory.com/trabajando-con-json-en-android/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_publicacion);

        //data = new ArrayList<String>();
        Lv = (ListView) findViewById(R.id.lv);
        context = this;

        //adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
       // Lv.setAdapter(adapter);
        Toast.makeText(this,"Loading Please Wait..",Toast.LENGTH_SHORT).show();
       // new AsyncLoadPub().execute();
        //WSListar tarea =
                new WSListar().execute();
        //tarea.execute();

        EnviarCorreo = (Button) findViewById(R.id.btnEnviarCorreo);

        EnviarCorreo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });
    }

    protected void sendEmail() {
        String[] TO = {correoCopiado}; //aquí pon tu correo
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

    private class WSListar extends AsyncTask<String,Integer,Boolean>
    {
		private String[] notas;
        public List<String> notasListaCorreos = new ArrayList<String>();
        public List<String> notasListaPub = new ArrayList<String>();
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
                notasListaCorreos = new ArrayList<String>(respJSON.length());
                notasListaPub = new ArrayList<String>(respJSON.length());
	        	for(int i=0; i<respJSON.length(); i++)
	        	{
	        		JSONObject obj = respJSON.getJSONObject(i);
		        	String depa = obj.getString("Departamento");
		        	String desc = obj.getString("Descripcion");
                    String link = obj.getString("Enlace");
                    String fecha = obj.getString("Fecha");
                    String correo = obj.getString("CorreoElectronico");
	        		notas[i] = "\n" + depa + "\n"+ "\n" + desc + "\n"+ "\n" + link + "\n"+ "\n" + fecha + "\n"+ "\n" + correo +"\n";
                    notasListaPub.add(""+desc);
                    notasListaCorreos.add(""+ correo);
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
	        	ArrayAdapter<String> adaptador = new ArrayAdapter<String>(ListaPublicacionActivity.this, android.R.layout.simple_list_item_1, notas);
	        	Lv.setAdapter(adaptador);
                Toast.makeText(context,"Loading Completed",Toast.LENGTH_SHORT).show();

                Lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
                    {
                        Toast.makeText(ListaPublicacionActivity.this, "Copiado: " + notasListaCorreos.get(position), Toast.LENGTH_LONG).show();
                        correoCopiado = notasListaCorreos.get(position);
                    }
                });
	    	}
	    }
	}

/*
    protected class AsyncLoadPub extends AsyncTask<Void, JSONObject, ArrayList<AlumnoModelo>> {
        ArrayList<AlumnoModelo> deptTable = null;

        @Override
        protected ArrayList<AlumnoModelo> doInBackground(Void... params) {
            // TODO Auto-generated method stub

            RestAPI api = new RestAPI();
            try
            {
                JSONObject jsonObj = api.GetPublicacion();
                JSONParser parser = new JSONParser();
                deptTable = parser.parseDepa(jsonObj);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncLoadPub", e.getMessage());
                Toast.makeText(getApplicationContext(),"Error desde el AsyncTask",Toast.LENGTH_SHORT).show();
            }

            return deptTable;
        }

        @Override
        protected void onPostExecute(ArrayList<AlumnoModelo> result) {
            // TODO Auto-generated method stub

            for (int i = 0; i < result.size(); i++) {
                data.add(result.get(i).getDepartamento()
                        + "\n" + result.get(i).getDescripcion()
                        + "\n" + result.get(i).getEnlace()
                        + "\n" + result.get(i).getFecha()
                        + "\n" + result.get(i).getCorreo());
            }
            adapter.notifyDataSetChanged();
            Toast.makeText(context,"Loading Completed",Toast.LENGTH_SHORT).show();
        }
    }*/
}
