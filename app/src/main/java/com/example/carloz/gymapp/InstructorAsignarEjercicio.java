package com.example.carloz.gymapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.carloz.gymapp.adaptador.AdaptadorEjercicio;
import com.example.carloz.gymapp.adaptador.AdaptadorInstructorClientesAsignados;
import com.example.carloz.gymapp.items.ItemClienteInstructor;
import com.example.carloz.gymapp.items.ItemEjercicio;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class InstructorAsignarEjercicio extends AppCompatActivity {

    TextView txtvMusculo;
    String stringMusculo;
    ArrayList<ItemEjercicio> listaClientes;
    String nombre, id;
    RecyclerView recyclerClientes;
    String registroCliente, dianum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_asignar_ejercicio);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        listaClientes = new ArrayList<>();

        recyclerClientes = (RecyclerView) findViewById(R.id.listvEjercicios_AsignarEjercicio);
        recyclerClientes.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        txtvMusculo = (TextView) findViewById(R.id.txtvMusculo_InstructorAsignarEjercicio);
        Typeface Thin = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Thin.ttf");
        txtvMusculo.setTypeface(Thin);
        registroCliente = getIntent().getStringExtra("REGISTRO");



        recibirdatos();
        Toast.makeText(this,""+dianum,Toast.LENGTH_SHORT).show();
        conexionBDEjercicios();

    }

    private void conexionBDEjercicios() {
        String url = "http://thegymlife.online/php/instructor/Entrenador_Lista_Ejercicios_Area.php?area="+stringMusculo;
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
                                    JSONArray jsonArray = response.getJSONArray("Ejercicios");

                                    AdaptadorEjercicio adapter = new AdaptadorEjercicio(listaClientes);
                                    adapter.contexto= InstructorAsignarEjercicio.this;
                                    for (int i =0; i<jsonArray.length();i++){
                                        JSONObject clientes = jsonArray.getJSONObject(i);
                                        nombre =clientes.getString("Ejercicio_Nombre");
                                        id =clientes.getString("Ejercicio_id");

                                        listaClientes.add(new ItemEjercicio(nombre,id,registroCliente,dianum));
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
                        Toast.makeText(InstructorAsignarEjercicio.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(InstructorAsignarEjercicio.this);
        x.add(peticion);

    }

    private void recibirdatos(){
        Intent intent= getIntent();
        Bundle bundle = intent.getExtras();
        stringMusculo =(String) bundle.get("MUSCULO");
        dianum = (String) bundle.get("DIANUM");
        txtvMusculo.setText(stringMusculo);
    }
}
