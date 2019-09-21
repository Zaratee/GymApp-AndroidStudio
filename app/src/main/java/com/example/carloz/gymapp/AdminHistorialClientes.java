package com.example.carloz.gymapp;

import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.carloz.gymapp.adaptador.AdaptadorAdminHistorial;
import com.example.carloz.gymapp.adaptador.AdaptadorBuscarAlimento;
import com.example.carloz.gymapp.adaptador.AdaptadorQueja;
import com.example.carloz.gymapp.items.ItemAlimentoBuscado;
import com.example.carloz.gymapp.items.ItemHistorial;
import com.example.carloz.gymapp.items.ItemQueja;
import com.example.carloz.gymapp.items.ItemUsuariosAdmin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdminHistorialClientes extends AppCompatActivity implements View.OnClickListener {

    TextView txtvEliminados, txtvCreados, txtvCliente, txtvEntrenadores, txtvNutriologos, Titulo;
    CardView crdvEliminados, crdvCreados, crdvCliente, crdvEntrenadores, crdvNutriologos;
    EditText etxtBuscar;
    Button btnBuscar;
    LinearLayout lnrLayoutbnts;
    ArrayList<ItemHistorial> listaClientes;
    RecyclerView recyclerClientes;
    Integer num= 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_historial_clientes);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        txtvEliminados = findViewById(R.id.txtvProgreso_ClientePerfil);
        txtvCreados = findViewById(R.id.txtvCodigoo_ClientePerfil);
        txtvCliente = findViewById(R.id.txtvActualizarContra_ClientePerfil);
        txtvEntrenadores = findViewById(R.id.txtvCerrarSesion_ClientePerfil);
        txtvNutriologos = findViewById(R.id.txtvActualizarContra_Nutriologo);
        Titulo = findViewById(R.id.txtvNoActionAgregar_AdminHistorialClientes);

        crdvCreados = findViewById(R.id.cardvCodigo_ClientePerfil);
        crdvEliminados = findViewById(R.id.cardvProgreso_ClientePerfil);
        crdvCliente = findViewById(R.id.cardvActualizarContra_ClientePerfil);
        crdvEntrenadores = findViewById(R.id.cardvCerrarSesion_ClientePerfil);
        crdvNutriologos = findViewById(R.id.cardvActualizarContra_Nutriologo);

        etxtBuscar = findViewById(R.id.etxtHistorial_AdminHistorialClientes);
        btnBuscar = findViewById(R.id.btnBuscarAlimento_ClienteBuscarAlimento);
        lnrLayoutbnts = findViewById(R.id.layoutbtnHistorial);

        recyclerClientes = findViewById(R.id.recycler_view_AdminHistorialClientes);
        listaClientes = new ArrayList<>();
        recyclerClientes.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        Typeface Thin = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Thin.ttf");
        Typeface Condensed = Typeface.createFromAsset(getAssets(),"fonts/RobotoCondensed-Light.ttf");
        Titulo.setTypeface(Thin);

        txtvEliminados.setTypeface(Condensed);
        txtvCreados.setTypeface(Condensed);
        txtvCliente.setTypeface(Condensed);
        txtvEntrenadores.setTypeface(Condensed);
        txtvNutriologos.setTypeface(Condensed);

        crdvCliente.setOnClickListener(this);
        crdvEliminados.setOnClickListener(this);
        crdvCreados.setOnClickListener(this);
        crdvEntrenadores.setOnClickListener(this);
        crdvNutriologos.setOnClickListener(this);
        btnBuscar.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cardvCodigo_ClientePerfil:
                //Creados
                lnrLayoutbnts.setVisibility(View.GONE);
                recyclerClientes.setVisibility(View.VISIBLE);
                conexionBuscar("creados");
                num=0;
                break;
            case R.id.cardvProgreso_ClientePerfil:
                //eliminados

                lnrLayoutbnts.setVisibility(View.GONE);
                recyclerClientes.setVisibility(View.VISIBLE);
                conexionBuscar("eliminados");
                num=0;
                break;
            case R.id.cardvActualizarContra_ClientePerfil:
                //Cliente

                lnrLayoutbnts.setVisibility(View.GONE);
                recyclerClientes.setVisibility(View.VISIBLE);
                conexionBuscar("clientes");
                num=0;
                break;
            case R.id.cardvCerrarSesion_ClientePerfil:
                //Entrenadores

                lnrLayoutbnts.setVisibility(View.GONE);
                recyclerClientes.setVisibility(View.VISIBLE);
                conexionBuscar("entrenadores");
                num=0;
                break;
            case R.id.cardvActualizarContra_Nutriologo:
                //Nutriologos

                lnrLayoutbnts.setVisibility(View.GONE);
                recyclerClientes.setVisibility(View.VISIBLE);
                conexionBuscar("nutriologos");
                num=0;
                break;
            case R.id.btnBuscarAlimento_ClienteBuscarAlimento:
                String buscar = etxtBuscar.getText().toString();
                if (!buscar.isEmpty()){
                    lnrLayoutbnts.setVisibility(View.GONE);
                    recyclerClientes.setVisibility(View.VISIBLE);
                    conexionBuscar(etxtBuscar.getText().toString().trim());
                    num=0;
                }

                break;
        }
    }

    void conexionBuscar(String dato){

        InputMethodManager manager = (InputMethodManager)getSystemService(ClienteBuscarAlimento.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(btnBuscar.getWindowToken(),0);

        String url = "http://thegymlife.online/php/admin/Administrador_Lista_Filtrado_Historial_Usuarios.php?buscar="+dato;
        url = url.replaceAll(" ", "%20");
        listaClientes.clear();

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
                                    JSONArray jsonArray = response.getJSONArray("Usuarios");
                                    String Nombre,Apellido,Registro,Telefono,Estado,Fecha;
                                    AdaptadorAdminHistorial adapter = new AdaptadorAdminHistorial(listaClientes);
                                    adapter.contexto= AdminHistorialClientes.this;
                                    for (int i =0; i<jsonArray.length();i++){

                                        JSONObject clientes = jsonArray.getJSONObject(i);
                                        Nombre  =clientes.getString("Usuario_Nombre");
                                        Apellido =clientes.getString("Usuario_Apellido");
                                        Registro =clientes.getString("Usuario_Registro");
                                        Telefono = clientes.getString("Usuario_Telefono");
                                        Estado = clientes.getString("Usuario_Estado");
                                        Fecha = clientes.getString("Usuario_Fecha");

                                        listaClientes.add(new ItemHistorial(Nombre, Apellido,Registro, Telefono, Fecha, Estado));
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
                        Toast.makeText(AdminHistorialClientes.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(AdminHistorialClientes.this);
        x.add(peticion);



    }
    public void onBackPressed() {
        if (num==1){
            finish();
        }
        else {
            lnrLayoutbnts.setVisibility(View.VISIBLE);
            recyclerClientes.setVisibility(View.GONE);
            num++;

        }
    }


}
