package com.example.carloz.gymapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.zolad.zoominimageview.ZoomInImageView;

import org.json.JSONException;
import org.json.JSONObject;

public class InstructorAsignarEjercicioInfo extends AppCompatActivity {

    ZoomInImageView imgvEjercicio;
    TextView txtvNombre,txtvArea,txtvAparato,txtvDescripcion;
    EditText etxtRepeticiones, etxtSeries, etxtPeso;
    Button btnAsignar;

    String ID,registroCliente,dianum,Instructor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_asignar_ejercicio_info);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        txtvNombre = (TextView) findViewById(R.id.txtvNombre_InstructorAsignarEjercicioInfo);
        txtvArea = (TextView) findViewById(R.id.txtvArea_InstructorAsignarEjercicioInfo);
        txtvAparato = (TextView) findViewById(R.id.txtvAparato_InstructorAsignarEjercicioInfo);
        txtvDescripcion = (TextView) findViewById(R.id.txtvDescripcion_InstructorAsignarEjercicioInfo);
        imgvEjercicio = (ZoomInImageView) findViewById(R.id.imgvEjercicio_InstructorAsignarEjercicioInfo);
        etxtRepeticiones = (EditText) findViewById(R.id.etxtRepeticiones_InstructorAsignarEjercicioInfo);
        etxtSeries = (EditText) findViewById(R.id.etxtSeries_InstructorAsignarEjercicioInfo);
        btnAsignar = (Button) findViewById(R.id.btnAsignar_InstructorAsignarEjercicioInfo);
        etxtPeso = (EditText) findViewById(R.id.etxtPeso_InstructorAsignarEjercicioInfo);




        registroCliente = getIntent().getStringExtra("REGISTRO");
        SharedPreferences preferences = getSharedPreferences("Cuemta",Context.MODE_PRIVATE);
        Instructor = preferences.getString("instructor","hola");



        recibirDatos();
        conexionBDEjercicioInfo();
        Glide.with(InstructorAsignarEjercicioInfo.this).load("http://thegymlife.online/php/fotos/imagenesEjercicio/"
                +ID+".JPG").apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).error(R.drawable.logonb).into(imgvEjercicio);

        btnAsignar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conexionBDAsignar();
            }
        });
    }

    private void recibirDatos() {
        Intent intent= getIntent();
        Bundle bundle = intent.getExtras();
        ID = (String) bundle.get("ID");
        dianum = (String) bundle.get("DIANUM");
    }

    private void conexionBDAsignar() {
        String url = "http://thegymlife.online/php/instructor/Entrenador_Asignar_Rutina_Cliente.php?registro="+
                registroCliente+"&ejercicio="+ID+"&entrenador="+Instructor+"&repeticiones="+etxtRepeticiones.getText().toString().trim()
                +"&series="+etxtSeries.getText().toString().trim()+"&peso="+etxtPeso.getText().toString().trim()+"&dia="+dianum;
        url = url.replaceAll(" ", "%20");

        JsonObjectRequest peticion = new JsonObjectRequest
                (
                        Request.Method.GET,
                        url,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String valor = response.getString("Estado");
                                    Toast.makeText(InstructorAsignarEjercicioInfo.this, "1: "+valor, Toast.LENGTH_SHORT).show();
                                    switch(valor) {
                                        case "OK":
                                            Toast.makeText(InstructorAsignarEjercicioInfo.this,"Ejercicio asignado",Toast.LENGTH_SHORT).show();
                                            finish();
                                            break;

                                        case "Error":
                                            Toast.makeText(InstructorAsignarEjercicioInfo.this,"Error conexion",Toast.LENGTH_SHORT).show();
                                            break;
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        , new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(InstructorAsignarEjercicioInfo.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(InstructorAsignarEjercicioInfo.this);
        x.add(peticion);

    }




    private void conexionBDEjercicioInfo() {
        String url = "http://thegymlife.online/php/cliente/Cliente_Visualizar_Informacion_Ejercicio.php?ejercicio="+ID;
        url = url.replaceAll(" ", "%20");

        JsonObjectRequest peticion = new JsonObjectRequest
                (
                        Request.Method.GET,
                        url,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String valor = response.getString("Estado");
                                    switch(valor) {
                                        case "OK":
                                            String bdNombre = response.getString("Ejercicio_Nombre");
                                            String bdAparato = response.getString("Ejercicio_Aparato");
                                            String bdArea = response.getString("Ejercicio_Area");
                                            String bdDescripcion = response.getString("Ejercicio_Descripcion");

                                            txtvNombre.setText(bdNombre);
                                            txtvAparato.setText(bdAparato);
                                            txtvArea.setText(bdArea);
                                            txtvDescripcion.setText(bdDescripcion);
                                            break;

                                        case "Error":
                                            Toast.makeText(InstructorAsignarEjercicioInfo.this,"Error Conexion",Toast.LENGTH_SHORT).show();
                                            break;
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        , new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(InstructorAsignarEjercicioInfo.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(InstructorAsignarEjercicioInfo.this);
        x.add(peticion);

    }
}
