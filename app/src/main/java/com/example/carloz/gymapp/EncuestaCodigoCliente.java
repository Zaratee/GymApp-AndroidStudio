package com.example.carloz.gymapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
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

import org.json.JSONException;
import org.json.JSONObject;

public class EncuestaCodigoCliente extends AppCompatActivity {

    TextView txtvNombre,txtvRegistro, txtvTitulo, txtvNANombre, txtvNARegistro, txtvApellido;
    EditText etxtCodigo;
    Button btnContinuar;
    String stringCuenta;
    ImageView imgvUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuesta_codigo_cliente);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        txtvNombre = (TextView) findViewById(R.id.txtvNombre_EncuestaCodigoCliente);
        txtvApellido = (TextView) findViewById(R.id.txtvApellido_EncuestaCodigoCliente);
        txtvRegistro = (TextView) findViewById(R.id.txtvRegistro_EncuestaCodigoCliente);
        etxtCodigo = (EditText) findViewById(R.id.etxtCodigo_EncuestaCodigoCliente);
        btnContinuar = (Button) findViewById(R.id.btnContinuar_EncuestaCodigoCliente);
        txtvNANombre = (TextView) findViewById(R.id.txtvNoActionNombre_EncuestaCodigoCliente);
        txtvNARegistro = (TextView) findViewById(R.id.txtvNoActionRegistro_EncuestaCodigoCliente);
        txtvTitulo = (TextView) findViewById(R.id.txtvNoActionTitulo_EncuestaCodigoCliente);

        imgvUsuario = (ImageView) findViewById(R.id.imgVInstructor_EncuestaCodigoCliente);

        Typeface Thin = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Thin.ttf");
        Typeface Light = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Light.ttf");
        Typeface Regular = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");
        Typeface Condensed = Typeface.createFromAsset(getAssets(),"fonts/RobotoCondensed-Light.ttf");

        txtvTitulo.setTypeface(Thin);
        txtvRegistro.setTypeface(Light);
        txtvNombre.setTypeface(Light);
        txtvApellido.setTypeface(Light);
        txtvNANombre.setTypeface(Regular);
        txtvNARegistro.setTypeface(Regular);
        btnContinuar.setTypeface(Condensed);

        SharedPreferences preferences = getSharedPreferences("Cuemta",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("cuenta","clienteEncuCodigo");
        editor.commit();

        String user = preferences.getString("usuario","nada");
        if (!user.equals("nada")){
            Login.Registro = user;
        }

        conexionBDInfoInstructor();
        recibirDatos();


        ClickbtnContinuar();
    }

    private void conexionBDInfoInstructor() {
        //Log.d("Error","inicioo");

        String url = "http://thegymlife.online/php/cliente/Cliente_Visualizar_Entrenador_Asignado.php?registro="+Login.Registro;
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
                                    String entrenadorNombre = response.getString("Entrenador_Nombre");
                                    String entrenadorApellido = response.getString("Entrenador_Apellido");
                                    String entrenadorRegistro = response.getString("Entrenador_Registro");


                                    txtvNombre.setText(entrenadorNombre);
                                    txtvApellido.setText(entrenadorApellido);
                                    txtvRegistro.setText(entrenadorRegistro);
                                    Glide.with(EncuestaCodigoCliente.this).load("http://thegymlife.online/php/fotos/imagenesInstructor/"
                                            +txtvRegistro.getText().toString()+".jpg").apply(RequestOptions.skipMemoryCacheOf(true))
                                            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).error(R.drawable.logonb).into(imgvUsuario);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EncuestaCodigoCliente.this, "Error php", Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(EncuestaCodigoCliente.this);
        x.add(peticion);

    }

    private void recibirDatos() {
        Intent intent= getIntent();
        Bundle bundle = intent.getExtras();
        stringCuenta = Login.Registro;

    }

    private void ClickbtnContinuar() {
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                {

                    if (etxtCodigo.equals("")){
                        etxtCodigo.setError("Rellenar Código");
                    }
                    //Log.d("Error","inicioo");

                    String url = "http://thegymlife.online/php/cliente/Cliente_Comparar_Codigo_Inicio.php?registro="+Login.Registro+"&codigo="+etxtCodigo.getText().toString();
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
                                                switch (valor) {
                                                    case "OK":
                                                        Intent intent = new Intent(EncuestaCodigoCliente.this, ClienteMenu.class);

                                                        startActivity(intent);

                                                        break;
                                                    case "ERROR":
                                                        Toast.makeText(EncuestaCodigoCliente.this, "Codigo no coincide", Toast.LENGTH_SHORT).show();

                                                }

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                    , new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(EncuestaCodigoCliente.this, "Error conexión", Toast.LENGTH_SHORT).show();
                                }
                            });
                    RequestQueue x = Volley.newRequestQueue(EncuestaCodigoCliente.this);
                    x.add(peticion);

                }
            }
        });
    }

    public void onBackPressed(){

    }
}
