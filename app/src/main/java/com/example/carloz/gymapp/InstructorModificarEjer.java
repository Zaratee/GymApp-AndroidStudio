package com.example.carloz.gymapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import com.example.carloz.gymapp.adaptador.AdaptadorEjercicioEdit;
import com.example.carloz.gymapp.items.ItemEjercicio;
import com.example.carloz.gymapp.items.ItemEjercicioEditar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class InstructorModificarEjer extends AppCompatActivity {

    TextView txtvTitulo;
    String stringTitulo;
    ArrayList<ItemEjercicioEditar> listaClientes;
    String Nombre,Area,Series,Repeticiones, ID, EjercicioID,Peso;
    RecyclerView recyclerClientes;
    String registroCliente, dianum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_modificar_ejer);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        listaClientes = new ArrayList<>();

        recyclerClientes = (RecyclerView) findViewById(R.id.listvEjercicios_InstructorModificarEjer);
        recyclerClientes.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        txtvTitulo = (TextView) findViewById(R.id.txtvDia_InstructorModificarEjer);
        recibirDatos();



        conexionBDEjercicios();


    }

    private void recibirDatos() {
        Intent intent= getIntent();
        Bundle bundle = intent.getExtras();
        stringTitulo =(String) bundle.get("DIA");
        dianum = (String) bundle.get("DIANUM");
        registroCliente = (String) bundle.get("REGISTRO");
        txtvTitulo.setText(stringTitulo);
        Toast.makeText(this, ""+dianum, Toast.LENGTH_SHORT).show();
    }

    private void conexionBDEjercicios() {
        String url = "http://thegymlife.online/php/instructor/Entrenador_Lista_Ejercicios_Cliente.php?registro="+registroCliente+"&dia="+dianum;
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
                                    JSONArray jsonArray = response.getJSONArray("Cliente");
                                    AdaptadorEjercicioEdit adapter = new AdaptadorEjercicioEdit(listaClientes);
                                    adapter.contexto= InstructorModificarEjer.this;
                                    for (int i =0; i<jsonArray.length();i++){
                                        JSONObject clientes = jsonArray.getJSONObject(i);
                                        Nombre =clientes.getString("Ejercicio_Nombre");
                                        ID =clientes.getString("id_Rutina");
                                        Area =clientes.getString("Ejercicio_Area");
                                        Series =clientes.getString("Numero_Series");
                                        Repeticiones =clientes.getString("Numero_Repeticiones");
                                        EjercicioID = clientes.getString("Ejercicio_id");
                                        Peso = clientes.getString("Numero_Peso");

                                        listaClientes.add(new ItemEjercicioEditar( Nombre,Area,Series,Repeticiones,ID,EjercicioID, Peso,registroCliente));
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
                        Toast.makeText(InstructorModificarEjer.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(InstructorModificarEjer.this);
        x.add(peticion);

    }

}
