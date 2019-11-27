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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BuscarAlimentoSeleccionado extends AppCompatActivity {

    TextView txtvMarca, txtvCarboH, txtvProte, txtvCalorias, txtvGrasas, txtvTipo, txtvNombre, txtvMedida, txtvPorcion;
    String stringAlimento, stringTiempo, user;
    EditText etxtCantidad;
    Button btnAgregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_alimento_seleccionado);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        txtvMarca = findViewById(R.id.txtvMarca_BuscarAlimentoSeleccionado);
        txtvCarboH = findViewById(R.id.txtvCarbh_CantidadAlimentoSeleccionado);
        txtvProte = findViewById(R.id.txtvProteinas_CantidadAlimentoSeleccionado);
        txtvCalorias = findViewById(R.id.txtvCalorias_BuscarAlimentoSeleccionado);
        txtvGrasas = findViewById(R.id.txtvGrasas_BuscarAlimentoSeleccionado);
        txtvTipo = findViewById(R.id.txtvTipo_BuscarAlimentoSeleccionado);
        txtvNombre = findViewById(R.id.txtvAlimentoNombre_BuscarAlimentoSeleccionado);
        btnAgregar = findViewById(R.id.btnAgregar_BuscarAlimentoSeleccionado);
        etxtCantidad = findViewById(R.id.etxtCantidad_BuscarAlimentoSeleccionado);
        txtvMedida = findViewById(R.id.txtvMedida_BuscarAlimentoSeleccionado);
        txtvPorcion = findViewById(R.id.porcion_BuscarAlimentoSeleccionado);

        recibirDatos();
        Typeface Thin = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Thin.ttf");
        txtvNombre.setTypeface(Thin);
        SharedPreferences preferences = getSharedPreferences("Cuemta",Context.MODE_PRIVATE);
        user = preferences.getString("NutritClientReg","nada");

        //Toast.makeText(this, "id alimento: "+stringAlimento, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "regNutr: "+Login.Registro, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "regClien: "+user, Toast.LENGTH_SHORT).show();

        BDinfoAlimento();
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conexionBDSubir();
            }
        });
    }
    private void recibirDatos() {
        Intent intent= getIntent();
        Bundle bundle = intent.getExtras();
        stringTiempo = (String) bundle.get("Tipo");
        stringAlimento =(String) bundle.get("Alimento");

    }


    private void conexionBDSubir() {
        String url = "http://thegymlife.online/php/nutriologo/Nutriologo_Asignar_Alimentos_Cliente.php?registro="+user+"&alimento="+stringAlimento
                +"&tipo="+stringTiempo+"&cantidad="+etxtCantidad.getText().toString().trim()+"&nutriologo="+Login.Registro;
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
                                    switch(valor) {
                                        case "OK":

                                            Toast.makeText(BuscarAlimentoSeleccionado.this,"Alimento asignado",Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(BuscarAlimentoSeleccionado.this,NutriologoClienteAsignar.class);
                                            intent.putExtra("BANDERA","1");
                                            intent.putExtra("REGISTRO",user);
                                            startActivity(intent);
                                            break;
                                        case "Error":
                                            Toast.makeText(BuscarAlimentoSeleccionado.this,"Queja no enviada",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(BuscarAlimentoSeleccionado.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(BuscarAlimentoSeleccionado.this);
        x.add(peticion);

    }


    private void BDinfoAlimento() {

        String url = "http://thegymlife.online/php/nutriologo/Nutriologo_Visualizar_Alimento_Cliente.php?lista="+stringAlimento;
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
                                    String nombre = response.getString("Alimento_Nombre");
                                    String valor = response.getString("Estado");
                                    String Marca = response.getString("Alimento_Marca");
                                    String CarboH = response.getString("Alimento_Carbohidratos");
                                    String Prote = response.getString("Alimento_Proteinas");
                                    String Grasas = response.getString("Alimento_Grasas");
                                    String Tipo = response.getString("Alimento_Tipo");
                                    String Calorias = response.getString("Alimento_Calorias");
                                    String Medida = response.getString("Alimento_Medida");
                                    String Porcion = response.getString("Alimento_Cantidad");

                                    switch(valor) {
                                        case "OK":
                                            txtvNombre.setText(nombre);
                                            txtvCalorias.setText(Calorias);
                                            txtvCarboH.setText(CarboH);
                                            txtvGrasas.setText(Grasas);
                                            txtvProte.setText(Prote);
                                            txtvMarca.setText(Marca);
                                            txtvTipo.setText(Tipo);
                                            txtvMedida.setText(Medida);
                                            txtvPorcion.setText(Porcion);



                                            break;
                                        case "Error":
                                            Toast.makeText(BuscarAlimentoSeleccionado.this,"Fallo de conexión",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(BuscarAlimentoSeleccionado.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(BuscarAlimentoSeleccionado.this);
        x.add(peticion);



    }
}
