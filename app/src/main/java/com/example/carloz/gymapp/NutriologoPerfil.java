package com.example.carloz.gymapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.example.carloz.gymapp.adaptador.AdaptadorInstructorClientesAsignados;
import com.example.carloz.gymapp.items.ItemClienteInstructor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NutriologoPerfil extends AppCompatActivity {

    CircularImageView imgvUsuario;
    String stringCuenta, nombre, apellido, registro;
    ArrayList<ItemClienteInstructor> listaClientes;
    RecyclerView recyclerClientes;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutriologo_perfil);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        imgvUsuario = (CircularImageView) findViewById(R.id.imgvUsuario_NutriologoPerfil);


        auth = FirebaseAuth.getInstance();

        listaClientes = new ArrayList<>();

        recyclerClientes = (RecyclerView) findViewById(R.id.listvClientes_NutriologoPerfil);
        recyclerClientes.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        SharedPreferences preferences = getSharedPreferences("Cuemta",Context.MODE_PRIVATE);
        String user = preferences.getString("usuario","nada");
        //Toast.makeText(this, "reg: "+user, Toast.LENGTH_SHORT).show();
        if (!user.equals("nada")){
            Login.Registro = user;
        }
        stringCuenta = Login.Registro;

        Glide.with(NutriologoPerfil.this).load("http://thegymlife.online/php/fotos/imagenesNutriologo/"
                +Login.Registro+".jpg").error(R.drawable.logonb).apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).error(R.drawable.logonb).into(imgvUsuario);

        clickImgPerfil();
        //conexionFirebase();
        conexionBDListaUsuarios();
    }





    private void conexionBDListaUsuarios() {

            String url = "http://thegymlife.online/php/nutriologo/Nutriologo_Lista_Clientes_Asignado.php?registro="+stringCuenta;
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
                                        //String valor = response.getString("Estado");
                                        JSONArray jsonArray = response.getJSONArray("Clientes");

                                        AdaptadorInstructorClientesAsignados adapter = new AdaptadorInstructorClientesAsignados(listaClientes);
                                        adapter.contexto= NutriologoPerfil.this;
                                        for (int i =0; i<jsonArray.length();i++){
                                            JSONObject clientes = jsonArray.getJSONObject(i);
                                            registro =clientes.getString("Cliente_Registro");
                                            nombre =clientes.getString("Cliente_Nombre");
                                            apellido =clientes.getString("Cliente_Apellido");

                                            listaClientes.add(new ItemClienteInstructor(nombre,registro,apellido,"0"));


                                        }

                                        recyclerClientes.setAdapter(adapter);

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
                            Toast.makeText(NutriologoPerfil.this,"Error php",Toast.LENGTH_SHORT).show();
                        }
                    });
            RequestQueue x = Volley.newRequestQueue(NutriologoPerfil.this);
            x.add(peticion);

    }

    private void clickImgPerfil() {
            imgvUsuario.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String url = "http://thegymlife.online/php/nutriologo/Nutriologo_Visualizar_Perfil.php?registro="+Login.Registro;
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
                                                String bdRegistro = response.getString("Nutriologo_Registro");
                                                String bdNombre = response.getString("Nutriologo_Nombre");
                                                String bdApellido = response.getString("Nutriologo_Apellido");
                                                switch(valor) {

                                                    case "OK":


                                                        AlertDialog.Builder mbuilder = new AlertDialog.Builder(NutriologoPerfil.this);
                                                        AlertDialog dialog;

                                                        View view = getLayoutInflater().inflate(R.layout.perfilnutriologo, null);

                                                        CircularImageView imgvPerfil = (CircularImageView) view.findViewById(R.id.imgvUsuario_Perfil);

                                                        TextView txtvNombre = (TextView) view.findViewById(R.id.txtvNombre_Perfil);
                                                        TextView txtvApellido = (TextView) view.findViewById(R.id.txtvApellido_Perfil);
                                                        TextView txtvRegistro = (TextView) view.findViewById(R.id.txtvRegistro_Perfil);
                                                        TextView txtvNANombre = (TextView) view.findViewById(R.id.txtvNoActionNombre_Perfil);
                                                        TextView txtvNARegistro = (TextView) view.findViewById(R.id.txtvNoActionRegistro_Perfil);

                                                        TextView txtvSolicitudAlimen = (TextView) view.findViewById(R.id.txtvProgreso_Perfil);
                                                        TextView txtvCerrarSesion = (TextView) view.findViewById(R.id.txtvCerrarSesion_Perfil);
                                                        TextView txtvQuejas = (TextView) view.findViewById(R.id.txtvQuejas_Perfil);
                                                        TextView txtvActulizarContra = (TextView) view.findViewById(R.id.txtvActualizarContra_Perfil);

                                                        CardView btnSolicitudAlim = (CardView) view.findViewById(R.id.cardvSolicitudAlimento_Perfil);
                                                        CardView btnCerrarSesion = (CardView) view.findViewById(R.id.cardvCerrarSesion_Perfil);
                                                        CardView btnActualizarContra = (CardView) view.findViewById(R.id.cardvActualizarContra_Perfil);
                                                        CardView btnQuejas = (CardView) view.findViewById(R.id.cardvQuejas_Perfil);

                                                        Typeface Thin = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Thin.ttf");
                                                        Typeface Light = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Light.ttf");
                                                        Typeface Regular = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");
                                                        Typeface Condensed = Typeface.createFromAsset(getAssets(),"fonts/RobotoCondensed-Light.ttf");

                                                        txtvSolicitudAlimen.setTypeface(Condensed);
                                                        txtvCerrarSesion.setTypeface(Condensed);
                                                        txtvQuejas.setTypeface(Condensed);
                                                        txtvActulizarContra.setTypeface(Condensed);

                                                        txtvNANombre.setTypeface(Regular);
                                                        txtvNARegistro.setTypeface(Regular);

                                                        txtvNombre.setTypeface(Light);
                                                        txtvApellido.setTypeface(Light);
                                                        txtvRegistro.setTypeface(Light);

                                                        txtvNombre.setText(bdNombre);
                                                        txtvApellido.setText(bdApellido);
                                                        txtvRegistro.setText(bdRegistro);

                                                        Glide.with(NutriologoPerfil.this).load("http://thegymlife.online/php/fotos/imagenesNutriologo/"+Login.Registro+".jpg")
                                                                .error(R.drawable.logonb).apply(RequestOptions.skipMemoryCacheOf(true))
                                                                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).error(R.drawable.logonb).into(imgvPerfil);

                                                        btnSolicitudAlim.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                Intent intent = new Intent(NutriologoPerfil.this,NutriologoSolicitudAlimentoLista.class);
                                                                startActivity(intent);

                                                            }
                                                        });
                                                        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                Intent intent = new Intent(NutriologoPerfil.this,Login.class);

                                                                SharedPreferences preferences = getSharedPreferences("Cuemta",Context.MODE_PRIVATE);

                                                                SharedPreferences.Editor editor = preferences.edit();
                                                                editor.putString("usuario","nada");
                                                                editor.putString("contrasena","nada");
                                                                editor.putString("cuenta","nada");
                                                                editor.commit();

                                                                startActivity(intent);
                                                                finish();
                                                            }
                                                        });
                                                        btnActualizarContra.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                Intent intent = new Intent(NutriologoPerfil.this,ActualizarContrasena.class);
                                                                String cuenta = "Nutriólogo";
                                                                intent.putExtra("CUENTA", cuenta);
                                                                intent.putExtra("REGISTRO",Login.Registro);
                                                                startActivity(intent);

                                                            }
                                                        });

                                                        btnQuejas.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                Intent intent = new Intent(NutriologoPerfil.this,NutriologoBuzonQuejas.class);
                                                                startActivity(intent);
                                                            }
                                                        });

                                                        mbuilder.setView(view);
                                                        dialog = mbuilder.create();
                                                        dialog.show();

                                                        break;
                                                    case "Error":
                                                        Toast.makeText(NutriologoPerfil.this,"Error Conexion",Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(NutriologoPerfil.this,"Error Conexión",Toast.LENGTH_SHORT).show();

                                }
                            });
                    RequestQueue x = Volley.newRequestQueue(NutriologoPerfil.this);
                    x.add(peticion);

                }

            });

    }

    public void onBackPressed(){
    }
}
