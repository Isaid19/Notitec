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
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CrearPublicacionActivity extends AppCompatActivity
{


    private Button Enviar;
    private EditText Descripcion, Enlace, Correo;
    //private ImageView Foto;
    private Spinner spin;
    private TextView Departamento;
    int notificationId = 12345;

    OkHttpClient mClient = new OkHttpClient();
    String refreshedToken = FirebaseInstanceId.getInstance().getToken();//add your user refresh tokens who are logged in with firebase.
    JSONArray jsonArray = new JSONArray();


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
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getApplicationContext(), "Publicando mensaje... ", Toast.LENGTH_SHORT).show();
        }

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
            startActivity(i);

            jsonArray.put(refreshedToken);
            //jsonArray.put("/topics/all");

            String departamento = Departamento.getText().toString();
            String descripcion = Descripcion.getText().toString();
            sendMessage(/*jsonArray,*/departamento,descripcion,"....");
            Toast.makeText(getApplicationContext(), "Mensaje publicado.", Toast.LENGTH_SHORT).show();

            finish();
        }
    }



    public void sendMessage(/*final JSONArray recipients, */final String title, final String body, final String message)
    {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    JSONObject root = new JSONObject();
                    JSONObject notification = new JSONObject();
                    notification.put("body", body);
                    notification.put("title", title);

                    JSONObject data = new JSONObject();
                    data.put("message", message);
                    //root.put("registration_ids", recipients);
                    root.put("to","/topics/all");
                    root.put("notification", notification);
                    root.put("data", data);
                    String result = postToFCM(root.toString());
                    Log.d("Main Activity", "Result: " + result);
                    return result;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                try {
                    JSONObject resultJson = new JSONObject(result);
                    int success, failure;
                    success = resultJson.getInt("success");
                    failure = resultJson.getInt("failure");
                    //Toast.makeText(getApplicationContext(), "Message Success: " + success + "Message Failed: " + failure, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(), "Message Failed, Unknown error occurred.", Toast.LENGTH_LONG).show();
                }
            }
        }.execute();
    }

    String postToFCM(String bodyString) throws IOException
    {
        //String serverKey= "AAAAAjxlJag:APA91bGdphBQfeP8VrGG9AIOLB4U2kXeJB3mByq-rIbJUzPqJwK01V_h6NFlgrxIWqKNvISdfYfFfeW0nYSHbxadA9cV9B3zMOzAlOmeGBvBty2qFaVxOxCZ5mqFm-DOJdUo63Lurltm";
        String serverKey = "AAAAVZiwF9E:APA91bHfdaWlFy5K5hrL6yrwWfTBsGp2Bhay-wX15c2wxP7iQYx8vosJXrpPMlxTi5wWECakRS4XGavBHqYr2GT_9Pnj_2KYa_Bug3p95mZa1C2uuikD1nUjdKhvj1pfDJr13Sbve8z2";

        final String FCM_MESSAGE_URL = "https://fcm.googleapis.com/fcm/send";
        final MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, bodyString);
        Request request = new Request.Builder()
                .url(FCM_MESSAGE_URL)
                .post(body)
                .addHeader("Authorization", "key=" + serverKey)
                .build();
        Response response = mClient.newCall(request).execute();
        return response.body().string();
    }



}
