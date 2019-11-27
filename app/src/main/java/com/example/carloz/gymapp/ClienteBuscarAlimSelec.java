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

import org.json.JSONException;
import org.json.JSONObject;

public class ClienteBuscarAlimSelec extends AppCompatActivity {


    TextView txtvMarca, txtvCarboH, txtvProte, txtvCalorias, txtvGrasas, txtvTipo, txtvNombre, txtvMedida, txtvPorcion;
    String stringAlimento, stringTiempo, user;
    EditText etxtCantidad;
    Button btnAgregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_buscar_alim_selec);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        txtvMarca = findViewById(R.id.txtvMarca_ClienteBuscarAlimSelec);
        txtvCarboH = findViewById(R.id.txtvCarbh_CantidadAlimentoSeleccionado);
        txtvProte = findViewById(R.id.txtvProteinas_CantidadAlimentoSeleccionado);
        txtvCalorias = findViewById(R.id.txtvCalorias_ClienteBuscarAlimSelec);
        txtvGrasas = findViewById(R.id.txtvGrasas_ClienteBuscarAlimSelec);
        txtvTipo = findViewById(R.id.txtvTipo_ClienteBuscarAlimSelec);
        txtvNombre = findViewById(R.id.txtvAlimentoNombre_ClienteBuscarAlimSelec);
        btnAgregar = findViewById(R.id.btnAgregar_ClienteBuscarAlimSelec);
        etxtCantidad = findViewById(R.id.etxtCantidad_ClienteBuscarAlimSelec);
        txtvMedida = findViewById(R.id.txtvMedida_ClienteBuscarAlimSelec);
        txtvPorcion = findViewById(R.id.porcion_ClienteBuscarAlimSelec);

        Typeface Thin = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Thin.ttf");
        txtvNombre.setTypeface(Thin);
        //Toast.makeText(this, "aqui", Toast.LENGTH_SHORT).show();
        recibirDatos();

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
        String url = "http://thegymlife.online/php/cliente/Cliente_Ingresar_Alimento_Diario.php?registro="+Login.Registro+"&alimento="+stringAlimento
                +"&tipo="+stringTiempo+"&cantidad="+etxtCantidad.getText().toString().trim();

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

                                            Toast.makeText(ClienteBuscarAlimSelec.this,"Alimento asignado",Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(ClienteBuscarAlimSelec.this,ClienteAlimentacion.class);
                                            intent.putExtra("BANDERA","1");
                                            intent.putExtra("REGISTRO",user);
                                            startActivity(intent);
                                            finish();
                                            break;
                                        case "Error":
                                            Toast.makeText(ClienteBuscarAlimSelec.this,"Alimento no enviada",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(ClienteBuscarAlimSelec.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(ClienteBuscarAlimSelec.this);
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
                                    String Cantidad = response.getString("Alimento_Cantidad");
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
                                            txtvPorcion.setText(Cantidad);


                                            break;
                                        case "Error":
                                            Toast.makeText(ClienteBuscarAlimSelec.this,"Fallo de conexión",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(ClienteBuscarAlimSelec.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(ClienteBuscarAlimSelec.this);
        x.add(peticion);



    }
}
