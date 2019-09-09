package com.example.carloz.gymapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
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
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONException;
import org.json.JSONObject;

public class AdminModificarSelecUsuario extends AppCompatActivity {

    String Cuenta;
    CircularImageView imgvUsuario;
    TextView txtvNANombre, txtvNARegistro, txtvNARFID, txtvNAHorario, txtvNATelefono, txtvNombre, txtvRegistro,
            txtvRFID, txtvHorario, txtvTelefono, txtvBTNActualizarContra, txtvBTNModificarDatos;
    CardView crdvModificar, crdvContraseña;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_modificar_selec_usuario);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        imgvUsuario = (CircularImageView) findViewById(R.id.imgvUsuario_AdminModificarSelecUsuario);

        txtvNANombre = (TextView) findViewById(R.id.txtvNoActionNombre_AdminModificarSelecUsuario);
        txtvNARegistro = (TextView) findViewById(R.id.txtvNoActionRegistro_AdminModificarSelecUsuario);
        txtvNARFID = (TextView) findViewById(R.id.txtvNoActionRFID_AdminModificarSelecUsuario);
        txtvNAHorario = (TextView) findViewById(R.id.txtvNoActionHorario_AdminModificarSelecUsuario);
        txtvNATelefono = (TextView) findViewById(R.id.txtvNoActionTelefono_AdminModificarSelecUsuario);

        txtvNombre = (TextView) findViewById(R.id.txtvNombre_AdminModificarSelecUsuario);
        txtvRegistro = (TextView) findViewById(R.id.txtvRegistro_AdminModificarSelecUsuario);
        txtvRFID = (TextView) findViewById(R.id.txtvRFID_AdminModificarSelecUsuario);
        txtvHorario = (TextView) findViewById(R.id.txtvHorario_AdminModificarSelecUsuario);
        txtvTelefono = (TextView) findViewById(R.id.txtvTelefono_AdminModificarSelecUsuario);

        txtvBTNActualizarContra = (TextView) findViewById(R.id.txtvActualizarContra_AdminModificarSelecUsuario);
        txtvBTNModificarDatos = (TextView) findViewById(R.id.txtvCerrarSesion_AdminModificarSelecUsuario);

        crdvModificar = (CardView) findViewById(R.id.cardvModificarDatos_AdminModificarSelecUsuario);
        crdvContraseña = (CardView) findViewById(R.id.cardvActualizarContra_AdminModificarSelecUsuario);

        Typeface Light = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Light.ttf");
        Typeface Regular = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");
        Typeface Condensed = Typeface.createFromAsset(getAssets(),"fonts/RobotoCondensed-Light.ttf");

        txtvNANombre.setTypeface(Regular);
        txtvNARegistro.setTypeface(Regular);
        txtvNARFID.setTypeface(Regular);
        txtvNAHorario.setTypeface(Regular);
        txtvNATelefono.setTypeface(Regular);

        txtvNombre.setTypeface(Light);
        txtvRegistro.setTypeface(Light);
        txtvRFID.setTypeface(Light);
        txtvHorario.setTypeface(Light);
        txtvTelefono.setTypeface(Light);

        txtvBTNActualizarContra.setTypeface(Condensed);
        txtvBTNModificarDatos.setTypeface(Condensed);


        Toast.makeText(this,""+getIntent().getStringExtra("REGISTRO"),Toast.LENGTH_SHORT).show();
        Cuenta = getIntent().getStringExtra("REGISTRO");
        conexionBDInfoUsuario();

        crdvModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCliente = new Intent(AdminModificarSelecUsuario.this,AdminModificarDatosCliente.class);
                Intent intentNutriologo = new Intent(AdminModificarSelecUsuario.this,AdminModificarDatosNutriologo.class);
                Intent intentInstructor = new Intent(AdminModificarSelecUsuario.this,AdminModificarDatosInstructor.class);

                int registro  = Integer.parseInt(Cuenta);
                if (registro>=1000 && registro<3000){
                    intentCliente.putExtra("REGISTRO",""+registro);
                    startActivity(intentCliente);
                }else if (registro>=3000 && registro<4000){
                    intentInstructor.putExtra("REGISTRO",""+registro);
                    startActivity(intentInstructor);
                }else if (registro>=4000 && registro<5000){
                    intentNutriologo.putExtra("REGISTRO",""+registro);
                    startActivity(intentNutriologo);
                }
            }
        });
        crdvContraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminModificarSelecUsuario.this,ActualizarContrasena.class);
                int registro  = Integer.parseInt(Cuenta);
                if (registro>=1000 && registro<3000){
                    intent.putExtra("CUENTA","ModificarCliente");
                    intent.putExtra("REGISTRO",""+registro);
                    startActivity(intent);
                }else if (registro>=3000 && registro<4000){
                    intent.putExtra("CUENTA","ModificarInstructor");
                    intent.putExtra("REGISTRO",""+registro);
                    startActivity(intent);
                }else if (registro>=4000 && registro<5000){
                    intent.putExtra("CUENTA","ModificarNutriologo");
                    intent.putExtra("REGISTRO",""+registro);
                    startActivity(intent);
                }
            }
        });
    }

    private void conexionBDInfoUsuario() {
        int registro  = Integer.parseInt(Cuenta);
        if (registro>=1000 && registro<3000){
            Glide.with(AdminModificarSelecUsuario.this).load("http://thegymlife.online/php/fotos/imagenesClientes/"+Cuenta+".jpg").apply(RequestOptions.skipMemoryCacheOf(true))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).error(R.drawable.logonb).into(imgvUsuario);
            conexionBDInfoCliente();
        }else if (registro>=3000 && registro<4000){
            txtvNARFID.setVisibility(View.INVISIBLE);
            txtvRFID.setVisibility(View.INVISIBLE);
            Glide.with(AdminModificarSelecUsuario.this).load("http://thegymlife.online/php/fotos/imagenesInstructor/"+Cuenta+".jpg").apply(RequestOptions.skipMemoryCacheOf(true))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).error(R.drawable.logonb).into(imgvUsuario);
            conexionBDInfoInstructor();
        }else if (registro>=4000 && registro<5000){
            txtvNARFID.setVisibility(View.INVISIBLE);
            txtvRFID.setVisibility(View.INVISIBLE);
            Glide.with(AdminModificarSelecUsuario.this).load("http://thegymlife.online/php/fotos/imagenesNutriologo/"+Cuenta+".jpg").apply(RequestOptions.skipMemoryCacheOf(true))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).error(R.drawable.logonb).into(imgvUsuario);
            conexionBDInfoNutriologo();
        }
    }

    private void conexionBDInfoCliente() {

            String url = "http://thegymlife.online/php/admin/Administrador_Visualizar_Modificar_Cliente_Datos.php?registro="+Cuenta;
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
                                        String bdRegistro = Cuenta;
                                        String bdNombre = response.getString("Cliente_Nombre");
                                        String bdApellido = response.getString("Cliente_Apellido");
                                        String bdHorario = response.getString("Cliente_Horario");
                                        String bdTelefono = response.getString("Cliente_Telefono");
                                        String bdRFID = response.getString("Cliente_RFID");

                                        switch(valor) {
                                            case "OK":

                                                txtvNombre.setText(bdNombre+" "+bdApellido);
                                                txtvRegistro.setText(bdRegistro);
                                                txtvRFID.setText(bdRFID);
                                                if (bdHorario.equals("0")){
                                                    txtvHorario.setText("Matutino");
                                                } else if (bdHorario.equals("1")){
                                                    txtvHorario.setText("Vespertino");
                                                }
                                                txtvTelefono.setText(bdTelefono);

                                                break;
                                            case "ERROR":
                                                Toast.makeText(AdminModificarSelecUsuario.this,"Fallo de conexión",Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(AdminModificarSelecUsuario.this,"Error conexion",Toast.LENGTH_SHORT).show();
                        }
                    });
            RequestQueue x = Volley.newRequestQueue(AdminModificarSelecUsuario.this);
            x.add(peticion);


    }

    private void conexionBDInfoInstructor() {

        String url = "http://thegymlife.online/php/admin/Administrador_Visualizar_Modificar_Entrenador_Datos.php?registro="+Cuenta;
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
                                    String bdRegistro = Cuenta;
                                    String bdNombre = response.getString("Entrenador_Nombre");
                                    String bdApellido = response.getString("Entrenador_Apellido");
                                    String bdHorario = response.getString("Entrenador_Horario");
                                    String bdTelefono = response.getString("Entrenador_Telefono");


                                    switch(valor) {
                                        case "OK":

                                            txtvNombre.setText(bdNombre+" "+bdApellido);
                                            txtvRegistro.setText(bdRegistro);
                                            if (bdHorario.equals("0")){
                                                txtvHorario.setText("Matutino");
                                            } else if (bdHorario.equals("1")){
                                                txtvHorario.setText("Vespertino");
                                            }
                                            txtvTelefono.setText(bdTelefono);

                                            break;
                                        case "ERROR":
                                            Toast.makeText(AdminModificarSelecUsuario.this,"Fallo de conexión",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(AdminModificarSelecUsuario.this,"Error conexion",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(AdminModificarSelecUsuario.this);
        x.add(peticion);


    }

    private void conexionBDInfoNutriologo() {

        String url = "http://thegymlife.online/php/admin/Administrador_Visualizar_Modificar_Nutriologo_Datos.php?registro="+Cuenta;
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
                                    String bdRegistro = Cuenta;
                                    String bdNombre = response.getString("Nutriologo_Nombre");
                                    String bdApellido = response.getString("Nutriologo_Apellido");
                                    String bdHorario = response.getString("Nutriologo_Horario");
                                    String bdTelefono = response.getString("Nutriologo_Telefono");


                                    switch(valor) {
                                        case "OK":

                                            txtvNombre.setText(bdNombre+" "+bdApellido);
                                            txtvRegistro.setText(bdRegistro);
                                            if (bdHorario.equals("0")){
                                                txtvHorario.setText("Matutino");
                                            } else if (bdHorario.equals("1")){
                                                txtvHorario.setText("Vespertino");
                                            }
                                            txtvTelefono.setText(bdTelefono);

                                            break;
                                        case "ERROR":
                                            Toast.makeText(AdminModificarSelecUsuario.this,"Fallo de conexión",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(AdminModificarSelecUsuario.this,"Error conexion",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(AdminModificarSelecUsuario.this);
        x.add(peticion);


    }

}
