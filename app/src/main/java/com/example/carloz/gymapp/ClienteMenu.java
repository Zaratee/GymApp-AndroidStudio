package com.example.carloz.gymapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONException;
import org.json.JSONObject;

public class ClienteMenu extends AppCompatActivity {

    CardView btnQueja,btnEjercicio,btnAlimentacio;
    String stringCuenta, registroInstru, registroNutri, bdEstadoNutri2, bdEstadoInstru2, dato;

    CircularImageView btnPerfil;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        btnQueja = (CardView) findViewById(R.id.btnQuejas_ClienteMenu);
        btnEjercicio = (CardView) findViewById(R.id.btnEjercicio_ClienteMenu);
        btnAlimentacio = (CardView) findViewById(R.id.btnAlimentacion_ClienteMenu);

        btnPerfil = (CircularImageView) findViewById(R.id.imgvUsuarioAdmin_cliente_menu);


        SharedPreferences preferences = getSharedPreferences("Cuemta",Context.MODE_PRIVATE);
        String user = preferences.getString("usuario","nada");
        if (!user.equals("nada")){
            Login.Registro = user;
        }

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("cuenta","cliente");
        editor.putString("cliente",Login.Registro);
        editor.commit();

        Glide.with(ClienteMenu.this).load("http://thegymlife.online/php/fotos/imagenesClientes/"+Login.Registro+".jpg").apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).error(R.drawable.logonb).into(btnPerfil);

        auth = FirebaseAuth.getInstance();

        ClickbtnPerfil();
        ClickbtnAlimentacio();
        ClickbtnQueja();
        ClickbtnEjercicio();

        ConexionBDPerfil2();


        stringCuenta =Login.Registro;
    }

    private void ConexionBDPerfil2() {
        String url = "http://thegymlife.online/php/cliente/Cliente_Visualizar_Perfil.php?registro="+Login.Registro;

        JsonObjectRequest peticion = new JsonObjectRequest
                (
                        Request.Method.GET,
                        url,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {

                                    bdEstadoInstru2 = response.getString("Estado_Entrenador");
                                    bdEstadoNutri2 = response.getString("Estado_Nutriologo");
                                    registroInstru = response.getString("Cliente_Entrenador");
                                    registroNutri = response.getString("Cliente_Nutriologo");

                                    if (bdEstadoInstru2.equals("NUEVO") && bdEstadoNutri2.equals("OK")){
                                        dato="Instructor asignado";
                                        alertDialog();
                                    } else if (bdEstadoInstru2.equals("OK") && bdEstadoNutri2.equals("NUEVO")){
                                        dato="Nutriolgo asignado";
                                        alertDialog();
                                    }if (bdEstadoInstru2.equals("NUEVO") && bdEstadoNutri2.equals("NUEVO")){
                                        dato="Nutriolgo y asignado";
                                        alertDialog();
                                    }



                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            private void alertDialog() {
                                final AlertDialog.Builder alertbox = new AlertDialog.Builder(ClienteMenu.this);

                                alertbox.setMessage("Nuevo "+dato+" revisar perfil para mayor informacioón");
                                alertbox.setTitle("Monitoreo");



                                alertbox.setPositiveButton("Recibido",
                                        new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface arg0,
                                                                int arg1) {

                                            }
                                        });
                                alertbox.show();
                                Toast.makeText(ClienteMenu.this, ""+Login.Registro, Toast.LENGTH_SHORT).show();

                            }
                        }
                        , new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(ClienteMenu.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(ClienteMenu.this);
        x.add(peticion);

    }

    private void conexionFirebase() {
        String email = Login.Registro+"@gymlife.com", password = "GymLife";

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            Toast.makeText(ClienteMenu.this,"Complete todos los campos",Toast.LENGTH_SHORT).show();
        }else {
            auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(ClienteMenu.this, "Logeadoooo parse", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ClienteMenu.this,ClienteEjercicio.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("REGISTROINSTRU",registroInstru);
                                intent.putExtra("IDEJER","0");
                                startActivity(intent);
                            }else {
                                Toast.makeText(ClienteMenu.this,"Usuario no existe",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
    }
    private void conexionFirebase2() {
        String email = Login.Registro+"@gymlife.com", password = "GymLife";

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            Toast.makeText(ClienteMenu.this,"Complete todos los campos",Toast.LENGTH_SHORT).show();
        }else {
            auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(ClienteMenu.this, "Logeadoooo parse", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ClienteMenu.this,ClienteAlimentacion.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("REGISTRONUTRI",registroNutri);
                                startActivity(intent);
                            }else {
                                Toast.makeText(ClienteMenu.this,"Usuario no existe",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
    }


    private void ClickbtnPerfil() {
        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConexionBDPerfil();

            }
        });
    }
    private void ClickbtnQueja() {
        btnQueja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClienteMenu.this,ClienteBuzonQuejas.class);
                startActivity(intent);
            }
        });
    }
    private void ClickbtnEjercicio() {
        btnEjercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conexionFirebase();
                //Intent intent = new Intent(ClienteMenu.this,ClienteEjercicio.class);
                //startActivity(intent);
            }
        });
    }
    private void ClickbtnAlimentacio() {
        btnAlimentacio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conexionFirebase2();
            }
        });
    }

    private void ConexionBDPerfil() {

            String url = "http://thegymlife.online/php/cliente/Cliente_Visualizar_Perfil.php?registro="+stringCuenta;
            final Intent intentCliente = new Intent(ClienteMenu.this,ClientePerfil.class);
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
                                        String bdRegistro = response.getString("Cliente_Registro");
                                        String bdNombre = response.getString("Cliente_Nombre");
                                        String bdApellido = response.getString("Cliente_Apellido");
                                        String bdEdad = response.getString("Edad");
                                        String bdPeso = response.getString("Peso");
                                        String bdEstatura = response.getString("Estatura");
                                        String bdGrasaCorporal = response.getString("Grasa_Corporal");

                                        String nombreInst = response.getString("Entrenador_Nombre");
                                        String apellInst = response.getString("Entrenador_Apellido");
                                        String bdEstadoInstru = response.getString("Estado_Entrenador");
                                        String bdEstadoNutri = response.getString("Estado_Nutriologo");
                                        String nombreNutr = response.getString("Nutriologo_Nombre");
                                        String apellNutr = response.getString("Nutriologo_Apellido");

                                        switch(valor) {
                                            case "OK":
                                                intentCliente.putExtra("REGISTRO",stringCuenta);
                                                intentCliente.putExtra("BDREGISTRO",bdRegistro);
                                                intentCliente.putExtra("BDNOMBRE",bdNombre);
                                                intentCliente.putExtra("BDAPELLIDO",bdApellido);
                                                intentCliente.putExtra("BDEDAD",bdEdad);
                                                intentCliente.putExtra("BDPESO",bdPeso);
                                                intentCliente.putExtra("BDESTATURA",bdEstatura);
                                                intentCliente.putExtra("BDGRASA",bdGrasaCorporal);
                                                registroInstru = response.getString("Cliente_Entrenador");
                                                registroNutri = response.getString("Cliente_Nutriologo");
                                                intentCliente.putExtra("REGINST",registroInstru);
                                                intentCliente.putExtra("REGNUTRI",registroNutri);
                                                intentCliente.putExtra("INSTEST",bdEstadoInstru);
                                                intentCliente.putExtra("NUTRIEST",bdEstadoNutri);
                                                intentCliente.putExtra("INSTAPELL",apellInst);
                                                intentCliente.putExtra("INSTNOMB",nombreInst);
                                                intentCliente.putExtra("NUTRNOMB",nombreNutr);
                                                intentCliente.putExtra("NUTRAPELL",apellNutr);


                                                startActivity(intentCliente);
                                                break;
                                            case "Error":
                                                Toast.makeText(ClienteMenu.this,"Fallo de conexión",Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(ClienteMenu.this,"Error php",Toast.LENGTH_SHORT).show();
                        }
                    });
            RequestQueue x = Volley.newRequestQueue(ClienteMenu.this);
            x.add(peticion);

        }

    public void onBackPressed(){
        moveTaskToBack(true);
    }
}
