package com.example.carloz.gymapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import org.json.JSONException;
import org.json.JSONObject;

public class AdminVisualizarQuejaInfo extends AppCompatActivity {

    String ID, registroo,estado;

    TextView txtvNombre, txtvRegistro, txtvQueja,Titulo;
    ImageView imgvFoto;
    Button btnRealizado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_visualizar_queja_info);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Titulo = (TextView) findViewById(R.id.txtvNoActionAgregar_AdminModificarUsuario);
        txtvNombre = (TextView) findViewById(R.id.txtvNombre_AdminVisualizarQuejaInfo);
        txtvQueja = (TextView) findViewById(R.id.txtvQuejaa_AdminVisualizarQuejaInfo);
        txtvRegistro = (TextView) findViewById(R.id.txtvRegistro_AdminVisualizarQuejaInfo);
        imgvFoto = (ImageView) findViewById(R.id.imgvFoto_AdminVisualizarQuejaInfo);
        btnRealizado = (Button) findViewById(R.id.btnRealizado_AdminVisualizarQuejaInfo);

        Typeface Thin = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Thin.ttf");
        Titulo.setTypeface(Thin);

        ID = getIntent().getStringExtra("ID");
        registroo = getIntent().getStringExtra("REGISTRO");
        estado = getIntent().getStringExtra("ESTADO");

        if (estado.equals("Realizado")) {
            btnRealizado.setVisibility(View.INVISIBLE);
        }

        int registro = Integer.parseInt(registroo);
        if (registro>=1000 && registro<3000){
            Glide.with(this).load("http://thegymlife.online/php/fotos/imagenesClientes/"+registro+".jpg").apply(RequestOptions.skipMemoryCacheOf(true))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).error(R.drawable.logonb).into(imgvFoto);
            conexionBDCliente();
        }else if (registro>=3000 && registro<4000){
            Glide.with(this).load("http://thegymlife.online/php/fotos/imagenesInstructor/"+registro+".jpg").apply(RequestOptions.skipMemoryCacheOf(true))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).error(R.drawable.logonb).into(imgvFoto);
            conexionBDEntre();
        }else if (registro>=4000 && registro<5000){
            Glide.with(this).load("http://thegymlife.online/php/fotos/imagenesNutriologo/"+registro+".jpg").apply(RequestOptions.skipMemoryCacheOf(true))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).error(R.drawable.logonb).into(imgvFoto);
            conexionBDNutri();
        }

        btnRealizado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conexionBDRealizado();
            }
        });

    }

    private void conexionBDRealizado() {
        String url = "http://thegymlife.online/php/admin/Administrador_Queja_Realizada.php?queja="+ID;
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
                                    switch (valor){
                                        case "OK":
                                            Toast.makeText(AdminVisualizarQuejaInfo.this, "Queja actualizada", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(AdminVisualizarQuejaInfo.this,admin_menu.class);
                                            startActivity(intent);
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
                        Toast.makeText(AdminVisualizarQuejaInfo.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(AdminVisualizarQuejaInfo.this);
        x.add(peticion);

    }


    private void conexionBDCliente() {
        String url = "http://thegymlife.online/php/admin/Administrador_Visualizar_Cliente_Queja.php?queja="+ID;
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
                                    String Cliente_Nombre = response.getString("Cliente_Nombre");
                                    String bdApellido = response.getString("Cliente_Apellido");
                                    String bdRegistro = response.getString("Cliente_Registro");
                                    String bdQueja = response.getString("Queja_Descripcion");

                                    txtvNombre.setText(Cliente_Nombre+" "+bdApellido);
                                    txtvRegistro.setText(bdRegistro);
                                    txtvQueja.setText(bdQueja);

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
                        Toast.makeText(AdminVisualizarQuejaInfo.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(AdminVisualizarQuejaInfo.this);
        x.add(peticion);

    }
    private void conexionBDNutri() {
        String url = "http://thegymlife.online/php/admin/Administrador_Visualizar_Nutriologo_Queja.php?queja="+ID;
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
                                    String Cliente_Nombre = response.getString("Nutriologo_Nombre");
                                    String bdApellido = response.getString("Nutriologo_Apellido");
                                    String bdRegistro = response.getString("Nutriologo_Registro");
                                    String bdQueja = response.getString("Queja_Descripcion");

                                    txtvNombre.setText(Cliente_Nombre+" "+bdApellido);
                                    txtvRegistro.setText(bdRegistro);
                                    txtvQueja.setText(bdQueja);


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
                        Toast.makeText(AdminVisualizarQuejaInfo.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(AdminVisualizarQuejaInfo.this);
        x.add(peticion);

    }
    private void conexionBDEntre() {
        String url = "http://thegymlife.online/php/admin/Administrador_Visualizar_Entrenador_Queja.php?queja="+ID;
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
                                    String Cliente_Nombre = response.getString("Entrenador_Nombre");
                                    String bdApellido = response.getString("Entrenador_Apellido");
                                    String bdRegistro = response.getString("Entrenador_Registro");
                                    String bdQueja = response.getString("Queja_Descripcion");

                                    txtvNombre.setText(Cliente_Nombre+" "+bdApellido);
                                    txtvRegistro.setText(bdRegistro);
                                    txtvQueja.setText(bdQueja);


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
                        Toast.makeText(AdminVisualizarQuejaInfo.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(AdminVisualizarQuejaInfo.this);
        x.add(peticion);

    }
}
