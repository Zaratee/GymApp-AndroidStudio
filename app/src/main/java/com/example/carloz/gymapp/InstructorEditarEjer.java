package com.example.carloz.gymapp;

import android.content.Intent;
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

public class InstructorEditarEjer extends AppCompatActivity {

    ZoomInImageView imgvEjercicio;
    TextView txtvNombre,txtvArea,txtvAparato,txtvDescripcion;
    EditText etxtRepeticiones, etxtSeries, etxtPeso;
    Button btnAsignar, btnEliminar;

    String ID,Registro,IDEjercicio, Series,Repeticiones, Peso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_editar_ejer);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        txtvNombre = (TextView) findViewById(R.id.txtvNombre_InstructorEditarEjer);
        txtvArea = (TextView) findViewById(R.id.txtvArea_InstructorEditarEjer);
        txtvAparato = (TextView) findViewById(R.id.txtvAparato_InstructorEditarEjer);
        txtvDescripcion = (TextView) findViewById(R.id.txtvDescripcion_InstructorEditarEjer);
        imgvEjercicio = (ZoomInImageView) findViewById(R.id.imgvEjercicio_InstructorEditarEjer);
        etxtRepeticiones = (EditText) findViewById(R.id.etxtRepeticiones_InstructorEditarEjer);
        etxtSeries = (EditText) findViewById(R.id.etxtSeries_InstructorEditarEjer);
        etxtPeso = (EditText) findViewById(R.id.etxtPeso_InstructorEditarEjer);
        btnAsignar = (Button) findViewById(R.id.btnAsignar_InstructorEditarEjer);
        btnEliminar = (Button) findViewById(R.id.btnEliminar_InstructorEditarEjer);

        ID = getIntent().getStringExtra("ID");
        IDEjercicio = getIntent().getStringExtra("EjerID");
        Series = getIntent().getStringExtra("SERIES");
        Repeticiones = getIntent().getStringExtra("REPETI");
        Peso = getIntent().getStringExtra("PESO");
        Registro = getIntent().getStringExtra("REGISTRO");

        etxtRepeticiones.setText(Repeticiones);
        etxtSeries.setText(Series);
        etxtPeso.setText(Peso);
        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        Glide.with(InstructorEditarEjer.this).load("http://thegymlife.online/php/fotos/imagenesEjercicio/"
                +IDEjercicio+".JPG").apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).error(R.drawable.logonb).into(imgvEjercicio);

        conexionBDEjercicioInfo();
        btnAsignar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conexionBDAsignar();
            }
        });
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conexionBDElimianr();
            }
        });
        
    }

    private void conexionBDElimianr() {
        String url = "http://thegymlife.online/php/instructor/Entrenador_Eliminar_Ejercicio_Cliente.php?rutina="+ID;
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

                                            Intent intent = new Intent(InstructorEditarEjer.this,InstructorEditarDia.class);
                                            intent.putExtra("REGISTRO",Registro);
                                            startActivity(intent);
                                            finish();
                                            break;
                                        case "Error":
                                            Toast.makeText(InstructorEditarEjer.this,"Ejercicio Eliminado",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(InstructorEditarEjer.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(InstructorEditarEjer.this);
        x.add(peticion);

    }

    private void conexionBDEjercicioInfo() {
        String url = "http://thegymlife.online/php/cliente/Cliente_Visualizar_Informacion_Ejercicio.php?ejercicio="+IDEjercicio;
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
                                            Toast.makeText(InstructorEditarEjer.this,"Error Conexion",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(InstructorEditarEjer.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(InstructorEditarEjer.this);
        x.add(peticion);

    }

    private void conexionBDAsignar() {
        String url = "http://thegymlife.online/php/instructor/Entrenador_Modificar_Ejercicios_Cliente.php?rutina="+ID+"&repeticiones="
                +etxtRepeticiones.getText().toString().trim()+"&series="+etxtSeries.getText().toString().trim()+
                "&peso="+etxtPeso.getText().toString().trim();
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
                                            Toast.makeText(InstructorEditarEjer.this,"Ejercicio modificado",Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(InstructorEditarEjer.this,InstructorEditarDia.class);
                                            intent.putExtra("REGISTRO",Registro);
                                            startActivity(intent);
                                            finish();
                                            break;

                                        case "Error":
                                            Toast.makeText(InstructorEditarEjer.this,"Error conexion",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(InstructorEditarEjer.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(InstructorEditarEjer.this);
        x.add(peticion);

    }
}
