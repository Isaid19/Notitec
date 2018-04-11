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
import android.widget.TextView;
import android.widget.Toast;

//import com.loopj.android.http.HttpGet;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ListaPublicacionActivity extends AppCompatActivity
{
    private ListView Lv;
    private Button EnviarCorreo;
    private TextView tvPag;
    Context context;
    ArrayAdapter<String> adapter;
    ArrayList<String> data;
    String cadenaApi = "http://core.itnuevolaredo.edu.mx/notitec/Api/Publicaciones";
    //String cadenaApi = "http://192.168.1.76/PublicWebServiceRest1/Api/Publicaciones";
    String correoCopiado;
    String pagina;
    String textoPagina;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_publicacion);

        //data = new ArrayList<String>();
        Lv = (ListView) findViewById(R.id.lv);
        tvPag = (TextView) findViewById(R.id.tvPagina);
        context = this;

        //adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
       // Lv.setAdapter(adapter);
        Toast.makeText(this,"Cargando por favor espere...",Toast.LENGTH_SHORT).show();
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
        public List<String> notasListaPagina = new ArrayList<String>();
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
                notasListaPagina = new ArrayList<String>(respJSON.length());
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
                    notasListaPagina.add(""+link);
                    //correoCopiado = notasListaCorreos.set(i,correo);
	        	}
	        }

	        catch(Exception ex)
	        {
	        	Log.e("ServicioRest","Error!", ex);
	        	resul = false;
                Toast.makeText(getApplicationContext(),"Error " + ex.getMessage(),Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(ListaPublicacionActivity.this, "Correo: " + notasListaCorreos.get(position), Toast.LENGTH_LONG).show();
                        correoCopiado = notasListaCorreos.get(position);
                        pagina = notasListaPagina.get(position);
                        textoPagina = "Visitar página: "+ pagina;
                        tvPag.setText(textoPagina);
                    }
                });
	    	}
	    }
	}
}
