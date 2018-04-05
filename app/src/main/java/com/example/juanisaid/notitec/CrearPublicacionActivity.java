package com.example.juanisaid.notitec;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CrearPublicacionActivity extends AppCompatActivity
{

    private Button Enviar,Examinar;
    private EditText Descripcion, Enlace, Correo;
    //private ImageView Foto;
    private Spinner spin;
    private TextView Departamento;
    int notificationId = 12345;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_publicacion);

        Enviar=(Button)findViewById(R.id.btnEnviarPubicacion);
        Departamento=(TextView)findViewById(R.id.textView);
        Descripcion=(EditText)findViewById(R.id.editTextDescripcion);
        Enlace=(EditText)findViewById(R.id.editTextEnlace);
        Correo=(EditText)findViewById(R.id.editTextCorreo);
        spin=(Spinner)findViewById(R.id.spinner2);

        final String[] depa =
                {"Recursos Humanos","Servicios Escolares","Desarrollo Academico",
                        "División de Estudios Profesionales","Extra Escolares","Comunicación"
                        ,"Recursos Financieros","Planeación","Calidad","Mantenimiento y Equipo",
                        "Centro de información","Dirección","Sub-Dirección","Centro de Computo"
                        ,"Gestión Tecnológica y Vinculación", "Recursos Materiales y de Servicios",
                        "Sistemas Computacionales","Ing. Industrial","Ing. Mecanica","Ing. Eléctrica"
                        ,"Ing. Electrónica","Ing. Mecatrónica","Ciencias Económico Administrativas", "Ciencias de la Tierra"};

        spin.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,depa));

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
            {
                //String text = spin.getSelectedItem().toString();Departamento.setText(text);
                Departamento.setText(spin.getSelectedItem().toString());
                //Toast.makeText(adapterView.getContext(), (String) adapterView.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                // vacio

            }
        });

        Enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String departamento = Departamento.getText().toString();
                String descripcion = Descripcion.getText().toString();
                String enlace=Enlace.getText().toString();
                String fecha = getDateTime();
                String email = Correo.getText().toString();
                //email = dato;
                //int foto=Foto.getImageAlpha();

                PublicacionModelo userDetail = new PublicacionModelo(departamento,
                        descripcion, enlace,fecha,email);
                new AsyncCrearPublicacion().execute(userDetail);
                /*if(validarEmail(email)==true) {


                    //new WSInsertar().execute(departamento,descripcion,enlace,email);
                    //agregarUsuario();

                    //Notificación Push



                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Correo no aceptado",Toast.LENGTH_LONG).show();
                }*/


            }
        });

    }
    public static boolean validarEmail(String email){

        boolean isValid = false;
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }


    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(); return dateFormat.format(date); }

    protected class AsyncCrearPublicacion extends AsyncTask<PublicacionModelo, Void,Void>
    {

        @Override
        protected Void doInBackground(PublicacionModelo... params) {

            WebServiceRest api = new WebServiceRest();
            //RestAPI api = new RestAPI();
            try {

                api.CrearPublicacion(params[0].getDepartamento(), params[0].getDescripcion(), params[0].getEnlace(), params[0].getCorreo() /*,params[0].getFecha(), params[0].getFoto()*/);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncCreateUser", e.getMessage());

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Intent i=new Intent(getApplicationContext(),ListaPublicacionesUsuariosActivity.class);
            //i.putExtra("correo",Cor);
            startActivity(i);

            ///new AsyncCrearPublicacion().execute();
           /* Intent i=new Intent(getApplicationContext(),PublicacionActivity.class);
            //i.putExtra("correo",Cor);
            startActivity(i);*/

            String departamento = Departamento.getText().toString();
            String descripcion = Descripcion.getText().toString();
            int icono = R.mipmap.ic_launcher;
            NotificationCompat.Builder mBuilder;
            mBuilder = new NotificationCompat.Builder(getApplicationContext())
                    //.setContentIntent(pendingIntent)
                    .setSmallIcon(icono)
                    .setContentTitle(departamento)
                    .setContentText(descripcion)
            //.setVibrate(new long[] {100, 250, 100, 500})
            //.setAutoCancel(true)
            ;

            Intent intent = new Intent(getApplicationContext(), AlumnoActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            mBuilder.setContentIntent(pendingIntent);

            NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            mNotifyMgr.notify(notificationId, mBuilder.build());
            //mBuilder = new NotificationCompat.Builder(getApplicationContext());
            finish();
        }
    }

    ///Otra clase para insertar las publicaciones
    private class WSInsertar extends AsyncTask<String,Integer,Boolean> {

        protected Boolean doInBackground(String... params) {

            boolean resul = true;

            HttpClient httpClient = new DefaultHttpClient();

            HttpPost post = new
                    HttpPost("http://10.10.21.249/Api/Publicaciones/Publicacion");

            post.setHeader("content-type", "application/json");

            try
            {
                //Construimos el objeto cliente en formato JSON
                JSONObject dato = new JSONObject();

                dato.put("Departamento", params[0]);
                dato.put("Descripcion", params[1]);
                dato.put("Enlace", params[2]);
                dato.put("Fecha", params[3]);
                dato.put("CorreoElectronico", params[4]);

                StringEntity entity = new StringEntity(dato.toString());
                post.setEntity(entity);

                HttpResponse resp = httpClient.execute(post);
                String respStr = EntityUtils.toString(resp.getEntity());

                if(!respStr.equals("true"))
                    resul = false;
            }
            catch(Exception ex)
            {
                Log.e("ServicioRest","Error!", ex);
                resul = false;
            }

            return resul;
        }

        protected void onPostExecute(Boolean result) {

            if (result)
            {
                Intent i=new Intent(getApplicationContext(),ListaPublicacionesUsuariosActivity.class);
                //i.putExtra("correo",Cor);
                startActivity(i);
            }
        }
    }

}
