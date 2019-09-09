package com.example.carloz.gymapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
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
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class InstructorPerfil extends AppCompatActivity {

    CircularImageView imgvUsuario;
    String  nombre, apellido, registro, estado;
    ArrayList<ItemClienteInstructor> listaClientes;
    RecyclerView recyclerClientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_perfil);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        imgvUsuario = (CircularImageView) findViewById(R.id.imgvUsuario_InstructorPerfil);


        listaClientes = new ArrayList<>();

        recyclerClientes = (RecyclerView) findViewById(R.id.listvClientes_InstructorPerfil);
        recyclerClientes.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        SharedPreferences preferences = getSharedPreferences("Cuemta",Context.MODE_PRIVATE);
        String user = preferences.getString("usuario","nada");
        if (!user.equals("nada")){
            Login.Registro = user;
        }

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("instructor",Login.Registro);
        editor.commit();


        Glide.with(InstructorPerfil.this).load("http://thegymlife.online/php/fotos/imagenesInstructor/"
                +Login.Registro+".jpg").apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).error(R.drawable.logonb).into(imgvUsuario);

        clickImgPerfil();
        conexionBDListaUsuarios();

    }

    private void conexionBDListaUsuarios() {
        String url = "http://thegymlife.online/php/instructor/Entrenador_Lista_Clientes_Asignados.php?registro="+Login.Registro;
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
                                    adapter.contexto= InstructorPerfil.this;
                                    for (int i =0; i<jsonArray.length();i++){
                                        JSONObject clientes = jsonArray.getJSONObject(i);
                                        registro =clientes.getString("Cliente_Registro");
                                        nombre =clientes.getString("Cliente_Nombre");
                                        apellido =clientes.getString("Cliente_Apellido");
                                        estado =clientes.getString("Estado");

                                        listaClientes.add(new ItemClienteInstructor(nombre,registro,apellido,estado ));
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
                        Toast.makeText(InstructorPerfil.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(InstructorPerfil.this);
        x.add(peticion);

    }

    private void clickImgPerfil() {
        imgvUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String url = "http://thegymlife.online/php/instructor/Entrenador_Vizualizar_Perfil.php?registro="+Login.Registro;
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
                                            String bdRegistro = response.getString("Entrenador_Registro");
                                            String bdNombre = response.getString("Entrenador_Nombre");
                                            String bdApellido = response.getString("Entrenador_Apellido");
                                            switch(valor) {

                                                case "OK":

                                                    AlertDialog.Builder mbuilder = new AlertDialog.Builder(InstructorPerfil.this);
                                                    AlertDialog dialog;

                                                    View view = getLayoutInflater().inflate(R.layout.perfilinstructor, null);

                                                    CircularImageView imgvPerfil = (CircularImageView) view.findViewById(R.id.imgvUsuario_PerfilInstructor);

                                                    TextView txtvNombre = (TextView) view.findViewById(R.id.txtvNombre_PerfilInstructor);
                                                    TextView txtvApellido = (TextView) view.findViewById(R.id.txtvApellido_PerfilInstructor);
                                                    TextView txtvRegistro = (TextView) view.findViewById(R.id.txtvRegistro_PerfilInstructor);
                                                    TextView txtvNANombre = (TextView) view.findViewById(R.id.txtvNoActionNombre_PerfilInstructor);
                                                    TextView txtvNARegistro = (TextView) view.findViewById(R.id.txtvNoActionRegistro_PerfilInstructor);

                                                    TextView txtvCerrarSesion = (TextView) view.findViewById(R.id.txtvCerrarSesion_PerfilInstructor);
                                                    TextView txtvQuejas = (TextView) view.findViewById(R.id.txtvQuejas_PerfilInstructor);
                                                    TextView txtvActulizarContra = (TextView) view.findViewById(R.id.txtvActualizarContra_PerfilInstructor);

                                                    CardView btnCerrarSesion = (CardView) view.findViewById(R.id.cardvCerrarSesion_PerfilInstructor);
                                                    CardView btnActualizarContra = (CardView) view.findViewById(R.id.cardvActualizarContra_PerfilInstructor);
                                                    CardView btnQuejas = (CardView) view.findViewById(R.id.cardvQuejas_PerfilInstructor);

                                                    Typeface Light = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Light.ttf");
                                                    Typeface Regular = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");
                                                    Typeface Condensed = Typeface.createFromAsset(getAssets(),"fonts/RobotoCondensed-Light.ttf");


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

                                                    Glide.with(InstructorPerfil.this).load("http://thegymlife.online/php/fotos/imagenesInstructor/"+Login.Registro+".jpg").apply(RequestOptions.skipMemoryCacheOf(true))
                                                            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).error(R.drawable.logonb).into(imgvPerfil);
                                                    btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            Intent intent = new Intent(InstructorPerfil.this,Login.class);

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
                                                            Intent intent = new Intent(InstructorPerfil.this,ActualizarContrasena.class);
                                                            String cuenta = "Instructor";
                                                            intent.putExtra("CUENTA", cuenta);
                                                            intent.putExtra("REGISTRO",Login.Registro);
                                                            startActivity(intent);
                                                        }
                                                    });

                                                    btnQuejas.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            Intent intent = new Intent(InstructorPerfil.this,InstructorBuzonQuejas.class);
                                                            startActivity(intent);
                                                        }
                                                    });


                                                    mbuilder.setView(view);
                                                    dialog = mbuilder.create();
                                                    dialog.show();

                                                    break;
                                                case "Error":
                                                    Toast.makeText(InstructorPerfil.this,"Error Conexion",Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(InstructorPerfil.this,"Error Conexión",Toast.LENGTH_SHORT).show();

                            }
                        });
                RequestQueue x = Volley.newRequestQueue(InstructorPerfil.this);
                x.add(peticion);

            }

        });
    }

}
