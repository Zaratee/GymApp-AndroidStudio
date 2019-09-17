package com.example.carloz.gymapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.carloz.gymapp.adaptador.AdaptadorBuscarAlimento;
import com.example.carloz.gymapp.items.ItemAlimentoBuscado;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ClienteBuscarAlimento extends AppCompatActivity {

    TextView txtvTiempo;
    Button btnBuscar;
    EditText etxtAlimento;

    ArrayList<ItemAlimentoBuscado> listaAlimentos;
    RecyclerView recyclerClientes;

    String Nombre, Cantidad, Marca, id, Tiempo, stringTiempo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_buscar_alimento);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        txtvTiempo = (TextView) findViewById(R.id.txtvTiempo_ClienteBuscarAlimento);
        Typeface Thin = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Thin.ttf");
        txtvTiempo.setTypeface(Thin);
        etxtAlimento = (EditText) findViewById(R.id.etxtAlimento_ClienteBuscarAlimento);
        btnBuscar = (Button) findViewById(R.id.btnBuscarAlimento_ClienteBuscarAlimento);

        recibirdatos();
        listaAlimentos = new ArrayList<>();
        recyclerClientes = (RecyclerView) findViewById(R.id.listvAlimentos_ClienteBuscarAlimento);
        recyclerClientes.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BDBusqueda();
            }
        });

    }

    private void recibirdatos(){
        Intent intent= getIntent();
        Bundle bundle = intent.getExtras();
        stringTiempo =(String) bundle.get("VARIABLE");
        txtvTiempo.setText(stringTiempo);
        switch (stringTiempo){
            case "Desayuno":
                Tiempo = "0";
                break;
            case "Almuerzo":
                Tiempo = "1";
                break;
            case "Cena":
                Tiempo = "2";
                break;
            case "Pasa bocas":
                Tiempo = "3";
                break;
        }
    }
    private void BDBusqueda() {

        InputMethodManager manager = (InputMethodManager)getSystemService(ClienteBuscarAlimento.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(btnBuscar.getWindowToken(),0);

        String url = "http://thegymlife.online/php/nutriologo/Nutriologo_Buscar_Alimento.php?nombre="+etxtAlimento.getText().toString().trim();
        url = url.replaceAll(" ", "%20");
        listaAlimentos.clear();

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
                                    JSONArray jsonArray = response.getJSONArray("Alimentos");

                                    AdaptadorBuscarAlimento adapter = new AdaptadorBuscarAlimento(listaAlimentos);

                                    adapter.contexto= ClienteBuscarAlimento.this;
                                    for (int i =0; i<jsonArray.length();i++){
                                        JSONObject clientes = jsonArray.getJSONObject(i);
                                        Nombre  =clientes.getString("Alimento_Nombre");
                                        Cantidad =clientes.getString("Alimento_Cantidad");
                                        Marca =clientes.getString("Alimento_Marca");
                                        id = clientes.getString("Alimento_id");

                                        listaAlimentos.add(new ItemAlimentoBuscado(Nombre,Marca,Cantidad,Tiempo,id));
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
                        Toast.makeText(ClienteBuscarAlimento.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(ClienteBuscarAlimento.this);
        x.add(peticion);


    }





}
